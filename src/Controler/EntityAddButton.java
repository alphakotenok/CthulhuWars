package Controler;

import Model.Entity;
import Model.Location;
import Model.Variables;
import View.ButtonVisualizer;
import View.EntityVisualizer;
import View.Misc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EntityAddButton implements EventHandler<ActionEvent> {
    Location continent;
    Entity entity;

    public EntityAddButton(Entity entity, Location continent) {
        this.continent = continent;
        this.entity = entity;
    }

    public void handle(ActionEvent arg0) {
        EntityVisualizer.removeEntitiesFromMap(continent);
        Variables.core.addEntity(continent, entity);
        EntityVisualizer.placeEntitiesOnMap(continent);
        Misc.removeButtons(ContinentButton.class);
        Misc.removeButtons(EntityButton.class);
        Misc.removeButtons(EntityAddButton.class);
        ButtonVisualizer.displayContinentButtons();
    }
}
