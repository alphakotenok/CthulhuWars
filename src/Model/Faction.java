package Model;

import java.io.FileInputStream;
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
        cultistAlive = 6;
        gatesControlled = 1;
        unitsCaptured = 0;
        activeGOO = 0;
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
            try {
                FileInputStream fileStream = new FileInputStream(path);
                Image icon = new Image(fileStream);
                bookImages.add(icon);
            } catch (Exception e) {
            }
        }
    }

    boolean isBookOpened(int bookNum) {
        boolean ans = false;
        for (int i : openedBooks) {
            if (i == bookNum) {
                ans = true;
                break;
            }
        }
        return ans;
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
        energy = cultistAlive + unitsCaptured + 2 * gatesControlled + core.gates.getLocationsWithFreeGates().size();
        unitsCaptured = 0;
    }

    void recountPoints() {
        victoryPoints += gatesControlled;
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
            try {
                FileInputStream fileStream = new FileInputStream(path);
                Image icon = new Image(fileStream);
                ans.add(icon);
            } catch (Exception e) {
            }
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
        for (int i = 0; i < 6; ++i) {
            cultist.spawn(loc);
        }
        // core.gates.buildGate(loc);
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

}
