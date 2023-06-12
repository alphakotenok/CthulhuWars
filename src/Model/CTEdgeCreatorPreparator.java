package Model;

class CTEdgeCreatorPreparator {
        static void prepareEdgeCreators(CommandTree ct) {
                ct.gameFlowDecisionNodes.openBookNode.addEdgeCreator(null, CTDataGeneratorTreeFunctions::booksToOpen,
                                CTEdgeNameTreeFunctions::bookToOpenName,
                                AccumulatorTreeFunctions::accumulateBookOpening,
                                CTEdgeTreeFunctions::openBook, CTEdgeCreatorTreeChecker::always);
                ct.gameFlowDecisionNodes.startNode.addEdgeCreator(ct.gameFlowDecisionNodes.startLocChooseNode,
                                CTDataGeneratorTreeFunctions::permutationGenerator,
                                CTEdgeNameTreeFunctions::permutationName, AccumulatorTreeFunctions::accumulatePerm,
                                CTEdgeTreeFunctions::permutateFactions,
                                CTEdgeCreatorTreeChecker::always);
                ct.gameFlowDecisionNodes.startLocChooseNode.addEdgeCreator(ct.gameFlowDecisionNodes.startLocChooseNode,
                                CTDataGeneratorTreeFunctions::startLocGenerator,
                                CTEdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateLocation,
                                CTEdgeTreeFunctions::startLocPlacement,
                                CTEdgeCreatorTreeChecker.opposite(CTEdgeCreatorTreeChecker::isLastPlayerDoing));
                ct.gameFlowDecisionNodes.startLocChooseNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::startLocGenerator,
                                CTEdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateLocation,
                                CTEdgeTreeFunctions::startLocLastPlacement,
                                CTEdgeCreatorTreeChecker::isLastPlayerDoing);
                ct.gameFlowDecisionNodes.actionChooseNode.addMover(ct.actionNodes.chooseEntityToMoveNode, "Move",
                                CTEdgeCreatorTreeChecker.isFirstAction(1));
                ct.gameFlowDecisionNodes.actionChooseNode.addMover(ct.actionNodes.chooseLocationToBuildGate,
                                "Build Gate", CTEdgeCreatorTreeChecker
                                                .combine(CTEdgeCreatorTreeChecker::canGateBeBuilt,
                                                                CTEdgeCreatorTreeChecker.isFirstAction(3)));
                ct.gameFlowDecisionNodes.actionChooseNode.addMover(ct.actionNodes.chooseEntityToSpawnNode, "Spawn",
                                CTEdgeCreatorTreeChecker
                                                .combine(CTEdgeCreatorTreeChecker::canSummon,
                                                                CTEdgeCreatorTreeChecker.isFirstAction(0)));
                ct.gameFlowDecisionNodes.actionChooseNode.addMover(ct.actionNodes.captureNode, "Capture",
                                CTEdgeCreatorTreeChecker
                                                .combine(CTEdgeCreatorTreeChecker::canCapture,
                                                                CTEdgeCreatorTreeChecker.isFirstAction(1)));
                ct.gameFlowDecisionNodes.actionChooseNode.addMover(ct.actionNodes.blackGoatKillTwoCultistsNode,
                                "Kill two cultists to get the spellbook",
                                CTEdgeCreatorTreeChecker.combine(CTEdgeCreatorTreeChecker.isFirstAction(0),
                                                CTEdgeCreatorTreeChecker::canBlackGoatKill));

                ct.gameFlowDecisionNodes.actionChooseNode.addEdgeCreator(ct.actionNodes.attackLocationNode,
                                CTDataGeneratorTreeFunctions::justOne, CTEdgeNameTreeFunctions.constName("Attack"),
                                AccumulatorTreeFunctions::none, CTEdgeTreeFunctions::attack,
                                CTEdgeCreatorTreeChecker::canAttack);

