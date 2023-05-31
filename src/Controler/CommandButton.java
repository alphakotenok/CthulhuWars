package Controler;

import java.io.FileNotFoundException;

import Model.Variables;
import View.ButtonVisualizer;
import View.EntityVisualizer;
import View.ImageMisc;
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
        try {
            ActionsMisc.removeImage(ImageMisc.getArrowImage(Variables.core.getWay()));
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        ActionsMisc.removeLabelById("Power label");
        ActionsMisc.removeLabelById("Doom label");
        ActionsMisc.removeLabelById("ElderSign label");
        ActionsMisc.removeLabelById("ritual");
        ActionsMisc.removeLabelByText(Variables.core.getCommandDescription());
        Variables.core.activateCommand(commandID);
        ButtonVisualizer.displayCommandButtons();
        Visualizer.initializeLabels();
        Visualizer.displayRitualLabel();
        try {
            Visualizer.arrowImageView();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        EntityVisualizer.removeEntitiesFromMap();
        EntityVisualizer.placeEntitiesOnMap();
        return;
    }

}