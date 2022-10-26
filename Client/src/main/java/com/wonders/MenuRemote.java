package com.wonders;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MenuRemote extends Remote {

//    public int totalPlayers() throws RemoteException;
//
//    public String getRooms() throws RemoteException;
//
//    public int create_lobby(String name) throws RemoteException;
//
//    public int join_lobby(String playerName, String lobbyName) throws RemoteException;
//
//    public void leave_lobby(String name) throws RemoteException;
//
//    public int totalRooms() throws RemoteException;
//
//    public String lobby_status(String lobbyName) throws RemoteException;
//
//    public boolean me_ready(String lobbyName, String player_name) throws RemoteException;
//
//    public boolean start_game(String lobbyName) throws RemoteException;

    public int play_request(String name) throws RemoteException;

    public String createMatch(String name) throws RemoteException;
}
