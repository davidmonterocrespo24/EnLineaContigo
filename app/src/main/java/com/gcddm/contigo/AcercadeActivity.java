package com.gcddm.contigo;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AcercadeActivity extends AppCompatActivity {

    TextView acercade;
    ImageView mail;
    ImageView face;
    ImageView twitter;
    ImageView phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        acercade=(TextView)findViewById(R.id.acercadeText);

        mail = (ImageView)findViewById(R.id.acerca_mail);
        face = (ImageView)findViewById(R.id.acerca_facebook);
        twitter = (ImageView)findViewById(R.id.acerca_twitter);
        phone = (ImageView)findViewById(R.id.acerca_phone);

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent sendMail = new Intent(Intent.ACTION_SEND);
                sendMail.setType("email");
                sendMail.putExtra(Intent.EXTRA_EMAIL, new String[]{"enlineacontigo@teleturquino.icrt.cu"});
                startActivity(Intent.createChooser(sendMail, "Elija aplicación para enviar"));*/
                //startActivity(sendMail);
                //EmailDriver.getInstance(AcercadeActivity.this).send(new String[]{"enlineacontigo@teleturquino.icrt.cu"}, "", "",null);
                String[] TO = {"enlineacontigo@teleturquino.icrt.cu"}; //aquí pon tu correo
                String[] CC = {""};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_CC, CC);
// Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Enviar correo..."));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(AcercadeActivity.this,
                            "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
                }
            }

        });


        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.facebook.com/enlinea.contigo");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.twitter.com/En Linea Contigo");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.CALL");
                intent.setData(Uri.parse("tel: 52886644"));
                startActivity(intent);
            }
        });
    }
}