                ct.gameFlowDecisionNodes.actionChooseNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName("Spend 4 power to get the spellbook"),
                                AccumulatorTreeFunctions::none,
                                CTEdgeTreeFunctions::loseEnergy4,
                                CTEdgeCreatorTreeChecker.combine(CTEdgeCreatorTreeChecker.isFirstAction(4),
                                                CTEdgeCreatorTreeChecker::canLoseEnergy4));
                ct.gameFlowDecisionNodes.actionChooseNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName("Spend 6 power to get the spellbook"),
                                AccumulatorTreeFunctions::none,
                                CTEdgeTreeFunctions::loseEnergy6,
                                CTEdgeCreatorTreeChecker.combine(CTEdgeCreatorTreeChecker.isFirstAction(6),
                                                CTEdgeCreatorTreeChecker::canLoseEnergy6));
                ct.gameFlowDecisionNodes.actionChooseNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName("Spend 10 power to get two spellbooks"),
                                AccumulatorTreeFunctions::none,
                                CTEdgeTreeFunctions::loseEnergy10,
                                CTEdgeCreatorTreeChecker.combine(CTEdgeCreatorTreeChecker.isFirstAction(10),
                                                CTEdgeCreatorTreeChecker::canLoseEnergy10));
                ct.gameFlowDecisionNodes.actionChooseNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName(
                                                "Spend 3 power to get the spellbook(other players get 1 power)"),
                                AccumulatorTreeFunctions::none,
                                CTEdgeTreeFunctions::lose3EnergyAndGet1EnergyForOthers,
                                CTEdgeCreatorTreeChecker.combine(CTEdgeCreatorTreeChecker.isFirstAction(3),
                                                CTEdgeCreatorTreeChecker::canLose3EnergyAndGet1EnergyForOthers));
                ct.gameFlowDecisionNodes.actionChooseNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName(
                                                "Spend 3 power to get the spellbook(other players spend 1 power)"),
                                AccumulatorTreeFunctions::none,
                                CTEdgeTreeFunctions::lose3EnergyAnd1EnergyForOthers,
                                CTEdgeCreatorTreeChecker.combine(CTEdgeCreatorTreeChecker.isFirstAction(3),
                                                CTEdgeCreatorTreeChecker::canLose3EnergyAnd1EnergyForOthers));
                ct.gameFlowDecisionNodes.actionChooseNode.addMover(ct.actionNodes.sleeperPresentEnergyNode,
                                "Spend 3 power to get the spellbook(one player gets 3 power)", CTEdgeCreatorTreeChecker
                                                .combine(CTEdgeCreatorTreeChecker::canSpend3EnergySleeper,
                                                                CTEdgeCreatorTreeChecker.isFirstAction(3)));
                ct.gameFlowDecisionNodes.actionChooseNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName("Pass and lose remaining power"),
                                AccumulatorTreeFunctions::none,
                                CTEdgeTreeFunctions::passTurn,
                                CTEdgeCreatorTreeChecker.combine(
                                                CTEdgeCreatorTreeChecker
                                                                .opposite(CTEdgeCreatorTreeChecker::isLastPlayerDoing),
                                                CTEdgeCreatorTreeChecker.opposite(
                                                                CTEdgeCreatorTreeChecker::isActionPerformed)));
                ct.gameFlowDecisionNodes.actionChooseNode.addEdgeCreator(
                                ct.gameFlowDecisionNodes.firstPlayerSelectionNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName("Pass and lose remaining power"),
                                AccumulatorTreeFunctions::none,
                                CTEdgeTreeFunctions::lastPassTurn,
                                CTEdgeCreatorTreeChecker.combine(CTEdgeCreatorTreeChecker::isLastPlayerDoing,
                                                CTEdgeCreatorTreeChecker.opposite(
                                                                CTEdgeCreatorTreeChecker::isActionPerformed)));
                ct.gameFlowDecisionNodes.actionChooseNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName("Done"), AccumulatorTreeFunctions::none,
                                CTEdgeTreeFunctions::doneTurn, CTEdgeCreatorTreeChecker::isActionPerformed);
                ct.gameFlowDecisionNodes.firstPlayerSelectionNode.addEdgeCreator(
                                ct.gameFlowDecisionNodes.firstPlayerSelectionNode,
                                CTDataGeneratorTreeFunctions::firstPlayerCandidates,
                                CTEdgeNameTreeFunctions::factionName,
                                AccumulatorTreeFunctions::accumulateFirstPlayer, CTEdgeTreeFunctions::none,
                                CTEdgeCreatorTreeChecker.opposite(CTEdgeCreatorTreeChecker::isfirstPlayerDetermined));
                ct.gameFlowDecisionNodes.firstPlayerSelectionNode.addEdgeCreator(ct.gameFlowDecisionNodes.ritualNode,
                                CTDataGeneratorTreeFunctions::wayGenerator, CTEdgeNameTreeFunctions::wayName,
                                AccumulatorTreeFunctions::accumulateWay, CTEdgeTreeFunctions::none,
                                CTEdgeCreatorTreeChecker::isfirstPlayerDetermined);
                ct.gameFlowDecisionNodes.ritualNode.addEdgeCreator(ct.gameFlowDecisionNodes.ritualNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName("Perform ritual"),
                                AccumulatorTreeFunctions::none, CTEdgeTreeFunctions::performRitual,
                                CTEdgeCreatorTreeChecker::canPerformRitual);
                ct.gameFlowDecisionNodes.ritualNode.addEdgeCreator(ct.gameFlowDecisionNodes.viewElderSignsNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName("View Elder Signs"),
                                AccumulatorTreeFunctions::none, CTEdgeTreeFunctions::none,
                                CTEdgeCreatorTreeChecker::always);
                ct.gameFlowDecisionNodes.ritualNode.addEdgeCreator(ct.gameFlowDecisionNodes.ritualNode,
                                CTDataGeneratorTreeFunctions::justOne, CTEdgeNameTreeFunctions.constName("Done"),
                                AccumulatorTreeFunctions::none, CTEdgeTreeFunctions::doneRitual,
                                CTEdgeCreatorTreeChecker.opposite(CTEdgeCreatorTreeChecker::isLastPlayerDoing));
                ct.gameFlowDecisionNodes.ritualNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::justOne, CTEdgeNameTreeFunctions.constName("Done"),
                                AccumulatorTreeFunctions::none, CTEdgeTreeFunctions::lastDoneRitual,
                                CTEdgeCreatorTreeChecker.combine(CTEdgeCreatorTreeChecker::isLastPlayerDoing,
                                                CTEdgeCreatorTreeChecker
                                                                .opposite(CTEdgeCreatorTreeChecker::isGameFinish)));
                ct.gameFlowDecisionNodes.ritualNode.addMover(ct.gameFlowDecisionNodes.finishGame, "Finish game",
                                CTEdgeCreatorTreeChecker.combine(CTEdgeCreatorTreeChecker::isGameFinish,
                                                CTEdgeCreatorTreeChecker::isLastPlayerDoing));
                ct.gameFlowDecisionNodes.viewElderSignsNode.addEdgeCreator(ct.gameFlowDecisionNodes.viewElderSignsNode,
                                CTDataGeneratorTreeFunctions::elderSigns, CTEdgeNameTreeFunctions::elderSignRevealName,
                                AccumulatorTreeFunctions::accumulateSignReveal, CTEdgeTreeFunctions::revealElderSign,
                                CTEdgeCreatorTreeChecker::always);
                ct.gameFlowDecisionNodes.viewElderSignsNode.addMover(ct.gameFlowDecisionNodes.ritualNode, "Back",
                                CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.chooseEntityToMoveNode.addMover(ct.gameFlowDecisionNodes.actionChooseNode, "Cancel",
                                CTEdgeCreatorTreeChecker.isFirstAction(1));
                ct.actionNodes.chooseEntityToMoveNode.addMover(ct.gameFlowDecisionNodes.actionChooseNode, "Done",
                                CTEdgeCreatorTreeChecker.opposite(CTEdgeCreatorTreeChecker.isFirstAction(1)));
                ct.actionNodes.chooseEntityToMoveNode.addEdgeCreator(ct.actionNodes.chooseLocationToMoveNode,
                                CTDataGeneratorTreeFunctions::enableToMoveGenerator,
                                CTEdgeNameTreeFunctions::fullEntityName,
                                AccumulatorTreeFunctions::accumulateEntityToMove, CTEdgeTreeFunctions::none,
                                CTEdgeCreatorTreeChecker::canMoveAgain);
                ct.actionNodes.chooseLocationToMoveNode.addMover(ct.actionNodes.chooseEntityToMoveNode, "Back",
                                CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.chooseLocationToMoveNode.addEdgeCreator(ct.actionNodes.chooseEntityToMoveNode,
                                CTDataGeneratorTreeFunctions::adjLocations,
                                CTEdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateDestination,
                                CTEdgeTreeFunctions::performMovement, CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.chooseLocationToBuildGate.addMover(ct.gameFlowDecisionNodes.actionChooseNode, "Cancel",
                                CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.chooseLocationToBuildGate.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::gatePossibleLocations,
                                CTEdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateLocation,
                                CTEdgeTreeFunctions::buildGate, CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.chooseEntityToSpawnNode.addMover(ct.gameFlowDecisionNodes.actionChooseNode, "Cancel",
                                CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.chooseEntityToSpawnNode.addEdgeCreator(ct.actionNodes.chooseLocationToSpawnNode,
                                CTDataGeneratorTreeFunctions::spawnEntities,
                                CTEdgeNameTreeFunctions::spawnEntityName,
                                AccumulatorTreeFunctions::accumulateEntityToSpawn,
                                CTEdgeTreeFunctions::none, CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.chooseLocationToSpawnNode.addMover(ct.actionNodes.chooseEntityToSpawnNode, "Back",
                                CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.chooseLocationToSpawnNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::locationsToSpawn,
                                CTEdgeNameTreeFunctions::locationName, AccumulatorTreeFunctions::accumulateLocation,
                                CTEdgeTreeFunctions::spawnEntity, CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.captureNode.addMover(ct.gameFlowDecisionNodes.actionChooseNode, "Cancel",
                                CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.captureNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::entitiesToCapture,
                                CTEdgeNameTreeFunctions::mulitFactionalFullEntityName,
                                AccumulatorTreeFunctions::accumulateCapture,
                                CTEdgeTreeFunctions::captureEntity, CTEdgeCreatorTreeChecker::always);

                ct.actionNodes.attackLocationNode.addEdgeCreator(ct.actionNodes.attackFactionNode,
                                CTDataGeneratorTreeFunctions::generateLocationsToAttack,
                                CTEdgeNameTreeFunctions::possibleLocationName,
                                AccumulatorTreeFunctions::accumulateBattleLocation,
                                CTEdgeTreeFunctions::none, CTEdgeCreatorTreeChecker::always);

                ct.actionNodes.attackFactionNode.addEdgeCreator(ct.battleNodes.firstThrowsDiceNode,
                                CTDataGeneratorTreeFunctions::generateFactionsToAttack,
                                CTEdgeNameTreeFunctions::factionName,
                                AccumulatorTreeFunctions::accumulateFaction, CTEdgeTreeFunctions::none,
                                CTEdgeCreatorTreeChecker::always);

                ct.battleNodes.firstThrowsDiceNode.addMover(ct.battleNodes.secondThrowsDiceNode, "Throw",
                                CTEdgeCreatorTreeChecker::always);
                ct.battleNodes.secondThrowsDiceNode.addMover(ct.battleNodes.resultThrowingDiceNode, "Throw",
                                CTEdgeCreatorTreeChecker::always);
                ct.battleNodes.resultThrowingDiceNode.addMover(ct.battleNodes.firstPlayerKillEntityNode, "Next",
                                CTEdgeCreatorTreeChecker::always);

                ct.battleNodes.firstPlayerKillEntityNode.addEdgeCreator(ct.battleNodes.firstPlayerKillEntityNode,
                                CTDataGeneratorTreeFunctions::generateEntitiesFirstPlayer,
                                CTEdgeNameTreeFunctions::entityToKillFirstPlayerName,
                                AccumulatorTreeFunctions::accumulateEntityToKill,
                                CTEdgeTreeFunctions::entityToKillFirstPlayer,
                                CTEdgeCreatorTreeChecker::canKillEntityFirstPlayer);
                ct.battleNodes.firstPlayerKillEntityNode.addMover(ct.battleNodes.secondPlayerKillEntityNode, "Done",
                                CTEdgeCreatorTreeChecker.opposite(CTEdgeCreatorTreeChecker::canKillEntityFirstPlayer));

                ct.battleNodes.secondPlayerKillEntityNode.addEdgeCreator(ct.battleNodes.secondPlayerKillEntityNode,
                                CTDataGeneratorTreeFunctions::generateEntitiesSecondPlayer,
                                CTEdgeNameTreeFunctions::entityToKillSecondPlayerName,
                                AccumulatorTreeFunctions::accumulateEntityToKill,
                                CTEdgeTreeFunctions::entityToKillSecondPlayer,
                                CTEdgeCreatorTreeChecker::canKillEntitySecondPlayer);
                ct.battleNodes.secondPlayerKillEntityNode.addMover(ct.battleNodes.firstPlayerInjureEntityNode, "Done",
                                CTEdgeCreatorTreeChecker.opposite(CTEdgeCreatorTreeChecker::canKillEntitySecondPlayer));

                ct.battleNodes.firstPlayerInjureEntityNode.addEdgeCreator(ct.battleNodes.firstPlayerInjurePositionNode,
                                CTDataGeneratorTreeFunctions::generateEntitiesFirstPlayer,
                                CTEdgeNameTreeFunctions::entityToKillFirstPlayerName,
                                AccumulatorTreeFunctions::accumulateEntityToKill,
                                CTEdgeTreeFunctions::none, CTEdgeCreatorTreeChecker::canInjureEntityFirstPlayer);
                ct.battleNodes.firstPlayerInjureEntityNode.addMover(ct.battleNodes.secondPlayerInjureEntityNode, "Done",
                                CTEdgeCreatorTreeChecker
                                                .opposite(CTEdgeCreatorTreeChecker::canInjureEntityFirstPlayer));

                ct.battleNodes.firstPlayerInjurePositionNode.addEdgeCreator(ct.battleNodes.firstPlayerInjureEntityNode,
                                CTDataGeneratorTreeFunctions::generatePositionsFirstPlayer,
                                CTEdgeNameTreeFunctions::PositionToInjureFirstPlayerName,
                                AccumulatorTreeFunctions::accumulateDestinationPlayer,
                                CTEdgeTreeFunctions::performMoveInjureFirstPlayer,
                                CTEdgeCreatorTreeChecker::canInjurePositionFirstPlayer);

                ct.battleNodes.firstPlayerInjurePositionNode.addEdgeCreator(ct.battleNodes.firstPlayerInjureEntityNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName("Kill"),
                                AccumulatorTreeFunctions::none,
                                CTEdgeTreeFunctions::KillInjureFirstPlayer, CTEdgeCreatorTreeChecker
                                                .opposite(CTEdgeCreatorTreeChecker::canInjurePositionFirstPlayer));

                ct.battleNodes.firstPlayerInjurePositionNode.addMover(ct.battleNodes.firstPlayerInjureEntityNode,
                                "Back",
                                CTEdgeCreatorTreeChecker::always);

                ct.battleNodes.secondPlayerInjureEntityNode.addEdgeCreator(
                                ct.battleNodes.secondPlayerInjurePositionNode,
                                CTDataGeneratorTreeFunctions::generateEntitiesSecondPlayer,
                                CTEdgeNameTreeFunctions::entityToKillSecondPlayerName,
                                AccumulatorTreeFunctions::accumulateEntityToKill,
                                CTEdgeTreeFunctions::none, CTEdgeCreatorTreeChecker::canInjureEntitySecondPlayer);
                ct.battleNodes.secondPlayerInjureEntityNode.addMover(ct.gameFlowDecisionNodes.actionChooseNode, "Done",
                                CTEdgeCreatorTreeChecker
                                                .opposite(CTEdgeCreatorTreeChecker::canInjureEntitySecondPlayer));

                ct.battleNodes.secondPlayerInjurePositionNode.addEdgeCreator(
                                ct.battleNodes.secondPlayerInjureEntityNode,
                                CTDataGeneratorTreeFunctions::generatePositionsSecondPlayer,
                                CTEdgeNameTreeFunctions::PositionToInjureSecondPlayerName,
                                AccumulatorTreeFunctions::accumulateDestinationPlayer,
                                CTEdgeTreeFunctions::performMoveInjureSecondPlayer,
                                CTEdgeCreatorTreeChecker::canInjurePositionSecondPlayer);

                ct.battleNodes.secondPlayerInjurePositionNode.addEdgeCreator(
                                ct.battleNodes.secondPlayerInjureEntityNode,
                                CTDataGeneratorTreeFunctions::justOne,
                                CTEdgeNameTreeFunctions.constName("Kill"),
                                AccumulatorTreeFunctions::none,
                                CTEdgeTreeFunctions::KillInjureSecondPlayer, CTEdgeCreatorTreeChecker
                                                .opposite(CTEdgeCreatorTreeChecker::canInjurePositionSecondPlayer));

                ct.battleNodes.secondPlayerInjurePositionNode.addMover(ct.battleNodes.secondPlayerInjureEntityNode,
                                "Back",
                                CTEdgeCreatorTreeChecker::always);

                ct.actionNodes.attackLocationNode.addMover(ct.gameFlowDecisionNodes.actionChooseNode, "Cancel",
                                CTEdgeCreatorTreeChecker::always);

                ct.actionNodes.attackFactionNode.addMover(ct.actionNodes.attackLocationNode, "Cancel",
                                CTEdgeCreatorTreeChecker::always);

                ct.actionNodes.blackGoatKillTwoCultistsNode.addMover(ct.gameFlowDecisionNodes.actionChooseNode,
                                "Cancel", CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.blackGoatKillTwoCultistsNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::generatePairOfCultistToKill,
                                CTEdgeNameTreeFunctions::killCultistsName,
                                AccumulatorTreeFunctions::accumulateKillingCultistsBlackGoat,
                                CTEdgeTreeFunctions::killTwoCultistsBlackGoat, CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.sleeperPresentEnergyNode.addMover(ct.gameFlowDecisionNodes.actionChooseNode, "Cancel",
                                CTEdgeCreatorTreeChecker::always);
                ct.actionNodes.sleeperPresentEnergyNode.addEdgeCreator(ct.gameFlowDecisionNodes.actionChooseNode,
                                CTDataGeneratorTreeFunctions::generateFaction,
                                CTEdgeNameTreeFunctions::factionName,
                                AccumulatorTreeFunctions::accumulateFaction,
                                CTEdgeTreeFunctions::sleeperSpendAndGetEnergy, CTEdgeCreatorTreeChecker::always);
        }
}
