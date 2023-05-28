package Model;

import java.util.ArrayList;
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
        void activate(ArrayList<Integer> args);
    }

    static class Node {
        String name;
        ArrayList<Node> adj;
        FunctionContainer func;
        ArrayList<Integer> data;

        Node(String name, FunctionContainer func, ArrayList<Integer> data) {
            this.name = name;
            this.func = func;
            this.data = data;
            adj = new ArrayList<>();
        }

        void activate() {
            func.activate(data);
        }
    }

    CommandTree(Core core) {
        this.core = core;
        curNode = new Node("Start node", null, null);
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
        Node n = new Node(k, CommandTree::chooseFactionPermutation, order3);
        curNode.adj.add(n);
        while (nextPermutation.findNextPermutation(order2)) {
            order3 = new ArrayList<>();
            order3.addAll(order);
            order3.addAll(order2);
            k = "";
            for (int i = 0; i < core.numOfPlayers; ++i) {
                k += order3.get(i).toString();
            }
            n = new Node(k, CommandTree::chooseFactionPermutation, order3);
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

    static void chooseFactionPermutation(ArrayList<Integer> perm) {

    }
}