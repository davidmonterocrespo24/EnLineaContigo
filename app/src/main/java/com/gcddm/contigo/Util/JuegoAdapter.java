package com.gcddm.contigo.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcddm.contigo.R;
import com.gcddm.contigo.db.Juego;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIRTHA on 15/05/2017.
 */
public class JuegoAdapter extends ArrayAdapter<Juego> implements Filterable {
    private class Holder {
        ImageView icono;
        TextView nombre;
    }

    private Context context;
    private List<Juego> juegos;

    public JuegoAdapter(Context context, List<Juego> juegos){
        super(context, R.layout.juego_fila, juegos);
        this.context = context;
        this.juegos = new ArrayList<>();
        this.juegos.addAll(juegos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Juego juego = getItem(position);
        Holder holder = null;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.juego_fila, null);
            holder = new Holder();
            holder.icono = (ImageView)convertView.findViewById(R.id.icon_juego);
            holder.nombre = (TextView) convertView.findViewById(R.id.nombre_juego);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (juegos.get(position).getEstado().equals("on")){
            holder.icono.setImageResource(R.drawable.on);
        }
        else{
            holder.icono.setImageResource(R.drawable.off);
        }

        holder.nombre.setText(juego.getNombre_juego());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (!constraint.toString().isEmpty()){
                    List<Juego> filtro = new ArrayList<>();

                    for (int i = 0; i < juegos.size(); i++){
                        Juego juego = juegos.get(i);

                        if (juego.getNombre_juego().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(juego);
                        }
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.count = filtro.size();
                    filterResults.values = filtro;
                    return filterResults;
                }

                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();

                if (results != null){
                    List<Juego> filtro = (List<Juego>) results.values;
                    addAll(filtro);
                } else {
                    addAll(juegos);
                }
            }
        };
    }
}
