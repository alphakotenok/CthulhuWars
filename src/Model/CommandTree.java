package Model;

import java.util.ArrayList;

import Model.NodeInterfaces.AccumulatorFunctionContainer;
import Model.NodeInterfaces.DataGeneratorFunctionContainer;
import Model.NodeInterfaces.EdgeCreatorCheckerContainer;
import Model.NodeInterfaces.EdgeNameFunctionContainer;
import Model.NodeInterfaces.EgdeFunctionContainer;
import Model.NodeInterfaces.NodeNameFunctionContainer;

class CommandTree {

    static class InvalidNodeNumberException extends Exception {

    }

    Node curNode;
    Node phantommedNode;
    Core core;
    ActionNodes actionNodes = new ActionNodes(this);
    GameFlowDecisionNodes gameFlowDecisionNodes = new GameFlowDecisionNodes(this);
    BattleNodes battleNodes = new BattleNodes(this);

    CommandTree(Core core) {
        this.core = core;
        curNode = gameFlowDecisionNodes.startNode;
        gameFlowDecisionNodes.openBookNode.bookNode = true;
        CTEdgeCreatorPreparator.prepareEdgeCreators(this);
        curNode.prepareNode();
    }

    class Node {
        String name;
        ArrayList<Edge> edges;
        ArrayList<CTEdgeCreator> edgeCreators;
        NodeNameFunctionContainer nameFunction;
        boolean bookNode = false;
        ArrayList<Edge> constEdges;

        Node(NodeNameFunctionContainer nameFunction) {
            this.nameFunction = nameFunction;
            name = "";
            edgeCreators = new ArrayList<>();
        }

        void prepareNode() {
            if (this != gameFlowDecisionNodes.startNode) {
                if (!bookNode) {
                    for (int i = 0; i < core.var.numOfPlayers; ++i) {
                        Faction fact = core.factionBase.getFactionFromEnum(core.var.factionsList.get(i));
                        for (int j = 0; j < 6; ++j) {
                            if (!fact.isQuestCompletedEarlier(j) && fact.isQuestCompleted(j)) {
                                core.var.bookReceiver = fact.faction;
                                core.var.bookSlot = j;
                                phantommedNode = this;
                                gameFlowDecisionNodes.openBookNode.prepareNode();
                                return;
                            }
                        }
                    }
                } else {
                    for (CTEdgeCreator ec : edgeCreators) {
                        ec.to = phantommedNode;
                    }
                }
            }
            name = nameFunction.activate(core);
            edges = new ArrayList<>();
            for (CTEdgeCreator ec : edgeCreators) {
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
            edgeCreators.add(new CTEdgeCreator(to, dataGeneratorFunction, edgeNameFunction,
                    accumulatorFunction, egdeFunction, edgeCreatorChecker));
        }

        void addMover(Node to, String name, EdgeCreatorCheckerContainer edgeCreatorChecker) {
            edgeCreators
                    .add(new CTEdgeCreator(to, CTDataGeneratorTreeFunctions::justOne, CTEdgeNameTreeFunctions.constName(name),
                            AccumulatorTreeFunctions::none, CTEdgeTreeFunctions::none, edgeCreatorChecker));
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

    class CTEdgeCreator {
        Node to;
        DataGeneratorFunctionContainer dataGeneratorFunction;
        EdgeNameFunctionContainer edgeNameFunction;
        AccumulatorFunctionContainer accumulatorFunction;
        EgdeFunctionContainer edgeFunction;
        EdgeCreatorCheckerContainer edgeCreatorChecker;

        CTEdgeCreator(Node to, DataGeneratorFunctionContainer dataGeneratorFunction,
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