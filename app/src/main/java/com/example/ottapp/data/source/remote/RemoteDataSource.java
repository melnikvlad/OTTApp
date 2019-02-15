package com.example.ottapp.data.source.remote;

import com.example.ottapp.data.source.remote.network.Api;
import com.example.ottapp.data.source.remote.network.ApiClient;
import com.example.ottapp.data.source.remote.model.CompanyList;
import com.example.ottapp.data.source.remote.model.FlightList;
import com.example.ottapp.data.source.remote.model.HotelList;

import io.reactivex.Flowable;

public class RemoteDataSource implements IRemoteDataSource {

    private Api mService;

    public RemoteDataSource() {
        mService = ApiClient.getInstance().api();
    }

    @Override
    public Flowable<HotelList> fetchHotels() {
        return mService.hotels();
    }

    @Override
    public Flowable<FlightList> fetchFlights() {
        return mService.flights();
    }

    @Override
    public Flowable<CompanyList> fetchCompanies() {
        return mService.companies();
    }
}
