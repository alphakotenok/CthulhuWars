package Model;

import Model.FactionEnum.FactionType;

class Windwalker extends Faction {
    Windwalker(String name, Core core) {
        super(name, core);
        faction = FactionType.Windwalker;
    }
}
