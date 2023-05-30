package Controler;

import java.util.ArrayList;

import Model.Core;
import Model.Variables;
import Model.Core.InvalidFactionsSetException;
import Model.Core.InvalidNumOfPlayersException;
import Model.FactionEnum.FactionType;
import View.ActionsMisc;
import View.ButtonVisualizer;
import View.Visualizer;

public class Game {
    public static void startGame() {
        for (int i = 0; i < Variables.NUMBER_OF_FACTIONS; i++) {
            FactionSheetButton.buttonState[i] = false;
            SpellBookButton.buttonState[i] = false;
        }
        FactionPickButton.factionList.clear();
        ActionsMisc.clearScreen();
        Variables.core = null;
        Visualizer.displayStartGameButton();
    }

    public static void startCore(int numberOfPlayers, ArrayList<FactionType> factionList) {
        try {
            Variables.core = new Core(numberOfPlayers, factionList);
        } catch (InvalidNumOfPlayersException e) {
            e.printStackTrace();
        } catch (InvalidFactionsSetException e) {
            e.printStackTrace();
        }
        Variables.numberOfPlayers = numberOfPlayers;
        ButtonVisualizer.displayOrderChooseButtons();
    }
}
