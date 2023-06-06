package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

class OpenerOfTheWay extends Faction {
    OpenerOfTheWay(String name, FactionType faction, Core core) {
        super(name, faction, core);
        ArrayList<Image> entityIcons = Faction.getEntityImages(new ArrayList<>(
                Arrays.asList("Acolyte (purple)", "Mutant", "Abomination", "Spawn of Yog-Sothoth", "Yog-Sothoth")));
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6,
                EntitySet.constFunc(1), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Mutant", Category.Monster, faction, entityIcons.get(1), 4,
                EntitySet.constFunc(2), EntitySet.constFunc(1)));
        entitySetsList.add(new EntitySet(core, "Abomination", Category.Monster, faction, entityIcons.get(2), 3,
                EntitySet.constFunc(3), EntitySet.constFunc(2)));
        entitySetsList.add(new EntitySet(core, "Spawn of Yog-Sothoth", Category.Monster, faction, entityIcons.get(3), 2,
                EntitySet.constFunc(4), EntitySet.constFunc(3)));
        entitySetsList.add(new EntitySet(core, "Yog-Sothoth", Category.GOO, faction, entityIcons.get(4), 1,
                EntitySet.constFunc(6), OpenerOfTheWay::getCombatYogSothoth));
        getEntitySetByName("Cultist").iconOnGate = Core.getImage("images/Entities/Gates with Acolyte (purple).png");
    }

    static int getCombatYogSothoth(Core core) {
        int combat = 0;
        ArrayList<FactionType> factions = core.var.factionsList;
        for (FactionType faction : factions) {
            if (faction != FactionType.OpenerOfTheWay) {
                for (EntitySet entity : core.factionBase.getFactionFromEnum(faction).entitySetsList) {
                    if (entity.category == Category.GOO) {
                        combat++;
                    }
                }
            }
        }

        return combat * 2;
    }

    @Override
    void fillBooks() {
        books.add(new Book(0, FactionType.OpenerOfTheWay, "Channel Power"));
        books.add(new Book(1, FactionType.OpenerOfTheWay, "Dragon Ascending"));
        books.add(new Book(2, FactionType.OpenerOfTheWay, "Dragon Descending"));
        books.add(new Book(3, FactionType.OpenerOfTheWay, "Dread Curse of Azathoth"));
        books.add(new Book(4, FactionType.OpenerOfTheWay, "The Million Favored Ones"));
        books.add(new Book(5, FactionType.OpenerOfTheWay, "They Break Through"));
    }

    @Override
    boolean isQuestCompleted(int questNum) {
        if (questNum < 2) {
            int count = 0;
            for (Location location : core.map.locations) {
                if (core.gates.isGateControlled(location) == true) {
                    count++;
                }
            }
            if (getEntitySetByName("Yog-Sothoth").positions.size() != 0)
                count++;
            if (questNum == 0 && count >= 8)
                return true;
            if (questNum == 1 && count >= 12)
                return true;

        }

        if (questNum == 2) {
            int count = 0;
            for (Location location : core.map.locations) {
                if (core.gates.isGateControlled(location) == true
                        && core.gates.getGateController(location).faction != FactionType.OpenerOfTheWay
                        && getEntitiesInLocation(location).size() != 0) {
                    count++;
                }
            }
            if (count >= 2) {
                return true;
            }
        }

        if (questNum == 3) {

        }

        if (questNum == 4) {
            ArrayList<FactionType> factions = core.var.factionsList;
            for (FactionType faction : factions) {
                for (EntitySet entityOpenerOfTheWay : entitySetsList) {
                    if (entityOpenerOfTheWay.category == Category.GOO) {
                        for (Location locationOpenerOfTheWay : entityOpenerOfTheWay.positions) {
                            if (faction != FactionType.OpenerOfTheWay) {
                                for (EntitySet entity : core.factionBase.getFactionFromEnum(faction).entitySetsList) {
                                    if (entity.category == Category.GOO) {
                                        for (Location location : entity.positions) {
                                            if (location == locationOpenerOfTheWay)
                                                return true;
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }

        if (questNum == 5) {
            return (getEntitySetByName("Yog-Sothoth").positions.size() != 0);
        }
        return false;
    }
}