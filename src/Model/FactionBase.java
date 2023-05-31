package Model;

import java.util.ArrayList;

import Model.FactionEnum.FactionType;

class FactionBase {
    ArrayList<Faction> factList;

    FactionBase(Core core) {
        factList = new ArrayList<>();
        factList.add(new GreatCthulhu("GreatCthulhu", FactionType.GreatCthulhu, core));
        factList.add(new CrawlingChaos("CrawlingChaos", FactionType.CrawlingChaos, core));
        factList.add(new BlackGoat("BlackGoat", FactionType.BlackGoat, core));
        factList.add(new YellowSign("YellowSign", FactionType.YellowSign, core));
        factList.add(new OpenerOfTheWay("OpenerOfTheWay", FactionType.OpenerOfTheWay, core));
        factList.add(new Sleeper("Sleeper", FactionType.Sleeper, core));
        factList.add(new Windwalker("Windwalker", FactionType.Windwalker, core));
    }

    Faction getFactionFromEnum(FactionType faction) {
        return factList.get(faction.ordinal());
    }

    String getFactionNameFromEnum(FactionType faction) {
        return factList.get(faction.ordinal()).name;
    }
}
