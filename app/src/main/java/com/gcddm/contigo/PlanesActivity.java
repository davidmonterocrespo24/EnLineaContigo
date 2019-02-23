package com.gcddm.contigo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlanesActivity extends AppCompatActivity {

    /*ImageButton consultar;
    ImageButton sms;
    ImageButton voz;
    ImageButton amigos;
    ImageButton bolsa;*/


    ServicioAdapter service_adapter;
    List<Servicio> servicios;
    ListView planes_list;

    EditText planes_edit1;
    ImageButton planes_icon2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes);
        setupActionBar();

        System.gc();
        servicios=new ArrayList<>();
        servicios.add(new Servicio(3,5,"Contratar Plan de ETECSA"));
        servicios.add(new Servicio(3,6,"Consultar Plan de SMS"));
        servicios.add(new Servicio(3,7,"Consultar Plan de VOZ"));
        servicios.add(new Servicio(3,8,"Consultar Plan Amigos"));
        servicios.add(new Servicio(3,9,"Consultar Bolsa Nauta"));

        planes_list=(ListView) findViewById(R.id.planes_list);
        planes_list.setEmptyView(findViewById(R.id.emptyListPlanes));
        service_adapter= new ServicioAdapter(PlanesActivity.this,servicios);
        planes_list.setAdapter(service_adapter);

        planes_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Servicio servicio =service_adapter.getItem(position);
                if(servicio.getNombre().equals("Contratar Plan de ETECSA")){
                    Intent intent = new Intent("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel: *133" + Uri.encode("#")));
                    PlanesActivity.this.startActivity(intent);
                }else if(servicio.getNombre().equals("Consultar Plan de SMS")){
                    Intent intent = new Intent("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel: *222*767" + Uri.encode("#")));
                    PlanesActivity.this.startActivity(intent);
                }else if(servicio.getNombre().equals("Consultar Plan de VOZ")){
                    Intent intent = new Intent("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel: *222*869" + Uri.encode("#")));
                    PlanesActivity.this.startActivity(intent);
                }else if(servicio.getNombre().equals("Consultar Plan Amigos")){
                    Intent intent = new Intent("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel: *222*264" + Uri.encode("#")));
                    PlanesActivity.this.startActivity(intent);
                }else if(servicio.getNombre().equals("Consultar Bolsa Nauta")){
                    Intent intent = new Intent("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel: *222*328" + Uri.encode("#")));
                    PlanesActivity.this.startActivity(intent);
                }
            }
        });


        planes_edit1=(EditText)findViewById(R.id.planes_edit1) ;
        planes_edit1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                ((ArrayAdapter<String>)planes_list.getAdapter()).getFilter().filter(arg0);
//                   adapter.getFilter().filter(arg0);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        planes_icon2=(ImageButton)findViewById(R.id.planes_icon2);
        planes_icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //service_edit1.setText("");
                int cursorPosition1 = planes_edit1.getSelectionStart();
                if (cursorPosition1 > 0) {
                    planes_edit1.setText(planes_edit1.getText().delete(cursorPosition1 - 1, cursorPosition1));
                    planes_edit1.setSelection(cursorPosition1-1);
                }
            }
        });


        /*consultar=(ImageButton) findViewById(R.id.consultar);
        sms=(ImageButton) findViewById(R.id.sms);
        voz=(ImageButton) findViewById(R.id.voz);
        amigos=(ImageButton) findViewById(R.id.amigos);
        bolsa=(ImageButton) findViewById(R.id.bolsa);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.CALL");
                intent.setData(Uri.parse("tel: *133" + Uri.encode("#")));
                PlanesActivity.this.startActivity(intent);
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.CALL");
                intent.setData(Uri.parse("tel: *222*767" + Uri.encode("#")));
                PlanesActivity.this.startActivity(intent);
            }
        });

        voz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.CALL");
                intent.setData(Uri.parse("tel: *222*869" + Uri.encode("#")));
                PlanesActivity.this.startActivity(intent);
            }
        });

        amigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.CALL");
                intent.setData(Uri.parse("tel: *222*264" + Uri.encode("#")));
                PlanesActivity.this.startActivity(intent);
            }
        });

        bolsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.CALL");
                intent.setData(Uri.parse("tel: *222*328" + Uri.encode("#")));
                PlanesActivity.this.startActivity(intent);
            }
        });*/



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
                Intent i = new Intent(PlanesActivity.this, InicioActivity.class);
                startActivity(i);
                PlanesActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.

            actionBar.setDisplayHomeAsUpEnabled(true);
            // Hide the action bar title

        }
    }
}
