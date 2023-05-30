package Controler;

import java.io.FileNotFoundException;

import Model.Variables;
import View.ActionsMisc;
import View.ImageMisc;
import View.Visualizer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SpellBookButton implements EventHandler<ActionEvent> {
    private int factionID;
    public static Boolean[] buttonState = new Boolean[Variables.NUMBER_OF_FACTIONS];

    public SpellBookButton(int factionID) {
        this.factionID = factionID;
    }

    @Override
    public void handle(ActionEvent event) {
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
    }
}