package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.Faction.FactionType;
import javafx.scene.image.Image;

public class Core {
    GameMap map;
    EntityBase entityBase;
    int numOfPlayers;
    ArrayList<FactionType> factionsList;
    CommandTree ct;
    FactionBase factionBase;
    Ritual ritual;
    int turn;
    boolean correcrtWay;
    int firstPlayer;
    boolean endOfTheGame;
    ArrayList<Coordinates> rightBookCoordinates;

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

    public Core(int numOfPlayers, ArrayList<FactionType> factions)
            throws InvalidNumOfPlayersException, InvalidFactionsSetException {
        if (numOfPlayers > GameMap.maxNumOfPlayers || numOfPlayers < GameMap.minNumOfPlayers) {
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
        factionsList = new ArrayList<>();
        for (FactionType factionType : factions) {
            factionsList.add(factionType);
        }

        this.numOfPlayers = numOfPlayers;
        map = new GameMap(numOfPlayers, this);

        entityBase = new EntityBase(factionsList);
        factionBase = new FactionBase(this);
        ct = new CommandTree(this);
        ritual = new Ritual(this);
        turn = 0;
        correcrtWay = true;
        firstPlayer = 0;
        endOfTheGame = false;
        rightBookCoordinates = new ArrayList<>();
        rightBookCoordinates.add(new Coordinates(0.506, 0.241));
        rightBookCoordinates.add(new Coordinates(0.742, 0.241));
        rightBookCoordinates.add(new Coordinates(0.506, 0.485));
        rightBookCoordinates.add(new Coordinates(0.742, 0.485));
        rightBookCoordinates.add(new Coordinates(0.506, 0.724));
        rightBookCoordinates.add(new Coordinates(0.742, 0.724));
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public ArrayList<Entity> getEntityList() {
        return entityBase.entityList;
    }

    public ArrayList<Location> getLocationsList() {
        return map.locations;
    }

    public ArrayList<Entity> getEntityListInLocation(Location location) {
        return map.getEntityListInLocation(location);
    }

    public void addEntity(Location location, Entity entity) {
        location.entityList.add(entity);
    }

    public void deleteEntity(Location location, Entity entity) {
        location.entityList.remove(entity);
    }

    public Image getMapIcon() {
        return map.mapIcon;
    }

    public ArrayList<FactionType> getFactions() {
        return factionsList;
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
        return ct.curNode.desc;
    }

    int getNextTurn(int who) {
        if (correcrtWay)
            return (who + 1) % numOfPlayers;
        return (who - 1 + numOfPlayers) % numOfPlayers;
    }

    public ArrayList<Integer> getPowerList() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; ++i)
            ans.add(factionBase.factList.get(factionsList.get(i).ordinal()).energy);
        return ans;
    }

    public ArrayList<Integer> getDoomList() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; ++i)
            ans.add(factionBase.factList.get(factionsList.get(i).ordinal()).victoryPoints);
        return ans;
    }

    public ArrayList<Integer> getElderSignList() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; ++i)
            ans.add(factionBase.factList.get(factionsList.get(i).ordinal()).elderSignList.size());
        return ans;
    }

    public ArrayList<Integer> getRitualData() {
        if (endOfTheGame) {
            return new ArrayList<Integer>(
                    Arrays.asList(ritual.ritualTrack.get(ritual.ritualState), ritual.ritualTrack.size(),
                            ritual.ritualTrack.size()));
        }
        return new ArrayList<Integer>(Arrays.asList(ritual.ritualTrack.get(ritual.ritualState), ritual.ritualState,
                ritual.ritualTrack.size()));

    }

    public ArrayList<Integer> getOpenedBookList(FactionType faction) {
        return factionBase.getFactionFromEnum(faction).openedBooks;
    }

    public ArrayList<Image> getBookImageList(FactionType faction) {
        return factionBase.getFactionFromEnum(faction).bookImages;
    }

    public ArrayList<Coordinates> getLeftBookCoordinates() {
        return null;
    }

    public ArrayList<Coordinates> getRightBookCoordinates() {
        return rightBookCoordinates;
    }
}
