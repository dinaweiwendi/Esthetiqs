package com.example.final_project.Model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by sifang on 4/23/19.
 */

public class Event implements Serializable {

    public String id;
    public String name;
    public long time;
    public String location;
    public String payment;
    public String genre;
    public String description;
    public String imagePath;

    public Event(){}

    public Event(String name, long time, String location, String payment,
                 String genre, String description){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.time = time;
        this.location = location;
        this.payment = payment;
        this.genre = genre;
        this.description = description;
        this.imagePath = null;
    }

    public Event(String name, long time, String location, String payment,
                 String genre, String description, String imagePath){
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.time = time;
        this.location = location;
        this.payment = payment;
        this.genre = genre;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String toString(){
        return name + " " + time + " " + location + " " + payment + " " + genre;
    }

}
