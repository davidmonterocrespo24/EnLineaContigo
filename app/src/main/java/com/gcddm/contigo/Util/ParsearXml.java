package com.gcddm.contigo.Util;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;

import com.gcddm.contigo.db.FechaActualizacion;
import com.gcddm.contigo.db.Imagen;
import com.gcddm.contigo.db.Info;
import com.gcddm.contigo.db.Juego;
import com.gcddm.contigo.db.Mpuntos;
import com.gcddm.contigo.db.PreguntaRespuestas;
import com.gcddm.contigo.db.Programa;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Envenom on 24/11/2016.
 */
public class ParsearXml {

    public static boolean sobreEscribir=false;
    // Namespace general. null si no existe
    private static final String ns = null;
    //Etiquetas del archivos XML

    private static final String ETIQUETA_XML = "XML";
    private static final String ETIQUETA_FECHA_ACTUALIZACION = "FechaActualizacion";
    public static final String ETIQUETA_INFOS = "Infos";
    private static final String ETIQUETA_INFO = "Info";
    private static final String ETIQUETA_TITULO = "Titulo";
    private static final String ETIQUETA_TEXTO = "Texto";
    private static final String ETIQUETA_IMAGEN = "Imagen";
    private static final String ETIQUETA_VIDEO = "Video";
    public static final String ETIQUETA_JUEGOS = "Juegos";
    private static final String ETIQUETA_JUEGO = "Juego";
    private static final String ETIQUETA_NOMBRE = "Nombre";
    private static final String ETIQUETA_ESTADO = "Estado";
    private static final String ETIQUETA_PREGUNTAS = "Preguntas";
    private static final String ETIQUETA_PREGUNTA = "Pregunta";
    private static final String ETIQUETA_RESPUESTA_CORRECTA = "RespuestaCorrecta";
    private static final String ETIQUETA_RESPUESTA_INCORRECTA_1 = "RespuestaIncorrecta1";
    private static final String ETIQUETA_RESPUESTA_INCORRECTA_2 = "RespuestaIncorrecta2";
    private static final String ETIQUETA_PROGRAMA_NOMBRE = "Programa";
    private static final String ETIQUETA_PROGRAMA_FECHA = "FechaPrograma";

    //////////////////////////////////Mapa/////////////////////////////////////////////

    public static final String ETIQUETA_MAPA = "Mapas";
    private static final String ETIQUETA_PUNTOS = "Puntos";
    private static final String ETIQUETA_LONGITUD = "Longitud";
    private static final String ETIQUETA_LATITUD = "Latitud";
    private static final String ETIQUETA_IMAGEN_MAPA= "ImagenMapa";
    private static final String ETIQUETA_DIRECCION= "Direccion";
    private static final String ETIQUETA_TELEFONO= "Telefono";

    FechaActualizacion fa;
    Programa programa;
    long fecha_long;


    ArrayList<String> images = new ArrayList<String>();
    //Comienza a parsear el XML
    public boolean parsear(InputStream inputStream, Context context, File tmpFile)
            throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(inputStream, null);

            //Ignora todas las etiquetas hasta que encuetra una etiqueta <Presentacion>
            while (parser.nextTag() != XmlPullParser.END_TAG) {
                if (parser.getName().equals(ETIQUETA_XML)){
                    break;
                }
            }

