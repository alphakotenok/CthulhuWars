package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Model.Entity.EntityType;
import Model.Faction.FactionType;
import Model.GameMap.Location;
import javafx.scene.image.Image;

public class Core {
    GameMap map;
    int numOfPlayers;

    ArrayList<Entity> entityList;

    private void addEntityToList(String name, EntityType entityType, FactionType faction, int cost, int combat) {

        String path = "images/Entities/" + name + ".png";
        try {
            FileInputStream fileStream = new FileInputStream(path);
            Image icon = new Image(fileStream);
            entityList.add(new Entity(name, icon, entityType, faction, cost, combat));
        } catch (Exception e) {
        }

    }

    private void entityListInit() {
        entityList = new ArrayList<>();
        addEntityToList("Acolyte (blue)", EntityType.cultist, FactionType.CrawlingChaos, 1, 0);
        addEntityToList("Nightgaunt", EntityType.monster, FactionType.CrawlingChaos, 1, 0);
        addEntityToList("Flying Polyp", EntityType.monster, FactionType.CrawlingChaos, 2, 2);
        addEntityToList("Hunting Horror", EntityType.monster, FactionType.CrawlingChaos, 3, 3);
        addEntityToList("Nyarlathotep", EntityType.GOO, FactionType.CrawlingChaos, 10, 0);
        addEntityToList("Acolyte (green)", EntityType.cultist, FactionType.GreatCthulhu, 1, 0);
        addEntityToList("Deep One", EntityType.monster, FactionType.GreatCthulhu, 1, 1);
        addEntityToList("Shoggoth", EntityType.monster, FactionType.GreatCthulhu, 2, 2);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.GreatCthulhu, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.GOO, FactionType.GreatCthulhu, 1, 0);
        addEntityToList("Acolyte (red)", EntityType.cultist, FactionType.BlackGoat, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.BlackGoat, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.BlackGoat, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.BlackGoat, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.GOO, FactionType.BlackGoat, 1, 0);
        addEntityToList("Acolyte (purple)", EntityType.cultist, FactionType.OpenerOfTheWay, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.OpenerOfTheWay, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.OpenerOfTheWay, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.OpenerOfTheWay, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.GOO, FactionType.OpenerOfTheWay, 1, 0);
        addEntityToList("Acolyte (orange)", EntityType.cultist, FactionType.Sleeper, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.Sleeper, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.Sleeper, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.Sleeper, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.GOO, FactionType.Sleeper, 1, 0);
        addEntityToList("Acolyte (white)", EntityType.cultist, FactionType.Windwalker, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.Windwalker, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.Windwalker, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.GOO, FactionType.Windwalker, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.GOO, FactionType.Windwalker, 1, 0);
        addEntityToList("Acolyte (yellow)", EntityType.cultist, FactionType.YellowSign, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.YellowSign, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.monster, FactionType.YellowSign, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.GOO, FactionType.YellowSign, 1, 0);
        addEntityToList("Acolyte (blue)", EntityType.GOO, FactionType.YellowSign, 1, 0);
    }

    public Core(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        map = new GameMap(numOfPlayers);
        entityListInit();
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
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
}
