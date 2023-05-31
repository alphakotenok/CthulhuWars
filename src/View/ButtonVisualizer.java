package View;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import Controler.OrderChooseButton;
import Controler.SpellBookButton;
import Controler.FactionPickButton;
import Controler.FactionSheetButton;
import Controler.MenuButton;
import Controler.MiscFunctions;
import Controler.CommandButton;
import Model.Variables;
import Model.FactionEnum.FactionType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class ButtonVisualizer {
    public static void rebuildFactionPickButtons(int choosenFaction) {
        int playerID = FactionPickButton.playerID;
        double thisHeight = Variables.SCREEN_HEIGHT / (Variables.NUMBER_OF_FACTIONS + 1);

        ActionsMisc.removeButtons(FactionPickButton.class);

        displayFactionPickButtons(FactionPickButton.numberOfPlayers);

        ActionsMisc.removeLabelByText("player " + playerID);

        FactionPickButton.playerID++;

        Label label = new Label("player " + (playerID + 1));
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(Variables.SCREEN_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 40));

        ActionsMisc.display(label);
        return;
    }

    public static void initializeGameButtons(int countOfPlayers) throws FileNotFoundException {
        Button[] gameButton = new Button[countOfPlayers];
        double weight = Variables.SCREEN_WIDTH * Variables.PROCENT / countOfPlayers;
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;

        ArrayList<FactionType> orderFactions = Variables.core.getFactions();
        for (int i = 0; i < countOfPlayers; i++) {
            gameButton[i] = new Button();
            gameButton[i].setPrefHeight((Variables.SCREEN_HEIGHT - height) / 2);
            gameButton[i].setLayoutX(i * weight);
            gameButton[i].setPrefWidth(weight);
            Image logo = ImageMisc.getFactionLogoImage(orderFactions.get(i));
            ImageView logoView = new ImageView(logo);
            logoView.setFitHeight((Variables.SCREEN_HEIGHT - height) / 2);
            logoView.setFitWidth((Variables.SCREEN_HEIGHT - height) / 2);
            gameButton[i].setGraphic(logoView);
            gameButton[i].setOnAction(new FactionSheetButton(orderFactions.get(i).ordinal()));

            ActionsMisc.display(gameButton[i]);
        }
    }

    public static void displayCountOfPlayersButtons() {
        Button[] btnCountOfPlyers = new Button[Variables.MAX_COUNT_OF_PLAYERS - Variables.MIN_COUNT_OF_PLAYERS + 1];
        double thisHeight = Variables.SCREEN_HEIGHT
                / (Variables.MAX_COUNT_OF_PLAYERS - Variables.MIN_COUNT_OF_PLAYERS + 1);
        for (int countOfPlayers = Variables.MIN_COUNT_OF_PLAYERS; countOfPlayers <= Variables.MAX_COUNT_OF_PLAYERS; countOfPlayers++) {
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS] = new Button();
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS].setText(countOfPlayers + " players");
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS].setPrefHeight(thisHeight);
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS]
                    .setLayoutY((countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS) * thisHeight);
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS].setPrefWidth(Variables.SCREEN_WIDTH);
            btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS]
                    .setOnAction(new MenuButton(countOfPlayers));

            ActionsMisc.display(btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS]);
        }
    }

    public static void displayFactionPickButtons(int numberOfPlayers) {
        double thisHeight = Variables.SCREEN_HEIGHT / (Variables.NUMBER_OF_FACTIONS + 1);

        Label label = new Label("player " + FactionPickButton.playerID);
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(Variables.SCREEN_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 40));

        ActionsMisc.display(label);

        Button[] factionPickButtons = new Button[Variables.NUMBER_OF_FACTIONS];

        FactionPickButton.numberOfPlayers = numberOfPlayers;
        int id = 0;

        for (int factionId = 0; factionId < Variables.NUMBER_OF_FACTIONS; factionId++) {
            if (FactionPickButton.factionList.contains(MiscFunctions.getFactionByID(factionId)))
                continue;
            factionPickButtons[factionId] = new Button();
            factionPickButtons[factionId].setTextFill(Variables.COLOR_OF_FACTIONS[factionId]);
            factionPickButtons[factionId].setText(Variables.NAME_OF_FACTIONS[factionId]);
            factionPickButtons[factionId].setFont(Font.font("Arial", 40));
            factionPickButtons[factionId].setPrefHeight(thisHeight);
            factionPickButtons[factionId].setLayoutY((id + 1) * thisHeight);
            factionPickButtons[factionId].setPrefWidth(Variables.SCREEN_WIDTH);
            factionPickButtons[factionId]
                    .setOnAction(new FactionPickButton(factionId));
            id++;
            ActionsMisc.display(factionPickButtons[factionId]);
        }
    }

    public static void displayOrderChooseButtons() {
        ArrayList<String> orders = Variables.core.getCommandList();
        int countOfCommands = Math.min(orders.size(), Variables.NUMBER_OF_PERMUTATIONS);
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < orders.size(); i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes, new Random());
        double thisHeight = Variables.SCREEN_HEIGHT / (countOfCommands + 1);
        Label label = new Label(Variables.core.getCommandDescription());
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(Variables.SCREEN_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 40));
        ActionsMisc.display(label);

        Button[] commandButton = new Button[countOfCommands];
        for (int idx = 0; idx < countOfCommands; idx++) {
            int orderID = indexes.get(idx);
            commandButton[idx] = new Button();
            TextFlow textFlowFaction = new TextFlow();

            for (int faction = 0; faction < orders.get(orderID).length(); faction++) {
                int factionID = orders.get(orderID).charAt(faction) - '0';
                Text textEntity1 = new Text(Variables.NAME_OF_FACTIONS[factionID]);
                textEntity1.setFill(Variables.COLOR_OF_FACTIONS[factionID]);
                textEntity1.setFont(Font.font("Arial", 32));
                Text textEntity2 = new Text(" -> ");
                textEntity2.setFill(Color.BLACK);
                textEntity2.setFont(Font.font("Arial", 32));
                textFlowFaction.getChildren().add(textEntity1);
                if (faction != orders.get(orderID).length() - 1)
                    textFlowFaction.getChildren().add(textEntity2);
            }
            textFlowFaction.setTextAlignment(TextAlignment.CENTER);

            commandButton[idx].setGraphic(textFlowFaction);
            commandButton[idx].setPrefHeight(thisHeight);
            commandButton[idx].setLayoutY((idx + 1) * thisHeight);
            commandButton[idx].setPrefWidth(Variables.SCREEN_WIDTH);
            commandButton[idx].setOnAction(new OrderChooseButton(orderID));

            ActionsMisc.display(commandButton[idx]);
        }
    }

    public static void displayCommandButtons() {
        ArrayList<String> commands = Variables.core.getCommandList();
        double thisHeight = Variables.SCREEN_HEIGHT / (commands.size() + 1);
        Label label = new Label(Variables.core.getCommandDescription());
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(300);
        label.setLayoutX(Variables.SCREEN_WIDTH - 300);
        label.setAlignment(Pos.CENTER);
        label.setWrapText(true);
        label.setFont(Font.font("Arial", 16));
        ActionsMisc.display(label);

        Button[] commandButton = new Button[commands.size()];
        for (int commandID = 0; commandID < commands.size(); commandID++) {
            commandButton[commandID] = new Button();

            commandButton[commandID].setText(commands.get(commandID));
            commandButton[commandID].setPrefHeight(thisHeight);
            commandButton[commandID].setLayoutY((commandID + 1) * thisHeight);
            commandButton[commandID].setPrefWidth(Variables.SCREEN_WIDTH * (1 - Variables.PROCENT));
            commandButton[commandID].setLayoutX(Variables.SCREEN_WIDTH - 300);
            commandButton[commandID].setOnAction(new CommandButton(commandID));

            ActionsMisc.display(commandButton[commandID]);
        }
    }

    public static void spellBookButton(int factionID) {
        double width = Variables.SCREEN_WIDTH * Variables.PROCENT;
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        Button spellBookButton = new Button();
        spellBookButton.setText("About spellbooks");
        spellBookButton.setFont(Font.font("Arial", 25));
        spellBookButton.setPrefHeight(100 * Variables.PROCENT);
        spellBookButton.setLayoutY((Variables.SCREEN_HEIGHT - height) / 2 + 20 * Variables.PROCENT);
        spellBookButton.setPrefWidth(300 * Variables.PROCENT);
        spellBookButton.setLayoutX(width / 2 + 160 * Variables.PROCENT);
        spellBookButton.setOnAction(new SpellBookButton(factionID));

        ActionsMisc.display(spellBookButton);
    }
}