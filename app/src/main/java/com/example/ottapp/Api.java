package com.example.ottapp;

import com.example.ottapp.data.CompanyList;
import com.example.ottapp.data.FlightList;
import com.example.ottapp.data.HotelList;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface Api {

    @GET("zqxvw")
    Flowable<FlightList> flights();

    @GET("12q3ws")
    Flowable<HotelList> hotels();

    @GET("8d024")
    Flowable<CompanyList> companies();
}
