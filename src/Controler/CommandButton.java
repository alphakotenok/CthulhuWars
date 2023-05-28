package Controler;

import Model.Location;
import Model.Variables;
import View.ButtonVisualizer;
import View.EntityVisualizer;
import View.Visualizer;
import View.ActionsMisc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CommandButton implements EventHandler<ActionEvent> {
    int commandID;

    public CommandButton(int commandID) {
        this.commandID = commandID;
    }

    public void handle(ActionEvent arg0) {
        ActionsMisc.removeButtons(CommandButton.class);
        ActionsMisc.removeLabelById("Power label");
        ActionsMisc.removeLabelById("Doom label");
        ActionsMisc.removeLabelById("ElderSign label");
        ActionsMisc.removeLabelById("ritual");
        ActionsMisc.removeLabelByText(Variables.core.getCommandDescription());
        Variables.core.activateCommand(commandID);
        ButtonVisualizer.displayCommandButtons();
        Visualizer.initializeLabels();
        Visualizer.ritual();

        for (Location location : Variables.core.getLocationsList()) {
            EntityVisualizer.removeEntitiesFromMap(location);
            EntityVisualizer.placeEntitiesOnMap(location);
        }
        return;
    }

}