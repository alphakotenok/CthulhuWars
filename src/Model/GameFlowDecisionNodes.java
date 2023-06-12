package Model;

import Model.CommandTree.Node;

class GameFlowDecisionNodes {
    Node startNode;
    Node startLocChooseNode;
    Node actionChooseNode;
    Node firstPlayerSelectionNode;
    Node openBookNode;
    Node ritualNode;
    Node viewElderSignsNode;  
    Node finishGame;
    
    GameFlowDecisionNodes(CommandTree ct){
        startNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose players order"));
        startLocChooseNode = ct.new Node(CTNodeNameTreeFunctions::startLocSelection);
        actionChooseNode = ct.new Node(CTNodeNameTreeFunctions::actionSelection);
        firstPlayerSelectionNode = ct.new Node(CTNodeNameTreeFunctions::firstPlayerSelectionText);
        openBookNode = ct.new Node(CTNodeNameTreeFunctions::receiveSpellbookText);
        ritualNode = ct.new Node(CTNodeNameTreeFunctions::ritualText);
        viewElderSignsNode = ct.new Node(CTNodeNameTreeFunctions::viewElderSignsText);  
        finishGame = ct.new Node(CTNodeNameTreeFunctions::winnerText);
    }
}
