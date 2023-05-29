package Model;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Faction {
    public enum FactionType {
        GreatCthulhu, CrawlingChaos, BlackGoat, YellowSign, OpenerOfTheWay, Sleeper, Windwalker
    }

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
    ArrayList<Integer> elderSignList;
    ArrayList<Integer> openedBooks;
    ArrayList<Image> bookImages;

    Faction(String name, Core core) {
        elderSignList = new ArrayList<>();
        this.core = core;
        this.name = name;
        energy = 8;
        cultistAlive = 6;
        gatesControlled = 1;
        unitsCaptured = 0;
        activeGOO = 0;
        victoryPoints = 0;
        skip = false;
        openedBooks = new ArrayList<>();
        for (int i = 0; i < 6; ++i) {
            openedBooks.add(-1);
        }
        bookImages = new ArrayList<>();
    }

    void recountEnergy() {
        energy = cultistAlive + unitsCaptured + 2 * gatesControlled + core.entityBase.neutralGateExists;
        unitsCaptured = 0;
    }

    void getElderSign() {
        int sign = core.ritual.getSign();
        if (sign == -1) {
            victoryPoints += 1;
            return;
        }
        elderSignList.add(sign);
    }

}
