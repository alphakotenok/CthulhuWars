package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import Model.Faction.FactionType;

class nextPermutation {

    public static void swap(ArrayList<Integer> data, int left, int right) {
        Integer temp = data.get(left);
        data.set(left, data.get(right));
        data.set(right, temp);
    }

    public static void reverse(ArrayList<Integer> data, int left, int right) {
        while (left < right) {
            Integer temp = data.get(left);
            data.set(left++, data.get(right));
            data.set(right--, temp);
        }
    }

    public static boolean findNextPermutation(ArrayList<Integer> data) {

        if (data.size() <= 1)
            return false;

        int last = data.size() - 2;
        while (last >= 0) {
            if (data.get(last) < data.get(last + 1)) {
                break;
            }
            last--;
        }

        if (last < 0)
            return false;

        int nextGreater = data.size() - 1;

        for (int i = data.size() - 1; i > last; i--) {
            if (data.get(i) > data.get(last)) {
                nextGreater = i;
                break;
            }
        }

        swap(data, nextGreater, last);

        reverse(data, last + 1, data.size() - 1);

        return true;
    }
}

class CommandTree {

    Node curNode;
    Core core;

    static class InvalidNodeNumberException extends Exception {

    }

    @FunctionalInterface
    static interface FunctionContainer {
        void activate(ArrayList<Integer> args, Core core, Node curNode);
    }

    static class Node {
        String desc;
        String name;
        ArrayList<Node> adj;
        FunctionContainer func;
        ArrayList<Integer> data;
        Core core;

        Node(String name, String desc, FunctionContainer func, ArrayList<Integer> data, Core core) {
            this.name = name;
            this.func = func;
            this.data = data;
            this.core = core;
            this.desc = desc;
            adj = new ArrayList<>();
        }

        void activate() {
            func.activate(data, core, this);
        }
    }

    CommandTree(Core core) {
        this.core = core;
        curNode = new Node("Start node", "Choose players order", null, null, core);
        int CthulhuPlace = -1;
        for (int i = 0; i < core.numOfPlayers; ++i) {
            if (core.factionsList.get(i).equals(FactionType.GreatCthulhu)) {
                CthulhuPlace = i;
            }
        }
        ArrayList<Integer> order = new ArrayList<>();
        if (CthulhuPlace != -1)
            order.add(Integer.valueOf(0));
        ArrayList<Integer> order2 = new ArrayList<>();
        for (int i = 0; i < core.numOfPlayers; ++i) {
            if (i != CthulhuPlace)
                order2.add(Integer.valueOf(core.factionsList.get(i).ordinal()));
        }
        Collections.sort(order2);
        ArrayList<Integer> order3 = new ArrayList<>();
        order3.addAll(order);
        order3.addAll(order2);
        String k = "";
        for (int i = 0; i < core.numOfPlayers; ++i) {
            k += order3.get(i).toString();
        }
        Node n = new Node(k,
                "Choose " + core.factionBase.getFactionNameFromEnum(FactionType.values()[order3.get(0)])
                        + " start location",
                CommandTree::chooseFactionPermutation, order3, core);
        curNode.adj.add(n);
        while (nextPermutation.findNextPermutation(order2)) {
            order3 = new ArrayList<>();
            order3.addAll(order);
            order3.addAll(order2);
            k = "";
            for (int i = 0; i < core.numOfPlayers; ++i) {
                k += order3.get(i).toString();
            }
            n = new Node(k,
                    "Choose " + core.factionBase.getFactionNameFromEnum(FactionType.values()[order3.get(0)])
                            + " start location",
                    CommandTree::chooseFactionPermutation, order3, core);
            curNode.adj.add(n);
        }
    }

    ArrayList<String> getCommandList() {
        ArrayList<String> ans = new ArrayList<>();
        for (Node n : curNode.adj) {
            ans.add(n.name);
        }
        return ans;
    }

    void execute(int num) throws InvalidNodeNumberException {
        if (num < 0 || num >= curNode.adj.size()) {
            throw new InvalidNodeNumberException();
        }
        curNode = curNode.adj.get(num);
        curNode.activate();
    }

    static void chooseFactionPermutation(ArrayList<Integer> perm, Core core, Node curNode) {
        core.factionsList.clear();
        for (int i = 0; i < core.numOfPlayers; ++i) {
            core.factionsList.add(FactionType.values()[perm.get(i)]);
        }

        for (int j = 0; j < core.map.startLoc.get(core.factionsList.get(0).ordinal()).size(); ++j) {

            Node n = new Node(core.map.startLoc.get(core.factionsList.get(0).ordinal()).get(j).name,
                    "Choose " + core.factionBase.getFactionNameFromEnum(core.factionsList.get(1)) + " start location",
                    CommandTree::placeStart, new ArrayList<Integer>(Arrays.asList(0, j)), core);
            curNode.adj.add(n);
        }
    }

