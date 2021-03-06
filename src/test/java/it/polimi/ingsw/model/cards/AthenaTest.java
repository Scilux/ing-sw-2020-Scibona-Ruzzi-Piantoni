package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AthenaTest {

    Player player1, player2;
    Card cardAthe;
    GameMap gameMap;
    List<Directions> directions;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardAthe = CardLoader.loadCards().get("athena");
        player1.setPower(cardAthe);
        gameMap = new GameMap();
        gameMap.getMap().get(22).setMovement(player1,player1.getWorkers().get(0));
        player1.getWorkers().get(0).setBoardPosition(gameMap.getMap().get(22));
        gameMap.getMap().get(4).setMovement(player1,player1.getWorkers().get(1));
        player1.getWorkers().get(1).setBoardPosition(gameMap.getMap().get(4));
        gameMap.getMap().get(21).setMovement(player2,player2.getWorkers().get(0));
        player2.getWorkers().get(0).setBoardPosition(gameMap.getMap().get(21));
        gameMap.getMap().get(18).setMovement(player2,player2.getWorkers().get(1));
        player2.getWorkers().get(1).setBoardPosition(gameMap.getMap().get(18));
        player1.selectCurrentWorker(gameMap, "worker1");
        gameMap.getMap().get(23).addBuildingLevel();
        directions = player1.findWorkerMove(gameMap, player1.getWorkers().get(0));
    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardAthe.executeWorkerMove(null, Directions.NORD, player1));
        assertThrows(NullPointerException.class , () -> cardAthe.executeWorkerMove(gameMap, null, player1));
        assertThrows(NullPointerException.class , () -> cardAthe.executeWorkerMove(gameMap, Directions.NORD, null));

        assertEquals(1,gameMap.getMap().get(23).getBuildingLevel());
        assertEquals(Response.MOVED,cardAthe.executeWorkerMove(gameMap, Directions.OVEST, player1));
        assertEquals(Response.ASSIGNCONSTRAINT,cardAthe.executeWorkerMove(gameMap, Directions.NORD_EST, player1));

    }

    @Test
    void eliminateInvalidMove() {
        assertEquals(7,directions.size());
        assertEquals(6,cardAthe.eliminateInvalidMove(gameMap, player1.getCurrentWorker(), directions).size());
    }
}