package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Model.Location;
import Model.Variables;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Visualizer {
    static Boolean zoogCheck = false;
    static double mapRatio;

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
        mapRatio = map.getWidth() / map.getHeight();

        ImageView mapView = new ImageView(map);
        double height = (Variables.SCREEN_HEIGHT - Variables.SCREEN_WIDTH * Variables.PROCENT / mapRatio);
        mapView.setY(height / 2);
        mapView.setFitHeight((Variables.SCREEN_WIDTH / 2) * Variables.PROCENT);
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
        double height = (Variables.SCREEN_HEIGHT - Variables.SCREEN_WIDTH * Variables.PROCENT / mapRatio);
        for (int i = 0; i < countOfPlayers; i++) {
            gameButton[i] = new Button();
            gameButton[i].setPrefHeight(height / 2);
            gameButton[i].setLayoutX(i * weight);
            gameButton[i].setPrefWidth(weight);

            String logoName = "images/logo/Faction_" + Variables.NAME_OF_FRACTIONS[i] + ".png";
            FileInputStream inputStream = new FileInputStream(logoName);
            Image logo = new Image(inputStream);
            ImageView logoView = new ImageView(logo);
            logoView.setFitHeight(height / 2);
            logoView.setFitWidth(height / 2);
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

    public static void continentsButtons() {
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
            continentsButton[continentID].setText(continent[continentID]);
            continentsButton[continentID].setLayoutX(Variables.SCREEN_WIDTH - 300);

            continentsButton[continentID]
                    .setOnAction(new ContinentButton(Variables.core.getLocationsList().get(continentID)));
            Variables.root.getChildren().add(continentsButton[continentID]);
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
                ButtonVisualizer.chooseCountOfPlayers();
            }
        });
        Variables.root.getChildren().add(startButton);
    }

    public static void finishGame() {
        Button finishButton = new Button();
        double height = (Variables.SCREEN_HEIGHT - Variables.SCREEN_WIDTH * Variables.PROCENT / mapRatio);
        finishButton.setText("Finish game");
        finishButton.setPrefHeight(height / 2);
        finishButton.setLayoutY(Variables.SCREEN_HEIGHT - height / 2);
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
        } catch (Exception e) {
            throw e;
        }
    }
}
