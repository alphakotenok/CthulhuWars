package View;

import Model.Entity;
import Model.Location;
import Model.Variables;
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
        MapEntity.removeEntitiesFromLocation(continent);
        Variables.core.addEntity(continent, entity);
        MapEntity.placeOnMap(continent);
        Misc.removeButtons(ContinentButton.class);
        Misc.removeButtons(EntityButton.class);
        Misc.removeButtons(EntityAddButton.class);
        Visualizer.continentsButtons();
    }
}