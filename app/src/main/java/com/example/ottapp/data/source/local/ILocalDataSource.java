package com.example.ottapp.data.source.local;

import com.example.ottapp.data.source.local.model.UIObject;

import java.util.List;

public interface ILocalDataSource {
    void write(UIObject entity);

    List<UIObject> getAll();

    UIObject get(int hotelId);

    Integer clear();

}
