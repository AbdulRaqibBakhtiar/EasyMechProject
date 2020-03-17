package com.example.easymechproject;

public class FAQ_Viewer {
    private String Title;
    private String content;

    public FAQ_Viewer(String title, String content) {
        Title = title;
        this.content = content;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return content;
    }
}
