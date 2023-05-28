package View;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import Model.Variables;
import javafx.event.ActionEvent;

public class ActionsMisc {
    public static <T extends EventHandler<ActionEvent>> void removeButtons(Class<T> c) {
        Variables.root.getChildren().removeIf(node -> {
            if (node instanceof Button) {
                EventHandler<ActionEvent> onAction = ((Button) node).getOnAction();
                return onAction != null && c.isInstance(onAction);
            }
            return false;
        });
        return;
    }

    public static <T extends EventHandler<ActionEvent>> void disableButtons(Class<T> c) {
        Variables.root.getChildren().forEach(node -> {
            if (node instanceof Button) {
                EventHandler<ActionEvent> onAction = ((Button) node).getOnAction();
                if (onAction != null && c.isInstance(onAction)) {
                    node.setDisable(true);
                }
            }
        });
        return;
    }

    public static <T extends EventHandler<ActionEvent>> void enableButtons(Class<T> c) {
        Variables.root.getChildren().forEach(node -> {
            if (node instanceof Button) {
                EventHandler<ActionEvent> onAction = ((Button) node).getOnAction();
                if (onAction != null && c.isInstance(onAction)) {
                    node.setDisable(false);
                }
            }
        });
        return;
    }

    public static void removeLabel(String text) {
        Variables.root.getChildren().removeIf(node -> {
            return node instanceof Label && ((Label) node).getText().equals(text);
        });
        return;
    }

    public static void addButton(Button b) {
        Variables.root.getChildren().add(b);
    }

    public static void addLabel(Label l) {
        Variables.root.getChildren().add(l);
    }

    public static <T extends EventHandler<ActionEvent>> void removeButton(T curEventHandler) {
        Variables.root.getChildren().removeIf(node -> {
            if (node instanceof Button) {
                Object onAction = ((Button) node).getOnAction();
                return onAction != null && onAction.equals(curEventHandler);
            }
            return false;
        });
        return;
    }

    public static void removeImage(Image image) {
        Variables.root.getChildren().removeIf(node -> {
            return node instanceof ImageView && ImageMisc.imagesAreEqual(image, ((ImageView) node).getImage());
        });
    }
}