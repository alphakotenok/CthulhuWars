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
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6));
        entitySetsList.add(new EntitySet(core, "Undead", Category.Monster, faction, entityIcons.get(1), 6));
        entitySetsList.add(new EntitySet(core, "Byakhee", Category.Monster, faction, entityIcons.get(2), 4));
        entitySetsList.add(new EntitySet(core, "King in Yellow", Category.GOO, faction, entityIcons.get(3), 1));
        entitySetsList.add(new EntitySet(core, "Hastur", Category.GOO, faction, entityIcons.get(4), 1));
        getEntitySetByName("Cultist").iconOnGate = Core.getImage("images/Entities/Gates with Acolyte (yellow).png");
    }
}