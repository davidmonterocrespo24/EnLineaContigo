package com.gcddm.contigo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcddm.contigo.db.MensajeRecibido;
import com.gcddm.contigo.db.Review;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by FC Bayern Munchen on 1/18/2017.
 */
public class MensajeAdapter extends ArrayAdapter<MensajeRecibido>{

    MensajeRecibido elemento;
    private int show;
    private int select_all;

    private Context context;
    private List<MensajeRecibido> elementos;
    /*private int[] colores=new int[]{
            R.drawable.notificacion_para_funcionario_50_copia
    };*/

    /*private class Holder {
        ImageView icono;
        TextView sender;
        TextView text;
        TextView time;
        CheckBox check;
    }*/



    public MensajeAdapter(Context context, List<MensajeRecibido> elementos,int show,int select_all){
        super(context, R.layout.message, elementos);
        this.context = context;
        this.elementos = new ArrayList<>();
        this.elementos.addAll(elementos);
        if(select_all==1) {
            for (int i = 0; i < elementos.size(); i++) {
                if(!elementos.get(i).isSelected()) {
                    elementos.get(i).setSelected(true);
                    elementos.get(i).save();
                }
            }
        }else{
            for (int i = 0; i < elementos.size(); i++) {
                if(elementos.get(i).isSelected()) {
                    elementos.get(i).setSelected(false);
                    elementos.get(i).save();
                }
            }
        }
        this.show=show;
        this.select_all=select_all;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        elemento = getItem(position);
        //Holder holder = null;

        //ImageView icono;
        TextView sender;
        TextView text;
        TextView time;
        CheckBox check;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.message, null);
            //holder = new Holder();
            //icono = (ImageView)convertView.findViewById(R.id.message_thumbnail);
            sender = (TextView) convertView.findViewById(R.id.message_sender);
            text = (TextView) convertView.findViewById(R.id.message_body);
            time = (TextView) convertView.findViewById(R.id.message_date);
            check = (CheckBox) convertView.findViewById(R.id.message_check);
            if(this.show==1){
                check.setVisibility(convertView.VISIBLE);
            }
            if(this.select_all==1){
                check.setChecked(true);
            }

            //convertView.setTag(holder);
            convertView.setTag(new MensajeViewHolder(sender,text,time,check));

            check.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    MensajeRecibido men = (MensajeRecibido) cb.getTag();
                    men.setSelected(cb.isChecked());
                    men.save();

                    //elemento.setSelected(cb.isChecked());
                    //elemento.save();
                }
            });

        } else {
            //holder = (Holder) convertView.getTag();
            MensajeViewHolder viewHolder = (MensajeViewHolder) convertView.getTag();
            //icono=viewHolder.getIcono();
            sender=viewHolder.getSender();
            text=viewHolder.getText();
            time=viewHolder.getTime();
            check = viewHolder.getCheck();
        }

        check.setTag(elemento);

        sender.setText(elemento.getRemitente());
        text.setText(elemento.getTexto());
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(elemento.getFecha());
        SimpleDateFormat sdf=new SimpleDateFormat("d MMM y h:mm a");
        String temp = sdf.format(cal.getTime());
        time.setText(temp);
        //icono.setImageResource(colores[0]);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (!constraint.toString().isEmpty()){
                    List<MensajeRecibido> filtro = new ArrayList<>();

                    for (int i = 0; i < elementos.size(); i++){
                        MensajeRecibido elemento = elementos.get(i);
                        Calendar cal=Calendar.getInstance();
                        cal.setTimeInMillis(elemento.getFecha());
                        SimpleDateFormat sdf=new SimpleDateFormat("d MMM y h:mm a");
                        String temp = sdf.format(cal.getTime());

                        if (elemento.getRemitente().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(elemento);
                        }else if (elemento.getTexto().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(elemento);
                        }else if(temp.toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(elemento);
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
                    List<MensajeRecibido> filtro = (List<MensajeRecibido>) results.values;
                    addAll(filtro);
                } else {
                    addAll(elementos);
                }
            }
        };
    }


    public Object onRetainNonConfigurationInstance() {
        return elementos ;
    }
}
