package View;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;

import Model.Entity;
import Model.Location;
import Model.Variables;
import Model.Location.RatioCoordinates;

public class EntityVisualizer {
    public static void placeEntitiesOnMap(Location continent) {
        ArrayList<Entity> entities = continent.entityList;
        for (int entityID = 0; entityID < entities.size(); entityID++) {
            Entity entity = entities.get(entityID);
            MapImageEntity mapImageEntity = new MapImageEntity(continent, entity);
            mapImageEntity.setImage(entity.icon);
            RatioCoordinates z = continent.getEntityPosition(entityID);
            double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
            double widthEntity = entity.icon.getWidth() * Variables.PROCENT;
            double heightEntity = entity.icon.getHeight() * Variables.PROCENT;
            double x = z.x * Variables.SCREEN_WIDTH * Variables.PROCENT;
            double y = (Variables.SCREEN_HEIGHT - height) / 2 + height * z.y;
            mapImageEntity.setLayoutX(x - widthEntity / 2);
            mapImageEntity.setLayoutY(y - heightEntity / 2);
            mapImageEntity.setFitWidth(entity.icon.getWidth() * Variables.PROCENT / 2);
            mapImageEntity.setPreserveRatio(true);
            Variables.root.getChildren().add(mapImageEntity);
        }
        return;
    }

    public static void removeEntitiesFromMap(Location continent) {
        List<Node> removeNodes = new ArrayList<>();
        for (Node node : Variables.root.getChildren()) {
            if (node instanceof MapImageEntity && ((MapImageEntity) node).continent.equals(continent)) {
                removeNodes.add(node);
            }
        }
        Variables.root.getChildren().removeAll(removeNodes);
        return;
    }
}
