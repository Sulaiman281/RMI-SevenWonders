package com.wonders.servers.game;

import com.wonders.servers.game.cards.Card;
import com.wonders.servers.game.cities.City;
import com.wonders.servers.game.cities.CityFactory;
import com.wonders.servers.game.cities.Stage;

import java.util.ArrayList;

public class PlayerMatchState {

    public enum State {
        CARD_REQUEST,
        CARD_PLACING,
        CARD_PLACED,
        CARD_PASSED,
        CONFLICTS,
    }

    public State state;

    private City city;
    private ArrayList<Card> cards;

    private int coins;
    private int shields;


    private int buy_resource_value;
    private int conflicts_points;
    private int stage_points;
    private int blue_card_points;
    private int coin_points;
    private int yellow_card_points;
    private int purple_card_points;
    private int green_card_points;

    public boolean lastCard;
    public boolean copyGuild;
    public boolean discard;
    public boolean freecard;

    public PlayerMatchState() {
        buy_resource_value = 2;
        coins = 3;
        cards = new ArrayList<>();
        state = State.CARD_REQUEST;
    }

    // can we place card in city if yes what cost
    public String card_cityRequirement(Card card, PlayerMatchState ln, PlayerMatchState rn) {
        // if city can pay the cost of card or not.
        // can he purchase the card from neighbours?
        // is it for free?
        String cost = card.getCost();
        if (cost.equals("FREE")) {
            return cost;
        }
        int discount = city.gotDiscount(card.getType());
        if (cost.contains("&")) {
            String[] costs = cost.split("&");
            String str = "";
            for (String s : costs) {
                String result = costRequire(s, discount, ln, rn);
                if (result.equals("RED")) return result;
                str = str.concat(result).concat("$");
            }
            return str;
        } else {
            if (cost.contains("COIN")) {
                if (Integer.parseInt(cost.split(":")[1]) > coins) {
                    return "RED";
                }
                return cost;
            }
            return costRequire(cost, discount, ln, rn);
        }
    }

    public String stage_requirement(Stage stage, PlayerMatchState ln, PlayerMatchState rn) {
        if (stage.getCost().contains("&")) {
            String[] costs = stage.getCost().split("&");
            String price = "";
            for (String cost : costs) {
                String[] c = cost.split(":");
                int discount = city.gotDiscount(getCardType(c[0]));
                String result = costRequire(cost, discount, ln, rn);
                if (result.equals("RED")) return result;
                price = price.concat(result).concat("$");
            }
            return price;
        } else {
            String[] c = stage.getCost().split(":");
            int discount = city.gotDiscount(getCardType(c[0]));
            return costRequire(stage.getCost(), discount, ln, rn);
        }
    }

    String costRequire(String cost, int discount, PlayerMatchState ln, PlayerMatchState rn) {
        int res_cost = -1;
        String[] c = cost.split(":");
        int amount = Integer.parseInt(c[1]);
        int cityRes = city.haveResource(c[0]);
        int remain = amount - cityRes;
        if (remain <= 0) {
            // city have the resource so the card is for free
            return "FREE";
        } else {
            int left_cityRes = ln.city.haveResource(c[0]);
            int r = remain - left_cityRes;
            if (r <= 0) {
                // the resource found in left neighbours and num of R * buy_cost is the cost.
                if (discount == 1 || discount == 3) {
                    res_cost = remain;
                } else {
                    res_cost = remain * buy_resource_value;
                }
                if (freecard) return "FREE";
                if (res_cost > coins) return "RED";
                return "LCOIN:" + res_cost;
            } else {
                String str = "";
                if (left_cityRes > 0) {
                    if (discount == 1 || discount == 3) {
                        res_cost = left_cityRes;
                    } else {
                        res_cost = left_cityRes * buy_resource_value;
                    }
                    if (freecard) return "FREE";
                    if (res_cost > coins) return "RED";
                    str = str.concat("LCOIN:" + res_cost);
                }
                int right_cityRes = rn.city.haveResource(c[0]);
                int l = r - right_cityRes;
                if (l <= 0) {
                    // resource found and finish in right neighbour
                    // (r+left)*buy_cost is the cost.
                    if (discount == 2 || discount == 3) {
                        res_cost = r;
                    } else {
                        res_cost = r * buy_resource_value;
                    }
                    if (freecard) return "FREE";
                    if (res_cost > coins) return "RED";
                    return str.isEmpty() ? "RCOIN:" + res_cost : str.concat("&RCOIN:" + res_cost);
                } else {
                    return "RED";
                }// resource can not be purchased.
            }
        }
    }

    String getCardType(String res) {
        if (res.equals("WOOD") || res.equals("STONE") || res.equals("ORE") || res.equals("CLAY")) {
            return "BC";
        }
        if (res.equals("CLOTHE") || res.equals("PAPYRUS") || res.equals("GLASS"))
            return "GC";
        return "";
    }

    public void add_balance(int coins) {
        this.coins += coins;
    }

    public void minus_balance(int coins) {
        if (coins > this.coins) return;
        this.coins -= coins;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void setCity(String city) {
        this.city = CityFactory.getFactory(city);
    }

    public boolean isCitySelected() {
        return city != null;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getShields() {
        return shields;
    }

    public void addPoints(int points, String type) {
        switch (type) {
            case "VP" -> addStage_points(points);
            case "SHIELD" -> shields += points;
            case "YC" -> addYellow_cardPoints(points);
            case "PC" -> add_purplePoints(points);
            case "COIN" -> add_balance(points);
        }
    }

    public int totalVP() {

        int points;

        int tablets = (int) Math.pow(city.haveResource("TABLET"), 2);
        int gear = (int) Math.pow(city.haveResource("GEAR"), 2);
        int compass = (int) Math.pow(city.haveResource("COMPASS"), 2);

        int set = tablets * gear * compass;
        int c = coins / 3;
        points = tablets + gear + compass + (set != 0 ? 7 : 0) + c + stage_points + yellow_card_points + purple_card_points + blue_card_points + conflicts_points;
        return points;
    }

    public int getConflicts_points() {
        return conflicts_points;
    }

    public void addConflictsPoints(int conflicts_points) {
        this.conflicts_points += conflicts_points;
    }

    public void addStage_points(int points) {
        this.stage_points += points;
    }

    public void addBlue_cardPoints(int points) {
        this.blue_card_points += points;
    }

    public void addYellow_cardPoints(int points) {
        this.yellow_card_points += points;
    }


    public void add_purplePoints(int points) {
        this.purple_card_points += points;
    }

    public City getCity() {
        return city;
    }
}
