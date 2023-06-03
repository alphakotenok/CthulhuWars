package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.image.Image;
import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;

class Faction {

    String name;
    int energy;
    ArrayList<EntitySet> entitiesCaptured;
    boolean skip;
    Core core;
    FactionType faction;
    int victoryPoints;
    ArrayList<Integer> elderSignList = new ArrayList<>();

    ArrayList<Book> books = new ArrayList<>();

    boolean isRitualPerformed;

    ArrayList<EntitySet> entitySetsList = new ArrayList<>();

    void fillBooks() {
    }

    Faction(String name, FactionType faction, Core core) {
        this.core = core;
        this.name = name;
        this.faction = faction;
        energy = 8;
        entitiesCaptured = new ArrayList<>();
        victoryPoints = 0;
        skip = false;
        isRitualPerformed = false;
        getElderSign();
        fillBooks();
    }

    boolean isBookOpened(int bookNum) {
        return books.get(bookNum).openBook != -1;
     //   for (Book book : books) {
       //     if (book.openBook == bookNum) {
         //       return true;
     //       }
    //    }
    //    return false;
    }

    boolean isQuestCompletedEarlier(int questNum) {
        for(Book book : books)
        if(book.openBook == questNum) return true;

        return false;
       // return ((int) books.get(questNum).openBook) != -1;
    }

    boolean isQuestCompleted(int questNum) {
        return false;
    }

    void getElderSign() {
        int sign = core.ritual.getSign();
        if (sign == -1) {
            victoryPoints += 1;
            return;
        }
        elderSignList.add(sign);
    }

    void recountEnergy() {
        energy = getEntitySetByName("Cultist").positions.size() + entitiesCaptured.size()
                + 2 * core.gates.getNumOfControlledGates(faction)
                + core.gates.getLocationsWithFreeGates().size();
        for (EntitySet entity : entitiesCaptured) {
            ++entity.limit;
        }
    }

    void recountPoints() {
        victoryPoints += core.gates.getNumOfControlledGates(faction);
    }

    void prepareForNextRound() {
        recountEnergy();
        skip = false;
        recountPoints();
    }

    void revealSign(int num) {
        victoryPoints += elderSignList.get(num);
        elderSignList.remove(num);
    }

    static ArrayList<Image> getEntityImages(ArrayList<String> names) {
        ArrayList<Image> ans = new ArrayList<>();
        for (String name : names) {
            String path = "images/Entities/" + name + ".png";
            ans.add(Core.getImage(path));
        }
        return ans;
    }

    EntitySet getEntitySetByName(String name) {
        for (EntitySet entity : entitySetsList) {
            if (entity.name.equals(name)) {
                return entity;
            }
        }
        return null;
    }

    void setStartEntities(Location loc) {
        EntitySet cultist = getEntitySetByName("Cultist");
        if (cultist == null)
            return;
        core.gates.buildGate(loc);
        for (int i = 0; i < 6; ++i) {
            cultist.spawn(loc);
        }
    }

    ArrayList<EntitySet> getEntitiesInLocation(Location loc) {
        ArrayList<EntitySet> ans = new ArrayList<>();
        for (EntitySet entity : entitySetsList) {
            int num = entity.countInLocation(loc);
            for (int i = 0; i < num; ++i) {
                ans.add(entity);
            }
        }
        return ans;
    }

    void clearMovedEntities() {
        for (EntitySet entity : entitySetsList) {
            entity.moved.clear();
        }
    }

    int countGOO() {
        int ans = 0;
        for (EntitySet entity : entitySetsList) {
            if (entity.category != Category.GOO)
                continue;
            ans += entity.positions.size();
        }
        return ans;
    }

    boolean canSummonGOO() {
        return true;
    }

    ArrayList<Integer> enableToSpawn() {
        ArrayList<Integer> ans = new ArrayList<>();
        if (core.gates.getNumOfControlledGates(faction) == 0) {
            return ans;
        }
        for (int i = 0; i < entitySetsList.size(); ++i) {
            EntitySet entity = entitySetsList.get(i);
            if (entity.category == Category.GOO) {
                if (!canSummonGOO())
                    continue;
            }
            if (energy < entity.costFunc.activate(core))
                continue;
            if (entity.positions.size() == entity.limit)
                continue;
            ans.add(i);
        }
        return ans;
    }

    ArrayList<ArrayList<Integer>> getEntitiesToBeCaptured() {

        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < core.map.locations.size(); ++i) {
            Location loc = core.map.locations.get(i);
            int maxEntity = 0;
            for (int j = 0; j < entitySetsList.size(); ++j) {
                EntitySet entity = entitySetsList.get(j);
                if (!entity.positions.contains(loc))
                    continue;
                if (entity.category == Category.Monster)
                    maxEntity = Math.max(maxEntity, 1);
                if (entity.category == Category.GOO)
                    maxEntity = Math.max(maxEntity, 2);
            }
            for (int j = 0; j < core.factionBase.factList.size(); ++j) {
                Faction fact = core.factionBase.factList.get(j);
                if (fact == this)
                    continue;
                int maxOpponentEntity = 0;
                for (int k = 0; k < fact.entitySetsList.size(); ++k) {
                    EntitySet entity = fact.entitySetsList.get(k);
                    if (!entity.positions.contains(loc))
                        continue;
                    if (entity.category == Category.Monster)
                        maxOpponentEntity = Math.max(maxOpponentEntity, 1);
                    if (entity.category == Category.GOO)
                        maxOpponentEntity = Math.max(maxOpponentEntity, 2);
                }
                if (maxOpponentEntity >= maxEntity)
                    continue;
                for (int k = 0; k < fact.entitySetsList.size(); ++k) {
                    EntitySet entity = fact.entitySetsList.get(k);
                    if (!entity.positions.contains(loc))
                        continue;
                    if (entity.category == Category.Cultist) {
                        for (int t = 0; t < Collections.frequency(entity.positions, loc); ++t) {
                            ans.add(new ArrayList<>(Arrays.asList(i, j, k)));
                        }
                    }
                }
            }
        }
        return ans;
    }
}
