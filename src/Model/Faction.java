package Model;

import java.util.ArrayList;

public class Faction {
    public enum FactionType {
        GreatCthulhu, CrawlingChaos, BlackGoat, YellowSign, OpenerOfTheWay, Sleeper, Windwalker
    }

    int energy;
    int cultistAlive;
    int GatesControlled;
    int unitsCaptured;
    int activeGOO;
    Core core;
    FactionType faction;

    Faction(Core core) {
        this.core = core;
    }

    void recountEnergy() {
        energy = cultistAlive + unitsCaptured + 2 * GatesControlled + core.entityBase.neutralGateExists;
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
