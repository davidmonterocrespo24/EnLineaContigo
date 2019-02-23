package com.gcddm.contigo;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Germany on 18-may-17.
 */
public class ServicioAdapter extends ArrayAdapter<Servicio> implements Filterable {

    String carpeta_fuente="fonts/TCCB.TTF";
    Typeface fuente;

    private class Holder {
        LinearLayout linear;
        ImageView icono;
        TextView nombre;
    }

    private int[] colores=new int[]{
            R.drawable.consultar_saldo,
            R.drawable.recargar_saldo,
            R.drawable.transferir_saldo,
            R.drawable.planes_etecsa,
            R.drawable.qr_icon,
            R.drawable.contratar_plan,
            R.drawable.plan_sms,
            R.drawable.plan_voz,
            R.drawable.plan_amigos,
            R.drawable.bolsa_nauta
    };

    private int[] fondos=new int[]{
            R.drawable.rounded_corner_consultar_saldo,
            R.drawable.rounded_corner_recargar_saldo,
            R.drawable.rounded_corner_transferir_saldo,
            R.drawable.rounded_corner_planes_etecsa,
            R.drawable.rounded_corner_qr_icon
    };

    private Context context;
    private List<Servicio> servicios;

    public ServicioAdapter(Context context, List<Servicio> servicios){
        super(context, R.layout.service_item, servicios);
        this.context = context;
        this.servicios = new ArrayList<>();
        this.servicios.addAll(servicios);
        fuente= Typeface.createFromAsset(context.getAssets(),carpeta_fuente);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Servicio servicio = getItem(position);
        Holder holder = null;



        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.service_item, null);
            holder = new Holder();
            holder.linear = (LinearLayout) convertView.findViewById(R.id.service_linear);
            holder.icono = (ImageView)convertView.findViewById(R.id.service_icon);
            holder.nombre = (TextView) convertView.findViewById(R.id.service_name);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.linear.setBackgroundResource(fondos[servicio.getFondo()]);
        holder.icono.setImageResource(colores[servicio.getImagen()]);
        holder.nombre.setText(servicio.getNombre());
        if (holder.nombre!=null){
            holder.nombre.setTypeface(fuente);
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (!constraint.toString().isEmpty()){
                    List<Servicio> filtro = new ArrayList<>();

                    for (int i = 0; i < servicios.size(); i++){
                        Servicio servicio = servicios.get(i);

                        if (servicio.getNombre().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(servicio);
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
                    List<Servicio> filtro = (List<Servicio>) results.values;
                    addAll(filtro);
                } else {
                    addAll(servicios);
                }
            }
        };
    }
}