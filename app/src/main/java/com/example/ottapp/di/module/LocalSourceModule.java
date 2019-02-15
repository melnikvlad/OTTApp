package com.example.ottapp.di.module;

import android.app.Application;

import com.example.ottapp.data.source.local.LocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalSourceModule {

    @Provides
    @Singleton
    LocalDataSource provideLocalDataSource(Application application) {
        return new LocalDataSource(application);
    }
}
