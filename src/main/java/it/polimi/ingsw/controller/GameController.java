package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player.Player;
import it.polimi.ingsw.model.Response;
import it.polimi.ingsw.network.message.GameConfigMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.utils.Observer;
import it.polimi.ingsw.view.Server.VirtualView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class GameController implements Observer<Message> {

    private Game game;
    private int numberOfPlayers;
    private HashMap<String, VirtualView> clients;
    private Timer turnTimer ;
    private Timer reconnectionTimer;
    private RoundController roundController;

    public GameController(int numberOfPlayer,String gameID) {
        this.numberOfPlayers = numberOfPlayer;
        this.roundController = new RoundController();
        this.game = initializeGame(gameID);
        this.clients = new HashMap<>();
    }

    //
    //methods for new player
    //

    public synchronized void  handleNewPlayer(Message message) {
        VirtualView view = ((GameConfigMessage) message).getView();
        view.setYourTurn(true);
        String nick = message.getNickName();
        if(!game.addPlayer(new Player(nick),view)){
              game.setGameStatus(Response.NICKUSED);
              view.setYourTurn(false);
              return;
        }
        clients.put(nick,view);

        game.setGameStatus(Response.PLAYERADDED);
        view.setYourTurn(false);
        checkIfGameCanStart();

    }

    public synchronized void handleNewNickname(Message message){
        String nick = message.getNickName();
        VirtualView view = getViewFromUserID(message.getSender());
        view.setYourTurn(true);

        if(!game.newNickName(new Player(nick))){
            game.setGameStatus(Response.NICKUSED);
            view.setYourTurn(false);
            return;
        }

        clients.put(nick,view);
        game.setGameStatus(Response.PLAYERADDED);
        view.setYourTurn(false);
        checkIfGameCanStart();
    }

    public boolean checkIfGameCanStart(){
        if(game.getSettedPlayers().size() == game.getNumberOfPlayers() && game.getConfigPlayer() == 0) {
            game.setGameStarted(true);

            for(VirtualView values :clients.values()){
             values.setYourTurn(true);
            }

            game.setGameStatus(Response.GAMESTARTED);

            for(VirtualView values :clients.values()){
                values.setYourTurn(false);
            }
            return true;
        }
        return false;
    }

    public synchronized boolean isGameStarted(){
        return game.isGameStarted();
    }


    public synchronized Game initializeGame(String gameID){

        return new Game(numberOfPlayers,gameID);
    }

    public synchronized void addUserID(VirtualView view,String userID){
        clients.put(userID,view);
    }

    public VirtualView getViewFromUserID(String userID){
        return clients.get(userID);
    }

    public VirtualView getViewFromNickName(String nick){
        return clients.get(nick);
    }

    public synchronized boolean isFull(){
        return (game.getSettedPlayers().size()+game.getConfigPlayer()) == game.getNumberOfPlayers();
    }

    public String getGameID(){
        return game.getGameID();
    }
    public int getNumberOfPlayers(){
        return game.getNumberOfPlayers();
    }

    //
    //methods for disconnection from the Game
    //

    public synchronized void handleDisconnectionBeforeStart(Message message){
        VirtualView view = clients.get(message.getSender());
        if(message.getSubType().equals(MessageSubType.TIMEENDED)){
            handleLobbyTimerEnded(message);
            return;
        }
        disconnectPlayer(message);

        if(message.getSubType().equals(MessageSubType.BACK))
            view.getConnection().startLobbyTimer();

        if(message.getSubType().equals(MessageSubType.REQUEST)) {

            view.getConnection().closeConnection();
        }
    }

    public synchronized void handleLobbyTimerEnded(Message message){
        VirtualView view = clients.get(message.getSender());
        view.getConnection().setViewActive(false);
        game.removeObserver(view);
        game.removeConfigPlayer();
        clients.remove(message.getSender());
        view.getConnection().closeConnection();
    }


    public synchronized void disconnectPlayer(Message message){
        VirtualView view = clients.get(message.getSender());
        view.getConnection().setViewActive(false);
        game.removeObserver(view);
        if(message.getSubType().equals(MessageSubType.NICKMAXTRY))
            game.removeConfigPlayer();
        else
            game.removeSettedPlayer(message.getMessage());
        clients.remove(message.getMessage());
        clients.remove(message.getSender());
    }

    public synchronized boolean isFreeNick(String nick){
        ArrayList<Player> players = game.getPlayers();

        for(Player player : players){
            if(player.getNickname().equals(nick))
                return false;
        }

        return true;
    }

    //
    //methods for reconnection
    //

    //inserire metodi

    //
    //methods for Game handling
    //

    public void handleMatchBeginning(){

    }

    public void handleTurnBeginning(){

    }

    public void handleWorkerChoice(){

    }

    public void removeNonPermanentConstraint(){


    }

    public void handleEndTun(){


    }


    public void sendToRoundController(Message message){


    }




    public void processMessage(Message message){

        switch (message.getType()){
            case CONFIG:
                if(message.getSubType().equals(MessageSubType.ANSWER))
                    handleNewPlayer(message);
                else if(message.getSubType().equals(MessageSubType.UPDATE))
                    handleNewNickname(message);
                break;
            case DISCONNECTION:
                handleDisconnectionBeforeStart(message);
                break;
            default:
        }


    }



    @Override
    public void update(Message message) {

        processMessage(message);
    }


}
