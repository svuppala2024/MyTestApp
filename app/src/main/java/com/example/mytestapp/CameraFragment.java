package com.example.mytestapp;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import androidx.room.Room;


public class CameraFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final int CAMERA_ACTION_CODE = 1;
    ImageButton imageButton;
    String filePath;
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

    public CameraFragment() {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button:
                Resources res = getResources();
                if(areDrawablesIdentical(imageButton.getDrawable(), ResourcesCompat.getDrawable(res,R.drawable.camera, null))) {
                    Log.d(TAG, "onClick: save works");
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

                String type = clothingType.getSelectedItem().toString();
                String color = clothingColor.getSelectedItem().toString();
                Item clothing = new Item(type, color, isWinter, isSpring, isFall, isSummer, isPatterned);
                String url = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), toBitmap(imageButton.getDrawable()), "clothing" + clothing.getId() , "Clothing item " + clothing.getId());
                clothing.setPath(url);
                AppDatabase db = Room.databaseBuilder(view.getContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().build();

                ItemDao itemDao = db.itemDao();

                itemDao.insertAll(clothing);

                clothingColor.setSelection(0);
                clothingType.setSelection(0);

                imageButton.setImageResource(R.drawable.camera);

                spring.setChecked(false);
                summer.setChecked(false);
                winter.setChecked(false);
                fall.setChecked(false);
                patterned.setChecked(false);

                break;

            case R.id.imageView:
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
            imageButton.setImageBitmap(finalPhoto);

        }
    }

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

    public static boolean areDrawablesIdentical(Drawable drawableA, Drawable drawableB) {
        Drawable.ConstantState stateA = drawableA.getConstantState();
        Drawable.ConstantState stateB = drawableB.getConstantState();
        // If the constant state is identical, they are using the same drawable resource.
        // However, the opposite is not necessarily true.
        return (stateA != null && stateA.equals(stateB))
                || toBitmap(drawableA).sameAs(toBitmap(drawableB));
    }
}