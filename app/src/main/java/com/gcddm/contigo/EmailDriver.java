package com.gcddm.contigo;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.logging.Handler;

/**
 * Created by Gilbert on 5/10/2017.
 */
public class EmailDriver extends ContextWrapper{

    private static EmailDriver Instance = null;
    static public Context context = null;
    private Socket socket;
    private String msg = "vacio";
    private String str = "HELO Localhost";
    private static final int SERVERPORT = 25 ;
    private static final String SERVER = "smtp.nauta.cu";
    BufferedReader in;
    private String line;
    private smtpSend sender;
    private String senderHost = null, receiverHost = null, passwordSenderHost = null, text = null;

    public static EmailDriver getInstance(final Context c) {
        if(Instance == null)
            Instance = new EmailDriver(c);
        return Instance;
    }

    private EmailDriver(final Context c) {
        super(c);
        context = c;
        sender = new smtpSend();
    }

    private void turnOnMovileData(final boolean flag)
    {
        try {
            final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class conectivityManagerClass = Class.forName(cm.getClass().getName());
            final Field connectivityManagerField = conectivityManagerClass.getDeclaredField("mService");
            connectivityManagerField.setAccessible(true);
            final Object connectivityManager = connectivityManagerField.get(cm);
            final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
            final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(connectivityManager, flag);
        }
        catch (ClassNotFoundException e){}
        catch (NoSuchFieldException e){}
        catch (IllegalAccessException e){}
        catch (NoSuchMethodException e){}
        catch (InvocationTargetException e){}
    }

    private boolean connectToSocket()
    {
        try
        {
            InetAddress serverAddr = InetAddress.getByName(SERVER);
            socket = new Socket(serverAddr, SERVERPORT);
        }
        catch (UnknownHostException e1){}
        catch (IOException e1) {}
        return (socket != null);
    }

    private boolean sendEmail(){
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            if (in.ready()) {
                line = in.readLine() + "\n";
            } else
                return  false;
            if (line.startsWith("220")) {
                out.println("HELO envy.qpasa.cu");
                out.flush();
            }
            String tmp = in.readLine();
            line += tmp + "\n";
            //debugResp(line);
            if (tmp.startsWith("250")) {
                out.println("AUTH LOGIN");
                out.flush();
            }
            tmp = in.readLine();
            line += tmp + "\n";
            //debugResp(line);
            if (tmp.startsWith("334")) {
                String user = sender.toBase64mio(senderHost);
                //debugMsg(user);
                out.print(user);
                out.flush();
                tmp = in.readLine();
                line += tmp + "\n";
                //debugResp(line);
                if (tmp.startsWith("334")) {
                    //publishProgress("Autenticando");
                    String passwd = sender.toBase64mio(passwordSenderHost);
                    //debugMsg(passwd);
                    out.print(passwd);
                    out.flush();
                    tmp = in.readLine();
                    line += tmp + "\n";
                    //debugResp(line);
                    if (tmp.startsWith("235")) {
                        //publishProgress("Autenticacion OK");
                        String sender = "MAIL FROM:" + "<" + senderHost + ">";
                        out.println(sender);
                        out.flush();
                        tmp = in.readLine();
                        line += tmp + "\n";
                        //debugResp(line);
                        out.println("RCPT TO:" + "<" + receiverHost + ">");
                        out.flush();
                        tmp = in.readLine();
                        line += tmp + "\n";
                        //debugResp(line);
                        out.println("DATA");
                        out.flush();
                        tmp = in.readLine();
                        line += tmp + "\n";
                        //debugResp(line);
                        out.println("Subject: En linea contigo");
                        out.flush();
                        out.println("From:" + senderHost);
                        out.flush();
                        out.println("To:" + receiverHost);
                        out.flush();
                        out.println("");
                        out.flush();
                        out.println(text);
                        out.flush();
                        //tmp = in.readLine();
                        line += tmp + "\n";
                        //debugResp(line);
                        out.println(".");
                        out.flush();
                        tmp = in.readLine();
                        line += tmp + "\n";
                        //debugResp(line);
                        out.println("QUIT");
                        out.flush();
                        tmp = in.readLine();
                        line += tmp + "\n";
                        //debugResp(line);
                        in.close();
                        out.close();
                        socket.close();
                        return  true;
                    } else {
                        in.close();
                        out.close();
                        socket.close();
                        return false;
                    }
                }
                else {
                    in.close();
                    out.close();
                    socket.close();
                    return false;
                }

            }
            else {
                in.close();
                out.close();
                socket.close();
                return false;
            }


        }
        catch(UnknownHostException e1){}
        catch(IOException e1){}
        catch(Exception e2){}
        return false;
    }


    public boolean send(final String[] emailsAndPassword, final String subject, final String messageText, final String attachment)
    {
        boolean result = false;
        turnOnMovileData(true);
        try{Thread.sleep(10000);}catch(InterruptedException e){}
        receiverHost = emailsAndPassword[0];
        senderHost = emailsAndPassword[1];
        passwordSenderHost = emailsAndPassword[2];
        try {
            text =  Html.fromHtml(new String(messageText.getBytes("UTF-8"))).toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(connectToSocket())
           result = sendEmail();
        //turnOnMovileData(false);
        return result;
    }
}
