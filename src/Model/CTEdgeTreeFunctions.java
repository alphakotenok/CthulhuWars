package Model;

import java.util.ArrayList;

import Model.FactionEnum.FactionType;
import Model.GameVariables.PerformableAction;

class CTEdgeTreeFunctions {

    static void nextPlayerMovePreparation(Core core) {
        core.var.locationPossibleToAttack.clear();
        for (Location loc : core.map.locations) {
            core.var.locationPossibleToAttack.add(loc);
        }
        core.var.action = PerformableAction.None;
        core.getCurFact().clearMovedEntities();
        core.gates.generalGatesCheck();
    }

    static void none(Core core) {

    };

    static void permutateFactions(Core core) {
        core.var.factionsList.clear();
        for (int i = 0; i < core.var.numOfPlayers; ++i) {
            core.var.factionsList.add(FactionType.values()[core.var.chosenPerm.get(i)]);
        }
        core.var.playerCounter = 0;
    };

    static void startLocPlacement(Core core) {
        core.getCurFact().setStartEntities(core.var.chosenLocation);
        core.var.turn = core.var.getNextTurn(core.var.turn);
        ++core.var.playerCounter;
    }

    static void startLocLastPlacement(Core core) {
        core.getCurFact().setStartEntities(core.var.chosenLocation);
        core.var.turn = core.var.firstPlayer;
        core.var.playerCounter = 0;

        nextPlayerMovePreparation(core);
    }

    static void openBook(Core core) {
        core.factionBase.getFactionFromEnum(core.var.bookReceiver).books
                .get(core.var.bookNum).openBook = core.var.bookSlot;
        core.var.bookReceiver = null;
    }

    static void passTurn(Core core) {
        core.getCurFact().skip = true;
        core.getCurFact().energy = 0;
        while (core.getCurFact().skip)
            core.var.turn = core.var.getNextTurn(core.var.turn);
        ++core.var.playerCounter;
        nextPlayerMovePreparation(core);
    }

    static void doneTurn(Core core) {
        core.var.turn = core.var.getNextTurn(core.var.turn);
        while (core.getCurFact().skip)
            core.var.turn = core.var.getNextTurn(core.var.turn);
        nextPlayerMovePreparation(core);
    }

    static void lastPassTurn(Core core) {
        core.var.playerCounter = 0;
        int maxEnergy = 0;
        for (int i = 0; i < core.var.numOfPlayers; ++i) {
            Faction fact = core.factionBase.getFactionFromEnum(core.var.factionsList.get(i));
            fact.prepareForNextRound();
            maxEnergy = Math.max(maxEnergy, fact.energy);
        }
        core.var.totalSkip = 0;
        core.var.firstPlayerCandidates = new ArrayList<>();
        for (int i = 0; i < core.var.numOfPlayers; ++i) {
            Faction fact = core.factionBase.getFactionFromEnum(core.var.factionsList.get(i));
            if (maxEnergy == fact.energy) {
                core.var.firstPlayerCandidates.add(core.var.factionsList.indexOf(fact.faction));
            }
        }
        if (core.var.firstPlayerCandidates.size() == 1) {
            core.var.firstPlayer = core.var.firstPlayerCandidates.get(0);
        }
    }

    static void doneRitual(Core core) {
        core.var.turn = core.var.getNextTurn(core.var.turn);
        ++core.var.playerCounter;
    }

    static void lastDoneRitual(Core core) {
        core.var.turn = core.var.firstPlayer;
        core.var.playerCounter = 0;
        for (int i = 0; i < core.var.numOfPlayers; ++i) {
            Faction fact = core.getSomeFact(i);
            fact.isRitualPerformed = false;
        }

        nextPlayerMovePreparation(core);
    }

    static void performRitual(Core core) {
        core.ritual.performRitual(core.var.factionsList.get(core.var.turn));
        if (core.getCurFact().faction == FactionType.Sleeper)
            core.var.didRitualSleeper = true;
    }

    static void revealElderSign(Core core) {
        core.getCurFact().revealSign(core.var.signToReveal);
    }

    static void performMovement(Core core) {
        core.var.chosenEntity.performMovement(core.var.chosenLocation, core.var.chosenDestination);
        core.getCurFact().energy--;
    }

    static void buildGate(Core core) {
        core.gates.buildGate(core.var.chosenLocation);
        core.getCurFact().energy -= 3;
        core.var.action = PerformableAction.GateBuilding;
    }

    static void spawnEntity(Core core) {
        core.var.chosenEntity.spawn(core.var.chosenLocation);
        core.getCurFact().energy -= core.var.chosenEntity.costFunc.activate(core);
        core.var.action = PerformableAction.Spawn;
    }

    static void captureEntity(Core core) {
        core.var.chosenEntity.getCaptured(core.var.chosenLocation);
        --core.getCurFact().energy;
        core.var.action = PerformableAction.Capture;
        if (core.getCurFact().faction == FactionType.CrawlingChaos &&
                core.var.chosenEntity.name == "Cultist") {
            core.var.didCrawlingChaosCaptureCultist = true;
        }
    }

