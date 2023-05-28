package View;



import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Model.Variables;
import Model.Faction.FactionType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;

public class ImageMisc {
    public static ImageView getFactionSheetImageView(int factionID) throws FileNotFoundException{
        String factionName = "images/FactionSheet/FactionCard_" + Variables.NAME_OF_FACTIONS[factionID] + ".png";

        FileInputStream inputStream = new FileInputStream(factionName);
        Image sheet = new Image(inputStream);

        ImageView sheetView = new ImageView(sheet);
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        sheetView.setY((Variables.SCREEN_HEIGHT - height) / 2);
        sheetView.setFitWidth(Variables.SCREEN_WIDTH * Variables.PROCENT);
        sheetView.setFitHeight(height); 
        return sheetView;      
    }
    public static Image getFactionSheetImage(int factionID) throws FileNotFoundException{
        String factionName = "images/FactionSheet/FactionCard_" + Variables.NAME_OF_FACTIONS[factionID] + ".png";

        FileInputStream inputStream = new FileInputStream(factionName);
        Image sheet = new Image(inputStream);
        return sheet;
    }
    public static Image getFactionLogoImage(FactionType factionType) throws FileNotFoundException{
        String logoName = "images/logo/Faction_" + factionType.name() + ".png";
        FileInputStream inputStream = new FileInputStream(logoName);
        Image logo = new Image(inputStream);
        return logo;
    }
    public static Image getMapImage(int countOfPlayers) throws FileNotFoundException, Exception {
        String pathToMap;

        if (countOfPlayers == 2 || countOfPlayers == 3) {
            pathToMap = "images/maps/map2_3.jpg";
        } else if (countOfPlayers <= Variables.MAX_COUNT_OF_PLAYERS
                && countOfPlayers >= Variables.MIN_COUNT_OF_PLAYERS) {
            pathToMap = "images/maps/map" + countOfPlayers + ".jpg";
        } else {
            throw new Exception("too many players");
        }

        FileInputStream inputStream = new FileInputStream(pathToMap);
        Image map = new Image(inputStream);
        return map;
    }
    public static ImageView getGameIconImageView() throws FileNotFoundException {
        String pathToImage = "Images/Cthulhu_Wars.png";
        FileInputStream inputStream;
        inputStream = new FileInputStream(pathToImage);
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        return imageView;
    }
    public static Boolean imagesAreEqual(Image image1,Image image2){
        if(image1 == null) return image2 == null;
        if(image2 == null) return image1 == null;
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) return false;

        int width = (int) image1.getWidth();
        int height = (int) image1.getHeight();
        PixelReader reader1 = image1.getPixelReader();
        PixelReader reader2 = image2.getPixelReader();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (reader1.getArgb(x, y) != reader2.getArgb(x, y)) return false;
            }
        }
        return true;
    }
}