package View;

import Model.Entity;
import Model.Location;
import Model.Variables;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EntityDeleteButton implements EventHandler<ActionEvent> {
    Location continent;
    Entity entity;

    public EntityDeleteButton(Entity entity, Location continent) {
        this.continent = continent;
        this.entity = entity;
    }

    @Override
    public void handle(ActionEvent arg0) {
        MapEntity.removeEntitiesFromLocation(continent);
        Variables.core.deleteEntity(continent, entity);
        MapEntity.placeOnMap(continent);
        Misc.removeButtons(Variables.core.getEntityListInLocation(continent));
        ButtonVisualizer.rebuildEntityDelButtons(continent);
        Misc.removeButtons(ContinentButton.class);
        Misc.removeButtons(EntityDeleteButton.class);
        Misc.removeButtons(EntityButton.class);
        Visualizer.continentsButtons();
    }
}
