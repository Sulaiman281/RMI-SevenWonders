package com.wonders.servers;

import com.wonders.model.Player;

import java.util.ArrayList;

public class Room {

    private ArrayList<Player> players;

    final int max_players = 4;

    public Room(){
        players = new ArrayList<>();
    }

    public void add_player(Player player){
        players.add(player);
    }

    public boolean containPlayer(String name){
        for (Player player : players) {
            if(player.getName().equals(name))
                return true;
        }
        return false;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public boolean isFull(){
        return players.size() == max_players;
    }

}
