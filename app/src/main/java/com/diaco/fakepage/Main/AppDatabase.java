package com.diaco.fakepage.Main;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ImageData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ImageDao imageDao();

    private static AppDatabase instance;
    public static AppDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context, AppDatabase.class, "Instagram").build();

        return instance ;
    }
}
