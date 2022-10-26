package com.wonders;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameRemote extends Remote {

    public String game_status(String match_id) throws RemoteException;

    public int select_city(String match_id, String playerName, String cityName) throws RemoteException;

    public String selectedCities(String match_id) throws RemoteException;

    public boolean allSelectedCity(String match_id) throws RemoteException;

    public String playerSetup(String match_id, String playerName) throws RemoteException;

    public String player_CardRequest(String match_id, String playerName) throws RemoteException;

    public String player_Opponents(String match_id, String playerName) throws RemoteException;

    public String playerUpdateState(String match_id, String playerName) throws RemoteException;

    public String opponentsUpdateState(String match_id, String p_name) throws RemoteException;

    public String card_action_place(String match_id, String player_name, String action) throws RemoteException;

    public String passingRequest(String match_id, String playerName) throws RemoteException;

    public String nextAge(String match_id, String player_name) throws RemoteException;

    public String gameResult(String match_id) throws RemoteException;
}
