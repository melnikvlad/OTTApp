package com.example.ottapp.data.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hotel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("flights")
    @Expose
    private List<Integer> flights = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;

    public Hotel(Integer id, List<Integer> flights, String name, Integer price) {
        this.id = id;
        this.flights = flights;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getFlights() {
        return flights;
    }

    public void setFlights(List<Integer> flights) {
        this.flights = flights;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", flights=" + flights +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
