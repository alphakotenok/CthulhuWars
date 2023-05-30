package Model;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

public class Variables {
        public static final double SCREEN_WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
        public static final double SCREEN_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
        public static final double PROCENT = 0.8;
        public static final int MAX_COUNT_OF_PLAYERS = 6;
        public static final int MIN_COUNT_OF_PLAYERS = 2;
        public static final int NUMBER_OF_FACTIONS = 7;
        public static final int NUMBER_OF_PERMUTATIONS = 15;
        public static Group root = new Group();
        public static Core core;
        public static double mapRatio;
        public final static String[] NAME_OF_FACTIONS = { "GreatCthulhu", "CrawlingChaos", "BlackGoat", "YellowSign",
                        "OpenerOfTheWay",
                        "Sleeper", "Windwalker" };
        public final static Color[] COLOR_OF_FACTIONS = { Color.GREEN, Color.BLUE, Color.MAROON, Color.GOLD,
                        Color.PURPLE,
                        Color.DARKORANGE, Color.DARKGREY };
        public static int numberOfPlayers;
}
