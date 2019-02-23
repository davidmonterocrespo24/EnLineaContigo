package com.gcddm.contigo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Gilbert on 5/12/2017.
 */
public class SearchNetworksReceiver extends BroadcastReceiver {

    public static final String CONNECTION_ESTABLISHED = "contigo.networking.intent.connectionEstablished";

    @Override
    public void onReceive(final Context context, Intent intent)
    {   Toast.makeText(context,"se inicio la busqueda",Toast.LENGTH_LONG).show();
        if(WifiDriver.getInstance(context).correctNetworks())
            if(WifiDriver.getInstance(context).connectToNetwork())
            {
                Intent message = new Intent();
                message.setAction(CONNECTION_ESTABLISHED);
                context.sendBroadcast(message);
            }
    }
}
