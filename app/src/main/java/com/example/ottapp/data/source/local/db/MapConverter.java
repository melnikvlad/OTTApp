package com.example.ottapp.data.source.local.db;

import android.arch.persistence.room.TypeConverter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;


public class MapConverter {
    @TypeConverter
    public String fromMap(Map<Integer, String> map) {
        if (map == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Map<Integer, String>>() {}.getType();

        return gson.toJson(map, type);
    }

    @TypeConverter
    public Map<Integer, String> toMap(String mapString) {
        if (mapString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Map<Integer, String>>() {}.getType();

        return gson.fromJson(mapString, type);
    }
}
