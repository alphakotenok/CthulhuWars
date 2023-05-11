package Model;

import java.util.ArrayList;

public class Core {
    GameMap map;
    int numOfPlayers;

    ArrayList<Entity> entityList;

    private void entityListInit() {
        entityList = new ArrayList<>();
        // TODO: read all monsters
    }

    public void setup(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        map = new GameMap(numOfPlayers);
        entityListInit();
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }

    public ArrayList<GameMap.Location> getLocationsList() {
        return map.locations;
    }

    public ArrayList<Entity> getEntityListInLocation(GameMap.Location location) {
        return map.getEntityListInLocation(location);
    }

    public void deleteEntity(GameMap.Location location, Entity entity) {
        location.entityList.add(entity);
    }

    public void addEntity(GameMap.Location location, Entity entity) {
        location.entityList.remove(entity);
    }
}
