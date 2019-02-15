package com.example.ottapp.di.module;

import android.app.Application;

import com.example.ottapp.data.source.MainRepository;
import com.example.ottapp.data.source.local.LocalDataSource;
import com.example.ottapp.data.source.remote.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    MainRepository provideRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {

        return new MainRepository(remoteDataSource, localDataSource);
    }

    @Provides
    CompositeDisposable provideDisposable() {
        return new CompositeDisposable();
    }
}
