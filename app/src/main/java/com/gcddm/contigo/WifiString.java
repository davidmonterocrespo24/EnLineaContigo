package com.gcddm.contigo;

import android.content.Context;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.format.DateUtils;
import android.util.Xml;

import com.gcddm.contigo.db.Review;

import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Gilbert on 5/18/2017.
 */
public class WifiString extends StringProcess {

    private Context context = null;

    WifiString(final  Context c)
    {
        super();
        context = c;
    }

    @Override
    public String[] parseAnonymousMode() {
        String message[] = new String[2];
        try {
            message[0] = receptor;
            XmlSerializer xmlSerializer = Xml.newSerializer();
            FileOutputStream file = context.openFileOutput("ELC.xml",Context.MODE_PRIVATE);
            OutputStreamWriter streamWriter = new OutputStreamWriter(file);
            xmlSerializer.setOutput(streamWriter);
            message[1] = context.getFilesDir().getAbsolutePath() + "/ELC.xml";
            xmlSerializer.startTag("", "update");
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
            xmlSerializer.attribute("","date",sdt.format(System.currentTimeMillis()));
            xmlSerializer.attribute("","system","EnLineaContigo");
            xmlSerializer.startTag("","informations");
            xmlSerializer.startTag("","info");
            xmlSerializer.attribute("","type","opinion");
            xmlSerializer.startTag("","citizenid");
            xmlSerializer.text("");
            xmlSerializer.endTag("","citizenid");
            xmlSerializer.startTag("","programtitle");
            xmlSerializer.text("En linea contigo");
            xmlSerializer.endTag("","programtitle");
            xmlSerializer.startTag("","data");
            xmlSerializer.startTag("","contacto");
            xmlSerializer.startTag("","name");
            xmlSerializer.text("");
            xmlSerializer.endTag("","name");
            xmlSerializer.startTag("","address");
            xmlSerializer.text("");
            xmlSerializer.endTag("","address");
            xmlSerializer.startTag("","phone");
            xmlSerializer.text("");
            xmlSerializer.endTag("","phone");
            xmlSerializer.startTag("","mail");
            xmlSerializer.text("");
            xmlSerializer.endTag("","mail");
            xmlSerializer.startTag("","nickname");
            xmlSerializer.text(nickname);
            xmlSerializer.endTag("","nickname");
            xmlSerializer.startTag("","gps");
            xmlSerializer.text("");
            xmlSerializer.endTag("","gps");
            xmlSerializer.endTag("","contacto");
            xmlSerializer.startTag("","title");
            xmlSerializer.text(asunto);
            xmlSerializer.endTag("","title");
            xmlSerializer.startTag("","text");
            xmlSerializer.text(body);
            xmlSerializer.endTag("","text");
            xmlSerializer.endTag("","data");
            xmlSerializer.startTag("","images");
            xmlSerializer.startTag("","image");
            xmlSerializer.text("/199393.JPG");
            xmlSerializer.endTag("","image");
            xmlSerializer.endTag("","images");
            xmlSerializer.startTag("","videos");
            xmlSerializer.text("/gfgf.mp4");
            xmlSerializer.endTag("","videos");
            xmlSerializer.startTag("","records");
            xmlSerializer.text("/ssss.mp3");
            xmlSerializer.endTag("","records");
            xmlSerializer.endTag("","info");
            xmlSerializer.endTag("", "informations");
            xmlSerializer.endDocument();
            file.close();
            streamWriter.close();
        }
        catch (FileNotFoundException e)
        {

        }
        catch (IOException e)
        {

        }
        finally {

        }
        return message;
    }

