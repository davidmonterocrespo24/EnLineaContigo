package com.gcddm.contigo;


import android.Manifest;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.david24.mapa.MapaActivity;
import com.gcddm.contigo.Util.BadgeDrawable;
import com.gcddm.contigo.Util.UpdateTask;
import com.gcddm.contigo.db.Frases;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class InicioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TextView texto_frase;
    private TextView texto_frase1;
    private TextView autor_frase;
    private TextView autor_frase1;
    private Date date;
    private int mNotificationsCount = 0;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView10;
    private ImageView imageView11;
    private ImageView imageView12;
    private ImageView imageView13;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean mIsBackVisible = false;
    private AnimatorSet mSetRightOut2;
    private AnimatorSet mSetLeftIn2;
    private boolean mIsBackVisible2 = false;
    private AnimatorSet mSetRightOut3;
    private AnimatorSet mSetLeftIn3;
    private boolean mIsBackVisible3 = false;
    private AnimatorSet mSetRightOut4;
    private AnimatorSet mSetLeftIn4;
    private boolean mIsBackVisible4 = false;
    private AsyncTask asyncTask;
    private static final int SOLICITUD_PERMISOS = 0;
    private LinearLayout inicio;



    Frases frase;

    private ImageSwitcher imageSwitcher;

    private int[] gallery = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};

    private int position;

    private static final Integer DURATION = 7000;

    private Timer timer = null;

    public static String SERVER_ADDRESS = "";
    public static String EMAIL_ADDRES = "";
    public static int PHONE_NUMBER;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        frase= cargarFraseDelDia();
        final String f = frase.getFrase();
        final String a = frase.getAutor();

        System.gc();

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {
                //todo bien
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.CHANGE_NETWORK_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.READ_CONTACTS,
                        Manifest.permission.CALL_PHONE,Manifest.permission.VIBRATE,Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.INTERNET,Manifest.permission.CHANGE_WIFI_STATE}, SOLICITUD_PERMISOS
                );
            }
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);


        texto_frase = (TextView) findViewById(R.id.texto_frase);




        autor_frase = (TextView) findViewById(R.id.autor_frase);


        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView5 = (ImageView) findViewById(R.id.imageView5);

        imageView10 = (ImageView) findViewById(R.id.imageView10);
        imageView11 = (ImageView) findViewById(R.id.imageView11);
        imageView12 = (ImageView) findViewById(R.id.imageView12);
        imageView13 = (ImageView) findViewById(R.id.imageView13);

        inicio = (LinearLayout)findViewById(R.id.inicio);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InicioActivity.this,PublicarActivity.class);
                startActivity(i);

            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InicioActivity.this, JuegoActivity.class);
                startActivity(i);
            }
        });
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(InicioActivity.this,InfoActivity.class);
                startActivity(i);
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(InicioActivity.this,MapaActivity.class);
                startActivity(i);

            }
        });


        String carpeta_fuente="fonts/Basset Bold Italic.ttf";
        Typeface fuente= Typeface.createFromAsset(getAssets(),carpeta_fuente);
        if (texto_frase!=null){
            texto_frase.setTypeface(fuente);
        }

        if (autor_frase!=null){
            autor_frase.setTypeface(fuente);
        }


        if (frase != null) {
            String frase_ya = f;
            String [] arreglo = frase_ya.split(" ");
            String mostrar = "";
            if(arreglo.length > 15){
                for(int i = 0; i < 15; i++){
                    mostrar += arreglo[i]+" ";
                }
            }else{
                mostrar = frase_ya;
            }
            texto_frase.setText("‘‘"+ mostrar +"...’’");

            autor_frase.setText(a);

        }

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView switcherImageView = new ImageView(getApplicationContext());
                switcherImageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
                ));
                switcherImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                switcherImageView.setImageResource(R.drawable.a);

                return switcherImageView;
            }
        });

        texto_frase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frase!=null){

                    android.app.AlertDialog.Builder builder2 = new  android.app.AlertDialog.Builder(InicioActivity.this);
                    LayoutInflater inflater2 = InicioActivity.this.getLayoutInflater();
                    View alertLayout2 = inflater2.inflate(R.layout.frase_completa, null);
                    texto_frase1 = (TextView)alertLayout2.findViewById(R.id.texto_frase1);
                    autor_frase1 = (TextView)alertLayout2. findViewById(R.id.autor_frase1);
                    String carpeta_fuente="fonts/Basset Bold Italic.ttf";
                    Typeface fuente= Typeface.createFromAsset(getAssets(),carpeta_fuente);
                    if (texto_frase1!=null){
                        texto_frase1.setTypeface(fuente);
                    }
                    if (autor_frase1!=null){
                        autor_frase1.setTypeface(fuente);
                    }
                    texto_frase1.setText(f);
                    autor_frase1.setText(a);
                    final android.app.AlertDialog alertDialog = builder2.setView(alertLayout2).create();
                    alertDialog.show();



                }
            }
        });

        Animation animationOut = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        Animation animationIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        imageSwitcher.setOutAnimation(animationOut);
        imageSwitcher.setInAnimation(animationIn);
        start();

        loadAnimations();
        changeCameraDistance();


    }

    /**
     * starts or restarts the slider
     */
    public void start() {
        if (timer != null) {
            timer.cancel();
        }
        position = 0;
        startSlider();
    }


    public void startSlider() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // avoid exception:
                // "Only the original thread that created a view hierarchy can touch its views"
                runOnUiThread(new Runnable() {
                    public void run() {
                        imageSwitcher.setImageResource(gallery[position]);
                        position++;
                        if (position == gallery.length) {
                            position = 0;
                        }
                    }
                });
            }

        }, 0, DURATION);
    }



    // Stops the slider when the Activity is going into the background
    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer != null) {
            startSlider();
        }
        asyncTask = new Anymation().execute();

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(InicioActivity.this);
        SERVER_ADDRESS = pref.getString("IpAdress","");
        if(SERVER_ADDRESS.matches("")){
            SERVER_ADDRESS = "192.168.4.1";
        }
        EMAIL_ADDRES = pref.getString("emailAdress","");
        if(EMAIL_ADDRES.matches("")){
            EMAIL_ADDRES = "dionis@uo.edu.cu";
        }
        if(!pref.getString("phoneNumber","").matches("")){
            PHONE_NUMBER = Integer.valueOf(pref.getString("phoneNumber",""));

        }else{
            PHONE_NUMBER = 52886644;
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            this.finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ic_menu_publicar, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.alerta_sms:
                // TODO put your code here to respond to the button tap
                Intent i = new Intent(InicioActivity.this, BuzonesActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_config) {
            //settings
            Intent i = new Intent(InicioActivity.this, AdvancedSettingsActivity.class);
            startActivity(i);
        }

        if (id == R.id.nav_manage) {
            Intent i = new Intent(InicioActivity.this, SettingsActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {
            Intent i = new Intent(InicioActivity.this, AcercadeActivity.class);
            startActivity(i);

        }else if (id == R.id.nav_send) {
            Intent i = new Intent(InicioActivity.this, LinkActivity.class);
            startActivity(i);

        }else if (id == R.id.historia) {
            Intent i = new Intent(InicioActivity.this, HistoriaActivity.class);
            startActivity(i);

        }
        else if (id == R.id.info_drawer) {

            Intent i = new Intent(InicioActivity.this,
                    com.gcddm.contigo.Util.FileActivity.class);
            startActivityForResult(i,999);

        }
        else if (id == R.id.serv_drawer) {

            Intent i = new Intent(InicioActivity.this, ServiciosActivity.class);
            startActivity(i);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == RESULT_OK){
                String compactado = data.getStringExtra("compactado");

                UpdateTask task = new UpdateTask(InicioActivity.this);
                task.execute(compactado);
            }
        }
    }



    public Frases cargarFraseDelDia() {
        List<Frases> frases = Frases.listAll(Frases.class);
        if (frases.size() != 0) {
            long seed = System.currentTimeMillis();
            Random r = new Random(seed);
            return frases.get(r.nextInt(79));
        }
        return frases.get(0);
    }
    class Anymation extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // example count. This is where you'd
            // query your data store for the actual count.
            //lamar a la base de datos para saber la cantidad de mensajes recividos
            if(isCancelled())
                return false;

            return true;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            flipCard(imageView2,imageView10);
            flipCard2(imageView3,imageView11);
            flipCard3(imageView4,imageView12);
            flipCard4(imageView5,imageView13);
        }
    }

    @Override
    protected void onStop() {
        asyncTask.cancel(true);
        super.onStop();
    }



    private void changeCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        imageView2.setCameraDistance(scale);
        imageView10.setCameraDistance(scale);
        imageView3.setCameraDistance(scale);
        imageView11.setCameraDistance(scale);
        imageView4.setCameraDistance(scale);
        imageView12.setCameraDistance(scale);
        imageView5.setCameraDistance(scale);
        imageView13.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
        mSetRightOut2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
        mSetRightOut3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn3 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
        mSetRightOut4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn4 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
    }


    public void flipCard(View view1, View view) {
        if (!mIsBackVisible) {
            mSetRightOut.setTarget(view1);
            mSetLeftIn.setTarget(view);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(view);
            mSetLeftIn.setTarget(view1);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible = false;
        }
    }

    public void flipCard2(View view1, View view) {
        if (!mIsBackVisible2) {
            mSetRightOut2.setTarget(view1);
            mSetLeftIn2.setTarget(view);
            mSetRightOut2.start();
            mSetLeftIn2.start();
            mIsBackVisible2 = true;
        } else {
            mSetRightOut2.setTarget(view);
            mSetLeftIn2.setTarget(view1);
            mSetRightOut2.start();
            mSetLeftIn2.start();
            mIsBackVisible2 = false;
        }
    }

    public void flipCard3(View view1, View view) {
        if (!mIsBackVisible3) {
            mSetRightOut3.setTarget(view1);
            mSetLeftIn3.setTarget(view);
            mSetRightOut3.start();
            mSetLeftIn3.start();
            mIsBackVisible3 = true;
        } else {
            mSetRightOut3.setTarget(view);
            mSetLeftIn3.setTarget(view1);
            mSetRightOut3.start();
            mSetLeftIn3.start();
            mIsBackVisible3 = false;
        }
    }

    public void flipCard4(View view1, View view) {
        if (!mIsBackVisible4) {
            mSetRightOut4.setTarget(view1);
            mSetLeftIn4.setTarget(view);
            mSetRightOut4.start();
            mSetLeftIn4.start();
            mIsBackVisible4 = true;
        } else {
            mSetRightOut4.setTarget(view);
            mSetLeftIn4.setTarget(view1);
            mSetRightOut4.start();
            mSetLeftIn4.start();
            mIsBackVisible4 = false;
        }
    }







}
