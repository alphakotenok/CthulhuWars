package Model;

import Model.Faction.FactionType;
import javafx.scene.image.Image;

public class Entity {

    public enum EntityType {
        Cultist, Monster, GOO, GateWith, None
    }

    public String name;
    public Image icon;
    public EntityType entityType;
    public Faction.FactionType faction;
    public int cost;
    public int combat;
    Entity gateOwner;

    public Entity(String name, Image icon, EntityType entityType, FactionType faction, int cost, int combat) {
        this.name = name;
        this.icon = icon;
        this.entityType = entityType;
        this.faction = faction;
        this.cost = cost;
        this.combat = combat;
    }

}