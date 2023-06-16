package View;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Controler.MiscFunctions;

import java.util.ArrayList;
import java.util.function.Predicate;

import Model.Variables;
import javafx.collections.ObservableList;

public class ActionsMisc {
    public static <T extends Button> void removeButtons(Class<T> c) {
        Variables.root.getChildren().removeIf(node -> {
            return c.isInstance(node);
        });
        return;
    }

    public static <T extends Button> void disableButtons(Class<T> c) {
        Variables.root.getChildren().forEach(node -> {
            if (c.isInstance(node)) {
                node.setDisable(true);
            }
        });
        return;
    }

    public static <T extends Button> void enableButtons(Class<T> c) {
        Variables.root.getChildren().forEach(node -> {
            if (c.isInstance(node)) {
                node.setDisable(false);
            }
        });
        return;
    }

    public static void removeLabelByText(String text) {
        Variables.root.getChildren().removeIf(node -> {
            return node instanceof Label && ((Label) node).getText().equals(text);
        });
        return;
    }

    public static void removeLabelById(String id) {
        Variables.root.getChildren().removeIf(node -> {
            return node instanceof Label && ((Label) node).getId() != null && ((Label) node).getId().equals(id);
        });
        return;
    }

    public static <T extends Node> void display(T object) {
        Variables.root.getChildren().add(object);
    }

    public static ObservableList<Node> getDisplayedNodes() {
        return Variables.root.getChildren();
    }

    public static <T extends Button> void removeButton(T button) {
        Variables.root.getChildren().removeIf(node -> {
            return node.equals(button);
        });
        return;
    }

    public static void removeImage(Image image) {
        Variables.root.getChildren().removeIf(node -> {
            return node instanceof ImageView && ImageMisc.imagesAreEqual(image, ((ImageView) node).getImage());
        });
    }

    public static void removeBooks(int factionID) {
        ArrayList<Image> imageBooks = Variables.core.getBookImageList(MiscFunctions.getFactionByID(factionID));
        for (int i = 0; i < imageBooks.size(); i++) {
            removeImage(imageBooks.get(i));
        }
    }

    public static void removeUnopenedBooks(int factionID) {
        ArrayList<Integer> unopenedBooks = Variables.core.getOpenedBookList(MiscFunctions.getFactionByID(factionID));
        ArrayList<Image> imageBooks = Variables.core.getBookImageList(MiscFunctions.getFactionByID(factionID));
        for (int i = 0; i < imageBooks.size(); i++) {
            if (unopenedBooks.get(i).equals(-1)) {
                removeImage(imageBooks.get(i));
            }
        }
    }
    public static void clearScreen(){
        Variables.root.getChildren().clear();
    }
    public static void removeByFilter(Predicate<? super Node> filter){
        Variables.root.getChildren().removeIf(filter);
    }
}