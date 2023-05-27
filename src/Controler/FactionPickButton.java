package Controler;

import javafx.event.EventHandler;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import Model.Faction.FactionType;
import View.ButtonVisualizer;
import View.Misc;
import View.Visualizer;

public class FactionPickButton implements EventHandler<ActionEvent> {
    private int numberOfPlayers;
    public int playerID;
    public int factionID;
    static ArrayList<FactionType> factionList = new ArrayList<>();

    public FactionPickButton(int numberOfPlayers, int factionID, int playerID) {
        this.numberOfPlayers = numberOfPlayers;
        this.playerID = playerID;
        this.factionID = factionID;
        factionList.clear();
    }

    @Override
    public void handle(ActionEvent event) {
        ButtonVisualizer.rebuildFactionPickButtons(factionID);
        factionList.add(Misc.getFactionByID(factionID));

        if (playerID + 1 == numberOfPlayers) {
            Misc.removeButtons(FactionPickButton.class);
            Misc.removeLabel("player " + numberOfPlayers);
            Visualizer.startCore(numberOfPlayers,factionList);
        }
    }

    public void nextPlayer() {
        playerID++;
    }
}