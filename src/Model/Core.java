package Model;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;

import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

public class Core {

    static Image getImage(String path) {
        try {
            FileInputStream fileStream = new FileInputStream(path);
            Image image = new Image(fileStream);
            return image;
        } catch (Exception e) {
            return null;
        }
    }

    GameMap map;
    CommandTree ct;
    FactionBase factionBase;
    Ritual ritual;
    GameVariables var;
    Gates gates;

    ArrayList<Coordinates> rightBookCoordinates, leftBookCoordinates;

    Faction getCurFact() {
        return factionBase.getFactionFromEnum(var.factionsList.get(var.turn));
    }

    Faction getSomeFact(int i) {
        return factionBase.getFactionFromEnum(var.factionsList.get(i));
    }

    public class InvalidNumOfPlayersException extends Exception {

    }

    public class InvalidFactionsSetException extends Exception {

    }

    public static class Coordinates {
        public double x, y;

        Coordinates(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static class Drawable {
        public Coordinates coord;
        public Image image;

        Drawable(Coordinates coord, Image image) {
            this.coord = coord;
            this.image = image;
        }
    }

    public Core(int numOfPlayers, ArrayList<FactionType> factions)
            throws InvalidNumOfPlayersException, InvalidFactionsSetException {
        if (numOfPlayers > GameVariables.maxNumOfPlayers || numOfPlayers < GameVariables.minNumOfPlayers) {
            throw new InvalidNumOfPlayersException();
        }
        if (factions.size() != numOfPlayers) {
            throw new InvalidFactionsSetException();
        }
        for (int i = 0; i < factions.size(); ++i) {
            for (int j = i + 1; j < factions.size(); ++j) {
                if (factions.get(i).equals(factions.get(j))) {
                    throw new InvalidFactionsSetException();
                }
            }
        }
        var = new GameVariables(numOfPlayers, factions);
        map = new GameMap(this);
        ritual = new Ritual(this);
        factionBase = new FactionBase(this);
        ct = new CommandTree(this);
        gates = new Gates(this);
        gates.icon = getImage("images/Entities/Gates.png");

        rightBookCoordinates = new ArrayList<>();
        rightBookCoordinates.add(new Coordinates(0.506, 0.241));
        rightBookCoordinates.add(new Coordinates(0.742, 0.241));
        rightBookCoordinates.add(new Coordinates(0.506, 0.477));
        rightBookCoordinates.add(new Coordinates(0.742, 0.477));
        rightBookCoordinates.add(new Coordinates(0.506, 0.718));
        rightBookCoordinates.add(new Coordinates(0.742, 0.718));
        leftBookCoordinates = new ArrayList<>();
        leftBookCoordinates.add(new Coordinates(0.027, 0.241));
        leftBookCoordinates.add(new Coordinates(0.267, 0.241));
        leftBookCoordinates.add(new Coordinates(0.027, 0.477));
        leftBookCoordinates.add(new Coordinates(0.267, 0.477));
        leftBookCoordinates.add(new Coordinates(0.027, 0.718));
        leftBookCoordinates.add(new Coordinates(0.267, 0.718));
    }

    public int getNumOfPlayers() {
        return var.numOfPlayers;
    }

    public ArrayList<Drawable> getEntitiesToDraw() {
        ArrayList<Drawable> ans = new ArrayList<>();
        ArrayList<Drawable> subAns;
        for (Location loc : map.locations) {
            int numOfController = -1;
            subAns = new ArrayList<>();
            ArrayList<EntitySet> entityList = new ArrayList<>();
            for (FactionType faction : var.factionsList) {
                Faction fact = factionBase.getFactionFromEnum(faction);
                entityList.addAll(fact.getEntitiesInLocation(loc));
            }
            int locationSize = entityList.size();
            if (gates.isGateInLocation(loc)) {
                if (gates.isGateControlled(loc)) {
                    EntitySet controller = gates.getGateController(loc);
                    for (int i = 0; i < entityList.size(); ++i) {
                        if (controller == entityList.get(i)) {
                            numOfController = i;
                            break;
                        }
                    }
                } else {
                    ++locationSize;
                    subAns.add(new Drawable(loc.getEntityPosition(subAns.size(), locationSize), gates.icon));
                }
            }
            for (int i = 0; i < entityList.size(); ++i) {
                if (numOfController == i) {
                    subAns.add(new Drawable(loc.getEntityPosition(subAns.size(), locationSize),
                            entityList.get(i).iconOnGate));
                } else {
                    subAns.add(new Drawable(loc.getEntityPosition(subAns.size(), locationSize),
                            entityList.get(i).icon));
                }
            }
            ans.addAll(subAns);
        }
        return ans;
    }

    public ArrayList<FactionType> getFactions() {
        return var.factionsList;
    }

    public ArrayList<String> getCommandList() {
        return ct.getCommandList();
    }

    public void activateCommand(Integer num) {
        try {
            ct.execute(num);
        } catch (Exception e) {

        }
    }

    public String getCommandDescription() {
        return ct.curNode.name;
    }

    public ArrayList<Integer> getPowerList() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < var.numOfPlayers; ++i)
            ans.add(factionBase.factList.get(var.factionsList.get(i).ordinal()).energy);
        return ans;
    }

    public ArrayList<Integer> getDoomList() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < var.numOfPlayers; ++i)
            ans.add(factionBase.factList.get(var.factionsList.get(i).ordinal()).victoryPoints);
        return ans;
    }

    public ArrayList<Integer> getElderSignList() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < var.numOfPlayers; ++i)
            ans.add(factionBase.factList.get(var.factionsList.get(i).ordinal()).elderSignList.size());
        return ans;
    }

    public ArrayList<Integer> getRitualData() {
        if (var.endOfTheGame) {
            return new ArrayList<Integer>(
                    Arrays.asList(ritual.ritualTrack.get(ritual.ritualState), ritual.ritualTrack.size(),
                            ritual.ritualTrack.size()));
        }
        return new ArrayList<Integer>(Arrays.asList(ritual.ritualTrack.get(ritual.ritualState), ritual.ritualState,
                ritual.ritualTrack.size()));

    }

    public ArrayList<Integer> getOpenedBookList(FactionType faction) {
        ArrayList<Integer> openBooks = new ArrayList<>();
        for (Book book : factionBase.getFactionFromEnum(faction).books) {
            openBooks.add(book.openBook);
        }
        return openBooks;
    }

    public ArrayList<Image> getBookImageList(FactionType faction) {
        ArrayList<Image> icons = new ArrayList<>();
        for (Book book : factionBase.getFactionFromEnum(faction).books) {
            icons.add(book.icon);
        }
        return icons;
    }

    public ArrayList<Coordinates> getLeftBookCoordinates() {
        return leftBookCoordinates;
    }

    public ArrayList<Coordinates> getRightBookCoordinates() {
        return rightBookCoordinates;
    }

    public boolean getWay() {
        return var.correctWay;
    }

}
