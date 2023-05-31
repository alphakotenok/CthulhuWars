package Model;

import java.util.ArrayList;

import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

class EntitySet {

    @FunctionalInterface
    static interface intFunctionContainer {
        int activate(Core core);
    };

    enum Category {
        Cultist, Monster, GOO
    }

    String name;

    Category category;

    int limit;

    intFunctionContainer costFunc = (core -> 0);
    intFunctionContainer combatFunc = (core -> 0);

    FactionType faction;

    Image icon;
    Image iconOnGate;

    boolean isControlGate = false;

    ArrayList<Location> positions = new ArrayList<>();

    EntitySet(String name, Category category, FactionType faction, Image icon) {
        this.name = name;
        this.category = category;
        this.faction = faction;
        this.icon = icon;
    }
}
