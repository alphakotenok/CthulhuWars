package Model;

import Model.FactionEnum.FactionType;

class OpenerOfTheWay extends Faction {
    OpenerOfTheWay(String name, Core core) {
        super(name, core);
        faction = FactionType.OpenerOfTheWay;
    }
}
