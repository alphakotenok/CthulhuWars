package Model;

import java.util.*;

import Model.Location.Segment;
import javafx.scene.image.Image;

public class GameMap {

    ArrayList<Location> locations;
    Image mapIcon;
    static int minNumOfPlayers = 2;
    static int maxNumOfPlayers = 6;

    ArrayList<Segment> BeringSea() {
        ArrayList<Segment> BeringSea = new ArrayList<>();
        BeringSea.add(new Segment(10, 20, 420, 20));
        BeringSea.add(new Segment(20, 55, 385, 25));
        return BeringSea;
    }

    ArrayList<Segment> ArcticOceanSmall() {
        ArrayList<Segment> ArcticOceanSmall = new ArrayList<>();
        ArcticOceanSmall.add(new Segment(450, 15, 1120, 15));
        ArcticOceanSmall.add(new Segment(460, 60, 795, 60));
        ArcticOceanSmall.add(new Segment(930, 50, 1160, 50));
        return ArcticOceanSmall;
    }

    ArrayList<Segment> NorthAtlanticSmall() {
        ArrayList<Segment> NorthAtlanticSmall = new ArrayList<>();
        NorthAtlanticSmall.add(new Segment(475, 185, 690, 125));
        NorthAtlanticSmall.add(new Segment(320, 320, 600, 230));
        NorthAtlanticSmall.add(new Segment(440, 360, 650, 290));
        NorthAtlanticSmall.add(new Segment(550, 385, 680, 335));
        return NorthAtlanticSmall;
    }

    ArrayList<Segment> MediterraneanSea() {
        ArrayList<Segment> MediterraneanSea = new ArrayList<>();
        MediterraneanSea.add(new Segment(695, 235, 920, 285));
        MediterraneanSea.add(new Segment(665, 245, 710, 320));
        return MediterraneanSea;
    }

    ArrayList<Segment> AntarcticaSmall() {
        ArrayList<Segment> AntarcticaSmall = new ArrayList<>();
        AntarcticaSmall.add(new Segment(615, 550, 780, 590));
        AntarcticaSmall.add(new Segment(580, 580, 750, 630));
        AntarcticaSmall.add(new Segment(555, 610, 620, 630));
        return AntarcticaSmall;
    }

    ArrayList<Segment> MountainsofMadness() {
        ArrayList<Segment> MountainsofMadness = new ArrayList<>();
        MountainsofMadness.add(new Segment(790, 625, 1100, 625));
        MountainsofMadness.add(new Segment(795, 600, 880, 600));
        MountainsofMadness.add(new Segment(950, 600, 1080, 600));
        return MountainsofMadness;
    }

    ArrayList<Segment> NorthAmericaEast() {
        ArrayList<Segment> NorthAmericaEast = new ArrayList<>();
        NorthAmericaEast.add(new Segment(280, 60, 410, 200));
        NorthAmericaEast.add(new Segment(425, 175, 500, 105));
        return NorthAmericaEast;
    }

    ArrayList<Segment> NorthAmericaWest() {
        ArrayList<Segment> NorthAmericaWest = new ArrayList<>();
        NorthAmericaWest.add(new Segment(150, 90, 265, 90));
        NorthAmericaWest.add(new Segment(170, 150, 330, 145));
        NorthAmericaWest.add(new Segment(160, 205, 360, 205));
        NorthAmericaWest.add(new Segment(255, 250, 375, 250));
        return NorthAmericaWest;
    }

    ArrayList<Segment> CentralAmerica() {
        ArrayList<Segment> CentralAmerica = new ArrayList<>();
        CentralAmerica.add(new Segment(170, 235, 235, 330));
        CentralAmerica.add(new Segment(205, 320, 290, 350));
        return CentralAmerica;
    }

    ArrayList<Segment> SouthAmericaWest() {
        ArrayList<Segment> SouthAmericaWest = new ArrayList<>();
        SouthAmericaWest.add(new Segment(320, 425, 420, 555));
        SouthAmericaWest.add(new Segment(360, 400, 450, 520));
        return SouthAmericaWest;
    }

    ArrayList<Segment> SouthAmericaEast() {
        ArrayList<Segment> SouthAmericaEast = new ArrayList<>();
        SouthAmericaEast.add(new Segment(420, 410, 560, 410));
        SouthAmericaEast.add(new Segment(440, 450, 550, 450));
        SouthAmericaEast.add(new Segment(450, 480, 520, 480));
        return SouthAmericaEast;
    }

