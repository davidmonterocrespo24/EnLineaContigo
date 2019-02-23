package com.gcddm.contigo;

/**
 * Created by Gilbert on 5/10/2017.
 */
public class MailString extends StringProcess {

    private String subject = "En línea contigo";

    MailString()
    {
        super();
    }

    @Override
    public String[] parseAnonymousMode() {
        String mail[] = new String[5];
        mail[0] = receptor;
        mail[1] = email;
        mail[2] = emailPassword;
        mail[3] = "(" + subject + ")";
        mail[4] = "@" + nickname + "@" + "(" + asunto + ")" + "&" + body + "&";
        return mail;
    }

    @Override
    public String[] parseShortMode() {
        String mail[] = new String[5];
        mail[0] = receptor;
        mail[1] = email;
        mail[2] = emailPassword;
        mail[3] = "(" + subject + ")";
        mail[4] = "$" + name + "$" + "(" + asunto + ")" +"&" + body + "&";
        return mail;
    }

    @Override
    public String[] parseFullMode() {
        String mail[] = new String[5];
        mail[0] = receptor;
        mail[1] = email;
        mail[2] = emailPassword;
        mail[3] = "(" + subject + ")";
        mail[4] = "$" + name + "$" + "@" + nickname + "@" + "#"+ phone + "#" + "*" + address + "*" + "!" + email + "!" + "(" + asunto + ")" + "&" + body + "&";
        return mail;
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
