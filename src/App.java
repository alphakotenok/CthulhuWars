import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class App extends Application {
    public Boolean zoogCheck = false;
    Screen screen = Screen.getPrimary();
    Rectangle2D bounds = screen.getVisualBounds();
    final double screenWidth = bounds.getWidth();
    final double screenHeight = bounds.getHeight();
    final double procent = 0.8;
    Group root = new Group();
    double height;

    public ImageView mapInitialization(int countOfPlayers) throws Exception {
        String mapName = "error.png";

        if (countOfPlayers == 2 || countOfPlayers == 3) {
            mapName = "images/maps/map2_3.jpg";
        } else if (countOfPlayers <= 6 && countOfPlayers >= 1) {
            mapName = "images/maps/map" + countOfPlayers + ".jpg";
        } else {
            throw new Exception("too many players");
        }

        FileInputStream inputStream = new FileInputStream(mapName);
        Image map = new Image(inputStream);
        double mapRatio = map.getWidth() / map.getHeight();

        ImageView mapView = new ImageView(map);
        height = (screenHeight - screenWidth * procent / mapRatio);
        mapView.setY(height / 2);
        mapView.setFitHeight((screenWidth / 2) * procent);
        mapView.setPreserveRatio(true);

        return mapView;
    }

    public void buttons(int countOfPlayers) throws FileNotFoundException {
        FileInputStream inputstream1 = new FileInputStream("Zoog.png");
        Image zoog = new Image(inputstream1);
        ImageView zoogView = new ImageView(zoog);
        zoogView.setX(200);

        Button[] btn = new Button[countOfPlayers];
        double weight = screenWidth * procent / countOfPlayers;
        for (int i = 0; i < countOfPlayers; i++) {
            btn[i] = new Button();
            btn[i].setText("Player" + i);
            btn[i].setPrefHeight(height / 2);
            btn[i].setLayoutX(i * weight);
            btn[i].setPrefWidth(weight);
            root.getChildren().add(btn[i]);

            btn[i].setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    System.out.println("Hello");
                    if (!zoogCheck) {
                        root.getChildren().add(zoogView);
                    } else {
                        root.getChildren().remove(zoogView);
                    }
                    zoogCheck = !zoogCheck;

                }
            });
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            root.getChildren().add(mapInitialization(4));
            buttons(4);
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