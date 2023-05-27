package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Model.Core;
import Model.Variables;
import Model.Faction.FactionType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Model.Core.InvalidFactionsSetException;
import Model.Core.InvalidNumOfPlayersException;

public class Visualizer {
    static Boolean zoogCheck = false;

    public static ImageView mapInitialization(int countOfPlayers) throws Exception {
        String mapName = "error.png";

        if (countOfPlayers == 2 || countOfPlayers == 3) {
            mapName = "images/maps/map2_3.jpg";
        } else if (countOfPlayers <= Variables.MAX_COUNT_OF_PLAYERS
                && countOfPlayers >= Variables.MIN_COUNT_OF_PLAYERS) {
            mapName = "images/maps/map" + countOfPlayers + ".jpg";
        } else {
            throw new Exception("too many players");
        }

        FileInputStream inputStream = new FileInputStream(mapName);
        Image map = new Image(inputStream);
        Variables.mapRatio = map.getWidth() / map.getHeight();

        ImageView mapView = new ImageView(map);
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        mapView.setY((Variables.SCREEN_HEIGHT - height) / 2);
        mapView.setFitWidth(Variables.SCREEN_WIDTH * Variables.PROCENT);
        mapView.setPreserveRatio(true);

        return mapView;
    }

    public static void initializeGameButtons(int countOfPlayers) throws FileNotFoundException {
        FileInputStream inputstream1 = new FileInputStream("Zoog.png");
        Image zoog = new Image(inputstream1);
        ImageView zoogView = new ImageView(zoog);
        zoogView.setX(200);

        Button[] gameButton = new Button[countOfPlayers];
        double weight = Variables.SCREEN_WIDTH * Variables.PROCENT / countOfPlayers;
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        for (int i = 0; i < countOfPlayers; i++) {
            gameButton[i] = new Button();
            gameButton[i].setPrefHeight((Variables.SCREEN_HEIGHT - height) / 2);
            gameButton[i].setLayoutX(i * weight);
            gameButton[i].setPrefWidth(weight);

            String logoName = "images/logo/Faction_" + Variables.NAME_OF_FACTIONS[i] + ".png";
            FileInputStream inputStream = new FileInputStream(logoName);
            Image logo = new Image(inputStream);
            ImageView logoView = new ImageView(logo);
            logoView.setFitHeight((Variables.SCREEN_HEIGHT - height) / 2);
            logoView.setFitWidth((Variables.SCREEN_HEIGHT - height) / 2);
            gameButton[i].setGraphic(logoView);

            gameButton[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    /// TODO: WHAT THE $!@#$ IS IT?
                    if (!zoogCheck) {
                        Variables.root.getChildren().add(zoogView);
                    } else {
                        Variables.root.getChildren().remove(zoogView);
                    }
                    zoogCheck = !zoogCheck;
                }
            });
            Variables.root.getChildren().add(gameButton[i]);
        }
    }

    public static void startGame() {
        Button startButton = new Button();
        startButton.setText("Start game");
        startButton.setPrefHeight(Variables.SCREEN_HEIGHT / 2);
        startButton.setPrefWidth(Variables.SCREEN_WIDTH);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Variables.root.getChildren().remove(startButton);
                ButtonVisualizer.displayCountOfPlayersButtons();
            }
        });
        Variables.root.getChildren().add(startButton);
    }

    public static void finishGame() {
        Button finishButton = new Button();
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        finishButton.setText("Finish game");
        finishButton.setPrefHeight((Variables.SCREEN_HEIGHT - height) / 2);
        finishButton.setLayoutY((Variables.SCREEN_HEIGHT - height) / 2 + height);
        finishButton.setPrefWidth(100);

        finishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Variables.root.getChildren().clear();
                startGame();
            }
        });
        Variables.root.getChildren().add(finishButton);
    }

    public static void createField(int numberOfPlayers) throws Exception {
        try {
            Variables.root.getChildren().add(mapInitialization(numberOfPlayers));
            initializeGameButtons(numberOfPlayers);
            finishGame();
            ButtonVisualizer.displayContinentButtons();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void startCore(int numberOfPlayers, ArrayList<FactionType> factionList) {
        try {
            Variables.core = new Core(numberOfPlayers, factionList);
        } catch (InvalidNumOfPlayersException e) {
            e.printStackTrace();
        } catch (InvalidFactionsSetException e) {
            e.printStackTrace();
        }
        Variables.numberOfPlayers = numberOfPlayers;
        ButtonVisualizer.displayCommandButtons();
    }
}
