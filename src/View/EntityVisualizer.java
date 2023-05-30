package View;

import java.util.ArrayList;
import Model.Entity;
import Model.Location;
import Model.Variables;
import Model.Core.Coordinates;;

public class EntityVisualizer {
    public static void placeEntitiesOnMap(Location continent) {
        ArrayList<Entity> entities = continent.entityList;
        for (int entityID = 0; entityID < entities.size(); entityID++) {
            Entity entity = entities.get(entityID);
            MapImageEntity mapImageEntity = new MapImageEntity(continent, entity);
            mapImageEntity.setImage(entity.icon);
            Coordinates z = continent.getEntityPosition(entityID);
            double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
            double widthEntity = entity.icon.getWidth() * Variables.PROCENT * 0.5;
            double heightEntity = entity.icon.getHeight() * Variables.PROCENT * 0.5;
            double x = z.x * Variables.SCREEN_WIDTH * Variables.PROCENT;
            double y = (Variables.SCREEN_HEIGHT - height) / 2 + height * z.y;

            mapImageEntity.setLayoutX(x - widthEntity / 2);
            mapImageEntity.setLayoutY(y - heightEntity / 2);
            mapImageEntity.setFitWidth(widthEntity);
            mapImageEntity.setPreserveRatio(true);
            ActionsMisc.display(mapImageEntity);
        }
        return;
    }

    public static void removeEntitiesFromMap(Location continent) {
        ActionsMisc.removeByFilter(node -> {
            return node instanceof MapImageEntity && ((MapImageEntity) node).continent.equals(continent);
        });
        return;
    }
}
