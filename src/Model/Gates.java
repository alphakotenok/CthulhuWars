package Model;

import java.util.ArrayList;

import Model.EntitySet.Category;
import javafx.scene.image.Image;

class Gates {

    private Core core;

    private class Gate {
        Location location;
        EntitySet controlledBy = null;

        Gate(Location location) {
            this.location = location;
        }
    }

    Image icon;

    Gates(Core core) {
        this.core = core;
    }

    private ArrayList<Gate> gateList = new ArrayList<>();

    boolean isGateInLocation(Location location) {
        for (Gate g : gateList) {
            if (g.location == location) {
                return true;
            }
        }
        return false;
    }

    boolean isGateControlled(Location location) {
        if (!isGateInLocation(location))
            return false;
        Gate g = getGate(location);
        return g.controlledBy != null;
    }

    private Gate getGate(Location location) {
        for (Gate g : gateList) {
            if (g.location == location) {
                return g;
            }
        }
        return null;
    }

    void buildGate(Location location) {
        if (!isGateInLocation(location))
            gateList.add(new Gate(location));
    }

    void destroyGate(Location location) {
        for (int i = 0; i < gateList.size(); ++i) {
            if (gateList.get(i).location == location) {
                gateList.remove(i);
                --i;
            }
        }
    }

    void setCotrol(Location location, EntitySet entity) {
        if (!isGateInLocation(location))
            return;
        Gate g = getGate(location);
        g.controlledBy = entity;
    }

    ArrayList<Location> getLocationsWithFreeGates() {
        ArrayList<Location> ans = new ArrayList<>();
        for (Gate g : gateList) {
            if (g.controlledBy == null) {
                ans.add(g.location);
            }
        }
        return ans;
    }

    EntitySet getGateController(Location location) {
        if (!isGateInLocation(location))
            return null;
        return getGate(location).controlledBy;
    }

    void checkGate(Location location) {
        if (!isGateInLocation(location)) {
            return;
        }
        Gate g = getGate(location);
        if (g.controlledBy == null) {
            Faction fact = core.getCurFact();
            for (EntitySet entity : fact.getEntitiesInLocation(location)) {
                if (entity.category == Category.Cultist) {
                    g.controlledBy = entity;
                    break;
                }
            }
        } else {
            if (g.controlledBy.countInLocation(location) == 0) {
                g.controlledBy = null;
                checkGate(location);
            }
        }
    }
}
