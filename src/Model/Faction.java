package Model;

import java.util.ArrayList;

import javafx.scene.image.Image;
import Model.FactionEnum.FactionType;

class Faction {

    String name;
    int energy;
    int cultistAlive;
    int gatesControlled;
    int unitsCaptured;
    int activeGOO;
    boolean skip;
    Core core;
    FactionType faction;
    int victoryPoints;
    ArrayList<Integer> elderSignList = new ArrayList<>();

    // will be better
    ArrayList<Book> books = new ArrayList<>();

    boolean isRitualPerformed;

    ArrayList<EntitySet> entitySetsList = new ArrayList<>();

    void fillBooks() {}

    Faction(String name, FactionType faction, Core core) {
        this.core = core;
        this.name = name;
        this.faction = faction;
        energy = 8;
        unitsCaptured = 0;
        activeGOO = 0;
        victoryPoints = 0;
        skip = false;
        isRitualPerformed = false;
        getElderSign();
        fillBooks();
    }


    boolean isBookOpened(int bookNum) {
        for (Book book : books) {
            if (book.openBook == bookNum) {
                return true;
            }
        }
        return false;
    }

    boolean isQuestCompletedEarlier(int questNum) {
        return ((int) books.get(questNum).openBook) != -1;
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
}
