package com.example.mytestapp;

import android.util.Log;

import androidx.room.*;

import java.util.Arrays;
import java.util.List;

/* This class is the data structure used for all the items of clothing */
@Entity(tableName = "closet")
public class Item {
    @Ignore
    List<String> typeL = Arrays.asList("short-sleeved shirt", "long-sleeved shirt","tank-top","shorts","pants","skirt"); /* List of all types of clothing */
    @Ignore
    List<String> colorL = Arrays.asList("red","orange","yellow","green","blue","purple","black","white"); /* List of all colors for items of clothing */

    @Ignore
    String[] redMatch = {"black","white"}; /* String array of colors that match with red */
    @Ignore
    String[] orangeMatch = {"black","white"}; /* String array of colors that match with orange */
    @Ignore
    String[] yellowMatch = {"black","white"}; /* String array of colors that match with yellow */
    @Ignore
    String[] greenMatch = {"black","white"}; /* String array of colors that match with green */
    @Ignore
    String[] blueMatch = {"black","white"}; /* String array of colors that match with blue */
    @Ignore
    String[] purpleMatch = {"black","white"}; /* String array of colors that match with purple */
    @Ignore
    String[] blackMatch = {"red","orange","yellow","green","blue","purple","white"}; /* String array of colors that match with black*/
    @Ignore
    String[] whiteMatch = {"red","orange","yellow","green","blue","purple","black"}; /* String array of colors that match with white */
    @Ignore
    String[][] matches = {redMatch, orangeMatch, yellowMatch, greenMatch, blueMatch, purpleMatch, blackMatch, whiteMatch}; /* 2D String array of color matches*/

    @PrimaryKey(autoGenerate = true)
    public int id; /* Unique integer id for each item of clothing */
    @ColumnInfo(name = "item_path")
    public String itemPath; /* Directory of item of clothing */
    @ColumnInfo(name = "clothing_type")
    public String clothingType; /* The clothing type of the piece of clothing */
    @ColumnInfo(name = "is_winter")
    public boolean isWinter; /* Whether the item of clothing can be worn in winter */
    @ColumnInfo(name = "is_spring")
    public boolean isSpring; /* Whether the item of clothing can be worn in spring */
    @ColumnInfo(name = "is_fall")
    public boolean isFall; /* Whether the item of clothing can be worn in fall */
    @ColumnInfo(name = "is_summer")
    public boolean isSummer; /* Whether the item of clothing can be worn in summer */
    @ColumnInfo(name = "clothing_color")
    public String color; /* The color of the item of clothing */
    @ColumnInfo(name = "is_patterned")
    public boolean isPatterned; /* Whether the item of clothing has a pattern (stripes, words, design etc. ) */

    /* Constructor for Item object with above fields */
    public Item(String type, String color, boolean w, boolean sp, boolean f, boolean su, boolean patterned) {
        clothingType = type;
        this.color = color;
        isWinter = w;
        isSpring = sp;
        isFall = f;
        isSummer = su;
        isPatterned = patterned;
    }

    /* Sets the directory for an item of clothing */
    public void setPath(String path) {
        itemPath = path;
    }

    /* Empty constructor for Item object */
    public Item() {

    }

    /* Returns the Id of the item of clothing */
    public int getId() {
        return id;
    }

    /**
     * @return the type of clothing the garment is
     */
    public String Type() {
        return clothingType;
    }

    /**
     * @return the color the garment is
     */
    public String Color() {
        return color;
    }

    /**
     * @return the index of the color of the garment in the color list
     */
    public int colorIndex() {
        int index = colorL.indexOf(color);
        Log.d("Suhruth", "colorIndex: " + color);
        return index;
    }

    /**
     * Gets all the colors that match with one single color
     * @param index - the index of the color of the garment in the color list
     * @return all the colors that match with the color of the garment
     */
    public String[] getColorMatch(int index) {
        return matches[index];
    }

    /**
     * @return whether the garment has a pattern or not
     */
    public boolean Pattern() {
        return isPatterned;
    }

    /**
     * @return whether the garment is suitable for winter or not
     */
    public boolean W() {
        return isWinter;
    }

    /**
     * @return whether the garment is suitable for spring or not
     */
    public boolean Sp() {
        return isSpring;
    }

    /**
     * @return whether the garment is suitable for summer or not
     */
    public boolean Su() {
        return isSummer;
    }

    /**
     * @return whether the garment is suitable for fall or not
     */
    public boolean F() {
        return isFall;
    }

    /**
     * Displays all returns
     */
    @Override
    public String toString() {
        return Type() +"\n"+ Color() +"\n"+ Pattern() +"\n"+ W() +"\n"+ Sp() +"\n"+ Su() +"\n"+ F()+"\n";
    }


    /** @return the directory of the item of clothing */
    public String getPath() {
        return itemPath;
    }

    /* sets the id of the item of clothing */
    public void setId(int id) {
        this.id = id;
    }

}

