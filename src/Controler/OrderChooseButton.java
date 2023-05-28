package Controler;

import Model.Variables;
import View.ActionsMisc;
import View.Visualizer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class OrderChooseButton implements EventHandler<ActionEvent> {
    int commandID;
    public OrderChooseButton(int commandID){
        this.commandID = commandID; 
    }

    public void handle(ActionEvent arg0) {
        ActionsMisc.removeButtons(OrderChooseButton.class);
        ActionsMisc.removeLabelByText(Variables.core.getCommandDescription());
        Variables.core.activateCommand(commandID);
        try {
            Visualizer.createField(Variables.numberOfPlayers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }
    
}
