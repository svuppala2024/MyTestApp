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

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    AppDatabase db;
    ItemDao itemDao;

    public Bitmap[] imageArray;


    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
        db = AppDatabase.getInstance(mContext);
        itemDao = db.itemDao();
        imageArray = loadAllImages();
    }

    @Override
    public int getCount() {
        return imageArray.length;
    }

    @Override
    public Object getItem(int position) {
        return imageArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setBackground(new BitmapDrawable(parent.getResources(), imageArray[position]));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(340,350));
        return imageView;
    }

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

    public void loadImageFromPosition(ImageView imageView, int position) {
        List<String> paths = itemDao.loadAllPaths();
        List<Item> clothes = Arrays.asList(itemDao.getAllClothes());
        loadImageFromStorage(imageView, paths.get(position), clothes.get(position));
    }

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
