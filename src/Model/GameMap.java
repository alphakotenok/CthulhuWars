package Model;

import java.util.*;

import Model.FactionEnum.FactionType;
import Model.Location.Segment;
import javafx.scene.image.Image;

class GameMap {

    ArrayList<Location> locations;
    Image mapIcon;
    Core core;
    ArrayList<ArrayList<Location>> startLoc = new ArrayList<>();

    ArrayList<Segment> BeringSea() {
        ArrayList<Segment> BeringSea = new ArrayList<>();
        BeringSea.add(new Segment(10, 20, 420, 20));
        BeringSea.add(new Segment(20, 35, 385, 35));
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
        AntarcticaSmall.add(new Segment(580, 580, 750, 600));
        AntarcticaSmall.add(new Segment(555, 600, 620, 600));
        return AntarcticaSmall;
    }

    ArrayList<Segment> MountainsofMadness() {
        ArrayList<Segment> MountainsofMadness = new ArrayList<>();
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
        NorthAmericaWest.add(new Segment(255, 240, 375, 240));
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
        NewZealand.add(new Segment(150, 405, 240, 525));
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
        Africa.add(new Segment(730, 390, 960, 390));
        Africa.add(new Segment(830, 440, 900, 440));
        Africa.add(new Segment(880, 490, 950, 490));
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
        ArcticOcean.add(new Segment(120, 20, 260, 20));
        ArcticOcean.add(new Segment(475, 20, 755, 20));
        ArcticOcean.add(new Segment(970, 20, 1120, 20));
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
        IndianOcean.add(new Segment(1100, 415, 1250, 415));
        IndianOcean.add(new Segment(1050, 480, 1260, 480));
        IndianOcean.add(new Segment(1040, 545, 1260, 545));
        IndianOcean.add(new Segment(1110, 600, 1260, 600));
        return IndianOcean;
    }

    ArrayList<Segment> Australia() {
        ArrayList<Segment> Australia = new ArrayList<>();
        Australia.add(new Segment(75, 435, 155, 435));
        Australia.add(new Segment(80, 485, 200, 485));
        Australia.add(new Segment(150, 525, 215, 525));
        return Australia;
    }

    ArrayList<Segment> SouthPacific() {
        ArrayList<Segment> SouthPacific = new ArrayList<>();
        SouthPacific.add(new Segment(260, 460, 295, 460));
        SouthPacific.add(new Segment(270, 505, 325, 505));
        SouthPacific.add(new Segment(250, 560, 360, 560));
        SouthPacific.add(new Segment(225, 610, 495, 610));
        return SouthPacific;
    }

    ArrayList<Segment> SouthAmerica() {
        ArrayList<Segment> SouthAmerica = new ArrayList<>();
        SouthAmerica.add(new Segment(365, 425, 520, 425));
        SouthAmerica.add(new Segment(385, 470, 490, 470));
        SouthAmerica.add(new Segment(400, 520, 430, 520));
        return SouthAmerica;
    }

    ArrayList<Segment> SouthAtlantic() {
        ArrayList<Segment> SouthAtlantic = new ArrayList<>();
        SouthAtlantic.add(new Segment(610, 440, 675, 440));
        SouthAtlantic.add(new Segment(580, 485, 765, 485));
        SouthAtlantic.add(new Segment(565, 525, 835, 525));
        return SouthAtlantic;
    }

    ArrayList<Segment> Antarctica() {
        ArrayList<Segment> Antarctica = new ArrayList<>();
        Antarctica.add(new Segment(620, 565, 735, 565));
        Antarctica.add(new Segment(605, 620, 1040, 620));
        return Antarctica;
    }

    ArrayList<Segment> Europe() {
        ArrayList<Segment> Europe = new ArrayList<>();
        Europe.add(new Segment(750, 90, 1000, 90));
        Europe.add(new Segment(750, 130, 970, 130));
        Europe.add(new Segment(620, 170, 980, 170));
        return Europe;
    }

