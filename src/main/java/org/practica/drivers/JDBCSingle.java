package org.practica.drivers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCSingle {
        private static String routeProperties = "src"+ File.separator + "main" + File.separator + "resources" + File.separator + "dbconnection.properties";
        private static String HOST = "",DB_NAME = "",SQL_USER = "",SQL_PASSWORD = "";
        private static int PORT = 0;
        private static JDBCSingle instance;
        private static String url = "";
        private static Connection conexion;
        private Properties prop = null;

    private JDBCSingle() {
        try {
            prop = getProperties(routeProperties);
            HOST = prop.getProperty("host");
            DB_NAME = prop.getProperty("dbName");
            PORT = Integer.parseInt(prop.getProperty("port"));
            SQL_USER = prop.getProperty("sqluser");
            SQL_PASSWORD = prop.getProperty("sqlpassword");
            url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        } catch (IOException e) {
            System.out.println("Properties not found, trying with default properties");
            HOST = "localhost";
            DB_NAME = "publicationsdb";
            PORT = 3306;
            SQL_USER = "javaApp";
            SQL_PASSWORD = "javaApp";
            url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            return;
        }
    }
    public static Connection getConnection() throws SQLException {
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





