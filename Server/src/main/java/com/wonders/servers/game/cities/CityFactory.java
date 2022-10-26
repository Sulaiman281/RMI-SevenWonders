package com.wonders.servers.game.cities;

import com.wonders.servers.game.Stages;

import java.util.Random;

public abstract class CityFactory {

    public static City getFactory(String city_name) {
        if (city_name.isEmpty()) return null;
        City city = new City(city_name);
        switch (city_name) {
            case "rhodos" -> {
                city.setDefault_resource("BC_City Resource_ORE:1_FREE");
                city.setStage(Stages.rhodes[new Random().nextInt(2)]);
            }
            case "olympia" -> {
                city.setDefault_resource("BC_City Resource_CLAY:1_FREE");
                city.setStage(Stages.olympia[new Random().nextInt(2)]);
            }
            case "babylon" -> {
                city.setDefault_resource("BC_City Resource_WOOD:1_FREE");
                city.setStage(Stages.babylon[new Random().nextInt(2)]);
            }
            case "ephesos" -> {
                city.setDefault_resource("GC_City Resource_PAPYRUS:1_FREE");
                city.setStage(Stages.ephesus[new Random().nextInt(2)]);
            }
            case "halicarnassus" -> {
                city.setDefault_resource("GC_City Resource_CLOTHE:1_FREE\"");
                city.setStage(Stages.halicarnassus[new Random().nextInt(2)]);
            }
            case "alexandria" -> {
                city.setDefault_resource("GC_City Resource_GLASS:1_FREE");
                city.setStage(Stages.alexandria[new Random().nextInt(2)]);
            }
            case "gizah" -> {
                city.setDefault_resource("BC_City Resource_STONE:1_FREE");
                city.setStage(Stages.giza[new Random().nextInt(2)]);
            }
        }
        return city;
    }
}
