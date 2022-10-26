package com.wonders.servers.game.cards;

public class Card {
    private String type; // BLUE , RED, BROWN, GREY, GREEN, YELLOW and PURPLE

    // for resource cards
    private String name;
    private String cost;

    private String effect;

    // for special cards
    private String target_players;
    private String target_cards;
    private String prize;

    private int discount;

    public Card() {
    }

    public Card(String card){
        String[] c = card.split("_");
        if (c[0].equals("YC") || c[0].equals("PC")) {
            String[] effect = c[2].split("-");
            String price = effect[1].split("=")[1];
            target_players = effect[0];
            target_cards = effect[1].split("=")[0];
            prize = price;
        }
        type = c[0];
        name = c[1];
        effect = c[2];
        cost = c[3];
    }

    public Card(String type, String name, String target_players, String target_cards, String prize, String cost) {
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.target_players = target_players;
        this.target_cards = target_cards;
        this.prize = prize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTarget_players() {
        return target_players;
    }

    public void setTarget_players(String target_players) {
        this.target_players = target_players;
    }

    public String getTarget_cards() {
        return target_cards;
    }

    public void setTarget_cards(String target_cards) {
        this.target_cards = target_cards;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String ci_card(){
        return type+"_"+name+"_"+effect;
    }

    @Override
    public String toString(){
        return type+"_"+name+"_"+effect+"_"+cost;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
