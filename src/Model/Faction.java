package Model;

import java.util.ArrayList;

public class Faction {
    public enum FactionType {
        GreatCthulhu, CrawlingChaos, BlackGoat, YellowSign, OpenerOfTheWay, Sleeper, Windwalker
    }

    int energy;
    int cultistAlive;
    int gatesControlled;
    int unitsCaptured;
    int activeGOO;
    Core core;
    FactionType faction;

    Faction(Core core) {
        this.core = core;
        energy = 8;
        cultistAlive = 6;
        gatesControlled = 1;
        unitsCaptured = 0;
        activeGOO = 0;
        victoryPoints = 0;
    }

    void recountEnergy() {
        energy = cultistAlive + unitsCaptured + 2 * gatesControlled + core.entityBase.neutralGateExists;
    }

    void getElderSign() {
        int sign = core.ritual.getSign();
        if (sign == -1) {
            victoryPoints += 1;
            return;
        }
        elderSignList.add(sign);
    }

    int victoryPoints;

    ArrayList<Integer> elderSignList;
}
