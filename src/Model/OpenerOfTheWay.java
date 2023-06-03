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
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6));
        entitySetsList.add(new EntitySet(core, "Mutant", Category.Monster, faction, entityIcons.get(1), 4));
        entitySetsList.add(new EntitySet(core, "Abomination", Category.Monster, faction, entityIcons.get(2), 3));
        entitySetsList
                .add(new EntitySet(core, "Spawn of Yog-Sothoth", Category.Monster, faction, entityIcons.get(3), 2));
        entitySetsList.add(new EntitySet(core, "Yog-Sothoth", Category.GOO, faction, entityIcons.get(4), 1));
        getEntitySetByName("Cultist").iconOnGate = Core.getImage("images/Entities/Gates with Acolyte (purple).png");
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
}