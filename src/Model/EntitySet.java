package Model;

import java.util.ArrayList;

import Model.FactionEnum.FactionType;
import Model.GameVariables.PerformableAction;
import javafx.scene.image.Image;

public class EntitySet {

    @FunctionalInterface
    static interface IntFunctionContainer {
        int activate(Core core);
    };

    @FunctionalInterface
    static interface LocationFunctionContainer {
        ArrayList<Location> activate(Core core);
    }

    enum Category {
        Cultist, Monster, GOO
    }

    Core core;
    String name;
    Category category;
    int limit;

    ArrayList<Location> positions = new ArrayList<>();
    ArrayList<Location> moved = new ArrayList<>();

    IntFunctionContainer costFunc = (core -> 0);
    IntFunctionContainer combatFunc = (core -> 0);
    LocationFunctionContainer spawnPlace = (core -> core.gates.getControlledGates());

    static IntFunctionContainer constFunc(int num) {
        return (core -> num);
    }

    FactionType faction;
    Image icon;
    Image iconOnGate;

    EntitySet(Core core, String name, Category category, FactionType faction, Image icon, int limit, IntFunctionContainer costFunc, IntFunctionContainer combatFunc) {
        this.name = name;
        this.category = category;
        this.faction = faction;
        this.icon = icon;
        this.limit = limit;
        this.core = core;
        this.costFunc = costFunc;
        this.combatFunc = combatFunc;
    }

    void move(Location from, Location to) {
        int index = positions.indexOf(from);
        if (index == -1) {
            System.out.println("Move Error");
            return;
        }
        positions.remove(index);
        positions.add(to);
        core.gates.checkGate(from);
        core.gates.checkGate(to);
    }

    void performMovement(Location from, Location to) {
        move(from, to);
        moved.add(to);
        core.var.action = PerformableAction.Move;
    }

    void performMovementInjured(Location from, Location to) {
        move(from, to);
        moved.add(to);

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
        core.gates.checkGate(loc);
    }

    void kill(Location loc) {
        int index = positions.indexOf(loc);
        if (index == -1) {
            System.out.println("Kill Error");
            return;
        }
        positions.remove(index);
        core.gates.checkGate(loc);
    }

    ArrayList<Location> getEnableToMoveEnities() {
        ArrayList<Location> ans = new ArrayList<>();
        ArrayList<Location> toSubtitude = new ArrayList<>(moved);
        for (Location loc : positions) {
            int num = toSubtitude.indexOf(loc);
            if (num == -1) {
                ans.add(loc);
            } else {
                toSubtitude.remove(num);
            }
        }
        return ans;
    }

    void getCaptured(Location loc) {
        int index = positions.indexOf(loc);
        if (index == -1) {
            System.out.println("Capture Error");
            return;
        }
        positions.remove(index);
        core.gates.checkGate(loc);
        --limit;
    }

}
