package com.example.ottapp.di.module;

import android.app.Application;

import com.example.ottapp.data.source.remote.RemoteDataSource;
import com.example.ottapp.data.source.remote.network.Api;
import com.example.ottapp.data.source.remote.network.ApiClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RemoteDataSourceModule {
    @Provides
    @Singleton
    RemoteDataSource provideRemoteSource(Application application, Api api, ApiClient client) {
        return new RemoteDataSource(api, client);
    }

    @Provides
    @Singleton
    ApiClient provideApiClient() {
        return new ApiClient();
    }

    @Provides
    @Singleton
    Api provideApi(ApiClient client) {
        return client.api();
    }
}
