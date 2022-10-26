package com.wonders.servers.game.cities;

import com.wonders.servers.game.cards.Card;

import java.util.ArrayList;

public class City extends CityFactory {

    private String name;
    private int brownDiscount; // 1 for left and 2 for right and 3 for both
    private int greyDiscount; // 1 for left and 2 for right and 3 for both

    public ArrayList<Stage> stages;

    public ArrayList<Card> city_cards;
    public Card newCard;

    public Card city_resource;

    public ArrayList<Resource> resources;

    public City(String name) {
        this.name = name;
        city_cards = new ArrayList<>();
        stages = new ArrayList<>();
        resources = new ArrayList<>();
    }

    public void placeCard(Card card) {
        newCard = card;
    }

    public void clearNearCard() {
        if (newCard == null) return;
        city_cards.add(newCard);
        newCard = null;
    }

    public void addResource(String res) {
        String[] resource = res.split(":");
        for (Resource r : resources) {
            if (r.isRes(resource[0], Integer.parseInt(resource[1]))) {
                return;
            }
        }
        resources.add(new Resource(resource[0], Integer.parseInt(resource[1])));
    }

    public int haveResource(String res){
        int amount = 0;
        if (city_resource.getEffect().split(":")[0].equals(res)) amount++;
        for (Resource resource : resources) {
            int m = resource.getRes(res);
            if(m != -1){
                amount +=  m;
            }
        }
        return amount;
    }

//    public int haveResource(String res) {
//        int amt = 0;
//        if (city_resource.getEffect().split(":")[0].equals(res)) amt++;
//        for (Card city_card : city_cards) {
//            String effect;
//            if (city_card.getType().equals("YC") || city_card.getType().equals("PC"))
//                effect = city_card.getPrize();
//            else effect = city_card.getEffect();
//
//            if (effect.contains("/")) {
//                String[] e = effect.split("/");
//                for (String s : e) {
//                    String[] r = s.split(":");
//                    if (res.equals(r[0])) {
//                        amt += Integer.parseInt(r[1]);
//                    }
//                }
//            } else {
//                String[] r = effect.split(":");
//                if (res.equals(r[0])) {
//                    amt += Integer.parseInt(r[1]);
//                }
//            }
//        }
//        return amt;
//    }

    public int gotDiscount(String targetCard) {
        if(targetCard.equals("BC")){
            return brownDiscount;
        }else if(targetCard.equals("GC")){
            return greyDiscount;
        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStage() {
        String stage = "";
        for (Stage s : stages) {
            stage = stage.concat(s.toString()).concat(",");
        }
        return stage;
    }

    public void setStage(String stage) {
        String[] stages = stage.substring(stage.indexOf("(") + 1, stage.indexOf(")"))
                .split(",");
        int count = 0;
        for (String s : stages) {
            this.stages.add(new Stage(s, ++count));
        }

    }

    public int calculate_type_cards(String type) {
        int cards = 0;
        for (Card city_card : city_cards) {
            if (city_card.getType().equals(type)) cards++;
        }
        return cards;
    }

    public void setDefault_resource(String card) {
        city_resource = new Card(card);
    }

    public String cityCards() {
        String cards = "";
        for (Card city_card : city_cards) {
            cards = cards.concat(city_card.ci_card()).concat(",");
        }
        return cards;
    }

    public int completedStages() {
        int count = 0;
        for (Stage stage : stages) {
            if (stage.isComplete()) count++;
        }
        return count;
    }

    public int getBrownDiscount() {
        return brownDiscount;
    }

    public void setBrownDiscount(int brownDiscount) {
        this.brownDiscount = brownDiscount;
    }

    public int getGreyDiscount() {
        return greyDiscount;
    }

    public void setGreyDiscount(int greyDiscount) {
        this.greyDiscount = greyDiscount;
    }

    public int totalStages() {
        return stages.size();
    }

    public Stage unFinishedStage() {
        return stages.get(completedStages());
    }
}

//    rhodos ( one ore card )
//    Olympia  ( one clay card )
//    bablyon ( one wood card )
//    EPHESOS ( papyrus card )
//    halikarnassos ( one clothe card )
//    alexandria ( one glass card )
//    gizah ( one stone card )
