package com.example.blogapp.Classes;

public class Blog {
    String userid ;
    String title ;
    String image ;
    String describtion ;
    String time ;

    public Blog(){}

    public Blog(String userid, String title, String image, String describtion, String time) {
        this.userid = userid;
        this.title = title;
        this.image = image;
        this.describtion = describtion;
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
