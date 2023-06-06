package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

class GreatCthulhu extends Faction {
    GreatCthulhu(String name, FactionType faction, Core core) {
        super(name, faction, core);
        ArrayList<Image> entityIcons = Faction.getEntityImages(new ArrayList<>(
                Arrays.asList("Acolyte (green)", "Deep One", "Shoggoth", "Star Spawn", "Cthulhu")));
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Deep One", Category.Monster, faction, entityIcons.get(1), 4, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Shoggoth", Category.Monster, faction, entityIcons.get(2), 4, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Star Spawn", Category.Monster, faction, entityIcons.get(3), 2, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Cthulhu", Category.GOO, faction, entityIcons.get(4), 1, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        getEntitySetByName("Cultist").iconOnGate = Core.getImage("images/Entities/Gates with Acolyte (green).png");
    }

    @Override
    void fillBooks() {
       books.add(new Book(0, FactionType.GreatCthulhu, "Absorb"));
       books.add(new Book(1, FactionType.GreatCthulhu, "Devolve"));
       books.add(new Book(2, FactionType.GreatCthulhu, "Dreams"));
       books.add(new Book(3, FactionType.GreatCthulhu, "Regenerate"));
       books.add(new Book(4, FactionType.GreatCthulhu, "Submerge"));
       books.add(new Book(5, FactionType.GreatCthulhu, "Y'ha Nthlei"));
    }
}