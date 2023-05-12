package Model;

import java.util.ArrayList;

public class Location {
    public String name;
    public ArrayList<Location> adj = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Segment> segments = new ArrayList<>();

    public static class Point{
        int x;
        int y;
        Point(int curX, int curY){
            x = curX;
            y = curY;
        }
    }
    
    public static class RatioCoordinates{
        double x;
        double y;
        RatioCoordinates(double curX, double curY){
            x = curX;
            y = curY;
        }
    }

    static class Segment{
        Point left;
        Point right;
        Segment(int leftX, int leftY, int rightX, int rightY){
            left = new Point(leftX, leftY);
            right = new Point(rightX, rightY);
        }
    }
    
    Location(String curName, ArrayList<Segment> curSegments) {
        this.name = curName;
        this.segments = curSegments;
    }

    RatioCoordinates getEntityPosition(int entityNum){
        int entityTotal = entityList.size();
        int segTotal = segments.size();
        int col = entityTotal / segTotal;
        int ost = entityTotal % segTotal;
        for(int i = 0; i < segTotal; i ++){
            int curAmount = col;
            if(i < ost) curAmount ++;
            if(entityNum >= col) entityNum -= curAmount;
            else{
                Point left = segments.get(i).left;
                Point right = segments.get(i).right;
                double xCoordinate = (left.x * (curAmount - entityNum) + right.x * (entityNum + 1)) / curAmount;
                double yCoordinate = (left.y * (curAmount - entityNum) + right.y * (entityNum + 1)) / curAmount;
                xCoordinate /= 1280.0;
                yCoordinate /= 638.0;
                return new RatioCoordinates(xCoordinate, yCoordinate);
            }
        }
        return null;
    }


}