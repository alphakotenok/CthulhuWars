package View;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import Controler.EntityDeleteButton;
import Model.Entity;
import Model.Variables;
import Model.Entity.EntityType;
import Model.Faction.FactionType;
import javafx.event.ActionEvent;

public class Misc {
    public static <T extends EventHandler<ActionEvent>> void removeButtons(Class<T> c) {
        Variables.root.getChildren().removeIf(node -> {
            if(node instanceof Button){
                EventHandler<ActionEvent> onAction = ((Button) node).getOnAction();
                if (onAction != null && c.isInstance(onAction)) {
                    return true;
                }
            }
            return false;
        });
        return;
    }
    public static void removeLabel(String text) {
        Variables.root.getChildren().removeIf(node -> {
            return node instanceof Label && ((Label) node).getText().equals(text);
        });
        return;
    }
    public static void addButton(Button b){
        Variables.root.getChildren().add(b);
    }
    public static void addLabel(Label l){
        Variables.root.getChildren().add(l);
    }
    public static <T extends EventHandler<ActionEvent>> void removeButton(T curEventHandler){
        for (Node node : Variables.root.getChildren()) {
            if (node instanceof Button) {
                Object onAction = ((Button) node).getOnAction();
                if (onAction != null && onAction.equals(curEventHandler)) {
                    Variables.root.getChildren().remove(node);
                    return;
                }
            }
        }
        return;
    }
    public static void removeButtons(ArrayList<Entity> entities){
        Variables.root.getChildren().removeIf(node -> {
            if (node instanceof Button) {
                EventHandler<ActionEvent> onAction = ((Button) node).getOnAction();
                return onAction != null && onAction instanceof EntityDeleteButton && entities.contains(((EntityDeleteButton)onAction).entity);
            }
            return false;
        });
        return;
    }
    public static EntityType getEntityByID(int entityID){
        return Entity.EntityType.values()[entityID];
    }
    public static FactionType getFactionByID(int factionID){
        return FactionType.values()[factionID];
    }
}
