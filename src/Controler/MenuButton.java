package Controler;

import View.ActionsMisc;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class MenuButton extends Button {
    
    public MenuButton(int numberOfPlayers) {
        setFont(Font.font("Arial", 32));
        setOnAction(action -> {
            ActionsMisc.removeButtons(MenuButton.class);
            FactionPickButton.playerID = 0;
            View.ButtonVisualizer.displayFactionPickButtons(numberOfPlayers);
        });
    }

}
