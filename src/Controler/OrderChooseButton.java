package Controler;

import Model.Variables;
import View.Misc;
import View.Visualizer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class OrderChooseButton implements EventHandler<ActionEvent> {
    int commandID;
    public OrderChooseButton(int commandID){
        this.commandID = commandID; 
    }

    public void handle(ActionEvent arg0) {
        Misc.removeButtons(OrderChooseButton.class);
        Misc.removeLabel(Variables.core.getCommandDescription());
        Variables.core.activateCommand(commandID);
        try {
            Visualizer.createField(Variables.numberOfPlayers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
    
}
