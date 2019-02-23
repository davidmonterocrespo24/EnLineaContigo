package com.gcddm.contigo.Util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by MIRTHA on 24/11/2016.
 */
public class Comprimido {
    public static void unZipFiles(File zipfile, String descDir, File tmp) throws Exception{
        Log.d("ALERGIAS", "Descomprimiendo");
        if (!tmp.exists()) {
            try {
                tmp.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ZipFile zf = new ZipFile(zipfile);

        for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zf.getInputStream(entry);
            OutputStream out = new FileOutputStream(tmp.getPath() + File.separator + zipEntryName);

            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
            System.out.println("Descompresión completa.");
        }

        zf.close();
    }

    public static ZipEntry buscarArchivoXml(String fileName) throws IOException{
        ZipFile zipFile = null;
        ZipEntry entry = null;

        try{
            zipFile = new ZipFile(fileName);
            Enumeration e = zipFile.entries();

            while (e.hasMoreElements()){
                entry = (ZipEntry) e.nextElement();
                String item = entry.getName();

                if (item.toLowerCase().endsWith(".xml")){
//                    inputStream = zipFile.getInputStream(entry);
                    break;
                }
            }
        } finally {
            zipFile.close();
        }

        return entry;
    }

    public static ZipEntry buscarArchivo(String fileName, String archivo) throws IOException{
        ZipFile zipFile = null;
        ZipEntry entry = null;

        try{
            zipFile = new ZipFile(fileName);
            Enumeration e = zipFile.entries();

            while (e.hasMoreElements()){
                entry = (ZipEntry) e.nextElement();
                String item = entry.getName();

                if (item.equals(archivo)){
                    break;
                }
            }
        } finally {
            zipFile.close();
        }

        return entry;
    }
}
