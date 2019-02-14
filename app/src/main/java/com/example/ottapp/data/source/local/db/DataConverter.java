package com.example.ottapp.data.source.local.db;

import android.arch.persistence.room.TypeConverter;

import com.example.ottapp.data.beans.Flight;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DataConverter {

    @TypeConverter
    public String fromFlightList(List<Flight> flights) {
        if (flights == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Flight>>() {}.getType();

        return gson.toJson(flights, type);
    }

    @TypeConverter
    public List<Flight> toFlightsList(String flightsString) {
        if (flightsString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Flight>>() {}.getType();

        return gson.fromJson(flightsString, type);
    }
}
