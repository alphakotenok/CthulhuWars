package View;

import Model.Variables;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class MenuButtonActionHandler implements EventHandler<ActionEvent> {
    private int numberOfPlayers;

    public MenuButtonActionHandler(int value) {
        numberOfPlayers = value;
    }

    @Override
    public void handle(ActionEvent event) {
        Misc.removeButtons(MenuButtonActionHandler.class);

        Button[] fractionPickButtons = new Button[Variables.NUMBER_OF_FRACTIONS + 1];
        double thisHeight = Variables.SCREEN_HEIGHT / (Variables.NUMBER_OF_FRACTIONS + 1);

        Label label = new Label("player " + 0);
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(Variables.SCREEN_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 40));
        Variables.root.getChildren().add(label);

        for (int fractionId = 0; fractionId < Variables.NUMBER_OF_FRACTIONS; fractionId++) {
            fractionPickButtons[fractionId] = new Button();
            fractionPickButtons[fractionId].setTextFill(Variables.COLOR_OF_FRACTIONS[fractionId]);
            fractionPickButtons[fractionId].setText(Variables.NAME_OF_FRACTIONS[fractionId]);
            fractionPickButtons[fractionId].setFont(Font.font("Arial", 40));
            fractionPickButtons[fractionId].setPrefHeight(thisHeight);
            fractionPickButtons[fractionId].setLayoutY((fractionId + 1) * thisHeight);
            fractionPickButtons[fractionId].setPrefWidth(Variables.SCREEN_WIDTH);
            fractionPickButtons[fractionId]
                    .setOnAction(new FractionPickButtonActionHandler(numberOfPlayers, fractionId, 0));

            Variables.root.getChildren().add(fractionPickButtons[fractionId]);
        }
    }
}
