package Model;

import Model.CommandTree.Node;

class ActionNodes {
    Node chooseEntityToMoveNode;
    Node chooseLocationToMoveNode;
    Node chooseLocationToBuildGate;
    Node chooseEntityToSpawnNode;
    Node chooseLocationToSpawnNode;
    Node captureNode;
    Node blackGoatKillTwoCultistsNode;
    Node sleeperPresentEnergyNode;
    Node attackLocationNode;
    Node attackFactionNode;

    ActionNodes(CommandTree ct){
        chooseEntityToMoveNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose Unit to move for 1 power"));
        chooseLocationToMoveNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose destination"));
        chooseLocationToBuildGate = ct.new Node(
                CTNodeNameTreeFunctions.constName("Choose where to build a Gate for 3 power"));
        chooseEntityToSpawnNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose Unit to spawn"));
        chooseLocationToSpawnNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose where to spawn"));
        captureNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose Unit to capture"));
        blackGoatKillTwoCultistsNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose two cultists to kill"));
        sleeperPresentEnergyNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose faction, who gets 3 power"));   
        attackLocationNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose location you want to attack"));
        attackFactionNode = ct.new Node(CTNodeNameTreeFunctions.constName("Choose faction you want to attack")); 
    }
}
