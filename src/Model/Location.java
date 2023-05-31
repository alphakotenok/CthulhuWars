package Model;

import java.util.ArrayList;

import Model.Core.Coordinates;

public class Location {
    String name;
    ArrayList<Location> adj = new ArrayList<>();
    public ArrayList<Entity> entityList = new ArrayList<>();
    ArrayList<Segment> segments = new ArrayList<>();

    static class Segment {
        Coordinates left;
        Coordinates right;

        Segment(int leftX, int leftY, int rightX, int rightY) {
            left = new Coordinates(leftX, leftY);
            right = new Coordinates(rightX, rightY);
        }
    }

    Location(String curName, ArrayList<Segment> curSegments) {
        this.name = curName;
        this.segments = curSegments;
    }

    public Coordinates getEntityPosition(int entityNum, int entityTotal) {
        ++entityTotal;
        int segTotal = segments.size();
        double lengthOfSegments[] = new double[segTotal];
        double sumOfLength = 0;
        for (int i = 0; i < segTotal; i++) {
            lengthOfSegments[i] = Math.sqrt((segments.get(i).left.x - segments.get(i).right.x)
                    * (segments.get(i).left.x - segments.get(i).right.x)
                    + (segments.get(i).left.y - segments.get(i).right.y)
                            * (segments.get(i).left.y - segments.get(i).right.y));
            sumOfLength += lengthOfSegments[i];
        }
        double avLength = sumOfLength / entityTotal;
        int colEnt[] = new int[segTotal];
        for (int i = 0; i < segTotal; i++) {
            colEnt[i] = (int) ((lengthOfSegments[i] + avLength / 2.0) / avLength);
            entityTotal -= colEnt[i];
        }
        for (int i = 0; i < segTotal; i++) {
            if (entityTotal > 0)
                colEnt[i]++;
            entityTotal--;
        }
        entityTotal = entityList.size();
        for (int i = 0; i < segTotal; i++) {
            if (entityNum >= colEnt[i])
                entityNum -= colEnt[i];
            else {
                Coordinates left = segments.get(i).left;
                Coordinates right = segments.get(i).right;
                double perShift = 1.0 / colEnt[i];
                double resPer = perShift * entityNum + perShift / 2;
                double xCoordinate = left.x * resPer + right.x * (1 - resPer);
                double yCoordinate = left.y * resPer + right.y * (1 - resPer);
                xCoordinate /= 1280.0;
                yCoordinate /= 638.0;
                return new Coordinates(xCoordinate, yCoordinate);
            }
        }
        return null;
    }

}