package Controler;

import View.ActionsMisc;
import View.ButtonVisualizer;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StartButton extends Button {

    public StartButton(){
        setStyle("-fx-background-color: grey");
        setTextFill(Color.SILVER);
        setFont(Font.font("Arial", 40));
        setText("Start game");

        setOnAction(action -> {
            ActionsMisc.clearScreen();
            ButtonVisualizer.displayCountOfPlayersButtons();
        });
    }
}
