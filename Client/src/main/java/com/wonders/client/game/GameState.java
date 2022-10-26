package com.wonders.client.game;

import java.util.ArrayList;

public class GameState {

    private ArrayList<Card> cityCards; // this will store city cards. cards which are placed into city.
    private String city_name;
    private String default_resource;

    private String stages;

    private int coins;
    private int shields;

    private int conflicts_points;
    private int stage_points;
    private int blue_card_points;
    private int coin_points;
    private int yellow_card_points;
    private int purple_card_points;
    private int green_card_points;

    public GameState(){
        cityCards = new ArrayList<>();
    }

    public ArrayList<Card> getCityCards() {
        return cityCards;
    }

    public void setCityCards(ArrayList<Card> cityCards) {
        this.cityCards = cityCards;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getConflicts_points() {
        return conflicts_points;
    }

    public void setConflicts_points(int conflicts_points) {
        this.conflicts_points = conflicts_points;
    }

    public int getStage_points() {
        return stage_points;
    }

    public void setStage_points(int stage_points) {
        this.stage_points = stage_points;
    }

    public int getBlue_card_points() {
        return blue_card_points;
    }

    public void setBlue_card_points(int blue_card_points) {
        this.blue_card_points = blue_card_points;
    }

    public int getCoin_points() {
        return coin_points;
    }

    public void setCoin_points(int coin_points) {
        this.coin_points = coin_points;
    }

    public int getYellow_card_points() {
        return yellow_card_points;
    }

    public void setYellow_card_points(int yellow_card_points) {
        this.yellow_card_points = yellow_card_points;
    }

    public int getPurple_card_points() {
        return purple_card_points;
    }

    public void setPurple_card_points(int purple_card_points) {
        this.purple_card_points = purple_card_points;
    }

    public int getGreen_card_points() {
        return green_card_points;
    }

    public void setGreen_card_points(int green_card_points) {
        this.green_card_points = green_card_points;
    }

    public String getStages() {
        return stages;
    }

    public void setStages(String stages) {
        this.stages = stages;
    }

    public int getShields() {
        return shields;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public String getDefault_resource() {
        return default_resource;
    }

    public void setDefault_resource(String default_resource) {
        this.default_resource = default_resource;
    }
}
