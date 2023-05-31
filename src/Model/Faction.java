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

    void fillFactionNames() {
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

    void getElderSign() {
        int sign = core.ritual.getSign();
        if (sign == -1) {
            victoryPoints += 1;
            return;
        }
        elderSignList.add(sign);
    }

    boolean isQuestCompleted(int questNum) {
        return false;
    }

    void recountEnergy() {
        energy = cultistAlive + unitsCaptured + 2 * gatesControlled + core.entityBase.neutralGateExists;
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
}
