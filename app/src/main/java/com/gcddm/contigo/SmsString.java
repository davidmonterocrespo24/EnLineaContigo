package com.gcddm.contigo;

import java.util.Vector;

/**
 * Created by Gilbert on 5/10/2017.
 */
public class SmsString extends StringProcess {
    @Override
    public String[] parseAnonymousMode() {
        String sms[] = new String[2];
        sms[0] = receptor;
        sms[1] = "@" + nickname + "@" + "(" + asunto + ")" + "&" + body + "&";
        return sms;
    }

    @Override
    public String[] parseShortMode() {
        String sms[] = new String[2];
        sms[0] = receptor;
        sms[1] = "$" + name + "$" + "(" + asunto + ")" + "&" + body + "&";
        return sms;
    }

    @Override
    public String[] parseFullMode() {
        String sms[] = new String[2];
        sms[0] = receptor;
        sms[1] = "$" + name + "$" + "@" + nickname + "@" + "#"+ phone + "#" + "*" + address + "*" + "!" + email + "!" + "(" + asunto + ")" + "&" + body + "&";
        return sms;
    }

    @Override
    public String[] parseFromDataBase() {
        return new String[0];
    }

    @Override
    public String getXmlString() {
        return null;
    }
}
