package com.example.ottapp.data.source.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.ottapp.data.beans.HotelUI;
import com.example.ottapp.utils.Constants;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao
public interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UITripEntity entity);

    @Query("SELECT * FROM " + Constants.TABLE_NAME)
    List<UITripEntity> getAll();

    @Query("SELECT * FROM " + Constants.TABLE_NAME + " WHERE hotelId = :id")
    UITripEntity get(int id);

    @Query("DELETE FROM " + Constants.TABLE_NAME)
    boolean clear();
}
