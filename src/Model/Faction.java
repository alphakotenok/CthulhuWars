package Model;

import java.util.ArrayList;

import javafx.scene.image.Image;
import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;

class Faction {

    String name;
    int energy;
    int unitsCaptured;
    boolean skip;
    Core core;
    FactionType faction;
    int victoryPoints;
    ArrayList<Integer> elderSignList = new ArrayList<>();

    // will be better
    ArrayList<Integer> openedBooks = new ArrayList<>();
    ArrayList<Image> bookImages = new ArrayList<>();
    ArrayList<String> bookNames = new ArrayList<>();

    boolean isRitualPerformed;

    ArrayList<EntitySet> entitySetsList = new ArrayList<>();

    void fillBookNames() {
        bookNames.add("Aboba");
        bookNames.add("Aboba");
        bookNames.add("Aboba");
        bookNames.add("Aboba");
        bookNames.add("Aboba");
        bookNames.add("Aboba");
    }

    Faction(String name, FactionType faction, Core core) {
        this.core = core;
        this.name = name;
        this.faction = faction;
        energy = 8;
        unitsCaptured = 0;
        victoryPoints = 0;
        skip = false;
        isRitualPerformed = false;
        for (int i = 0; i < 6; ++i) {
            openedBooks.add(-1);
        }
        loadBooksImages();
        getElderSign();
        fillBookNames();
    }

    void loadBooksImages() {
        for (int i = 0; i < 6; ++i) {
            String path = "images/Spellbooks/" + name + "/" + i + ".png";
            bookImages.add(Core.getImage(path));
        }
    }

    boolean isBookOpened(int bookNum) {
        for (int i : openedBooks) {
            if (i == bookNum) {
                return true;
            }
        }
        return false;
    }

    boolean isQuestCompletedEarlier(int questNum) {
        return ((int) openedBooks.get(questNum)) != -1;
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
        energy = getEntitySetByName("Cultist").positions.size() + unitsCaptured
                + 2 * core.gates.getNumOfControlledGates(faction)
                + core.gates.getLocationsWithFreeGates().size();
        unitsCaptured = 0;
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
}
