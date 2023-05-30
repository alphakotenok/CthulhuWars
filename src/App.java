import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import Controler.Game;
import Model.Variables;

public class App extends Application {
    static Screen screen = Screen.getPrimary();
    static Rectangle2D bounds = screen.getVisualBounds();

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Game.startGame();
            Scene scene = new Scene(Variables.root, Color.GREY);
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