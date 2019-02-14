package com.example.ottapp.data.source;

import com.example.ottapp.App;
import com.example.ottapp.data.beans.Company;
import com.example.ottapp.data.beans.Flight;
import com.example.ottapp.data.beans.HotelUI;
import com.example.ottapp.data.source.local.ILocalDataSource;
import com.example.ottapp.data.source.local.LocalDataSource;
import com.example.ottapp.data.source.local.db.UITripEntity;
import com.example.ottapp.data.source.remote.IRemoteDataSource;
import com.example.ottapp.data.source.remote.RemoteDataSource;
import com.example.ottapp.data.source.remote.model.CompanyList;
import com.example.ottapp.data.source.remote.model.FlightList;
import com.example.ottapp.data.source.remote.model.HotelList;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class MainRepository implements IMainRepository {

    private static MainRepository INSTANCE = null;
    private final IRemoteDataSource mRemoteDataSource;
    private final ILocalDataSource mLocalDataSource;

    private MainRepository(IRemoteDataSource remoteDataSource, ILocalDataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static MainRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainRepository(new RemoteDataSource(), new LocalDataSource(App.getApp()));
        }

        return INSTANCE;
    }

    @Override
    public Single<List<UITripEntity>> getLocalData() {
        return Single.create(emitter -> {
            List<UITripEntity> data = mLocalDataSource.getAll();
            if (data != null && data.size() > 0) {
                emitter.onSuccess(data);
            } else {
                emitter.onError(new Throwable("Cache is empty"));
            }
        });
    }

    @Override
    public Flowable<List<UITripEntity>> loadData() {
        return Flowable.zip(
                getHotelsObservable().subscribeOn(Schedulers.newThread()),
                getFlightsObservable().subscribeOn(Schedulers.newThread()),
                getCompaniesObservable().subscribeOn(Schedulers.newThread()),
                (hotelList, flightsMap, companiesMap) -> {

                    for (HotelUI h : hotelList) {
                        int minPrice = Integer.MAX_VALUE;
                        UITripEntity entity = createEntity(h);

                        List<Integer> arr = h.getFlightsIds();
                        for (Integer key : arr) {
                            Flight flight = flightsMap.get(key);
                            if (flight != null) {
                                entity.getFlights().add(flight);
                                minPrice = flight.getPrice() <= minPrice ? flight.getPrice() : minPrice;
                            }
                        }

                        entity.setTotalMinPrice(h.getPrice() + minPrice);
                        mLocalDataSource.write(entity);
                    }

                    return mLocalDataSource.getAll();
                });
    }

    @Override
    public Single<UITripEntity> getEntity(UITripEntity item) {
        return Single.create(emitter -> {
            UITripEntity entity = mLocalDataSource.get(item.getHotelId());
            if (entity != null) {
                emitter.onSuccess(entity);
            } else {
                emitter.onError(new Throwable("No such item in cache"));
            }
        });
    }

    @Override
    public Observable<Boolean> clearCache() {
        return Observable.just(mLocalDataSource.clear());
    }

    private Flowable<List<HotelUI>> getHotelsObservable() {
        return mRemoteDataSource
                .fetchHotels()
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

    private UITripEntity createEntity(final HotelUI hotel) {
        UITripEntity entity = new UITripEntity();
        entity.setHotelId(hotel.getId());
        entity.setHotelName(hotel.getName());
        entity.setHotelPrice(hotel.getPrice());

        return entity;
    }
}
