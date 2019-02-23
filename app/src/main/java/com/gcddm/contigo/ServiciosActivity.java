package com.gcddm.contigo;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gcddm.contigo.Util.PhoneNumberUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.List;

public class ServiciosActivity extends AppCompatActivity {

    ListView service_list;
    ServicioAdapter service_adapter;
    List<Servicio> servicios;
    EditText service_edit1;
    ImageButton service_icon2;
    private EditText recarga_edit;
    public static final String OWN_NUMBER = "receiver_number";
    public final int PICK_CONTACT = 1001;
    private EditText tx_amount;
    private EditText tx_pin;
    private EditText tx_receiver;
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;

    private ImageView qr_imagen;
    private EditText qr_edit;
    private Button qr_aceptar;
    private Button qr_cancelar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);
        setupActionBar();

        System.gc();

        servicios=new ArrayList<>();
        servicios.add(new Servicio(0,0,"Consultar Saldo"));
        servicios.add(new Servicio(1,1,"Recargar Saldo"));
        servicios.add(new Servicio(2,2,"Enviar Saldo"));
        servicios.add(new Servicio(3,3,"Planes de ETECSA"));
        servicios.add(new Servicio(4,4,"Capturar QR"));

        service_list=(ListView) findViewById(R.id.service_list);
        service_adapter= new ServicioAdapter(ServiciosActivity.this,servicios);
        service_list.setAdapter(service_adapter);

        service_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //final SubTemaAdapter adapter=(SubTemaAdapter) listView.getAdapter();
                Servicio servicio =service_adapter.getItem(position);

                if(servicio.getNombre().equals("Consultar Saldo")){
                    Intent intent = new Intent("android.intent.action.CALL");
                    intent.setData(Uri.parse("tel: *222" + Uri.encode("#")));
                    ServiciosActivity.this.startActivity(intent);
                }else if(servicio.getNombre().equals("Recargar Saldo")){
                    android.app.AlertDialog.Builder builder = new  android.app.AlertDialog.Builder(ServiciosActivity.this,R.style.AlertDialogCustom2);
                    builder.setTitle("Recargar saldo");
                    builder.setPositiveButton("Recargar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String numerorecarga = recarga_edit.getText().toString();
                            if(numerorecarga.length()==16){
                                Intent intent = new Intent("android.intent.action.CALL");
                                intent.setData(Uri.parse("tel: *662*" + numerorecarga + Uri.encode("#")));
                                startActivity(intent);
                            }else{
                                Toast.makeText(ServiciosActivity.this, "Código de recarga inválido. El código debe contener 16 dígitos.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    LayoutInflater inflater = ServiciosActivity.this.getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.activity_recarga, null);
                    builder.setView(alertLayout).create().show();

                    recarga_edit =(EditText) alertLayout.findViewById(R.id.recargar_edit);


                }else if(servicio.getNombre().equals("Enviar Saldo")){

                    android.app.AlertDialog.Builder builder = new  android.app.AlertDialog.Builder(ServiciosActivity.this,R.style.AlertDialogCustom2);

                    builder.setTitle("Enviar saldo");

                    builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StringBuilder sb = new StringBuilder();
                            String beneficiario = tx_receiver.getText().toString();
                            String clave = tx_pin.getText().toString();
                            String str_saldo = tx_amount.getText().toString();
                            double saldo = 0.0d;
                            if (!PhoneNumberUtils.isValidCellPhone(beneficiario)) {
                                sb.append("El número receptor debe comenzar con 5 y contener 8 dígitos.");
                                sb.append("\n");
                            }
                            if (clave.length() != 4) {
                                sb.append("El PIN debe contener 4 dígitos.");
                                sb.append("\n");
                            }
                            try {
                                saldo = Double.parseDouble(str_saldo);
                                if (saldo <= 0.0d || saldo > 2999.7d) {
                                    sb.append("El monto debe estar entre 0.01 y 2999.70.");
                                }
                            } catch (NumberFormatException e) {
                                if (saldo <= 0.0d || saldo > 2999.7d) {
                                    sb.append("El monto debe estar entre 0.01 y 2999.70.");
                                }
                            } catch (Throwable th) {
                                if (saldo <= 0.0d || saldo > 2999.7d) {
                                    sb.append("El monto debe estar entre 0.01 y 2999.70.");
                                }
                            }
                            if (sb.toString().length() == 0) {
                                String ast = Uri.encode("*");
                                String baseUssd = ast + "234" + ast + "1" + ast + beneficiario + ast + clave + ast;
                                StringBuilder builder = new StringBuilder();
                                builder.append(baseUssd);
                                builder.append(str_saldo);
                                builder.append(Uri.encode("#"));
                                ServiciosActivity.this.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + builder.toString())));
                                return;
                            }else{
                                Toast.makeText(ServiciosActivity.this,sb,Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    LayoutInflater inflater = ServiciosActivity.this.getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.activity_enviar_saldo, null);
                    builder.setView(alertLayout).create().show();

                    tx_receiver = (EditText) alertLayout.findViewById(R.id.tx_receiver);
                    tx_pin = (EditText) alertLayout.findViewById(R.id.tx_pin);
                    tx_amount = (EditText) alertLayout.findViewById(R.id.tx_amount);
                    Button contact = (Button)alertLayout.findViewById(R.id.pick_contact);
                    contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ServiciosActivity.this.startActivityForResult(new Intent("android.intent.action.PICK", ContactsContract.CommonDataKinds.Phone.CONTENT_URI), PICK_CONTACT);
                        }
                    });
                }else if(servicio.getNombre().equals("Capturar QR")){


                    final android.app.AlertDialog.Builder builder = new  android.app.AlertDialog.Builder(ServiciosActivity.this,R.style.AlertDialogCustom2);
                    builder.setTitle("QR");

                    builder.setCancelable(true);


                    LayoutInflater inflater = ServiciosActivity.this.getLayoutInflater();
                    final View alertLayout = inflater.inflate(R.layout.qr_button, null);

                    qr_edit = (EditText)alertLayout.findViewById(R.id.qr_edit);
                    qr_imagen = (ImageView) alertLayout.findViewById(R.id.qr_image);


                    final android.app.AlertDialog alertDialog = builder.setView(alertLayout).create();
                    alertDialog.show();

                    LinearLayout button =(LinearLayout) alertLayout.findViewById(R.id.qr_capturar);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (ContextCompat.checkSelfPermission(ServiciosActivity.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                                IntentIntegrator integrator = new IntentIntegrator(ServiciosActivity.this);
                                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                                integrator.setPrompt("Coloque el código QR en el recuadro");
                                integrator.setCameraId(0);  // Use a specific camera of the device
                                integrator.setBeepEnabled(true);
                                integrator.setBarcodeImageEnabled(true);
                                integrator.initiateScan();
                                alertDialog.dismiss();
                            }else{
                                Toast.makeText(ServiciosActivity.this,"Necesita permisos para utilizar la cámara del dispositivo.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    LinearLayout button2 =(LinearLayout) alertLayout.findViewById(R.id.qr_generar);
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            android.app.AlertDialog.Builder builder2 = new  android.app.AlertDialog.Builder(ServiciosActivity.this,R.style.AlertDialogCustom2);
                            builder2.setTitle("Generar QR");

                            builder2.setCancelable(true);


                            LayoutInflater inflater2 = ServiciosActivity.this.getLayoutInflater();
                            View alertLayout2 = inflater2.inflate(R.layout.activity_qr, null);

                            final android.app.AlertDialog alertDialog2 = builder2.setView(alertLayout2).create();

                            qr_edit = (EditText)alertLayout2.findViewById(R.id.qr_edit);
                            qr_imagen = (ImageView) alertLayout2.findViewById(R.id.qr_image);
                            qr_aceptar = (Button) alertLayout2.findViewById(R.id.qr_aceptar);
                            qr_cancelar = (Button) alertLayout2.findViewById(R.id.qr_cancelar);

                            qr_aceptar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String srt=qr_edit.getText().toString();
                                    if(srt.length()>0) {
                                        try {
                                            Bitmap bitmap=encodeAsBitmap(srt);
                                            qr_imagen.setImageBitmap(bitmap);
                                        } catch (WriterException e) {
                                            e.printStackTrace();
                                        }
                                    }else{
                                        qr_imagen.setImageBitmap(null);
                                        Toast.makeText(ServiciosActivity.this,"Debe escribir al menos un caracter.",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            qr_cancelar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog2.dismiss();
                                }
                            });
                            alertDialog2.show();
                            alertDialog.dismiss();



                        }
                    });
                }else if(servicio.getNombre().equals("Planes de ETECSA")){
                    Intent i = new Intent(ServiciosActivity.this, PlanesActivity.class);
                    startActivity(i);
                }
            }
        });

        service_edit1=(EditText)findViewById(R.id.service_edit1) ;
        service_edit1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                ((ArrayAdapter<String>)service_list.getAdapter()).getFilter().filter(arg0);
