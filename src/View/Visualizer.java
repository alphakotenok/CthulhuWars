package View;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import Controler.FinishButton;
import Controler.MiscFunctions;
import Controler.StartButton;
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
import javafx.scene.text.TextFlow;
import Model.Core.Coordinates;

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

    public static void displayStartGameButton() {
        try {
            ImageView imageView = ImageMisc.getGameIconImageView();
            imageView.setFitHeight(Variables.SCREEN_HEIGHT);
            imageView.setFitWidth(Variables.SCREEN_WIDTH);
            ActionsMisc.display(imageView);
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
        startButton.setOnAction(new StartButton());
        ActionsMisc.display(startButton);
    }

    public static void displayFinishGameButton() {
        Button finishButton = new Button();
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        finishButton.setText("Finish game");
        finishButton.setPrefHeight((Variables.SCREEN_HEIGHT - height) / 2);
        finishButton.setLayoutY((Variables.SCREEN_HEIGHT - height) / 2 + height);
        finishButton.setPrefWidth(100);

        finishButton.setOnAction(new FinishButton());
        ActionsMisc.display(finishButton);
    }

    public static void createField(int numberOfPlayers) throws Exception {
        try {
            ActionsMisc.display(mapInitialization(numberOfPlayers));
            ButtonVisualizer.initializeGameButtons(numberOfPlayers);
            displayFinishGameButton();
            ButtonVisualizer.displayCommandButtons();
            displayRitualLabel();

            initializeLabels();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void displayFactionSheet(int factionID) throws FileNotFoundException {
        ImageView sheetView = ImageMisc.getFactionSheetImageView(factionID);
        ActionsMisc.display(sheetView);
        ButtonVisualizer.spellBookButton(factionID);
    }

    public static void displaySpellBookSheet(int factionID) throws FileNotFoundException {
        ImageView sheetView = ImageMisc.getSpellBookSheetImageView();
        ActionsMisc.display(sheetView);
    }

    public static void initializeLabels() {
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        displayLabel("Power", Variables.core.getPowerList(), (Variables.SCREEN_HEIGHT - height) / 2, 350, 125,
                (Variables.SCREEN_HEIGHT - height) / 2 + height);
        displayLabel("Doom", Variables.core.getDoomList(), (Variables.SCREEN_HEIGHT - height) / 2, 350, 500,
                (Variables.SCREEN_HEIGHT - height) / 2 + height);
        displayLabel("ElderSign", Variables.core.getElderSignList(), (Variables.SCREEN_HEIGHT - height) / 2, 350,
                875, (Variables.SCREEN_HEIGHT - height) / 2 + height);
        return;
    }

    public static void displayLabel(String partName, ArrayList<Integer> list, double h, double w, double x,
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

        ActionsMisc.display(label);
    }

    public static void displayRitualLabel() {
        ArrayList<Integer> ritualList = Variables.core.getRitualData();
        Label ritual = new Label(
                String.valueOf("Ritual: " + ritualList.get(0)) + "(" + String.valueOf(ritualList.get(1)) + "/"
                        + String.valueOf(ritualList.get(2)) + ")");
        ritual.setPrefWidth(Variables.SCREEN_WIDTH * Variables.PROCENT - 100);
        ritual.setLayoutY(Variables.SCREEN_HEIGHT - 60);
        ritual.setLayoutX(100);
        ritual.setAlignment(Pos.CENTER);
        ritual.setFont(Font.font("Arial", 32));
        ritual.setId("ritual");
        ritual.setTextFill(Color.BLACK);
        ActionsMisc.display(ritual);
    }

    public static void displayOpenBookSheet(int factionID) {
        ArrayList<Integer> openedBooks = Variables.core.getOpenedBookList(MiscFunctions.getFactionByID(factionID));
        ArrayList<Image> imageBooks = Variables.core.getBookImageList(MiscFunctions.getFactionByID(factionID));
        ArrayList<Coordinates> rightSpellBooksSheetCoordinates = Variables.core.getRightBookCoordinates();
        for (int i = 0; i < openedBooks.size(); i++) {
            if (!openedBooks.get(i).equals(-1)) {
                ImageView bookView = ImageMisc.getOpenedSpellBookImageView(imageBooks.get(i),
                        rightSpellBooksSheetCoordinates.get(openedBooks.get(i)));
                ActionsMisc.display(bookView);
            }
        }
    }

    public static void displayUnopenedBookSheet(int factionID) {
        ArrayList<Integer> unopenedBooks = Variables.core.getOpenedBookList(MiscFunctions.getFactionByID(factionID));
        ArrayList<Image> imageBooks = Variables.core.getBookImageList(MiscFunctions.getFactionByID(factionID));
        ArrayList<Coordinates> leftSpellBooksSheetCoordinates = Variables.core.getLeftBookCoordinates();
        for (int i = 0; i < unopenedBooks.size(); i++) {
            if (unopenedBooks.get(i).equals(-1)) {
                ImageView bookView = ImageMisc.getOpenedSpellBookImageView(imageBooks.get(i),
                        leftSpellBooksSheetCoordinates.get(i));
                ActionsMisc.display(bookView);
            }
        }
    }
}
