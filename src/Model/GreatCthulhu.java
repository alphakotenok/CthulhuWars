package Model;

import Model.FactionEnum.FactionType;

class GreatCthulhu extends Faction {
    GreatCthulhu(String name, Core core) {
        super(name, core);
        faction = FactionType.GreatCthulhu;
    }
}
