package com.example.ottapp.data.beans;

import android.support.annotation.NonNull;

public class PopUpItem implements Comparable<PopUpItem> {
    private String companyName;
    private Integer sumPrice;

    public PopUpItem(String companyName, Integer sumPrice) {
        this.companyName = companyName;
        this.sumPrice = sumPrice;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getSumPrice() {
        return sumPrice;
    }


    @Override
    public int compareTo(@NonNull PopUpItem popUpItem) {
        if (getSumPrice() == null || popUpItem.getSumPrice() == null) {
            return 0;
        }

        return getSumPrice().compareTo(popUpItem.getSumPrice());
    }
}
