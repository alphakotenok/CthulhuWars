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
import javafx.scene.text.Font;

public class ButtonVisualizer {
    public static <T extends Button> Button customizeButton(T button, double h, double w, double x, double y, String text) {
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefHeight(h);
        button.setPrefWidth(w);
        button.setText(text);
        return button;
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
        FactionSheetButton[] gameButton = new FactionSheetButton[countOfPlayers];
        double weight = Variables.SCREEN_WIDTH * Variables.PROCENT / countOfPlayers;
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        double th = (Variables.SCREEN_HEIGHT - height) / 2;

        ArrayList<FactionType> orderFactions = Variables.core.getFactions();
        for (int i = 0; i < countOfPlayers; i++) {
            gameButton[i] = new FactionSheetButton(orderFactions.get(i).ordinal());
            customizeButton(gameButton[i], th, weight, i * weight, 0, null);
            Image logo = ImageMisc.getFactionLogoImage(orderFactions.get(i));
            ImageView logoView = new ImageView(logo);
            logoView.setFitHeight(th);
            logoView.setFitWidth(th);
            gameButton[i].setGraphic(logoView);

            ActionsMisc.display(gameButton[i]);
        }
    }

    public static void displayCountOfPlayersButtons() {
        int n = Variables.MAX_COUNT_OF_PLAYERS - Variables.MIN_COUNT_OF_PLAYERS + 1;
        MenuButton[] btnCountOfPlyers = new MenuButton[n];
        double thisHeight = Variables.SCREEN_HEIGHT
                / (Variables.MAX_COUNT_OF_PLAYERS - Variables.MIN_COUNT_OF_PLAYERS + 1);
        for (int countOfPlayers = Variables.MIN_COUNT_OF_PLAYERS; countOfPlayers <= Variables.MAX_COUNT_OF_PLAYERS; countOfPlayers++) {
            int id = countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS;
            btnCountOfPlyers[id] = new MenuButton(countOfPlayers);
            customizeButton(btnCountOfPlyers[id],thisHeight, Variables.SCREEN_WIDTH, 0, 
                id * thisHeight, countOfPlayers + " players");

            ActionsMisc.display(btnCountOfPlyers[id]);
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

        FactionPickButton[] factionPickButtons = new FactionPickButton[Variables.NUMBER_OF_FACTIONS];

        FactionPickButton.numberOfPlayers = numberOfPlayers;
        int id = 0;

        for (int factionId = 0; factionId < Variables.NUMBER_OF_FACTIONS; factionId++) {
            if (FactionPickButton.factionList.contains(MiscFunctions.getFactionByID(factionId)))
                continue;

            factionPickButtons[factionId] = new FactionPickButton(factionId);
            customizeButton(factionPickButtons[factionId], thisHeight, Variables.SCREEN_WIDTH, 0, (id + 1) * thisHeight, 
                Variables.NAME_OF_FACTIONS[factionId]);
            factionPickButtons[factionId].setTextFill(Variables.COLOR_OF_FACTIONS[factionId]);
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

        OrderChooseButton[] commandButton = new OrderChooseButton[countOfCommands];
        for (int idx = 0; idx < countOfCommands; idx++) {
            int orderID = indexes.get(idx);
            commandButton[idx] = new OrderChooseButton(orderID, orders.get(orderID));
            customizeButton(commandButton[idx], thisHeight, Variables.SCREEN_WIDTH, 0, (idx + 1) * thisHeight, 
                null);
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
        label.setFont(Font.font("Arial", 25));
        ActionsMisc.display(label);

        CommandButton[] commandButton = new CommandButton[commands.size()];
        for (int commandID = 0; commandID < commands.size(); commandID++) {
            commandButton[commandID] = new CommandButton(commandID);
            customizeButton(commandButton[commandID], thisHeight, Variables.SCREEN_WIDTH * (1 - Variables.PROCENT),
                Variables.SCREEN_WIDTH * Variables.PROCENT, (commandID + 1) * thisHeight, commands.get(commandID));
            ActionsMisc.display(commandButton[commandID]);
        }
    }

    public static void spellBookButton(int factionID) {
        double width = Variables.SCREEN_WIDTH * Variables.PROCENT;
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        SpellBookButton spellBookButton = new SpellBookButton(factionID);
        customizeButton(spellBookButton, 100 * Variables.PROCENT, 300 * Variables.PROCENT, width / 2 + 160 * Variables.PROCENT,
            (Variables.SCREEN_HEIGHT - height) / 2 + 20 * Variables.PROCENT, "About spellbooks");
        ActionsMisc.display(spellBookButton);
    }
}