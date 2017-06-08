package fr.ihm.polytech.com.ihmtboth.model;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

/**
 * Created by user on 26/04/2017.
 */

public class Article implements Serializable {
    private final float price;
    private int id;
    private String title;
    private String content;
    private Date date;
    private TypeE type;
    private Category category;
    private URL url;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public Category getCategory() {
        return category;
    }


    public URL getUrl() {
        return url;
    }

    public Article(int id, String title, String content, TypeE type, Category category, URL url, Date date, float price) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.category = category;
        this.url = url;
        this.date = date;
        this.price = price;
    }

    public String toString(){
        return title;
    }

    public float getPrice() {
        return price;
    }

    public TypeE getType() {
        return type;
    }
}
