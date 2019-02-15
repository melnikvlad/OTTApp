package com.example.ottapp.data.source.remote.network;

import com.example.ottapp.utils.Constants;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static ApiClient INSTANCE = null;
    private static Retrofit retrofit = null;

    private ApiClient() {
    }

    public static ApiClient getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApiClient();
        }

        return INSTANCE;
    }

    public Api api() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit.create(Api.class);
    }
}