    ArrayList<Segment> AustraliaSmall() {
        ArrayList<Segment> AustraliaSmall = new ArrayList<>();
        AustraliaSmall.add(new Segment(35, 445, 150, 445));
        AustraliaSmall.add(new Segment(30, 490, 150, 490));
        AustraliaSmall.add(new Segment(30, 520, 105, 520));
        return AustraliaSmall;
    }

    ArrayList<Segment> NewZealand() {
        ArrayList<Segment> NewZealand = new ArrayList<>();
        NewZealand.add(new Segment(130, 405, 240, 525));
        NewZealand.add(new Segment(150, 490, 210, 550));
        NewZealand.add(new Segment(120, 520, 170, 570));
        return NewZealand;
    }

    ArrayList<Segment> Asia() {
        ArrayList<Segment> Asia = new ArrayList<>();
        Asia.add(new Segment(1015, 115, 1210, 115));
        Asia.add(new Segment(980, 180, 1210, 180));
        Asia.add(new Segment(970, 250, 1240, 250));
        Asia.add(new Segment(945, 315, 1230, 315));
        return Asia;
    }

    ArrayList<Segment> Africa() {
        ArrayList<Segment> Africa = new ArrayList<>();
        Africa.add(new Segment(720, 330, 885, 330));
        Africa.add(new Segment(705, 390, 960, 390));
        Africa.add(new Segment(745, 440, 1020, 440));
        Africa.add(new Segment(830, 500, 1000, 500));
        return Africa;
    }

    ArrayList<Segment> NorthAmerica() {
        ArrayList<Segment> NorthAmerica = new ArrayList<>();
        NorthAmerica.add(new Segment(185, 185, 450, 185));
        NorthAmerica.add(new Segment(170, 130, 400, 130));
        NorthAmerica.add(new Segment(115, 75, 395, 75));
        NorthAmerica.add(new Segment(190, 255, 325, 255));
        return NorthAmerica;
    }

    ArrayList<Segment> ArcticOcean() {
        ArrayList<Segment> ArcticOcean = new ArrayList<>();
        ArcticOcean.add(new Segment(90, 20, 320, 20));
        ArcticOcean.add(new Segment(445, 20, 785, 20));
        ArcticOcean.add(new Segment(505, 70, 690, 70));
        ArcticOcean.add(new Segment(940, 20, 1150, 20));
        return ArcticOcean;
    }

    ArrayList<Segment> Scandinavia() {
        ArrayList<Segment> Scandinavia = new ArrayList<>();
        Scandinavia.add(new Segment(785, 100, 985, 100));
        Scandinavia.add(new Segment(760, 125, 980, 125));
        Scandinavia.add(new Segment(875, 60, 910, 60));
        return Scandinavia;
    }

    ArrayList<Segment> NorthAsia() {
        ArrayList<Segment> NorthAsia = new ArrayList<>();
        NorthAsia.add(new Segment(1025, 90, 1215, 90));
        NorthAsia.add(new Segment(990, 125, 1210, 125));
        NorthAsia.add(new Segment(975, 165, 1070, 165));
        return NorthAsia;
    }

    ArrayList<Segment> EuropeSmall() {
        ArrayList<Segment> EuropeSmall = new ArrayList<>();
        EuropeSmall.add(new Segment(700, 165, 950, 165));
        EuropeSmall.add(new Segment(640, 195, 950, 195));
        EuropeSmall.add(new Segment(880, 245, 950, 245));
        return EuropeSmall;
    }

    ArrayList<Segment> Arabia() {
        ArrayList<Segment> Arabia = new ArrayList<>();
        Arabia.add(new Segment(985, 225, 1085, 225));
        Arabia.add(new Segment(960, 280, 1095, 280));
        Arabia.add(new Segment(950, 330, 1115, 330));
        Arabia.add(new Segment(995, 380, 1070, 380));
        return Arabia;
    }

    ArrayList<Segment> SouthAsia() {
        ArrayList<Segment> SouthAsia = new ArrayList<>();
        SouthAsia.add(new Segment(1100, 190, 1220, 190));
        SouthAsia.add(new Segment(1105, 235, 1240, 235));
        SouthAsia.add(new Segment(1115, 280, 1255, 280));
        SouthAsia.add(new Segment(1140, 320, 1245, 320));
        return SouthAsia;
    }

    ArrayList<Segment> NorthPacific() {
        ArrayList<Segment> NorthPacific = new ArrayList<>();
        NorthPacific.add(new Segment(5, 150, 150, 150));
        NorthPacific.add(new Segment(5, 215, 125, 215));
        NorthPacific.add(new Segment(5, 270, 140, 270));
        NorthPacific.add(new Segment(5, 320, 160, 320));
        return NorthPacific;
    }

