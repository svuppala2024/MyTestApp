package com.example.mytestapp;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* The prupose of this class is to display the suggesitons view */
public class SuggestionsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suggestions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase db = AppDatabase.getInstance(getContext());

        ItemDao itemDao = db.itemDao();

        OutfitMaker outfitMaker = new OutfitMaker();
        List<String> TypeT = Arrays.asList("short-sleeved shirt", "long-sleeved shirt", "tank-top");
        List<String> TypeB = Arrays.asList("shorts", "pants", "skirt");

        List<String> clothes = itemDao.loadAllTypes();
        boolean hasTop = false;
        boolean hasBottom = false;
        for(int i = 0; i < TypeT.size(); i++) {
            if(clothes.contains(TypeT.get(i))) {
                hasTop = true;
            }
        }
        for(int i = 0; i < TypeB.size(); i++) {
            if(clothes.contains(TypeB.get(i))) {
                hasBottom = true;
            }
        }

        if(hasBottom & hasTop) {
            ArrayList<Item> outfit = outfitMaker.Generator(Arrays.asList(itemDao.getAllClothes()));
            ImageView imageTop = view.findViewById(R.id.top);
            ImageView imageBottom = view.findViewById(R.id.bottom);

            String topPath = outfit.get(0).getPath();
            String bottomPath = outfit.get(1).getPath();

            Log.d(TAG, "onViewCreated: " + topPath);
            Log.d(TAG, "onViewCreated: " + bottomPath);

            loadImageFromStorage(imageTop, topPath, outfit.get(0));

            loadImageFromStorage(imageBottom, bottomPath, outfit.get(1));
        }
        
    }

    /* Method to set background of ImageView to an image in the internal storage of the app */
    private void loadImageFromStorage(ImageView img, String path, Item clothing)
    {

        try {
            File f=new File(path, "clothing" + clothing.getId() +".png");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setBackground(new BitmapDrawable(getResources(), b));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }



}
