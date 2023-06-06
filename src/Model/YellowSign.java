package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

class YellowSign extends Faction {
    YellowSign(String name, FactionType faction, Core core) {
        super(name, faction, core);
        ArrayList<Image> entityIcons = Faction.getEntityImages(new ArrayList<>(
                Arrays.asList("Acolyte (yellow)", "Undead", "Byakhee", "King in Yellow", "Hastur")));
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6, EntitySet.constFunc(1), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Undead", Category.Monster, faction, entityIcons.get(1), 6, EntitySet.constFunc(1), YellowSign::getCombatUndead));
        entitySetsList.add(new EntitySet(core, "Byakhee", Category.Monster, faction, entityIcons.get(2), 4, EntitySet.constFunc(2), YellowSign::getCombatByakhee));
        entitySetsList.add(new EntitySet(core, "King in Yellow", Category.GOO, faction, entityIcons.get(3), 1, EntitySet.constFunc(4), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Hastur", Category.GOO, faction, entityIcons.get(4), 1, EntitySet.constFunc(10), YellowSign::getCombatHastur));
        getEntitySetByName("Cultist").iconOnGate = Core.getImage("images/Entities/Gates with Acolyte (yellow).png");
    }

    static int getCombatUndead(Core core) {
        ArrayList<Location> Zombies = core.factionBase.getFactionFromEnum(FactionType.BlackGoat).getEntitySetByName("Undead").positions;
        int combat = 0;
        for(int i = 0; i < Zombies.size(); i ++){
            if(Zombies.get(i) == core.var.battleLocation) combat ++;
        }
        return combat - 1;
    }

    static int getCombatByakhee(Core core) {
        ArrayList<Location> Zombies = core.factionBase.getFactionFromEnum(FactionType.BlackGoat).getEntitySetByName("Undead").positions;
        int combat = 0;
        for(int i = 0; i < Zombies.size(); i ++){
            if(Zombies.get(i) == core.var.battleLocation) combat ++;
        }
        return combat + 1;
    }

    static int getCombatHastur(Core core) {
        return core.ritual.ritualTrack.get(core.ritual.ritualState);
    }

    @Override
    void fillBooks() {
        books.add(new Book(0, FactionType.YellowSign, "He Who is Not to be Named"));
        books.add(new Book(1, FactionType.YellowSign, "Passion"));
        books.add(new Book(2, FactionType.YellowSign, "The Screaming Dead"));
        books.add(new Book(3, FactionType.YellowSign, "Shriek of the Byakhee"));
        books.add(new Book(4, FactionType.YellowSign, "The Third Eye"));
        books.add(new Book(5, FactionType.YellowSign, "Zingaya"));
    }
}