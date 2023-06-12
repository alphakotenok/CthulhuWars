package Model;

import java.util.ArrayList;

import Model.FactionEnum.FactionType;
import java.util.concurrent.ThreadLocalRandom;

public class GameVariables {
    int numOfPlayers;
    static int minNumOfPlayers = 2;
    static int maxNumOfPlayers = 6;
    ArrayList<FactionType> factionsList;
    int turn = 0;
    boolean correctWay = true;
    int firstPlayer = 0;
    boolean endOfTheGame = false;
    int playerCounter;
    ArrayList<Integer> chosenPerm;
    Location chosenLocation;
    Location chosenDestination;
    EntitySet chosenEntity;
    int totalSkip = 0;
    FactionType bookReceiver;
    int bookSlot;
    int bookNum;
    ArrayList<Integer> firstPlayerCandidates;
    ArrayList<Location> locationPossibleToAttack = new ArrayList<>();
    int signToReveal;
    int firstCultistToKillByBlackGoat = 0;
    int secondCultistToKillByBlackGoat = 0;
    int chosenFaction = 0;
    int numOfCtulhuAwakes = 0;
    int firstPlayerNothing = 0;
    int firstPlayerInjure = 0;
    int firstPlayerKill = 0;
    int secondPlayerNothing = 0;
    int secondPlayerInjure = 0;
    int secondPlayerKill = 0;
    Location battleLocation;
    boolean didBlackGoatKillTwoCultists = false;
    boolean loseEnergy4 = false, loseEnergy6 = false;
    boolean didRitualSleeper = false;
    boolean did3EnergyLoseAnd1EnergyForOthersGet = false;
    boolean did3EnergyLoseAnd3EnergyPresent = false;
    boolean did3EnergyAnd1EnergyForOthersLose = false;
    boolean didCrawlingChaosCaptureCultist = false;
    int entityToKill = 0;

    public enum PerformableAction {
        None, Move, Spawn, GateBuilding, Capture, Extra
    }

    PerformableAction action;

    GameVariables(int numOfPlayers, ArrayList<FactionType> factionsList) {
        this.numOfPlayers = numOfPlayers;
        this.factionsList = factionsList;
    }

    int getNextTurn(int who) {
        if (correctWay)
            return (who + 1) % numOfPlayers;
        return (who - 1 + numOfPlayers) % numOfPlayers;
    }

    void throwDice(int who, int amount) {
        if (who == 1) {
            firstPlayerNothing = 0;
            firstPlayerInjure = 0;
            firstPlayerKill = 0;
        } else {
            secondPlayerNothing = 0;
            secondPlayerInjure = 0;
            secondPlayerKill = 0;
        }
        for (int i = 0; i < amount; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 7);
            if (randomNum < 4) {
                if (who == 1)
                    firstPlayerNothing++;
                if (who == 2)
                    secondPlayerNothing++;
            }
            if (randomNum < 6 && randomNum > 3) {
                if (who == 1)
                    firstPlayerInjure++;
                if (who == 2)
                    secondPlayerInjure++;
            }
            if (randomNum == 6) {
                if (who == 1)
                    firstPlayerKill++;
                if (who == 2)
                    secondPlayerKill++;
            }
        }
    }
}
