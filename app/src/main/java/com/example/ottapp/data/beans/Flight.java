package com.example.ottapp.data.beans;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Flight implements Comparable<Flight> {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("price")
    @Expose
    private Integer price = null;

    public Flight(Integer id, Integer companyId, Integer price) {
        this.id = id;
        this.companyId = companyId;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(@NonNull Flight flight) {
        if (getPrice() == null || flight.getPrice() == null) {
            return 0;
        }

        return getPrice().compareTo(flight.getPrice());
    }
}
