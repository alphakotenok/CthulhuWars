package Model;

import javafx.scene.image.Image;

public class Entity {

    public enum EntityType {
        cultist, monster, GOO, none
    }

    public String name;
    public Image icon;
    public EntityType entityType;
    public Faction.FactionType faction;
    public int cost;
    public int combat;

    public Entity(String name, Image icon, EntityType entityType, Faction.FactionType faction, int cost, int combat) {
        this.name = name;
        this.icon = icon;
        this.entityType = entityType;
        this.faction = faction;
        this.cost = cost;
        this.combat = combat;
    }

}