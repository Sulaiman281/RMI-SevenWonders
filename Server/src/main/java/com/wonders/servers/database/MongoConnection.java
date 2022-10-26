package com.wonders.servers.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.wonders.Settings;

public class MongoConnection {

    private MongoDatabase db;
    private MongoClient client;

    public void authorize() throws Exception {
//        client = MongoClients.create("mongodb://cluster0-shard-00-00.2sa8t.mongodb.net:27017,cluster0-shard-00-01.2sa8t.mongodb.net:27017,cluster0-shard-00-02.2sa8t.mongodb.net:27017/sevenwonders?ssl=true&replicaSet=atlas-yu9vlv-shard-0&authSource=admin&retryWrites=true&w=majority");
        client = MongoClients.create();//"mongodb://"+ InetAddress.getLocalHost().getHostAddress()+":27017");
        db = client.getDatabase(Settings.database);
    }

    public MongoDatabase dBase(){
        return db;
    }
}
