package Model;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import Model.FactionEnum.FactionType;
import Model.Location.Segment;
import javafx.scene.image.Image;

class GameMap {
    
    ArrayList<Location> locations;
    Image mapIcon;
    Core core;
    ArrayList<ArrayList<Location>> startLoc = new ArrayList<>();

    private static final Type ARRAY_LIST_SEGMENT_TYPE = new TypeToken<ArrayList<Segment>>() {}.getType();
    private ArrayList<Segment> getSegmentsFromJSON(String filePath){
        try (JsonReader reader = new JsonReader(new FileReader(filePath))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, ARRAY_LIST_SEGMENT_TYPE);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static final Type ARRAY_LIST_STRING_TYPE = new TypeToken<ArrayList<String>>() {}.getType();
    private ArrayList<String> getStringsFromJSON(String filePath){
        try (JsonReader reader = new JsonReader(new FileReader(filePath))) {
            Gson gson = new Gson();
            return gson.fromJson(reader, ARRAY_LIST_STRING_TYPE);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    Location Europe             = new Location("Europe",                getSegmentsFromJSON("static/LocationSegments/Europe.json"));
    Location ArcticOcean        = new Location("Arctic Ocean",          getSegmentsFromJSON("static/LocationSegments/ArcticOcean.json"));
    Location NorthAmerica       = new Location("North America",         getSegmentsFromJSON("static/LocationSegments/NorthAmerica.json"));
    Location NorthPacific       = new Location("North Pacific",         getSegmentsFromJSON("static/LocationSegments/NorthPacific.json"));
    Location NorthAtlantic      = new Location("North Atlantic",        getSegmentsFromJSON("static/LocationSegments/NorthAtlantic.json"));
    Location Asia               = new Location("Asia",                  getSegmentsFromJSON("static/LocationSegments/Asia.json"));
    Location IndianOcean        = new Location("Indian Ocean",          getSegmentsFromJSON("static/LocationSegments/IndianOcean.json"));
    Location Australia          = new Location("Australia",             getSegmentsFromJSON("static/LocationSegments/Australia.json"));
    Location SouthPacific       = new Location("South Pacific",         getSegmentsFromJSON("static/LocationSegments/SouthPacific.json"));
    Location SouthAmerica       = new Location("South America",         getSegmentsFromJSON("static/LocationSegments/SouthAmerica.json"));
    Location SouthAtlantic      = new Location("South Atlantic",        getSegmentsFromJSON("static/LocationSegments/SouthAtlantic.json"));
    Location Antarctica         = new Location("Antarctica",            getSegmentsFromJSON("static/LocationSegments/Antarctica.json"));
    Location Africa             = new Location("Africa",                getSegmentsFromJSON("static/LocationSegments/Africa.json"));
    Location EuropeSmall        = new Location("Europe",                getSegmentsFromJSON("static/LocationSegments/EuropeSmall.json"));
    Location Scandinavia        = new Location("Scandinavia",           getSegmentsFromJSON("static/LocationSegments/Scandinavia.json"));
    Location NorthAmericaEast   = new Location("North America East",    getSegmentsFromJSON("static/LocationSegments/NorthAmericaEast.json"));
    Location ArcticOceanSmall   = new Location("Arctic Ocean",          getSegmentsFromJSON("static/LocationSegments/ArcticOceanSmall.json"));
    Location BeringSea          = new Location("Bering Sea",            getSegmentsFromJSON("static/LocationSegments/BeringSea.json"));
    Location NorthAmericaWest   = new Location("North America West",    getSegmentsFromJSON("static/LocationSegments/NorthAmericaWest.json"));
    Location CentralAmerica     = new Location("Central America",       getSegmentsFromJSON("static/LocationSegments/CentralAmerica.json"));
    Location NorthAtlanticSmall = new Location("North Atlantic",        getSegmentsFromJSON("static/LocationSegments/NorthAtlanticSmall.json"));
    Location MediterraneanSea   = new Location("Mediterranean Sea",     getSegmentsFromJSON("static/LocationSegments/MediterraneanSea.json"));
    Location NorthAsia          = new Location("North Asia",            getSegmentsFromJSON("static/LocationSegments/NorthAsia.json"));
    Location Arabia             = new Location("Arabia",                getSegmentsFromJSON("static/LocationSegments/Arabia.json"));
    Location SouthAsia          = new Location("South Asia",            getSegmentsFromJSON("static/LocationSegments/SouthAsia.json"));
    Location AustraliaSmall     = new Location("Australia",             getSegmentsFromJSON("static/LocationSegments/AustraliaSmall.json"));
    Location NewZealand         = new Location("New Zealand",           getSegmentsFromJSON("static/LocationSegments/NewZealand.json"));
    Location SouthAmericaEast   = new Location("South America East",    getSegmentsFromJSON("static/LocationSegments/SouthAmericaEast.json"));
    Location SouthAmericaWest   = new Location("South America West",    getSegmentsFromJSON("static/LocationSegments/SouthAmericaWest.json"));
    Location AntarcticaSmall    = new Location("Antarctica",            getSegmentsFromJSON("static/LocationSegments/AntarcticaSmall.json"));
    Location MountainsofMadness = new Location("Mountains of Madness",  getSegmentsFromJSON("static/LocationSegments/MountainsofMadness.json"));
    Location WestAfrica         = new Location("West Africa",           getSegmentsFromJSON("static/LocationSegments/WestAfrica.json"));
    Location EastAfrica         = new Location("East Africa",           getSegmentsFromJSON("static/LocationSegments/EastAfrica.json"));

    private void addAdj(Location location, ArrayList<String> adj){
        for(String locationName : adj){
            String variableName = locationName;
            variableName = variableName.replace(" ", "");
            Field field;
            try {
                field = GameMap.class.getDeclaredField(variableName);
                field.setAccessible(true);
                location.adj.add((Location)field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillAdj(int numOfPlayers) {
        addAdj(Europe,              getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Europe.json"));
        addAdj(ArcticOcean,         getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Arctic Ocean.json"));
        addAdj(NorthAmerica,        getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/North America.json"));
        addAdj(NorthPacific,        getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/North Pacific.json"));
        addAdj(NorthAtlantic,       getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/North Atlantic.json"));
        addAdj(Asia,                getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Asia.json"));
        addAdj(IndianOcean,         getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Indian Ocean.json"));
        addAdj(Australia,           getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Australia.json"));
        addAdj(SouthPacific,        getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/South Pacific.json"));
        addAdj(SouthAmerica,        getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/South America.json"));
        addAdj(SouthAtlantic,       getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/South Atlantic.json"));
        addAdj(Antarctica,          getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Antarctica.json"));
        addAdj(Africa,              getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Africa.json"));
        addAdj(EuropeSmall,         getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Europe Small.json"));
        addAdj(Scandinavia,         getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Scandinavia.json"));
        addAdj(NorthAmericaEast,    getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/North America East.json"));
        addAdj(ArcticOceanSmall,    getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Arctic Ocean Small.json"));
        addAdj(BeringSea,           getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Bering Sea.json"));
        addAdj(NorthAmericaWest,    getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/North America West.json"));
        addAdj(CentralAmerica,      getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Central America.json"));
        addAdj(NorthAtlanticSmall,  getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/North Atlantic Small.json"));
        addAdj(MediterraneanSea,    getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Mediterranean Sea.json"));
        addAdj(NorthAsia,           getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/North Asia.json"));
        addAdj(Arabia,              getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Arabia.json"));
        addAdj(SouthAsia,           getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/South Asia.json"));
        addAdj(AustraliaSmall,      getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Australia Small.json"));
        addAdj(NewZealand,          getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/New Zealand.json"));
        addAdj(SouthAmericaEast,    getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/South America East.json"));
        addAdj(SouthAmericaWest,    getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/South America West.json"));
        addAdj(AntarcticaSmall,     getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Antarctica Small.json"));
        addAdj(MountainsofMadness,  getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/Mountains of Madness.json"));
        addAdj(WestAfrica,          getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/West Africa.json"));
        addAdj(EastAfrica,          getStringsFromJSON("static/AdjLocations/"+numOfPlayers+"Players/East Africa.json"));

    }
    
    private void fillLocationList(int numOfPlayers) {
        for(String locationName : getStringsFromJSON("static/LocationList/"+numOfPlayers+"Players.json")){
            String variableName = locationName;
            variableName = variableName.replace(" ", "");
            Field field;
            try {
                field = GameMap.class.getDeclaredField(variableName);
                field.setAccessible(true);
                locations.add((Location) field.get(this));
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    public GameMap(Core core) {
        this.core = core;
        int numOfPlayers = core.var.numOfPlayers;
        locations = new ArrayList<>();
        fillAdj(numOfPlayers);
        fillLocationList(numOfPlayers);
        fillStartLocArray();
    }


    void fillStartLocArray() {
        ArrayList<Location> GreatCthulhu = new ArrayList<>();
        ArrayList<Location> CrawlingChaos = new ArrayList<>();
        ArrayList<Location> BlackGoat = new ArrayList<>();
        ArrayList<Location> YellowSign = new ArrayList<>();
        ArrayList<Location> OpenerOfTheWay = new ArrayList<>();
        ArrayList<Location> Sleeper = new ArrayList<>();
        ArrayList<Location> Windwalker = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            String s = locations.get(i).name;
            if (s.equals("South Pacific") && core.var.factionsList.indexOf(FactionType.GreatCthulhu) != -1)
                GreatCthulhu.add(locations.get(i));
            else if ((s.equals("Asia") || s.equals("South Asia"))
                    && core.var.factionsList.indexOf(FactionType.CrawlingChaos) != -1)
                CrawlingChaos.add(locations.get(i));
            else if ((s.equals("Africa") || s.equals("West Africa"))
                    && core.var.factionsList.indexOf(FactionType.BlackGoat) != -1)
                BlackGoat.add(locations.get(i));
            else if (s.equals("Europe") && core.var.factionsList.indexOf(FactionType.YellowSign) != -1)
                YellowSign.add(locations.get(i));
            else if ((s.equals("North America") || s.equals("North America West"))
                    && core.var.factionsList.indexOf(FactionType.Sleeper) != -1)
                Sleeper.add(locations.get(i));
            else if ((s.equals("Arctic Ocean") || (s.equals("Antarctica") && core.var.numOfPlayers < 6)
                    || s.equals("Mountains of Madness")) && core.var.factionsList.indexOf(FactionType.Windwalker) != -1)
                Windwalker.add(locations.get(i));
            else
                OpenerOfTheWay.add(locations.get(i));
        }
        startLoc.add(GreatCthulhu);
        startLoc.add(CrawlingChaos);
        startLoc.add(BlackGoat);
        startLoc.add(YellowSign);
        startLoc.add(OpenerOfTheWay);
        startLoc.add(Sleeper);
        startLoc.add(Windwalker);
    }
}
