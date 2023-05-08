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

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        final double screenWidth = bounds.getWidth();
        final double screenHeight = bounds.getHeight();
        final double procent = 0.8;

        Button btnZoog = new Button();
        btnZoog.setText("Zoog");
        btnZoog.setLayoutX(10);
        btnZoog.setLayoutY(10);
        btnZoog.setPrefWidth(200);
        btnZoog.setPrefHeight(100);

        FileInputStream inputstream = new FileInputStream("C:/Users/Julia Tatarinova/Desktop/java/map.png");
        Image map = new Image(inputstream);
        final double mapRatio = map.getWidth() / map.getHeight();

        ImageView mapView = new ImageView(map);
        mapView.setY((screenHeight - screenWidth * procent / mapRatio) / 2);
        mapView.setFitHeight((screenWidth / 2) * procent);
        mapView.setPreserveRatio(true);

        Group root = new Group();
        root.getChildren().add(mapView);
        root.getChildren().add(btnZoog);

        FileInputStream inputstream1 = new FileInputStream("C:/Users/Julia Tatarinova/Desktop/java/zoog.png");
        Image zoog = new Image(inputstream1);
        ImageView zoogView = new ImageView(zoog);
        zoogView.setX(200);

        btnZoog.setOnAction(new EventHandler<ActionEvent>() {

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

        /*
         * Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
         * Scene scene = new Scene(root);
         */

        Scene scene = new Scene(root, Color.GREY);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}