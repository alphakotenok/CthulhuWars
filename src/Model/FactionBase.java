package Model;

import java.util.ArrayList;

import Model.Faction.FactionType;

class FactionBase {
    ArrayList<Faction> factList;

    FactionBase(Core core) {
        factList.add(new GreatCthulhu(core));
    }

    Faction getFactionFromEnum(FactionType faction) {
        return factList.get(faction.ordinal());
    }
}
