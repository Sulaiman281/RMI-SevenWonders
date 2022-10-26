package com.wonders.model;

import com.wonders.client.game.Card;
import com.wonders.client.game.GameState;

import java.util.ArrayList;

public class Player {

    private String name;
    private String email;
    private String password;

    private GameState gameState;
    private ArrayList<Card> cards;

    public Player() {
    }

    public Player(String name){
        this.name = name;
    }

    public Player(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Player(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GameState getGameState() {
        if(gameState == null) gameState = new GameState();
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public ArrayList<Card> getCards() {
        if(cards == null) cards = new ArrayList<>();
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return name;
    }
}
