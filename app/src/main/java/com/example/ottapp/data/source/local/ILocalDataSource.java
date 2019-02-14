package com.example.ottapp.data.source.local;

import com.example.ottapp.data.source.local.db.UITripEntity;

import java.util.List;

public interface ILocalDataSource {
    void write(UITripEntity entity);

    List<UITripEntity> getAll();

    UITripEntity get(int hotelId);

    Integer clear();

}
