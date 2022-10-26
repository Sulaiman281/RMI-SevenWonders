package com.wonders.servers;

import com.wonders.MenuRemote;
import com.wonders.model.Player;
import com.wonders.servers.game.Match;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class MenuUpdates extends UnicastRemoteObject implements MenuRemote {
    protected MenuUpdates() throws RemoteException {
        super();
    }

    protected MenuUpdates(int port) throws RemoteException {
        super(port);
    }

    protected MenuUpdates(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public int totalPlayers() throws RemoteException {
        return ServerHandling.getInstance().connected_players.size();
    }

//    @Override
//    public String getRooms() throws RemoteException {
//        StringBuilder rooms = new StringBuilder();
//        for (Lobby room : ServerHandling.getInstance().rooms) {
//            rooms.append(room.getLobbyName()).append(":").append(String.format("%02d", room.getPlayers().size())).append(",");
//        }
//        return rooms.toString();
//    }
//
//    @Override
//    public int create_lobby(String name) throws RemoteException {
//        remove_player_from_lobby(name);
//        Lobby lobby = new Lobby(name);
//        ServerHandling.getInstance().rooms.add(lobby);
//        return 1;
//    }
//
//    @Override
//    public int join_lobby(String playerName, String lobbyName) throws RemoteException {
//        remove_player_from_lobby(playerName);
//        for (Lobby room : ServerHandling.getInstance().rooms) {
//            if (room.getLobbyName().equals(lobbyName)) {
//                if (room.getPlayers().size() == 4) return 1; // lobby is full
//                room.join_lobby(playerName);
//                return 0; // success
//            }
//        }
//        return -1; // lobby not found.
//    }
//
//    @Override
//    public void leave_lobby(String name) throws RemoteException {
//        remove_player_from_lobby(name);
//    }
//
//    void remove_player_from_lobby(String name) {
//        ServerHandling.getInstance().rooms.removeIf(room -> {
//            AtomicBoolean newHost = new AtomicBoolean(false);
//            room.getPlayers().removeIf(player -> {
//                if (player.name.equals(name)) {
//                    newHost.set(player.isHost);
//                    return true;
//                }
//                return false;
//            });
//            if (room.getPlayers().size() > 0) {
//                if (newHost.get())
//                    room.getPlayers().get(new Random().nextInt(room.getPlayers().size())).isHost = true;
//                return false;
//            }
//            return true;
//        });
//    }
//
//    @Override
//    public int totalRooms() throws RemoteException {
//        return ServerHandling.getInstance().rooms.size();
//    }
//
//    @Override
//    public String lobby_status(String lobbyName) throws RemoteException {
//        for (Lobby room : ServerHandling.getInstance().rooms) {
//            if (room.getLobbyName().equals(lobbyName)) {
//                if (room.isGameStarted()) {
//                    String id;
////                    for (Match active_match : ServerHandling.getInstance().active_matches) {
////                        if(active_match.getLobbyName().equals(lobbyName)){
////                            id = active_match.getId();
////                            return id + "&" + room.getLobbyPlayers();
////                        }
////                    }
////                    id = String.format("%04d", ServerHandling.getInstance().active_matches.size());
////                    Match match = new Match(lobbyName, id, room.getPlayersName());
////                    ServerHandling.getInstance().active_matches.add(match);
//                    return "&" + room.getLobbyPlayers();
//                } else {
//                    return "" + "&" + room.getLobbyPlayers();
//                }
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public boolean me_ready(String lobbyName, String player_name) throws RemoteException {
//        for (Lobby room : ServerHandling.getInstance().rooms) {
//            if (room.getLobbyName().equals(lobbyName)) {
//                for (Lobby.LobbyMate player : room.getPlayers()) {
//                    if (player.name.equals(player_name)) {
//                        player.ready = !player.ready;
//                        return player.ready;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean start_game(String lobbyName) throws RemoteException {
//        for (Lobby room : ServerHandling.getInstance().rooms) {
//            if (room.getLobbyName().equals(lobbyName)) {
//                if (room.getPlayers().size() == 4) {
//                    if (room.allReady()) {
//                        room.setGameStarted(true);
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    @Override
    public int play_request(String name) throws RemoteException {
        if(ServerHandling.getInstance().lobbies.isEmpty()){
            Room room = new Room();
            room.add_player(new Player(name));
            ServerHandling.getInstance().lobbies.add(room);
            return 3; // created lobby waiting for players.
        }
        for (Room lobby : ServerHandling.getInstance().lobbies) {
            if(lobby.containPlayer(name)){
                if(lobby.isFull()){
                    return 0; // start the game.
                }
                return 1; // waiting for the players.
            }else{
                lobby.add_player(ServerHandling.getInstance().getPlayer(name));
                return 2; // found the lobby.
            }
        }
        Room room = new Room();
        room.add_player(new Player(name));
        ServerHandling.getInstance().lobbies.add(room);
        return 3; // created lobby waiting for players.
    }

    @Override
    public String createMatch(String name) throws RemoteException {
        for (Room lobby : ServerHandling.getInstance().lobbies) {
            if(lobby.containPlayer(name)){
                String id;
                for (Match active_match : ServerHandling.getInstance().active_matches) {
                    if(active_match.getLobby() == lobby){
                        id = active_match.getId();
                        return id;
                    }
                }
                id = String.format("%04d", ServerHandling.getInstance().active_matches.size());
                Match match = new Match(lobby, id);
                ServerHandling.getInstance().active_matches.add(match);
                return id;
            }
        }
        return null;
    }
}
