package com.gcddm.contigo;

import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;

import com.gcddm.contigo.db.Review;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dionis on 10/05/2017.
 */
public class UpFileServices {
    private final int sdkVersion = Build.VERSION.SDK_INT;
    //private final String SERVICES_URL = "http://192.168.43.3:1337";
    private final String PORT = "1337";
    private final String TAG = "UpFileServices";
    private String title;
    private URL UPLOAD_URL;
    private String description;
    private String zipFileAddress;
    private String APPID = "";
    private String upLoadResults;
    private int TIME = 20000;

    protected String upLoad(String param) {
        //return (validateServicesStatus())?"OK":"";
///  Mientras haya conexion wifi y las redes son las autorizadas hacer accion

       if (validateServicesStatus()){

           return uploadReviewPackageEx(param);
       }
        return  null;

    }

    protected boolean validateServicesStatus(){
        String testUrl = "http://"+InicioActivity.SERVER_ADDRESS +":"+PORT+"/available";
            String output = null;

            try {
                URL url = new URL(testUrl);
                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setDoInput(true); // Allow Inputs
                    connection.setDoOutput(true); // Allow Outputs
                    connection.setUseCaches(true); //
                   // connection.setRequestProperty("Content-Type", "application/json"); //Esta propiedad
                                                                                         //puede dar problema
                                                                                         //al pasar parametros
                    connection.setRequestMethod("GET"); //GET,PUT or POST
                    connection.setConnectTimeout(TIME);
                    connection.setReadTimeout(TIME);
                    try {

                        ///Put parameter for services
                        String input = "{\"?device\"=HTC-ONE,\"action\"=\"test\"}";
                        input = "device=" + APPID;
                        //input = "";
//                        Log.d(TAG,"DIRECCION");
//                        Log.d(TAG,"URL a enviar " + connection.getURL().toString());
//                        Map<String, List<String>> map = connection.getRequestProperties();
//                        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
//                            Log.d(TAG, "entry:" + entry.getKey());
//                            for (String str : entry.getValue())
//                            { Log.d(TAG, "value:" + str); }
//                        }
                        OutputStream os = connection.getOutputStream();
                        os.write(input.getBytes());
                        os.flush();
                        os.close();
                        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                            Log.e(TAG, connection.getResponseMessage());
                            Log.e(TAG, "Error code " + connection.getResponseCode());
//                            throw new RuntimeException("Failed : HTTP error code : "
//                                    + connection.getResponseCode());
                            connection.disconnect();
                            return false;

                        }

                        Log.e(TAG, connection.getResponseMessage());
                        Log.e(TAG, "Error code " + connection.getResponseCode());

                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                (connection.getInputStream())));

                        Log.d(TAG, "Output from Server ....TEST \n");
                        while ((output = br.readLine()) != null) {
                            Log.d(TAG, output);
                        }
                        connection.disconnect();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //Put a file

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return true;
//        }

    }
    protected String uploadReviewPackageEx(String information) {
        String testUrl = "http://"+InicioActivity.SERVER_ADDRESS+":"+PORT+  "/uploadata";
        String output = null;
        String returnText = null;

        try {
            URL url = new URL(testUrl);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setDoInput(true); // Allow Inputs
                connection.setDoOutput(true); // Allow Outputs
                connection.setUseCaches(true); //
                // connection.setRequestProperty("Content-Type", "application/json"); //Esta propiedad
                //puede dar problema
                //al pasar parametros
                connection.setRequestMethod("POST"); //GET,PUT or POST
                connection.setConnectTimeout(TIME);
                connection.setReadTimeout(TIME);
                try {

                    ///Put parameter for services
                    String input = "{\"?device\"=HTC-ONE,\"action\"=\"test\"}";
                    input = "file=" + information;
                    //input = "";
//                        Log.d(TAG,"DIRECCION");
//                        Log.d(TAG,"URL a enviar " + connection.getURL().toString());
//                        Map<String, List<String>> map = connection.getRequestProperties();
//                        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
//                            Log.d(TAG, "entry:" + entry.getKey());
//                            for (String str : entry.getValue())
//                            { Log.d(TAG, "value:" + str); }
//                        }
                    OutputStream os = connection.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();
                    os.close();
                    if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e(TAG, connection.getResponseMessage());
                        Log.e(TAG, "Error code " + connection.getResponseCode());
//                            throw new RuntimeException("Failed : HTTP error code : "
//                                    + connection.getResponseCode());
                        connection.disconnect();
                        return null;

                    }

                    Log.e(TAG, connection.getResponseMessage());
                    Log.e(TAG, "Error code " + connection.getResponseCode());

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            (connection.getInputStream())));

                    Log.d(TAG, "Output from Server ....TEST \n");
                    while ((output = br.readLine()) != null) {
                        Log.d(TAG, output);
                        returnText = output;
                    }
                    connection.disconnect();

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Put a file

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return returnText;
    }
    protected boolean uploadReviewPackage(String zipFileAdress, String vTitle, String nDescription) {

        try {

            URL connectURL = null;
            String downloadURL =  "http://"+InicioActivity.SERVER_ADDRESS+":"+PORT+  "/uploadfile";
            try{
                connectURL = new URL(downloadURL);
                title= vTitle;
                description = nDescription;
            }catch(Exception ex){
                Log.i("HttpFileUpload","URL Malformatted");
            }

            // Set your file path here
            FileInputStream fileInputStream = new FileInputStream(Environment.getExternalStorageDirectory().toString()+ zipFileAdress);

            // Set your server page url (and the file title/description)

            String iFileName = "ovicam_temp_vid.mp4";
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            String Tag="fSnd";
            try
            {
                Log.e(Tag,"Starting Http File Sending to URL");

                // Open a HTTP connection to the URL
                HttpURLConnection conn = (HttpURLConnection)connectURL.openConnection();

                // Allow Inputs
                conn.setDoInput(true);
                // Allow Outputs
                conn.setDoOutput(true);
                // Don't use a cached copy.
                conn.setUseCaches(false);
                // Use a post method.
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"title\""+ lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(title);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"description\""+ lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(description);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + iFileName +"\"" + lineEnd);
                dos.writeBytes(lineEnd);

                Log.e(Tag,"Headers are written");

                // create a buffer of maximum size
                int bytesAvailable = fileInputStream.available();

                int maxBufferSize = 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[ ] buffer = new byte[bufferSize];

                // read file and write it into form...
                int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0)
                {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0,bufferSize);
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // close streams
                fileInputStream.close();

                dos.flush();

                Log.e(Tag,"File Sent, Response: "+String.valueOf(conn.getResponseCode()));

                InputStream is = conn.getInputStream();

                // retrieve the response from server
                int ch;

                StringBuffer b =new StringBuffer();
                while( ( ch = is.read() ) != -1 ){ b.append( (char)ch ); }
                String s=b.toString();
                Log.i("Response",s);
                dos.close();
            }
            catch (MalformedURLException ex)
            {
                Log.e(Tag, "URL error: " + ex.getMessage(), ex);
            }

            catch (IOException ioe)
            {
                Log.e(Tag, "IO error: " + ioe.getMessage(), ioe);
            }

        } catch (FileNotFoundException e) {
            // Error: File not found
        }
        return true;
    }
}