    Location Europe = new Location("Europe", Europe());
    Location ArcticOcean = new Location("Arctic Ocean", ArcticOcean());
    Location NorthAmerica = new Location("North America", NorthAmerica());
    Location NorthPacific = new Location("North Pacific", NorthPacific());
    Location NorthAtlantic = new Location("North Atlantic", NorthAtlantic());
    Location Asia = new Location("Asia", Asia());
    Location IndianOcean = new Location("Indian Ocean", IndianOcean());
    Location Australia = new Location("Australia", Australia());
    Location SouthPacific = new Location("South Pacific", SouthPacific());
    Location SouthAmerica = new Location("South America", SouthAmerica());
    Location SouthAtlantic = new Location("South Atlantic", SouthAtlantic());
    Location Antarctica = new Location("Antarctica", Antarctica());
    Location Africa = new Location("Africa", Africa());
    Location EuropeSmall = new Location("Europe", EuropeSmall());
    Location Scandinavia = new Location("Scandinavia", Scandinavia());
    Location NorthAmericaEast = new Location("North America East", NorthAmericaEast());
    Location ArcticOceanSmall = new Location("Arctic Ocean", ArcticOceanSmall());
    Location BeringSea = new Location("Bering Sea", BeringSea());
    Location NorthAmericaWest = new Location("North America West", NorthAmericaWest());
    Location CentralAmerica = new Location("Central America", CentralAmerica());
    Location NorthAtlanticSmall = new Location("North Atlantic", NorthAtlanticSmall());
    Location MediterraneanSea = new Location("Mediterranean Sea", MediterraneanSea());
    Location NorthAsia = new Location("North Asia", NorthAsia());
    Location Arabia = new Location("Arabia", Arabia());
    Location SouthAsia = new Location("South Asia", SouthAsia());
    Location AustraliaSmall = new Location("Australia", AustraliaSmall());
    Location NewZealand = new Location("New Zealand", NewZealand());
    Location SouthAmericaEast = new Location("South America East", SouthAmericaEast());
    Location SouthAmericaWest = new Location("South America West", SouthAmericaWest());
    Location AntarcticaSmall = new Location("Antarctica", AntarcticaSmall());
    Location MountainsofMadness = new Location("Mountains of Madness", MountainsofMadness());
    Location WestAfrica = new Location("West Africa", WestAfrica());
    Location EastAfrica = new Location("East Africa", EastAfrica());

