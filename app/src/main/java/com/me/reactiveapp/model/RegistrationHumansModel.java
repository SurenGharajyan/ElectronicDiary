package com.me.reactiveapp.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class RegistrationHumansModel extends RealmObject {

    @Required
    private String loginUser;
    @Required
    private String passwordUser;
    @Required
    private String userNameUser;


    public String getUserNameUser() {
        return userNameUser;
    }

    public void setUserNameUser(String userNameUser) {
        this.userNameUser = userNameUser;
    }


    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(final String loginUser) {
        this.loginUser = loginUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setPasswordUser(final String passwordUser) {
        this.passwordUser = passwordUser;
    }




}
