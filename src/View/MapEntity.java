package View;

import java.util.ArrayList;
import java.util.List;

import Model.Entity;
import Model.Location;
import Model.Variables;
import Model.Location.RatioCoordinates;
import javafx.scene.Node;

public class MapEntity {
    static void placeOnMap(Location continent) {
        ArrayList<Entity> entities = continent.entityList;
        for (int entityID = 0; entityID < entities.size(); entityID++) {
            Entity entity = entities.get(entityID);
            MapImageEntity mapImageEntity = new MapImageEntity(continent, entity);
            mapImageEntity.setImage(entity.icon);
            RatioCoordinates z = continent.getEntityPosition(entityID);
            double height = Variables.SCREEN_WIDTH * Variables.PROCENT / Variables.mapRatio;
            double x = z.x * Variables.SCREEN_WIDTH * Variables.PROCENT;
            double y = (Variables.SCREEN_HEIGHT - height) / 2 + height * z.y;
            mapImageEntity.setLayoutX(x);
            mapImageEntity.setLayoutY(y);
            mapImageEntity.setFitWidth(entity.icon.getWidth() * Variables.PROCENT);
            mapImageEntity.setPreserveRatio(true);
            Variables.root.getChildren().add(mapImageEntity);
        }
        return;
    }

    static void removeEntitiesFromLocation(Location continent) {
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