    void fillAdj(int numOfPlayers) {
        if (numOfPlayers <= 3) {
            Europe.adj.add(ArcticOcean);
            Europe.adj.add(NorthAtlantic);
            Europe.adj.add(Asia);

            Asia.adj.add(NorthPacific);
            Asia.adj.add(ArcticOcean);
            Asia.adj.add(Europe);
            Asia.adj.add(NorthAtlantic);
            Asia.adj.add(Africa);
            Asia.adj.add(IndianOcean);
        }

        if (numOfPlayers <= 5) {
            if (numOfPlayers <= 3) {
                ArcticOcean.adj.add(Asia);
                ArcticOcean.adj.add(Europe);
            }
            if (numOfPlayers <= 4)
                ArcticOcean.adj.add(NorthAmerica);
            ArcticOcean.adj.add(NorthPacific);
            ArcticOcean.adj.add(NorthAtlantic);
            if (numOfPlayers >= 4) {
                ArcticOcean.adj.add(Scandinavia);
                ArcticOcean.adj.add(NorthAsia);
            }
            if (numOfPlayers >= 5) {
                ArcticOcean.adj.add(NorthAmericaEast);
                ArcticOcean.adj.add(NorthAmericaWest);
            }
        }

        if (numOfPlayers >= 6) {
            ArcticOceanSmall.adj.add(NorthAmericaEast);
            ArcticOceanSmall.adj.add(BeringSea);
            ArcticOceanSmall.adj.add(NorthAtlanticSmall);
            ArcticOceanSmall.adj.add(Scandinavia);
            ArcticOceanSmall.adj.add(NorthAsia);

            BeringSea.adj.add(NorthAsia);
            BeringSea.adj.add(NorthPacific);
            BeringSea.adj.add(NorthAmericaEast);
            BeringSea.adj.add(NorthAmericaWest);
        }

        if (numOfPlayers >= 4) {
            if (numOfPlayers <= 5) {
                Scandinavia.adj.add(ArcticOcean);
                Scandinavia.adj.add(NorthAtlantic);
            }
            Scandinavia.adj.add(NorthAsia);
            Scandinavia.adj.add(EuropeSmall);
            if (numOfPlayers >= 6) {
                Scandinavia.adj.add(ArcticOceanSmall);
                Scandinavia.adj.add(NorthAtlanticSmall);
            }

            if (numOfPlayers <= 5)
                NorthAsia.adj.add(ArcticOcean);
            NorthAsia.adj.add(Scandinavia);
            NorthAsia.adj.add(EuropeSmall);
            NorthAsia.adj.add(NorthPacific);
            NorthAsia.adj.add(SouthAsia);
            NorthAsia.adj.add(Arabia);
            if (numOfPlayers >= 6)
                NorthAsia.adj.add(ArcticOceanSmall);

            SouthAsia.adj.add(Arabia);
            SouthAsia.adj.add(NorthAsia);
            SouthAsia.adj.add(NorthPacific);
            SouthAsia.adj.add(IndianOcean);

            if (numOfPlayers <= 5)
                Arabia.adj.add(NorthAtlantic);
            Arabia.adj.add(EuropeSmall);
            Arabia.adj.add(NorthAsia);
            Arabia.adj.add(SouthAsia);
            Arabia.adj.add(IndianOcean);
            Arabia.adj.add(EastAfrica);
            Arabia.adj.add(WestAfrica);
            if (numOfPlayers >= 6)
                Arabia.adj.add(MediterraneanSea);

            if (numOfPlayers <= 5)
                EuropeSmall.adj.add(NorthAtlantic);
            EuropeSmall.adj.add(Scandinavia);
            EuropeSmall.adj.add(NorthAsia);
            EuropeSmall.adj.add(Arabia);
            if (numOfPlayers >= 6) {
                EuropeSmall.adj.add(NorthAtlanticSmall);
                EuropeSmall.adj.add(MediterraneanSea);
            }
        }

        if (numOfPlayers <= 3)
            NorthPacific.adj.add(Asia);
        if (numOfPlayers <= 4) {
            NorthPacific.adj.add(NorthAmerica);
            NorthPacific.adj.add(SouthAmerica);
        }
        if (numOfPlayers <= 5) {
            NorthPacific.adj.add(ArcticOcean);
            NorthPacific.adj.add(NorthAtlantic);
        }
        NorthPacific.adj.add(IndianOcean);
        NorthPacific.adj.add(SouthPacific);
        if (numOfPlayers >= 4) {
            NorthPacific.adj.add(NorthAsia);
            NorthPacific.adj.add(SouthAsia);
        }
        if (numOfPlayers >= 5) {
            NorthPacific.adj.add(NorthAmericaWest);
            NorthPacific.adj.add(CentralAmerica);
            NorthPacific.adj.add(SouthAmericaWest);
        }
        if (numOfPlayers >= 6) {
            NorthPacific.adj.add(BeringSea);
            NorthPacific.adj.add(NorthAtlanticSmall);
        }

        if (numOfPlayers <= 4) {
            NorthAmerica.adj.add(NorthAtlantic);
            NorthAmerica.adj.add(ArcticOcean);
            NorthAmerica.adj.add(SouthAmerica);
            NorthAmerica.adj.add(NorthPacific);
        }

        if (numOfPlayers >= 5) {
            if (numOfPlayers <= 5) {
                NorthAmericaEast.adj.add(NorthAtlantic);
                NorthAmericaEast.adj.add(ArcticOcean);
            }
            NorthAmericaEast.adj.add(NorthAmericaWest);
            if (numOfPlayers >= 6) {
                NorthAmericaEast.adj.add(NorthAtlanticSmall);
                NorthAmericaEast.adj.add(BeringSea);
                NorthAmericaEast.adj.add(ArcticOceanSmall);
            }

            if (numOfPlayers <= 5) {
                NorthAmericaWest.adj.add(ArcticOcean);
                NorthAmericaWest.adj.add(NorthAtlantic);
            }
            NorthAmericaWest.adj.add(NorthAmericaEast);
            NorthAmericaWest.adj.add(CentralAmerica);
            NorthAmericaWest.adj.add(NorthPacific);
            if (numOfPlayers >= 6) {
                NorthAmericaWest.adj.add(NorthAtlanticSmall);
                NorthAmericaWest.adj.add(BeringSea);
            }

            if (numOfPlayers <= 5)
                CentralAmerica.adj.add(NorthAtlantic);
            CentralAmerica.adj.add(NorthPacific);
            CentralAmerica.adj.add(NorthAmericaWest);
            CentralAmerica.adj.add(SouthAmericaEast);
            CentralAmerica.adj.add(SouthAmericaWest);
            if (numOfPlayers >= 6)
                CentralAmerica.adj.add(NorthAtlanticSmall);
        }

        if (numOfPlayers <= 5) {
            if (numOfPlayers <= 3) {
                NorthAtlantic.adj.add(Europe);
                NorthAtlantic.adj.add(Asia);
                NorthAtlantic.adj.add(Africa);
            }
            if (numOfPlayers <= 4) {
                NorthAtlantic.adj.add(NorthAmerica);
                NorthAtlantic.adj.add(SouthAmerica);
            }
            NorthAtlantic.adj.add(NorthPacific);
            NorthAtlantic.adj.add(ArcticOcean);
            NorthAtlantic.adj.add(SouthAtlantic);
            if (numOfPlayers >= 4) {
                NorthAtlantic.adj.add(Scandinavia);
                NorthAtlantic.adj.add(EuropeSmall);
                NorthAtlantic.adj.add(Arabia);
                NorthAtlantic.adj.add(WestAfrica);
            }
            if (numOfPlayers >= 5) {
                NorthAtlantic.adj.add(NorthAmericaEast);
                NorthAtlantic.adj.add(NorthAmericaWest);
                NorthAtlantic.adj.add(CentralAmerica);
                NorthAtlantic.adj.add(SouthAmericaEast);
            }
        }

        if (numOfPlayers >= 6) {
            NorthAtlanticSmall.adj.add(NorthAmericaEast);
            NorthAtlanticSmall.adj.add(NorthAmericaWest);
            NorthAtlanticSmall.adj.add(CentralAmerica);
            NorthAtlanticSmall.adj.add(ArcticOceanSmall);
            NorthAtlanticSmall.adj.add(Scandinavia);
            NorthAtlanticSmall.adj.add(EuropeSmall);
            NorthAtlanticSmall.adj.add(MediterraneanSea);
            NorthAtlanticSmall.adj.add(WestAfrica);
            NorthAtlanticSmall.adj.add(SouthAtlantic);
            NorthAtlanticSmall.adj.add(SouthAmericaEast);
            NorthAtlanticSmall.adj.add(NorthPacific);

            MediterraneanSea.adj.add(EuropeSmall);
            MediterraneanSea.adj.add(NorthAtlanticSmall);
            MediterraneanSea.adj.add(WestAfrica);
            MediterraneanSea.adj.add(Arabia);
        }

        if (numOfPlayers <= 3) {
            IndianOcean.adj.add(Asia);
            IndianOcean.adj.add(Africa);
        }
        if (numOfPlayers <= 4)
            IndianOcean.adj.add(Australia);
        if (numOfPlayers <= 5)
            IndianOcean.adj.add(Antarctica);
        IndianOcean.adj.add(NorthPacific);
        IndianOcean.adj.add(SouthAtlantic);
        IndianOcean.adj.add(SouthPacific);
        if (numOfPlayers >= 4) {
            IndianOcean.adj.add(SouthAsia);
            IndianOcean.adj.add(Arabia);
            IndianOcean.adj.add(EastAfrica);
        }
        if (numOfPlayers >= 5) {
            IndianOcean.adj.add(AustraliaSmall);
            IndianOcean.adj.add(NewZealand);
        }
        if (numOfPlayers >= 6)
            IndianOcean.adj.add(MountainsofMadness);

        if (numOfPlayers <= 4) {
            Australia.adj.add(SouthPacific);
            Australia.adj.add(IndianOcean);
        }

        if (numOfPlayers >= 5) {
            AustraliaSmall.adj.add(IndianOcean);
            AustraliaSmall.adj.add(NewZealand);

            NewZealand.adj.add(AustraliaSmall);
            NewZealand.adj.add(IndianOcean);
            NewZealand.adj.add(SouthPacific);
        }

        if (numOfPlayers <= 4) {
            SouthPacific.adj.add(Australia);
            SouthPacific.adj.add(SouthAmerica);
        }
        if (numOfPlayers <= 5)
            SouthPacific.adj.add(Antarctica);
        SouthPacific.adj.add(NorthPacific);
        SouthPacific.adj.add(SouthAtlantic);
        SouthPacific.adj.add(IndianOcean);
        if (numOfPlayers >= 5) {
            SouthPacific.adj.add(AustraliaSmall);
            SouthPacific.adj.add(SouthAmericaWest);
        }
        if (numOfPlayers >= 6)
            SouthPacific.adj.add(AntarcticaSmall);

        if (numOfPlayers <= 4) {
            SouthAmerica.adj.add(NorthAmerica);
            SouthAmerica.adj.add(NorthAtlantic);
            SouthAmerica.adj.add(SouthAtlantic);
            SouthAmerica.adj.add(SouthPacific);
            SouthAmerica.adj.add(NorthPacific);
        }

        if (numOfPlayers >= 5) {
            SouthAmericaWest.adj.add(CentralAmerica);
            SouthAmericaWest.adj.add(SouthAmericaEast);
            SouthAmericaWest.adj.add(SouthAtlantic);
            SouthAmericaWest.adj.add(SouthPacific);
            SouthAmericaWest.adj.add(NorthPacific);

            if (numOfPlayers <= 5)
                SouthAmericaEast.adj.add(NorthAtlantic);
            SouthAmericaEast.adj.add(CentralAmerica);
            SouthAmericaEast.adj.add(SouthAtlantic);
            SouthAmericaEast.adj.add(SouthAmericaWest);
            if (numOfPlayers >= 6)
                SouthAmericaEast.adj.add(NorthAtlanticSmall);
        }

        if (numOfPlayers <= 3)
            SouthAtlantic.adj.add(Africa);
        if (numOfPlayers <= 4)
            SouthAtlantic.adj.add(SouthAmerica);
        if (numOfPlayers <= 5) {
            SouthAtlantic.adj.add(NorthAtlantic);
            SouthAtlantic.adj.add(Antarctica);
        }
        SouthAtlantic.adj.add(IndianOcean);
        SouthAtlantic.adj.add(SouthPacific);
        if (numOfPlayers >= 4) {
            SouthAtlantic.adj.add(WestAfrica);
            SouthAtlantic.adj.add(EastAfrica);
        }
        if (numOfPlayers >= 5) {
            SouthAtlantic.adj.add(SouthAmericaEast);
            SouthAtlantic.adj.add(SouthAmericaWest);
        }
        if (numOfPlayers >= 6) {
            SouthAtlantic.adj.add(NorthAtlanticSmall);
            SouthAtlantic.adj.add(AntarcticaSmall);
            SouthAtlantic.adj.add(MountainsofMadness);
        }

        if (numOfPlayers <= 3) {
            Africa.adj.add(SouthAtlantic);
            Africa.adj.add(NorthAtlantic);
            Africa.adj.add(Asia);
            Africa.adj.add(IndianOcean);
        }

        if (numOfPlayers >= 4) {
            if (numOfPlayers <= 5)
                WestAfrica.adj.add(NorthAtlantic);
            WestAfrica.adj.add(Arabia);
            WestAfrica.adj.add(EastAfrica);
            WestAfrica.adj.add(SouthAtlantic);
            if (numOfPlayers >= 6) {
                WestAfrica.adj.add(NorthAtlanticSmall);
                WestAfrica.adj.add(MediterraneanSea);
            }

            EastAfrica.adj.add(SouthAtlantic);
            EastAfrica.adj.add(Arabia);
            EastAfrica.adj.add(IndianOcean);
            EastAfrica.adj.add(WestAfrica);
        }

        if (numOfPlayers <= 5) {
            Antarctica.adj.add(IndianOcean);
            Antarctica.adj.add(SouthAtlantic);
            Antarctica.adj.add(SouthPacific);
        }

        if (numOfPlayers >= 6) {
            AntarcticaSmall.adj.add(SouthPacific);
            AntarcticaSmall.adj.add(SouthAtlantic);
            AntarcticaSmall.adj.add(MountainsofMadness);

            MountainsofMadness.adj.add(AntarcticaSmall);
            MountainsofMadness.adj.add(SouthAtlantic);
            MountainsofMadness.adj.add(IndianOcean);
        }
    }

