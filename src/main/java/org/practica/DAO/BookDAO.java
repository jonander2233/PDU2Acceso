package org.practica.DAO;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.practica.drivers.MongoSingle;
import org.practica.models.Book;

import java.util.ArrayList;
import java.util.Date;

public class BookDAO {
    private MongoSingle ms;
    private MongoDatabase md;
    private static BookDAO instance;
    private MongoClient mc;
    MongoCollection<Document> col;

    private BookDAO() {
        ms = MongoSingle.getInstance();
        md = ms.getDb();
        col = md.getCollection("books");
    }
    public static BookDAO getInstance(){
        if(instance == null){
            instance = new BookDAO();
        }
        return instance;
    }

    public ArrayList<Book> listByAuthor(String author){
        ArrayList<Book> books = new ArrayList<>();
        Document query = new Document("bookAuthor", author);
        for(Document doc : col.find(query)){
            int id = doc.getInteger("bookId");
            String desc = doc.getString("bookDescription");
            float price = Float.parseFloat(doc.getDouble("bookPrice").toString());
            String genre = doc.getString("bookGenre");
            String title = doc.getString("bookTitle");
            String author1 = doc.getString("bookAuthor");
            Date publishDate = doc.getDate("bookPublish_date");
            books.add(new Book(id,desc,publishDate,price,genre,title,author1));
        }
        if(books.isEmpty()){
            return null;
        }
        return books;
    }



    public ArrayList<Book> listAllBooks(){
        ArrayList<Book> books = new ArrayList<>();
        for(Document doc : col.find()){
            int id = doc.getInteger("bookId");
            String desc = doc.getString("bookDescription");
            float price = Float.parseFloat(doc.getDouble("bookPrice").toString());
            String genre = doc.getString("bookGenre");
            String title = doc.getString("bookTitle");
            String author = doc.getString("bookAuthor");
            Date publishDate = doc.getDate("bookPublish_date");
            books.add(new Book(id,desc,publishDate,price,genre,title,author));
        }
        if(books.isEmpty()){
            return null;
        }
        return books;
    }
    public int removeBookByAuthor(String author){
        Document query = new Document("bookAuthor", author);
        DeleteResult result = col.deleteMany(query);
        return (int)result.getDeletedCount();
    }
    public boolean removeBookByID(int id){
        Document query = new Document("bookId", id);
        Document book = col.find(query).first();
        if(book == null){
            return false;
        }
        col.deleteOne(query);
        return true;
    }
    public boolean addBook(Book book){
        Book doc = searchById(book.getId());
        if(doc == null){
            col.insertOne(book.getDocument());
            return true;
        }
        return false;
    }
    public Book searchById(int id){
        Document query = new Document("bookId", id);
        Document book = col.find(query).first();
        if(book == null){
            return null;
        }
        int id1 = book.getInteger("bookId");
        String desc = book.getString("bookDescription");
        float price = Float.parseFloat(book.getDouble("bookPrice").toString());
        String genre = book.getString("bookGenre");
        String title = book.getString("bookTitle");
        String author = book.getString("bookAuthor");
        Date publishDate = book.getDate("bookPublish_date");
        return new Book(id1,desc,publishDate,price,genre,title,author);
    }

}
