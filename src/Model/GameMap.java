package Model;

import java.util.*;

import javafx.scene.image.Image;

public class GameMap {

    ArrayList<Location> locations;
    Image mapIcon;
    static int maxNumOfPlayer = 2;
    static int minNumOfPlayers = 6;

    GameMap(int numOfPlayers) {
        if (numOfPlayers == 2) {
            return;
        }
    }

    ArrayList<Entity> getEntityListInLocation(Location location) {
        return location.entityList;
    }
}
