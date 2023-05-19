package com.example.mytestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/* The purpose f this class is to display the closet fragment */

public class ClosetFragment extends Fragment {

    GridView gridView; /* Gridview object */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_closet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridView = view.findViewById(R.id.grid_view);
        gridView.setAdapter(new ImageAdapter(getContext()));

        gridView.setOnItemClickListener((adapterView, view1, position, id) -> {
            Intent intent = new Intent(getContext(), com.example.mytestapp.FullScreenActivity.class);
            intent.putExtra("id",position);
            startActivity(intent);
        });

    }
}