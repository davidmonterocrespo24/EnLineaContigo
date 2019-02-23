package com.gcddm.contigo;

/**
 * Created by Gilbert on 5/10/2017.
 */
public abstract class StringProcess {

    protected String name = null, nickname = null, phone = null, address = null, email = null, emailPassword = null;
    protected   String receptor = null, body = null, asunto = null;
    protected int nameSize, lastnameSize, nicknameSize, phoneSize, addressSize, bodySize, emailSize;

    StringProcess()
    {
        name = nickname = phone = address  = receptor = body = email = asunto = emailPassword = "";
        nameSize = lastnameSize = nicknameSize = phoneSize = addressSize = bodySize = emailSize = 0;
    }

    public void setName(final String nam)
    {
        name = nam;
        nameSize = name.length() + 2;
    }

    public void setNickname(final String nick)
    {
        nickname = nick;
        nicknameSize = nickname.length() + 2;
    }

    public void setPhone(final String phoneN)
    {
        phone = phoneN;
        phoneSize = phone.length() + 2;
    }

    public void setAddress(final String add)
    {
        String [] split = new String[4];
        int iter = 0;
        String val = "";
        for(int i = 0; i < add.length(); i++){
            if(add.charAt(i)=='%'){
                split[iter] = val;
                iter++;
                val = "";
            }else {
                val += add.charAt(i);
            }
        }
        address = split[0] + " " + split[1] + " " + split[2] + " " + split[3];
        addressSize = address.length() + 2;
    }

    public void setReceptor(final  String recp)
    {
        receptor = recp;
    }

    public  void setBody(final String bod)
    {
        body = bod;
        bodySize = body.length() + 2;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public void setEmail(final String mail)
    {
        email = mail;
    }

    public void setEmailPassword(final String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getEmailPassword() {

        return emailPassword;
    }

    public int getNameSize()
    {
        return nameSize;
    }

    public int getNicknameSize()
    {
        return  nicknameSize;
    }

    public int getPhoneSize()
    {
        return  phoneSize;
    }

    public int getAddressSize()
    {
        return  addressSize;
    }

    public int getBodySize()
    {
        return  bodySize;
    }

    public int getEmailSize()
    {
        return emailSize;
    }

    public abstract String[] parseAnonymousMode();
    public abstract String[] parseShortMode();
    public abstract String[] parseFullMode();
    public abstract String[] parseFromDataBase();

    public abstract String getXmlString();
}
