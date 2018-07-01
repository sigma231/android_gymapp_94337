package com.sageart.spartan.spartan;

import com.google.android.gms.maps.model.LatLng;

public class gym {
    private String name, image_source, description;
    private int rating;
    private String location;

    public gym(){

    }
    public gym(String name, String image_source, String location, int rating, String description){
        this.name = name;
        this.image_source = image_source;
        this.location = location;
        this.rating = rating;
        this.description = description;

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;

    }
    public String getImage_source(){
        return image_source;
    }
    public void setImage_source(String image_source){
        this.image_source = image_source;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public String getLocation() {
        return location;
    }
}
