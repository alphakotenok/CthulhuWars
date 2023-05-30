package Model;

import Model.FactionEnum.FactionType;

class Sleeper extends Faction {
    Sleeper(String name, Core core) {
        super(name, core);
        faction = FactionType.Sleeper;
    }
}
