package Controler;

import javafx.event.EventHandler;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import Model.Faction.FactionType;
import View.ButtonVisualizer;
import View.ActionsMisc;

public class FactionPickButton implements EventHandler<ActionEvent> {
    public static int numberOfPlayers;
    public static int playerID;
    public int factionID;
    public static ArrayList<FactionType> factionList = new ArrayList<>();

    public FactionPickButton(int factionID) {
        this.factionID = factionID;
    }

    @Override
    public void handle(ActionEvent event) {
        factionList.add(MiscFunctions.getFactionByID(factionID));
        ButtonVisualizer.rebuildFactionPickButtons(factionID);
        if (playerID == numberOfPlayers) {
            ActionsMisc.removeButtons(FactionPickButton.class);
            ActionsMisc.removeLabelByText("player " + numberOfPlayers);
            Game.startCore(numberOfPlayers,factionList);
        }
    }
}
