package com.example.mytestapp;

import android.util.Log;

import androidx.room.*;

import java.util.Arrays;
import java.util.List;

@Fts4
@Entity(tableName = "closet")
public class Item {
    @Ignore
    List<String> typeL = Arrays.asList("short-sleeved shirt", "long-sleeved shirt","tank-top","shorts","pants","skirt");
    @Ignore
    List<String> colorL = Arrays.asList("red","orange","yellow","green","blue","purple","black","white");

    @Ignore
    String[] redMatch = {"black","white"};
    @Ignore
    String[] orangeMatch = {"black","white"};
    @Ignore
    String[] yellowMatch = {"black","white"};
    @Ignore
    String[] greenMatch = {"black","white"};
    @Ignore
    String[] blueMatch = {"black","white"};
    @Ignore
    String[] purpleMatch = {"black","white"};
    @Ignore
    String[] blackMatch = {"red","orange","yellow","green","blue","purple","white"};
    @Ignore
    String[] whiteMatch = {"red","orange","yellow","green","blue","purple","black"};
    @Ignore
    String[][] matches = {redMatch, orangeMatch, yellowMatch, greenMatch, blueMatch, purpleMatch, blackMatch, whiteMatch};

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    int id;
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

    public String getPath() {
        return itemPath;
    }

}

