package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

non-sealed class Windwalker extends Faction {
    Windwalker(String name, FactionType faction, Core core) {
        super(name, faction, core);
        ArrayList<Image> entityIcons = Faction.getEntityImages(new ArrayList<>(
                Arrays.asList("Acolyte (white)", "Wendigo", "Gnoph-Keh", "Rhan Tegoth", "Ithaqua")));
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6,
                EntitySet.constFunc(1), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Wendigo", Category.Monster, faction, entityIcons.get(1), 4,
                EntitySet.constFunc(1), EntitySet.constFunc(1)));
        entitySetsList.add(new EntitySet(core, "Gnoph-Keh", Category.Monster, faction, entityIcons.get(2), 4,
                Windwalker::getCostGnophKeh, EntitySet.constFunc(3)));
        entitySetsList.add(new EntitySet(core, "Rhan Tegoth", Category.GOO, faction, entityIcons.get(3), 1,
                EntitySet.constFunc(6), EntitySet.constFunc(3)));
        entitySetsList.add(new EntitySet(core, "Ithaqua", Category.GOO, faction, entityIcons.get(4), 1,
                EntitySet.constFunc(6), Windwalker::getCombatIthaqua));
        getEntitySetByName("Cultist").iconOnGate = Core.getImage("images/Entities/Gates with Acolyte (white).png");
    }

    static int getCostGnophKeh(Core core) {
        return core.factionBase.getFactionFromEnum(FactionType.Windwalker).getEntitySetByName("Gnoph-Keh").limit
                - core.factionBase.getFactionFromEnum(FactionType.Windwalker).getEntitySetByName("Gnoph-Keh").positions
                        .size();
    }

    static int getCombatIthaqua(Core core) {
        return 0;
    }

    @Override
    void fillBooks() {
        books.add(new Book(0, FactionType.Windwalker, "Arctic Wind"));
        books.add(new Book(1, FactionType.Windwalker, "Berserkergang"));
        books.add(new Book(2, FactionType.Windwalker, "Cannibalism"));
        books.add(new Book(3, FactionType.Windwalker, "Herald of the Outer Gods"));
        books.add(new Book(4, FactionType.Windwalker, "Howl"));
        books.add(new Book(5, FactionType.Windwalker, "Ice Age"));
    }

    @Override
    boolean isQuestCompleted(int questNum) {
        return false;
    }
}