    static void placeStart(ArrayList<Integer> data, Core core, Node curNode) {
        core.map.setStartUnits(core.factionsList.get(data.get(0)),
                core.map.startLoc.get(core.factionsList.get(data.get(0)).ordinal()).get(data.get(1)));
        int num = data.get(0) + 1;
        if (num >= core.numOfPlayers) {
            prepareActionSet(null, core, curNode);
            return;
        }
        for (int j = 0; j < core.map.startLoc.get(core.factionsList.get(num).ordinal()).size(); ++j) {
            String desc;
            if (num == core.numOfPlayers - 1)
                desc = core.factionBase.getFactionNameFromEnum(core.factionsList.get(core.turn)) + " action";
            else
                desc = "Choose " + core.factionBase.getFactionNameFromEnum(core.factionsList.get(num + 1))
                        + " start location";
            Node n = new Node(core.map.startLoc.get(core.factionsList.get(num).ordinal()).get(j).name, desc,
                    CommandTree::placeStart, new ArrayList<Integer>(Arrays.asList(num, j)), core);
            curNode.adj.add(n);
        }
    }

    static void prepareActionSet(ArrayList<Integer> data, Core core, Node curNode) {
        Faction faction = core.factionBase.getFactionFromEnum(core.factionsList.get(core.turn));
        if (faction.skip) {
            if (core.factionBase.totalSkip == core.numOfPlayers) {
                energyRecount(core);
                chooseFirstPlayer(core);
                core.turn = core.firstPlayer;
                // who + how much already was
                curNode.desc = core.factionBase.getFactionNameFromEnum(core.factionsList.get(core.firstPlayer))
                        + " can perform ritual";
                createRitualAsk(core, curNode, core.firstPlayer, 0);
                return;
            }
            Node n = new Node("Skip",
                    core.factionBase.getFactionNameFromEnum(core.factionsList.get(core.getNextTurn(core.turn)))
                            + " action",
                    CommandTree::prepareActionSet, null, core);
            curNode.adj.add(n);
            core.turn = core.getNextTurn(core.turn);
        } else {

            // TODO: delete this temp code
            if (core.factionBase.getFactionFromEnum(core.factionsList.get(core.turn)).energy > 0) {
                Node n = new Node("Some action to spend one energy",
                        core.factionBase.getFactionNameFromEnum(core.factionsList.get(core.getNextTurn(core.turn)))
                                + " action",
                        CommandTree::tempFunc, data, core);
                curNode.adj.add(n);
            }
            Node n = new Node("Pass and lose remaining power",
                    core.factionBase.getFactionNameFromEnum(core.factionsList.get(core.getNextTurn(core.turn)))
                            + " action",
                    CommandTree::passTurn, data, core);
            curNode.adj.add(n);
        }
    }

    static void tempFunc(ArrayList<Integer> data, Core core, Node curNode) {
        core.factionBase.getFactionFromEnum(core.factionsList.get(core.turn)).energy -= 1;
        core.turn = core.getNextTurn(core.turn);
        prepareActionSet(data, core, curNode);
    }

    static void passTurn(ArrayList<Integer> data, Core core, Node curNode) {
        Faction faction = core.factionBase.getFactionFromEnum(core.factionsList.get(core.turn));
        faction.skip = true;
        core.factionBase.totalSkip++;
        faction.energy = 0;
        core.turn = core.getNextTurn(core.turn);
        prepareActionSet(data, core, curNode);
    }

    static void energyRecount(Core core) {
        for (int i = 0; i < core.numOfPlayers; ++i) {
            Faction faction = core.factionBase.factList.get(core.factionsList.get(i).ordinal());
            System.out.println(faction.name);
            faction.recountEnergy();
            faction.skip = false;
        }
        core.factionBase.totalSkip = 0;

    }

    static void chooseFirstPlayer(Core core) {
        // TODO: first player
    }

    static boolean checkPlayersDoom() {
        // TODO: this func
        return false;
    }

    static void createRitualAsk(Core core, Node curNode, int who, int num) {
        if (num == core.numOfPlayers) {
            if (core.endOfTheGame || checkPlayersDoom()) {
                // TODO: end handler
                curNode.desc = "end";
                return;
            }
            curNode.desc = core.factionBase.getFactionNameFromEnum(core.factionsList.get(core.firstPlayer))
                    + " action";
            prepareActionSet(null, core, curNode);
            return;
        }
        if (core.ritual.canPerformRItual(core.factionsList.get(who))) {
            Node n = new Node("PerformRitual",
                    core.factionBase.getFactionNameFromEnum(core.factionsList.get(core.getNextTurn(who)))
                            + " can perform ritual",
                    CommandTree::performRitual, new ArrayList<>(Arrays.asList(who, num)), core);
            curNode.adj.add(n);
        }

        // TODO: add elder sign check

        Node n = new Node("Done",
                core.factionBase.getFactionNameFromEnum(core.factionsList.get(core.getNextTurn(who)))
                        + " can perform ritual",
                CommandTree::skipRitual, new ArrayList<>(Arrays.asList(who, num)), core);
        curNode.adj.add(n);
    }

    static void performRitual(ArrayList<Integer> data, Core core, Node curNode) {
        core.ritual.performRitual(core.factionsList.get(data.get(0)));
        createRitualAsk(core, curNode, core.getNextTurn(data.get(0)), data.get(1) + 1);
    }

    static void skipRitual(ArrayList<Integer> data, Core core, Node curNode) {
        createRitualAsk(core, curNode, core.getNextTurn(data.get(0)), data.get(1) + 1);
    }
}