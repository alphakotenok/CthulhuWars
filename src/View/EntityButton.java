package View;

import Model.Entity;
import Model.Location;
import Model.Variables;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EntityButton implements EventHandler<ActionEvent> {
    Location continent;
    Entity entity;
    public EntityButton(Entity entity,Location continent){
        this.continent = continent;
        this.entity = entity;
    }

    @Override
    public void handle(ActionEvent arg0) {
        Variables.core.deleteEntity(continent, entity);
        Misc.removeButtons(Variables.core.getEntityListInLocation(continent));
        ButtonVisualizer.rebuildEntityButtons(continent);
        Misc.removeButtons(ContinentButton.class);
        Visualizer.continentsButtons();
    }
}
