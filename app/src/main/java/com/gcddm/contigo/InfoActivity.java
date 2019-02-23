package com.gcddm.contigo;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.gcddm.contigo.Util.InfoAdapter;
import com.gcddm.contigo.db.Info;
import com.gcddm.contigo.db.Review;

import java.util.List;

public class InfoActivity extends AppCompatActivity {
    public List<Info> info;
    EditText inputSearch;
    ListView listView;
    InfoAdapter adapter;
    ImageButton delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setupActionBar();

        System.gc();

        info = Info.find(Info.class,null);
        adapter= new InfoAdapter(InfoActivity.this, info);

        listView = (ListView)findViewById(R.id.listview_info1);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.emptyListView));

        delete = (ImageButton) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cursorPosition = inputSearch.getSelectionStart();
                if (cursorPosition > 0) {
                    inputSearch.setText(inputSearch.getText().delete(cursorPosition - 1, cursorPosition));
                    inputSearch.setSelection(cursorPosition-1);
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Info info =adapter.getItem(position);

                Intent i=new Intent(InfoActivity.this, InfoContenidoActivity.class);
                i.putExtra("id", info.getId());
                startActivity(i);
            }
        });


                /* Activando el filtro de busqueda */
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