    @Override
    public String[] parseShortMode() {
        String message[] = new String[2];
        try {
            message[0] = receptor;
            XmlSerializer xmlSerializer = Xml.newSerializer();
            FileOutputStream file = context.openFileOutput("ELC.xml",Context.MODE_PRIVATE);
            OutputStreamWriter streamWriter = new OutputStreamWriter(file);
            xmlSerializer.setOutput(streamWriter);
            message[1] = context.getFilesDir().getAbsolutePath() + "/ELC.xml";
            xmlSerializer.startTag("", "update");
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
            xmlSerializer.attribute("","date",sdt.format(System.currentTimeMillis()));
            xmlSerializer.attribute("","system","EnLineaContigo");
            xmlSerializer.startTag("","informations");
            xmlSerializer.startTag("","info");
            xmlSerializer.attribute("","type","opinion");
            xmlSerializer.startTag("","citizenid");
            xmlSerializer.text("");
            xmlSerializer.endTag("","citizenid");
            xmlSerializer.startTag("","programtitle");
            xmlSerializer.text("En linea contigo");
            xmlSerializer.endTag("","programtitle");
            xmlSerializer.startTag("","data");
            xmlSerializer.startTag("","contacto");
            xmlSerializer.startTag("","name");
            xmlSerializer.text(name);
            xmlSerializer.endTag("","name");
            xmlSerializer.startTag("","address");
            xmlSerializer.text("");
            xmlSerializer.endTag("","address");
            xmlSerializer.startTag("","phone");
            xmlSerializer.text("");
            xmlSerializer.endTag("","phone");
            xmlSerializer.startTag("","mail");
            xmlSerializer.text("");
            xmlSerializer.endTag("","mail");
            xmlSerializer.startTag("","nickname");
            xmlSerializer.text("");
            xmlSerializer.endTag("","nickname");
            xmlSerializer.startTag("","gps");
            xmlSerializer.text("");
            xmlSerializer.endTag("","gps");
            xmlSerializer.endTag("","contacto");
            xmlSerializer.startTag("","title");
            xmlSerializer.text(asunto);
            xmlSerializer.endTag("","title");
            xmlSerializer.startTag("","text");
            xmlSerializer.text(body);
            xmlSerializer.endTag("","text");
            xmlSerializer.endTag("","data");
            xmlSerializer.startTag("","images");
            xmlSerializer.startTag("","image");
            xmlSerializer.text("/199393.JPG");
            xmlSerializer.endTag("","image");
            xmlSerializer.endTag("","images");
            xmlSerializer.startTag("","videos");
            xmlSerializer.text("/gfgf.mp4");
            xmlSerializer.endTag("","videos");
            xmlSerializer.startTag("","records");
            xmlSerializer.text("/ssss.mp3");
            xmlSerializer.endTag("","records");
            xmlSerializer.endTag("","info");
            xmlSerializer.endTag("", "informations");
            xmlSerializer.endDocument();
            file.close();
            streamWriter.close();
        }
        catch (FileNotFoundException e)
        {

        }
        catch (IOException e)
        {

        }
        finally {

        }
        return message;
    }

    @Override
    public String[] parseFullMode() {
        String message[] = new String[2];
        try {
            message[0] = receptor;
            XmlSerializer xmlSerializer = Xml.newSerializer();
            FileOutputStream file = context.openFileOutput("ELC.xml",Context.MODE_PRIVATE);
            OutputStreamWriter streamWriter = new OutputStreamWriter(file);
            xmlSerializer.setOutput(streamWriter);
            message[1] = context.getFilesDir().getAbsolutePath() + "/ELC.xml";
            xmlSerializer.startTag("", "update");
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
            xmlSerializer.attribute("","date",sdt.format(System.currentTimeMillis()));
            xmlSerializer.attribute("","system","EnLineaContigo");
            xmlSerializer.startTag("","informations");
            xmlSerializer.startTag("","info");
            xmlSerializer.attribute("","type","opinion");
            xmlSerializer.startTag("","citizenid");
            xmlSerializer.text("");
            xmlSerializer.endTag("","citizenid");
            xmlSerializer.startTag("","programtitle");
            xmlSerializer.text("En linea contigo");
            xmlSerializer.endTag("","programtitle");
            xmlSerializer.startTag("","data");
            xmlSerializer.startTag("","contacto");
            xmlSerializer.startTag("","name");
            xmlSerializer.text(name);
            xmlSerializer.endTag("","name");
            xmlSerializer.startTag("","address");
            xmlSerializer.text(address);
            xmlSerializer.endTag("","address");
            xmlSerializer.startTag("","phone");
            xmlSerializer.text(phone);
            xmlSerializer.endTag("","phone");
            xmlSerializer.startTag("","mail");
            xmlSerializer.text(email);
            xmlSerializer.endTag("","mail");
            xmlSerializer.startTag("","nickname");
            xmlSerializer.text(nickname);
            xmlSerializer.endTag("","nickname");
            xmlSerializer.startTag("","gps");
            xmlSerializer.text("");
            xmlSerializer.endTag("","gps");
            xmlSerializer.endTag("","contacto");
            xmlSerializer.startTag("","title");
            xmlSerializer.text(asunto);
            xmlSerializer.endTag("","title");
            xmlSerializer.startTag("","text");
            xmlSerializer.text(body);
            xmlSerializer.endTag("","text");
            xmlSerializer.endTag("","data");
            xmlSerializer.startTag("","images");
            xmlSerializer.startTag("","image");
            xmlSerializer.text("/199393.JPG");
            xmlSerializer.endTag("","image");
            xmlSerializer.endTag("","images");
            xmlSerializer.startTag("","videos");
            xmlSerializer.text("/gfgf.mp4");
            xmlSerializer.endTag("","videos");
            xmlSerializer.startTag("","records");
            xmlSerializer.text("/ssss.mp3");
            xmlSerializer.endTag("","records");
            xmlSerializer.endTag("","info");
            xmlSerializer.endTag("", "informations");
            xmlSerializer.endDocument();
            file.close();
            streamWriter.close();
        }
        catch (FileNotFoundException e)
        {

        }
        catch (IOException e)
        {

        }
        finally {

        }
        return message;
    }

