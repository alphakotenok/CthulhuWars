package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Model.FactionEnum.FactionType;
import Model.GameVariables.PerformableAction;

class CommandTree {

    @FunctionalInterface
    static interface EgdeFunctionContainer {
        void activate(Core core);
    }

    @FunctionalInterface
    static interface NodeNameFunctionContainer {
        String activate(Core core);
    }

    @FunctionalInterface
    static interface EdgeNameFunctionContainer {
        String activate(ArrayList<Integer> data, Core core);
    }

    @FunctionalInterface
    static interface DataGeneratorFunctionContainer {
        ArrayList<ArrayList<Integer>> activate(Core core);
    }

    @FunctionalInterface
    static interface AccumulatorFunctionContainer {
        void activate(ArrayList<Integer> data, Core core);
    }

    @FunctionalInterface
    static interface EdgeCreatorCheckerContainer {
        boolean activate(Core core);
    }

    static class InvalidNodeNumberException extends Exception {

    }

    Node curNode;
    Node phantommedNode;
    Core core;
    Node openBookNode = new Node(NodeNameTreeFunctions::receiveSpellbookText);

    Node startNode = new Node(NodeNameTreeFunctions.constName("Choose players order"));
    Node startLocChooseNode = new Node(NodeNameTreeFunctions::startLocChoose);
    Node actionChooseNode = new Node(NodeNameTreeFunctions::actionChoose);
    Node firstPlayerSelectionNode = new Node(NodeNameTreeFunctions::firstPlayerSelectionText);
    Node ritualNode = new Node(NodeNameTreeFunctions::ritualText);
    Node viewElderSignsNode = new Node(NodeNameTreeFunctions::viewElderSignsText);
    Node chooseEntityToMoveNode = new Node(NodeNameTreeFunctions.constName("Choose Unit to move for 1 power"));
    Node chooseLocationToMoveNode = new Node(NodeNameTreeFunctions.constName("Choose destination"));
    Node chooseLocationToBuildGate = new Node(
            NodeNameTreeFunctions.constName("Choose where to build a Gate for 3 power"));
    Node chooseEntityToSpawnNode = new Node(NodeNameTreeFunctions.constName("Choose Unit to spawn"));
    Node chooseLocationToSpawnNode = new Node(NodeNameTreeFunctions.constName("Choose where to spawn"));
    Node captureNode = new Node(NodeNameTreeFunctions.constName("Choose Unit to capture"));
    Node blackGoatKillTwoCultistsNode = new Node(NodeNameTreeFunctions.constName("Choose two cultists to kill"));
    Node sleeperPresentEnergyNode = new Node(NodeNameTreeFunctions.constName("Choose faction, who gets 3 power"));
    Node attackLocationNode = new Node(NodeNameTreeFunctions.constName("Choose location you want to attack"));
    Node attackFactionNode = new Node(NodeNameTreeFunctions.constName("Choose faction you want to attack"));
    Node firstAttackPreNode = new Node(NodeNameTreeFunctions::firstPlayerAttackText);
    Node secondAttackPreNode = new Node(NodeNameTreeFunctions::secondPlayerAttackText);
    Node firstThrowsDiceNode = new Node(NodeNameTreeFunctions::firstPlayerThrowsDiceText);
    Node secondThrowsDiceNode = new Node(NodeNameTreeFunctions::secondPlayerThrowsDiceText);
    Node resultThrowingDiceNode = new Node(NodeNameTreeFunctions::resultThrowsText);
    Node firstPlayerKillEntityNode = new Node(NodeNameTreeFunctions::firstPlayerKillEntityText);
    Node secondPlayerKillEntityNode = new Node(NodeNameTreeFunctions::secondPlayerKillEntityText);
    Node firstPlayerInjureEntityNode = new Node(NodeNameTreeFunctions::firstPlayerInjureEntityText);
    Node secondPlayerInjureEntityNode = new Node(NodeNameTreeFunctions::secondPlayerInjureEntityText);
    Node firstPlayerInjurePositionNode = new Node(NodeNameTreeFunctions.constName("Choose position to move"));
    Node secondPlayerInjurePositionNode = new Node(NodeNameTreeFunctions.constName("Choose position to move"));
    Node firstAttackPostBattleNode = new Node(NodeNameTreeFunctions::firstPlayerAttackPostText);
    Node secondAttackPostBattleNode = new Node(NodeNameTreeFunctions::secondPlayerAttackPostText);

