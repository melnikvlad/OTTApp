package com.example.ottapp;

import android.app.Application;

import com.example.ottapp.di.component.AppComponent;
import com.example.ottapp.di.component.DaggerAppComponent;
import com.example.ottapp.di.module.AppModule;
import com.example.ottapp.di.module.LocalSourceModule;
import com.example.ottapp.di.module.RemoteDataSourceModule;

public class App extends Application {
    private static App INSTANCE = null;
    private AppComponent appComponent;

    public static App getApp() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(INSTANCE))
                .localSourceModule(new LocalSourceModule())
                .remoteDataSourceModule(new RemoteDataSourceModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
