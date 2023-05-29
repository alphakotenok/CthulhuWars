package Controler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FinishButton implements EventHandler<ActionEvent> {
    public FinishButton(){

    }
    @Override
    public void handle(ActionEvent event) {
        Game.startGame();
    }
}
