package com.example.mytestapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    /* Gets all Items of clothing from the database */
    @Query("SELECT closet.* FROM Closet")
    Item[] getAllClothes();

    /* Gets all paths of the items of clothing from the database */
    @Query("SELECT item_path FROM CLOSET")
    List<String> loadAllPaths();

    /* Gets all the types of the items of clothing from the database */
    @Query("SELECT clothing_type FROM CLOSET")
    List<String> loadAllTypes();

    /* Gets all the ids of the items of clothing from the database */
    @Query("SELECT rowid FROM CLOSET")
    List<Integer> loadAllIDs();

    /* Inserts all items of clothing into the database */
    @Insert
    void insertAll(Item... Closets);

}