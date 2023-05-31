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

    ArrayList<Location> positions = new ArrayList<>();

    EntitySet(String name, Category category, FactionType faction, Image icon) {
        this.name = name;
        this.category = category;
        this.faction = faction;
        this.icon = icon;
    }

    void move(Location from, Location to) {
        int index = positions.indexOf(from);
        if (index == -1) {
            System.out.println("Move Error");
            return;
        }
        positions.remove(index);
        positions.add(to);

        // TODO: check for the gates
    }

    int countInLocation(Location loc) {
        int ans = 0;
        for (Location l : positions) {
            if (l == loc) {
                ++ans;
            }
        }
        return ans;
    }

    void spawn(Location loc) {
        if (positions.size() == limit) {
            System.out.println("Spawn Error");
            return;
        }
        positions.add(loc);

        // TODO: check for the gates
    }

    void kill(Location loc) {
        int index = positions.indexOf(loc);
        if (index == -1) {
            System.out.println("Move Error");
            return;
        }
        positions.remove(index);

        // TODO: check for the gates
    }
}
