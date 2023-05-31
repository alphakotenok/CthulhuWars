package Controler;

import Model.FactionEnum.FactionType;

public class MiscFunctions {

    public static FactionType getFactionByID(int factionID) {
        return FactionType.values()[factionID];
    }
}
