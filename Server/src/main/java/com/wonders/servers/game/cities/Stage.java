package com.wonders.servers.game.cities;

public class Stage {

    private int id;
    private String effect;
    private String cost;
    private boolean isComplete;

    public Stage(String stage, int id){
        this.id = id;
        String[] s = stage.split("_");
        effect = s[0];
        cost = s[1];
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getCost() {
        return cost;
    }

    public String getEffect() {
        return effect;
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return effect+"_"+cost;
    }
}
