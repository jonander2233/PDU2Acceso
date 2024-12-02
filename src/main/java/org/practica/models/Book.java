package org.practica.models;

import org.bson.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {
    private final int id;
    private final String author;
    private final String title;
    private final String genre;
    private final float price;
    private Date publishDate;
    private final String description;
    private String dateFormat = "yyyy-MM-dd";
    private SimpleDateFormat format = null;
    private String publishDateStr="";

    public Book(int id, String description, Date publishDate, float price, String genre, String title, String author) {
        this.description = description;
        this.price = price;
        this.genre = genre;
        this.title = title;
        this.author = author;
        this.id = id;
    }
    public Book(int id, String description, String publishDateStr, float price, String genre, String title, String author, String dateFormat) {
        this.description = description;
        this.publishDateStr = publishDateStr;
        this.price = price;
        this.genre = genre;
        this.title = title;
        this.author = author;
        this.id = id;
        this.dateFormat = dateFormat;
        this.format = new SimpleDateFormat(dateFormat);
        try {
            publishDate = this.format.parse(publishDateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Book( int id, String description, String publishDateStr, float price, String genre, String title, String author) {
        this.description = description;
        this.publishDateStr = publishDateStr;
        this.price = price;
        this.genre = genre;
        this.title = title;
        this.author = author;
        this.id = id;
        this.format = new SimpleDateFormat(dateFormat);
        try {
            publishDate = this.format.parse(publishDateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPublishDateStr() {
        return publishDateStr;
    }

    public String getDescription() {
        return description;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public float getPrice() {
        return price;
    }

    public String getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public String getDateFormat() {
        return dateFormat;
    }
    public Document getDocument(){
        return new Document("bookId",this.id).append("bookAuthor",this.author).append("bookTitle",this.title).append("bookGenre",this.genre).append("bookPrice",this.price).append("bookPublish_date",this.publishDate).append("bookDescription",this.description);
    }
}
