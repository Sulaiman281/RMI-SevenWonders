package com.wonders.servers.game;

import com.wonders.Settings;
import com.wonders.model.Player;
import com.wonders.servers.Room;
import com.wonders.servers.ServerHandling;
import com.wonders.servers.game.cards.Card;
import com.wonders.servers.game.cards.EffectHandler;
import com.wonders.servers.game.cards.CardsList;
import com.wonders.servers.game.cities.Resource;
import com.wonders.servers.game.cities.Stage;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Random;

public class Match {

    private Room lobby;

    public enum State {
        CITY_SELECTION,
        AGE_I_CARD,
        AGE_I_PLAY,
        AGE_II_CARD,
        AGE_II_PLAY,
        AGE_III_CARD,
        AGE_III_PLAY,
        GAME_RESULT, // end of the game showing game results
    }

    public State state;

    private final String id;
    private final ArrayList<Player> players;

    private final ArrayList<Card> card_box;

    String age;
    private final ArrayList<Card> discard;

    private int passing_side; // 1 for anticlockwise and 0 for clockwise.

    public Match(Room lobby, String id) {
        this.id = id;
        this.lobby = lobby;
        this.players = lobby.getPlayers();
        card_box = new ArrayList<>();
        discard = new ArrayList<>();

        for (Player player : this.players) {
            player.setMatchState(new PlayerMatchState());
        }

        state = State.CITY_SELECTION;
    }

    public String game_status() {
        return age.concat("#").concat(discard.size() + "#").concat(passing_side + "");
    }

    void fill_card_box() {
        String card;
        for (int i = 0; i < 28; ) {
            switch (state) {
                case AGE_I_CARD -> {
                    if (i >= CardsList.age_I.length) {
                        card = CardsList.age_I[new Random().nextInt(CardsList.age_I.length)];
                        i++;
                    } else {
                        card = CardsList.age_I[i++];
                    }
                    card_box.add(new Card(card));
                    age = "AGE_I";
                    passing_side = 0;
                }
                case AGE_II_CARD -> {
                    if (i >= CardsList.age_II.length) {
                        card = CardsList.age_II[new Random().nextInt(CardsList.age_II.length)];
                        i++;
                    } else {
                        card = CardsList.age_II[i++];
                    }
                    card_box.add(new Card(card));
                    age = "AGE_II";
                    passing_side = 1;
                }
                case AGE_III_CARD -> {
                    if (i >= CardsList.age_III.length) {
                        card = CardsList.age_III[new Random().nextInt(CardsList.age_III.length)];
                        i++;
                    } else {
                        card = CardsList.age_III[i++];
                    }
                    card_box.add(new Card(card));
                    age = "AGE_III";
                    passing_side = 0;
                }
            }
        }

        // distribute 7 cards to each player after getting AGE card.
        for (Player player : players) {
            // get 7 cards from box and give it to player.
            for (int i = 0; i < 7; i++) {
                player.getMatchState().getCards().add(
                        card_box.remove(new Random().nextInt(card_box.size()))
                );
            }
        }
        switch (state) {
            case AGE_I_CARD -> state = State.AGE_I_PLAY;
            case AGE_II_CARD -> state = State.AGE_II_PLAY;
            case AGE_III_CARD -> state = State.AGE_III_PLAY;
        }
    }

    public String playersCitySelectionState() {
        String str = "";
        for (Player player : players) {
            str = str.concat(player.getName() + ":" + (player.getMatchState().isCitySelected() ? player.getMatchState().getCity().getName() : "null") + ",");
        }
        return str;
    }

    public int city_selected(String player_name, String cityName) {
        for (Player player : players) {
            if (!player.getMatchState().isCitySelected()) continue;
            if (player.getMatchState().getCity().getName().equals(cityName))
                return 1; // city is already selected
        }
        for (Player player : players) {
            if (player.getName().equals(player_name)) {
                player.getMatchState().setCity(cityName);
                return 0; // city is selected successfully
            }
        }
        return -1; // when no things happen.
    }

