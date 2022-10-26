package com.wonders.model;

public class Age {
    public String name;
    public int passing_side;
    public int discards;

    public Age(String age){
        set(age);
    }

    public void setAge(String age){
        set(age);
    }

    void set(String age){
        String[] data = age.split("#");
        name = data[0];
        discards = Integer.parseInt(data[1]);
        passing_side = Integer.parseInt(data[2]);
    }
}
