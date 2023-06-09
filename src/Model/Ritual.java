package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Model.FactionEnum.FactionType;

class Ritual {
    ArrayList<Integer> elderSignPool;
    ArrayList<Integer> ritualTrack;
    Core core;
    int ritualState;

    Ritual(Core core) {
        this.core = core;
        elderSignPool = new ArrayList<>();
        for (int i = 0; i < 18; ++i)
            elderSignPool.add(Integer.valueOf(1));
        for (int i = 0; i < 12; ++i)
            elderSignPool.add(Integer.valueOf(2));
        for (int i = 0; i < 6; ++i)
            elderSignPool.add(Integer.valueOf(3));
        if (core.var.numOfPlayers <= 3)
            ritualTrack = new ArrayList<>(Arrays.asList(5, 6, 7, 8, 9, 10));
        if (core.var.numOfPlayers == 4)
            ritualTrack = new ArrayList<>(Arrays.asList(5, 6, 7, 7, 8, 8, 9, 10));
        if (core.var.numOfPlayers == 5)
            ritualTrack = new ArrayList<>(Arrays.asList(5, 6, 6, 7, 7, 8, 8, 9, 9, 10));
        if (core.var.numOfPlayers == 6)
            ritualTrack = new ArrayList<>(Arrays.asList(5, 6, 6, 7, 7, 7, 8, 8, 8, 9, 9, 10));
        ritualState = 0;
    }

    boolean canPerformRitual(FactionType faction) {
        Faction fact = core.factionBase.getFactionFromEnum(faction);
        return (fact.energy >= ritualTrack.get(ritualState) && !fact.isRitualPerformed);
    }

    void performRitual(FactionType faction) {
        Faction curFac = core.factionBase.getFactionFromEnum(faction);
        curFac.energy -= ritualTrack.get(ritualState);
        curFac.victoryPoints += core.gates.getNumOfControlledGates(faction);
        for (int i = 0; i < curFac.countGOO(); ++i)
            curFac.getElderSign();
        ++ritualState;
        if (ritualState >= ritualTrack.size()) {
            --ritualState;
            core.var.endOfTheGame = true;
        }
        curFac.isRitualPerformed = true;
    }

    int getSign() {
        if (elderSignPool.size() == 0)
            return -1;
        Random rn = new Random();
        int pos = rn.nextInt(elderSignPool.size());
        int ans = elderSignPool.get(pos);
        elderSignPool.remove(pos);
        return ans;
    }
}
