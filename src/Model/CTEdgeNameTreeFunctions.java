package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.NodeInterfaces.EdgeNameFunctionContainer;

class CTEdgeNameTreeFunctions {
    static EdgeNameFunctionContainer constName(String name) {
        return ((data, core) -> name);
    }

    static String permutationName(ArrayList<Integer> data, Core core) {
        String ans = "";
        for (int i = 0; i < core.var.numOfPlayers; ++i) {
            ans += data.get(i).toString();
        }
        return ans;
    }

    static String locationName(ArrayList<Integer> data, Core core) {
        return core.map.locations.get(data.get(0)).name;
    }

    static String possibleLocationName(ArrayList<Integer> data, Core core) {
        return core.var.locationPossibleToAttack.get(data.get(0)).name;
    }

    static String bookToOpenName(ArrayList<Integer> data, Core core) {
        return core.factionBase.getFactionFromEnum(core.var.bookReceiver).books.get(data.get(0)).name;
    }

    static String factionName(ArrayList<Integer> data, Core core) {
        return core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(data.get(0)));
    }

    static String wayName(ArrayList<Integer> data, Core core) {
        if (data.get(0) == 0)
            return "<<<<<";
        else
            return ">>>>>";
    }

    static String elderSignRevealName(ArrayList<Integer> data, Core core) {
        return core.getCurFact().elderSignList.get(data.get(0)) + ", reveal it";
    }

    static String fullEntityName(ArrayList<Integer> data, Core core) {
        return core.getCurFact().entitySetsList.get(data.get(0)).name + " from "
                + core.map.locations.get(data.get(1)).name;
    }

    static String killCultistsName(ArrayList<Integer> data, Core core) {
        return "From " + core.getCurFact().getEntitySetByName("Cultist").positions.get(data.get(0)).name +
                ", from " + core.getCurFact().getEntitySetByName("Cultist").positions.get(data.get(1)).name;
    }

    static String spawnEntityName(ArrayList<Integer> data, Core core) {
        return core.getCurFact().entitySetsList.get(data.get(0)).name + " for "
                + core.getCurFact().entitySetsList.get(data.get(0)).costFunc.activate(core) + " power";
    }

    static String mulitFactionalFullEntityName(ArrayList<Integer> data, Core core) {
        return core.factionBase.factList.get(data.get(1)).entitySetsList.get(data.get(2)).name + " from "
                + core.map.locations.get(data.get(0)).name;
    }

    static String entityToKillFirstPlayerName(ArrayList<Integer> data, Core core) {
        return core.getCurFact().getEntitiesInLocation(core.var.battleLocation).get(data.get(0)).name;
    }

    static String entityToKillSecondPlayerName(ArrayList<Integer> data, Core core) {
        return core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction))
                .getEntitiesInLocation(core.var.battleLocation).get(data.get(0)).name;
    }

    static String PositionToInjureFirstPlayerName(ArrayList<Integer> data, Core core) {
        return core.var.battleLocation.adj.get(data.get(0)).name;
    }

    static String PositionToInjureSecondPlayerName(ArrayList<Integer> data, Core core) {
        return core.var.battleLocation.adj.get(data.get(0)).name;
    }
}

class AccumulatorTreeFunctions {
    static void none(ArrayList<Integer> data, Core core) {

    };

    static void accumulatePerm(ArrayList<Integer> data, Core core) {
        core.var.chosenPerm = data;
    }

    static void accumulateBattleLocation(ArrayList<Integer> data, Core core) {
        core.var.battleLocation = core.var.locationPossibleToAttack.get(data.get(0));
    }

    static void accumulateLocation(ArrayList<Integer> data, Core core) {
        core.var.chosenLocation = core.map.locations.get(data.get(0));
    }

    static void accumulateBookOpening(ArrayList<Integer> data, Core core) {
        core.var.bookNum = data.get(0);
    }

    static void accumulateFirstPlayer(ArrayList<Integer> data, Core core) {
        core.var.firstPlayer = data.get(0);
        core.var.firstPlayerCandidates = new ArrayList<>(Arrays.asList(data.get(0)));
    }

    static void accumulateWay(ArrayList<Integer> data, Core core) {
        if (data.get(0) == 0) {
            core.var.correctWay = false;
        } else
            core.var.correctWay = true;
        core.var.turn = core.var.firstPlayer;
    }

    static void accumulateSignReveal(ArrayList<Integer> data, Core core) {
        core.var.signToReveal = data.get(0);
    }

    static void accumulateEntityToMove(ArrayList<Integer> data, Core core) {
        core.var.chosenEntity = core.getCurFact().entitySetsList.get(data.get(0));
        core.var.chosenLocation = core.map.locations.get(data.get(1));
    }

    static void accumulateDestination(ArrayList<Integer> data, Core core) {
        core.var.chosenDestination = core.map.locations.get(data.get(0));
    }

    static void accumulateEntityToSpawn(ArrayList<Integer> data, Core core) {
        core.var.chosenEntity = core.getCurFact().entitySetsList.get(data.get(0));
    }

    static void accumulateCapture(ArrayList<Integer> data, Core core) {
        core.var.chosenEntity = core.factionBase.factList.get(data.get(1)).entitySetsList.get(data.get(2));
        core.var.chosenLocation = core.map.locations.get(data.get(0));
    }

    static void accumulateKillingCultistsBlackGoat(ArrayList<Integer> data, Core core) {
        core.var.firstCultistToKillByBlackGoat = data.get(0);
        core.var.secondCultistToKillByBlackGoat = data.get(1);
    }

    static void accumulateEntityToKill(ArrayList<Integer> data, Core core) {
        core.var.entityToKill = data.get(0);
    }

    static void accumulateFaction(ArrayList<Integer> data, Core core) {
        core.var.chosenFaction = data.get(0);
    }

    static void accumulateDestinationPlayer(ArrayList<Integer> data, Core core) {
        core.var.chosenDestination = core.var.battleLocation.adj.get(data.get(0));
    }
}