package com.example.ottapp.data.source.local;

import android.app.Application;

import com.example.ottapp.data.source.local.db.TripDB;
import com.example.ottapp.data.source.local.model.UITripEntity;

import java.util.List;

public class LocalDataSource implements ILocalDataSource {

    private static TripDB sDB;

    public LocalDataSource(Application context) {
        sDB = TripDB.getDB(context);
    }

    @Override
    public void write(final UITripEntity entity) {
        sDB.getDao().insert(entity);
    }

    @Override
    public List<UITripEntity> getAll() {
        return sDB.getDao().getAll();
    }

    @Override
    public UITripEntity get(int hotelId) {
        return sDB.getDao().get(hotelId);
    }

    @Override
    public Integer clear() {
        return sDB.getDao().clear();
    }
}