    void prepareEdgeCreators() {
        openBookNode.addEdgeCreator(null, DataGeneratorTreeFunctions::booksToOpen,
                EdgeNameTreeFunctions::bookToOpenName, AccumulatorTreeFunctions::accumulateBookOpening,
                EdgeTreeFunctions::openBook, EdgeCreatorTreeChecker::always);
        startNode.addEdgeCreator(startLocChooseNode, DataGeneratorTreeFunctions::permutationGenerator,
                EdgeNameTreeFunctions::permutationName, AccumulatorTreeFunctions::accumulatePerm,
                EdgeTreeFunctions::permutateFactions,
                EdgeCreatorTreeChecker::always);
        startLocChooseNode.addEdgeCreator(startLocChooseNode, DataGeneratorTreeFunctions::startLocGenerator,
                EdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateLocation,
                EdgeTreeFunctions::startLocPlacement,
                EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::isLastPlayerDoing));
        startLocChooseNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::startLocGenerator,
                EdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateLocation,
                EdgeTreeFunctions::startLocLastPlacement,
                EdgeCreatorTreeChecker::isLastPlayerDoing);
        actionChooseNode.addMover(chooseEntityToMoveNode, "Move", EdgeCreatorTreeChecker.isFirstAction(1));
        actionChooseNode.addMover(chooseLocationToBuildGate, "Build Gate", EdgeCreatorTreeChecker
                .combine(EdgeCreatorTreeChecker::canGateBeBuilt, EdgeCreatorTreeChecker.isFirstAction(3)));
        actionChooseNode.addMover(chooseEntityToSpawnNode, "Spawn", EdgeCreatorTreeChecker
                .combine(EdgeCreatorTreeChecker::canSummon, EdgeCreatorTreeChecker.isFirstAction(0)));
        actionChooseNode.addMover(captureNode, "Capture", EdgeCreatorTreeChecker
                .combine(EdgeCreatorTreeChecker::canCapture, EdgeCreatorTreeChecker.isFirstAction(1)));
        actionChooseNode.addMover(blackGoatKillTwoCultistsNode, "Kill two cultists to get the spellbook",
                EdgeCreatorTreeChecker.combine(EdgeCreatorTreeChecker.isFirstAction(0),
                        EdgeCreatorTreeChecker::canBlackGoatKill));

        actionChooseNode.addEdgeCreator(attackLocationNode, DataGeneratorTreeFunctions::justOne, EdgeNameTreeFunctions.constName("Attack"),
        AccumulatorTreeFunctions::none, EdgeTreeFunctions::attack, EdgeCreatorTreeChecker::canAttack);

        actionChooseNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Spend 4 power to get the spellbook"), AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::loseEnergy4, EdgeCreatorTreeChecker.combine(EdgeCreatorTreeChecker.isFirstAction(4),
                        EdgeCreatorTreeChecker::canLoseEnergy4));
        actionChooseNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Spend 6 power to get the spellbook"), AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::loseEnergy6, EdgeCreatorTreeChecker.combine(EdgeCreatorTreeChecker.isFirstAction(6),
                        EdgeCreatorTreeChecker::canLoseEnergy6));
        actionChooseNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Spend 10 power to get two spellbooks"), AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::loseEnergy10,
                EdgeCreatorTreeChecker.combine(EdgeCreatorTreeChecker.isFirstAction(10),
                        EdgeCreatorTreeChecker::canLoseEnergy10));
        actionChooseNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Spend 3 power to get the spellbook(other players get 1 power)"),
                AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::lose3EnergyAndGet1EnergyForOthers,
                EdgeCreatorTreeChecker.combine(EdgeCreatorTreeChecker.isFirstAction(3),
                        EdgeCreatorTreeChecker::canLose3EnergyAndGet1EnergyForOthers));
        actionChooseNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Spend 3 power to get the spellbook(other players spend 1 power)"),
                AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::lose3EnergyAnd1EnergyForOthers,
                EdgeCreatorTreeChecker.combine(EdgeCreatorTreeChecker.isFirstAction(3),
                        EdgeCreatorTreeChecker::canLose3EnergyAnd1EnergyForOthers));
        actionChooseNode.addMover(sleeperPresentEnergyNode,
                "Spend 3 power to get the spellbook(one player gets 3 power)", EdgeCreatorTreeChecker
                        .combine(EdgeCreatorTreeChecker::canSpend3EnergySleeper,
                                EdgeCreatorTreeChecker.isFirstAction(3)));
        actionChooseNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Pass and lose remaining power"), AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::passTurn,
                EdgeCreatorTreeChecker.combine(
                        EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::isLastPlayerDoing),
                        EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::isActionPerformed)));
        actionChooseNode.addEdgeCreator(firstPlayerSelectionNode, DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Pass and lose remaining power"), AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::lastPassTurn,
                EdgeCreatorTreeChecker.combine(EdgeCreatorTreeChecker::isLastPlayerDoing,
                        EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::isActionPerformed)));
        actionChooseNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Done"), AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::doneTurn, EdgeCreatorTreeChecker::isActionPerformed);
        firstPlayerSelectionNode.addEdgeCreator(firstPlayerSelectionNode,
                DataGeneratorTreeFunctions::firstPlayerCandidates, EdgeNameTreeFunctions::factionName,
                AccumulatorTreeFunctions::accumulateFirstPlayer, EdgeTreeFunctions::none,
                EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::isfirstPlayerDetermined));
        firstPlayerSelectionNode.addEdgeCreator(ritualNode,
                DataGeneratorTreeFunctions::wayGenerator, EdgeNameTreeFunctions::wayName,
                AccumulatorTreeFunctions::accumulateWay, EdgeTreeFunctions::none,
                EdgeCreatorTreeChecker::isfirstPlayerDetermined);
        ritualNode.addEdgeCreator(ritualNode,
                DataGeneratorTreeFunctions::justOne, EdgeNameTreeFunctions.constName("Perform ritual"),
                AccumulatorTreeFunctions::none, EdgeTreeFunctions::performRitual,
                EdgeCreatorTreeChecker::canPerformRitual);
        ritualNode.addEdgeCreator(viewElderSignsNode,
                DataGeneratorTreeFunctions::justOne, EdgeNameTreeFunctions.constName("View Elder Signs"),
                AccumulatorTreeFunctions::none, EdgeTreeFunctions::none,
                EdgeCreatorTreeChecker::always);
        ritualNode.addEdgeCreator(actionChooseNode,
                DataGeneratorTreeFunctions::justOne, EdgeNameTreeFunctions.constName("Done"),
                AccumulatorTreeFunctions::none, EdgeTreeFunctions::lastDoneRitual,
                EdgeCreatorTreeChecker::isLastPlayerDoing);
        ritualNode.addEdgeCreator(ritualNode,
                DataGeneratorTreeFunctions::justOne, EdgeNameTreeFunctions.constName("Done"),
                AccumulatorTreeFunctions::none, EdgeTreeFunctions::doneRitual,
                EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::isLastPlayerDoing));
        viewElderSignsNode.addEdgeCreator(viewElderSignsNode,
                DataGeneratorTreeFunctions::elderSigns, EdgeNameTreeFunctions::elderSignRevealName,
                AccumulatorTreeFunctions::accumulateSignReveal, EdgeTreeFunctions::revealElderSign,
                EdgeCreatorTreeChecker::always);
        viewElderSignsNode.addMover(ritualNode, "Back", EdgeCreatorTreeChecker::always);
        chooseEntityToMoveNode.addMover(actionChooseNode, "Cancel", EdgeCreatorTreeChecker.isFirstAction(1));
        chooseEntityToMoveNode.addMover(actionChooseNode, "Done",
                EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker.isFirstAction(1)));
        chooseEntityToMoveNode.addEdgeCreator(chooseLocationToMoveNode,
                DataGeneratorTreeFunctions::enableToMoveGenerator, EdgeNameTreeFunctions::fullEntityName,
                AccumulatorTreeFunctions::accumulateEntityToMove, EdgeTreeFunctions::none,
                EdgeCreatorTreeChecker::canMoveAgain);
        chooseLocationToMoveNode.addMover(chooseEntityToMoveNode, "Back", EdgeCreatorTreeChecker::always);
        chooseLocationToMoveNode.addEdgeCreator(chooseEntityToMoveNode, DataGeneratorTreeFunctions::adjLocations,
                EdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateDestination,
                EdgeTreeFunctions::performMovement, EdgeCreatorTreeChecker::always);
        chooseLocationToBuildGate.addMover(actionChooseNode, "Cancel", EdgeCreatorTreeChecker::always);
        chooseLocationToBuildGate.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::gatePossibleLocations,
                EdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateLocation,
                EdgeTreeFunctions::buildGate, EdgeCreatorTreeChecker::always);
        chooseEntityToSpawnNode.addMover(actionChooseNode, "Cancel", EdgeCreatorTreeChecker::always);
        chooseEntityToSpawnNode.addEdgeCreator(chooseLocationToSpawnNode, DataGeneratorTreeFunctions::spawnEntities,
                EdgeNameTreeFunctions::spawnEntityName, AccumulatorTreeFunctions::accumulateEntityToSpawn,
                EdgeTreeFunctions::none, EdgeCreatorTreeChecker::always);
        chooseLocationToSpawnNode.addMover(chooseEntityToSpawnNode, "Back", EdgeCreatorTreeChecker::always);
        chooseLocationToSpawnNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::locationsToSpawn,
                EdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateLocation,
                EdgeTreeFunctions::spawnEntity, EdgeCreatorTreeChecker::always);
        captureNode.addMover(actionChooseNode, "Cancel", EdgeCreatorTreeChecker::always);
        captureNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::entitiesToCapture,
                EdgeNameTreeFunctions::mulitFactionalFullEntityName, AccumulatorTreeFunctions::accumulateCapture,
                EdgeTreeFunctions::captureEntity, EdgeCreatorTreeChecker::always);

        attackLocationNode.addEdgeCreator(attackFactionNode, DataGeneratorTreeFunctions::generateLocationsToAttack,
                EdgeNameTreeFunctions::possibleLocationName, AccumulatorTreeFunctions::accumulateBattleLocation,
                EdgeTreeFunctions::none, EdgeCreatorTreeChecker::always);

        attackFactionNode.addEdgeCreator(firstAttackPreNode, DataGeneratorTreeFunctions::generateFactionsToAttack,
                EdgeNameTreeFunctions::factionName,
                AccumulatorTreeFunctions::accumulateFaction, EdgeTreeFunctions::none, EdgeCreatorTreeChecker::always);
        firstAttackPreNode.addMover(secondAttackPreNode, "Done", EdgeCreatorTreeChecker::always);
        secondAttackPreNode.addMover(firstThrowsDiceNode, "Done", EdgeCreatorTreeChecker::always);

        firstThrowsDiceNode.addMover(secondThrowsDiceNode, "Throw", EdgeCreatorTreeChecker::always);
        secondThrowsDiceNode.addMover(resultThrowingDiceNode, "Throw", EdgeCreatorTreeChecker::always);
        resultThrowingDiceNode.addMover(firstPlayerKillEntityNode, "Next", EdgeCreatorTreeChecker::always);

        firstPlayerKillEntityNode.addEdgeCreator(firstPlayerKillEntityNode,
                DataGeneratorTreeFunctions::generateEntitiesFirstPlayer,
                EdgeNameTreeFunctions::entityToKillFirstPlayerName, AccumulatorTreeFunctions::accumulateEntityToKill,
                EdgeTreeFunctions::entityToKillFirstPlayer, EdgeCreatorTreeChecker::canKillEntityFirstPlayer);
        firstPlayerKillEntityNode.addMover(secondPlayerKillEntityNode, "Done",
                EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::canKillEntityFirstPlayer));

        secondPlayerKillEntityNode.addEdgeCreator(secondPlayerKillEntityNode,
                DataGeneratorTreeFunctions::generateEntitiesSecondPlayer,
                EdgeNameTreeFunctions::entityToKillSecondPlayerName, AccumulatorTreeFunctions::accumulateEntityToKill,
                EdgeTreeFunctions::entityToKillSecondPlayer, EdgeCreatorTreeChecker::canKillEntitySecondPlayer);
        secondPlayerKillEntityNode.addMover(firstPlayerInjureEntityNode, "Done",
                EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::canKillEntitySecondPlayer));

        firstPlayerInjureEntityNode.addEdgeCreator(firstPlayerInjurePositionNode,
                DataGeneratorTreeFunctions::generateEntitiesFirstPlayer,
                EdgeNameTreeFunctions::entityToKillFirstPlayerName, AccumulatorTreeFunctions::accumulateEntityToKill,
                EdgeTreeFunctions::none, EdgeCreatorTreeChecker::canInjureEntityFirstPlayer);
        firstPlayerInjureEntityNode.addMover(secondPlayerInjureEntityNode, "Done",
                EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::canInjureEntityFirstPlayer));

        firstPlayerInjurePositionNode.addEdgeCreator(firstPlayerInjureEntityNode,
                DataGeneratorTreeFunctions::generatePositionsFirstPlayer,
                EdgeNameTreeFunctions::PositionToInjureFirstPlayerName,
                AccumulatorTreeFunctions::accumulateDestinationPlayer,
                EdgeTreeFunctions::performMoveInjureFirstPlayer, EdgeCreatorTreeChecker::canInjurePositionFirstPlayer);

     
        firstPlayerInjurePositionNode.addEdgeCreator(firstPlayerInjureEntityNode,
                DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Kill"),
                AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::KillInjureFirstPlayer, EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::canInjurePositionFirstPlayer));
    
        firstPlayerInjurePositionNode.addMover(firstPlayerInjureEntityNode, "Back",
                EdgeCreatorTreeChecker::always);





        secondPlayerInjureEntityNode.addEdgeCreator(secondPlayerInjurePositionNode,
                DataGeneratorTreeFunctions::generateEntitiesSecondPlayer,
                EdgeNameTreeFunctions::entityToKillSecondPlayerName, AccumulatorTreeFunctions::accumulateEntityToKill,
                EdgeTreeFunctions::none, EdgeCreatorTreeChecker::canInjureEntitySecondPlayer);
        secondPlayerInjureEntityNode.addMover(firstAttackPostBattleNode, "Done",
                EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::canInjureEntitySecondPlayer));

        secondPlayerInjurePositionNode.addEdgeCreator(secondPlayerInjureEntityNode,
                DataGeneratorTreeFunctions::generatePositionsSecondPlayer,
                EdgeNameTreeFunctions::PositionToInjureSecondPlayerName,
                AccumulatorTreeFunctions::accumulateDestinationPlayer,
                EdgeTreeFunctions::performMoveInjureSecondPlayer, EdgeCreatorTreeChecker::canInjurePositionSecondPlayer);

     
        secondPlayerInjurePositionNode.addEdgeCreator(secondPlayerInjureEntityNode,
                DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Kill"),
                AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::KillInjureSecondPlayer, EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::canInjurePositionSecondPlayer));
    
        secondPlayerInjurePositionNode.addMover(secondPlayerInjureEntityNode, "Back",
                EdgeCreatorTreeChecker::always);


                

        firstAttackPostBattleNode.addMover(secondAttackPostBattleNode, "Done",
                EdgeCreatorTreeChecker::always);

        secondAttackPostBattleNode.addMover(actionChooseNode, "Done",
                EdgeCreatorTreeChecker::always);

        attackLocationNode.addMover(actionChooseNode, "Cancel", EdgeCreatorTreeChecker::always);

        attackFactionNode.addMover(attackLocationNode, "Cancel", EdgeCreatorTreeChecker::always);

        blackGoatKillTwoCultistsNode.addMover(actionChooseNode, "Cancel", EdgeCreatorTreeChecker::always);
        blackGoatKillTwoCultistsNode.addEdgeCreator(actionChooseNode,
                DataGeneratorTreeFunctions::generatePairOfCultistToKill,
                EdgeNameTreeFunctions::killCultistsName, AccumulatorTreeFunctions::accumulateKillingCultistsBlackGoat,
                EdgeTreeFunctions::killTwoCultistsBlackGoat, EdgeCreatorTreeChecker::always);
        sleeperPresentEnergyNode.addMover(actionChooseNode, "Cancel", EdgeCreatorTreeChecker::always);
        sleeperPresentEnergyNode.addEdgeCreator(actionChooseNode,
                DataGeneratorTreeFunctions::generateFaction,
                EdgeNameTreeFunctions::factionName,
                AccumulatorTreeFunctions::accumulateFaction,
                EdgeTreeFunctions::sleeperSpendAndGetEnergy, EdgeCreatorTreeChecker::always);
    }

    CommandTree(Core core) {
        this.core = core;
        curNode = startNode;
        openBookNode.bookNode = true;
        prepareEdgeCreators();
        curNode.prepareNode();
    }

    class Node {
        String name;
        ArrayList<Edge> edges;
        ArrayList<EdgeCreator> edgeCreators;
        NodeNameFunctionContainer nameFunction;
        boolean bookNode = false;
        ArrayList<Edge> constEdges;

        Node(NodeNameFunctionContainer nameFunction) {
            this.nameFunction = nameFunction;
            name = "";
            edgeCreators = new ArrayList<>();
        }

        void prepareNode() {
            if (this != startNode) {
                if (!bookNode) {
                    for (int i = 0; i < core.var.numOfPlayers; ++i) {
                        Faction fact = core.factionBase.getFactionFromEnum(core.var.factionsList.get(i));
                        for (int j = 0; j < 6; ++j) {
                            if (!fact.isQuestCompletedEarlier(j) && fact.isQuestCompleted(j)) {
                                core.var.bookReceiver = fact.faction;
                                core.var.bookSlot = j;
                                phantommedNode = this;
                                openBookNode.prepareNode();
                                return;
                            }
                        }
                    }
                } else {
                    for (EdgeCreator ec : edgeCreators) {
                        ec.to = phantommedNode;
                    }
                }
            }
            name = nameFunction.activate(core);
            edges = new ArrayList<>();
            for (EdgeCreator ec : edgeCreators) {
                edges.addAll(ec.createEdges());
            }
            curNode = this;
        }

        void moveByEdge(int edgeNum) {
            edges.get(edgeNum).run();
        }

        void addEdgeCreator(Node to, DataGeneratorFunctionContainer dataGeneratorFunction,
                EdgeNameFunctionContainer edgeNameFunction,
                AccumulatorFunctionContainer accumulatorFunction,
                EgdeFunctionContainer egdeFunction, EdgeCreatorCheckerContainer edgeCreatorChecker) {
            edgeCreators.add(new EdgeCreator(to, dataGeneratorFunction, edgeNameFunction,
                    accumulatorFunction, egdeFunction, edgeCreatorChecker));
        }

        void addMover(Node to, String name, EdgeCreatorCheckerContainer edgeCreatorChecker) {
            edgeCreators
                    .add(new EdgeCreator(to, DataGeneratorTreeFunctions::justOne, EdgeNameTreeFunctions.constName(name),
                            AccumulatorTreeFunctions::none, EdgeTreeFunctions::none, edgeCreatorChecker));
        }

    }

    class Edge {
        String name;
        AccumulatorFunctionContainer accumulatorFunction;
        EgdeFunctionContainer edgeFunction;
        Node to;
        ArrayList<Integer> data;

        Edge(String name, AccumulatorFunctionContainer accumulatorFunction, EgdeFunctionContainer edgeFunction, Node to,
                ArrayList<Integer> data) {
            this.name = name;
            this.accumulatorFunction = accumulatorFunction;
            this.edgeFunction = edgeFunction;
            this.to = to;
            this.data = data;
        }

        void run() {
            accumulatorFunction.activate(data, core);
            edgeFunction.activate(core);
            to.prepareNode();
        }
    }

    class EdgeCreator {
        Node to;
        DataGeneratorFunctionContainer dataGeneratorFunction;
        EdgeNameFunctionContainer edgeNameFunction;
        AccumulatorFunctionContainer accumulatorFunction;
        EgdeFunctionContainer edgeFunction;
        EdgeCreatorCheckerContainer edgeCreatorChecker;

        EdgeCreator(Node to, DataGeneratorFunctionContainer dataGeneratorFunction,
                EdgeNameFunctionContainer edgeNameFunction, AccumulatorFunctionContainer accumulatorFunction,
                EgdeFunctionContainer edgeFunction, EdgeCreatorCheckerContainer edgeCreatorChecker) {
            this.to = to;
            this.dataGeneratorFunction = dataGeneratorFunction;
            this.edgeNameFunction = edgeNameFunction;
            this.accumulatorFunction = accumulatorFunction;
            this.edgeFunction = edgeFunction;
            this.edgeCreatorChecker = edgeCreatorChecker;
        }

        ArrayList<Edge> createEdges() {
            if (!edgeCreatorChecker.activate(core))
                return new ArrayList<>();
            ArrayList<ArrayList<Integer>> dataSet = dataGeneratorFunction.activate(core);
            ArrayList<Edge> ans = new ArrayList<>();
            for (ArrayList<Integer> data : dataSet) {
                String edgeName = edgeNameFunction.activate(data, core);
                ans.add(new Edge(edgeName, accumulatorFunction, edgeFunction, to, data));
            }
            return ans;
        }
    }

    ArrayList<String> getCommandList() {
        ArrayList<String> ans = new ArrayList<>();
        for (Edge e : curNode.edges) {
            ans.add(e.name);
        }
        return ans;
    }

    void execute(int num) throws InvalidNodeNumberException {
        if (num < 0 || num >= curNode.edges.size()) {
            throw new InvalidNodeNumberException();
        }
        curNode.moveByEdge(num);
    }
}

