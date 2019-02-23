package com.gcddm.contigo;

import android.content.Context;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.Html;

import java.util.ArrayList;

/**
 * Created by Gilbert on 5/10/2017.
 */
public class SMSDriver {
    private static SMSDriver Instance = new SMSDriver();

    public static SMSDriver getInstance() {
        return Instance;
    }

    private SMSDriver() {}

    public boolean send(final String phoneNumber, final String text){
        try
        {
            SmsManager manager = SmsManager.getDefault();
            String utf8String = Html.fromHtml(new String(text.getBytes("UTF-8"))).toString();
            if(utf8String.length() <= 140){
                manager.sendTextMessage(phoneNumber,null,utf8String,null,null);
            }else {
                ArrayList<String> parts = manager.divideMessage(utf8String);
                manager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
            }
            return true;

        }
        catch (Exception e)
        {
            return false;
        }
        finally
        {
            return false;
        }
    }

}
