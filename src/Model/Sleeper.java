package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

class Sleeper extends Faction {
    Sleeper(String name, FactionType faction, Core core) {
        super(name, faction, core);
        ArrayList<Image> entityIcons = Faction.getEntityImages(new ArrayList<>(
                Arrays.asList("Acolyte (orange)", "Wizard", "Serpent Man", "Formless Spawn", "Tsathoggua")));
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6));
        entitySetsList.add(new EntitySet(core, "Wizard", Category.Monster, faction, entityIcons.get(1), 2));
        entitySetsList.add(new EntitySet(core, "Serpent Man", Category.Monster, faction, entityIcons.get(2), 3));
        entitySetsList.add(new EntitySet(core, "Formless Spawn", Category.Monster, faction, entityIcons.get(3), 4));
        entitySetsList.add(new EntitySet(core, "Tsathoggua", Category.GOO, faction, entityIcons.get(4), 1));
    }
}