//                   adapter.getFilter().filter(arg0);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        service_icon2=(ImageButton)findViewById(R.id.service_icon2);
        service_icon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //service_edit1.setText("");
                int cursorPosition1 = service_edit1.getSelectionStart();
                if (cursorPosition1 > 0) {
                    service_edit1.setText(service_edit1.getText().delete(cursorPosition1 - 1, cursorPosition1));
                    service_edit1.setSelection(cursorPosition1-1);
                }
            }
        });




    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_CONTACT) {
                if(data != null) {
                    Uri result = data.getData();
                    Cursor c = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, "_id=?", new String[]{result.getLastPathSegment()}, null);
                    if (c.getCount() >= 1 && c.moveToFirst()) {
                        final String string = c.getString(c.getColumnIndex("data1"));
                        new Handler().post(new Runnable() {
                            public void run() {
                                String phone = PhoneNumberUtils.trimPhone(string.replace("*99", "").replaceAll("\\D+", ""));
                                if (PhoneNumberUtils.isValidCellPhone(phone)) {
                                    ServiciosActivity.this.tx_receiver.setText(phone);
                                } else {
                                    Toast.makeText(ServiciosActivity.this, "El número debe comenzar con 5 y contener 8 dígitos", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            }else{
                IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if(result != null) {
                    if(result.getContents() != null) {
                        new AlertDialog.Builder(ServiciosActivity.this)
                                .setTitle("Escaneado")
                                .setMessage(result.getContents())
                                .setCancelable(false)
                                .setPositiveButton("Aceptar",null)
                                .show();
                    }
                }
            }
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.

            actionBar.setDisplayHomeAsUpEnabled(true);
            // Hide the action bar title

        }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            QRCodeWriter writer = new QRCodeWriter();
            result = writer.encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

}
