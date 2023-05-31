package View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Model.Variables;
import Model.Core.Coordinates;
import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;

public class ImageMisc {
    public static ImageView getFactionSheetImageView(int factionID) throws FileNotFoundException {
        Image sheet = getFactionSheetImage(factionID);
        Variables.factionSheetHeight = sheet.getHeight();
        Variables.factionSheetWidth = sheet.getWidth();
        ImageView sheetView = new ImageView(sheet);
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        sheetView.setY((Variables.SCREEN_HEIGHT - height) / 2);
        sheetView.setFitWidth(Variables.SCREEN_WIDTH * Variables.PROCENT);
        sheetView.setFitHeight(height);
        return sheetView;
    }

    public static Image getFactionSheetImage(int factionID) throws FileNotFoundException {
        String factionName = "images/FactionSheet/FactionCard_" + Variables.NAME_OF_FACTIONS[factionID] + ".png";

        FileInputStream inputStream = new FileInputStream(factionName);
        Image sheet = new Image(inputStream);
        return sheet;
    }

    public static Image getFactionLogoImage(FactionType factionType) throws FileNotFoundException {
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

    public static Image getSpellBookSheetImage() throws FileNotFoundException {
        String spellBookSheetName = "images/SpellBooks/SpellBookSheet.png";
        FileInputStream inputStream = new FileInputStream(spellBookSheetName);
        Image spellBookSheet = new Image(inputStream);
        return spellBookSheet;
    }

    public static ImageView getSpellBookSheetImageView() throws FileNotFoundException {
        Image spellBookSheet = getSpellBookSheetImage();

        ImageView sheetView = new ImageView(spellBookSheet);
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        double width = Variables.SCREEN_WIDTH * Variables.PROCENT;
        sheetView.setY((Variables.SCREEN_HEIGHT - height) / 2 + 130 * height / Variables.factionSheetHeight);
        sheetView.setX(15 * width / Variables.factionSheetWidth);
        sheetView.setFitWidth(width / 2 - 30 * width / Variables.factionSheetWidth);
        sheetView.setFitHeight(height - 150 * height / Variables.factionSheetHeight);
        return sheetView;
    }

    public static ImageView getGameIconImageView() throws FileNotFoundException {
        String pathToImage = "Images/logo/Cthulhu_Wars.png";
        FileInputStream inputStream;
        inputStream = new FileInputStream(pathToImage);
        Image image = new Image(inputStream);
        ImageView imageView = new ImageView(image);
        return imageView;
    }

    public static ImageView getSpellBookImageView(Image bookImage, Coordinates bookCoordinates) {
        double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
        double width = Variables.SCREEN_WIDTH * Variables.PROCENT;
        double heightBook = bookImage.getHeight() * height / Variables.factionSheetHeight;
        double widthBook = (bookImage.getWidth() - 15) * width / Variables.factionSheetWidth;
        double x = bookCoordinates.x * width;
        double y = (Variables.SCREEN_HEIGHT - height) / 2 + height * bookCoordinates.y;

        ImageView bookView = new ImageView(bookImage);
        bookView.setY(y);
        bookView.setX(x);
        bookView.setFitWidth(widthBook);
        bookView.setFitHeight(heightBook);
        return bookView;
    }

    public static Boolean imagesAreEqual(Image image1, Image image2) {
        if (image1 == null)
            return image2 == null;
        if (image2 == null)
            return image1 == null;
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight())
            return false;

        int width = (int) image1.getWidth();
        int height = (int) image1.getHeight();
        PixelReader reader1 = image1.getPixelReader();
        PixelReader reader2 = image2.getPixelReader();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (reader1.getArgb(x, y) != reader2.getArgb(x, y))
                    return false;
            }
        }
        return true;
    }
}