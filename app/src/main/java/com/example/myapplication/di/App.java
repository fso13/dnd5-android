package com.example.myapplication.di;

import android.app.Application;

public class App extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        //needs to run once to generate it
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

    }


    public AppComponent getComponent() {
        return component;
    }

}