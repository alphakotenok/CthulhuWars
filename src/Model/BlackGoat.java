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
        entitySetsList.add(new EntitySet("Cultist", Category.Cultist, faction, entityIcons.get(0)));
        entitySetsList.add(new EntitySet("Ghoul", Category.Cultist, faction, entityIcons.get(1)));
        entitySetsList.add(new EntitySet("Fungi from Yuggoth", Category.Cultist, faction, entityIcons.get(2)));
        entitySetsList.add(new EntitySet("Dark Young", Category.Cultist, faction, entityIcons.get(3)));
        entitySetsList.add(new EntitySet("Shub-Niggurath", Category.Cultist, faction, entityIcons.get(4)));
    }
}
