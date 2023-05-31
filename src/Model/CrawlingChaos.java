package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;


class CrawlingChaos extends Faction {
    CrawlingChaos(String name, FactionType faction, Core core) {
        super(name, faction, core);
        ArrayList<Image> entityIcons = Faction.getEntityImages(new ArrayList<>(
                Arrays.asList("Acolyte (blue)", "Nightgaunt", "Flying Polyp", "Hunting Horror", "Nyarlathotep")));
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6));
        entitySetsList.add(new EntitySet(core, "Nightgaunt", Category.Monster, faction, entityIcons.get(1), 3));
        entitySetsList.add(new EntitySet(core, "Flying Polyp", Category.Monster, faction, entityIcons.get(2), 3));
        entitySetsList.add(new EntitySet(core, "Hunting Horror", Category.Monster, faction, entityIcons.get(3), 2));
        entitySetsList.add(new EntitySet(core, "Nyarlathotep", Category.GOO, faction, entityIcons.get(4), 1));
    }
}