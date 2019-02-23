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

import com.gcddm.contigo.db.Review;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by FC Bayern Munchen on 1/18/2017.
 */
public class ReviewAdapter extends ArrayAdapter<Review>{

    private Review elemento;
    private int show;
    private int select_all;

    private Context context;
    private List<Review> elementos;
   private int[] adjunto=new int[]{
            R.drawable.adjuntar
    };

    /*private class Holder {
        ImageView icono;
        TextView sender;
        TextView text;
        TextView time;
        CheckBox check;
    }*/

    public ReviewAdapter(Context context, List<Review> elementos,int show,int select_all){
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
        elemento = this.getItem(position);
        //Holder holder = null;

        ImageView icono;
        TextView sender;
        TextView text;
        TextView time;
        CheckBox check;


        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.message, null);
            //holder = new Holder();
            icono = (ImageView)convertView.findViewById(R.id.message_adjunto);
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


            convertView.setTag(new ReviewViewHolder(icono,sender,text,time,check));

            check.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v ;
                    Review review = (Review) cb.getTag();
                    review.setSelected(cb.isChecked());
                    review.save();
                    //elemento.setSelected(cb.isChecked());
                    //elemento.save();
                }
            });


        } else {
            //holder = (Holder) convertView.getTag();
            // Because we use a ViewHolder, we avoid having to call findViewById().
            ReviewViewHolder viewHolder = (ReviewViewHolder) convertView.getTag();
            icono=viewHolder.getIcono();
            sender=viewHolder.getSender();
            text=viewHolder.getText();
            time=viewHolder.getTime();
            check = viewHolder.getCheck();
        }

        check.setTag(elemento);



        String[] nombre;
        nombre=elemento.getContacto().getNombre_Apellido().split(" ");
        sender.setText(nombre[0]);
        text.setText(elemento.getAsunto()+"\n"+elemento.getMensaje());
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(elemento.getFecha());
        SimpleDateFormat sdf=new SimpleDateFormat("d MMM y h:mm a");
        String temp = sdf.format(cal.getTime());
        time.setText(temp);
        icono.setImageResource(adjunto[0]);
        if(!elemento.getAdjunto1().equals("") || !elemento.getAdjunto2().equals("") || !elemento.getAdjunto3().equals("")) {
            icono.setVisibility(View.VISIBLE);
        }else{
            icono.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (!constraint.toString().isEmpty()){
                    List<Review> filtro = new ArrayList<>();

                    for (int i = 0; i < elementos.size(); i++){
                        Review elemento = elementos.get(i);
                        Calendar cal=Calendar.getInstance();
                        cal.setTimeInMillis(elemento.getFecha());
                        SimpleDateFormat sdf=new SimpleDateFormat("d MMM y h:mm a");
                        String temp = sdf.format(cal.getTime());
                        if (elemento.getAsunto().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(elemento);
                        }else if (elemento.getContacto().getNombre_Apellido().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(elemento);
                        }else if (elemento.getContacto().getNickname().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(elemento);
                        }else if (elemento.getMensaje().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(elemento);
                        }/*else if (elemento.getTema().nombre.toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(elemento);
                        }else if (elemento.getTema_sec().nombre.toLowerCase().contains(constraint.toString().toLowerCase())){
                            filtro.add(elemento);
                        }*/else if(temp.toLowerCase().contains(constraint.toString().toLowerCase())){
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
                    List<Review> filtro = (List<Review>) results.values;
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