    GameMap(Core core) {
        this.core = core;
        int numOfPlayers = core.var.numOfPlayers;
        locations = new ArrayList<>();
        fillAdj(numOfPlayers);
        if (numOfPlayers <= 3)
            locations.add(Europe);
        if (numOfPlayers >= 4)
            locations.add(EuropeSmall);
        if (numOfPlayers >= 4)
            locations.add(Scandinavia);
        if (numOfPlayers <= 5)
            locations.add(ArcticOcean);
        if (numOfPlayers <= 4)
            locations.add(NorthAmerica);
        if (numOfPlayers >= 5)
            locations.add(NorthAmericaEast);
        if (numOfPlayers >= 5)
            locations.add(NorthAmericaWest);
        if (numOfPlayers == 6)
            locations.add(ArcticOceanSmall);
        if (numOfPlayers == 6)
            locations.add(BeringSea);
        if (numOfPlayers >= 5)
            locations.add(CentralAmerica);
        if (numOfPlayers == 6)
            locations.add(NorthAtlanticSmall);
        if (numOfPlayers == 6)
            locations.add(MediterraneanSea);
        if (numOfPlayers <= 5)
            locations.add(NorthAtlantic);
        if (numOfPlayers <= 3)
            locations.add(Asia);
        if (numOfPlayers >= 4)
            locations.add(NorthAsia);
        locations.add(NorthPacific);
        if (numOfPlayers >= 4)
            locations.add(Arabia);
        if (numOfPlayers >= 4)
            locations.add(SouthAsia);
        locations.add(IndianOcean);
        if (numOfPlayers >= 5)
            locations.add(AustraliaSmall);
        if (numOfPlayers >= 5)
            locations.add(NewZealand);
        if (numOfPlayers <= 4)
            locations.add(Australia);
        locations.add(SouthPacific);
        if (numOfPlayers >= 5)
            locations.add(SouthAmericaEast);
        if (numOfPlayers >= 5)
            locations.add(SouthAmericaWest);
        if (numOfPlayers <= 4)
            locations.add(SouthAmerica);
        locations.add(SouthAtlantic);
        if (numOfPlayers == 6)
            locations.add(AntarcticaSmall);
        if (numOfPlayers == 6)
            locations.add(MountainsofMadness);
        if (numOfPlayers <= 5)
            locations.add(Antarctica);
        if (numOfPlayers <= 3)
            locations.add(Africa);
        if (numOfPlayers >= 4)
            locations.add(EastAfrica);
        if (numOfPlayers >= 4)
            locations.add(WestAfrica);
        fillStartLocArray();
    }

