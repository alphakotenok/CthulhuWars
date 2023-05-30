package Model;

import Model.FactionEnum.FactionType;

class YellowSign extends Faction {
    YellowSign(String name, Core core) {
        super(name, core);
        faction = FactionType.YellowSign;
    }
}
