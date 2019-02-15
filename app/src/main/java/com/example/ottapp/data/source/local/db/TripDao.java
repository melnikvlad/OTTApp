package com.example.ottapp.data.source.local.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.ottapp.data.source.local.model.UIObject;
import com.example.ottapp.utils.Constants;

import java.util.List;

@Dao
public interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UIObject entity);

    @Query("SELECT * FROM " + Constants.TABLE_NAME)
    List<UIObject> getAll();

    @Query("SELECT * FROM " + Constants.TABLE_NAME + " WHERE hotelId = :id")
    UIObject get(int id);

    @Query("DELETE FROM " + Constants.TABLE_NAME)
    int clear();
}
