package com.example.ottapp;

import com.example.ottapp.data.source.remote.model.CompanyList;
import com.example.ottapp.data.source.remote.model.FlightList;
import com.example.ottapp.data.source.remote.model.HotelList;

import io.reactivex.Flowable;
import retrofit2.http.GET;

public interface Api {

    @GET("zqxvw")
    Flowable<FlightList> flights();

    @GET("12q3ws")
    Flowable<HotelList> hotels();

    @GET("8d024")
    Flowable<CompanyList> companies();
}
