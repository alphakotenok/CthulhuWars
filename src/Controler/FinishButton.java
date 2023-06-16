package Controler;

import javafx.scene.control.Button;

public class FinishButton extends Button {
    public FinishButton(){
        setText("Finish game");
        setOnAction(action -> {
            Game.startGame();
        });
    }
}
