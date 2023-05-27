package Model;

<<<<<<< HEAD
class CommandTree {

    public static class Node {

    }

=======
import java.util.ArrayList;

class CommandTree {

    Node curNode;

    static class InvalidNodeNumberException extends Exception {

    }

    @FunctionalInterface
    static interface FunctionContainer {
        void activate();
    }

    static class Node {
        String name;
        ArrayList<Node> adj;
        FunctionContainer func;
    }

    CommandTree() {

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
        curNode.func.activate();
    }
>>>>>>> 1fdfbbe095361dbf4c2338b2d016d340c1f84801
}