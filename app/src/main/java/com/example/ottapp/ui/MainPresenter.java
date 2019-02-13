package com.example.ottapp.ui;

import android.util.Log;

import com.example.ottapp.ApiClient;
import com.example.ottapp.data.CompanyList;
import com.example.ottapp.data.FlightList;
import com.example.ottapp.data.HotelList;
import com.example.ottapp.data.beans.Company;
import com.example.ottapp.data.beans.Flight;
import com.example.ottapp.data.beans.HotelUI;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    MainPresenter(MainContract.View view) {
        if (view != null) {
            mView = view;
            mView.setPresenter(this);
            mCompositeDisposable = new CompositeDisposable();
        }
    }

    @Override
    public void subscribe() {
        mView.renderLoadingState();
        load();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void refresh() {
        mView.renderRefreshingState();
    }

    @Override
    public void load() {

        Flowable<List<HotelUI>> hotelListObservable = ApiClient.getInstance().api().hotels()
                .map(HotelList::getHotels)
                .flatMapIterable(source -> source)
                .map(hotel -> {
                    HotelUI hotelUI = new HotelUI();
                    hotelUI.setId(hotel.getId());
                    hotelUI.setName(hotel.getName());
                    hotelUI.setPrice(hotel.getPrice());
                    hotelUI.setFlightsIds(hotel.getFlights());

                    return hotelUI;
                })
                .toList()
                .toFlowable();

        Flowable<Map<Integer, Flight>> flightListObservable = ApiClient.getInstance().api().flights()
                .map(FlightList::getFlights)
                .flatMapIterable(source -> source)
                .toMap(Flight::getId, flight -> flight)
                .toFlowable();


        Flowable<Map<Integer, String>> companyListObservable = ApiClient.getInstance().api().companies()
                .map(CompanyList::getCompanies)
                .flatMapIterable(source -> source)
                .toMap(Company::getId, Company::getName)
                .toFlowable();

        Disposable obj = Flowable.zip(
                hotelListObservable.subscribeOn(Schedulers.newThread()),
                flightListObservable.subscribeOn(Schedulers.newThread()),
                companyListObservable.subscribeOn(Schedulers.newThread()),
                (hotelList, flightsMap, companiesMap) -> {
                    for (HotelUI h : hotelList) {
                        int minPrice = Integer.MAX_VALUE;

                        List<Integer> arr = h.getFlightsIds();
                        for (Integer key : arr) {
                            Flight flight = flightsMap.get(key);
                            if (flight != null) {
                                h.getFlights().add(flight);
                                minPrice = flight.getPrice() <= minPrice ? flight.getPrice() : minPrice;
                            }
                        }

                        h.setTotalMinPrice(h.getPrice() + minPrice);
                    }

                    return hotelList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        uiList -> mView.renderDataState(uiList)
                );

        mCompositeDisposable.add(obj);
    }

    @Override
    public void click(int pos, HotelUI item) {
        for (Flight f : item.getFlights()) {
            Log.d("TAG", f.toString());
        }

    }

    @Override
    public void save() {

    }

    @Override
    public void clearCache() {

    }
}
