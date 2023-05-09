package Model;

public class Core {
    static Map map;
    static int numOfPlayers;

    public static void setup(int numOfPlayers) {
        Core.numOfPlayers = numOfPlayers;
        map = new Map(numOfPlayers);

    }
}
