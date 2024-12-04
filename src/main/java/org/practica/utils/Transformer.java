package org.practica.utils;

import java.util.Date;

public class Transformer {
    public static java.sql.Date javaDateToSQLDate(Date date){
        return new java.sql.Date(date.getTime());
    }
}
