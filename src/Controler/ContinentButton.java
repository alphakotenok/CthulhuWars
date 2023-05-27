package Controler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Model.Location;
import View.Misc;

public class ContinentButton implements EventHandler<ActionEvent> {
    Location continent;

    public ContinentButton(Location continent) {
        this.continent = continent;
    }

    @Override
    public void handle(ActionEvent arg0) {
        Misc.removeButtons(ContinentButton.class);
        View.ButtonVisualizer.rebuildEntityDelButtons(continent);
        return;
    }
}