    public String[] parseFromDataBase()
    {
        String message[] = new String[2];
        try {
            message[0] = receptor;
            XmlSerializer xmlSerializer = Xml.newSerializer();
            FileOutputStream file = context.openFileOutput("ELC.xml",Context.MODE_PRIVATE);
            OutputStreamWriter streamWriter = new OutputStreamWriter(file);
            xmlSerializer.setOutput(streamWriter);
            message[1] = context.getFilesDir().getAbsolutePath() + "/ELC.xml";
            xmlSerializer.startTag("", "update");
            SimpleDateFormat sdt = new SimpleDateFormat("yyyy/MM/dd");
            xmlSerializer.attribute("","date",sdt.format(System.currentTimeMillis()));
            xmlSerializer.attribute("","system","EnLineaContigo");
            xmlSerializer.startTag("","informations");
            List<Review> list = Review.findWithQuery(Review.class, "SELECT * FROM Review WHERE estado = ? ORDER BY fecha ASC",  String.valueOf(Review.POR_ENVIAR));
            if(list != null) {
                for (int i = 0; i < list.size(); i++) {
                    xmlSerializer.startTag("", "info");
                    xmlSerializer.attribute("", "type", "opinion");
                    xmlSerializer.comment("Identificador del ciudadano en el sistema del programa");
                    xmlSerializer.startTag("", "citizenid");
                    xmlSerializer.text(list.get(i).getId().toString());
                    xmlSerializer.endTag("", "citizenid");
                    xmlSerializer.startTag("", "programtitle");
                    xmlSerializer.text(list.get(i).getAsunto());
                    xmlSerializer.endTag("", "programtitle");
                    xmlSerializer.startTag("", "data");
                    xmlSerializer.startTag("", "contacto");
                    xmlSerializer.startTag("", "name");
                    xmlSerializer.text(list.get(i).getContacto().getNombre_Apellido());
                    xmlSerializer.endTag("", "name");
                    xmlSerializer.startTag("", "address");
                    xmlSerializer.text(list.get(i).getContacto().getDireccion());
                    xmlSerializer.endTag("", "address");
                    xmlSerializer.startTag("", "phone");
                    xmlSerializer.text(String.valueOf(list.get(i).getContacto().getTelefono()));
                    xmlSerializer.endTag("", "phone");
                    xmlSerializer.startTag("", "mail");
                    xmlSerializer.text(list.get(i).getContacto().getCorreo());
                    xmlSerializer.endTag("", "mail");
                    xmlSerializer.startTag("", "nickname");
                    xmlSerializer.text(list.get(i).getContacto().getNickname());
                    xmlSerializer.endTag("", "nickname");
                    xmlSerializer.startTag("", "gps");
                    xmlSerializer.text("");
                    xmlSerializer.endTag("", "gps");
                    xmlSerializer.endTag("", "contacto");
                    xmlSerializer.startTag("", "title");
                    xmlSerializer.text(list.get(i).getAsunto());
                    xmlSerializer.endTag("", "title");
                    xmlSerializer.startTag("", "text");
                    xmlSerializer.text(list.get(i).getMensaje());
                    xmlSerializer.endTag("", "text");
                    xmlSerializer.endTag("", "data");
                    xmlSerializer.startTag("", "images");
                    xmlSerializer.startTag("", "image");
                    xmlSerializer.text("/199393.JPG");
                    xmlSerializer.endTag("", "image");
                    xmlSerializer.endTag("", "images");
                    xmlSerializer.startTag("", "videos");
                    xmlSerializer.text("/gfgf.mp4");
                    xmlSerializer.endTag("", "videos");
                    xmlSerializer.startTag("", "records");
                    xmlSerializer.text("/ssss.mp3");
                    xmlSerializer.endTag("", "records");
                    xmlSerializer.endTag("", "info");
                    Review review = list.get(i);
                    review.setEstado(Review.ENVIADO);
                }
            }

//            for(int i = 0; i < 1; i++)
//            {
//                xmlSerializer.startTag("","info");
//                xmlSerializer.attribute("","type","felicitation");
//                xmlSerializer.comment("Identificador del ciudadano en el sistema del programa");
//                xmlSerializer.startTag("","citizenid");
//                xmlSerializer.text("RTE33454544");
//                xmlSerializer.endTag("","citizenid");
//                xmlSerializer.startTag("","programtitle");
//                xmlSerializer.text("El problema del agua en Santiago");
//                xmlSerializer.endTag("","programtitle");
//                xmlSerializer.startTag("","data");
//                xmlSerializer.startTag("","contacto");
//                xmlSerializer.startTag("","name");
//                xmlSerializer.text("Pedro Jose");
//                xmlSerializer.endTag("","name");
//                xmlSerializer.startTag("","lastname");
//                xmlSerializer.text("Moreno");
//                xmlSerializer.endTag("","lastname");
//                xmlSerializer.startTag("","address");
//                xmlSerializer.text("Calle 10 Rpto Sueño, #264");
//                xmlSerializer.endTag("","address");
//                xmlSerializer.startTag("","phone");
//                xmlSerializer.text("12347889000");
//                xmlSerializer.endTag("","phone");
//                xmlSerializer.startTag("","mail");
//                xmlSerializer.text("pedro@nauta.cu");
//                xmlSerializer.endTag("","mail");
//                xmlSerializer.startTag("","nickname");
//                xmlSerializer.text("el sabelotodo");
//                xmlSerializer.endTag("","nickname");
//                xmlSerializer.startTag("","gps");
//                xmlSerializer.text("");
//                xmlSerializer.endTag("","gps");
//                xmlSerializer.endTag("","contacto");
//                xmlSerializer.startTag("","title");
//                xmlSerializer.text("El problema del agua es terrible");
//                xmlSerializer.endTag("","title");
//                xmlSerializer.startTag("","text");
//                xmlSerializer.text("Hace 10 dias que no llega el agua a mi casa");
//                xmlSerializer.endTag("","text");
//                xmlSerializer.endTag("","data");
//                xmlSerializer.startTag("","images");
//                xmlSerializer.startTag("","image");
//                xmlSerializer.text("/199393.JPG");
//                xmlSerializer.endTag("","image");
//                xmlSerializer.endTag("","images");
//                xmlSerializer.startTag("","videos");
//                xmlSerializer.text("/gfgf.mp4");
//                xmlSerializer.endTag("","videos");
//                xmlSerializer.startTag("","records");
//                xmlSerializer.text("/ssss.mp3");
//                xmlSerializer.endTag("","records");
//                xmlSerializer.endTag("","info");
//            }
            xmlSerializer.endTag("", "informations");
            xmlSerializer.endDocument();
            file.close();
            streamWriter.close();
        }
        catch (FileNotFoundException e)
        {

        }
        catch (IOException e)
        {

        }
        finally {

        }
        return message;
    }

    public String getXmlString()
    {
        String xmlString = null;
        try{
            BufferedReader fin = new BufferedReader(new InputStreamReader(context.openFileInput("ELC.xml")));
            xmlString = fin.readLine();
            fin.close();
        }
        catch (FileNotFoundException e){

        }
        catch (IOException e){

        }
        return xmlString;
    }
}
