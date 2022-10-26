package com.wonders.model;

import com.wonders.servers.game.PlayerMatchState;
import org.bson.Document;

public class Player {

    private String name;

    public PlayerMatchState matchState;

    private String email;
    private String password;

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

    public PlayerMatchState getMatchState() {
        return matchState;
    }

    public void setMatchState(PlayerMatchState matchState) {
        this.matchState = matchState;
    }

    public Document nameQuery(){
        Document document = new Document();
        document.append("name",name);
        return document;
    }

    public Document emailQuery(){
        Document document = new Document();
        document.append("email", email);
        return document;
    }
    public Document authQuery(){
        Document document = new Document();
        document.append("name",name);
        document.append("password", password);
        return document;
    }

    public Document getData(){
        Document document = new Document();
        document.append("name",name);
        document.append("email", email);
        document.append("password", password);
        return document;
    }

    @Override
    public String toString() {
        return name;
    }
}