    static void killTwoCultistsBlackGoat(Core core) {
        EntitySet entities = core.getCurFact().getEntitySetByName("Cultist");
        Location locFirstCultist = entities.positions.get(core.var.firstCultistToKillByBlackGoat);
        Location locSecondCultist = entities.positions.get(core.var.secondCultistToKillByBlackGoat);
        entities.kill(locFirstCultist);
        entities.kill(locSecondCultist);
        core.var.action = PerformableAction.Extra;
        core.var.didBlackGoatKillTwoCultists = true;
    }

    static void loseEnergy4(Core core) {
        core.getCurFact().energy -= 4;
        core.var.action = PerformableAction.Extra;
        core.var.loseEnergy4 = true;
    }

    static void loseEnergy6(Core core) {
        core.getCurFact().energy -= 6;
        core.var.action = PerformableAction.Extra;
        core.var.loseEnergy6 = true;
    }

    static void loseEnergy10(Core core) {
        core.getCurFact().energy -= 10;
        core.var.action = PerformableAction.Extra;
        core.var.loseEnergy4 = true;
        core.var.loseEnergy6 = true;
    }

    static void lose3EnergyAndGet1EnergyForOthers(Core core) {
        core.getCurFact().energy -= 3;
        ArrayList<FactionType> factions = core.var.factionsList;
        for (FactionType faction : factions) {
            if (faction != core.getCurFact().faction) {
                core.factionBase.getFactionFromEnum(faction).energy++;
                if (core.factionBase.getFactionFromEnum(faction).skip == true) {
                    core.factionBase.getFactionFromEnum(faction).skip = false;
                    core.var.totalSkip--;
                }
            }
        }
        core.var.action = PerformableAction.Extra;
        core.var.did3EnergyLoseAnd1EnergyForOthersGet = true;
    }

    static void sleeperSpendAndGetEnergy(Core core) {
        core.getCurFact().energy -= 3;
        Faction faction = core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction));
        faction.energy += 3;
        if (faction.skip == true) {
            faction.skip = false;
            core.var.totalSkip--;
        }
        core.var.action = PerformableAction.Extra;
        core.var.did3EnergyLoseAnd3EnergyPresent = true;
    }

    static void lose3EnergyAnd1EnergyForOthers(Core core) {
        core.getCurFact().energy -= 3;
        ArrayList<FactionType> factions = core.var.factionsList;
        for (FactionType faction : factions) {
            if (faction != core.getCurFact().faction) {
                if (core.factionBase.getFactionFromEnum(faction).energy != 0)
                    core.factionBase.getFactionFromEnum(faction).energy--;
            }
        }
        core.var.action = PerformableAction.Extra;
        core.var.did3EnergyAnd1EnergyForOthersLose = true;
    }

    static void entityToKillFirstPlayer(Core core) {
        EntitySet entity = core.getCurFact().getEntitiesInLocation(core.var.battleLocation).get(core.var.entityToKill);
        entity.kill(core.var.battleLocation);
        core.var.firstPlayerKill--;
    }

    static void entityToKillSecondPlayer(Core core) {
        EntitySet entity = core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction))
                .getEntitiesInLocation(core.var.battleLocation).get(core.var.entityToKill);
        entity.kill(core.var.battleLocation);
        core.var.secondPlayerKill--;
    }

    static void performMoveInjureFirstPlayer(Core core) {
        core.getCurFact().core.getCurFact().getEntitiesInLocation(core.var.battleLocation).get(core.var.entityToKill).performMovementInjured(core.var.battleLocation, core.var.chosenDestination);
        core.var.firstPlayerInjure--;
    }

    static void performMoveInjureSecondPlayer(Core core) {
        core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction))
        .getEntitiesInLocation(core.var.battleLocation).get(core.var.entityToKill).performMovementInjured(core.var.battleLocation, core.var.chosenDestination);
        core.var.secondPlayerInjure--;
    }

    static void KillInjureFirstPlayer(Core core) {
        core.getCurFact().core.getCurFact().getEntitiesInLocation(core.var.battleLocation).get(core.var.entityToKill).kill(core.var.battleLocation);
        core.var.firstPlayerInjure = 0;
    }

    static void KillInjureSecondPlayer(Core core) {
        core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction))
                .getEntitiesInLocation(core.var.battleLocation).get(core.var.entityToKill).kill(core.var.battleLocation);
        core.var.secondPlayerInjure = 0;
    }

    static void attack(Core core){
        boolean allBooksOpened = true;
        for (int i = 0; i < 6; i++) {
            if (!core.getCurFact().isBookOpened(i))
                allBooksOpened = false;
        }
        if(!allBooksOpened)
         core.var.action = PerformableAction.Extra;
        core.getCurFact().energy --;
    }
}
