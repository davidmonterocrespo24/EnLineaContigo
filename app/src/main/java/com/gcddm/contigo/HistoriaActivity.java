package com.gcddm.contigo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gcddm.contigo.Util.TouchImageView;

public class HistoriaActivity extends Activity implements  ViewPager.OnPageChangeListener {
    ViewPager viewPager;
    private LinearLayout indicator;
    private ImageView[] marks;
    private int activePage = 0;
    private static final int[] selected = new int[]{R.drawable.sq_red, R.drawable.sq_green};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.historia_programa_layout);

        System.gc();

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new PagerAdapter() {
            final int[] images = new int[]{R.drawable.h_1111, R.drawable.h_2222};

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ViewGroup layout = (ViewGroup) LayoutInflater.from(HistoriaActivity.this).inflate(R.layout.slideshow_item, container, false);
                container.addView(layout);
                System.gc();

                try {
                    TouchImageView imageView = (TouchImageView) layout.findViewById(R.id.image_iq);
                    /*Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.large_icon);
                    imageView.setImageBitmap(largeIcon);*/
                    imageView.setImageResource(images[position]);
                }catch (OutOfMemoryError e){
                    Toast.makeText(HistoriaActivity.this,"Lo sentimos debido al poco espacio en memoria no podemos mostrar esta opcion completamente",Toast.LENGTH_LONG).show();

                }


                return layout;
            }

            public int getCount() {
                return this.images.length;
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
        this.indicator = (LinearLayout) findViewById(R.id.page_indicator);
        this.indicator.setGravity(17);
        android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(-2,-2);
        this.marks = new ImageView[2];
        int padding = (int) ((3.0f * getResources().getDisplayMetrics().density) + 0.5f);
        for (int i = 0; i < this.marks.length; i++) {
            this.marks[i] = new ImageView(this);
            this.marks[i].setPadding(padding, padding, padding, padding);
            this.marks[i].setImageResource(R.drawable.sq_gray);
            this.indicator.addView(this.marks[i], i, params);
        }
        this.marks[this.activePage].setImageResource(R.drawable.sq_red);
        this.viewPager.addOnPageChangeListener(HistoriaActivity.this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.marks[this.activePage].setImageResource(R.drawable.sq_gray);
        ImageView[] imageViewArr = this.marks;
        this.activePage = position;
        imageViewArr[position].setImageResource(selected[position]);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
