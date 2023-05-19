package com.example.mytestapp;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;


public class CameraFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final int CAMERA_ACTION_CODE = 1; /* final integer for the camera action code */
    ImageButton imageButton; /* ImageButton which will be clicked in order to take a picture*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageButton = view.findViewById(R.id.imageView);
        Spinner spinner1 = view.findViewById(R.id.spinner1);
        Spinner spinner2 = view.findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.types, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.colors, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        imageButton.setOnClickListener(this);

        Button save = view.findViewById(R.id.button);
        save.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /* Empty camera fragment contructor */
    public CameraFragment() {

    }

    /* What to do in the case of the click of a button*/
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button: /* What happens when the save button is clicked */
                Resources res = getResources();
                if(imageButton.getTag().equals("Camera")) {
                    Log.d("Suhruth", "onClick: save works");
                    TextView error = view.getRootView().findViewById(R.id.error);
                    error.setVisibility(View.VISIBLE);
                    break;
                }
                CheckBox spring;
                CheckBox summer;
                CheckBox winter;
                CheckBox fall;

                CheckBox patterned;

                Spinner clothingType;
                Spinner clothingColor;

                spring = view.getRootView().findViewById(R.id.checkBox);
                summer = view.getRootView().findViewById(R.id.checkBox4);
                fall = view.getRootView().findViewById(R.id.checkBox3);
                winter = view.getRootView().findViewById(R.id.checkBox2);
                patterned = view.getRootView().findViewById(R.id.checkBox5);

                clothingType = view.getRootView().findViewById(R.id.spinner1);
                clothingColor = view.getRootView().findViewById(R.id.spinner2);

                boolean isSpring = spring.isChecked();
                boolean isSummer = summer.isChecked();
                boolean isFall = fall.isChecked();
                boolean isWinter = winter.isChecked();
                boolean isPatterned = patterned.isChecked();

                AppDatabase db = AppDatabase.getInstance(getContext());

                ItemDao itemDao = db.itemDao();

                String type = clothingType.getSelectedItem().toString();
                String color = clothingColor.getSelectedItem().toString();
                Item clothing = new Item(type, color, isWinter, isSpring, isFall, isSummer, isPatterned);
                clothing.setId(itemDao.loadAllIDs().size()+1);
                String path = saveToInternalStorage(clothing, toBitmap(imageButton.getBackground()));
                clothing.setPath(path);

                itemDao.insertAll(clothing);

                clothingColor.setSelection(0);
                clothingType.setSelection(0);

                imageButton.setBackgroundResource(R.drawable.camera);
                imageButton.setTag("Camera");

                spring.setChecked(false);
                summer.setChecked(false);
                winter.setChecked(false);
                fall.setChecked(false);
                patterned.setChecked(false);

                break;

            case R.id.imageView: /* What happens when the image is clicked */
                TextView error = view.getRootView().findViewById(R.id.error);
                error.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(requireActivity().getPackageManager()) != null)    {
                    startActivityForResult(intent, CAMERA_ACTION_CODE);
                } else {
                    Toast.makeText(getContext(),"there is no app that support this action",
                            Toast.LENGTH_SHORT).show();
                }


                break;


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_ACTION_CODE && resultCode == RESULT_OK) {
            assert data != null;
            Bundle bundle = data.getExtras();
            Bitmap finalPhoto = (Bitmap) bundle.get("data");
            imageButton.setBackground(new BitmapDrawable(getResources(), finalPhoto));
            imageButton.setTag("Not Camera");

        }
    }

    /* Method to convert a drawable to a bitmap */
    public static Bitmap toBitmap(Drawable drawable) {
        try {
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            // Handle the error
            return null;
        }
    }


    /* Saves item of clothing to internal storage as an a bitmap */
    private String saveToInternalStorage(Item clothing, Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory,"clothing" + clothing.getId() + ".png");
        Log.d("Suhruth", "saveToInternalStorage: " + clothing.getId());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

}