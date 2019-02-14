package com.example.ottapp.data.source.remote.model;

import com.example.ottapp.data.beans.Hotel;

import java.util.List;

public class HotelList {
    List<Hotel> hotels = null;

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }
}
