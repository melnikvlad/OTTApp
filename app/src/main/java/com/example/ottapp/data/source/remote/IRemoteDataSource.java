package com.example.ottapp.data.source.remote;

import com.example.ottapp.data.source.remote.model.CompanyList;
import com.example.ottapp.data.source.remote.model.FlightList;
import com.example.ottapp.data.source.remote.model.HotelList;

import io.reactivex.Flowable;

public interface IRemoteDataSource {

    Flowable<HotelList> fetchHotels();

    Flowable<FlightList> fetchFlights();

    Flowable<CompanyList> fetchCompanies();

}
