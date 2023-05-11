package com.example.mytestapp;

import androidx.room.*;

@Fts4
@Entity(tableName = "closet")
public class Item {
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    public static int id;
    @ColumnInfo(name = "item_path")
    public String itemPath;
    @ColumnInfo(name = "clothing_type")
    public String clothingType;
    @ColumnInfo(name = "is_winter")
    public boolean isWinter;
    @ColumnInfo(name = "is_spring")
    public boolean isSpring;
    @ColumnInfo(name = "is_fall")
    public boolean isFall;
    @ColumnInfo(name = "is_summer")
    public boolean isSummer;
    @ColumnInfo(name = "clothing_color")
    public String color;
    @ColumnInfo(name = "is_patterned")
    public boolean isPatterned;

    public Item(String type, String color, boolean w, boolean sp, boolean f, boolean su, boolean patterned) {
        id++;
        clothingType = type;
        this.color = color;
        isWinter = w;
        isSpring = sp;
        isFall = f;
        isSummer = su;
        isPatterned = patterned;
    }

    public void setPath(String path) {
        itemPath = path;
    }

    public Item() {

    }

    public int getId() {
        return id;
    }

}

