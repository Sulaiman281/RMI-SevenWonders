package com.wonders.servers;

import com.wonders.GameRemote;
import com.wonders.servers.game.Match;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class GameHandling extends UnicastRemoteObject implements GameRemote {


    protected GameHandling() throws RemoteException {
        super();
    }

    protected GameHandling(int port) throws RemoteException {
        super(port);
    }

    protected GameHandling(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public String game_status(String match_id) throws RemoteException {
        //AGE#DISCARD#PASSINGSIDE
        for (Match active_match : ServerHandling.getInstance().active_matches) {
            return active_match.game_status();
        }
        return null;
    }

    @Override
    public int select_city(String match_id, String playerName, String cityName) throws RemoteException {
        for (Match active_match : ServerHandling.getInstance().active_matches) {
            if (active_match.getId().equals(match_id)) {
                return active_match.city_selected(playerName, cityName);
            }
        }
        return -1;
    }

    @Override
    public String selectedCities(String match_id) throws RemoteException {
        try {
            for (Match active_match : ServerHandling.getInstance().active_matches) {
                if (active_match.getId().equals(match_id)) {
                    return active_match.playersCitySelectionState();
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    @Override
    public boolean allSelectedCity(String match_id) throws RemoteException {
        for (Match active_match : ServerHandling.getInstance().active_matches) {
            if (active_match.getId().equals(match_id)) {
                return active_match.all_players_selected_city();
            }
        }
        return false;
    }

    @Override
    public String playerSetup(String match_id, String playerName) throws RemoteException {
        try {
            for (Match active_match : ServerHandling.getInstance().active_matches) {
                if (active_match.getId().equals(match_id)) {
                    return active_match.playerSetup(playerName);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String player_CardRequest(String match_id, String playerName) throws RemoteException {
        try {
            for (Match active_match : ServerHandling.getInstance().active_matches) {
                if (active_match.getId().equals(match_id)) {
                    return active_match.card_request(playerName);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String player_Opponents(String match_id, String playerName) throws RemoteException {
        try {
            for (Match active_match : ServerHandling.getInstance().active_matches) {
                if (active_match.getId().equals(match_id)) {
                    return active_match.getOpponents(playerName);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String playerUpdateState(String match_id, String playerName) throws RemoteException {
        //COINS#SHIELDS#STAGES
        try {
            for (Match active_match : ServerHandling.getInstance().active_matches) {
                if (active_match.getId().equals(match_id)) {
                    return active_match.playerUpdate(playerName);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String opponentsUpdateState(String match_id, String p_name) throws RemoteException {
        try {
            for (Match active_match : ServerHandling.getInstance().active_matches) {
                if (active_match.getId().equals(match_id)) {
                    return active_match.playerStatusAs_Opponent(p_name);
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String card_action_place(String match_id, String player_name, String action) throws RemoteException {
        try {
            for (Match match : ServerHandling.getInstance().active_matches) {
                if (match.getId().equals(match_id)) {
                    return match.card_action(player_name, action)+"";
                }
            }
        } catch (Exception e) {
            return -1+"";
        }
        return -1+"";
    }

    @Override
    public String passingRequest(String match_id, String playerName) throws RemoteException {
        try {
            for (Match active_match : ServerHandling.getInstance().active_matches) {
                if (active_match.getId().equals(match_id)) {
                    return active_match.card_passRequest(playerName);
                }
            }
        } catch (Exception e) {
            return "WAIT"+e.getMessage();
        }
        return null;
    }

    @Override
    public String nextAge(String match_id, String player_name) throws RemoteException {
        try {
            for (Match active_match : ServerHandling.getInstance().active_matches) {
                if (active_match.getId().equals(match_id)) {
                    return active_match.nextAge(player_name);
                }
            }
        } catch (Exception e) {
            return "WAIT"+e.getMessage();
        }
        return null;
    }

    @Override
    public String gameResult(String match_id) throws RemoteException {
        for (Match active_match : ServerHandling.getInstance().active_matches) {
            if(active_match.getId().equals(match_id)){
                return active_match.gameResult();
            }
        }
        return null;
    }
}