    void fillStartLocArray() {
        ArrayList<Location> GreatCthulhu = new ArrayList<>();
        ArrayList<Location> CrawlingChaos = new ArrayList<>();
        ArrayList<Location> BlackGoat = new ArrayList<>();
        ArrayList<Location> YellowSign = new ArrayList<>();
        ArrayList<Location> OpenerOfTheWay = new ArrayList<>();
        ArrayList<Location> Sleeper = new ArrayList<>();
        ArrayList<Location> Windwalker = new ArrayList<>();
        for (int i = 0; i < locations.size(); i++) {
            String s = locations.get(i).name;
            if (s.equals("South Pacific") && core.var.factionsList.indexOf(FactionType.GreatCthulhu) != -1)
                GreatCthulhu.add(locations.get(i));
            else if ((s.equals("Asia") || s.equals("South Asia"))
                    && core.var.factionsList.indexOf(FactionType.CrawlingChaos) != -1)
                CrawlingChaos.add(locations.get(i));
            else if ((s.equals("Africa") || s.equals("West Africa"))
                    && core.var.factionsList.indexOf(FactionType.BlackGoat) != -1)
                BlackGoat.add(locations.get(i));
            else if (s.equals("Europe") && core.var.factionsList.indexOf(FactionType.YellowSign) != -1)
                YellowSign.add(locations.get(i));
            else if ((s.equals("North America") || s.equals("North America West"))
                    && core.var.factionsList.indexOf(FactionType.Sleeper) != -1)
                Sleeper.add(locations.get(i));
            else if ((s.equals("Arctic Ocean") || (s.equals("Antarctica") && core.var.numOfPlayers < 6)
                    || s.equals("Mountains of Madness")) && core.var.factionsList.indexOf(FactionType.Windwalker) != -1)
                Windwalker.add(locations.get(i));
            else
                OpenerOfTheWay.add(locations.get(i));
        }
        startLoc.add(GreatCthulhu);
        startLoc.add(CrawlingChaos);
        startLoc.add(BlackGoat);
        startLoc.add(YellowSign);
        startLoc.add(OpenerOfTheWay);
        startLoc.add(Sleeper);
        startLoc.add(Windwalker);
    }
}
