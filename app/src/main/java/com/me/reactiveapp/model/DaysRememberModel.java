package com.me.reactiveapp.model;

public class DaysRememberModel {
    private String title;
    private String day;
    private String withImage;

    public DaysRememberModel(String title, String day, String withImage) {
        this.title = title;
        this.day = day;
        this.withImage = withImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDay() {
        return day;
    }

    public String getWithImage() {
        return withImage;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setWithImage(String withImage) {
        this.withImage = withImage;
    }
}