    ArrayList<Segment> NorthAtlantic() {
        ArrayList<Segment> NorthAtlantic = new ArrayList<>();
        NorthAtlantic.add(new Segment(505, 170, 600, 170));
        NorthAtlantic.add(new Segment(430, 230, 600, 230));
        NorthAtlantic.add(new Segment(405, 290, 720, 290));
        NorthAtlantic.add(new Segment(400, 340, 690, 340));
        return NorthAtlantic;
    }

    ArrayList<Segment> WestAfrica() {
        ArrayList<Segment> WestAfrica = new ArrayList<>();
        WestAfrica.add(new Segment(745, 320, 870, 320));
        WestAfrica.add(new Segment(710, 365, 920, 365));
        WestAfrica.add(new Segment(715, 415, 855, 415));
        return WestAfrica;
    }

    ArrayList<Segment> EastAfrica() {
        ArrayList<Segment> EastAfrica = new ArrayList<>();
        EastAfrica.add(new Segment(880, 410, 1000, 410));
        EastAfrica.add(new Segment(840, 470, 995, 470));
        EastAfrica.add(new Segment(850, 520, 970, 520));
        return EastAfrica;
    }

    ArrayList<Segment> IndianOcean() {
        ArrayList<Segment> IndianOcean = new ArrayList<>();
        IndianOcean.add(new Segment(1080, 415, 1250, 415));
        IndianOcean.add(new Segment(1050, 480, 1260, 480));
        IndianOcean.add(new Segment(1040, 545, 1260, 545));
        IndianOcean.add(new Segment(1110, 600, 1260, 600));
        return IndianOcean;
    }

    ArrayList<Segment> Australia() {
        ArrayList<Segment> Australia = new ArrayList<>();
        Australia.add(new Segment(55, 435, 175, 435));
        Australia.add(new Segment(30, 485, 220, 485));
        Australia.add(new Segment(130, 525, 235, 525));
        return Australia;
    }

    ArrayList<Segment> SouthPacific() {
        ArrayList<Segment> SouthPacific = new ArrayList<>();
        SouthPacific.add(new Segment(240, 460, 315, 460));
        SouthPacific.add(new Segment(250, 505, 345, 505));
        SouthPacific.add(new Segment(230, 560, 380, 560));
        SouthPacific.add(new Segment(205, 610, 515, 610));
        return SouthPacific;
    }

    ArrayList<Segment> SouthAmerica() {
        ArrayList<Segment> SouthAmerica = new ArrayList<>();
        SouthAmerica.add(new Segment(345, 425, 540, 425));
        SouthAmerica.add(new Segment(355, 470, 510, 470));
        SouthAmerica.add(new Segment(380, 520, 450, 520));
        return SouthAmerica;
    }

    ArrayList<Segment> SouthAtlantic() {
        ArrayList<Segment> SouthAtlantic = new ArrayList<>();
        SouthAtlantic.add(new Segment(590, 440, 695, 440));
        SouthAtlantic.add(new Segment(560, 485, 785, 485));
        SouthAtlantic.add(new Segment(545, 525, 815, 525));
        return SouthAtlantic;
    }

    ArrayList<Segment> Antarctica() {
        ArrayList<Segment> Antarctica = new ArrayList<>();
        Antarctica.add(new Segment(600, 565, 755, 565));
        Antarctica.add(new Segment(585, 620, 1060, 620));
        return Antarctica;
    }

    ArrayList<Segment> Europe() {
        ArrayList<Segment> Europe = new ArrayList<>();
        Europe.add(new Segment(750, 110, 980, 110));
        Europe.add(new Segment(750, 150, 965, 150));
        Europe.add(new Segment(620, 190, 950, 190));
        return Europe;
    }

