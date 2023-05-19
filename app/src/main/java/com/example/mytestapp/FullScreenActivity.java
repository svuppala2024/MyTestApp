package com.example.mytestapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

/* The majority of this class was created from the following video: https://www.youtube.com/watch?v=MRv0xtnaJAY */

public class FullScreenActivity extends AppCompatActivity {

    ImageView imageView; /* The ImageView the user clicks to make fullscreen */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        imageView = findViewById(R.id.image_view);

        Objects.requireNonNull(getSupportActionBar()).hide();
        getSupportActionBar().setTitle("Full Screen Image");

        Intent i = getIntent();
        int position = i.getExtras().getInt("id"); /* position of image that needs to be fullscreened */

        ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext()); /* New ImageAdapter object */

        imageAdapter.loadImageFromPosition(imageView, position);
    }

}