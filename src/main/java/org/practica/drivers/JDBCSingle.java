package org.practica.drivers;

import org.practica.utils.PasswordEncryption;

import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCSingle {
        private static String routeProperties = "src"+ File.separator + "main" + File.separator + "resources" + File.separator + "sql_db_properties.properties";
        private static String routeKey = "src" + File.separator + "main" +File.separator+ "resources" +File.separator+ "key";
        private static String DB_NAME = "", SQL_USER = "",SQL_PASSWORD_ENCRYPTED = "",SQL_PASSWORD = "",SQL_PORT = "";
        private static SecretKey SQL_DECRYPT_KEY;
        private static String SQL_HOST = "";

        private static JDBCSingle instance;
        private static String url = "";
        private Properties prop = null;

        private static Connection conexion;

    private JDBCSingle() throws Exception {
        try {
            prop = getProperties(routeProperties);
            DB_NAME = prop.getProperty("dbName");
            SQL_USER = prop.getProperty("sqluser");
            SQL_PASSWORD_ENCRYPTED = prop.getProperty("sqlpassword");
            SQL_PORT = prop.getProperty("port");
            SQL_DECRYPT_KEY = PasswordEncryption.loadKeyFromFile(routeKey,"AES");
            SQL_PASSWORD = PasswordEncryption.decrypt(SQL_PASSWORD_ENCRYPTED,SQL_DECRYPT_KEY);
            SQL_HOST = prop.getProperty("host");
            url = "jdbc:mysql://" + SQL_HOST + ":" + SQL_PORT + "/" + DB_NAME;
        } catch (IOException e) {
            System.out.println("Properties not found, trying with default properties");
            SQL_HOST = "localhost";
            DB_NAME = "";
            SQL_PORT = "3306";
            SQL_USER = "javaApp";
            SQL_PASSWORD = "javaApp";
            url = "jdbc:mysql://" + SQL_HOST + ":" + SQL_PORT + "/" + DB_NAME;
            return;
        }
    }
    public static Connection getConnection() throws Exception {
        if (instance == null){
            instance = new JDBCSingle();
            conexion = DriverManager.getConnection(url, SQL_USER, SQL_PASSWORD);
            return conexion;
        } else {
            return conexion;
        }
    }
    public static boolean closeConnection() throws SQLException {
        if(conexion == null || conexion.isClosed()){
            return false;
        } else {
            conexion.close();
            instance = null;
            return true;
        }
    }
    private static Properties getProperties(String ruta) throws IOException {
        File archivo = new File(ruta);
        boolean existe = archivo.exists();
        FileInputStream fis = new FileInputStream(archivo);
        Properties prop = new Properties();
        prop.load(fis);
        return prop;
    }
}





