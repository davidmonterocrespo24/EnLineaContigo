package com.gcddm.contigo.Util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gcddm.contigo.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Lizzy on 5/15/2017.
 */
public class AdjuntosAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private LayoutInflater inflater=null;
    Context ctx;
    private View vi;
    private ListView listView;

    public AdjuntosAdapter(Activity a, ArrayList<HashMap<String, String>> d, ListView listView)
    {
        ctx = a.getBaseContext();
        activity = a;
        data=d;
        this.listView = listView;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        vi=convertView;

        if(convertView==null)
            vi = inflater.inflate(R.layout.adjunto_item, null);

        TextView title = (TextView)vi.findViewById(R.id.textView);
        ImageView thumbail = (ImageView)vi.findViewById(R.id.thumbail);
        TextView fecha = (TextView) vi.findViewById(R.id.textView9);
        ImageView erase = (ImageView)vi.findViewById(R.id.imageView3);

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //borrar el adjunto
                if(!data.isEmpty()) {
                    data.remove(position);
                    setListViewHeightBasedOnChildren(listView);
                    notifyDataSetChanged();

                }
            }
        });
        if(!data.isEmpty()) {

            HashMap<String, String> category = new HashMap<String, String>();
            category = data.get(position);

            title.setText(category.get("title"));
            fecha.setText(category.get("fecha"));
            String imagePath = category.get("path");

            int THUMBNAIL_SIZE = 300;

            int screenSize = activity.getResources().getConfiguration().screenLayout &
                    Configuration.SCREENLAYOUT_SIZE_MASK;


            switch(screenSize) {
                case Configuration.SCREENLAYOUT_SIZE_LARGE:
                    THUMBNAIL_SIZE = 350;
                    break;
                case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                    THUMBNAIL_SIZE = 200;
                    break;
                case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                    THUMBNAIL_SIZE = 400;
                    break;
                case Configuration.SCREENLAYOUT_SIZE_SMALL:
                    THUMBNAIL_SIZE = 50;
                    break;
                default:

            }



            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            Bitmap resized = Bitmap.createScaledBitmap(bitmap, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);



            try {
                ExifInterface exif = new ExifInterface(imagePath);
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                }
                else if (orientation == 3) {
                    matrix.postRotate(180);
                }
                else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                resized = Bitmap.createBitmap(resized, 0, 0, resized.getWidth(), resized.getHeight(), matrix, true); // rotating bitmap
            }
            catch (Exception e) {

            }


            thumbail.setImageBitmap(resized);
        }
        return vi;
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            listItem.measure(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
