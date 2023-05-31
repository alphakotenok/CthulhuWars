package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Model.FactionEnum.FactionType;
import Model.GameVariables.PerformedAction;

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
    Node chooseEntityToMoveNode = new Node(NodeNameTreeFunctions.constName("Choose Unit"));
    Node chooseLocationToMoveNode = new Node(NodeNameTreeFunctions.constName("Choose Destination"));

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
        actionChooseNode.addMover(chooseEntityToMoveNode, "Move", EdgeCreatorTreeChecker::canMove);
        actionChooseNode.addEdgeCreator(actionChooseNode, DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Pass and lose remaining energy"), AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::passTurn,
                EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::isLastPlayerDoing));
        actionChooseNode.addEdgeCreator(firstPlayerSelectionNode, DataGeneratorTreeFunctions::justOne,
                EdgeNameTreeFunctions.constName("Pass and lose remaining energy"), AccumulatorTreeFunctions::none,
                EdgeTreeFunctions::lastPassTurn,
                EdgeCreatorTreeChecker::isLastPlayerDoing);
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
        chooseEntityToMoveNode.addMover(actionChooseNode, "Cancel", EdgeCreatorTreeChecker::isFirstMovement);
        chooseEntityToMoveNode.addMover(actionChooseNode, "Done",
                EdgeCreatorTreeChecker.opposite(EdgeCreatorTreeChecker::isFirstMovement));
        chooseEntityToMoveNode.addEdgeCreator(chooseLocationToMoveNode,
                DataGeneratorTreeFunctions::enableToMoveGenerator, EdgeNameTreeFunctions::fullEntityName,
                AccumulatorTreeFunctions::accumulateEntityToMove, EdgeTreeFunctions::none,
                EdgeCreatorTreeChecker::canMove);
        chooseLocationToMoveNode.addMover(chooseEntityToMoveNode, "Back", EdgeCreatorTreeChecker::always);
        chooseLocationToMoveNode.addEdgeCreator(chooseEntityToMoveNode, DataGeneratorTreeFunctions::adjLocations,
                EdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateDestination,
                EdgeTreeFunctions::performMovement, EdgeCreatorTreeChecker::always);
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

    static String bookToOpenName(ArrayList<Integer> data, Core core) {
        return core.factionBase.getFactionFromEnum(core.var.bookReceiver).bookNames.get(data.get(0));
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
}

class AccumulatorTreeFunctions {
    static void none(ArrayList<Integer> data, Core core) {

    };

    static void accumulatePerm(ArrayList<Integer> data, Core core) {
        core.var.chosenPerm = data;
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
}

class EdgeTreeFunctions {

    static void nextPlayerMovePreparation(Core core) {

        core.var.action = PerformedAction.None;
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
        core.factionBase.getFactionFromEnum(core.var.bookReceiver).openedBooks.set(core.var.bookSlot, core.var.bookNum);
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
    }

    static void revealElderSign(Core core) {
        core.getCurFact().revealSign(core.var.signToReveal);
    }

    static void performMovement(Core core) {
        core.var.chosenEntity.performMovement(core.var.chosenLocation, core.var.chosenDestination);
        core.getCurFact().energy--;
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
}

class EdgeCreatorTreeChecker {

    static CommandTree.EdgeCreatorCheckerContainer opposite(CommandTree.EdgeCreatorCheckerContainer checker) {
        return (core -> !checker.activate(core));
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

    static boolean isFirstMovement(Core core) {
        return core.var.action == PerformedAction.None && core.getCurFact().energy > 0;
    }

    static boolean canMove(Core core) {
        return (core.var.action == PerformedAction.None || core.var.action == PerformedAction.Move)
                && core.getCurFact().energy > 0;
    }
}