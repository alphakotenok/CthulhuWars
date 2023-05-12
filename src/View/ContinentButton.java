package View;

import java.util.ArrayList;

import Model.Entity;
import Model.GameMap;
import Model.Variables;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ContinentButton implements EventHandler<ActionEvent> {
    GameMap.Location continent;

    public ContinentButton(GameMap.Location continent) {
        this.continent = continent;
    }

    @Override
    public void handle(ActionEvent arg0) {
        /// TODO: we have to do something here, don't we?
        Misc.removeButtons(ContinentButton.class);
        ArrayList<Entity> nameOfEntity = Variables.core.getEntityListInLocation(continent);

        return;
    }
}
