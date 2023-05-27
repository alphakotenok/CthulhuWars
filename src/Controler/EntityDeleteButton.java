package Controler;

import Model.Entity;
import Model.Location;
import Model.Variables;
import View.ButtonVisualizer;
import View.EntityVisualizer;
import View.Misc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EntityDeleteButton implements EventHandler<ActionEvent> {
    Location continent;
    public Entity entity;

    public EntityDeleteButton(Entity entity, Location continent) {
        this.continent = continent;
        this.entity = entity;
    }

    @Override
    public void handle(ActionEvent arg0) {
        EntityVisualizer.removeEntitiesFromMap(continent);
        Variables.core.deleteEntity(continent, entity);
        EntityVisualizer.placeEntitiesOnMap(continent);
        Misc.removeButtons(Variables.core.getEntityListInLocation(continent));
        ButtonVisualizer.rebuildEntityDelButtons(continent);
        Misc.removeButtons(ContinentButton.class);
        Misc.removeButtons(EntityDeleteButton.class);
        Misc.removeButtons(EntityButton.class);
        ButtonVisualizer.displayContinentButtons();
    }
}
