package com.example.ottapp.data.source;

import com.example.ottapp.data.beans.Company;
import com.example.ottapp.data.beans.Flight;
import com.example.ottapp.data.beans.Trip;
import com.example.ottapp.data.beans.PopUpItem;
import com.example.ottapp.data.source.local.ILocalDataSource;
import com.example.ottapp.data.source.local.model.UIObject;
import com.example.ottapp.data.source.remote.IRemoteDataSource;
import com.example.ottapp.data.source.remote.model.CompanyList;
import com.example.ottapp.data.source.remote.model.FlightList;
import com.example.ottapp.data.source.remote.model.HotelList;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MainRepository implements IMainRepository {
    @Inject
    IRemoteDataSource mRemoteDataSource;
    @Inject
    ILocalDataSource mLocalDataSource;

    public MainRepository(IRemoteDataSource remoteDataSource, ILocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    @Override
    public Single<List<UIObject>> getLocalData() {
        return Single.create(emitter -> {
            List<UIObject> data = mLocalDataSource.getAll();
            if (data != null && !data.isEmpty()) {
                emitter.onSuccess(data);
            } else {
                emitter.onError(new Throwable("Cache is empty"));
            }
        });
    }

    @Override
    public Flowable<List<UIObject>> loadData() {
        return Flowable.zip(
                getHotelsObservable().subscribeOn(Schedulers.newThread()),
                getFlightsObservable().subscribeOn(Schedulers.newThread()),
                getCompaniesObservable().subscribeOn(Schedulers.newThread()),
                (trips, flightsMap, companiesMap) -> {

                    for (Trip trip : trips) {
                        int minPrice = Integer.MAX_VALUE;
                        UIObject entity = createEntity(trip);

                        List<Integer> arr = trip.getFlightsIds();
                        for (Integer key : arr) {
                            Flight flight = flightsMap.get(key);
                            if (flight != null) {
                                entity.getFlights().add(flight);
                                minPrice = flight.getPrice() <= minPrice ? flight.getPrice() : minPrice;
                            }
                        }

                        entity.setTotalMinPrice(trip.getPrice() + minPrice);
                        entity.setCompanies(companiesMap);

                        mLocalDataSource.write(entity);
                    }

                    return mLocalDataSource.getAll();
                });
    }

    @Override
    public Single<Integer> clearCache() {
        return Single.fromCallable(mLocalDataSource::clear);
    }

    @Override
    public Flowable<UIObject> getEntity(int id) {
        return Flowable.fromCallable(() -> mLocalDataSource.get(id))
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public Flowable<List<PopUpItem>> preparePopupData(UIObject entity) {
        Map<Integer, String> companies = entity.getCompanies();
        int hotelPrice = entity.getHotelPrice();

        return Flowable.just(entity)
                .map(UIObject::getFlights)
                .flatMapIterable(source -> source)
                .map(flight -> new PopUpItem(companies.get(flight.getCompanyId()), hotelPrice + flight.getPrice()))
                .toSortedList()
                .toFlowable();
    }

    private Flowable<List<Trip>> getHotelsObservable() {
        return mRemoteDataSource
                .fetchHotels()
                .map(HotelList::getHotels)
                .flatMapIterable(source -> source)
                .map(hotel -> {
                    Trip trip = new Trip();
                    trip.setId(hotel.getId());
                    trip.setName(hotel.getName());
                    trip.setPrice(hotel.getPrice());
                    trip.setFlightsIds(hotel.getFlights());

                    return trip;
                })
                .toList()
                .toFlowable();
    }

    private Flowable<Map<Integer, Flight>> getFlightsObservable() {
        return mRemoteDataSource
                .fetchFlights()
                .map(FlightList::getFlights)
                .flatMapIterable(source -> source)
                .toMap(Flight::getId, flight -> flight)
                .toFlowable();
    }

    private Flowable<Map<Integer, String>> getCompaniesObservable() {
        return mRemoteDataSource
                .fetchCompanies()
                .map(CompanyList::getCompanies)
                .flatMapIterable(source -> source)
                .toMap(Company::getId, Company::getName)
                .toFlowable();
    }

    private UIObject createEntity(final Trip trip) {
        UIObject entity = new UIObject();
        entity.setHotelId(trip.getId());
        entity.setHotelName(trip.getName());
        entity.setHotelPrice(trip.getPrice());

        return entity;
    }
}
