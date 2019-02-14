package com.example.ottapp;

import android.app.Application;

public class App extends Application {
    private static App INSTANCE = null;

    public static App getApp() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
