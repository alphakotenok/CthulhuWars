package Controler;

import Model.Entity.EntityType;
import Model.Faction.FactionType;

public class MiscFunctions {
    public static EntityType getEntityByID(int entityID) {
        return EntityType.values()[entityID];
    }

    public static FactionType getFactionByID(int factionID) {
        return FactionType.values()[factionID];
    }
}
