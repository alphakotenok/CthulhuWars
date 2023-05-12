package View;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class FractionPickButtonActionHandler implements EventHandler<ActionEvent> {
    private int numberOfPlayers;
    public int playerID;
    public int fractionID;
    
    public FractionPickButtonActionHandler(int numberOfPlayers, int fractionID, int playerID) {
        this.numberOfPlayers = numberOfPlayers;
        this.playerID = playerID;
        this.fractionID = fractionID;
    }

    @Override
    public void handle(ActionEvent event) {
        /// TODO: we have to assign player with playerID its fraction id.
        ButtonVisualizer.rebuildFractionPickButtons(fractionID);

        if (playerID + 1 == numberOfPlayers) {
            Misc.removeButtons(FractionPickButtonActionHandler.class);
            Misc.removeLabel("player " + numberOfPlayers);
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
