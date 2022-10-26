package com.wonders.client;

import com.wonders.Settings;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRemote {

    public String server_address = "";


    public boolean connect_to_server(String address){
        try {
            Registry registry = LocateRegistry.getRegistry(address, Settings.PORT);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Remote rmiRequest(String data) throws MalformedURLException, NotBoundException, RemoteException {
        return Naming.lookup("rmi://"+server_address.concat(":"+ Settings.PORT).concat("/").concat(data));
    }
}
