package View;

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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import Model.Core.InvalidFactionsSetException;
import Model.Core.InvalidNumOfPlayersException;

public class Visualizer {
    static Boolean zoogCheck = false;

    public static ImageView mapInitialization(int countOfPlayers) throws FileNotFoundException, Exception {
        
        Image map = ImageMisc.getMapImage(countOfPlayers);
        Variables.mapRatio = map.getWidth() / map.getHeight();

        ImageView mapView = new ImageView(map);
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        mapView.setY((Variables.SCREEN_HEIGHT - height) / 2);
        mapView.setFitWidth(Variables.SCREEN_WIDTH * Variables.PROCENT);
        mapView.setPreserveRatio(true);

        return mapView;
    }

    public static void startGame() {
        for (int i = 0; i < Variables.NUMBER_OF_FACTIONS; i++) {
            Variables.factionSheetButtonState[i] = false;
        }

        try {
            ImageView imageView = ImageMisc.getGameIconImageView();
            imageView.setFitHeight(Variables.SCREEN_HEIGHT);
            imageView.setFitWidth(Variables.SCREEN_WIDTH);
            Variables.root.getChildren().add(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Button startButton = new Button();
        startButton.setText("Start game");
        startButton.setPrefHeight(100);
        startButton.setLayoutX(Variables.SCREEN_WIDTH - 300);
        startButton.setPrefWidth(300);
        startButton.setTextFill(Color.SILVER);
        startButton.setStyle("-fx-background-color: grey");

        startButton.setFont(Font.font("Arial", 40));
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Variables.root.getChildren().clear();
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
            ButtonVisualizer.initializeGameButtons(numberOfPlayers);
            finishGame();
            ButtonVisualizer.displayCommandButtons();
            // ButtonVisualizer.displayContinentButtons();
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
        ButtonVisualizer.displayOrderChooseButtons();
    }

    public static void displayfactionSheet(int factionID) throws FileNotFoundException {
        ImageView sheetView = ImageMisc.getFactionSheetImageView(factionID);
        Variables.root.getChildren().add(sheetView);
    }
}