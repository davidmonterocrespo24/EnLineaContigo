package com.gcddm.contigo;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImagenAumentada extends AppCompatActivity {
    ImageView galleryview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_aumentada);
        galleryview = (ImageView) findViewById(R.id.imagen_aumentada);

        String path=getIntent().getStringExtra("path");
        galleryview.setImageBitmap(BitmapFactory.decodeFile(path));
        galleryview.setScaleType(ImageView.ScaleType.FIT_XY);
    }
}
