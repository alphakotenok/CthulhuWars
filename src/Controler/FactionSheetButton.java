package Controler;

import java.io.FileNotFoundException;
import Model.Variables;
import View.ActionsMisc;
import View.ImageMisc;
import View.Visualizer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FactionSheetButton implements EventHandler<ActionEvent> {
    private int factionID;
    public static Boolean[] buttonState = new Boolean[Variables.NUMBER_OF_FACTIONS];

    public FactionSheetButton(int factionID) {
        this.factionID = factionID;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            ActionsMisc.removeImage(ImageMisc.getSpellBookSheetImage());
            ActionsMisc.removeButtons(SpellBookButton.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < Variables.NUMBER_OF_FACTIONS; i++) {
            if (i != factionID) {
                if (buttonState[i] == true) {
                    try {
                        ActionsMisc.removeImage(ImageMisc.getFactionSheetImage(i));
                        ActionsMisc.removeBooks(i);
                        SpellBookButton.buttonState[i] = false;
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                buttonState[i] = false;
            }
        }

        buttonState[factionID] = !buttonState[factionID];
        if (buttonState[factionID] == true) {
            ActionsMisc.disableButtons(CommandButton.class);
            try {
                Visualizer.displayFactionSheet(factionID);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Visualizer.displayOpenBookSheet(factionID);
        } else {
            ActionsMisc.enableButtons(CommandButton.class);
            try {
                ActionsMisc.removeImage(ImageMisc.getFactionSheetImage(factionID));
                ActionsMisc.removeBooks(factionID);
                SpellBookButton.buttonState[factionID] = false;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
