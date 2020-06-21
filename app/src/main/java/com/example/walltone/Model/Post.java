package com.example.walltone.Model;

import com.google.gson.annotations.SerializedName;

public class Post {

   public int id;

   public String name;

    @SerializedName("thumb_image")
   public String imageURL;

    //getter & setter
    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString(){

        return name;
    }

}
