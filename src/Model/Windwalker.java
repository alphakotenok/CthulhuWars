package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

class Windwalker extends Faction {
    Windwalker(String name, FactionType faction, Core core) {
        super(name, faction, core);
        ArrayList<Image> entityIcons = Faction.getEntityImages(new ArrayList<>(
                Arrays.asList("Acolyte (white)", "Wendigo", "Gnoph-Keh", "Rhan Tegoth", "Ithaqua")));
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6));
        entitySetsList.add(new EntitySet(core, "Wendigo", Category.Monster, faction, entityIcons.get(1), 4));
        entitySetsList.add(new EntitySet(core, "Gnoph-Keh", Category.Monster, faction, entityIcons.get(2), 4));
        entitySetsList.add(new EntitySet(core, "Rhan Tegoth", Category.GOO, faction, entityIcons.get(3), 1));
        entitySetsList.add(new EntitySet(core, "Ithaqua", Category.GOO, faction, entityIcons.get(4), 1));
        getEntitySetByName("Cultist").iconOnGate = Core.getImage("images/Entities/Gates with Acolyte (white).png");
    }
}