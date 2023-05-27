package Controler;

import Model.Location;
import View.ButtonVisualizer;
import View.Misc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EntityButton implements EventHandler<ActionEvent> {
    Location continent;

    public EntityButton(Location continent) {
        this.continent = continent;
    }

    @Override
    public void handle(ActionEvent arg0) {
        Misc.removeButtons(EntityDeleteButton.class);
        Misc.removeButtons(EntityButton.class);
        ButtonVisualizer.rebuildEntityAddButtons(continent);
        return;
    }
}