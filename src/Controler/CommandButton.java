package Controler;

import java.io.FileNotFoundException;

import Model.Variables;
import View.ButtonVisualizer;
import View.EntityVisualizer;
import View.ImageMisc;
import View.Visualizer;
import View.ActionsMisc;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class CommandButton extends Button {
    public CommandButton(int commandID) {
        setFont(Font.font("Arial", 25));
        setWrapText(true);
        setOnAction(action -> {
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
        });
    }

}