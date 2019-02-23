package com.gcddm.contigo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gcddm.contigo.Util.AdjuntosAdapter;
import com.gcddm.contigo.Util.NumeroAleatorio;
import com.gcddm.contigo.db.Contacto;
import com.gcddm.contigo.db.Juego;
import com.gcddm.contigo.db.PreguntaRespuestas;
import com.gcddm.contigo.db.Review;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Juego1Activity extends AppCompatActivity {
    Button boton;
    private long idJuego;
    int indice;
    String estado;
    ArrayList<String> evaluacion;
    ArrayList<String> respuestas;
    private List<Contacto> contacto;
    ContactDialogManager conact;
    int item = 0;
    LinearLayout linearLayout;
    RadioButton rbresp_correcta;
    RadioButton rbresp_incorrecta1;
    RadioButton rbresp_incorrecta2;
    String nombre_juego = "";
    Boolean ganador = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego1);

        setupActionBar();

        System.gc();

        Intent intent = getIntent();
        idJuego = intent.getLongExtra("id", -1);
        estado= intent.getStringExtra("Estado");
        nombre_juego=intent.getStringExtra("Nombre_Juego");
        indice = intent.getIntExtra("Indice", -1);
        evaluacion = intent.getStringArrayListExtra("Evaluacion");
        respuestas = intent.getStringArrayListExtra("Respuestas");

        TextView pregunta = (TextView) findViewById(R.id.textView6);
        TextView nombre = (TextView)findViewById(R.id.nombre_juego);
        rbresp_correcta = (RadioButton) findViewById(R.id.rbBien);
        rbresp_incorrecta1 = (RadioButton) findViewById(R.id.rbMal1);
        rbresp_incorrecta2 = (RadioButton) findViewById(R.id.rbMal2);
        linearLayout = (LinearLayout)findViewById(R.id.siguiente);

        linearLayout.setVisibility(View.GONE);
        boton = (Button) findViewById(R.id.boton_juego);
        conact = new ContactDialogManager(Juego1Activity.this,getApplicationContext());

        Juego juego = Juego.findById(Juego.class,idJuego);

        final List<PreguntaRespuestas> list = juego.getPreguntas();
        int cant=list.size();
        if(indice == list.size()-1){
            if(estado.equals("on"))
                boton.setText("Enviar");
            else
                boton.setText("Resultado");
        }
        int n=0;
        NumeroAleatorio na = new NumeroAleatorio(0,2);
       List<String> elementos=new ArrayList<>();
        if(n<list.size()){
                nombre.setText(nombre_juego);
                pregunta.setText(list.get(indice).getPregunta());
                elementos.add(list.get(indice).getRespuesta_correcta());
                elementos.add(list.get(indice).getRespuesta_incorrecta1());
                elementos.add(list.get(indice).getRespuesta_incorrecta2());


                       rbresp_correcta.setText(elementos.get(na.generar()));

                       rbresp_incorrecta1.setText(elementos.get(na.generar()));

                       rbresp_incorrecta2.setText(elementos.get(na.generar()));


            n++;
            elementos.clear();
                }

        rbresp_correcta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbresp_correcta.getText().equals(list.get(indice).getRespuesta_correcta())){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rbresp_correcta.setBackground(getResources().getDrawable(R.drawable.juego_layout_true));
                    }
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rbresp_correcta.setBackground(getResources().getDrawable(R.drawable.juego_layout_false));
                    }
                }
                rbresp_incorrecta1.setVisibility(View.GONE);
                rbresp_incorrecta2.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

        rbresp_incorrecta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbresp_incorrecta1.getText().equals(list.get(indice).getRespuesta_correcta())){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rbresp_incorrecta1.setBackground(getResources().getDrawable(R.drawable.juego_layout_true));
                    }
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rbresp_incorrecta1.setBackground(getResources().getDrawable(R.drawable.juego_layout_false));
                    }
                }
                rbresp_correcta.setVisibility(View.GONE);
                rbresp_incorrecta2.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });

        rbresp_incorrecta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbresp_incorrecta2.getText().equals(list.get(indice).getRespuesta_correcta())){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rbresp_incorrecta2.setBackground(getResources().getDrawable(R.drawable.juego_layout_true));
                    }
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        rbresp_incorrecta2.setBackground(getResources().getDrawable(R.drawable.juego_layout_false));
                    }
                }
                rbresp_incorrecta1.setVisibility(View.GONE);
                rbresp_correcta.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                respuestas.add(list.get(indice).getRespuesta_correcta());


                if(rbresp_correcta.isChecked() && rbresp_correcta.getText().equals(list.get(indice).getRespuesta_correcta())){
                    evaluacion.add("true");
                }else if(rbresp_incorrecta1.isChecked() && rbresp_incorrecta1.getText().equals(list.get(indice).getRespuesta_correcta())){
                    evaluacion.add("true");
                }
                else if(rbresp_incorrecta2.isChecked() && rbresp_incorrecta2.getText().equals(list.get(indice).getRespuesta_correcta())){
                    evaluacion.add("true");
                }
                else{
                    evaluacion.add("false");
                }

                if (indice < list.size()-1) {
                    Intent intent1 = new Intent(Juego1Activity.this, Juego1Activity.class);
                    intent1.putExtra("id", idJuego);
                    intent1.putExtra("Indice", ++indice);
                    intent1.putExtra("Estado",estado);
                    intent1.putExtra("Nombre_Juego",nombre_juego);
                    intent1.putStringArrayListExtra("Evaluacion", evaluacion);
                    intent1.putStringArrayListExtra("Respuestas",respuestas);
                    startActivity(intent1);
                    Juego1Activity.this.finish();

                } else {

                    if (estado.equals("on")) {
                        final String[] items = new String[3];
                        items[0] = "SMS";
                        items[1] = "Correo";
                        items[2] = "Wifi";
                        new AlertDialog.Builder(Juego1Activity.this)
                            .setTitle("Enviar por")
                            .setCancelable(false)
                            .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    Juego1Activity.this.item = item;

                                }
                            })
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int item) {
                                    contacto = Contacto.listAll(Contacto.class);
                                    if (contacto.isEmpty()) {
                                        Toast.makeText(getApplicationContext(), "Debe configurar su contacto", Toast.LENGTH_LONG).show();
                                        conact.MostraDialog();
                                    } else {
                                        Contacto contacto1 = contacto.get(0);
                                        StringProcess stringProcess = null;

                                        for (int i = 0; i < evaluacion.size(); i++) {
                                            if (evaluacion.get(i).equals("false")) {
                                                ganador = false;
                                            }

                                        }
                                        switch (Juego1Activity.this.item) {
                                            case 0:
                                                //enviar por sms
                                                stringProcess = new SmsString();
                                                stringProcess.setAddress(contacto1.getDireccion());
                                                stringProcess.setName(contacto1.getNombre_Apellido());
                                                stringProcess.setNickname(contacto1.getNickname());
                                                stringProcess.setPhone(String.valueOf(contacto1.getTelefono()));
                                                stringProcess.setReceptor(String.valueOf(InicioActivity.PHONE_NUMBER));
                                                stringProcess.setBody(nombre_juego + ganador);
                                                stringProcess.setAsunto("Juego");

                                                Sender.getInstance(Juego1Activity.this).sendMessage(stringProcess, Sender.SEND_ALL);
                                                Toast.makeText(getApplicationContext(), "Sus respuestas han sido enviadas", Toast.LENGTH_SHORT).show();
                                                break;
                                            case 1:
                                                if (!contacto1.getCorreo().matches("")) {
                                                    //enviar por correo

                                                    SendEmail sendThread = new SendEmail();
                                                    sendThread.execute(Juego1Activity.this);
                                                }else{
                                                    Toast.makeText(getApplicationContext(), "Usted no tiene correo configurado", Toast.LENGTH_LONG).show();
                                                }
                                                break;
                                            case 2:
                                                // enviar por Wifi
                                                SendThread sendThread = new SendThread();
                                                sendThread.execute(Juego1Activity.this);
                                                break;

                                        }

                                    }

                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                    } else {
                        //cuando se acaban los juegos si es offline
                        if(evaluacion.contains("false")){
                            //perdio el juego
                            android.app.AlertDialog.Builder builder2 = new android.app.AlertDialog.Builder(Juego1Activity.this);

                            builder2.setCancelable(false);

                            LayoutInflater inflater2 = Juego1Activity.this.getLayoutInflater();
                            View alertLayout2 = inflater2.inflate(R.layout.final_juego_layout, null);

                            android.app.AlertDialog alertDialog2 = builder2.setView(alertLayout2).create();

                            Button resultados = (Button) alertLayout2.findViewById(R.id.resultados);

                            Button salir = (Button) alertLayout2.findViewById(R.id.salir);
                            TextView textView = (TextView) alertLayout2.findViewById(R.id.textView4);
                            textView.setText("Has perdido");

                            TextView textView2 = (TextView) alertLayout2.findViewById(R.id.textView5);
                            textView2.setText("Lo siento");

                            ImageView imageView = (ImageView) alertLayout2.findViewById(R.id.photo);
                            imageView.setRotation(180);

                            resultados.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(Juego1Activity.this, EvaluacionActivity.class);
                                    intent1.putStringArrayListExtra("Evaluacion", evaluacion);
                                    intent1.putStringArrayListExtra("Respuestas",respuestas);
                                    intent1.putExtra("id",idJuego);

                                    startActivity(intent1);
                                    Juego1Activity.this.finish();
                                }
                            });

                            salir.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Juego1Activity.this.finish();
                                }
                            });
                            alertDialog2.show();
                        }else {
                            android.app.AlertDialog.Builder builder3 = new android.app.AlertDialog.Builder(Juego1Activity.this);

                            builder3.setCancelable(false);

                            LayoutInflater inflater3 = Juego1Activity.this.getLayoutInflater();
                            View alertLayout3 = inflater3.inflate(R.layout.final_juego_layout, null);

                            android.app.AlertDialog alertDialog3 = builder3.setView(alertLayout3).create();

                            Button resultados = (Button) alertLayout3.findViewById(R.id.resultados);
                            Button salir = (Button) alertLayout3.findViewById(R.id.salir);

                            resultados.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent1 = new Intent(Juego1Activity.this, EvaluacionActivity.class);
                                    intent1.putStringArrayListExtra("Evaluacion", evaluacion);
                                    intent1.putStringArrayListExtra("Respuestas",respuestas);
                                    intent1.putExtra("id",idJuego);

                                    startActivity(intent1);
                                    Juego1Activity.this.finish();
                                }
                            });

                            salir.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Juego1Activity.this.finish();
                                }
                            });
                            alertDialog3.show();
                        }


                    }
                }

            }



        });
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
                Intent i = new Intent(Juego1Activity.this, InicioActivity.class);
                startActivity(i);
                Juego1Activity.this.finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.

            actionBar.setDisplayHomeAsUpEnabled(true);
            // Hide the action bar title

        }
    }

    class SendThread extends AsyncTask<Context,Void,Boolean>
    {
        ProgressDialog progressDialog;
        @Override
        protected Boolean doInBackground(Context... params) {
            Contacto contacto1 = contacto.get(0);
            StringProcess sp = new WifiString(params[0]);
            sp.setPhone(String.valueOf(contacto1.getTelefono()));
            sp.setNickname(contacto1.getNickname());
            sp.setName(contacto1.getNombre_Apellido());
            sp.setAddress(contacto1.getDireccion());
            sp.setBody(nombre_juego + ganador);

            return Sender.getInstance(params[0]).sendMessage(sp,Sender.SEND_ALL);



        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Juego1Activity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Enviando");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if(aBoolean == Boolean.TRUE) {
                Toast.makeText(Juego1Activity.this, "Enviado satisfactoriamente", Toast.LENGTH_SHORT).show();


            }else if(aBoolean == Boolean.FALSE){
                Toast.makeText(Juego1Activity.this,"No se pudo enviar",Toast.LENGTH_SHORT).show();


            }
            //super.onPostExecute(aBoolean);
        }
    }

    class SendEmail extends AsyncTask<Context,Void,Boolean>
    {
        ProgressDialog progressDialog;
        @Override
        protected Boolean doInBackground(Context... params) {
            Contacto contacto1 = contacto.get(0);
            StringProcess stringProcess = new MailString();
            stringProcess.setAddress(contacto1.getDireccion());
            stringProcess.setName(contacto1.getNombre_Apellido());
            stringProcess.setNickname(contacto1.getNickname());
            stringProcess.setPhone(String.valueOf(contacto1.getTelefono()));
            stringProcess.setBody(nombre_juego + ganador);
            stringProcess.setReceptor(InicioActivity.EMAIL_ADDRES);
            stringProcess.setEmail(contacto1.getCorreo());
            stringProcess.setEmailPassword(contacto1.getPassword());

            return Sender.getInstance(Juego1Activity.this).sendMessage(stringProcess, Sender.SEND_ALL);

        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Juego1Activity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Enviando");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.show();
            //super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if(aBoolean == Boolean.TRUE) {
                Toast.makeText(Juego1Activity.this, "Enviado satisfactoriamente", Toast.LENGTH_SHORT).show();

            }else if(aBoolean == Boolean.FALSE){
                Toast.makeText(Juego1Activity.this,"No se pudo enviar",Toast.LENGTH_SHORT).show();


            }
            //super.onPostExecute(aBoolean);
        }
    }


}
