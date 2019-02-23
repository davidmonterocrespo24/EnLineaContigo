package com.gcddm.contigo;

/**
 * Created by vladimir on 19/05/17.
 */
import java.net.*;
import java.io.*;
import android.util.Base64;


public class smtpSend {

    private PrintWriter out;
    private BufferedReader in;
    private String HostName;
    private String localHostName;
    private Socket miSocket;
    String Errores;

    public smtpSend()
    {}

    public String getErrores()
    {
        return Errores;
    }
    public smtpSend(Socket s)
    {
        //this.HostName = hostName;
        this.miSocket = s;

    }

    public String sendMail()
    {
        String response ="sin try";
        try
        {

            out = new PrintWriter(miSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(miSocket.getInputStream()));
            //System.out.println("hostName = " + localHostName);
            send(null);
            send("HELO " + localHostName);
            send("MAIL FROM: " + "my email@gmail.com");
            send("RCPT TO: " + "my email@gmail.com");
            send("DATA");
            send("Happy SMTP Programming!!");
            send("Happy SMTP Programming!!");
            send(".");

            send("QUIT");
            miSocket.close();
            out.close();
            in.close();

        }
        catch(Exception e)
        {
            response = e.getMessage();return response;
        }
        return response;
    }
    public String toBase64mio(String text)
    {
        return  new String(Base64.encode(text.getBytes(),Base64.DEFAULT));
    }

    public String send(String s) throws IOException
    { if (s != null)
    { out.println(s);
        out.flush();
    }
        String line;
        if ((line = in.readLine()) != null) //output the response
            return line;
        else
            return "Error Conecting to Server" + HostName;
    }
}
