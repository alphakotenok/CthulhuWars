package Model;

import java.util.ArrayList;

import javafx.scene.image.Image;

class Gates {
    private class Gate {
        Location location;
        boolean isControlled = false;

        Gate(Location location) {
            this.location = location;
        }
    }

    Image icon;

    Gates() {

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

    void setCotrol(Location location) {

    }

    ArrayList<Location> locationsWithFreeGates() {
        ArrayList<Location> ans = new ArrayList<>();
        for (Gate g : gateList) {
            if (!g.isControlled) {
                ans.add(g.location);
            }
        }
        return ans;
    }
}