            return leerXML(parser, context, tmpFile);
        } finally {
            inputStream.close();
        }
    }

    public List<String> buscarConenido(InputStream inputStream, Context context, File tmpFile)
            throws XmlPullParserException, IOException {
        List<String> conStrings = new ArrayList<>();
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(inputStream, null);

            //Ignora todas las etiquetas hasta que encuetra una etiqueta <Presentacion>
            while (parser.nextTag() != XmlPullParser.END_TAG) {
                if (parser.getName().equals(ETIQUETA_XML)){
                    break;
                }
            }

            parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_XML);
            //Objeto del tipo


            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }

                String name = parser.getName();

                switch (name) {
                    case ETIQUETA_INFOS:
                        conStrings.add(ETIQUETA_INFOS);
                        break;

                    case ETIQUETA_JUEGOS:
                        conStrings.add(ETIQUETA_JUEGOS);
                        break;
                    case ETIQUETA_MAPA:
                        conStrings.add(ETIQUETA_MAPA);
                        break;
                    default:
                        saltarEtiqueta(parser);
                }
            }
        } finally {
            inputStream.close();
        }
        return conStrings;
    }

    //Lee la etiqueta <XML>
    private boolean leerXML(XmlPullParser parser, Context context, File tmpFile)
            throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_XML);
        //Objeto del tipo


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            switch (name) {
                case ETIQUETA_FECHA_ACTUALIZACION:

                    if ( FechaActualizacion.listAll(FechaActualizacion.class).isEmpty()){

                        fa = new FechaActualizacion();
                    }
                    else{
                        fa =  FechaActualizacion.listAll(FechaActualizacion.class).get(0);
                    }
                    fecha_long =  new Long(leerFecha(parser));
                    if (fa.getFecha()>-1){
                        if(fa.getFecha()<fecha_long || sobreEscribir==true){
                            FechaActualizacion.deleteAll(FechaActualizacion.class);
                            fa = new FechaActualizacion();
                            fa.setFecha(fecha_long);
                            fa.save();
                        } else{
                            return false;
                        }

                    }
                break;
                case ETIQUETA_PROGRAMA_NOMBRE:

                    if ( Programa.listAll(Programa.class).isEmpty()){

                        programa = new Programa();
                        programa.setNombre(leerNombrePrograma(parser));
                        programa.save();
                    }
                    else{
                        programa = new Programa();
                        if(Programa.listAll(Programa.class).size()>1){
                            Programa.listAll(Programa.class).get(0).delete();
                            programa.setNombre(leerNombrePrograma(parser));
                            programa.save();
                        }
                        else{
                            programa.setNombre(leerNombrePrograma(parser));
                            programa.save();
                        }
                    }

                    break;


                case ETIQUETA_PROGRAMA_FECHA:

                    if(Programa.listAll(Programa.class).size()>1) {
                        programa = Programa.listAll(Programa.class).get(1);
                    }else{
                        programa = Programa.listAll(Programa.class).get(0);
                    }
                    if(Programa.listAll(Programa.class).size()>1){
                        programa.setFecha(leerFechaPrograma(parser));
                        programa.save();
                    }
                    else{
                        programa.setFecha(leerFechaPrograma(parser));
                        programa.save();
                    }
                    break;


                case ETIQUETA_INFOS:
                    for ( Info i : leerInfos(parser, context, tmpFile)){
                        Info info = SearchInfoInDataBase(i);
                        if(info == null){
                            i.save();
                            List<Imagen> imagens = new ArrayList<>();
                            imagens = i.getImagen();
                            for(int j = 0; j < imagens.size(); j++){
                                imagens.get(j).setInfo(i);
                                imagens.get(j).save();
                            }
                        }else{
                            info.setNombre_info(i.getNombre_info());
                            info.setTexto_info(i.getTexto_info());
                            info.setVideo(i.getVideo());
                            Imagen.deleteAll(Imagen.class,"info = ?", String.valueOf(info.getId()));
                            //salvo la pregintas y respuestas
                            List<Imagen> imagens = new ArrayList<>();
                            imagens = i.getImagen();
                            for(int j = 0; j < imagens.size(); j++){
                                imagens.get(j).setInfo(info);
                                imagens.get(j).save();
                            }
                            info.save();
                        }
                    }
                    break;
                case ETIQUETA_JUEGOS:
                    for (Juego i : leerJuegos(parser, context, tmpFile)){
                        Juego juego = SearchJuegoInDataBase(i);
                        if(juego == null){
                            i.save();
                            //salvo la pregintas y respuestas
                            List<PreguntaRespuestas> preguntaRespuestases = new ArrayList<>();
                            preguntaRespuestases = i.getPreguntaRespuestases();
                            for(int j = 0; j < preguntaRespuestases.size(); j++){
                                preguntaRespuestases.get(j).setJuego(i);
                                preguntaRespuestases.get(j).save();
                            }
                        }else{
                            juego.setNombre_juego(i.getNombre_juego());
                            juego.setEstado(i.getEstado());
                            //Elimino todas las preguntas y respuesa de ese jeugo y pongo las nuevas
                            PreguntaRespuestas.deleteAll(PreguntaRespuestas.class,"juego = ?", String.valueOf(juego.getId()));
                            //salvo la pregintas y respuestas
                            List<PreguntaRespuestas> preguntaRespuestases = new ArrayList<>();
                            preguntaRespuestases = i.getPreguntaRespuestases();
                            for(int j = 0; j < preguntaRespuestases.size(); j++){
                                preguntaRespuestases.get(j).setJuego(i);
                                preguntaRespuestases.get(j).save();
                            }
                            juego.save();

                        }

                    }
                    break;
                case ETIQUETA_MAPA:
                    for (Mpuntos i : leerMapa(parser, context, tmpFile)) {
                        Mpuntos mpuntos = SearchMapaInDataBase(i);
                        if(mpuntos == null){
                            i.save();
                        }else{
                            mpuntos.setTelef(i.getTelef());
                            mpuntos.setDireccion(i.getDireccion());
                            mpuntos.setInformacion(i.getInformacion());
                            mpuntos.setLat(i.getLat());
                            mpuntos.setLon(i.getLon());
                            mpuntos.setNombre(i.getNombre());
                            ArrayList<String> imagenes = i.getImagenes();
                            ArrayList<String> imagenes2 = new ArrayList<>();
                            for(int j = 0; j < imagenes.size(); j++){
                                imagenes2.add(imagenes.get(j));
                            }
                            mpuntos.setImagenes(imagenes2);

                            mpuntos.save();
                        }
                    }
                    break;
                default:
                    saltarEtiqueta(parser);
            }
        }

        return true;
    }

    private List<Info> leerInfos(XmlPullParser parser, Context context, File file) throws IOException, XmlPullParserException {

        List<Info> infos = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_INFOS);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            if (name.equals(ETIQUETA_INFO)) {
                infos.add(leerInfo(parser, context, file));
            } else {
                saltarEtiqueta(parser);
            }
        }

        return infos;
    }

    private Info leerInfo(XmlPullParser parser, Context context, File file) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_INFO);
          Info info = new Info();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();


            switch (name) {
                case ETIQUETA_TITULO:
                    info.setNombre_info(leerTitulo(parser));
                    break;
                case ETIQUETA_TEXTO:
                    info.setTexto_info(leerTexto(parser));
                    break;
                case ETIQUETA_IMAGEN:
                    String imagenName = leerImagen(parser);
                    Imagen imagen = new Imagen();

                    InputStream isImage = new FileInputStream(
                            new File(file.getPath() + File.separator + imagenName)
                    );

                    File dirFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                    if (!dirFile.exists()){
                        dirFile.mkdir();
                    }



                    File imageFile = new File(dirFile, imagenName);
                    OutputStream os = new FileOutputStream(imageFile);

                    try{
                        byte[] buffer = new byte[1024];
                        int len;

                        while ((len = isImage.read(buffer)) > 0){
                            os.write(buffer);
                        }

                        isImage.close();
                        os.close();

                        imagen.setPath(imageFile.getPath());
                    } catch (IOException e){
                        os.close();
                        isImage.close();
                    }

                    info.addImagen(imagen);
                    break;
                case ETIQUETA_VIDEO:
                    String videoName = leerVideo(parser);

                    InputStream isVideo = new FileInputStream(
                            new File(file.getPath() + File.separator + videoName)
                    );

                    File movieFile = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES);

                    if (!movieFile.exists()){
                        movieFile.mkdir();
                    }

                    File videoFile = new File(movieFile, videoName);
                    OutputStream osVideo = new FileOutputStream(videoFile);

                    try{
                        byte[] buffer = new byte[1024];
                        int len;

                        while ((len = isVideo.read(buffer)) > 0){
                            osVideo.write(buffer);
                        }

                        isVideo.close();
                        osVideo.close();

                        info.setVideo(videoFile.getPath());
                    } catch (IOException e){
                        isVideo.close();
                        osVideo.close();
                    }
                    break;
                default:
                    saltarEtiqueta(parser);
            }
        }


        return info;
    }

    private List<Juego> leerJuegos(XmlPullParser parser, Context context, File file) throws IOException, XmlPullParserException {
        List<Juego> juegos = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_JUEGOS);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            if (name.equals(ETIQUETA_JUEGO)) {
                juegos.add(leerJuego(parser));
            } else {
                saltarEtiqueta(parser);
            }
        }

        return juegos;
    }

    private Juego leerJuego(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_JUEGO);
        //Objeto del tipo Juego
        Juego juego = new Juego();

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            switch (name) {
                case ETIQUETA_NOMBRE:
                    juego.setNombre_juego(leerNombre(parser));
                    break;
                case ETIQUETA_ESTADO:
                    juego.setEstado(leerEstado(parser));
                    break;
                case ETIQUETA_PREGUNTAS:
                    for (PreguntaRespuestas pr : leerPreguntas(parser)){
                        juego.addPreguntas(pr);
                    }
                    break;
                default:
                    saltarEtiqueta(parser);
            }
        }

        return juego;
    }

    private List<PreguntaRespuestas> leerPreguntas(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_PREGUNTAS);
        //Objeto del tipo Juego
        List<PreguntaRespuestas> list = new ArrayList<>();

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            if (name.equals(ETIQUETA_PREGUNTA)){
                list.add(leerPreguntaRespuestas(parser));
            } else {
                saltarEtiqueta(parser);
            }
        }

        return list;
    }

    private PreguntaRespuestas leerPreguntaRespuestas(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_PREGUNTA);
        //Objeto del tipo Juego
        PreguntaRespuestas pr = new PreguntaRespuestas();

        while(parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            switch (name){
                case ETIQUETA_TEXTO:
                    pr.setPregunta(leerTexto(parser));
                    break;
                case ETIQUETA_RESPUESTA_CORRECTA:
                    pr.setRespuesta_correcta(leerRespuestaCorrecta(parser));
                    break;
                case ETIQUETA_RESPUESTA_INCORRECTA_1:
                    pr.setRespuesta_incorrecta1(leerRespuestaIncorrecta1(parser));
                    break;
                case ETIQUETA_RESPUESTA_INCORRECTA_2:
                    pr.setRespuesta_incorrecta2(leerRespuestaIncorrecta2(parser));
                    break;
                default:
                    saltarEtiqueta(parser);
            }
        }

        return pr;
    }

    private String leerTitulo(XmlPullParser parser) throws XmlPullParserException, IOException {
        String nombre;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_TITULO);
        nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_TITULO);
        return nombre;
    }

    private String leerFecha(XmlPullParser parser) throws XmlPullParserException, IOException {
        String fecha;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_FECHA_ACTUALIZACION);
        fecha = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_FECHA_ACTUALIZACION);
        return fecha;
    }

    private String leerNombre(XmlPullParser parser) throws XmlPullParserException, IOException {
        String nombre;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_NOMBRE);
        nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_NOMBRE);
        return nombre;
    }

    private String leerEstado(XmlPullParser parser) throws XmlPullParserException, IOException {
        String estado;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_ESTADO);
        estado = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_ESTADO);
        return estado;
    }

    private String leerTexto(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String texto;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_TEXTO);
        texto = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_TEXTO);
        return texto;
    }

    private String leerImagen(XmlPullParser parser) throws XmlPullParserException, IOException {
        String imagen;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_IMAGEN);
        imagen = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_IMAGEN);
        return imagen;
    }

    private String leerVideo(XmlPullParser parser) throws XmlPullParserException, IOException {
        String video;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_VIDEO);
        video = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_VIDEO);
        return video;
    }

    private String leerPregunta(XmlPullParser parser) throws XmlPullParserException, IOException {
        String pregunta;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_PREGUNTA);
        pregunta = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_PREGUNTA);
        return pregunta;
    }

    private String leerRespuestaCorrecta(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String respCorrecta;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_RESPUESTA_CORRECTA);
        respCorrecta = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_RESPUESTA_CORRECTA);
        return respCorrecta;
    }

    private String leerRespuestaIncorrecta1(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String respIncorrecta;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_RESPUESTA_INCORRECTA_1);
        respIncorrecta = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_RESPUESTA_INCORRECTA_1);
        return respIncorrecta;
    }

    private String leerRespuestaIncorrecta2(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String respIncorrecta;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_RESPUESTA_INCORRECTA_2);
        respIncorrecta = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_RESPUESTA_INCORRECTA_2);
        return respIncorrecta;
    }

    private String leerNombrePrograma(XmlPullParser parser) throws XmlPullParserException, IOException {
        String nombrePrograma;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_PROGRAMA_NOMBRE);
        nombrePrograma = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_PROGRAMA_NOMBRE);
        return nombrePrograma;
    }

    private String leerFechaPrograma(XmlPullParser parser) throws XmlPullParserException, IOException {
        String nombrePrograma;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_PROGRAMA_FECHA);
        nombrePrograma = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_PROGRAMA_FECHA);
        return nombrePrograma;
    }

    private String obtenerTexto(XmlPullParser parser) throws XmlPullParserException, IOException {
        String resultado = "";

        if (parser.next() == XmlPullParser.TEXT) {
            resultado = parser.getText();
            parser.nextTag();
        }

        return resultado;
    }

    //Salta aquellos objetos que no interesan en la jerarquia XML
    private void saltarEtiqueta(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG){
            throw new IllegalStateException();
        }

        int depth = 1;

        while (depth != 0){
            switch (parser.next()){
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
    ////////////////Mapas////////////////////////

    private List<Mpuntos> leerMapa(XmlPullParser parser, Context context, File file) throws IOException, XmlPullParserException {

        List<Mpuntos> mPuntos = new ArrayList<>();

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_MAPA);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            if (name.equals(ETIQUETA_PUNTOS)) {
                Mpuntos mpuntos = leerPunto(parser, context, file);
                mpuntos.setImagenes(images);
                images = new ArrayList<>();
                mPuntos.add(mpuntos);
            } else {
                saltarEtiqueta(parser);
            }
        }


        return mPuntos;
    }

    private Mpuntos leerPunto(XmlPullParser parser, Context context, File file) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_PUNTOS);
        Mpuntos mpuntos = new Mpuntos();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case ETIQUETA_TITULO:
                    mpuntos.setNombre(leerTitulo(parser));
                    break;
                case ETIQUETA_TEXTO:
                    mpuntos.setInformacion(leerTexto(parser));
                    break;
                case ETIQUETA_LATITUD:
                    mpuntos.setLat(Double.valueOf(leerLatitud(parser)));
                    break;
                case ETIQUETA_LONGITUD:
                    mpuntos.setLon(Double.valueOf(leerLongitud(parser)));
                    break;

                case ETIQUETA_IMAGEN_MAPA:
                    String imagenName = leerImagenSecundaria(parser);
                    Imagen imagen = new Imagen();
                    InputStream isImage = new FileInputStream(
                            new File(file.getPath() + File.separator + imagenName)
                    );
                    File dirFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                    if (!dirFile.exists()) {
                        dirFile.mkdir();
                    }
                    File imageFile = new File(dirFile, imagenName);
                    OutputStream os = new FileOutputStream(imageFile);
                    try {
                        byte[] buffer = new byte[1024];
                        int len;

                        while ((len = isImage.read(buffer)) > 0) {
                            os.write(buffer);
                        }

                        isImage.close();
                        os.close();

                        imagen.setPath(imageFile.getPath());
                    } catch (IOException e) {
                        os.close();
                        isImage.close();
                    }

                    images.add(imagenName);

                    break;
                case ETIQUETA_DIRECCION:
                    mpuntos.setDireccion(leerDireccion(parser));
                    break;
                case ETIQUETA_TELEFONO:
                    mpuntos.setTelef(leerTelefono(parser));
                    break;
                default:
                    saltarEtiqueta(parser);
            }
        }

        return mpuntos;
    }

    private String leerLatitud(XmlPullParser parser) throws XmlPullParserException, IOException {
        String nombre;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_LATITUD);
        nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_LATITUD);
        return nombre;
    }
    private String leerLongitud(XmlPullParser parser) throws XmlPullParserException, IOException {
        String nombre;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_LONGITUD);
        nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_LONGITUD);
        return nombre;
    }
    private String leerImagenSecundaria(XmlPullParser parser) throws XmlPullParserException, IOException {
        String imagen;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_IMAGEN_MAPA);
        imagen = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_IMAGEN_MAPA);
        return imagen;
    }
    private String leerDireccion(XmlPullParser parser) throws XmlPullParserException, IOException {
        String nombre;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_DIRECCION);
        nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_DIRECCION);
        return nombre;
    }
    private String leerTelefono(XmlPullParser parser) throws XmlPullParserException, IOException {
        String nombre;

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_TELEFONO);
        nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_TELEFONO);
        return nombre;
    }

    private Info SearchInfoInDataBase(Info info){
        List<Info> informacion = Info.findWithQuery(Info.class, "SELECT * FROM Info WHERE nombreinfo = ?", info.getNombre_info());

        if(!informacion.isEmpty()){
            return informacion.get(0);
        }

        return null;

    }

    private Juego SearchJuegoInDataBase(Juego juego){
        List<Juego> juegos = Juego.findWithQuery(Juego.class, "SELECT * FROM Juego WHERE nombrejuego = ?", juego.getNombre_juego());

        if(!juegos.isEmpty()){
            return juegos.get(0);
        }

        return null;

    }

    private Mpuntos SearchMapaInDataBase(Mpuntos mpuntos){
        List<Mpuntos> mpuntoses = Mpuntos.findWithQuery(Mpuntos.class, "SELECT * FROM Mpuntos WHERE nombre = ?", mpuntos.getNombre());

        if(!mpuntoses.isEmpty()){
            return mpuntoses.get(0);
        }

        return null;

    }

}
