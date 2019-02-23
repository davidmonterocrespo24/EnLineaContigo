package com.gcddm.contigo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.gcddm.contigo.Util.JuegoAdapter;
import com.gcddm.contigo.db.Juego;

import java.util.ArrayList;
import java.util.List;

public class JuegoActivity extends AppCompatActivity {
    public List<Juego> juego;
    EditText inputSearch;
    ListView listView;
    JuegoAdapter adapter;
    private ImageButton tab1_button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        setupActionBar();

        juego = Juego.listAll(Juego.class);
        adapter= new JuegoAdapter(JuegoActivity.this, juego);
        if(juego!=null){
            listView = (ListView)findViewById(R.id.listview_juego);
            listView.setEmptyView(findViewById(R.id.emptyListView));
            listView.setAdapter(adapter);
            listView.setEmptyView(findViewById(R.id.emptyListView));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Juego juego =adapter.getItem(position);

             Intent i=new Intent(JuegoActivity.this, Juego1Activity.class);
                    i.putExtra("Indice",0);
                    i.putExtra("Estado",juego.getEstado());
                    i.putExtra("id", juego.getId());
                    i.putExtra("Nombre_Juego",juego.getNombre_juego());
                    i.putStringArrayListExtra("Evaluacion",new ArrayList<String>());
                    i.putStringArrayListExtra("Respuestas",new ArrayList<String>());
                startActivity(i);

                }
            });
        }



          // Activando el filtro de busqueda
        inputSearch=(EditText)findViewById(R.id.search) ;
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                ((ArrayAdapter<String>)listView.getAdapter()).getFilter().filter(arg0);
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
                int cursorPosition = inputSearch.getSelectionStart();
                if (cursorPosition > 0) {
                    inputSearch.setText(inputSearch.getText().delete(cursorPosition - 1, cursorPosition));
                    inputSearch.setSelection(cursorPosition-1);
                }
            }
        });

    }
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.

            actionBar.setDisplayHomeAsUpEnabled(true);
            // Hide the action bar title

        }
    }


}
