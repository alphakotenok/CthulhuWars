package Controler;

import View.Misc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MenuButton implements EventHandler<ActionEvent> {
    private int numberOfPlayers;

    public MenuButton(int value) {
        numberOfPlayers = value;
    }

    @Override
    public void handle(ActionEvent event) {
        Misc.removeButtons(MenuButton.class);
        
        View.ButtonVisualizer.displayFactionPickButtons(numberOfPlayers);
    }
}
