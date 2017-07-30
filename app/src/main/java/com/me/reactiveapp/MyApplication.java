package com.me.reactiveapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;



public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .deleteRealmIfMigrationNeeded()
                .name("appname")
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
