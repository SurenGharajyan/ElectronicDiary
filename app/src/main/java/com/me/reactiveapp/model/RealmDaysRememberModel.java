package com.me.reactiveapp.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class RealmDaysRememberModel extends RealmObject {
    @Required
    private String title;
    @Required
    private String day;
    @Required
    private String withImage;
    @Required
    private String user;

//    public RealmDaysRememberModel(String title, String day, String withImage) {
//        this.title = title;
//        this.day = day;
//        this.withImage = withImage;
//    }


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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
