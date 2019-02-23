package com.gcddm.contigo.db;

import com.orm.SugarRecord;

/**
 * Created by Germany on 09-may-17.
 * @Table
 */
public class Contacto extends SugarRecord {
    String nickname;
    String nombre_apellido;
    String municipio;
    int telefono;
    String direccion;
    String correo;
    String imagen;
    String password;


    public Contacto(String nickname, String nombre_apellido, int telefono, String direccion, String correo, String municipio, String imagen, String password) {
        this.nickname = nickname;
        this.nombre_apellido = nombre_apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.municipio = municipio;
        this.imagen = imagen;
        this.password = password;

    }

    public Contacto() {
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre_Apellido() {
        return nombre_apellido;
    }

    public void getNombre_Apellido(String nombre) {
        this.nombre_apellido = nombre;
    }


    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMunicipio() {
        return municipio;
    }


    public void setNombre_apellido(String nombre_apellido) {
        this.nombre_apellido = nombre_apellido;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre_apellido() {
        return nombre_apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
