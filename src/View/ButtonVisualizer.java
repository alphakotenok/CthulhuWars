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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    public static <T extends EventHandler<ActionEvent>> Button buildButton(double h, double w, double x, double y, String text, T eventHandler) {
        Button b = new Button();
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setPrefHeight(h);
        b.setPrefWidth(w);
        b.setText(text);
        b.setOnAction(eventHandler);
        return b;
    }


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
        double th = (Variables.SCREEN_HEIGHT - height) / 2;

        ArrayList<FactionType> orderFactions = Variables.core.getFactions();
        for (int i = 0; i < countOfPlayers; i++) {
            gameButton[i] = buildButton(th, weight, i * weight, 0, null, new FactionSheetButton(orderFactions.get(i).ordinal()));
            Image logo = ImageMisc.getFactionLogoImage(orderFactions.get(i));
            ImageView logoView = new ImageView(logo);
            logoView.setFitHeight(th);
            logoView.setFitWidth(th);
            gameButton[i].setGraphic(logoView);

            ActionsMisc.display(gameButton[i]);
        }
    }

    public static void displayCountOfPlayersButtons() {
        Button[] btnCountOfPlyers = new Button[Variables.MAX_COUNT_OF_PLAYERS - Variables.MIN_COUNT_OF_PLAYERS + 1];
        double thisHeight = Variables.SCREEN_HEIGHT
                / (Variables.MAX_COUNT_OF_PLAYERS - Variables.MIN_COUNT_OF_PLAYERS + 1);
        for (int countOfPlayers = Variables.MIN_COUNT_OF_PLAYERS; countOfPlayers <= Variables.MAX_COUNT_OF_PLAYERS; countOfPlayers++) {
            int id = countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS;
            btnCountOfPlyers[id] = buildButton(thisHeight, Variables.SCREEN_WIDTH, 0, 
                (countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS) * thisHeight, countOfPlayers + " players", new MenuButton(countOfPlayers));
            btnCountOfPlyers[id].setFont(Font.font("Arial", 32));
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
            factionPickButtons[factionId] = buildButton(thisHeight, Variables.SCREEN_WIDTH, 0, (id + 1) * thisHeight, 
                Variables.NAME_OF_FACTIONS[factionId], new FactionPickButton(factionId));
            factionPickButtons[factionId].setTextFill(Variables.COLOR_OF_FACTIONS[factionId]);
            factionPickButtons[factionId].setFont(Font.font("Arial", 40));
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
            commandButton[idx] = buildButton(thisHeight, Variables.SCREEN_WIDTH, 0, (idx + 1) * thisHeight, 
                null, new OrderChooseButton(orderID));
            
            ActionsMisc.display(commandButton[idx]);
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

            commandButton[idx].setGraphic(textFlowFaction);
            textFlowFaction.setTextAlignment(TextAlignment.CENTER);

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
        label.setFont(Font.font("Arial", 25));
        ActionsMisc.display(label);

        Button[] commandButton = new Button[commands.size()];
        for (int commandID = 0; commandID < commands.size(); commandID++) {
            commandButton[commandID] = buildButton(thisHeight, Variables.SCREEN_WIDTH * (1 - Variables.PROCENT),
                Variables.SCREEN_WIDTH * Variables.PROCENT, (commandID + 1) * thisHeight, commands.get(commandID), new CommandButton(commandID));

            commandButton[commandID].setFont(Font.font("Arial", 25));
            commandButton[commandID].setWrapText(true);

            ActionsMisc.display(commandButton[commandID]);
        }
    }

    public static void spellBookButton(int factionID) {
        double width = Variables.SCREEN_WIDTH * Variables.PROCENT;
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        Button spellBookButton = buildButton(100 * Variables.PROCENT, 300 * Variables.PROCENT, width / 2 + 160 * Variables.PROCENT,
            (Variables.SCREEN_HEIGHT - height) / 2 + 20 * Variables.PROCENT, "About spellbooks", new SpellBookButton(factionID));
        spellBookButton.setFont(Font.font("Arial", 25));
        ActionsMisc.display(spellBookButton);
    }
}