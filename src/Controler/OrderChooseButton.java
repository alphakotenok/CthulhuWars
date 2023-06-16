package Controler;

import Model.Variables;
import View.ActionsMisc;
import View.Visualizer;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class OrderChooseButton extends Button {
    int commandID;
    public OrderChooseButton(int commandID, String order){
        this.commandID = commandID;

         TextFlow textFlowFaction = new TextFlow();
            
        for (int faction = 0; faction < order.length(); faction++) {
            int factionID = order.charAt(faction) - '0';
            Text textEntity1 = new Text(Variables.NAME_OF_FACTIONS[factionID]);
            textEntity1.setFill(Variables.COLOR_OF_FACTIONS[factionID]);
            textEntity1.setFont(Font.font("Arial", 32));
            Text textEntity2 = new Text(" -> ");
            textEntity2.setFill(Color.BLACK);
            textEntity2.setFont(Font.font("Arial", 32));
            textFlowFaction.getChildren().add(textEntity1);
            if (faction != order.length() - 1)
            textFlowFaction.getChildren().add(textEntity2);
        }

        textFlowFaction.setTextAlignment(TextAlignment.CENTER);
        setGraphic(textFlowFaction);

        setOnAction(action -> {
            ActionsMisc.removeButtons(OrderChooseButton.class);
            ActionsMisc.removeLabelByText(Variables.core.getCommandDescription());
            Variables.core.activateCommand(commandID);
            try {
                Visualizer.createField(Variables.numberOfPlayers);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
