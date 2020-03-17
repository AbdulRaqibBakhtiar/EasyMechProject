package com.example.easymechproject;

public class Mechanics {
    private String title;
    private String description;
    private String email;
    private int images;

    public Mechanics(String title, String description, String email, int images){
        this.title = title;
        this.description = description;
        this.images = images;
        this.email = email;
    }

    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getEmail(){
        return email;
    }
    public int getImages(){
        return images;
    }
}
