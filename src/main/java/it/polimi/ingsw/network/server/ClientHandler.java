package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MessageSubType;
import it.polimi.ingsw.network.message.MessageType;
import it.polimi.ingsw.utils.Logger;
import it.polimi.ingsw.view.Server.VirtualView;

import java.io.*;
import java.net.Socket;
import java.util.Timer;

public class ClientHandler implements Runnable{

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;
    private Socket socket;
    private Server server;
    private boolean isViewActive = false;
    private boolean isConnectionActive;
    private VirtualView view;
    private Timer lobbyTimer;

    public ClientHandler(Server server, Socket socket){
        this.socket = socket;
        this.server = server;
        this.isConnectionActive = true;

    }

    public boolean isConnectionActive() {
        return isConnectionActive;
    }

    public void setConnectionActive(boolean connectionActive) {
        isConnectionActive = connectionActive;
    }

    public boolean isViewActive() {
        return isViewActive;
    }

    public void setViewActive(boolean active) {
        isViewActive = active;
    }

    public VirtualView getView() {
        return view;
    }

    public void setView(VirtualView view) {
        this.view = view;
    }

    public void sendMessage(Message msg){//fare un'altra funzione per mandare in asincrono i messaggi

        try {
            objectOut.writeObject(msg);
            objectOut.flush();
            objectOut.reset();

        }catch (IOException e){
            Logger.info("error in connection");
        }

    }

    public void closeConnection(){
        //chiusura connesione
        try{
        objectIn.close();
        objectOut.close();
        socket.close();
        }catch (IOException e){
            Logger.info("problem in closing connection");
        }
    }

    @Override
    public void run() {

            try {
                this.objectOut = new ObjectOutputStream(socket.getOutputStream());
                this.objectIn = new ObjectInputStream(socket.getInputStream());
                server.firsLogin(this);
                while(isConnectionActive()) {
                    Message input = (Message) objectIn.readObject();

                    if (input.getType() == MessageType.CONFIG && input.getSubType() == MessageSubType.ANSWER) {
                        server.InsertPlayerInGame(input,this);
                    }
                    else if(input.getType() == MessageType.DISCONNECTION){
                        server.handleClientDisconnectionBeforeStarting(input);
                    }
                    else {
                        view.dispatchMessageToVirtualView(input); //runnarlo in un altro thread?
                    }

                }
                closeConnection();

            }catch (IOException e){
                Logger.info("player disconnected");//gestire la disconessione del player
            }
            catch(ClassNotFoundException c){
                Logger.info("problem with class");
            }
        }

}
