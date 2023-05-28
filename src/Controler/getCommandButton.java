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
        Misc.removeButtons(getCommandButton.class);
        Misc.removeLabel(Variables.core.getCommandDescription());
        Variables.core.activateCommand(commandID);

        ButtonVisualizer.getcommandButton();
        return;
    }

}
