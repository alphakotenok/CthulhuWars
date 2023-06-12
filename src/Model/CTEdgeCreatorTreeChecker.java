package Model;

import java.util.ArrayList;

import Model.FactionEnum.FactionType;
import Model.GameVariables.PerformableAction;
import Model.NodeInterfaces.EdgeCreatorCheckerContainer;

class CTEdgeCreatorTreeChecker {

    static EdgeCreatorCheckerContainer opposite(EdgeCreatorCheckerContainer checker) {
        return (core -> !checker.activate(core));
    }

    static EdgeCreatorCheckerContainer combine(EdgeCreatorCheckerContainer checker1,
            EdgeCreatorCheckerContainer checker2) {
        return (core -> (checker1.activate(core) && checker2.activate(core)));
    }

    static EdgeCreatorCheckerContainer isFirstAction(int cost) {
        return ((core) -> (core.var.action == PerformableAction.None && core.getCurFact().energy >= cost));
    }

    static boolean always(Core core) {
        return true;
    }

    static boolean isLastPlayerDoing(Core core) {
        return core.var.playerCounter == core.var.numOfPlayers - 1;
    }

    static boolean isfirstPlayerDetermined(Core core) {
        return core.var.firstPlayerCandidates.size() == 1;
    }

    static boolean canPerformRitual(Core core) {
        return core.ritual.canPerformRitual(core.var.factionsList.get(core.var.turn));
    }

    static boolean canMoveAgain(Core core) {
        return (core.var.action == PerformableAction.None || core.var.action == PerformableAction.Move)
                && core.getCurFact().energy > 0;
    }

    static boolean isActionPerformed(Core core) {
        return core.var.action != PerformableAction.None;
    }

    static boolean canSummon(Core core) {
        return core.getCurFact().enableToSpawn().size() > 0;
    }

    static boolean canGateBeBuilt(Core core) {
        return core.gates.getPlacesToBuild().size() > 0;
    }

    static boolean canCapture(Core core) {
        return core.getCurFact().getEntitiesToBeCaptured().size() > 0;
    }

    static boolean canBlackGoatKill(Core core) {
        return core.getCurFact().faction == FactionType.BlackGoat && !core.getCurFact().isQuestCompletedEarlier(3);
    }

    static boolean canLoseEnergy4(Core core) {
        return core.getCurFact().faction == FactionType.CrawlingChaos && !core.getCurFact().isQuestCompletedEarlier(0);
    }

    static boolean canLoseEnergy6(Core core) {
        return core.getCurFact().faction == FactionType.CrawlingChaos && !core.getCurFact().isQuestCompletedEarlier(1);
    }

    static boolean canLoseEnergy10(Core core) {
        return core.getCurFact().faction == FactionType.CrawlingChaos && !core.getCurFact().isQuestCompletedEarlier(0)
                && !core.getCurFact().isQuestCompletedEarlier(1);
    }

    static boolean canLose3EnergyAndGet1EnergyForOthers(Core core) {
        return core.getCurFact().faction == FactionType.Sleeper && !core.getCurFact().isQuestCompletedEarlier(1);
    }

    static boolean canSpend3EnergySleeper(Core core) {
        return core.getCurFact().faction == FactionType.Sleeper && !core.getCurFact().isQuestCompletedEarlier(0);
    }

    static boolean canLose3EnergyAnd1EnergyForOthers(Core core) {
        return core.getCurFact().faction == FactionType.Sleeper && !core.getCurFact().isQuestCompletedEarlier(2);
    }

    static boolean canAttack(Core core) {
        boolean allBooksOpened = true;
        for (int i = 0; i < 6; i++) {
            if (!core.getCurFact().isBookOpened(i))
                allBooksOpened = false;
        }
        ArrayList<ArrayList<Integer>> loc = CTDataGeneratorTreeFunctions.generateLocationsToAttack(core);
        return (core.var.action == PerformableAction.None || allBooksOpened) && core.getCurFact().energy >= 1 &&
                loc.size() > 0;
    }

    static boolean canKillEntityFirstPlayer(Core core) {
        return core.getCurFact().getEntitiesInLocation(core.var.battleLocation).size() > 0
                && core.var.firstPlayerKill > 0;
    }

    static boolean canInjureEntityFirstPlayer(Core core) {
        return core.getCurFact().getEntitiesInLocation(core.var.battleLocation).size() > 0
                && core.var.firstPlayerInjure > 0;
    }

    static boolean canInjureEntitySecondPlayer(Core core) {
        return core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction))
                .getEntitiesInLocation(core.var.battleLocation).size() > 0
                && core.var.secondPlayerInjure > 0;
    }

    static boolean canKillEntitySecondPlayer(Core core) {
        return core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction))
                .getEntitiesInLocation(core.var.battleLocation).size() > 0
                && core.var.secondPlayerKill > 0;
    }

    static boolean canInjurePositionFirstPlayer(Core core) {
        return CTDataGeneratorTreeFunctions.generatePositionsFirstPlayer(core).size() > 0;
    }

    static boolean canInjurePositionSecondPlayer(Core core) {
        return CTDataGeneratorTreeFunctions.generatePositionsSecondPlayer(core).size() > 0;
    }
    static boolean isGameFinish(Core core) {
        for (Integer doom : core.getDoomList()) {
            if (doom.compareTo(30) >= 0)
                return true;
        }
        return core.var.endOfTheGame;
    }
}