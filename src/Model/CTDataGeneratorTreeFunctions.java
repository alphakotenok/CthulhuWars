package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Model.FactionEnum.FactionType;

class CTDataGeneratorTreeFunctions {
    static ArrayList<ArrayList<Integer>> justOne(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>(Arrays.asList(0)));
        return ans;
    }

    static ArrayList<ArrayList<Integer>> permutationGenerator(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        int CthulhuPlace = -1;
        for (int i = 0; i < core.var.numOfPlayers; ++i) {
            if (core.var.factionsList.get(i).equals(FactionType.GreatCthulhu)) {
                CthulhuPlace = i;
            }
        }
        ArrayList<Integer> order = new ArrayList<>();
        if (CthulhuPlace != -1)
            order.add(Integer.valueOf(0));
        ArrayList<Integer> order2 = new ArrayList<>();
        for (int i = 0; i < core.var.numOfPlayers; ++i) {
            if (i != CthulhuPlace)
                order2.add(Integer.valueOf(core.var.factionsList.get(i).ordinal()));
        }
        Collections.sort(order2);
        ArrayList<Integer> order3 = new ArrayList<>();
        order3.addAll(order);
        order3.addAll(order2);
        ans.add(order3);
        while (Permutation.findNextPermutation(order2)) {
            order3 = new ArrayList<>();
            order3.addAll(order);
            order3.addAll(order2);
            ans.add(order3);
        }
        return ans;
    }

    static ArrayList<ArrayList<Integer>> startLocGenerator(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Location> loc = core.map.startLoc.get(core.var.factionsList.get(core.var.turn).ordinal());
        for (Location l : loc)
            ans.add(new ArrayList<>(Arrays.asList(core.map.locations.indexOf(l))));
        return ans;
    }

    static ArrayList<ArrayList<Integer>> booksToOpen(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < 6; ++i) {
            if (core.factionBase.getFactionFromEnum(core.var.bookReceiver).isBookOpened(i)) {
                continue;
            }
            ans.add(new ArrayList<>(Arrays.asList(i)));
        }
        return ans;
    }

    static ArrayList<ArrayList<Integer>> firstPlayerCandidates(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < core.var.firstPlayerCandidates.size(); ++i) {
            ans.add(new ArrayList<>(Arrays.asList(core.var.firstPlayerCandidates.get(i))));
        }
        return ans;
    }

    static ArrayList<ArrayList<Integer>> wayGenerator(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>(Arrays.asList(0)));
        ans.add(new ArrayList<>(Arrays.asList(1)));
        return ans;
    }

    static ArrayList<ArrayList<Integer>> elderSigns(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        Faction fact = core.getCurFact();
        for (int i = 0; i < fact.elderSignList.size(); ++i) {
            ans.add(new ArrayList<>(Arrays.asList(i)));
        }
        return ans;
    }

    static ArrayList<ArrayList<Integer>> enableToMoveGenerator(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        Faction fact = core.getCurFact();
        for (int i = 0; i < fact.entitySetsList.size(); ++i) {
            EntitySet entity = fact.entitySetsList.get(i);
            ArrayList<Location> loc = entity.getEnableToMoveEnities();
            for (Location l : loc) {
                ans.add(new ArrayList<>(Arrays.asList(i, core.map.locations.indexOf(l))));
            }
        }
        return ans;
    }

    static ArrayList<ArrayList<Integer>> adjLocations(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Location> locList = core.var.chosenLocation.adj;
        for (Location loc : locList) {
            ans.add(new ArrayList<>(Arrays.asList(core.map.locations.indexOf(loc))));
        }
        return ans;
    }

    static ArrayList<ArrayList<Integer>> gatePossibleLocations(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Location> locList = core.gates.getPlacesToBuild();
        for (Location loc : locList) {
            ans.add(new ArrayList<>(Arrays.asList(core.map.locations.indexOf(loc))));
        }
        return ans;
    }

    static ArrayList<ArrayList<Integer>> spawnEntities(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Integer> entityNum = core.getCurFact().enableToSpawn();
        for (int num : entityNum) {
            ans.add(new ArrayList<>(Arrays.asList(num)));
        }
        return ans;
    }

    static ArrayList<ArrayList<Integer>> locationsToSpawn(Core core) {
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        ArrayList<Location> locations = core.gates.getControlledGates();
        for (Location loc : locations) {
            ans.add(new ArrayList<>(Arrays.asList(core.map.locations.indexOf(loc))));
        }
        return ans;
    }

    static ArrayList<ArrayList<Integer>> entitiesToCapture(Core core) {
        return core.getCurFact().getEntitiesToBeCaptured();
    }

    static ArrayList<ArrayList<Integer>> generatePairOfCultistToKill(Core core) {
        ArrayList<ArrayList<Integer>> pairOfCultist = new ArrayList<>();
        int numberOfCultists = core.getCurFact().getEntitySetByName("Cultist").positions.size();
        for (int i = 0; i < numberOfCultists; i++) {
            for (int j = i + 1; j < numberOfCultists; j++) {
                pairOfCultist.add(new ArrayList<Integer>(Arrays.asList(i, j)));
            }
        }
        return pairOfCultist;
    }

    static ArrayList<ArrayList<Integer>> generateFaction(Core core) {
        ArrayList<ArrayList<Integer>> factionToGenerate = new ArrayList<>();
        ArrayList<FactionType> factions = core.var.factionsList;
        for (int i = 0; i < factions.size(); i++) {
            if (core.factionBase.getFactionFromEnum(factions.get(i)) != core.getCurFact()) {
                factionToGenerate.add(new ArrayList<Integer>(Arrays.asList(i)));
            }
        }
        return factionToGenerate;
    }

    static ArrayList<ArrayList<Integer>> generateLocationsToAttack(Core core) {
        ArrayList<ArrayList<Integer>> locationsToAttack = new ArrayList<>();
        ArrayList<FactionType> factions = core.var.factionsList;
        int ind = 0;
        for (Location loc : core.var.locationPossibleToAttack) {
            if (core.getCurFact().getEntitiesInLocation(loc).size() != 0) {
                for (FactionType faction : factions) {
                    if (faction != core.getCurFact().faction
                            && core.factionBase.getFactionFromEnum(faction).getEntitiesInLocation(loc).size() != 0) {
                        locationsToAttack.add(new ArrayList<>(Arrays.asList(ind)));
                        break;
                    }
                }
            }
            ind++;
        }
        return locationsToAttack;
    }

    static ArrayList<ArrayList<Integer>> generateFactionsToAttack(Core core) {
        ArrayList<ArrayList<Integer>> factionsToAttack = new ArrayList<>();
        ArrayList<FactionType> factions = core.var.factionsList;
        int ind = 0;
        for (FactionType faction : factions) {
            if (faction != core.getCurFact().faction && core.factionBase.getFactionFromEnum(faction)
                    .getEntitiesInLocation(core.var.battleLocation).size() != 0) {
                factionsToAttack.add(new ArrayList<>(Arrays.asList(ind)));
            }
            ind++;
        }
        return factionsToAttack;
    }

    static ArrayList<ArrayList<Integer>> generateEntitiesFirstPlayer(Core core) {
        ArrayList<ArrayList<Integer>> entityToKill = new ArrayList<>();
        for (int i = 0; i < core.getCurFact().getEntitiesInLocation(core.var.battleLocation).size(); i++) {
            entityToKill.add(new ArrayList<>(Arrays.asList(i)));
        }
        return entityToKill;
    }

    static ArrayList<ArrayList<Integer>> generateEntitiesSecondPlayer(Core core) {
        ArrayList<ArrayList<Integer>> entityToKill = new ArrayList<>();
        for (int i = 0; i < core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction))
                .getEntitiesInLocation(core.var.battleLocation).size(); i++) {
            entityToKill.add(new ArrayList<>(Arrays.asList(i)));
        }
        return entityToKill;
    }

    static ArrayList<ArrayList<Integer>> generatePositionsFirstPlayer(Core core) {
        ArrayList<ArrayList<Integer>> positionsToMove = new ArrayList<>();
        int ind = 0;
        for (Location location : core.var.battleLocation.adj) {
            if (core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction))
                    .getEntitiesInLocation(location).size() == 0)
                positionsToMove.add(new ArrayList<>(Arrays.asList(ind)));

            ind++;
        }
        return positionsToMove;
    }

    static ArrayList<ArrayList<Integer>> generatePositionsSecondPlayer(Core core) {
        ArrayList<ArrayList<Integer>> positionsToMove = new ArrayList<>();
        int ind = 0;
        for (Location location : core.var.battleLocation.adj) {
            if (core.getCurFact()
                    .getEntitiesInLocation(location).size() == 0)
                positionsToMove.add(new ArrayList<>(Arrays.asList(ind)));

            ind++;
        }
        return positionsToMove;
    }
}