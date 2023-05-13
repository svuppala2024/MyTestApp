package com.example.mytestapp;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


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


//            String topPath = outfit.get(0).getPath();
//            String bottomPath = outfit.get(1).getPath();

            ContextWrapper cw = new ContextWrapper(requireActivity().getApplicationContext());
            //File topDirectory = cw.getDir(topPath, Context.MODE_PRIVATE);
            File topFile = new File(requireContext().getFilesDir().getAbsolutePath(), "clothing" + outfit.get(0).getId() + ".jpg");
            Log.d(TAG, "onViewCreated: " + topFile.toString());
            imageTop.setBackground(Drawable.createFromPath(topFile.toString()));

            //File bottomDirectory = cw.getDir(bottomPath, Context.MODE_PRIVATE);
            File bottomFile = new File(requireContext().getFilesDir(), "clothing" + outfit.get(1).getId() + ".jpg");
            imageBottom.setBackground(Drawable.createFromPath(bottomFile.toString()));
        }



        
    }
}