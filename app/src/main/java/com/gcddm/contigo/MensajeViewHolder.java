package com.gcddm.contigo;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Germany on 12-may-17.
 */
public class MensajeViewHolder{ /** Holds child views for one row. */

    //ImageView icono;
    TextView sender;
    TextView text;
    TextView time;
    CheckBox check;

    public MensajeViewHolder() {}

    public MensajeViewHolder( TextView sender, TextView text, TextView time, CheckBox check) {
        //this.icono = icono;
        this.sender = sender;
        this.text = text;
        this.time = time;
        this.check = check;
    }

    /*public ImageView getIcono() {
        return icono;
    }

    public void setIcono(ImageView icono) {
        this.icono = icono;
    }*/

    public TextView getSender() {
        return sender;
    }

    public void setSender(TextView sender) {
        this.sender = sender;
    }

    public TextView getText() {
        return text;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }
}