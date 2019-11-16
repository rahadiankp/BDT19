import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainApplication {
    public static void main(String[] args) {
        // disable mongo logging
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("reddit");
        System.out.println(mongoDatabase.getName());
        for(String collection : mongoDatabase.listCollectionNames()) {
            System.out.println(collection);
        }
    }
}