class NodeNameTreeFunctions {
    static CommandTree.NodeNameFunctionContainer constName(String name) {
        return (core -> name);
    }

    static String startLocChoose(Core core) {
        return "Choose " + core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(core.var.turn))
                + " start location";
    }

    static String actionChoose(Core core) {
        return core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(core.var.turn)) + " action";
    }

    static String ritualText(Core core) {
        return core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(core.var.turn))
                + " can perform ritual";
    }

    static String receiveSpellbookText(Core core) {
        return core.factionBase.getFactionNameFromEnum(core.var.bookReceiver) + " receive spellbook";
    }

    static String firstPlayerSelectionText(Core core) {
        if (EdgeCreatorTreeChecker.isfirstPlayerDetermined(core)) {
            return core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(core.var.firstPlayer))
                    + " choose the order of moves in the next round";
        }
        return core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(core.var.firstPlayer))
                + " choose the first player in the next round";
    }

    static String viewElderSignsText(Core core) {
        return core.getCurFact().name + " Elder Signs";
    }

    static String firstPlayerAttackText(Core core) {
        return core.getCurFact().name + " does special pre attack actions";
    }

    static String secondPlayerAttackText(Core core) {
        return core.var.factionsList.get(core.var.chosenFaction).name() + " does special pre attack actions";
    }

    static String firstPlayerAttackPostText(Core core) {
        return core.getCurFact().name + " does special post attack actions";
    }

    static String secondPlayerAttackPostText(Core core) {
        return core.var.factionsList.get(core.var.chosenFaction).name() + " does special post attack actions";
    }

    static String firstPlayerThrowsDiceText(Core core) {
        String s = core.getCurFact().name + " throws " + core.getCurFact().totalCombat() + " dice";
        if (core.getCurFact().totalCombat() > 1)
            s += "s";
        return s;
    }

    static String secondPlayerThrowsDiceText(Core core) {
        String s = core.var.factionsList.get(core.var.chosenFaction).name() + " throws " +
                core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction)).totalCombat()
                + " dice";
        if (core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction)).totalCombat() > 1)
            s += "s";
        return s;
    }

    static String resultThrowsText(Core core) {
        int amountFirst = core.getCurFact().totalCombat();
        int amountSecond = core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction))
                .totalCombat();
        core.var.throwDice(1, amountFirst);
        core.var.throwDice(2, amountSecond);
        String s = core.getCurFact().name + " kills " + core.var.firstPlayerKill + ", injures "
                + core.var.firstPlayerInjure + ", misses " + core.var.firstPlayerNothing
                + "; " + core.var.factionsList.get(core.var.chosenFaction).name() + " kills "
                + core.var.secondPlayerKill
                + ", injures " + core.var.secondPlayerInjure + ", misses " + core.var.secondPlayerNothing;
        return s;
    }

    static String firstPlayerKillEntityText(Core core) {
        String s = core.getCurFact().name + " kills his entity";
        return s;
    }

    static String secondPlayerKillEntityText(Core core) {
        String s = core.var.factionsList.get(core.var.chosenFaction).name() + " kills his entity";
        return s;
    }

    static String firstPlayerInjureEntityText(Core core) {
        String s = core.getCurFact().name + " injures his entity";
        return s;
    }

    static String secondPlayerInjureEntityText(Core core) {
        String s = core.var.factionsList.get(core.var.chosenFaction).name() + " injures his entity";
        return s;
    }
}

class EdgeNameTreeFunctions {
    static CommandTree.EdgeNameFunctionContainer constName(String name) {
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

class EdgeTreeFunctions {

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

class DataGeneratorTreeFunctions {
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

class EdgeCreatorTreeChecker {

    static CommandTree.EdgeCreatorCheckerContainer opposite(CommandTree.EdgeCreatorCheckerContainer checker) {
        return (core -> !checker.activate(core));
    }

    static CommandTree.EdgeCreatorCheckerContainer combine(CommandTree.EdgeCreatorCheckerContainer checker1,
            CommandTree.EdgeCreatorCheckerContainer checker2) {
        return (core -> (checker1.activate(core) && checker2.activate(core)));
    }

    static CommandTree.EdgeCreatorCheckerContainer isFirstAction(int cost) {
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
        ArrayList<ArrayList<Integer>> loc = DataGeneratorTreeFunctions.generateLocationsToAttack(core);
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
        return DataGeneratorTreeFunctions.generatePositionsFirstPlayer(core).size() > 0;
    }

    static boolean canInjurePositionSecondPlayer(Core core) {
        return DataGeneratorTreeFunctions.generatePositionsSecondPlayer(core).size() > 0;
    }
}