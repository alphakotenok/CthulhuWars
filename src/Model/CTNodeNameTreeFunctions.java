package Model;

import java.util.ArrayList;

import Model.FactionEnum.FactionType;

class CTNodeNameTreeFunctions {
    static NodeInterfaces.NodeNameFunctionContainer constName(String name) {
        return (core -> name);
    }

    static String startLocSelection(Core core) {
        return "Choose " + core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(core.var.turn))
                + " start location";
    }

    static String actionSelection(Core core) {
        return core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(core.var.turn)) + " action";
    }

    static String ritualText(Core core) {
        return core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(core.var.turn))
                + " can perform ritual";
    }

    static String receiveSpellbookText(Core core) {
        return core.factionBase.getFactionNameFromEnum(core.var.bookReceiver) + " receive spellbook";
    }

    static String firstPlayerSelectionText(Core core) {
        if (CTEdgeCreatorTreeChecker.isfirstPlayerDetermined(core)) {
            return core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(core.var.firstPlayer))
                    + " choose the order of moves in the next round";
        }
        return core.factionBase.getFactionNameFromEnum(core.var.factionsList.get(core.var.firstPlayer))
                + " choose the first player in the next round";
    }

    static String viewElderSignsText(Core core) {
        return core.getCurFact().name + " Elder Signs";
    }

    static String firstPlayerAttackText(Core core) {
        return core.getCurFact().name + " does special pre attack actions";
    }

    static String secondPlayerAttackText(Core core) {
        return core.var.factionsList.get(core.var.chosenFaction).name() + " does special pre attack actions";
    }

    static String firstPlayerAttackPostText(Core core) {
        return core.getCurFact().name + " does special post attack actions";
    }

    static String secondPlayerAttackPostText(Core core) {
        return core.var.factionsList.get(core.var.chosenFaction).name() + " does special post attack actions";
    }

    static String firstPlayerThrowsDiceText(Core core) {
        String s = core.getCurFact().name + " throws " + core.getCurFact().totalCombat() + " dice";
        if (core.getCurFact().totalCombat() > 1)
            s += "s";
        return s;
    }

    static String secondPlayerThrowsDiceText(Core core) {
        String s = core.var.factionsList.get(core.var.chosenFaction).name() + " throws " +
                core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction)).totalCombat()
                + " dice";
        if (core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction)).totalCombat() > 1)
            s += "s";
        return s;
    }

    static String resultThrowsText(Core core) {
        int amountFirst = core.getCurFact().totalCombat();
        int amountSecond = core.factionBase.getFactionFromEnum(core.var.factionsList.get(core.var.chosenFaction))
                .totalCombat();
        core.var.throwDice(1, amountFirst);
        core.var.throwDice(2, amountSecond);
        String s = core.getCurFact().name + " kills " + core.var.firstPlayerKill + ", injures "
                + core.var.firstPlayerInjure + ", misses " + core.var.firstPlayerNothing
                + "; " + core.var.factionsList.get(core.var.chosenFaction).name() + " kills "
                + core.var.secondPlayerKill
                + ", injures " + core.var.secondPlayerInjure + ", misses " + core.var.secondPlayerNothing;
        return s;
    }

    static String firstPlayerKillEntityText(Core core) {
        String s = core.getCurFact().name + " kills his entity";
        return s;
    }

    static String secondPlayerKillEntityText(Core core) {
        String s = core.var.factionsList.get(core.var.chosenFaction).name() + " kills his entity";
        return s;
    }

    static String firstPlayerInjureEntityText(Core core) {
        String s = core.getCurFact().name + " injures his entity";
        return s;
    }

    static String secondPlayerInjureEntityText(Core core) {
        String s = core.var.factionsList.get(core.var.chosenFaction).name() + " injures his entity";
        return s;
    }
    static String winnerText(Core core) {
        String s = "";
        ArrayList<FactionType> winners = new ArrayList<>();
        ArrayList<FactionType> factions = core.var.factionsList;
        for (FactionType faction : factions) {
            Boolean winner = true;
            for (int i = 0; i < 6; i++) {
                if (!core.factionBase.getFactionFromEnum(faction).isBookOpened(i)) {
                    winner = false;
                }
                if (winner) {
                    winners.add(faction);
                }
            }
        }

        if (winners.size() == 0)
            s = "Nobody won";
        else {
            int maxDoom = 0;
            for (FactionType faction : winners) {
                maxDoom = Integer.max(maxDoom, core.factionBase.getFactionFromEnum(faction).victoryPoints);
            }

            for (FactionType faction : winners) {
                if (core.factionBase.getFactionFromEnum(faction).victoryPoints == maxDoom) {
                    if (s.length() != 0) {
                        s += ", ";
                    }
                    s += core.factionBase.getFactionNameFromEnum(faction);
                }
            }

            s += " won";
        }

        return s;
    }
}
