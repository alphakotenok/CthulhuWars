package Model;

import Model.FactionEnum.FactionType;

class BlackGoat extends Faction {
    BlackGoat(String name, Core core) {
        super(name, core);
        faction = FactionType.BlackGoat;
    }
}