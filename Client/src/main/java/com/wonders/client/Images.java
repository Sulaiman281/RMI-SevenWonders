package com.wonders.client;

import com.wonders.client.pattern.Singleton;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Images {

    public static Image alexandria_city;
    public static Image babylon_city;
    public static Image ephesos_city;
    public static Image gizah_city;
    public static Image halicrnassus_city;
    public static Image olympia_city;
    public static Image rhodos_city;

    public static ArrayList<Image> wonders = new ArrayList<>();

    public static void loadCityImages(){
        try{
            alexandria_city = Singleton.getInstance().loadImage("images/alexandria");
            babylon_city = Singleton.getInstance().loadImage("images/babylon");
            ephesos_city = Singleton.getInstance().loadImage("images/ephesos");
            gizah_city = Singleton.getInstance().loadImage("images/gizah");
            halicrnassus_city = Singleton.getInstance().loadImage("images/halicarnassus");
            olympia_city = Singleton.getInstance().loadImage("images/olympia");
            rhodos_city = Singleton.getInstance().loadImage("images/rhodos");

            File file = new File(Images.class.getResource("images/wonders").toURI().getPath());
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                if(listFile.getName().contains(".png")){
                    wonders.add(new Image(listFile.toURI().toString()));
                }
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static Image getWonder(){
        return wonders.get(new Random().nextInt(wonders.size()));
    }
}
