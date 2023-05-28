package Model;

import java.util.ArrayList;

import Model.Faction.FactionType;

class FactionBase {
    ArrayList<Faction> factList;

    int totalSkip;

    FactionBase(Core core) {
        factList = new ArrayList<>();
        factList.add(new GreatCthulhu("GreatCthulhu", core));
        factList.add(new CrawlingChaos("CrawlingChaos", core));
        factList.add(new BlackGoat("BlackGoat", core));
        factList.add(new YellowSign("YellowSign", core));
        factList.add(new OpenerOfTheWay("OpenerOfTheWay", core));
        factList.add(new Sleeper("Sleeper", core));
        factList.add(new Windwalker("Windwalker", core));
        totalSkip = 0;
    }

    Faction getFactionFromEnum(FactionType faction) {
        return factList.get(faction.ordinal());
    }

    String getFactionNameFromEnum(FactionType faction) {
        return factList.get(faction.ordinal()).name;
    }
}
