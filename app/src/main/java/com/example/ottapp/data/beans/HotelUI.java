package com.example.ottapp.data.beans;

import java.util.ArrayList;
import java.util.List;

public class HotelUI {
    private int id;
    private String name;
    private int price;
    private List<Integer> flightsIds = null;
    private List<Flight> flights = null;
    private Integer totalMinPrice = -1;

    public HotelUI() {
        flights = new ArrayList<>();
    }

    public HotelUI(int id, String name, int price, List<Integer> flightsIds, List<Flight> flights, Integer totalMinPrice) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.flightsIds = flightsIds;
        this.flights = new ArrayList<>();
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

    @Override
    public String toString() {
        return "HotelUI{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", flightsIds=" + flightsIds +
                ", totalMinPrice=" + totalMinPrice +
                '}';
    }
}