    GameMap(int numOfPlayers) {
        locations = new ArrayList<>();
        if (numOfPlayers == 2 || numOfPlayers == 3) {
            locations.add(new Location("Europe", Europe()));
            locations.add(new Location("Arctic Ocean", ArcticOcean()));
            locations.add(new Location("North America", NorthAmerica()));
            locations.add(new Location("North Pacific", NorthPacific()));
            locations.add(new Location("North Atlantic", NorthAtlantic()));
            locations.add(new Location("Asia", Asia()));
            locations.add(new Location("Indian Ocean", IndianOcean()));
            locations.add(new Location("Australia", Australia()));
            locations.add(new Location("South Pacific", SouthPacific()));
            locations.add(new Location("South America", SouthAmerica()));
            locations.add(new Location("South Atlantic", SouthAtlantic()));
            locations.add(new Location("Antarctica", Antarctica()));
            locations.add(new Location("Africa", Africa()));
        }
        if (numOfPlayers == 4) {
            locations.add(new Location("Europe", EuropeSmall()));
            locations.add(new Location("Scandinavia", Scandinavia()));
            locations.add(new Location("Arctic Ocean", ArcticOcean()));
            locations.add(new Location("North America", NorthAmerica()));
            locations.add(new Location("North Pacific", NorthPacific()));
            locations.add(new Location("North Atlantic", NorthAtlantic()));
            locations.add(new Location("North Asia", NorthAsia()));
            locations.add(new Location("Arabia", Arabia()));
            locations.add(new Location("South Asia", SouthAsia()));
            locations.add(new Location("Indian Ocean", IndianOcean()));
            locations.add(new Location("Australia", Australia()));
            locations.add(new Location("South Pacific", SouthPacific()));
            locations.add(new Location("South America", SouthAmerica()));
            locations.add(new Location("South Atlantic", SouthAtlantic()));
            locations.add(new Location("Antarctica", Antarctica()));
            locations.add(new Location("West Africa", WestAfrica()));
            locations.add(new Location("East Africa", EastAfrica()));
        }
        if (numOfPlayers == 5) {
            locations.add(new Location("Europe", EuropeSmall()));
            locations.add(new Location("Scandinavia", Scandinavia()));
            locations.add(new Location("Arctic Ocean", ArcticOcean()));
            locations.add(new Location("North America East", NorthAmericaEast()));
            locations.add(new Location("North America West", NorthAmericaWest()));
            locations.add(new Location("Central America", CentralAmerica()));
            locations.add(new Location("North Pacific", NorthPacific()));
            locations.add(new Location("North Atlantic", NorthAtlantic()));
            locations.add(new Location("North Asia", NorthAsia()));
            locations.add(new Location("Arabia", Arabia()));
            locations.add(new Location("South Asia", SouthAsia()));
            locations.add(new Location("Indian Ocean", IndianOcean()));
            locations.add(new Location("Australia", AustraliaSmall()));
            locations.add(new Location("New Zealand", NewZealand()));
            locations.add(new Location("South Pacific", SouthPacific()));
            locations.add(new Location("South America East", SouthAmericaEast()));
            locations.add(new Location("South America West", SouthAmericaWest()));
            locations.add(new Location("South Atlantic", SouthAtlantic()));
            locations.add(new Location("Antarctica", Antarctica()));
            locations.add(new Location("West Africa", WestAfrica()));
            locations.add(new Location("East Africa", EastAfrica()));
        }
        if (numOfPlayers == 6) {
            locations.add(new Location("Europe", EuropeSmall()));
            locations.add(new Location("Scandinavia", Scandinavia()));
            locations.add(new Location("Arctic Ocean", ArcticOceanSmall()));
            locations.add(new Location("Bering Sea", BeringSea()));
            locations.add(new Location("North America East", NorthAmericaEast()));
            locations.add(new Location("North America West", NorthAmericaWest()));
            locations.add(new Location("Central America", CentralAmerica()));
            locations.add(new Location("North Pacific", NorthPacific()));
            locations.add(new Location("North Atlantic", NorthAtlanticSmall()));
            locations.add(new Location("Mediterranean Sea", MediterraneanSea()));
            locations.add(new Location("North Asia", NorthAsia()));
            locations.add(new Location("Arabia", Arabia()));
            locations.add(new Location("South Asia", SouthAsia()));
            locations.add(new Location("Indian Ocean", IndianOcean()));
            locations.add(new Location("Australia", AustraliaSmall()));
            locations.add(new Location("New Zealand", NewZealand()));
            locations.add(new Location("South Pacific", SouthPacific()));
            locations.add(new Location("South America East", SouthAmericaEast()));
            locations.add(new Location("South America West", SouthAmericaWest()));
            locations.add(new Location("South Atlantic", SouthAtlantic()));
            locations.add(new Location("Antarctica", AntarcticaSmall()));
            locations.add(new Location("Mountains of Madness", MountainsofMadness()));
            locations.add(new Location("West Africa", WestAfrica()));
            locations.add(new Location("East Africa", EastAfrica()));
        }
    }

    ArrayList<Entity> getEntityListInLocation(Location location) {
        return location.entityList;
    }
}
