package com.example.ottapp.data.source;

import com.example.ottapp.data.beans.PopUpItem;
import com.example.ottapp.data.source.local.model.UITripEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IMainRepository {

    Flowable<List<UITripEntity>> loadData();

    Single<List<UITripEntity>> getLocalData();

    Single<Integer> clearCache();

    Flowable<List<PopUpItem>> preparePopupData(UITripEntity item);

    Flowable<UITripEntity> getEntity(int id);

}
