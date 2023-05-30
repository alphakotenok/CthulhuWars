package Model;

import Model.FactionEnum.FactionType;

class CrawlingChaos extends Faction {
    CrawlingChaos(String name, Core core) {
        super(name, core);
        faction = FactionType.CrawlingChaos;
    }
}