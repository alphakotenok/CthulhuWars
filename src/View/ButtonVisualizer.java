package View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Controler.CommandButton;
import Controler.ContinentButton;
import Controler.EntityAddButton;
import Controler.EntityButton;
import Controler.EntityDeleteButton;
import Controler.FactionPickButton;
import Controler.MenuButton;
import Controler.getCommandButton;
import Model.Entity;
import Model.Location;
import Model.Variables;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class ButtonVisualizer {
    public static void displayContinentButtons() {
        ArrayList<String> continent = new ArrayList<>();
        for (Location location : Variables.core.getLocationsList()) {
            continent.add(location.name);
        }

        int numberOfContinents = continent.size();
        double height = Variables.SCREEN_HEIGHT / numberOfContinents;
        Button[] continentsButton = new Button[numberOfContinents];

        for (int continentID = 0; continentID < numberOfContinents; continentID++) {
            continentsButton[continentID] = new Button();
            continentsButton[continentID].setPrefHeight(height);
            continentsButton[continentID].setLayoutY(continentID * height);
            continentsButton[continentID].setPrefWidth(300);
            continentsButton[continentID].setText(continent.get(continentID));
            continentsButton[continentID].setLayoutX(Variables.SCREEN_WIDTH - 300);

            continentsButton[continentID]
                    .setOnAction(new ContinentButton(Variables.core.getLocationsList().get(continentID)));
            Variables.root.getChildren().add(continentsButton[continentID]);
        }
    }

    public static void rebuildFactionPickButtons(int choosenFaction) {
        List<Node> nodeList = new ArrayList<>();
        List<Button> buttonList = new ArrayList<>();
        int playerID = 0;
        for (Node node : Variables.root.getChildren()) {
            if (node instanceof Button) {
                EventHandler<ActionEvent> onAction = ((Button) node).getOnAction();
                if (onAction != null && onAction instanceof FactionPickButton) {
                    nodeList.add(node);
                    if (((FactionPickButton) onAction).factionID != choosenFaction)
                        buttonList.add((Button) node);
                    playerID = ((FactionPickButton) onAction).playerID;
                }
            }
        }
        Variables.root.getChildren().removeAll(nodeList);
        double thisHeight = Variables.SCREEN_HEIGHT / (Variables.NUMBER_OF_FACTIONS + 1);

        Misc.removeLabel("player " + playerID);

        int factionId = 0;

        for (Button FactionPickButton : buttonList) {
            FactionPickButton onAction = (FactionPickButton) FactionPickButton
                    .getOnAction();
            onAction.nextPlayer();

            FactionPickButton.setLayoutY((factionId + 1) * thisHeight);
            Variables.root.getChildren().add(FactionPickButton);
            factionId++;
        }

        Label label = new Label("player " + (playerID + 1));
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(Variables.SCREEN_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 40));
        Variables.root.getChildren().add(label);
        return;
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

            Variables.root.getChildren().add(btnCountOfPlyers[countOfPlayers - Variables.MIN_COUNT_OF_PLAYERS]);
        }
    }

    public static void rebuildEntityDelButtons(Location continent) {
        ArrayList<Entity> nameOfEntity = Variables.core.getEntityListInLocation(continent);
        int numberEntity = nameOfEntity.size();
        double height = Variables.SCREEN_HEIGHT / (numberEntity + 1);

        Button add = new Button();
        add.setPrefHeight(Math.min(height, 300));
        add.setPrefWidth(300);
        add.setText("add");
        add.setLayoutX(Variables.SCREEN_WIDTH - 300);
        add.setOnAction(new EntityButton(continent));

        Variables.root.getChildren().add(add);
        Button[] entityButton = new Button[numberEntity];

        for (int entityID = 0; entityID < numberEntity; entityID++) {
            entityButton[entityID] = new Button();
            entityButton[entityID].setPrefHeight(Math.min(height, 300));
            entityButton[entityID].setLayoutY((entityID + 1) * Math.min(height, 300));
            entityButton[entityID].setPrefWidth(300);
            entityButton[entityID].setText(nameOfEntity.get(entityID).name);
            entityButton[entityID].setLayoutX(Variables.SCREEN_WIDTH - 300);

            entityButton[entityID].setOnAction(new EntityDeleteButton(nameOfEntity.get(entityID), continent));
            Variables.root.getChildren().add(entityButton[entityID]);
        }
    }

    public static void rebuildEntityAddButtons(Location continent) {
        ArrayList<Entity> nameOfEntity = Variables.core.getEntityList();
        int numberEntity = nameOfEntity.size();
        double height = Variables.SCREEN_HEIGHT / numberEntity;

        Button[] entityButton = new Button[numberEntity];

        for (int entityID = 0; entityID < numberEntity; entityID++) {
            entityButton[entityID] = new Button();
            entityButton[entityID].setPrefHeight(height);
            entityButton[entityID].setLayoutY(entityID * height);
            entityButton[entityID].setPrefWidth(300);
            entityButton[entityID].setText(nameOfEntity.get(entityID).name);
            entityButton[entityID].setLayoutX(Variables.SCREEN_WIDTH - 300);

            entityButton[entityID].setOnAction(new EntityAddButton(nameOfEntity.get(entityID), continent));
            Variables.root.getChildren().add(entityButton[entityID]);
        }
    }

    public static void displayFactionPickButtons(int numberOfPlayers) {
        double thisHeight = Variables.SCREEN_HEIGHT / (Variables.NUMBER_OF_FACTIONS + 1);

        Label label = new Label("player " + 0);
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(Variables.SCREEN_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 40));
        Variables.root.getChildren().add(label);
        Button[] factionPickButtons = new Button[Variables.NUMBER_OF_FACTIONS];
        for (int factionId = 0; factionId < Variables.NUMBER_OF_FACTIONS; factionId++) {
            factionPickButtons[factionId] = new Button();
            factionPickButtons[factionId].setTextFill(Variables.COLOR_OF_FACTIONS[factionId]);
            factionPickButtons[factionId].setText(Variables.NAME_OF_FACTIONS[factionId]);
            factionPickButtons[factionId].setFont(Font.font("Arial", 40));
            factionPickButtons[factionId].setPrefHeight(thisHeight);
            factionPickButtons[factionId].setLayoutY((factionId + 1) * thisHeight);
            factionPickButtons[factionId].setPrefWidth(Variables.SCREEN_WIDTH);
            factionPickButtons[factionId]
                    .setOnAction(new FactionPickButton(numberOfPlayers, factionId, 0));

            Variables.root.getChildren().add(factionPickButtons[factionId]);
        }
    }

    public static void displayCommandButtons() {
        ArrayList<String> commandsAll = Variables.core.getCommandList();
        ArrayList<String> commands = new ArrayList<>();

        if (commandsAll.size() < Variables.NUMBER_OF_PERMUTATIONS) {
            for (int commandsID = 0; commandsID < commandsAll.size(); commandsID++) {
                commands.add(commandsAll.get(commandsID));
            }
        } else {
            Random random = new Random();

            while (commands.size() < Variables.NUMBER_OF_PERMUTATIONS) {
                int randomIndex = random.nextInt(commandsAll.size());
                String randomElement = commandsAll.get(randomIndex);

                if (!commands.contains(randomElement)) {
                    commands.add(randomElement);
                }
            }
        }

        double thisHeight = Variables.SCREEN_HEIGHT / (commands.size() + 1);
        Label label = new Label(Variables.core.getCommandDescription());
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(Variables.SCREEN_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 40));
        Variables.root.getChildren().add(label);

        Button[] commandButton = new Button[commands.size()];
        for (int commandID = 0; commandID < commands.size(); commandID++) {
            commandButton[commandID] = new Button();
            TextFlow textFlowFaction = new TextFlow();

            for (int faction = 0; faction < commands.get(commandID).length(); faction++) {
                int factionID = commands.get(commandID).charAt(faction) - '0';
                Text textEntity1 = new Text(Variables.NAME_OF_FACTIONS[factionID]);
                textEntity1.setFill(Variables.COLOR_OF_FACTIONS[factionID]);
                textEntity1.setFont(Font.font("Arial", 32));
                Text textEntity2 = new Text(" -> ");
                textEntity2.setFill(Color.BLACK);
                textEntity2.setFont(Font.font("Arial", 32));
                textFlowFaction.getChildren().add(textEntity1);
                if (faction != commands.get(commandID).length() - 1)
                    textFlowFaction.getChildren().add(textEntity2);
            }
            textFlowFaction.setTextAlignment(TextAlignment.CENTER);

            commandButton[commandID].setGraphic(textFlowFaction);
            commandButton[commandID].setPrefHeight(thisHeight);
            commandButton[commandID].setLayoutY((commandID + 1) * thisHeight);
            commandButton[commandID].setPrefWidth(Variables.SCREEN_WIDTH);
            commandButton[commandID].setOnAction(new CommandButton(commandID));

            Variables.root.getChildren().add(commandButton[commandID]);
        }
    }

    public static void getcommandButton() {
        ArrayList<String> commands = Variables.core.getCommandList();
        double thisHeight = Variables.SCREEN_HEIGHT / (commands.size() + 1);
        Label label = new Label(Variables.core.getCommandDescription());
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(Variables.SCREEN_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 40));
        Variables.root.getChildren().add(label);

        Button[] commandButton = new Button[commands.size()];
        for (int commandID = 0; commandID < commands.size(); commandID++) {
            commandButton[commandID] = new Button();
            TextFlow textFlowFaction = new TextFlow();

            for (int faction = 0; faction < commands.get(commandID).length(); faction++) {
                int factionID = commands.get(commandID).charAt(faction) - '0';
                Text textEntity1 = new Text(Variables.NAME_OF_FACTIONS[factionID]);
                textEntity1.setFill(Variables.COLOR_OF_FACTIONS[factionID]);
                textEntity1.setFont(Font.font("Arial", 32));
                Text textEntity2 = new Text(" -> ");
                textEntity2.setFill(Color.BLACK);
                textEntity2.setFont(Font.font("Arial", 32));
                textFlowFaction.getChildren().add(textEntity1);
                if (faction != commands.get(commandID).length() - 1)
                    textFlowFaction.getChildren().add(textEntity2);
            }
            textFlowFaction.setTextAlignment(TextAlignment.CENTER);

            commandButton[commandID].setGraphic(textFlowFaction);
            commandButton[commandID].setPrefHeight(thisHeight);
            commandButton[commandID].setLayoutY((commandID + 1) * thisHeight);
            commandButton[commandID].setPrefWidth(Variables.SCREEN_WIDTH);
            commandButton[commandID].setOnAction(new getCommandButton(commandID));

            Variables.root.getChildren().add(commandButton[commandID]);
        }
    }
}