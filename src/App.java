import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import Model.Core;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {

    public Boolean zoogCheck = false;
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    final double SCREEN_WIDTH = bounds.getWidth();
    final double SCREEN_HEIGHT = bounds.getHeight();
    final double PROCENT = 0.8;
    final int MAX_COUNT_OF_PLAYERS = 6;
    final int MIN_COUNT_OF_PLAYERS = 2;
    final int NUMBER_OF_FRACTIONS = 7;
    Group root = new Group();
    double height;

    final String[] NAME_OF_FRACTIONS = { "GreatCthulhu", "CrawlingChaos", "BlackGoat", "YellowSign", "OpenerOfTheWay",
            "Sleeper", "Windwalker" };
    final Color[] COLOR_OF_FRACTIONS = { Color.GREEN, Color.BLUE, Color.MAROON, Color.GOLD, Color.PURPLE,
            Color.DARKORANGE, Color.DARKGREY };

    public ImageView mapInitialization(int countOfPlayers) throws Exception {
        String mapName = "error.png";

        if (countOfPlayers == 2 || countOfPlayers == 3) {
            mapName = "images/maps/map2_3.jpg";
        } else if (countOfPlayers <= MAX_COUNT_OF_PLAYERS && countOfPlayers >= MIN_COUNT_OF_PLAYERS) {
            mapName = "images/maps/map" + countOfPlayers + ".jpg";
        } else {
            throw new Exception("too many players");
        }

        FileInputStream inputStream = new FileInputStream(mapName);
        Image map = new Image(inputStream);
        double mapRatio = map.getWidth() / map.getHeight();

        ImageView mapView = new ImageView(map);
        height = (SCREEN_HEIGHT - SCREEN_WIDTH * PROCENT / mapRatio);
        mapView.setY(height / 2);
        mapView.setFitHeight((SCREEN_WIDTH / 2) * PROCENT);
        mapView.setPreserveRatio(true);

        return mapView;
    }

    public void initializeGameButtons(int countOfPlayers) throws FileNotFoundException {
        FileInputStream inputstream1 = new FileInputStream("Zoog.png");
        Image zoog = new Image(inputstream1);
        ImageView zoogView = new ImageView(zoog);
        zoogView.setX(200);

        Button[] gameButton = new Button[countOfPlayers];
        double width = SCREEN_WIDTH * PROCENT / countOfPlayers;
        for (int i = 0; i < countOfPlayers; i++) {
            gameButton[i] = new Button();
            gameButton[i].setPrefHeight(height / 2);
            gameButton[i].setLayoutX(i * width);
            gameButton[i].setPrefWidth(width);

            String logoName = "images/logo/Faction_" + NAME_OF_FRACTIONS[i] + ".png";
            FileInputStream inputStream = new FileInputStream(logoName);
            Image logo = new Image(inputStream);
            ImageView logoView = new ImageView(logo);
            logoView.setFitHeight(height / 2);
            logoView.setFitWidth(height / 2);
            gameButton[i].setGraphic(logoView);

            gameButton[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (!zoogCheck) {
                        root.getChildren().add(zoogView);
                    } else {
                        root.getChildren().remove(zoogView);
                    }
                    zoogCheck = !zoogCheck;
                }
            });
            root.getChildren().add(gameButton[i]);
        }
    }

    private void createField(int numberOfPlayers) throws Exception {
        try {
            root.getChildren().add(mapInitialization(numberOfPlayers));
            initializeGameButtons(numberOfPlayers);
            finishGame();
        } catch (Exception e) {
            throw e;
        }
    }

    private <T extends EventHandler<ActionEvent>> void removeButtons(Class<T> c) {
        List<Node> buttonsToRemove = new ArrayList<>();
        for (Node node : root.getChildren()) {
            if (node instanceof Button) {
                EventHandler<ActionEvent> onAction = ((Button) node).getOnAction();
                if (onAction != null && c.isInstance(onAction)) {
                    buttonsToRemove.add(node);
                }
            }
        }
        root.getChildren().removeAll(buttonsToRemove);
        return;
    }

    private void removeLabel(String text) {
        List<Node> labelsToRemove = new ArrayList<>();
        for (Node node : root.getChildren()) {
            if (node instanceof Label) {
                if (((Label) node).getText().equals(text)) {
                    labelsToRemove.add(node);
                }
            }
        }
        root.getChildren().removeAll(labelsToRemove);
        return;
    }

    private class FractionPickButtonActionHandler implements EventHandler<ActionEvent> {
        private int numberOfPlayers, playerID;
        public int fractionID;

        public FractionPickButtonActionHandler(int numberOfPlayers, int fractionID, int playerID) {
            this.numberOfPlayers = numberOfPlayers;
            this.playerID = playerID;
            this.fractionID = fractionID;
        }

        @Override
        public void handle(ActionEvent event) {
            /// TODO: we have to assign player with playerID its fraction id.
            rebuildFractionPickButtons(fractionID);

            if (playerID + 1 == numberOfPlayers) {
                removeButtons(FractionPickButtonActionHandler.class);
                removeLabel("player " + numberOfPlayers);
                try {
                    createField(numberOfPlayers);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        void nextPlayer() {
            playerID++;
        }
    }

    private void rebuildFractionPickButtons(int choosenFraction) {
        List<Node> nodeList = new ArrayList<>();
        List<Button> buttonList = new ArrayList<>();
        int playerID = 0;
        for (Node node : root.getChildren()) {
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
        root.getChildren().removeAll(nodeList);
        double thisHeight = SCREEN_HEIGHT / (NUMBER_OF_FRACTIONS + 1);

        removeLabel("player " + playerID);

        int fractionId = 0;

        for (Button fractionPickButton : buttonList) {
            FractionPickButtonActionHandler onAction = (FractionPickButtonActionHandler) fractionPickButton
                    .getOnAction();
            onAction.nextPlayer();

            fractionPickButton.setLayoutY((fractionId + 1) * thisHeight);
            root.getChildren().add(fractionPickButton);
            fractionId++;
        }

        Label label = new Label("player " + (playerID + 1));
        label.setPrefHeight(thisHeight);
        label.setPrefWidth(SCREEN_WIDTH);
        label.setAlignment(Pos.CENTER);
        label.setFont(Font.font("Arial", 40));
        root.getChildren().add(label);
        return;
    }

    private class MenuButtonActionHandler implements EventHandler<ActionEvent> {
        private int numberOfPlayers;

        public MenuButtonActionHandler(int value) {
            numberOfPlayers = value;
        }

        @Override
        public void handle(ActionEvent event) {
            removeButtons(MenuButtonActionHandler.class);

            Button[] fractionPickButtons = new Button[NUMBER_OF_FRACTIONS + 1];
            double thisHeight = SCREEN_HEIGHT / (NUMBER_OF_FRACTIONS + 1);

            Label label = new Label("player " + 0);
            label.setPrefHeight(thisHeight);
            label.setPrefWidth(SCREEN_WIDTH);
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font("Arial", 40));
            root.getChildren().add(label);

            for (int fractionId = 0; fractionId < NUMBER_OF_FRACTIONS; fractionId++) {
                fractionPickButtons[fractionId] = new Button();
                fractionPickButtons[fractionId].setTextFill(COLOR_OF_FRACTIONS[fractionId]);
                fractionPickButtons[fractionId].setText(NAME_OF_FRACTIONS[fractionId]);
                fractionPickButtons[fractionId].setFont(Font.font("Arial", 40));
                fractionPickButtons[fractionId].setPrefHeight(thisHeight);
                fractionPickButtons[fractionId].setLayoutY((fractionId + 1) * thisHeight);
                fractionPickButtons[fractionId].setPrefWidth(SCREEN_WIDTH);
                fractionPickButtons[fractionId]
                        .setOnAction(new FractionPickButtonActionHandler(numberOfPlayers, fractionId, 0));

                root.getChildren().add(fractionPickButtons[fractionId]);
            }
        }
    }

    public void chooseCountOfPlayers() {
        Button[] btnCountOfPlyers = new Button[MAX_COUNT_OF_PLAYERS - MIN_COUNT_OF_PLAYERS + 1];
        double thisHeight = SCREEN_HEIGHT / (MAX_COUNT_OF_PLAYERS - MIN_COUNT_OF_PLAYERS + 1);
        for (int countOfPlayers = MIN_COUNT_OF_PLAYERS; countOfPlayers <= MAX_COUNT_OF_PLAYERS; countOfPlayers++) {
            btnCountOfPlyers[countOfPlayers - MIN_COUNT_OF_PLAYERS] = new Button();
            btnCountOfPlyers[countOfPlayers - MIN_COUNT_OF_PLAYERS].setText(countOfPlayers + " players");
            btnCountOfPlyers[countOfPlayers - MIN_COUNT_OF_PLAYERS].setPrefHeight(thisHeight);
            btnCountOfPlyers[countOfPlayers - MIN_COUNT_OF_PLAYERS]
                    .setLayoutY((countOfPlayers - MIN_COUNT_OF_PLAYERS) * thisHeight);
            btnCountOfPlyers[countOfPlayers - MIN_COUNT_OF_PLAYERS].setPrefWidth(SCREEN_WIDTH);
            btnCountOfPlyers[countOfPlayers - MIN_COUNT_OF_PLAYERS]
                    .setOnAction(new MenuButtonActionHandler(countOfPlayers));

            root.getChildren().add(btnCountOfPlyers[countOfPlayers - MIN_COUNT_OF_PLAYERS]);
        }
    }

    public void startGame() {
        Button startButton = new Button();
        startButton.setText("Start game");
        startButton.setPrefHeight(SCREEN_HEIGHT / 2);
        startButton.setPrefWidth(SCREEN_WIDTH);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(startButton);
                chooseCountOfPlayers();
            }
        });
        root.getChildren().add(startButton);
    }

    public void finishGame() {
        Button finishButton = new Button();
        finishButton.setText("Finish game");
        finishButton.setPrefHeight(height / 2);
        finishButton.setLayoutY(SCREEN_HEIGHT - height / 2);
        finishButton.setPrefWidth(100);

        finishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().clear();
                startGame();
            }
        });
        root.getChildren().add(finishButton);
    }

    // public void buttonsOfContinents(){
    // ArrayList <String> continents = Core.getLocationsList(
    // }
    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            startGame();
            Scene scene = new Scene(root, Color.GREY);
            primaryStage.setTitle("CthulhuWars");
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}