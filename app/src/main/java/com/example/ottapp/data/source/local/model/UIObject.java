package com.example.ottapp.data.source.local.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.ottapp.data.beans.Flight;
import com.example.ottapp.data.source.MainRepository;
import com.example.ottapp.data.source.local.db.DataConverter;
import com.example.ottapp.data.source.local.db.MapConverter;
import com.example.ottapp.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = Constants.TABLE_NAME)
public class UIObject {
    @PrimaryKey(autoGenerate = true)
    private int entityId;

    @ColumnInfo(name = "hotelId")
    private int hotelId;

    @ColumnInfo(name = "hotelName")
    private String hotelName;

    @ColumnInfo(name = "hotelPrice")
    private int hotelPrice;

    @ColumnInfo(name = "flights")
    @TypeConverters(DataConverter.class)
    private List<Flight> flights;
    @ColumnInfo(name = "companies")
    @TypeConverters(MapConverter.class)
    private Map<Integer, String> companies;

    @ColumnInfo(name = "totalMinPrice")
    private Integer totalMinPrice;

    public UIObject() {
        flights = new ArrayList<>();
        companies = new HashMap<>();
    }

    public UIObject(int entityId, int hotelId, String hotelName, int hotelPrice, List<Flight> flights, Integer totalMinPrice) {
        this.entityId = entityId;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.hotelPrice = hotelPrice;
        this.flights = flights;
        this.totalMinPrice = totalMinPrice;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(int hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public Integer getTotalMinPrice() {
        return totalMinPrice;
    }

    public void setTotalMinPrice(Integer totalMinPrice) {
        this.totalMinPrice = totalMinPrice;
    }

    public Map<Integer, String> getCompanies() {
        return companies;
    }

    public void setCompanies(Map<Integer, String> companies) {
        this.companies = companies;
    }
}
