package com.example.ottapp.data.beans;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.example.ottapp.data.source.local.db.DataConverter;
import com.example.ottapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class HotelUI {
    private int id;
    private String name;
    private int price;
    private List<Integer> flightsIds = null;
    private List<Flight> flights = new ArrayList<>();
    private Integer totalMinPrice = -1;

    public HotelUI() {

    }

    public HotelUI(int id, String name, int price, List<Integer> flightsIds, Integer totalMinPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.flightsIds = flightsIds;
        this.totalMinPrice = totalMinPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Integer> getFlightsIds() {
        return flightsIds;
    }

    public void setFlightsIds(List<Integer> flightsIds) {
        this.flightsIds = flightsIds;
    }

    public Integer getTotalMinPrice() {
        return totalMinPrice;
    }

    public void setTotalMinPrice(Integer totalMinPrice) {
        this.totalMinPrice = totalMinPrice;
    }




    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
