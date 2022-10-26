package com.wonders.servers;

import com.wonders.Settings;
import com.wonders.model.Player;
import com.wonders.servers.database.MongoConnection;
import com.wonders.servers.game.Match;
import com.wonders.servers.game.cards.Card;
import org.apache.log4j.BasicConfigurator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRemote {

    public static void main(String[] args) {

        ServerHandling.getInstance().db_connection = new MongoConnection();
        BasicConfigurator.configure();

        try {
            // connecting mongodb host.
            ServerHandling.getInstance().db_connection.authorize();

            // setting RMI to run on LocalNetwork
            String address = InetAddress.getLocalHost().getHostAddress();
            System.out.println(address);
            System.setProperty("java.rmi.server.hostname", address);

            Registry registry = LocateRegistry.createRegistry(Settings.PORT);

            registry.rebind(Settings.PLAYER_DATA, new PlayerData());
            registry.rebind(Settings.MENU, new MenuUpdates());
            registry.rebind(Settings.GAME_HANDLING, new GameHandling());

        } catch (RemoteException | UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Failed to Connect with Database.\n" + e.getMessage());
        }
    }
}
