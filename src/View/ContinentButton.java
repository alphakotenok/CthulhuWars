package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Model.Location;

public class ContinentButton implements EventHandler<ActionEvent> {
    Location continent;

    public ContinentButton(Location continent) {
        this.continent = continent;
    }

    @Override
    public void handle(ActionEvent arg0) {
        /// TODO: we have to do something here, don't we?
        Misc.removeButtons(ContinentButton.class);
        ButtonVisualizer.rebuildEntityDelButtons(continent);
        return;
    }
}