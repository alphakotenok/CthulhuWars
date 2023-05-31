package Model;

import java.util.ArrayList;

import Model.FactionEnum.FactionType;

class GameVariables {
    int numOfPlayers;
    static int minNumOfPlayers = 2;
    static int maxNumOfPlayers = 6;
    ArrayList<FactionType> factionsList;
    int turn = 0;
    boolean correcrtWay = true;
    int firstPlayer = 0;
    boolean endOfTheGame = false;
    int playerCounter;
    ArrayList<Integer> chosenPerm;
    Location chosenLocation;
    Entity chosenEntity;
    int totalSkip = 0;
    FactionType bookReceiver;
    int bookSlot;
    int bookNum;
    ArrayList<Integer> firstPlayerCandidates;
    int signToReveal;

    GameVariables(int numOfPlayers, ArrayList<FactionType> factionsList) {
        this.numOfPlayers = numOfPlayers;
        this.factionsList = factionsList;
    }

    int getNextTurn(int who) {
        if (correcrtWay)
            return (who + 1) % numOfPlayers;
        return (who - 1 + numOfPlayers) % numOfPlayers;
    }
}
