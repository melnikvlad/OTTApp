package com.example.ottapp.data.source;

import com.example.ottapp.data.beans.PopUpItem;
import com.example.ottapp.data.source.local.model.UIObject;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface IMainRepository {

    Flowable<List<UIObject>> loadData();

    Single<List<UIObject>> getLocalData();

    Single<Integer> clearCache();

    Flowable<List<PopUpItem>> preparePopupData(UIObject item);

    Flowable<UIObject> getEntity(int id);

}
