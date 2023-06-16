package Controler;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

import java.util.ArrayList;

import Model.FactionEnum.FactionType;
import View.ButtonVisualizer;
import View.ActionsMisc;

public class FactionPickButton extends Button {
    public static int numberOfPlayers;
    public static int playerID;
    public static ArrayList<FactionType> factionList = new ArrayList<>();

    public FactionPickButton(int factionID) {
        setFont(Font.font("Arial", 40));
        setOnAction(action -> {
            factionList.add(MiscFunctions.getFactionByID(factionID));
            ButtonVisualizer.rebuildFactionPickButtons(factionID);
            if (playerID == numberOfPlayers) {
                ActionsMisc.removeButtons(FactionPickButton.class);
                ActionsMisc.removeLabelByText("player " + numberOfPlayers);
                Game.startCore(numberOfPlayers, factionList);
            }
        });
    }
}
