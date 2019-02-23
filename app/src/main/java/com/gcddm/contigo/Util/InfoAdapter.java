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
import com.gcddm.contigo.db.Info;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIRTHA on 15/05/2017.
 */
public class InfoAdapter extends ArrayAdapter<Info> implements Filterable {
    private class Holder {
        ImageView icono;
        TextView nombre;
    }

    private Context context;
    private List<Info> infos;

    public InfoAdapter(Context context, List<Info> infos){
        super(context, R.layout.info_fila, infos);
        this.context = context;
        this.infos = new ArrayList<>();
        this.infos.addAll(infos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Info info = getItem(position);
        Holder holder = null;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.info_fila, null);
            holder = new Holder();
            holder.icono = (ImageView)convertView.findViewById(R.id.icon_info);
            holder.nombre = (TextView) convertView.findViewById(R.id.nombre_info);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.nombre.setText(info.getNombre_info());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (!constraint.toString().isEmpty()){
                    List<Info> filtro = new ArrayList<>();

                    for (int i = 0; i < infos.size(); i++){
                        Info info = infos.get(i);

                        if (info.getNombre_info().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(info);
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
                    List<Info> filtro = (List<Info>) results.values;
                    addAll(filtro);
                } else {
                    addAll(infos);
                }
            }
        };
    }
}
