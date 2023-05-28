package Controler;

import View.ActionsMisc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuButton implements EventHandler<ActionEvent> {
    private int numberOfPlayers;

    public MenuButton(int value) {
        numberOfPlayers = value;
    }

    @Override
    public void handle(ActionEvent event) {
        ActionsMisc.removeButtons(MenuButton.class);
        
        View.ButtonVisualizer.displayFactionPickButtons(numberOfPlayers);
    }
}
