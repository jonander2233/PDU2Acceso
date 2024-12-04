package org.practica.drivers;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.bson.Document;
import org.practica.utils.PasswordEncryption;

public class MongoSingle {

    private static String routeProperties = "src" + File.separator + "main" +File.separator+ "resources" +File.separator+ "mongo_db_properties.properties";
    private static String routeKey = "src" + File.separator + "main" +File.separator+ "resources" +File.separator+ "key";
    private static String DB_NAME, MONGO_USER,MONGO_PASSWORD_ENCRYPTED,MONGO_PASSWORD,MONGO_PORT;
    private static SecretKey MONGO_DECRYPT_KEY;
    private static String MONGO_HOST = "localhost";
    private static MongoSingle instance;
    private static String url = "";
    private Properties prop = null;

    private MongoClient dbClient;
    private MongoDatabase database;

    private MongoSingle()  throws Exception{
        try {
            this.prop = getProperties(routeProperties);
            DB_NAME = prop.getProperty("dbName");
            MONGO_USER = prop.getProperty("mongo_user");
            MONGO_PASSWORD_ENCRYPTED = prop.getProperty("mongo_password");
            MONGO_PORT = prop.getProperty("mongo_port");
            MONGO_DECRYPT_KEY = PasswordEncryption.loadKeyFromFile(routeKey,"AES");
            MONGO_PASSWORD = PasswordEncryption.decrypt(MONGO_PASSWORD_ENCRYPTED,MONGO_DECRYPT_KEY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        MONGO_HOST = prop.getProperty("host");
        url = "mongodb://" + MONGO_USER +":"+ MONGO_PASSWORD +"@"+ MONGO_HOST +":" + MONGO_PORT +"/"+DB_NAME;

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.OFF);
        dbClient = MongoClients.create(url);
        database = dbClient.getDatabase("jaap");
        database.runCommand(new Document("ping", 1));
    }

    public static MongoSingle getInstance() throws Exception {
        if(instance == null){
            return new MongoSingle();
        }
        return instance;
    }

    public MongoClient getDbClient(){
        return dbClient;
    }
    public MongoDatabase getDb(){
        return database;
    }

//    public boolean closeConection(){
//
//    }
    private static Properties getProperties(String ruta) throws IOException {
        File archivo = new File(ruta);
        boolean existe = archivo.exists();
        FileInputStream fis = new FileInputStream(archivo);
        Properties prop = new Properties();
        prop.load(fis);
        return prop;
    }
}
