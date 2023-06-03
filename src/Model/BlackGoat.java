package Model;

import java.util.ArrayList;
import java.util.Arrays;

import Model.EntitySet.Category;
import Model.FactionEnum.FactionType;
import javafx.scene.image.Image;

class BlackGoat extends Faction {
    BlackGoat(String name, FactionType faction, Core core) {
        super(name, faction, core);
        ArrayList<Image> entityIcons = Faction.getEntityImages(new ArrayList<>(
                Arrays.asList("Acolyte (red)", "Ghoul", "Fungi from Yuggoth", "Dark Young", "Shub-Niggurath")));
        entitySetsList.add(new EntitySet(core, "Cultist", Category.Cultist, faction, entityIcons.get(0), 6));
        entitySetsList.add(new EntitySet(core, "Ghoul", Category.Monster, faction, entityIcons.get(1), 2));
        entitySetsList.add(new EntitySet(core, "Fungi from Yuggoth", Category.Monster, faction, entityIcons.get(2), 4));
        entitySetsList.add(new EntitySet(core, "Dark Young", Category.Monster, faction, entityIcons.get(3), 3));
        entitySetsList.add(new EntitySet(core, "Shub-Niggurath", Category.GOO, faction, entityIcons.get(4), 1));
        getEntitySetByName("Cultist").iconOnGate = Core.getImage("images/Entities/Gates with Acolyte (red).png");
        getEntitySetByName("Dark Young").iconOnGate = Core.getImage("images/Entities/Gates with Dark Young.png");
    }

    static int getCombatShubNiggurath(Core core) {
        return core.factionBase.getFactionFromEnum(FactionType.BlackGoat).getEntitySetByName("Cultist").positions.size()
                + core.gates.getNumOfControlledGates(FactionType.BlackGoat);
    }

    @Override
    void fillBooks() {
       books.add(new Book(0, FactionType.BlackGoat, "Blood Sacrifice"));
       books.add(new Book(1, FactionType.BlackGoat, "Frenzy"));
       books.add(new Book(2, FactionType.BlackGoat, "Ghroth"));
       books.add(new Book(3, FactionType.BlackGoat, "Necrophagy"));
       books.add(new Book(4, FactionType.BlackGoat, "The Red Sign"));
       books.add(new Book(5, FactionType.BlackGoat, "The Thousand Young"));
    }

    @Override
    boolean isQuestCompleted(int questNum) {
        if(questNum <= 2){
            int count = 0;
            for(Location location: super.core.map.locations){
                if(getEntitiesInLocation(location).size() != 0) count ++;
            }
            if(questNum == 0 && count >= 4) return true;
            if(questNum == 1 && count >= 6) return true;
            if(questNum == 2 && count >= 8) return true;
        }
        if(questNum == 3){
            return core.var.didBlackGoatKillTwoCultists;
        }
        if(questNum == 4){
            ArrayList<FactionType> factions = core.var.factionsList;
            for(FactionType faction : factions){
                boolean ok = false;
                if(faction != FactionType.BlackGoat){
                    for(Location location: super.core.map.locations){
                        if(core.factionBase.getFactionFromEnum(faction).getEntitiesInLocation(location).size() != 0
                        && getEntitiesInLocation(location).size() != 0) ok = true;
                    }
                }
                if(!ok) return false;
            }
            return true;
        }
        if(questNum == 5){
            return(getEntitySetByName("Shub-Niggurath").positions.size() != 0);
        }
        return false;
    }
}
