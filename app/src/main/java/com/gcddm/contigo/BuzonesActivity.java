package com.gcddm.contigo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;


import com.gcddm.contigo.db.Configuracion;
import com.gcddm.contigo.db.Contacto;
import com.gcddm.contigo.db.MensajeRecibido;
import com.gcddm.contigo.db.Review;
import com.gcddm.contigo.db.SubTema;
import com.gcddm.contigo.db.TemaCaliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class BuzonesActivity extends AppCompatActivity {

    public String selected_tab="";
    public int selected;
    public int show=0;
    public int selected_all=0;

    public ListView lista;
    public List<Review> list;
    public ReviewAdapter adaptador;
    public ListView lista1;
    public List<Review> list1;
    public ReviewAdapter adaptador1;
    public ListView lista2;
    public List<Review> list2;
    public ReviewAdapter adaptador2;


    public EditText inputSearch0;
    public EditText inputSearch1;
    public EditText inputSearch2;
    public ImageButton tab1_button1;
    public ImageButton tab2_button1;
    public ImageButton tab3_button1;
    public TabHost tabs;


    EditText fecha;
    EditText tema;
    EditText subtema;
    EditText nombre;
    Button search;
    String fecha_busq="";
    String tema_busq="";
    String subtema_busq="";
    String nombre_busq="";

    CheckBox tab1_ch1;
    CheckBox tab1_ch2;
    CheckBox tab2_ch1;
    CheckBox tab2_ch2;
    CheckBox tab3_ch1;
    CheckBox tab3_ch2;


    MenuItem eliminar;
    MenuItem todos;

    @Override
    protected void onResume() {
        super.onResume();
        update_list_0(0,0);
        update_list_1(0,0);
        update_list_2(0,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buzones_activity);
        setupActionBar();

        //crear_info();

        Resources res = getResources();

        tabs=(TabHost)findViewById(android.R.id.tabhost);
        tabs.setup();


        TabHost.TabSpec spec=tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            spec.setIndicator("Por Enviar",getResources().getDrawable(R.drawable.sms_enviados_copia,null));
        }else{
            spec.setIndicator("Por Enviar",getResources().getDrawable(R.drawable.sms_enviados_copia));
        }
        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            spec.setIndicator("Enviados",getResources().getDrawable(R.drawable.sms_confirmados_50_copia,null));
        }else{
            spec.setIndicator("Enviados",getResources().getDrawable(R.drawable.sms_confirmados_50_copia));
        }

        tabs.addTab(spec);

        spec=tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            spec.setIndicator("Borradores",getResources().getDrawable(R.drawable.sms_recibidos_50_copia,null));
        }else{
            spec.setIndicator("Borradores",getResources().getDrawable(R.drawable.sms_recibidos_50_copia));
        }

        tabs.addTab(spec);



        tabs.setCurrentTab(0);
        selected_tab="mitab1";



        tabs.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.i("BuzonesActivityLog", "Pulsada pestaña: " + tabId);
                selected_tab=tabId;
                if(selected_tab.equals("mitab1")){
                    show=0;
                    selected_all=0;
                    update_list_1(show,selected_all);
                    update_list_2(show,selected_all);
                    todos.setVisible(false);
                    todos.setChecked(false);
                }else if(selected_tab.equals("mitab2")){
                    show=0;
                    selected_all=0;
                    update_list_0(show,selected_all);
                    update_list_2(show,selected_all);
                    todos.setVisible(false);
                    todos.setChecked(false);
                }else if(selected_tab.equals("mitab3")){
                    show=0;
                    selected_all=0;
                    update_list_0(show,selected_all);
                    update_list_1(show,selected_all);
                    todos.setVisible(false);
                    todos.setChecked(false);
                }
            }
        });


        lista = (ListView)findViewById(R.id.lvLista0);
        lista.setEmptyView(findViewById(R.id.emptyList0));
        lista1 = (ListView)findViewById(R.id.lvLista1);
        lista1.setEmptyView(findViewById(R.id.emptyList1));

        lista2=(ListView) findViewById(R.id.lvLista2);
        lista2.setEmptyView(findViewById(R.id.emptyList2));
        lista2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> parent, View item,
                                     int position, long id) {
                Review rev = adaptador2.getItem(position);

                Intent intent=new Intent(BuzonesActivity.this,PublicarActivity.class);
                intent.putExtra("id",rev.getId());
                startActivity(intent);
                BuzonesActivity.this.finish();
            }
        });



        List<Review> lista_elim = new ArrayList<Review>();
        lista_elim = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ?", String.valueOf(Review.POR_ENVIAR));
        if (lista_elim.size() >= 100) {
            for (int i = 0; i < lista_elim.size()-55; i++) {
                lista_elim.get(i).delete();
            }
        }
        List<Review> lista_elim2 = new ArrayList<Review>();
        lista_elim2 = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ?", String.valueOf(Review.ENVIADO));
        if (lista_elim2.size() >= 100) {
            for (int i = 0; i < lista_elim2.size()-55; i++) {
                lista_elim2.get(i).delete();
            }
        }
        List<Review> lista_elim3 = new ArrayList<Review>();
        lista_elim3 = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ?", String.valueOf(Review.BORRADOR));
        if (lista_elim3.size() >= 100) {
            for (int i = 0; i < lista_elim3.size()-55; i++) {
                lista_elim3.get(i).delete();
            }
        }
        update_list_0(0,0);
        update_list_1(0,0);
        update_list_2(0,0);





        inputSearch0=(EditText)findViewById(R.id.tab1_edit1) ;
        inputSearch0.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                ((ArrayAdapter<String>)lista.getAdapter()).getFilter().filter(arg0);
