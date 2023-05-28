package Model;

import java.io.FileInputStream;
import java.util.ArrayList;

import Model.Entity.EntityType;
import Model.Faction.FactionType;
import javafx.scene.image.Image;

public class EntityBase {

    ArrayList<Entity> entityList;
    int neutralGateExists;

    void addEntityToList(String name, EntityType entityType, FactionType faction, int cost, int combat) {

        String path = "images/Entities/" + name + ".png";
        try {
            FileInputStream fileStream = new FileInputStream(path);
            Image icon = new Image(fileStream);
            entityList.add(new Entity(name, icon, entityType, faction, cost, combat));
        } catch (Exception e) {
        }

    }

    void entityListInit(FactionType faction) {
        if (faction == FactionType.CrawlingChaos) {
            addEntityToList("Acolyte (blue)", EntityType.Cultist, FactionType.CrawlingChaos, 1, 0);
            addEntityToList("Nightgaunt", EntityType.Monster, FactionType.CrawlingChaos, 1, 0);
            addEntityToList("Flying Polyp", EntityType.Monster, FactionType.CrawlingChaos, 2, 2);
            addEntityToList("Hunting Horror", EntityType.Monster, FactionType.CrawlingChaos, 3, 3);
            addEntityToList("Nyarlathotep", EntityType.GOO, FactionType.CrawlingChaos, 10, 0);
            return;
        }
        if (faction == FactionType.GreatCthulhu) {
            addEntityToList("Acolyte (green)", EntityType.Cultist, FactionType.GreatCthulhu, 1, 0);
            addEntityToList("Deep One", EntityType.Monster, FactionType.GreatCthulhu, 1, 1);
            addEntityToList("Shoggoth", EntityType.Monster, FactionType.GreatCthulhu, 2, 2);
            addEntityToList("Star Spawn", EntityType.Monster, FactionType.GreatCthulhu, 3, 3);
            addEntityToList("Cthulhu", EntityType.GOO, FactionType.GreatCthulhu, 4, 6);
            return;
        }
        if (faction == FactionType.BlackGoat) {
            addEntityToList("Acolyte (red)", EntityType.Cultist, FactionType.BlackGoat, 1, 0);
            addEntityToList("Ghoul", EntityType.Monster, FactionType.BlackGoat, 1, 0);
            addEntityToList("Fungi from Yuggoth", EntityType.Monster, FactionType.BlackGoat, 2, 1);
            addEntityToList("Dark Young", EntityType.Monster, FactionType.BlackGoat, 3, 2);
            addEntityToList("Shub-Niggurath", EntityType.GOO, FactionType.BlackGoat, 8, 0);
            return;
        }
        if (faction == FactionType.OpenerOfTheWay) {
            addEntityToList("Acolyte (purple)", EntityType.Cultist, FactionType.OpenerOfTheWay, 1, 0);
            addEntityToList("Mutant", EntityType.Monster, FactionType.OpenerOfTheWay, 2, 1);
            addEntityToList("Abomination", EntityType.Monster, FactionType.OpenerOfTheWay, 3, 2);
            addEntityToList("Spawn of Yog-Sothoth", EntityType.Monster, FactionType.OpenerOfTheWay, 4, 3);
            addEntityToList("Yog-Sothoth", EntityType.GOO, FactionType.OpenerOfTheWay, 6, 0);
            return;
        }
        if (faction == FactionType.Sleeper) {
            addEntityToList("Acolyte (orange)", EntityType.Cultist, FactionType.Sleeper, 1, 0);
            addEntityToList("Wizard", EntityType.Monster, FactionType.Sleeper, 1, 0);
            addEntityToList("Serpent Man", EntityType.Monster, FactionType.Sleeper, 2, 1);
            addEntityToList("Formless Spawn", EntityType.Monster, FactionType.Sleeper, 3, 0);
            addEntityToList("Tsathoggua", EntityType.GOO, FactionType.Sleeper, 8, 0);
            return;
        }
        if (faction == FactionType.Windwalker) {
            addEntityToList("Acolyte (white)", EntityType.Cultist, FactionType.Windwalker, 1, 0);
            addEntityToList("Wendigo", EntityType.Monster, FactionType.Windwalker, 1, 1);
            addEntityToList("Gnoph-Keh", EntityType.Monster, FactionType.Windwalker, 0, 3);
            addEntityToList("Rhan Tegoth", EntityType.GOO, FactionType.Windwalker, 6, 3);
            addEntityToList("Ithaqua", EntityType.GOO, FactionType.Windwalker, 6, 0);
            return;
        }
        if (faction == FactionType.YellowSign) {
            addEntityToList("Acolyte (yellow)", EntityType.Cultist, FactionType.YellowSign, 1, 0);
            addEntityToList("Undead", EntityType.Monster, FactionType.YellowSign, 1, 0);
            addEntityToList("Byakhee", EntityType.Monster, FactionType.YellowSign, 2, 0);
            addEntityToList("King in Yellow", EntityType.GOO, FactionType.YellowSign, 4, 0);
            addEntityToList("Hastur", EntityType.GOO, FactionType.YellowSign, 10, 0);
            return;
        }
        neutralGateExists = 0;
    }

    EntityBase(ArrayList<FactionType> factions) {
        entityList = new ArrayList<>();
        for (FactionType faction : factions) {
            entityListInit(faction);
        }
    }

    ArrayList<Entity> getUnitsOfFaction(FactionType faction) {
        return null;
    }
}
