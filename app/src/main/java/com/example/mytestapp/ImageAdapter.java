package com.example.mytestapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* The majority of this class was created from the following video: https://www.youtube.com/watch?v=MRv0xtnaJAY */

public class ImageAdapter extends BaseAdapter {

    private Context mContext; // Context of ImageAdapter
    AppDatabase db; // AppDatabase to store all data
    ItemDao itemDao; // itemDao in order to access database

    public Bitmap[] imageArray; // Array of all bitmaps

    /* Constructor for ImageAdapter object that declares mContext, Database, ItemDao, and imageArray */
    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
        db = AppDatabase.getInstance(mContext);
        itemDao = db.itemDao();
        imageArray = loadAllImages();
    }
    /* returns the length of imageArray */
    @Override
    public int getCount() {
        return imageArray.length;
    }

    /* Returns an image from image array with the given position */
    @Override
    public Object getItem(int position) {
        return imageArray[position];
    }

    /* Returns Id of item at its position in image array*/
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /* Returns the view of the closet view */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setBackground(new BitmapDrawable(parent.getResources(), imageArray[position]));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(340,350));
        return imageView;
    }

    /* Sets the background of an ImageView to an item of clothing using the path and Item */
    private void loadImageFromStorage(ImageView img, String path, Item clothing)
    {

        try {
            File f=new File(path, "clothing" + clothing.getId() +".png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setBackground(new BitmapDrawable(Resources.getSystem(), b));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    /* Sets the background of an imageview to be the item of clothing at a position in the database */
    public void loadImageFromPosition(ImageView imageView, int position) {
        List<String> paths = itemDao.loadAllPaths();
        List<Item> clothes = Arrays.asList(itemDao.getAllClothes());
        loadImageFromStorage(imageView, paths.get(position), clothes.get(position));
    }

    /* returns a Bitmap array of all the images in the SQL Database */
    public Bitmap[] loadAllImages() {
        List<String> paths = itemDao.loadAllPaths();
        List<Item> clothes = Arrays.asList(itemDao.getAllClothes());
        Bitmap[] images = new Bitmap[clothes.size()];
        for(Item clothing: clothes) {
            try {
                File f=new File(paths.get(0), "clothing" + clothing.getId() +".png");
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                images[clothing.getId() - 1] = b;
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }

        }

        return images;
    }
}
