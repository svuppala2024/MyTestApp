package com.example.mytestapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/* The main prupise of this class is to create the App Database */

@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao(); /* Item Dao of the App Database */

    static final String DATABASE_NAME = "database_name"; /* Name of the database */

    private static AppDatabase Instance; /* Instance of the database */


    /* A constructor of the AppDatabase class which takes into account if an object has already been initialized*/
    public static AppDatabase getInstance(Context context) {
        if (Instance == null) {
            synchronized (AppDatabase.class) {
                if (Instance == null) {
                    Instance =
                            Room.databaseBuilder(context.getApplicationContext(),
                                            AppDatabase.class, DATABASE_NAME)
                                    .allowMainThreadQueries().build();
                }
            }
        }
        return Instance;
    }
}