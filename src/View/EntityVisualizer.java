package View;

import Model.Variables;
import Model.Core.Coordinates;
import Model.Core.Drawable;;

public class EntityVisualizer {
    public static void placeEntitiesOnMap() {
        for (Drawable entity : Variables.core.getEntitiesToDraw()) {
            MapImageEntity mapImageEntity = new MapImageEntity();
            mapImageEntity.setImage(entity.image);
            Coordinates z = entity.coord;
            double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
            double widthEntity = entity.image.getWidth() * Variables.PROCENT * 0.5;
            double heightEntity = entity.image.getHeight() * Variables.PROCENT * 0.5;
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

    public static void removeEntitiesFromMap() {
        ActionsMisc.removeByFilter(node -> {
            return node instanceof MapImageEntity;
        });
        return;
    }
}
