package Controler;

import View.ActionsMisc;
import View.ButtonVisualizer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class StartButton implements EventHandler<ActionEvent> {

    public StartButton(){
        
    }
    @Override
    public void handle(ActionEvent arg0) {
        ActionsMisc.clearScreen();
        ButtonVisualizer.displayCountOfPlayersButtons();
    }
}
