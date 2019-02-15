package com.example.ottapp.data.source.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.ottapp.data.source.local.model.UIObject;
import com.example.ottapp.utils.Constants;

@Database(entities = {UIObject.class}, version = 1)
public abstract class TripDB extends RoomDatabase {

    private static TripDB sInstance;
    public abstract TripDao getDao();

    public static TripDB getDB(Context context) {
        if (sInstance == null) {
            synchronized (TripDao.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            TripDB.class,
                            Constants.DB_NAME).build();
                }
            }
        }
        return sInstance;
    }

}
