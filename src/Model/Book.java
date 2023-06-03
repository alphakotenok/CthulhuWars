package Model;

import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

class Book {
    Image icon;
    String name;
    int openBook = -1;
    boolean isFlipped = false;

    Book(int number, FactionType faction, String name){
        this.name = name;
        String path = "images/Spellbooks/" + faction.name() + "/" + number + ".png";
        this.icon = Core.getImage(path);
    }

}