//                   adapter.getFilter().filter(arg0);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        tab1_button1=(ImageButton)findViewById(R.id.tab1_button1);
        tab1_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String temp=inputSearch0.getText().toString();
//                String temp2=temp.substring(0,temp.length()-1);
//                inputSearch0.setText(temp2);
                int cursorPosition = inputSearch0.getSelectionStart();
                if (cursorPosition > 0) {
                    inputSearch0.setText(inputSearch0.getText().delete(cursorPosition - 1, cursorPosition));
                    inputSearch0.setSelection(cursorPosition-1);
                }
            }
        });



        inputSearch1=(EditText)findViewById(R.id.tab2_edit2) ;
        inputSearch1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                ((ArrayAdapter<String>)lista1.getAdapter()).getFilter().filter(arg0);
//                   adapter.getFilter().filter(arg0);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        tab2_button1=(ImageButton) findViewById(R.id.tab2_button2);
        tab2_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inputSearch1.setText("");
                int cursorPosition1 = inputSearch1.getSelectionStart();
                if (cursorPosition1 > 0) {
                    inputSearch1.setText(inputSearch1.getText().delete(cursorPosition1 - 1, cursorPosition1));
                    inputSearch1.setSelection(cursorPosition1-1);
                }
            }
        });

        inputSearch2=(EditText)findViewById(R.id.tab3_edit3) ;
        inputSearch2.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
               ((ArrayAdapter<String>)lista2.getAdapter()).getFilter().filter(arg0);
