package Model;

import java.util.ArrayList;

public class Location {
    public String name;
    public ArrayList<Location> adj = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();

    Location(String name) {

    }
}
