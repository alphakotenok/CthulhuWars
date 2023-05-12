package Model;

import java.util.ArrayList;

import Model.Faction.FactionType;
import javafx.scene.image.Image;

public class Core {
    GameMap map;
    EntityBase entityBase;
    int numOfPlayers;
    ArrayList<FactionType> factionsList;

    class InvalidNumOfPlayersException extends Exception {

    }

    class InvalidFactionsSetException extends Exception {

    }

    public Core(int numOfPlayers, FactionType[] factions)
            throws InvalidNumOfPlayersException, InvalidFactionsSetException {
        if (numOfPlayers > GameMap.maxNumOfPlayer || numOfPlayers < GameMap.minNumOfPlayers) {
            throw new InvalidNumOfPlayersException();
        }
        if (factions.length != numOfPlayers) {
            throw new InvalidFactionsSetException();
        }
        for (FactionType factionType : factions) {
            for (FactionType factionType2 : factions) {
                if (factionType.equals(factionType2)) {
                    throw new InvalidFactionsSetException();
                }
            }
        }
        factionsList = new ArrayList<>();
        for (FactionType factionType : factions) {
            factionsList.add(factionType);
        }

        this.numOfPlayers = numOfPlayers;
        map = new GameMap(numOfPlayers);

        entityBase = new EntityBase(factionsList);
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public ArrayList<Entity> getEntityList() {
        return entityBase.entityList;
    }

    public ArrayList<Location> getLocationsList() {
        return map.locations;
    }

    public ArrayList<Entity> getEntityListInLocation(Location location) {
        return map.getEntityListInLocation(location);
    }

    public void deleteEntity(Location location, Entity entity) {
        location.entityList.add(entity);
    }

    public void addEntity(Location location, Entity entity) {
        location.entityList.remove(entity);
    }

    public Image getMapIcon() {
        return map.mapIcon;
    }
}
