package com.gcddm.contigo;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gilbert on 5/11/2017.
 */
public class WifiDriver extends ContextWrapper {

    private static WifiDriver Instance = null;
    private Context context = null;
    private WifiManager manager = null;
    private List<ScanResult> wifiList = null;
    public static String SSID1 = "\"WIFI_ETECSA\"";
    public static String SSID2 = "\"WIFI-DESOFT\"";
    public static String SSID3 = "\"prueba\"";
    public static String ssid1 = "WIFI_ETECSA";
    public static String ssid2 = "WIFI-DESOFT";
    public static String ssid3 = "prueba";
    public static String SEND_USER_OPERATION_ACTIVE = "contigo.networking.intent.sendUserOperationActive";
    private String answerFromServer;
    private int netId;
    private boolean sendingData = false;

    public static WifiDriver getInstance(final Context c) {
        if(Instance == null)
            Instance = new WifiDriver(c);
        return Instance;
    }

    private WifiDriver(final Context c) {
        super(c);
        context = c;
        manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public boolean turnOnWifi()
    {
        return manager.setWifiEnabled(true);
    }

    public boolean turnOffWifi()
    {
        return manager.setWifiEnabled(false);
    }

    synchronized public void updateWifiList()
    {
        manager.startScan();
        wifiList = manager.getScanResults();
    }

    public boolean correctNetworks()
    {
        updateWifiList();
        if(wifiList != null && !wifiList.isEmpty())
        {
            for(int i = 0; i < wifiList.size(); i++)
                if(/*wifiList.get(i).SSID.equals(ssid1) || wifiList.get(i).SSID.equals(ssid2) ||*/ wifiList.get(i).SSID.equals(ssid3))
                    return true;
        }
        return  false;
    }

    public WifiManager getWifiManager()
    {
        return  manager;
    }

    /**
     * Funcion que se encarga de una vez activada la wifi conectarse a
     * una de las redes conocidas y configuradas
     * @return true/false
     */
    public boolean connectToNetwork()
    {
        if(correctNetworks())
        {
            List<WifiConfiguration> configuredNets = manager.getConfiguredNetworks();
            if(configuredNets != null && !configuredNets.isEmpty())
            {
                WifiConfiguration tmpConfig;
                for(int i = 0; i < configuredNets.size(); i++)
                {
                    tmpConfig = configuredNets.get(i);
                    if(/*tmpConfig.SSID.equals(SSID1) || tmpConfig.SSID.equals(SSID2) ||*/ tmpConfig.SSID.equals(SSID3))
                    {
                        netId = tmpConfig.networkId;
                        return manager.enableNetwork(netId, false);
                    }
                }
            }
        }
        return false;
    }

    public boolean isSendingData()
    {
        return  sendingData;
    }

    synchronized public boolean send(final String xmlPath,boolean desactivateWifi) {
        if (turnOnWifi()) {
//            boolean flag = false;//retardo para esperar q se ective
//            while(!flag) {
//                if (manager.getWifiState() == WifiManager.WIFI_STATE_ENABLED)
//                    flag = true;
            //}
            try{Thread.sleep(10000);}catch(InterruptedException e){}
            if (connectToNetwork()) {
                UpFileServices upLoadService = new UpFileServices();
                answerFromServer = upLoadService.upLoad(xmlPath);
                if(desactivateWifi)
                    turnOffWifi();
                return (answerFromServer != null);
            }
            if(desactivateWifi)
                turnOffWifi();
        }
        return false;
    }
}
