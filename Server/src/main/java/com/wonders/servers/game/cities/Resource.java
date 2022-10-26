package com.wonders.servers.game.cities;

public class Resource {

    private final String name;
    private int amount;

    public Resource(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public void updateResource() {
        if (name.equals("WOOD") || name.equals("STONE") || name.equals("ORE") || name.equals("CLAY") ||
                name.equals("CLOTHE") || name.equals("PAPYRUS") || name.equals("GLASS"))
            amount++;
    }

    public boolean isRes(String res, int value) {
        if (res.equals(name)) {
            amount += value;
            return true;
        }
        return false;
    }

    public int getRes(String res) {
        if (res.equals(name)) {
            return amount;
        }
        return -1;
    }
}
