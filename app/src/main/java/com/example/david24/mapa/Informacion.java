package com.example.david24.mapa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bluejamesbond.text.DocumentView;
import com.gcddm.contigo.InicioActivity;
import com.gcddm.contigo.R;
import com.gcddm.contigo.Util.TouchImageView;
import com.gcddm.contigo.db.Imagen;
import com.gcddm.contigo.db.Info;
import com.gcddm.contigo.db.Mpuntos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Informacion extends AppCompatActivity implements  ViewPager.OnPageChangeListener{

    ViewPager viewPager;
    private LinearLayout indicator;
    private ImageView[] marks;
    private int activePage = 0;
    private static final int[] selected = new int[]{R.drawable.sq_red, R.drawable.sq_red, R.drawable.sq_red};
    private ArrayList<String> imagens;
    public Bitmap [] bitmaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        setContentView(R.layout.activity_informacion);

        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        Mpuntos info = Mpuntos.findById(Mpuntos.class, new Long(id));

        TextView titulo=(TextView)findViewById(R.id.tituloinformacion);
        titulo.setText(info.getNombre());

        TextView direccion=(TextView)findViewById(R.id.direccion);
        direccion.setText(info.getDireccion());

        DocumentView descripcion=(DocumentView)findViewById(R.id.descripcion);
        descripcion.setText(info.getInformacion());

        /*if(info.getImagen().trim().length()!=0){
            File dirFile = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dirFile,info.getImagen());
            Bitmap imagP = BitmapFactory.decodeFile(imageFile.getPath());
        }*/

        imagens = info.getImagenes();
        bitmaps = new Bitmap[imagens.size()];

        for(int i = 0; i < imagens.size(); i++){
            File dirFile = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dirFile,imagens.get(i));
            Bitmap imagP = BitmapFactory.decodeFile(imageFile.getPath());
            bitmaps[i] = imagP;
        }


        TextView tel=(TextView)findViewById(R.id.telef);
        tel.setText(info.getTelef());

        viewPager = (ViewPager) findViewById(R.id.slide);
        viewPager.setAdapter(new PagerAdapter() {


            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ViewGroup layout = (ViewGroup) LayoutInflater.from(Informacion.this).inflate(R.layout.slide_informacion, container, false);
                container.addView(layout);

                ImageView imageView = (ImageView) layout.findViewById(R.id.image_iq2);
                imageView.setImageBitmap(bitmaps[position]);

                return layout;
            }

            public int getCount() {
                return bitmaps.length;
            }

            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        setupPageIndicators();
    }

    private void setupPageIndicators() {
        if(imagens.size() != 0) {
            this.indicator = (LinearLayout) findViewById(R.id.page_indicator);
            this.indicator.setGravity(17);
            android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(-2, -2);
            this.marks = new ImageView[imagens.size()];
            int padding = (int) ((3.0f * getResources().getDisplayMetrics().density) + 0.5f);
            for (int i = 0; i < this.marks.length; i++) {
                this.marks[i] = new ImageView(this);
                this.marks[i].setPadding(padding, padding, padding, padding);
                this.marks[i].setImageResource(R.drawable.sq_blue);
                this.indicator.addView(this.marks[i], i, params);
            }
            this.marks[this.activePage].setImageResource(R.drawable.sq_red);
            this.viewPager.addOnPageChangeListener(Informacion.this);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.marks[this.activePage].setImageResource(R.drawable.sq_blue);
        ImageView[] imageViewArr = this.marks;
        this.activePage = position;
        imageViewArr[position].setImageResource(selected[position]);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.

            actionBar.setDisplayHomeAsUpEnabled(true);
            // Hide the action bar title

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ic_menu_general, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                // TODO put your code here to respond to the button tap
                Intent i = new Intent(Informacion.this, InicioActivity.class);
                startActivity(i);
                Informacion.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
