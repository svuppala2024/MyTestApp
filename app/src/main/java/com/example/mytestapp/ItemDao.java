package com.example.mytestapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT closet.* FROM Closet")
    Item[] getAllClothes();

//    @Query("SELECT * FROM Closet")
//    List<Item> allClothes();

    @Query("SELECT closet.* FROM Closet WHERE clothing_color IN (:closetColors)")
    List<Item> loadAllByColor(int[] closetColors);

    @Query("SELECT closet.* FROM Closet WHERE is_winter LIKE :winter AND " +
            "is_fall LIKE :fall AND" + " is_summer LIKE :summer AND" + " is_spring LIKE :spring")
    Item findBySeason(boolean winter, boolean spring, boolean fall, boolean summer);

    @Query("SELECT closet.* FROM Closet WHERE clothing_type IN (:closetTypes)")
    List<Item> loadAllByType(int[] closetTypes);

    @Query("SELECT closet.* FROM Closet WHERE is_patterned IN (:arePatterned)")
    List<Item> loadAllByType(boolean[] arePatterned);

    @Query("SELECT item_path FROM CLOSET")
    List<String> loadAllPaths();

    @Query("SELECT clothing_type FROM CLOSET")
    List<String> loadAllTypes();

    @Query("SELECT rowid FROM CLOSET")
    List<Integer> loadAllIDs();

    @Query("SELECT closet.* FROM Closet WHERE item_path = :itemPath")
    Item getItemFromPath(String itemPath);


    @Insert
    void insertAll(Item... Closets);





    @Delete
    void delete(Item Closet);
}