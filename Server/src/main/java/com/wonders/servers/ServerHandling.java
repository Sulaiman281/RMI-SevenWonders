package com.wonders.servers;

import com.wonders.model.Player;
import com.wonders.servers.database.MongoConnection;
import com.wonders.servers.game.Match;

import java.util.ArrayList;

public class ServerHandling {

    public MongoConnection db_connection;


    public ArrayList<Player> connected_players;

    public ArrayList<Match> active_matches;

    public ArrayList<Room> lobbies;

    private static ServerHandling _instance;

    private ServerHandling(){
        connected_players = new ArrayList<>();
        active_matches = new ArrayList<>();
        lobbies = new ArrayList<>();
    }

    public Player getPlayer(String name){
        for(Player player : connected_players){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
    }

    public static ServerHandling getInstance() {
        if (_instance == null) _instance = new ServerHandling();
        return _instance;
    }
}
