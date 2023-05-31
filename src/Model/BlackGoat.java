package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

class BlackGoat extends Faction {
    BlackGoat(String name, FactionType faction, Core core) {
        super(name, faction, core);
        ArrayList<Image> entityIcons = Faction.getEntityImages(new ArrayList<>(
                Arrays.asList("Acolyte (red)", "Ghoul", "Fungi from Yuggoth", "Dark Young", "Shub-Niggurath")));
        System.out.println(entityIcons.get(0));
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6));
        entitySetsList.add(new EntitySet(core, "Ghoul", Category.Cultist, faction, entityIcons.get(1), 2));
        entitySetsList.add(new EntitySet(core, "Fungi from Yuggoth", Category.Cultist, faction, entityIcons.get(2), 4));
        entitySetsList.add(new EntitySet(core, "Dark Young", Category.Cultist, faction, entityIcons.get(3), 3));
        entitySetsList.add(new EntitySet(core, "Shub-Niggurath", Category.Cultist, faction, entityIcons.get(4), 1));
    }
}
