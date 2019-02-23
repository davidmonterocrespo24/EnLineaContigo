package com.gcddm.contigo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.gcddm.contigo.Util.JuegoAdapter1;
import com.gcddm.contigo.db.Juego;
import com.gcddm.contigo.db.PreguntaRespuestas;

import java.util.List;

public class EvaluacionActivity extends AppCompatActivity {
    List<String> evaluacion;
    List<String>respuestas;
    ListView listView;
    RatingBar ratingBar;
    int cont=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion);

        listView=(ListView)findViewById(R.id.listView);
        /*ratingBar=(RatingBar)findViewById(R.id.rating);
        Drawable drawable = ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#03abff"), PorterDuff.Mode.SRC_ATOP);*/

        Intent intent=getIntent();
        evaluacion=intent.getStringArrayListExtra("Evaluacion");
        respuestas=intent.getStringArrayListExtra("Respuestas");
        long id=intent.getLongExtra("id",0);

        Juego juego = Juego.findById(Juego.class,id);

        final List<PreguntaRespuestas> list = juego.getPreguntas();
        listView.setAdapter(new JuegoAdapter1(this,evaluacion,list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String res = respuestas.get(position);
                Snackbar.make(view, res, Snackbar.LENGTH_SHORT).show();
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
                Intent i = new Intent(EvaluacionActivity.this, InicioActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
