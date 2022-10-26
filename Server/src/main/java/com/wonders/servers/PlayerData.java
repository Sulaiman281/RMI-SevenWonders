package com.wonders.servers;

import com.mongodb.client.MongoDatabase;
import com.wonders.PlayerRemote;
import com.wonders.model.Player;
import org.bson.Document;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class PlayerData extends UnicastRemoteObject implements PlayerRemote {

    protected PlayerData() throws RemoteException {
        super();
    }

    protected PlayerData(int port) throws RemoteException {
        super(port);
    }

    protected PlayerData(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public int connect_player(String name) throws RemoteException {
        for (Player connected_player : ServerHandling.getInstance().connected_players) {
            if(connected_player.getName().equals(name)){
                return 1;
            }
        }
        Player player = new Player();
        player.setName(name);
        ServerHandling.getInstance().connected_players.add(player);
        return 0;
    }

    @Override
    public int register_player(String name, String email, String pass) throws RemoteException {
        Player player = new Player(name, email, pass);
        try {
            MongoDatabase db = ServerHandling.getInstance().db_connection.dBase();
            Document doc = db.getCollection("Player").find(player.nameQuery()).first();
            if(doc != null){ // name exists
                return 1;
            }else{
                db.getCollection("Player").insertOne(player.getData());
                ServerHandling.getInstance().connected_players.add(player);
                return 0; // added perfectly.
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return -1; // run into exception.
        }
    }

    @Override
    public int login_player(String name, String pass) {
        Player player = new Player(name, pass);
        try {
            MongoDatabase db = ServerHandling.getInstance().db_connection.dBase();
            Document doc = db.getCollection("Player").find(player.authQuery()).first();
            if(doc != null){ // name exists
                ServerHandling.getInstance().connected_players.removeIf(e-> e.getName().equals(name));
                ServerHandling.getInstance().connected_players.add(player);
                return 0; // player exist
            }else{
                return -2; // player does not exist
            }
        } catch (Exception e) {
            return -1; // run into exception.
        }
    }
}
