package Model;

import java.io.FileInputStream;
import java.util.ArrayList;

import Model.Entity.EntityType;
import Model.Faction.FactionType;
import javafx.scene.image.Image;

public class EntityBase {

    ArrayList<Entity> entityList;

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
            addEntityToList("Acolyte (blue)", EntityType.cultist, FactionType.CrawlingChaos, 1, 0);
            addEntityToList("Nightgaunt", EntityType.monster, FactionType.CrawlingChaos, 1, 0);
            addEntityToList("Flying Polyp", EntityType.monster, FactionType.CrawlingChaos, 2, 2);
            addEntityToList("Hunting Horror", EntityType.monster, FactionType.CrawlingChaos, 3, 3);
            addEntityToList("Nyarlathotep", EntityType.GOO, FactionType.CrawlingChaos, 10, 0);
            return;
        }
        if (faction == FactionType.GreatCthulhu) {
            addEntityToList("Acolyte (green)", EntityType.cultist, FactionType.GreatCthulhu, 1, 0);
            addEntityToList("Deep One", EntityType.monster, FactionType.GreatCthulhu, 1, 1);
            addEntityToList("Shoggoth", EntityType.monster, FactionType.GreatCthulhu, 2, 2);
            addEntityToList("Star Spawn", EntityType.monster, FactionType.GreatCthulhu, 3, 3);
            addEntityToList("Cthulhu", EntityType.GOO, FactionType.GreatCthulhu, 4, 6);
            return;
        }
        if (faction == FactionType.BlackGoat) {
            addEntityToList("Acolyte (red)", EntityType.cultist, FactionType.BlackGoat, 1, 0);
            addEntityToList("Ghoul", EntityType.monster, FactionType.BlackGoat, 1, 0);
            addEntityToList("Fungi from Yuggoth", EntityType.monster, FactionType.BlackGoat, 2, 1);
            addEntityToList("Dark Young", EntityType.monster, FactionType.BlackGoat, 3, 2);
            addEntityToList("Shub-Niggurath", EntityType.GOO, FactionType.BlackGoat, 8, 0);
            return;
        }
        if (faction == FactionType.OpenerOfTheWay) {
            addEntityToList("Acolyte (purple)", EntityType.cultist, FactionType.OpenerOfTheWay, 1, 0);
            addEntityToList("Mutant", EntityType.monster, FactionType.OpenerOfTheWay, 2, 1);
            addEntityToList("Abomination", EntityType.monster, FactionType.OpenerOfTheWay, 3, 2);
            addEntityToList("Spawn of Yog-Sothoth", EntityType.monster, FactionType.OpenerOfTheWay, 4, 3);
            addEntityToList("Yog-Sothoth", EntityType.GOO, FactionType.OpenerOfTheWay, 6, 0);
            return;
        }
        if (faction == FactionType.Sleeper) {
            addEntityToList("Acolyte (orange)", EntityType.cultist, FactionType.Sleeper, 1, 0);
            addEntityToList("Wizard", EntityType.monster, FactionType.Sleeper, 1, 0);
            addEntityToList("Serpent Man", EntityType.monster, FactionType.Sleeper, 2, 1);
            addEntityToList("Formless Spawn", EntityType.monster, FactionType.Sleeper, 3, 0);
            addEntityToList("Tsathoggua", EntityType.GOO, FactionType.Sleeper, 8, 0);
            return;
        }
        if (faction == FactionType.Windwalker) {
            addEntityToList("Acolyte (white)", EntityType.cultist, FactionType.Windwalker, 1, 0);
            addEntityToList("Wendigo", EntityType.monster, FactionType.Windwalker, 1, 1);
            addEntityToList("Gnoph-Keh", EntityType.monster, FactionType.Windwalker, 0, 3);
            addEntityToList("Rhan Tegoth", EntityType.GOO, FactionType.Windwalker, 6, 3);
            addEntityToList("Ithaqua", EntityType.GOO, FactionType.Windwalker, 6, 0);
            return;
        }
        if (faction == FactionType.YellowSign) {
            addEntityToList("Acolyte (yellow)", EntityType.cultist, FactionType.YellowSign, 1, 0);
            addEntityToList("Undead", EntityType.monster, FactionType.YellowSign, 1, 0);
            addEntityToList("Byakhee", EntityType.monster, FactionType.YellowSign, 2, 0);
            addEntityToList("King in Yellow", EntityType.GOO, FactionType.YellowSign, 4, 0);
            addEntityToList("Hastur", EntityType.GOO, FactionType.YellowSign, 10, 0);
            return;
        }
    }

    EntityBase(ArrayList<FactionType> factions) {
        entityList = new ArrayList<>();
        for (FactionType faction : factions) {
            entityListInit(faction);
        }
    }
}
