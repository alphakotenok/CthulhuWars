package Model;

import java.util.ArrayList;

public class Location {
    public String name;
    public ArrayList<Location> adj = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Segment> segments = new ArrayList<>();

    public static class Point {
        int x;
        int y;

        Point(int curX, int curY) {
            x = curX;
            y = curY;
        }
    }

    public static class RatioCoordinates {
        public double x;
        public double y;

        RatioCoordinates(double curX, double curY) {
            x = curX;
            y = curY;
        }
    }

    static class Segment {
        Point left;
        Point right;

        Segment(int leftX, int leftY, int rightX, int rightY) {
            left = new Point(leftX, leftY);
            right = new Point(rightX, rightY);
        }
    }

    Location(String curName, ArrayList<Segment> curSegments) {
        this.name = curName;
        this.segments = curSegments;
    }

    public RatioCoordinates getEntityPosition(int entityNum) {
        int entityTotal = entityList.size() + 1;
        int segTotal = segments.size();
        int lengthOfSegments[] = new int[segTotal];
        int sumOfLength = 0;
        for(int i = 0; i < segTotal; i ++){
            lengthOfSegments[i] = (int)Math.sqrt((segments.get(i).left.x - segments.get(i).right.x ) * (segments.get(i).left.x - segments.get(i).right.x) + (segments.get(i).left.y - segments.get(i).right.y ) * (segments.get(i).left.y - segments.get(i).right.y) );
            sumOfLength += lengthOfSegments[i];
        }
        int avLength = sumOfLength / entityTotal;
        int colEnt[] = new int[segTotal];
        for(int i = 0; i < segTotal; i ++){
            colEnt[i] = (lengthOfSegments[i] + avLength / 2) / avLength;
            entityTotal -= colEnt[i];
        }
        for(int i = 0; i < segTotal; i ++){
            if(entityTotal > 0) colEnt[i] ++;
            entityTotal--;
        }
        entityTotal = entityList.size();
        for (int i = 0; i < segTotal; i++) {
            if (entityNum >= colEnt[i])
                entityNum -= colEnt[i];
            else {
                Point left = segments.get(i).left;
                Point right = segments.get(i).right;
                double perShift = 1.0 / colEnt[i];
                double resPer = perShift * entityNum + perShift / 2;
                double xCoordinate = left.x * resPer + right.x * (1 - resPer);
                double yCoordinate = left.y * resPer + right.y * (1 - resPer);
                xCoordinate /= 1280.0;
                yCoordinate /= 638.0;
                return new RatioCoordinates(xCoordinate, yCoordinate);
            }
        }
        return null;
    }

} 