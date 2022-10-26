package com.wonders.client.game;

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

    private String placeable;

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

    public Card(String type, String name, String effect, String cost) {
        this.type = type;
        this.name = name;
        this.effect = effect;
        this.cost = cost;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEffect() {
        return effect;
    }

    public String getCost() {
        return cost;
    }

    public String getTarget_players() {
        return target_players;
    }

    public String getTarget_cards() {
        return target_cards;
    }

    public String getPrize() {
        return prize;
    }

    public void setCityCard(String card){
        String[] c = card.split("_");
        if (c[0].equals("YC") || c[0].equals("PC")) {
            String[] effect = c[2].split("-");
            String price = effect[1].split("=")[1];
            target_players = effect[0];
            target_cards = effect[1];
            prize = price;
        }
        type = c[0];
        name = c[1];
        effect = c[2];

    }

    public String ci_card(){
        return type+"_"+name+"_"+effect;
    }

    @Override
    public String toString(){
        return type+"_"+name+"_"+effect+"_"+cost;
    }

    public String placeBoard(){
        return type+"_"+name+"_"+effect+"_"+cost+"#"+placeable;
    }

    public String getPlaceable() {
        return placeable;
    }

    public void setPlaceable(String placeable) {
        this.placeable = placeable;
    }
}
