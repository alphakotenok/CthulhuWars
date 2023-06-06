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
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Nightgaunt", Category.Monster, faction, entityIcons.get(1), 3, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Flying Polyp", Category.Monster, faction, entityIcons.get(2), 3, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Hunting Horror", Category.Monster, faction, entityIcons.get(3), 2, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Nyarlathotep", Category.GOO, faction, entityIcons.get(4), 1, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        getEntitySetByName("Cultist").iconOnGate = Core.getImage("images/Entities/Gates with Acolyte (blue).png");
    }

    @Override
    void fillBooks() {
       books.add(new Book(0, FactionType.CrawlingChaos, "Abduct"));
       books.add(new Book(1, FactionType.CrawlingChaos, "Emissary of the Outer Gods"));
       books.add(new Book(2, FactionType.CrawlingChaos, "Invisibility"));
       books.add(new Book(3, FactionType.CrawlingChaos, "Madness"));
       books.add(new Book(4, FactionType.CrawlingChaos, "Seek and Destroy"));
       books.add(new Book(5, FactionType.CrawlingChaos, "The Thousand Forms"));
    }
}