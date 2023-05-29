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
                if (Variables.factionSheetButtonState[i] == true) {
                    try {
                        ActionsMisc.removeImage(ImageMisc.getFactionSheetImage(i));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                Variables.factionSheetButtonState[i] = false;
            }
        }

        Variables.factionSheetButtonState[factionID] = !Variables.factionSheetButtonState[factionID];
        if (Variables.factionSheetButtonState[factionID] == true) {
            ActionsMisc.disableButtons(CommandButton.class);
            try {
                Visualizer.displayfactionSheet(factionID);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            ActionsMisc.enableButtons(CommandButton.class);
            try {
                ActionsMisc.removeImage(ImageMisc.getFactionSheetImage(factionID));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}