//                   adapter.getFilter().filter(arg0);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        tab3_button1=(ImageButton) findViewById(R.id.tab3_button3);
        tab3_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //inputSearch2.setText("");
                int cursorPosition2 = inputSearch2.getSelectionStart();
                if (cursorPosition2 > 0) {
                    inputSearch2.setText(inputSearch2.getText().delete(cursorPosition2 - 1, cursorPosition2));
                    inputSearch2.setSelection(cursorPosition2-1);
                }
            }
        });


        tab1_ch1=(CheckBox) findViewById(R.id.tab1_checkBox1);
        tab1_ch2=(CheckBox) findViewById(R.id.tab1_checkBox2);
        //Tab Por Enviar Fecha
        tab1_ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tab1_ch2.isChecked()){
                    tab1_ch2.setChecked(false);
                }
                //list = Review.find(Review.class, "estado = ?",  String.valueOf(Review.POR_ENVIAR),"fecha ASC");
                if(tab1_ch1.isChecked()) {
                    list = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY fecha DESC", String.valueOf(Review.POR_ENVIAR));
                    if (list != null) {
                        adaptador = new ReviewAdapter(BuzonesActivity.this, list, show, selected_all);
                        lista.setAdapter(adaptador);
                        lista.setSelection(lista.getCount() - 1);
                    }
                }else{
                    update_list_0(0,0);
                }

            }
        });
        //Tab Por Enviar Contacto
        tab1_ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tab1_ch1.isChecked()){
                    tab1_ch1.setChecked(false);
                }
                if(tab1_ch2.isChecked()) {
                    list = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY contacto DESC", String.valueOf(Review.POR_ENVIAR));
                    if (list != null) {
                        adaptador = new ReviewAdapter(BuzonesActivity.this, list, show, selected_all);
                        lista.setAdapter(adaptador);
                        lista.setSelection(lista.getCount() - 1);
                    }
                }else{
                    list = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY contacto ASC", String.valueOf(Review.POR_ENVIAR));
                    if (list != null) {
                        adaptador = new ReviewAdapter(BuzonesActivity.this, list, show, selected_all);
                        lista.setAdapter(adaptador);
                        lista.setSelection(lista.getCount() - 1);
                    }
                }
            }
        });


        tab2_ch1=(CheckBox) findViewById(R.id.tab2_checkBox1);
        tab2_ch2=(CheckBox) findViewById(R.id.tab2_checkBox2);
        //Tab Enviados Fecha
        tab2_ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tab2_ch2.isChecked()){
                    tab2_ch2.setChecked(false);
                }
                //list = Review.find(Review.class, "estado = ?",  String.valueOf(Review.POR_ENVIAR),"fecha ASC");
                if(tab2_ch1.isChecked()) {
                    list1 = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY fecha DESC", String.valueOf(Review.ENVIADO));
                    if (list1 != null) {
                        adaptador1 = new ReviewAdapter(BuzonesActivity.this, list1, show, selected_all);
                        lista1.setAdapter(adaptador1);
                        lista1.setSelection(lista1.getCount() - 1);
                    }
                }else{
                    update_list_1(0,0);
                }

            }
        });
        //Tab Enviados Contactos
        tab2_ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tab2_ch1.isChecked()){
                    tab2_ch1.setChecked(false);
                }
                if(tab2_ch2.isChecked()) {
                    list1 = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY contacto DESC", String.valueOf(Review.ENVIADO));
                    if (list1 != null) {
                        adaptador1 = new ReviewAdapter(BuzonesActivity.this, list1, show, selected_all);
                        lista1.setAdapter(adaptador1);
                        lista1.setSelection(lista1.getCount() - 1);
                    }
                }else{
                    list1 = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY contacto ASC", String.valueOf(Review.ENVIADO));
                    if (list1 != null) {
                        adaptador1 = new ReviewAdapter(BuzonesActivity.this, list1, show, selected_all);
                        lista1.setAdapter(adaptador1);
                        lista1.setSelection(lista1.getCount() - 1);
                    }
                }
            }
        });



        tab3_ch1=(CheckBox) findViewById(R.id.tab3_checkBox1);
        tab3_ch2=(CheckBox) findViewById(R.id.tab3_checkBox2);
        //Tab Enviados Fecha
        tab3_ch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tab3_ch2.isChecked()){
                    tab3_ch2.setChecked(false);
                }
                if(tab3_ch1.isChecked()) {
                    list2 = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY fecha DESC", String.valueOf(Review.BORRADOR));
                    if (list2 != null) {
                        adaptador2 = new ReviewAdapter(BuzonesActivity.this, list2, show, selected_all);
                        lista2.setAdapter(adaptador2);
                        lista2.setSelection(lista2.getCount() - 1);
                    }
                }else{
                    update_list_2(0,0);
                }

            }
        });
        //Tab Enviados Contactos
        tab3_ch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tab3_ch1.isChecked()){
                    tab3_ch1.setChecked(false);
                }
                if(tab3_ch2.isChecked()) {
                    list2 = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY contacto DESC", String.valueOf(Review.BORRADOR));
                    if (list2 != null) {
                        adaptador2 = new ReviewAdapter(BuzonesActivity.this, list2, show, selected_all);
                        lista2.setAdapter(adaptador2);
                        lista2.setSelection(lista2.getCount() - 1);
                    }
                }else{
                    list2 = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY contacto ASC", String.valueOf(Review.BORRADOR));
                    if (list2 != null) {
                        adaptador2 = new ReviewAdapter(BuzonesActivity.this, list2, show, selected_all);
                        lista2.setAdapter(adaptador2);
                        lista2.setSelection(lista2.getCount() - 1);
                    }
                }
            }
        });

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ic_menu_buzones, menu);
        eliminar = menu.findItem(R.id.buzones_eliminar);
        todos = menu.findItem(R.id.buzones_todos);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.buzones_eliminar:
                // TODO put your code here to respond to the button tap
                //todos.setVisible(true);
                    if(selected_tab.equals("mitab1")) {
                        String con[]=new String[2];
                        con[0]=String.valueOf(Review.POR_ENVIAR);
                        con[1]="1";
                        Review.deleteAll(Review.class,"estado = ? AND selected = ?",con);
                        show=1;
                        selected_all=0;
                        int cant=update_list_0(show, selected_all);
                        if(cant==0){
                            todos.setVisible(false);
                        }else{
                            todos.setVisible(true);
                        }
                        todos.setChecked(false);
                    }else if(selected_tab.equals("mitab2")) {
                        String con[]=new String[2];
                        con[0]=String.valueOf(Review.ENVIADO);
                        con[1]="1";
                        Review.deleteAll(Review.class,"estado = ? AND selected = ?",con);
                        show=1;
                        selected_all=0;
                        int cant=update_list_1(show, selected_all);
                        if(cant==0){
                                todos.setVisible(false);
                        }else{
                            todos.setVisible(true);
                        }
                        todos.setChecked(false);
                    }else if(selected_tab.equals("mitab3")) {
                        String con[]=new String[2];
                        con[0]=String.valueOf(Review.BORRADOR);
                        con[1]="1";
                        Review.deleteAll(Review.class,"estado = ? AND selected = ?",con);
                        show=1;
                        selected_all=0;
                        int cant=update_list_2(show, selected_all);
                        if(cant==0){
                            todos.setVisible(false);
                        }else{
                            todos.setVisible(true);
                        }
                        todos.setChecked(false);
                    }
               // }
                return true;
            case R.id.buzones_todos:
                // TODO put your code here to respond to the button tap
                if(item.isChecked()){
                    // If item already checked then unchecked it
                    item.setChecked(false);
                    if(selected_tab.equals("mitab1")) {
                        show=1;
                        selected_all=0;
                        update_list_0(show,selected_all);
                    }else if(selected_tab.equals("mitab2")) {
                        show=1;
                        selected_all=0;
                        update_list_1(show,selected_all);
                    }else if(selected_tab.equals("mitab3")) {
                        show=1;
                        selected_all=0;
                        update_list_2(show,selected_all);
                    }
                }else{
                    // If item is unchecked then checked it
                    item.setChecked(true);
                    if(selected_tab.equals("mitab1")) {
                        show=1;
                        selected_all=1;
                        update_list_0(show,selected_all);
                    }else if(selected_tab.equals("mitab2")) {
                        show=1;
                        selected_all=1;
                        update_list_1(show,selected_all);
                    }else if(selected_tab.equals("mitab3")) {
                        show=1;
                        selected_all=1;
                        update_list_2(show,selected_all);
                    }
                }

                return true;
            case R.id.enviar_todos:
                SendThread sendThread = new SendThread();
                sendThread.execute(BuzonesActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public int update_list_0(int show,int selected_all){
        list = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY fecha ASC",  String.valueOf(Review.POR_ENVIAR));
        if(list != null) {

            adaptador = new ReviewAdapter(BuzonesActivity.this, list, show, selected_all);
            lista.setAdapter(adaptador);
            lista.setSelection(lista.getCount() - 1);

        }
        return list.size();
    }
    public int update_list_1(int show,int selected_all){
        list1 = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY fecha ASC", String.valueOf(Review.ENVIADO));
        if (list1 != null) {

            adaptador1 = new ReviewAdapter(BuzonesActivity.this, list1, show, selected_all);
            lista1.setAdapter(adaptador1);
            lista1.setSelection(lista1.getCount() - 1);

        }
        return list1.size();
    }
    public int update_list_2(int show,int selected_all){
        list2 = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY fecha ASC", String.valueOf(Review.BORRADOR));
        if(list2 != null) {

            adaptador2 = new ReviewAdapter(BuzonesActivity.this, list2, show, selected_all);
            lista2.setAdapter(adaptador2);
            lista2.setSelection(lista2.getCount() - 1);

        }
        return  list2.size();
    }

    public void revisar_cant_messages(){
        if(selected_tab.equals("mitab1")) {
            List<Review> lista = new ArrayList<Review>();
            lista = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY fecha DESC", String.valueOf(Review.POR_ENVIAR));
            if (lista.size() > 55) {
                for (int i = 55; i < lista.size(); i++) {
                    lista.get(i).delete();
                }
            }
            Toast.makeText(BuzonesActivity.this, "Los mensajes antiguos han sido eliminados", Toast.LENGTH_SHORT).show();
        }
    }






    public boolean validar(String temp) {
        if (temp.length() > 0) {
            if (temp.startsWith(" ") || temp.startsWith("\n")) {
                return false;
            } else {
                if (temp.trim().length() > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    class SendThread extends AsyncTask<Context,Void,Boolean>
    {
        ProgressDialog progressDialog = null;
        @Override
        protected Boolean doInBackground(Context... params) {

            StringProcess sp = new WifiString(params[0]);

            return Sender.getInstance(params[0]).sendMessage(sp,Sender.SEND_FROM_DATA_BASE);
        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(BuzonesActivity.this);
            progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Enviando opiniones");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if(aBoolean == Boolean.TRUE) {
                Toast.makeText(BuzonesActivity.this, "Enviado satisfactoriamente", Toast.LENGTH_SHORT).show();
                List<Review> reviews = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ?", String.valueOf(Review.POR_ENVIAR));
                for(int i = 0; i < reviews.size(); i++){
                    reviews.get(i).setEstado(Review.ENVIADO);
                    reviews.get(i).save();
                }
                update_list_0(0, 0);
                update_list_1(0, 0);
            }else if(aBoolean == Boolean.FALSE){
                Toast.makeText(BuzonesActivity.this,"No se pudo enviar",Toast.LENGTH_SHORT).show();
            }

        }
    }


}
