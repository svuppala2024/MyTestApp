package com.example.mytestapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();

    static final String DATABASE_NAME = "database_name";

    private static AppDatabase Instance;

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