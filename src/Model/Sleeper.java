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
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Wizard", Category.Monster, faction, entityIcons.get(1), 2, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Serpent Man", Category.Monster, faction, entityIcons.get(2), 3, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Formless Spawn", Category.Monster, faction, entityIcons.get(3), 4, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        entitySetsList.add(new EntitySet(core, "Tsathoggua", Category.GOO, faction, entityIcons.get(4), 1, EntitySet.constFunc(0), EntitySet.constFunc(0)));
        getEntitySetByName("Cultist").iconOnGate = Core.getImage("images/Entities/Gates with Acolyte (orange).png");
    }

    @Override
    void fillBooks() {
        books.add(new Book(0, FactionType.Sleeper, "Ancient Sorcery"));
        books.add(new Book(1, FactionType.Sleeper, "Burrow"));
        books.add(new Book(2, FactionType.Sleeper, "Capture Monster"));
        books.add(new Book(3, FactionType.Sleeper, "Cursed Slumber"));
        books.add(new Book(4, FactionType.Sleeper, "Demand Sacrifice"));
        books.add(new Book(5, FactionType.Sleeper, "Energy Nexus"));
    }
}