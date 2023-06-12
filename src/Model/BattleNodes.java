package Model;

import Model.CommandTree.Node;

class BattleNodes {
    Node firstThrowsDiceNode;
    Node secondThrowsDiceNode;
    Node resultThrowingDiceNode;
    Node firstPlayerKillEntityNode;
    Node secondPlayerKillEntityNode;
    Node firstPlayerInjureEntityNode;
    Node secondPlayerInjureEntityNode;
    Node firstPlayerInjurePositionNode;
    Node secondPlayerInjurePositionNode;
    BattleNodes(CommandTree ct){

        firstThrowsDiceNode = ct.new Node(CTNodeNameTreeFunctions::firstPlayerThrowsDiceText);
        secondThrowsDiceNode = ct.new Node(CTNodeNameTreeFunctions::secondPlayerThrowsDiceText);
        resultThrowingDiceNode = ct.new Node(CTNodeNameTreeFunctions::resultThrowsText);
        firstPlayerKillEntityNode = ct.new Node(CTNodeNameTreeFunctions::firstPlayerKillEntityText);
        secondPlayerKillEntityNode = ct.new Node(CTNodeNameTreeFunctions::secondPlayerKillEntityText);
        firstPlayerInjureEntityNode = ct.new Node(CTNodeNameTreeFunctions::firstPlayerInjureEntityText);
        secondPlayerInjureEntityNode = ct.new Node(CTNodeNameTreeFunctions::secondPlayerInjureEntityText);
        firstPlayerInjurePositionNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose position to move"));
        secondPlayerInjurePositionNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose position to move"));

    }
}
