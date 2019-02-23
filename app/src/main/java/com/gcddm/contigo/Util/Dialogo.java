package com.gcddm.contigo.Util;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by MIRTHA on 01/12/2016.
 */
public class Dialogo {

    public static void dialogoError(Context contexto, String titulo, String mensaje){
        new AlertDialog.Builder(contexto)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(titulo)
                .setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton("OK",null)
                .show();
    }
}
