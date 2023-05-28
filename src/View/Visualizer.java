package View;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import Model.Core;
import Model.Variables;
import Model.Faction.FactionType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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

            initializeLabels();
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

    public static void initializeLabels() {
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        initializeLabel("Power", Variables.core.getPowerList(), (Variables.SCREEN_HEIGHT - height) / 2, 300, 100,
                (Variables.SCREEN_HEIGHT - height) / 2 + height);
        initializeLabel("Doom", Variables.core.getDoomList(), (Variables.SCREEN_HEIGHT - height) / 2, 300, 500,
                (Variables.SCREEN_HEIGHT - height) / 2 + height);
        initializeLabel("ElderSign", Variables.core.getElderSignList(), (Variables.SCREEN_HEIGHT - height) / 2, 300,
                900, (Variables.SCREEN_HEIGHT - height) / 2 + height);
        return;
    }

    public static void initializeLabel(String partName, ArrayList<Integer> list, double h, double w, double x,
            double y) {
        TextFlow textFlow = new TextFlow();
        Text text = new Text(partName + ": ");
        text.setFill(Color.BLACK);
        text.setFont(Font.font("Arial", 32));
        textFlow.getChildren().add(text);
        ArrayList<FactionType> order = Variables.core.getFactions();
        for (int i = 0; i < list.size(); i++) {
            Text Faction1 = new Text(String.valueOf(list.get(i)));
            Faction1.setFill(Variables.COLOR_OF_FACTIONS[order.get(i).ordinal()]);
            Faction1.setFont(Font.font("Arial", 32));
            textFlow.getChildren().add(Faction1);
            Text Faction2 = new Text("|");
            Faction2.setFill(Color.BLACK);
            Faction2.setFont(Font.font("Arial", 32));
            if (i != list.size() - 1)
                textFlow.getChildren().add(Faction2);
        }

        Label label = new Label();
        label.setId(partName + " label");
        label.setGraphic(textFlow);

        label.setPrefHeight(h);
        label.setLayoutY(y);
        label.setPrefWidth(w);
        label.setLayoutX(x);

        Variables.root.getChildren().add(label);
    }
}