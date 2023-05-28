package Controler;

import Model.Variables;
import View.ButtonVisualizer;
import View.Misc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CommandButton implements EventHandler<ActionEvent> {
    int commandID;

    public CommandButton(int commandID) {
        this.commandID = commandID;
    }

    public void handle(ActionEvent arg0) {
        Variables.core.activateCommand(commandID);
        Misc.removeButtons(CommandButton.class);
        ButtonVisualizer.displayCommandButtons();
        return;
    }

}
