package it.polimi.ingsw.model.Cards;

import com.sun.javafx.scene.traversal.Direction;
import it.polimi.ingsw.model.Map.Building;
import it.polimi.ingsw.model.Map.Directions;
import it.polimi.ingsw.model.Map.GameMap;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Player.Worker;

import java.util.ArrayList;

public class Card {

    private String name;
    private String description;
    private boolean isPlayableIn3;
    private CardType type;
    private CardSubType subType;

    public Card (String name, String description, boolean isPlayableIn3, CardType type, CardSubType subType)
    {
        this.name = name;
        this.description = description;
        this.isPlayableIn3 = isPlayableIn3;
        this.type = type;
        this.subType = subType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPlayableIn3() {
        return isPlayableIn3;
    }

    public void setPlayableIn3(boolean playableIn3) {
        isPlayableIn3 = playableIn3;
    }

    public CardType getType() { return type;}

    public void setType(CardType type) {
        this.type = type;
    }

    public CardSubType getSubType() {
        return subType;
    }

    public void setSubType(CardSubType subType) {
        this.subType = subType;
    }

    public ArrayList<Directions> findWorkerMove(GameMap gameMap, Worker worker) {
        return gameMap.reachableSquares(worker);
    }

    public void executeWorkerMove(GameMap gameMap, Directions directions, Player player) {
        gameMap.moveWorkerTo(player, directions);
    }

    public ArrayList<Directions> findPossibleBuild(GameMap gameMap, Worker worker) {
        return gameMap.reachableSquares(worker);
    }

    public void executeBuild(GameMap gameMap, Building building, Directions directions, Worker worker) {
        gameMap.buildInSquare(worker, directions, building);
    }

    public boolean checkVictory(GameMap gameMap, Worker worker) {
        return worker.getBoardPosition().getBuildingLevel() == 3 && worker.getPreviousBoardPosition().getBuildingLevel() == 2;
    }

}
