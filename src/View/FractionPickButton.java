package View;

import javafx.event.EventHandler;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import Model.Core;
import Model.Variables;
import Model.Core.InvalidFactionsSetException;
import Model.Core.InvalidNumOfPlayersException;
import Model.Faction.FactionType;

public class FractionPickButton implements EventHandler<ActionEvent> {
    private int numberOfPlayers;
    public int playerID;
    public int fractionID;
    static ArrayList<FactionType> FactionList = new ArrayList<>();

    public FractionPickButton(int numberOfPlayers, int fractionID, int playerID) {
        this.numberOfPlayers = numberOfPlayers;
        this.playerID = playerID;
        this.fractionID = fractionID;
        FactionList.clear();
    }

    @Override
    public void handle(ActionEvent event) {
        /// TODO: we have to assign player with playerID its fraction id.
        ButtonVisualizer.rebuildFractionPickButtons(fractionID);
        FactionList.add(Misc.getFactionByID(fractionID));

        if (playerID + 1 == numberOfPlayers) {
            Misc.removeButtons(FractionPickButton.class);
            Misc.removeLabel("player " + numberOfPlayers);
            try {
                Variables.core = new Core(numberOfPlayers, FactionList);
            } catch (InvalidNumOfPlayersException e) {
                // TODO: catch exception
                e.printStackTrace();
            } catch (InvalidFactionsSetException e) {
                // TODO: catch exception
                e.printStackTrace();
            }
            try {
                Visualizer.createField(numberOfPlayers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void nextPlayer() {
        playerID++;
    }
}
