package com.gcddm.contigo.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gcddm.contigo.ImagenAumentada;
import com.gcddm.contigo.db.Imagen;

import java.io.File;
import java.util.List;

/**
 * Created by MIRTHA on 02/12/2016.
 */
public class ImagenAdapter extends BaseAdapter {
    private Context context;
    private List<Imagen> imagenes;

    public ImagenAdapter(Context context, List<Imagen> imagenes){
        this.context = context;
        this.imagenes = imagenes;
    }

    @Override
    public int getCount() {
        return imagenes.size();
    }

    @Override
    public Imagen getItem(int position) {
        return imagenes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return imagenes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Imagen imagen = getItem(position);
        File file = new File(imagen.getPath());

        ImageView galleryview = new ImageView(context);

        if (file.exists()){
            galleryview.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
            galleryview.setAdjustViewBounds(true);
            galleryview.setScaleType(ImageView.ScaleType.FIT_XY);
           // galleryview.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }


        galleryview.setLayoutParams(new AbsListView.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        galleryview.setPadding(5, 5, 5, 5);
//        galleryview.setBackgroundResource(android.R.drawable.picture_frame);
        galleryview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context, ImagenAumentada.class);
                i.putExtra("path",imagen.getPath());
                context.startActivity(i);
            }
        });
        return galleryview;
    }
}
