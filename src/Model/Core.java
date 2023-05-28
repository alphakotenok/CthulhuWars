package Model;

import java.util.ArrayList;

import Model.Faction.FactionType;
import javafx.scene.image.Image;

public class Core {
    GameMap map;
    EntityBase entityBase;
    int numOfPlayers;
    ArrayList<FactionType> factionsList;
    CommandTree ct;

    public class InvalidNumOfPlayersException extends Exception {

    }

    public class InvalidFactionsSetException extends Exception {

    }

    public Core(int numOfPlayers, ArrayList<FactionType> factions)
            throws InvalidNumOfPlayersException, InvalidFactionsSetException {
        if (numOfPlayers > GameMap.maxNumOfPlayers || numOfPlayers < GameMap.minNumOfPlayers) {
            throw new InvalidNumOfPlayersException();
        }
        if (factions.size() != numOfPlayers) {
            throw new InvalidFactionsSetException();
        }
        for (int i = 0; i < factions.size(); ++i) {
            for (int j = i + 1; j < factions.size(); ++j) {
                if (factions.get(i).equals(factions.get(j))) {
                    throw new InvalidFactionsSetException();
                }
            }
        }
        factionsList = new ArrayList<>();
        for (FactionType factionType : factions) {
            factionsList.add(factionType);
        }

        this.numOfPlayers = numOfPlayers;
        map = new GameMap(numOfPlayers, this);

        entityBase = new EntityBase(factionsList);
        ct = new CommandTree(this);
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

    public void addEntity(Location location, Entity entity) {
        location.entityList.add(entity);
    }

    public void deleteEntity(Location location, Entity entity) {
        location.entityList.remove(entity);
    }

    public Image getMapIcon() {
        return map.mapIcon;
    }

    public ArrayList<FactionType> getFactions() {
        return factionsList;
    }

    public ArrayList<String> getCommandList() {
        return ct.getCommandList();
    }

    public void activateCommand(Integer num) {
        try {
            ct.execute(num);
        } catch (Exception e) {

        }
    }
}
