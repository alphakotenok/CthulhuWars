package View;

import Model.Entity;
import Model.Location;
import javafx.scene.image.ImageView;

public class MapImageEntity extends ImageView{
    Location continent;
    Entity entity;
    public MapImageEntity(Location continent,Entity entity){
        this.continent = continent;
        this.entity = entity;
    }
}
