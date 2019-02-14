package com.example.ottapp.data.source;

import com.example.ottapp.data.source.local.db.UITripEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface IMainRepository {

    Flowable<List<UITripEntity>> loadData();

    Single<List<UITripEntity>> getLocalData();

    Single<UITripEntity> getEntity(UITripEntity item);

    Observable<Integer> clearCache();
}
