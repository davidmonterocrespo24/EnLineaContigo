package com.gcddm.contigo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.gcddm.contigo.db.Review;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by FC Bayern Munchen on 1/18/2017.
 */
public class ContigoAdapter extends ArrayAdapter<Review>{

    private class Holder {
        ImageView icono;
        TextView sender;
        TextView title;
        TextView text;
        TextView time;
    }

    private Context context;
    private int[] colores=new int[]{
            R.drawable.info_p4,
    };

    public ContigoAdapter(Context context, List<Review> elementos){
        super(context, R.layout.message_item, elementos);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Review elemento = getItem(position);
        Holder holder = null;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.message_item, null);
            holder = new Holder();
            holder.icono = (ImageView)convertView.findViewById(R.id.inbox_message_avatar);
            holder.sender = (TextView) convertView.findViewById(R.id.inbox_message_sender);
            holder.title = (TextView) convertView.findViewById(R.id.inbox_message_title);
            holder.text = (TextView) convertView.findViewById(R.id.inbox_message_text);
            holder.time = (TextView) convertView.findViewById(R.id.inbox_message_time);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.sender.setText(elemento.getContacto().getNombre_Apellido());
        holder.title.setText(elemento.getAsunto());
        holder.text.setText(elemento.getMensaje());
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(elemento.getFecha());
        SimpleDateFormat sdf=new SimpleDateFormat("d-MMM-y h:mm a");
        String temp = sdf.format(cal.getTime());
        holder.time.setText(temp);
        holder.icono.setImageResource(colores[0]);
        return convertView;
    }
}
