package Controler;

import java.io.FileNotFoundException;

import Model.Variables;
import View.ActionsMisc;
import View.ImageMisc;
import View.Visualizer;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class SpellBookButton extends Button {
    public static Boolean[] buttonState = new Boolean[Variables.NUMBER_OF_FACTIONS];

    public SpellBookButton(int factionID) {
        setFont(Font.font("Arial", 25));
        setOnAction(action -> {
            buttonState[factionID] = !buttonState[factionID];
            if (buttonState[factionID] == true) {
                try {
                    Visualizer.displaySpellBookSheet(factionID);
                    Visualizer.displayUnopenedBookSheet(factionID);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ActionsMisc.removeImage(ImageMisc.getSpellBookSheetImage());
                    ActionsMisc.removeUnopenedBooks(factionID);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}