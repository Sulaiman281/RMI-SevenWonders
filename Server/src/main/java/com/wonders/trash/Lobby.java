package com.wonders.trash;

import java.util.ArrayList;

public class Lobby {

    private final String lobbyName;

    private final ArrayList<LobbyMate> players;

    private boolean gameStarted = false;

    class LobbyMate {
        String name;
        boolean isHost;
        boolean ready;

        public LobbyMate(String name, boolean isHost, boolean ready) {
            this.name = name;
            this.isHost = isHost;
            this.ready = ready;
        }

        @Override
        public String toString() {
            return String.format("%s:%b", name, isHost || ready);
        }
    }

    public Lobby(String name) {
        players = new ArrayList<>();
        LobbyMate host = new LobbyMate(name, true, false);
        players.add(host);
        lobbyName = name;
    }

    public void join_lobby(String name) {
        LobbyMate mate = new LobbyMate(name, false, false);
        players.add(mate);
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public ArrayList<LobbyMate> getPlayers() {
        return players;
    }

    public String getLobbyPlayers() {
        StringBuilder str = new StringBuilder();
        for (LobbyMate player : players) {
            str.append(player.toString()).append(",");
        }
        return String.valueOf(str);
    }

    public String[] getPlayersName() {
        String[] p = new String[players.size()];
        for (int i = 0; i < players.size(); i++) {
            p[i] = players.get(i).name;
        }
        return p;
    }

    public boolean allReady() {
        for (LobbyMate player : players) {
            if (player.isHost) continue;
            if (!player.ready) return false;
        }
        return true;
    }
}
