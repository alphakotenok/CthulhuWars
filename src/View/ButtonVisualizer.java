package View;

import java.util.ArrayList;
import java.util.List;

import Model.Variables;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class ButtonVisualizer {
    static void rebuildFractionPickButtons(int choosenFraction) {
        List<Node> nodeList = new ArrayList<>();
        List<Button> buttonList = new ArrayList<>();
        int playerID = 0;
        for (Node node : Variables.root.getChildren()) {
            if (node instanceof Button) {
                EventHandler<ActionEvent> onAction = ((Button) node).getOnAction();
                if (onAction != null && onAction instanceof FractionPickButtonActionHandler) {
                    nodeList.add(node);
                    if (((FractionPickButtonActionHandler) onAction).fractionID != choosenFraction)
                        buttonList.add((Button) node);
                    playerID = ((FractionPickButtonActionHandler) onAction).playerID;
                }
            }
        }
        Variables.root.getChildren().removeAll(nodeList);
        double thisHeight = Variables.SCREEN_HEIGHT / (Variables.NUMBER_OF_FRACTIONS + 1);

        Misc.removeLabel("player " + playerID);

        int fractionId = 0;

        for (Button fractionPickButton : buttonList) {
            FractionPickButtonActionHandler onAction = (FractionPickButtonActionHandler) fractionPickButton
                    .getOnAction();
            onAction.nextPlayer();

            fractionPickButton.setLayoutY((fractionId + 1) * thisHeight);
            Variables.root.getChildren().add(fractionPickButton);
            fractionId++;
        }

        Label label = new Label("player " + (playerID + 1));
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(Variables.SCREEN_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 40));
        Variables.root.getChildren().add(label);
        return;
    }

    static void chooseCountOfPlayers() {
        Button[] btnCountOfPlyers = new Button[Variables.MAX_COUNT_OF_PLAYERS - Variables.MIN_COUNT_OF_PLAYERS + 1];
        double thisHeight = Variables.SCREEN_HEIGHT / (Variables.MAX_COUNT_OF_PLAYERS - Variables.MIN_COUNT_OF_PLAYERS + 1);
        for (int countOfPlayers = Variables.MIN_COUNT_OF_PLAYERS; countOfPlayers <= Variables.MAX_COUNT_OF_PLAYERS; countOfPlayers++) {
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS] = new Button();
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS].setText(countOfPlayers + " players");
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS].setPrefHeight(thisHeight);
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS]
                    .setLayoutY((countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS) * thisHeight);
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS].setPrefWidth(Variables.SCREEN_WIDTH);
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS]
                    .setOnAction(new MenuButtonActionHandler(countOfPlayers));

            Variables.root.getChildren().add(btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS]);
        }
    }
}
