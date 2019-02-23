package com.gcddm.contigo.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.gcddm.contigo.R;
import com.gcddm.contigo.db.Juego;
import com.gcddm.contigo.db.PreguntaRespuestas;

import java.util.List;

/**
 * Created by MIRTHA on 12/12/2016.
 */
public class JuegoAdapter1 extends ArrayAdapter<String> {

    private List<String>evaluacion;
    private List<PreguntaRespuestas> pr;
    Context context;

    public class Holder{
        ImageView imagen;
        TextView texto;
    }

    public JuegoAdapter1(Context context, List<String>evaluacion, List<PreguntaRespuestas>pr ) {
        super(context, R.layout.evaluacion_fila,evaluacion);

        this.evaluacion=evaluacion;
        this.pr=pr;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.evaluacion_fila,null);
            holder=new Holder();
            holder.imagen=(ImageView) convertView.findViewById(R.id.imagen);
            holder.texto=(TextView) convertView.findViewById(R.id.texto);

            convertView.setTag(holder);
        }
        else{
            holder=(Holder)convertView.getTag();
        }
        if(evaluacion.get(position).equals("true")){
            holder.imagen.setImageResource(R.drawable.ic_done_black_24dp);
        }
        else{
            holder.imagen.setImageResource(R.drawable.ic_clear_black_24dp);
        }
        holder.texto.setText(pr.get(position).getPregunta());
        return convertView;
    }
}
