package Controler;

import java.io.FileNotFoundException;

import Model.Variables;
import View.Misc;
import View.Visualizer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class FactionSheet implements EventHandler<ActionEvent> {
    private int numberOfFaction;

    public FactionSheet(int numberOfFaction) {
        this.numberOfFaction = numberOfFaction;
    }

    @Override
    public void handle(ActionEvent event) {
        for (int i = 0; i < Variables.NUMBER_OF_FACTIONS; i++) {
            if (i != numberOfFaction) {
                if (Variables.factionSheetButtonState[i] == true)
                    Misc.removeLastImage();
                Variables.factionSheetButtonState[i] = false;
            }
        }

        Variables.factionSheetButtonState[numberOfFaction] = !Variables.factionSheetButtonState[numberOfFaction];
        if (Variables.factionSheetButtonState[numberOfFaction] == true) {
            Misc.disableButtons(ContinentButton.class);
            try {
                Visualizer.factionSheet(numberOfFaction);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Misc.ableButtons(ContinentButton.class);
            Misc.removeLastImage();
        }
    }
}