package com.gcddm.contigo.Util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.gcddm.contigo.db.Imagen;
import com.gcddm.contigo.db.Info;
import com.gcddm.contigo.db.Juego;
import com.gcddm.contigo.db.Mpuntos;
import com.gcddm.contigo.db.PreguntaRespuestas;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by MIRTHA on 29/11/2016.
 */
public class UpdateTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog3;
    private AlertDialog.Builder dialog2;
    private AlertDialog alertDialog;
    private static boolean borrar = false;
    private static boolean process = false;
    private String params;
    public UpdateTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        List<Mpuntos> mpuntoses = Mpuntos.listAll(Mpuntos.class);
        List<Juego> juego = Juego.listAll(Juego.class);
        List<Info> informacion = Info.listAll(Info.class);
        if(!mpuntoses.isEmpty() || !juego.isEmpty() || !informacion.isEmpty()){
            dialog2 = new AlertDialog.Builder(context);
            dialog2.setTitle("Información");
            dialog2.setMessage("Desea mantener todo el contenido de la aplicación?");
            dialog2.setIcon(android.R.drawable.ic_dialog_info);
            dialog2.setCancelable(false);
            dialog2.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    borrar = false;
                    process = true;
                    dialog3 = new ProgressDialog(context);
                    dialog3.setTitle("Espere por favor");
                    dialog3.setMessage("Actualizando.....");
                    dialog3.setIndeterminate(true);
                    dialog3.setIcon(android.R.drawable.ic_dialog_info);
                    dialog3.setCancelable(false);
                    dialog3.show();
                }
            });
            dialog2.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    borrar = true;
                    process = true;
                    dialog3 = new ProgressDialog(context);
                    dialog3.setTitle("Espere por favor");
                    dialog3.setMessage("Actualizando.....");
                    dialog3.setIndeterminate(true);
                    dialog3.setIcon(android.R.drawable.ic_dialog_info);
                    dialog3.setCancelable(false);
                    dialog3.show();

                }
            });
            alertDialog = dialog2.create();
            alertDialog.show();
        }else if(mpuntoses.isEmpty() && juego.isEmpty() && informacion.isEmpty()){
            dialog3 = new ProgressDialog(context);
            dialog3.setTitle("Espere por favor");
            dialog3.setMessage("Actualizando.....");
            dialog3.setIndeterminate(true);
            dialog3.setIcon(android.R.drawable.ic_dialog_info);
            dialog3.setCancelable(false);
            dialog3.show();
            process = true;
        }
    }

    @Override
    protected Boolean doInBackground(String... params) {
        while (process==false);

        this.params= params[0];
        InputStream inputStream = descomprimir(this.params);
        File zipFile = new File(this.params);
        File tmpFile = new File(zipFile.getParent() + File.separator + "tmp");
        if(borrar == true){
            if(inputStream != null) {
                ParsearXml parsearXml = new ParsearXml();
                try {
                    List<String> strings = parsearXml.buscarConenido(inputStream,context,tmpFile);
                    for(int i = 0; i< strings.size(); i++){
                        if (strings.get(i).equals(ParsearXml.ETIQUETA_INFOS)){
                            Eliminar_Infos();
                        }
                        if (strings.get(i).equals(ParsearXml.ETIQUETA_JUEGOS)){
                            Eliminar_Juegos();
                        }
                        if (strings.get(i).equals(ParsearXml.ETIQUETA_MAPA)){
                            Eliminar_Mpuntos();
                        }
                    }
                    if(parsearXml.parsear(inputStream,context,tmpFile)){
                        //se pudo actualizar
                        //Borrar todos los archivos de la carpeta
                        for (File archivo : tmpFile.listFiles()) {
                            archivo.delete();
                        }

                        tmpFile.delete();
                        return true;
                    }else{
                        //fecha incorrecta
                        //Borrar todos los archivos de la carpeta
                        for (File archivo : tmpFile.listFiles()) {
                            archivo.delete();
                        }

                        tmpFile.delete();
                        return false;
                    }
                } catch (XmlPullParserException e) {
                    Toast.makeText(context, "Error al leer archivo de actualizacion", Toast.LENGTH_LONG).show();
                    cancel(true);
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(context, "Error al leer archivo de actualizacion", Toast.LENGTH_LONG).show();
                    cancel(true);
                    e.printStackTrace();
                }
            }

        }else{
            if(inputStream != null){

                ParsearXml parsearXml = new ParsearXml();
                try {
                    if(parsearXml.parsear(inputStream,context,tmpFile)){
                        //se pudo actualizar
                        //Borrar todos los archivos de la carpeta
                        for (File archivo : tmpFile.listFiles()) {
                            archivo.delete();
                        }

                        tmpFile.delete();
                        return true;
                    }else{
                        //fecha incorrecta
                        //Borrar todos los archivos de la carpeta
                        for (File archivo : tmpFile.listFiles()) {
                            archivo.delete();
                        }

                        tmpFile.delete();
                        return false;
                    }
                } catch (XmlPullParserException e) {
                    Toast.makeText(context, "Error al leer archivo de actualizacion", Toast.LENGTH_LONG).show();
                    cancel(true);
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(context, "Error al leer archivo de actualizacion", Toast.LENGTH_LONG).show();
                    cancel(true);
                    e.printStackTrace();
                }
            }
        }



        return false;
    }

    @Override
    protected void onCancelled() {
        if(alertDialog != null){
            alertDialog.dismiss();
        }
        if(dialog3 != null){
            dialog3.dismiss();

        }

        borrar = false;
        process = false;

        super.onCancelled();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(alertDialog != null) {
            alertDialog.dismiss();
        }
        if(dialog3 != null){
            dialog3.dismiss();

        }

        borrar = false;
        process = false;
        if(aBoolean == Boolean.FALSE) {
            new AlertDialog.Builder(context)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("No se puede actualizar")
                    .setMessage("La actualización que desea cargar es inferiror a la que ya está en el sistema")
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }else if(aBoolean == Boolean.TRUE){
            Toast.makeText(context,"Se ha actualizado correctamente", Toast.LENGTH_SHORT).show();
        }


    }


    private InputStream descomprimir (String fileName){
        File zipFile = new File(fileName);
        File tmpFile = new File(zipFile.getParent() + File.separator + "tmp");

        try {
            //descomprimir el fichero
            Comprimido.unZipFiles(zipFile, zipFile.getParent(), tmpFile);

            File xmlFile = null;

            for (File archivo : tmpFile.listFiles()) {
                if (archivo.getName().endsWith(".xml")) {
                    xmlFile = archivo;
                    break;
                }
            }

            if (xmlFile == null){
                Toast.makeText(context, "No se encontro el archivo XML", Toast.LENGTH_LONG).show();
                return null;
            }

            InputStream xmlStream = new FileInputStream(xmlFile);


            return xmlStream;

        } catch (Exception e){
            Toast.makeText(context, "Error al descomprimir archivo", Toast.LENGTH_LONG).show();
            this.cancel(true);
            return null;
        }


    }


    public void Eliminar_Infos(){
        File movieFile = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES + File.separator);
        File imagenFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator);

        if(movieFile!=null){
            for (File file : movieFile.listFiles()){
                file.delete();
            }
        }
        if(imagenFile!=null){
            for (File file : imagenFile.listFiles()){
                file.delete();
            }
        }
//      BORRAR LAS TABLAS
        Info.deleteAll(Info.class);
        Imagen.deleteAll(Imagen.class);
    }
    public void Eliminar_Juegos(){
        Juego.deleteAll(Juego.class);
        PreguntaRespuestas.deleteAll(PreguntaRespuestas.class);
    }

    public void Eliminar_Mpuntos(){
        Mpuntos.deleteAll(Mpuntos.class);
    }

}
