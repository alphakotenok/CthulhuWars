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
        Faction faction = core.factionBase.getFactionFromEnum(FactionType.values()[core.turn]);
        if (faction.skip) {
            if (core.factionBase.totalSkip == core.numOfPlayers) {
                // TODO: if everyone skip
                curNode.desc = "turnEnd";
                return;
            }
            Node n = new Node("Skip",
                    core.factionBase.getFactionNameFromEnum(core.factionsList.get((core.turn + 1) % core.numOfPlayers))
                            + " action",
                    CommandTree::prepareActionSet, null, core);
            curNode.adj.add(n);
            core.turn = (core.turn + 1) % core.numOfPlayers;
        } else {

            Node n = new Node("Skip",
                    core.factionBase.getFactionNameFromEnum(core.factionsList.get((core.turn + 1) % core.numOfPlayers))
                            + " action",
                    CommandTree::skipTurn, data, core);
            curNode.adj.add(n);
        }
    }

    static void skipTurn(ArrayList<Integer> data, Core core, Node curNode) {
        Faction faction = core.factionBase.getFactionFromEnum(FactionType.values()[core.turn]);
        faction.skip = true;
        core.factionBase.totalSkip++;
        core.turn = (core.turn + 1) % core.numOfPlayers;
        prepareActionSet(data, core, curNode);
    }

    static void energyRecount(Core core) {
        // recount enegry
    }

    // static void
}