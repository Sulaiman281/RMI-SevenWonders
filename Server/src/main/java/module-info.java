module com.wonders {
    requires java.rmi;
    requires log4j;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;


    exports com.wonders;
}