    // CARD#PLACABLE#DISCOUNT
    public String card_request(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                player.getMatchState().state = PlayerMatchState.State.CARD_PLACING;
                return getPlayerCards(player);
            }
        }
        return null;
    }

    String getPlayerCards(Player player) {
        String str = "";
        int index = players.indexOf(player);
        for (Card card : player.getMatchState().getCards()) {
            card.setDiscount(player.getMatchState().getCity().gotDiscount(card.getType()));
            String requirement = player.getMatchState().card_cityRequirement(card, (index == 0) ? players.get(3).matchState : players.get(index - 1).matchState
                    , (index == 3) ? players.get(0).matchState : players.get(index + 1).matchState);
            str = str.concat(card.toString()).concat("#") // CARD
                    .concat(requirement) + ","; // DISCOUNT
        }
        return str;
    }

    // cityName#stages#defaultResource
    public String playerSetup(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                return player.getMatchState().getCity().getName().concat("#")
                        .concat(player.getMatchState().getCity().getStage()).concat("#")
                        .concat(player.getMatchState().getCity().city_resource.ci_card());
            }
        }
        return null;
    }

    public String getOpponents(String playerName) {
        int index = -1;
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                index = players.indexOf(player);
            }
        }
        if (index == -1) return null;
        String str = "";
        for (int i = 0; i < 3; i++) {
            ++index;
            if (index == players.size()) index = 0;
            Player p = players.get(index);
            str = str.concat(p.getName()).concat("#") // NAME
                    .concat(p.getMatchState().getCity().getName()).concat("#") // CITY NAME
                    .concat(p.getMatchState().getCity().city_resource.ci_card()) + ","; // city resource
        }
        return str;
    }

    public boolean all_players_selected_city() {
        for (Player player : players) {
            if (!player.getMatchState().isCitySelected()) {
                return false;
            }
        }
        if (state == State.CITY_SELECTION) {
            state = State.AGE_I_CARD;
            fill_card_box();
        }
        return true;
    }

    public String playerStatusAs_Opponent(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                String str = String.format("%02d", player.getMatchState().getShields()).concat("#") // SHIELD
                        .concat(String.format("%02d", player.getMatchState().getCoins())).concat("#") // COIN
                        .concat(player.getMatchState().getCity().getStage()).concat("#"); // STAGE
                str = str.concat(player.getMatchState().getCity().cityCards()); // CITYBUILDS
                return str;
            }
        }
        return null;
    }

    public String playerUpdate(String name) {
        //COINS#SHIELD#STAGES#CITYCARD
        for (Player player : players) {
            if (player.getName().equals(name)) {
                String str = player.getMatchState().getCoins() + "#"
                        + player.getMatchState().getShields() + "#";

                for (Stage stage : player.getMatchState().getCity().stages) {
                    str = str.concat(stage.getEffect().concat("_").concat(stage.isComplete() + "")).concat(",");
                }
                String cityCards = player.matchState.getCity().cityCards();
                if (cityCards.isEmpty())
                    return str;
                str = str.concat("#").concat(player.getMatchState().getCity().cityCards());
                return str;
            }
        }
        return null;
    }

    public String card_action(String playerName, String move) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                if (player.getMatchState().state == PlayerMatchState.State.CARD_PLACING) {
                    String result = playerMove(playerName, move);
                    if (result.equals("0")) {
                        player.getMatchState().state = PlayerMatchState.State.CARD_PLACED;
                        if (playersSameState(PlayerMatchState.State.CARD_PLACED)) {
                            passCard();
                            setPlayersState(PlayerMatchState.State.CARD_PASSED);
                        }
                    }
                    return result;
                }
            }
        }
        return "Player Not found";
    }

    boolean playersSameState(PlayerMatchState.State state) {
        for (Player player : players) {
            if (player.getMatchState().state != state) {
                return false;
            }
        }
        return true;
    }

    void setPlayersState(PlayerMatchState.State state) {
        for (Player player : players) {
            player.getMatchState().state = state;
        }
    }

    String playerMove(String name, String move) {
        Player player = null;
        for (Player p : players) {
            if (p.getName().equals(name)) {
                player = p;
            }
        }
        if (player == null) return "ERROR: Player Not found";
        String[] action = move.split("#");
        switch (action[0]) {
            case "SELL" -> {
                for (Card card : player.getMatchState().getCards()) {
                    if (card.toString().equals(action[1])) {
                        discard.add(card);
                        player.getMatchState().getCards().remove(card);
                        player.getMatchState().setCoins(player.getMatchState().getCoins() + 3);
                        return 0 + ""; // Success
                    }
                }
                return "ERROR: Card Not found";
            }
            case "CITY" -> {
                for (Card card : player.getMatchState().getCards()) {
                    if (card.toString().equals(action[1])) {
                        for (Card city_card : player.getMatchState().getCity().city_cards) {
                            if (city_card.getName().equals(card.getName())) {
                                return "ERROR: same card can not be placed";
                            }
                        }
                        int index = players.indexOf(player);
                        Player leftNeighbour = players.get((index == 0) ? 3 : index - 1);
                        Player rightNeighbour = players.get((index == 3) ? 0 : index + 1);
                        player.getMatchState().getCards().remove(card);
                        player.getMatchState().getCity().placeCard(card);
                        EffectHandler.add_card_in_city(card, player.getMatchState(), leftNeighbour.matchState, rightNeighbour.matchState);
                        if (action[2].contains("$")) {
                            String[] costs = action[2].split("\\$");
                            for (String cost : costs) {
                                if (cost.contains("&")) {
                                    String[] buyers = cost.split("&");
                                    for (String buyer : buyers) {
                                        if (buyer.contains("RCOIN")) {
                                            int amount = Integer.parseInt(buyer.split(":")[1]);
                                            player.getMatchState().minus_balance(amount);
                                            rightNeighbour.getMatchState().add_balance(amount);
                                        } else if (buyer.contains("LCOIN")) {
                                            int amount = Integer.parseInt(buyer.split(":")[1]);
                                            player.getMatchState().minus_balance(amount);
                                            leftNeighbour.getMatchState().add_balance(amount);
                                        }
                                    }
                                } else {
                                    if (cost.contains("RCOIN")) {
                                        int amount = Integer.parseInt(cost.split(":")[1]);
                                        player.getMatchState().minus_balance(amount);
                                        rightNeighbour.getMatchState().add_balance(amount);
                                    } else if (cost.contains("LCOIN")) {
                                        int amount = Integer.parseInt(cost.split(":")[1]);
                                        player.getMatchState().minus_balance(amount);
                                        leftNeighbour.getMatchState().add_balance(amount);
                                    } else if (cost.contains("COIN")) {
                                        int amount = Integer.parseInt(cost.split(":")[1]);
                                        player.getMatchState().minus_balance(amount);
                                    }
                                }
                            }
                        } else {
                            if (action[2].contains("RCOIN")) {
                                int amount = Integer.parseInt(action[2].split(":")[1]);
                                player.getMatchState().minus_balance(amount);
                                rightNeighbour.getMatchState().add_balance(amount);
                            } else if (action[2].contains("LCOIN")) {
                                int amount = Integer.parseInt(action[2].split(":")[1]);
                                player.getMatchState().minus_balance(amount);
                                leftNeighbour.getMatchState().add_balance(amount);
                            } else if (action[2].contains("COIN")) {
                                int amount = Integer.parseInt(action[2].split(":")[1]);
                                player.getMatchState().minus_balance(amount);
                            }
                        }
                        return "0";
                    }
                }
                return "0";
//                return "Card Not found\t"+action[1]+"\n"+player.getMatchState().getCards().toString();
            }
            case "STAGE" -> {
                Card card = null;
                for (Card c : player.getMatchState().getCards()) {
                    if (c.toString().equals(action[1])) {
                        card = c;
                        break;
                    }
                }
                if (card == null) return "Card Not found";
                Stage stage = player.matchState.getCity().unFinishedStage();
                int index = players.indexOf(player);
                Player leftNeighbour = players.get((index == 0) ? 3 : index - 1);
                Player rightNeighbour = players.get((index == 3) ? 0 : index + 1);
                String result = player.matchState.stage_requirement(stage, leftNeighbour.matchState, rightNeighbour.matchState);
                if (result.equals("RED")) return "Not enough resources" + result;
                if (result.equals("FREE")) {
                    stage.setComplete(true);
                    EffectHandler.stageEffect(stage.getEffect(), player.getMatchState());
                    player.matchState.getCards().remove(card);
                    return 0 + "";
                }
                if (result.contains("$")) {
                    String[] prices = result.split("\\$");
                    for (String price : prices) {
                        if (price.equals("FREE")) continue;
                        if (result.contains("&")) {
                            String[] buyers = result.split("&");
                            int leftCost = Integer.parseInt(buyers[0].split(":")[1]);
                            if (leftCost > player.matchState.getCoins()) return 1 + "";
                            int rightCost = Integer.parseInt(buyers[1].split(":")[1]);
                            if (rightCost > player.matchState.getCoins() - leftCost) return 1 + "";
                            leftNeighbour.matchState.add_balance(leftCost);
                            rightNeighbour.matchState.add_balance(rightCost);
                            player.matchState.minus_balance(leftCost + rightCost);
                            player.matchState.getCards().remove(card);
                            stage.setComplete(true);
                            EffectHandler.stageEffect(stage.getEffect(), player.getMatchState());
                            return 0 + "";
                        } else {
                            if (result.contains("LCOIN")) {
                                int leftCost = Integer.parseInt(result.split(":")[1]);
                                if (leftCost > player.matchState.getCoins()) return 1 + "";
                                leftNeighbour.matchState.add_balance(leftCost);
                                player.matchState.minus_balance(leftCost);
                                player.matchState.getCards().remove(card);
                                stage.setComplete(true);
                                EffectHandler.stageEffect(stage.getEffect(), player.getMatchState());
                                return 0 + "";
                            } else {
                                int cost = Integer.parseInt(result.split(":")[1]);
                                if (cost > player.matchState.getCoins()) return 1 + "";
                                player.matchState.minus_balance(cost);
                                player.matchState.getCards().remove(card);
                                stage.setComplete(true);
                                EffectHandler.stageEffect(stage.getEffect(), player.getMatchState());
                                return 0 + "";
                            }
                        }
                    }
                } else if (result.contains("&")) {
                    String[] buyers = result.split("&");
                    int leftCost = Integer.parseInt(buyers[0].split(":")[1]);
                    if (leftCost > player.matchState.getCoins()) return 1 + "";
                    int rightCost = Integer.parseInt(buyers[1].split(":")[1]);
                    if (rightCost > player.matchState.getCoins() - leftCost) return 1 + "";
                    leftNeighbour.matchState.add_balance(leftCost);
                    rightNeighbour.matchState.add_balance(rightCost);
                    player.matchState.minus_balance(leftCost + rightCost);
                    player.matchState.getCards().remove(card);
                    EffectHandler.stageEffect(stage.getEffect(), player.getMatchState());
                    stage.setComplete(true);
                    return 0 + "";
                } else {
                    if (result.contains("LCOIN")) {
                        int leftCost = Integer.parseInt(result.split(":")[1]);
                        if (leftCost > player.matchState.getCoins()) return 1 + "";
                        leftNeighbour.matchState.add_balance(leftCost);
                        player.matchState.minus_balance(leftCost);
                        player.matchState.getCards().remove(card);
                        EffectHandler.stageEffect(stage.getEffect(), player.getMatchState());
                        stage.setComplete(true);
                        return 0 + "";
                    } else {
                        int cost = Integer.parseInt(result.split(":")[1]);
                        if (cost > player.matchState.getCoins()) return 1 + "";
                        player.matchState.minus_balance(cost);
                        player.matchState.getCards().remove(card);
                        EffectHandler.stageEffect(stage.getEffect(), player.getMatchState());
                        stage.setComplete(true);
                        return 0 + "";
                    }
                }
                return "" + result;
            }
        }
        return "invalid action.";
    }

    public String card_passRequest(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                if (player.getMatchState().state != PlayerMatchState.State.CARD_PASSED) return "WAIT";
                if (player.getMatchState().getCards().size() == 1) {
                    discard.add(player.getMatchState().getCards().remove(0));
                    // MilitaryConflict here.
                    player.getMatchState().state = PlayerMatchState.State.CONFLICTS;
                    return "CONFLICT#" + conflict(player);
                }
                player.matchState.getCity().clearNearCard();
                String result = getPlayerCards(player);
                if (result != null) {
                    player.matchState.state = PlayerMatchState.State.CARD_PLACING;
                }
                return result;
            }
        }
        return null;
    }

    String conflict(Player player) {
        int index = players.indexOf(player);
        int leftShield = players.get((index == 0) ? 3 : index - 1).matchState.getShields();
        int rightShield = players.get((index == 3) ? 0 : index + 1).matchState.getShields();
        int leftConflicts = player.getMatchState().getShields() - leftShield; // 0 is tie -1 lose +1 is won
        int rightConflicts = player.getMatchState().getShields() - rightShield;
        switch (state) {
            case AGE_I_PLAY -> {
                if (leftConflicts > 0) {
                    leftConflicts = 1;
                    player.getMatchState().addConflictsPoints(+1);
                } else if (leftConflicts < 0) {
                    leftConflicts = -1;
                    player.getMatchState().addConflictsPoints(-1);
                }
                if (rightConflicts > 0) {
                    rightConflicts = 1;
                    player.getMatchState().addConflictsPoints(+1);
                } else if (rightConflicts < 0) {
                    rightConflicts = -1;
                    player.getMatchState().addConflictsPoints(-1);
                }
            }
            case AGE_II_PLAY -> {
                if (leftConflicts > 0) {
                    leftConflicts = 3;
                    player.getMatchState().addConflictsPoints(+3);
                } else if (leftConflicts < 0) {
                    leftConflicts = -3;
                    player.getMatchState().addConflictsPoints(-3);
                }
                if (rightConflicts > 0) {
                    rightConflicts = 3;
                    player.getMatchState().addConflictsPoints(+3);
                } else if (rightConflicts < 0) {
                    rightConflicts = -3;
                    player.getMatchState().addConflictsPoints(-3);
                }
            }
            case AGE_III_PLAY -> {
                if (leftConflicts > 0) {
                    leftConflicts = 5;
                    player.getMatchState().addConflictsPoints(+5);
                } else if (leftConflicts < 0) {
                    leftConflicts = -5;
                    player.getMatchState().addConflictsPoints(-5);
                }
                if (rightConflicts > 0) {
                    rightConflicts = 5;
                    player.getMatchState().addConflictsPoints(+5);
                } else if (rightConflicts < 0) {
                    rightConflicts = -5;
                    player.getMatchState().addConflictsPoints(-5);
                }
            }
        }
        // LName:Shields:leftConflicts_PShields_RName:Shields:rightConflicts
        return players.get((index == 0) ? 3 : index - 1).getName().concat(":").concat(leftShield + ":")
                .concat(leftConflicts + "_").concat(player.getMatchState().getShields() + "_")
                .concat(players.get((index == 3) ? 0 : index + 1).getName()).concat(":" + rightShield + ":")
                .concat(rightConflicts + "");
    }

    int count = 0;

    public String nextAge(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                if(state == State.GAME_RESULT) return "GAMEOVER";
                if (player.getMatchState().state != PlayerMatchState.State.CONFLICTS) return "WAIT";
                if (count == 0) {
                    if (state == State.AGE_I_PLAY) {
                        state = State.AGE_II_CARD;
                    } else if (state == State.AGE_II_PLAY) {
                        state = State.AGE_III_CARD;
                    } else if (state == State.AGE_III_PLAY) {
                        state = State.GAME_RESULT;
                        return "GAMEOVER";
                    }
                    fill_card_box();
                }
                count++;
                if (count >= 4) {
                    count = 0;
                }
                player.getMatchState().state = PlayerMatchState.State.CARD_PLACING;
                return card_request(playerName);
            }
        }

        return null;
    }

    void passCard() {
        ArrayList<Card> tempA = players.get(0).matchState.getCards();
        ArrayList<Card> tempB = players.get(1).matchState.getCards();
        ArrayList<Card> tempC = players.get(2).matchState.getCards();
        ArrayList<Card> tempD = players.get(3).matchState.getCards();

        if (passing_side == 0) {
            // P1 > P2, P2 > P3, P3 > P4, P4 > P1
            // P1 > P4, P2 > P1, P3 > P2, P4 > P3
            players.get(0).getMatchState().setCards(tempB);
            players.get(1).getMatchState().setCards(tempC);
            players.get(2).getMatchState().setCards(tempD);
            players.get(3).getMatchState().setCards(tempA);
        } else {
            players.get(0).getMatchState().setCards(tempD);
            players.get(1).getMatchState().setCards(tempA);
            players.get(2).getMatchState().setCards(tempB);
            players.get(3).getMatchState().setCards(tempC);
        }
        for (Player player : players) {
            for (Resource resource : player.getMatchState().getCity().resources) {
                resource.updateResource();
            }
        }
    }

    public String gameResult() {
        String str = "";
        for (Player p : players) {
            str = str.concat(p.getName() + "#" + p.getMatchState().totalVP()).concat(",");
            saveData();
        }
        return str;
    }

    public void saveData(){
        Document document = new Document();
        document.put("MATCH_ID", id);
        for (Player player : players) {
            document.put("player_name", player.getName());
            document.put("victory_points", player.getMatchState().totalVP());
        }
        ServerHandling.getInstance().db_connection.dBase()
                .getCollection(Settings.database).insertOne(document);
    }

    public String getId() {
        return id;
    }

    public Room getLobby() {
        return lobby;
    }
}
