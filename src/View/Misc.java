package View;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

import Model.Variables;
import javafx.event.ActionEvent;

public class Misc {
    public static <T extends EventHandler<ActionEvent>> void removeButtons(Class<T> c) {
        List<Node> buttonsToRemove = new ArrayList<>();
        for (Node node : Variables.root.getChildren()) {
            if (node instanceof Button) {
                EventHandler<ActionEvent> onAction = ((Button) node).getOnAction();
                if (onAction != null && c.isInstance(onAction)) {
                    buttonsToRemove.add(node);
                }
            }
        }
        Variables.root.getChildren().removeAll(buttonsToRemove);
        return;
    }
    public static void removeLabel(String text) {
        List<Node> labelsToRemove = new ArrayList<>();
        for (Node node : Variables.root.getChildren()) {
            if (node instanceof Label) {
                if (((Label) node).getText().equals(text)) {
                    labelsToRemove.add(node);
                }
            }
        }
        Variables.root.getChildren().removeAll(labelsToRemove);
        return;
    }
}
