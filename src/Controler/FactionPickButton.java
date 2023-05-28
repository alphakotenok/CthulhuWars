package Controler;

import javafx.event.EventHandler;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import Model.Faction.FactionType;
import View.ButtonVisualizer;
import View.ActionsMisc;
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
        factionList.add(MiscFunctions.getFactionByID(factionID));

        if (playerID + 1 == numberOfPlayers) {
            ActionsMisc.removeButtons(FactionPickButton.class);
            ActionsMisc.removeLabelByText("player " + numberOfPlayers);
            Visualizer.startCore(numberOfPlayers,factionList);
        }
    }

    public void nextPlayer() {
        playerID++;
    }
}
