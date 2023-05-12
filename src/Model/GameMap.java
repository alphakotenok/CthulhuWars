package Model;

import java.util.*;

import javafx.scene.image.Image;

public class GameMap {

    ArrayList<Location> locations;
    public Image mapIcon;

    GameMap(int numOfPlayers) {
        if (numOfPlayers < 2 || numOfPlayers > 6)
            return;
        // TODO: init locations
    }

    public static class Location {
        public String name;
        public ArrayList<Location> adj;
        public ArrayList<Entity> entityList;
    }

    ArrayList<Entity> getEntityListInLocation(Location location) {
        return location.entityList;
    }
}
