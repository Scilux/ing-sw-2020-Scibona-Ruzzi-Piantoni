package it.polimi.ingsw.model.cards;

import it.polimi.ingsw.model.map.Directions;
import it.polimi.ingsw.model.map.GameMap;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApolloTest {

    Player player1, player2;
    Card cardApo, cardAthe;
    GameMap gameMap;

    @BeforeEach
    void setup(){
        player1 = new Player("GoodPlayer");
        player2 = new Player("BadPlayer");
        cardApo = CardLoader.loadCards().get("apollo");
        cardAthe = CardLoader.loadCards().get("athena");
        player1.setPower(cardApo);
        player2.setPower(cardAthe);
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
        player2.selectCurrentWorker(gameMap, "worker1");
    }

    @Test
    void findWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardApo.findWorkerMove(null, player1.getCurrentWorker()));
        assertThrows(NullPointerException.class , () -> cardApo.findWorkerMove(gameMap, null));

        assertEquals(8,cardApo.findWorkerMove(gameMap, player1.getCurrentWorker()).size());
    }

    @Test
    void executeWorkerMove() {
        assertThrows(NullPointerException.class , () -> cardApo.executeWorkerMove(null, Directions.NORD, player1));
        assertThrows(NullPointerException.class , () -> cardApo.executeWorkerMove(gameMap, null, player1));
        assertThrows(NullPointerException.class , () -> cardApo.executeWorkerMove(gameMap, Directions.NORD, null));

        assertEquals(gameMap.getMap().get(22),player1.getCurrentWorker().getBoardPosition());
        assertEquals(gameMap.getMap().get(21),player2.getCurrentWorker().getBoardPosition());
        player1.executeWorkerMove(gameMap, Directions.EST);
        assertEquals(gameMap.getMap().get(21),player1.getCurrentWorker().getBoardPosition());
        assertEquals(gameMap.getMap().get(22),player2.getCurrentWorker().getBoardPosition());
        assertEquals(gameMap.getMap().get(22),gameMap.getModifiedSquare().get(0));
        assertEquals(gameMap.getMap().get(21),gameMap.getModifiedSquare().get(1));
    }
}