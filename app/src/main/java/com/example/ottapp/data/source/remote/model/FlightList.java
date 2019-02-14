package com.example.ottapp.data.source.remote.model;

import com.example.ottapp.data.beans.Flight;

import java.util.List;

public class FlightList {
    List<Flight> flights = null;

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
}
