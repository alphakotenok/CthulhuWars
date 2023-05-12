package View;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

import Model.Entity;
import Model.Variables;
import Model.Entity.EntityType;
import Model.Faction.FactionType;
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
        List<Node> buttonsToRemove = new ArrayList<>();
        for (Node node : Variables.root.getChildren()) {
            if (node instanceof Button) {
                EventHandler<ActionEvent> onAction = ((Button) node).getOnAction();
                if (onAction != null && onAction instanceof EntityButton) {
                    if(entities.contains(((EntityButton)onAction).entity)){
                        buttonsToRemove.add(node);
                    }
                }
            }
        }
        Variables.root.getChildren().removeAll(buttonsToRemove);
        return;
    }
    public static EntityType getEntityByID(int entityID){
        return Entity.EntityType.values()[entityID];
    }
    public static FactionType getFactionByID(int factionID){
        return FactionType.values()[factionID];
    }
}
