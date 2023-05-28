package Controler;

import Model.Variables;
import View.ButtonVisualizer;
import View.Misc;
import View.Visualizer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class getCommandButton implements EventHandler<ActionEvent> {
    int commandID;

    public getCommandButton(int commandID) {
        this.commandID = commandID;
    }

    public void handle(ActionEvent arg0) {
        Variables.core.activateCommand(commandID);
        Misc.removeButtons(getCommandButton.class);
        ButtonVisualizer.getcommandButton();
        return;
    }

}
