package Model;

import javafx.scene.image.Image;

public class Entity {
    public static enum EntityType {
        cultist, monster, GOO, none
    };

    public final EntityType entityType;
    public final Image icon;
    public final Factions.Faction faction;
    public final String name;

    public Entity() {
        entityType = EntityType.none;
        icon = null;
        faction = Factions.Faction.none;
        name = "";
    }

    public int getCost() {
        return 0;
    }

    public int getCombat() {
        return 0;
    }
}