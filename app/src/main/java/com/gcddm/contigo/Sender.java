package com.gcddm.contigo;

import android.content.Context;
import android.content.ContextWrapper;

/**
 * Created by Gilbert on 5/11/2017.
 */
public class Sender extends ContextWrapper{
    private static Sender ourInstance = new Sender();
    public static int SEND_NICKNAME_ONLY = 0;
    public static int SEND_NAME_ONLY = 1;
    public static int SEND_ALL = 2;
    public static int SEND_FROM_DATA_BASE = 3;
    private static Context context = null;

    public static Sender getInstance(final Context c) {
        context = c;
        return ourInstance;
    }

    private Sender() {
        super(context);
    }

    public boolean sendMessage(final StringProcess sp, final int dataToSend)
    {
        if(sp.getClass().getSimpleName().equals("SmsString"))
        {
            String sms[] = null;
            if(dataToSend == SEND_NICKNAME_ONLY)
                sms = sp.parseAnonymousMode();
            else if(dataToSend == SEND_NAME_ONLY)
                sms = sp.parseShortMode();
            else
                sms = sp.parseFullMode();
            return SMSDriver.getInstance().send(sms[0], sms[1]);
        }
        if(sp.getClass().getSimpleName().equals("MailString"))
        {
            String email[] = null;
            if(dataToSend == SEND_NICKNAME_ONLY)
                email = sp.parseAnonymousMode();
            else if(dataToSend == SEND_NAME_ONLY)
                email = sp.parseShortMode();
            else
                email = sp.parseFullMode();
            return EmailDriver.getInstance(context).send(new String[]{email[0], email[1], email[2]}, email[3], email[4],new WifiString(context).parseFullMode()[1]);
        }
        if(sp.getClass().getSimpleName().equals("WifiString"))
        {
            if(dataToSend == SEND_NICKNAME_ONLY)
                sp.parseAnonymousMode();
            else if(dataToSend == SEND_NAME_ONLY)
                sp.parseShortMode();
            else if(dataToSend == SEND_ALL)
                sp.parseFullMode();
            else {
                sp.parseFromDataBase();
                return WifiDriver.getInstance(context).send(sp.getXmlString(),false);
            }
            return WifiDriver.getInstance(context).send(sp.getXmlString(),true);
        }
        return false;
    }

}
