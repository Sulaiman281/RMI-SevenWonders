package com.wonders;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerRemote extends Remote {

    public int connect_player(String name) throws RemoteException;
    public int register_player(String name, String email, String pass) throws RemoteException;
    public int login_player(String name, String pass) throws RemoteException;
}
