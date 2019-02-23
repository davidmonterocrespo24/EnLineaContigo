package com.example.david24.mapa;

/**
 * Created by GRID-3 on 23/05/2017.
 */

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gcddm.contigo.R;
import com.gcddm.contigo.db.Mpuntos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuscarActivity extends ListActivity {

    private EditText et;
    private String[] listview_names;

    private int[] listview_images = {R.drawable.ima1, R.drawable.ima1,
            R.drawable.ima1, R.drawable.ima2,
            R.drawable.ima1, R.drawable.ima2,
            R.drawable.ima1, R.drawable.ima2,
            R.drawable.ima2};

    private ArrayList<String> array_sort;
    private ArrayList<Integer> image_sort;
    int textlength = 0;

    private ListView lv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        List<Mpuntos> mpuntoses = Mpuntos.listAll(Mpuntos.class);
        listview_names=new String[mpuntoses.size()];
        for (int i=0;i<mpuntoses.size();i++){
            listview_names[i]=mpuntoses.get(i).getNombre();
        }
        et = (EditText) findViewById(R.id.EditText01);
        lv = (ListView) findViewById(android.R.id.list);

        array_sort = new ArrayList<String>(Arrays.asList(listview_names));
        image_sort = new ArrayList<Integer>();
        for (int index = 0; index < listview_images.length; index++) {
            image_sort.add(listview_images[index]);
        }

        setListAdapter(new bsAdapter(this));


        et.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                // Abstract Method of TextWatcher Interface.
            }

            public void beforeTextChanged(CharSequence s,
                                          int start, int count, int after) {
                // Abstract Method of TextWatcher Interface.
            }

            public void onTextChanged(CharSequence s,
                                      int start, int before, int count) {
                textlength = et.getText().length();
                array_sort.clear();
                image_sort.clear();
                for (int i = 0; i < listview_names.length; i++) {
                    if (textlength <= listview_names[i].length()) {
                            if (listview_names[i].toLowerCase().contains(
                                et.getText().toString().toLowerCase().trim())) {
                            array_sort.add(listview_names[i]);
                            image_sort.add(listview_images[i]);
                        }
                    }
                }
                AppendList(array_sort, image_sort);
            }
        });

        lv.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0,
                                    View arg1, int position, long arg3) {
                Intent i = new Intent(BuscarActivity.this, MapaActivity.class);
                i.putExtra("nombre",  array_sort.get(position));
                startActivity(i);
            }
        });
    }

    public void AppendList(ArrayList<String> str, ArrayList<Integer> img) {
        setListAdapter(new bsAdapter(this));
    }

    public class bsAdapter extends BaseAdapter {
        Activity cntx;

        public bsAdapter(Activity context) {
            // TODO Auto-generated constructor stub
            this.cntx = context;

        }

        public int getCount() {
            // TODO Auto-generated method stub
            return array_sort.size();
        }

        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return array_sort.get(position);
        }

        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return array_sort.size();
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = null;

            LayoutInflater inflater = cntx.getLayoutInflater();
            row = inflater.inflate(R.layout.lista_lugares, null);

            TextView tv = (TextView) row.findViewById(R.id.title);
            ImageView im = (ImageView) row.findViewById(R.id.imageview);

            tv.setText(array_sort.get(position));
            im.setImageDrawable(getResources().getDrawable(image_sort.get(position)));

            return row;
        }
    }
}