package com.gcddm.contigo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gcddm.contigo.Util.AdjuntosAdapter;
import com.gcddm.contigo.db.Contacto;
import com.gcddm.contigo.db.Programa;
import com.gcddm.contigo.db.Review;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Lizzy on 5/8/2017.
 */
public class PublicarActivity extends AppCompatActivity{

    private TextView textView22;
    private TextView textView21;
    private TextView textView2;
    private TextView conteo;
    private ImageView imageView10;
    private ImageView imageView11;
    private ImageView imageView12;
    private Spinner spinner_tema;
    private EditText editText2;
    private CircularImageView imagen;
    private ContactDialogManager conact;
    private TextView temaCaliente;
    private CheckBox sms_check;
    private CheckBox correo_check;
    private CheckBox wifi_check;
    private Button enviar_opinion;
    private Button cancelar_opinion;
    private ListView ItemList;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private RadioButton radioButton6;
    private ArrayList<HashMap<String, String>> detailss;
    private RadioGroup radioGroup;

    private int mNotificationsCount = 0;

    private int cant_mensajes = 1;


    private EditText mensaje;
    // action bar
    private ActionBar actionBar;
    public static final int PICK_IMAGE = 111;
    public static final int TAKE_IMAGE = 211;
    public static final int TAKE_IMAGE_2 = 311;
    private List<Contacto> contacto;

    private long review_id;
    private boolean update_review = false;
    private  ArrayAdapter adapter;



    // Title navigation Spinner data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicar_activity);
        setupActionBar();

        System.gc();

        detailss = new ArrayList<HashMap<String, String>>();

        conact = new ContactDialogManager(PublicarActivity.this,getApplicationContext());
        textView22 = (TextView)findViewById(R.id.textView22);
        textView21 = (TextView)findViewById(R.id.textView21);
        imageView10 = (ImageView)findViewById(R.id.imageView10);
        imageView11 = (ImageView)findViewById(R.id.imageView11);
        //imageView12 = (ImageView)findViewById(R.id.imageView12);
        enviar_opinion = (Button)findViewById(R.id.enviar_opinion);
        cancelar_opinion = (Button)findViewById(R.id.cancelar_opinion);
        conteo = (TextView)findViewById(R.id.conteo);
        radioButton4 =(RadioButton)findViewById(R.id.radioButton4);
        radioButton5 =(RadioButton)findViewById(R.id.radioButton5);
        radioButton6 =(RadioButton)findViewById(R.id.radioButton6);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        textView2 = (TextView) findViewById(R.id.textView2);

        ItemList = (ListView)findViewById(R.id.lista_adjuntos);


        spinner_tema = (Spinner) findViewById(R.id.spinner_tema);
        final ArrayList<String> contactlist= new ArrayList<String>();
        List<Programa> programas = Programa.listAll(Programa.class);
        for(int i = 0; i < programas.size(); i++){
            contactlist.add(programas.get(i).getNombre());
        }
        if(programas.isEmpty()) {
            contactlist.add("En línea contigo");
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, contactlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_tema.setAdapter(adapter);


        editText2 = (EditText)findViewById(R.id.editText2);

        sms_check = (CheckBox)findViewById(R.id.sms_check);
        correo_check = (CheckBox)findViewById(R.id.correo_check);
        wifi_check = (CheckBox)findViewById(R.id.wifi_check);

        radioButton4.setChecked(true);

        sms_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correo_check.isChecked()){
                    correo_check.setChecked(false);
                }
                if(wifi_check.isChecked()){
                    wifi_check.setChecked(false);

                }
                smsCounter();
                detailss = new ArrayList<HashMap<String, String>>();
                AdjuntosAdapter adapter = new AdjuntosAdapter(PublicarActivity.this, detailss,ItemList);
                ItemList.setAdapter(adapter);
                textView22.setVisibility(View.VISIBLE);
                conteo.setVisibility(View.VISIBLE);

            }
        });
        correo_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sms_check.isChecked()){
                    sms_check.setChecked(false);
                }
                if(wifi_check.isChecked()){
                    wifi_check.setChecked(false);
                }
                textView22.setVisibility(View.INVISIBLE);
                conteo.setVisibility(View.INVISIBLE);

            }
        });
        wifi_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sms_check.isChecked()){
                    sms_check.setChecked(false);
                }
                if(correo_check.isChecked()){
                    correo_check.setChecked(false);
                }
                textView22.setVisibility(View.INVISIBLE);
                conteo.setVisibility(View.INVISIBLE);

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                smsCounter();
            }
        });


        temaCaliente = (TextView) findViewById(R.id.textView3);

        imagen = (CircularImageView)findViewById(R.id.circleImage);

        List<Contacto> contactos = Contacto.listAll(Contacto.class);
        if(!contactos.isEmpty()) {
            Contacto c = contactos.get(0);
            Bitmap myBitmap = BitmapFactory.decodeFile(c.getImagen());

            if (myBitmap != null) {
                Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 100, 100, false);

                try {
                    ExifInterface exif = new ExifInterface(c.getImagen());
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                    Matrix matrix = new Matrix();
                    if (orientation == 6) {
                        matrix.postRotate(90);
                    } else if (orientation == 3) {
                        matrix.postRotate(180);
                    } else if (orientation == 8) {
                        matrix.postRotate(270);
                    }
                    resized = Bitmap.createBitmap(resized, 0, 0, resized.getWidth(), resized.getHeight(), matrix, true); // rotating bitmap
                } catch (Exception e) {

                }
                imagen.setImageBitmap(resized);
            }
        }

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conact.MostraDialog();
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conact.MostraDialog();
            }
        });


        mensaje = (EditText)findViewById(R.id.editText2);

        mensaje.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                smsCounter();


            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sms_check.isChecked()) {
                    if (detailss.size() < 3) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, TAKE_IMAGE);
                    } else {
                        Toast.makeText(PublicarActivity.this, "Solo puede seleccionar 3 adjuntos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PublicarActivity.this, "Esta opción para sms esta deshabilitada", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sms_check.isChecked()) {
                    if (detailss.size() < 3) {
                        //Intent intent = new Intent();
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        //intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Seleccione imagen"), PICK_IMAGE);
                    } else {
                        Toast.makeText(PublicarActivity.this, "Solo puede seleccionar 3 adjuntos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(PublicarActivity.this, "Esta opción para sms esta deshabilitada", Toast.LENGTH_SHORT).show();
                }


            }
        });

        enviar_opinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contacto = Contacto.listAll(Contacto.class);
                if(contacto.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe configurar su contacto", Toast.LENGTH_LONG).show();
                    conact.MostraDialog();
                }else if(!ValidateOpinionFields()){
                    Toast.makeText(getApplicationContext(), "Los campos con * son obligatorios", Toast.LENGTH_LONG).show();
                }else{
                    //sino con el alamacenado
                    Contacto contacto1 = contacto.get(0);
                    StringProcess stringProcess = null;
                    if(sms_check.isChecked()) {//sms
                        stringProcess = new SmsString();
                        stringProcess.setAddress(contacto1.getDireccion());
                        stringProcess.setName(contacto1.getNombre_Apellido());
                        stringProcess.setNickname(contacto1.getNickname());
                        stringProcess.setPhone(String.valueOf(contacto1.getTelefono()));
                        stringProcess.setBody(editText2.getText().toString());
                        stringProcess.setAsunto(spinner_tema.getSelectedItem().toString());
                        Log.i("SMS",String.valueOf(InicioActivity.PHONE_NUMBER));
                        stringProcess.setReceptor(String.valueOf(InicioActivity.PHONE_NUMBER));


                        if (radioButton5.isChecked()){

                            Sender.getInstance(PublicarActivity.this).sendMessage(stringProcess, Sender.SEND_NAME_ONLY);

                        }else if (radioButton6.isChecked()){

                            Sender.getInstance(PublicarActivity.this).sendMessage(stringProcess, Sender.SEND_NICKNAME_ONLY);

                        }else if (radioButton4.isChecked()){

                            Sender.getInstance(PublicarActivity.this).sendMessage(stringProcess, Sender.SEND_ALL);

                        }
                        Calendar cal=Calendar.getInstance();
                        long time=cal.getTimeInMillis();

                        if (detailss.isEmpty()) {
                            if(update_review == true){
                                updateReviewNoAttachments(time,contacto1,Review.ENVIADO);
                            }else {

                                createReviewNoAttachments(time,contacto1,Review.ENVIADO);
                            }

                        } else {
                            ArrayList<String> adjuntos = new ArrayList<String>();
                            for (int i = 0; i < detailss.size(); i++) {
                                adjuntos.add(detailss.get(i).get("path"));
                            }
                            if(update_review == true){
                                updateReviewAttachments(time,contacto1,adjuntos,Review.ENVIADO);
                            }else {
                                createReviewAttachments(time,contacto1,adjuntos,Review.ENVIADO);
                            }
                        }
                        update_review = false;

                        editText2.setText("");
                        conteo.setVisibility(View.INVISIBLE);
                        textView22.setVisibility(View.INVISIBLE);
                        if(sms_check.isChecked()){
                            sms_check.setChecked(false);
                        }
                        if(correo_check.isChecked()){
                            correo_check.setChecked(false);
                        }
                        if(wifi_check.isChecked()){
                            wifi_check.setChecked(false);
                        }
                        radioGroup.check(R.id.radioButton4);
                        detailss = new ArrayList<HashMap<String, String>>();
                        AdjuntosAdapter adapter = new AdjuntosAdapter(PublicarActivity.this, detailss,ItemList);

                        ItemList.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(ItemList);


                    }else if(correo_check.isChecked()) {//corre
                        if (!contacto1.getCorreo().matches("")){
                            SendEmail sendThread = new SendEmail();
                            sendThread.execute(PublicarActivity.this);

                        }else{
                            Toast.makeText(getApplicationContext(), "Usted no tiene correo configurado", Toast.LENGTH_LONG).show();
                        }

                    }else if(wifi_check.isChecked()){//wifi
                        SendThread sendThread = new SendThread();
                        sendThread.execute(PublicarActivity.this);

                    }else{
                        Toast.makeText(getApplicationContext(), "Debe de seleccionar una forma de envío", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        cancelar_opinion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicarActivity.this.finish();
            }
        });

        Bundle extras = getIntent().getExtras();

        update_review = false;
        if (extras != null) {
            update_review = true;

            review_id = extras.getLong("id");
            // and get whatever type user account id is
            Review review = Review.findById(Review.class,review_id);
            String asunto = review.getAsunto();
            String opinion = review.getMensaje();
            String adjunto1 = review.getAdjunto1();
            String adjunto2 = review.getAdjunto2();
            String adjunto3 = review.getAdjunto3();


            int pos = FinSpinnerItemnPos(adapter,asunto);

            if(pos == -1){
                contactlist.add(asunto);
                adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, contactlist);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_tema.setAdapter(adapter);
                int pos2 = FinSpinnerItemnPos(adapter,asunto);
                spinner_tema.setSelection(pos2);
            }else{
                spinner_tema.setSelection(pos);
            }
            editText2.setText(opinion);

            if(review.getDatos() == Review.ALIAS){
                radioButton6.setChecked(true);
            }else  if(review.getDatos() == Review.NOMBRE){
                radioButton5.setChecked(true);
            }else if(review.getDatos() == Review.TODO){
                radioButton4.setChecked(true);

            }

            if(review.getEnviadoPor() == Review.CORREO){
                correo_check.setChecked(true);
            }else  if(review.getEnviadoPor() == Review.SMS){
                sms_check.setChecked(true);
            }else if(review.getEnviadoPor() == Review.WIFI){
                wifi_check.setChecked(true);

            }

            if(!adjunto1.matches("")){

                HashMap<String, String> map = new HashMap<String, String>();


                map.put("fecha", "2017-02-05");
                map.put("title", "Foto");
                map.put("path", adjunto1);

                detailss.add(map);

                AdjuntosAdapter adapter = new AdjuntosAdapter(PublicarActivity.this, detailss,ItemList);

                ItemList.setAdapter(adapter);
                setListViewHeightBasedOnChildren(ItemList);
            }
            if(!adjunto2.matches("")){
                HashMap<String, String> map = new HashMap<String, String>();


                map.put("fecha", "2017-02-05");
                map.put("title", "Foto");
                map.put("path", adjunto2);

                detailss.add(map);

                AdjuntosAdapter adapter = new AdjuntosAdapter(PublicarActivity.this, detailss,ItemList);

                ItemList.setAdapter(adapter);
                setListViewHeightBasedOnChildren(ItemList);
            }
            if(!adjunto3.matches("")){
                HashMap<String, String> map = new HashMap<String, String>();


                map.put("fecha", "2017-02-05");
                map.put("title", "Foto");
                map.put("path", adjunto3);

                detailss.add(map);

                AdjuntosAdapter adapter = new AdjuntosAdapter(PublicarActivity.this, detailss, ItemList);

                ItemList.setAdapter(adapter);
                setListViewHeightBasedOnChildren(ItemList);
            }
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ic_menu_publicar, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.alerta_sms:
                // TODO put your code here to respond to the button tap
                Intent i = new Intent(PublicarActivity.this, BuzonesActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.

            actionBar.setDisplayHomeAsUpEnabled(true);
            // Hide the action bar title

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                Uri selectedImageUri = data.getData();
                String imagePath = getRealPathFromURI(selectedImageUri);
                if(imagePath != null) {
                    if (!imagePath.matches("")) {

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put("fecha", "2017-02-05");
                        map.put("title", "Foto");
                        map.put("path", imagePath);

                        detailss.add(map);

                        AdjuntosAdapter adapter = new AdjuntosAdapter(this, detailss,ItemList);


                        ItemList.setAdapter(adapter);

                        setListViewHeightBasedOnChildren(ItemList);
                    }
                }else {
                    Toast.makeText(PublicarActivity.this, "Lo sentimos hubo un problema con la cámara", Toast.LENGTH_LONG).show();
                }

            }else if(requestCode == TAKE_IMAGE){
                Uri selectedImageUri = data.getData();
                String imagePath = getRealPathFromURI(selectedImageUri);
                if(imagePath != null) {
                    if(!imagePath.matches("")) {

                        HashMap<String, String> map = new HashMap<String, String>();


                        map.put("fecha", "2017-02-05");
                        map.put("title", "Foto");
                        map.put("path", imagePath);

                        detailss.add(map);

                        AdjuntosAdapter adapter = new AdjuntosAdapter(PublicarActivity.this, detailss,ItemList);

                        ItemList.setAdapter(adapter);
                        setListViewHeightBasedOnChildren(ItemList);
                    }

                }else{
                    Toast.makeText(PublicarActivity.this,"Lo sentimos hubo un problema con la cámara",Toast.LENGTH_LONG).show();
                }

            }else if (requestCode == Crop.REQUEST_PICK) {
                beginCrop(data.getData());
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            conact.setUser_image(null);

            Uri output = Crop.getOutput(result);
            conact.setUser_image(output);
            imagen.setImageURI(output);
            conact.setImage_path(output.getPath());


            BitmapDrawable bitmapDrawable = (BitmapDrawable)conact.getUser_image().getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();

            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, false);

            try {
                ExifInterface exif = new ExifInterface(output.getPath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                }
                else if (orientation == 3) {
                    matrix.postRotate(180);
                }
                else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                resized = Bitmap.createBitmap(resized, 0, 0, resized.getWidth(), resized.getHeight(), matrix, true); // rotating bitmap
            }
            catch (Exception e) {

            }

            conact.setUser_image_Bitmap(resized);
            imagen.setImageBitmap(resized);

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(PublicarActivity.this,"Lo sentimos hubo un problema con la cámara",Toast.LENGTH_LONG).show();

        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            listItem.measure(ItemList.getWidth(), View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public String getRealPathFromURI(Uri contentUri) {

        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(PublicarActivity.this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }




    @Override
    public void onBackPressed() {
        //si por lo menos escribio algo
        String value = editText2.getText().toString();
        if(!value.matches("")) {

            //guardar en la base de datos
            List<Contacto> contacto = Contacto.listAll(Contacto.class);
            if(!contacto.isEmpty()) {

                //creo la queja y la guardo en review por enviar
                final AlertDialog.Builder builder = new AlertDialog.Builder(PublicarActivity.this);
                builder.setCancelable(true);

                builder.setTitle("Guardar opinión")
                        .setMessage("Desea guardar la opinión como borrador?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                Calendar cal=Calendar.getInstance();
                                long time=cal.getTimeInMillis();
                                List<Contacto> contacto = Contacto.listAll(Contacto.class);


                                if (detailss.isEmpty()) {
                                    if(update_review == true){
                                        updateReviewNoAttachments(time,contacto.get(0),Review.BORRADOR);
                                    }else {

                                        createReviewNoAttachments(time,contacto.get(0),Review.BORRADOR);
                                    }

                                } else {
                                    ArrayList<String> adjuntos = new ArrayList<String>();
                                    for (int i = 0; i < detailss.size(); i++) {
                                        adjuntos.add(detailss.get(i).get("path"));
                                    }
                                    if(update_review == true){
                                        updateReviewAttachments(time,contacto.get(0),adjuntos,Review.BORRADOR);
                                    }else {
                                        createReviewAttachments(time,contacto.get(0),adjuntos,Review.BORRADOR);
                                    }
                                }
                                update_review = false;

                                editText2.setText("");
                                editText2.setText("");
                                if(sms_check.isChecked()){
                                    sms_check.setChecked(false);
                                }
                                if(correo_check.isChecked()){
                                    correo_check.setChecked(false);
                                }
                                if(wifi_check.isChecked()){
                                    wifi_check.setChecked(false);
                                }
                                radioGroup.check(R.id.radioButton4);
                                detailss = new ArrayList<HashMap<String, String>>();
                                AdjuntosAdapter adapter = new AdjuntosAdapter(PublicarActivity.this, detailss,ItemList);

                                ItemList.setAdapter(adapter);
                                setListViewHeightBasedOnChildren(ItemList);
                                Toast.makeText(getApplicationContext(), "Su opinión ha sido guardada en borradores", Toast.LENGTH_LONG).show();
                                PublicarActivity.this.finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                PublicarActivity.this.finish();


                            }
                        })
                        .setIcon(R.drawable.error_para_formulario_50_copia)
                        .show();

            }else{

                final AlertDialog.Builder builder = new AlertDialog.Builder(PublicarActivity.this);
                builder.setCancelable(true);

                builder.setTitle("Información")
                        .setMessage("No tiene contacto configurado desea configurarlo ahora?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                conact.MostraDialog();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PublicarActivity.this.finish();
                            }
                        })
                        .setIcon(R.drawable.error_para_formulario_50_copia)
                        .show();


            }

        }else{

            PublicarActivity.this.finish();
        }


    }


    @Override
    protected void onResume() {

        super.onResume();

    }


    private boolean ValidateOpinionFields(){
         if(editText2.getText().toString().equals("")){
            return false;
        }
        return true;
    }
    private void smsCounter(){
        String datos= "";
        List<Contacto> contacto = Contacto.listAll(Contacto.class);

        if(!contacto.isEmpty()){
            Contacto contacto1 = contacto.get(0);
            if(radioButton4.isChecked()){
                datos += contacto1.getCorreo()+contacto1.getDireccion()+contacto1.getNombre_Apellido()+contacto1.getMunicipio()+String.valueOf(contacto1.getTelefono());
            }else if(radioButton5.isChecked()){
                datos+=contacto1.getNombre_Apellido();
            }else if(radioButton6.isChecked()){
                datos+=contacto1.getNickname();
            }

        }
        if(sms_check.isChecked()){
            cant_mensajes = ((datos.length()+ editText2.getText().length())/141)+1;
            int lenght = datos.length()+ editText2.getText().length();
            textView22.setText(lenght + "/" + String.valueOf(cant_mensajes));
        }


    }

    class SendEmail extends AsyncTask<Context,Void,Boolean>{
        ProgressDialog progressDialog;
        String body = "";
        String asunto = "";
        int radioButton = -1;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PublicarActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Enviando");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.show();
            body = editText2.getText().toString();
            asunto = (String)spinner_tema.getSelectedItem();
            if (radioButton5.isChecked()) {
                radioButton = 1;
            } else if (radioButton6.isChecked()) {
                radioButton = 2;
            } else if (radioButton4.isChecked()) {
                radioButton = 3;
            }
        }

        @Override
        protected Boolean doInBackground(Context... params) {
            Contacto contacto1 = contacto.get(0);
            StringProcess stringProcess = new MailString();
            stringProcess.setAddress(contacto1.getDireccion());
            stringProcess.setName(contacto1.getNombre_Apellido());
            stringProcess.setNickname(contacto1.getNickname());
            stringProcess.setBody(body);
            stringProcess.setPhone(String.valueOf(contacto1.getTelefono()));
            stringProcess.setReceptor(InicioActivity.EMAIL_ADDRES);
            stringProcess.setEmail(contacto1.getCorreo());
            stringProcess.setAsunto(asunto);
            stringProcess.setEmailPassword(contacto1.getPassword());

            if (radioButton == 1) {
                return Sender.getInstance(params[0]).sendMessage(stringProcess,Sender.SEND_NAME_ONLY);
            } else if (radioButton == 2) {
                return Sender.getInstance(params[0]).sendMessage(stringProcess,Sender.SEND_NICKNAME_ONLY);
            } else if (radioButton == 3) {
                return Sender.getInstance(params[0]).sendMessage(stringProcess,Sender.SEND_ALL);
            }

            return false;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if(aBoolean == Boolean.TRUE){
                Contacto contacto1 = contacto.get(0);
                Calendar cal = Calendar.getInstance();
                long time = cal.getTimeInMillis();

                if (detailss.isEmpty()) {
                    if(update_review == true){
                        updateReviewNoAttachments(time,contacto1,Review.ENVIADO);
                    }else {

                        createReviewNoAttachments(time,contacto1,Review.ENVIADO);
                    }

                } else {
                    ArrayList<String> adjuntos = new ArrayList<String>();
                    for (int i = 0; i < detailss.size(); i++) {
                        adjuntos.add(detailss.get(i).get("path"));
                    }
                    if(update_review == true){
                        updateReviewAttachments(time,contacto1,adjuntos,Review.ENVIADO);
                    }else {
                        createReviewAttachments(time,contacto1,adjuntos,Review.ENVIADO);
                    }
                }
                update_review = false;
                editText2.setText("");
                if(sms_check.isChecked()){
                    sms_check.setChecked(false);
                }
                if(correo_check.isChecked()){
                    correo_check.setChecked(false);
                }
                if(wifi_check.isChecked()){
                    wifi_check.setChecked(false);
                }
                radioGroup.check(R.id.radioButton4);
                detailss = new ArrayList<HashMap<String, String>>();
                AdjuntosAdapter adapter = new AdjuntosAdapter(PublicarActivity.this, detailss,ItemList);

                ItemList.setAdapter(adapter);
                setListViewHeightBasedOnChildren(ItemList);

            }else{
                //Toast de no se pudo enviar y que lo guarde en borradores si desea
                Toast.makeText(PublicarActivity.this,"No se pudo enviar",Toast.LENGTH_SHORT).show();
            }
        }
    }

    class SendThread extends AsyncTask<Context,Void,Boolean>
    {
        ProgressDialog progressDialog;
        String body = "";
        String asunto = "";
        int radioButton = -1;
        @Override
        protected Boolean doInBackground(Context... params) {
            Contacto contacto1 = contacto.get(0);
            StringProcess sp = new WifiString(params[0]);
            sp.setPhone(String.valueOf(contacto1.getTelefono()));
            sp.setNickname(contacto1.getNickname());
            sp.setName(contacto1.getNombre_Apellido());
            sp.setAddress(contacto1.getDireccion());
            sp.setBody(body);
            sp.setAsunto(asunto);
            if (radioButton == 1) {
                return Sender.getInstance(params[0]).sendMessage(sp,Sender.SEND_NAME_ONLY);
            } else if (radioButton == 2) {
                return Sender.getInstance(params[0]).sendMessage(sp,Sender.SEND_NICKNAME_ONLY);
            } else if (radioButton == 3) {
                return Sender.getInstance(params[0]).sendMessage(sp,Sender.SEND_ALL);
            }
            return false;


        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(PublicarActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Enviando");
            progressDialog.setCancelable(false);
            progressDialog.setMax(100);
            progressDialog.show();
            body = editText2.getText().toString();
            asunto = (String)spinner_tema.getSelectedItem();
            if (radioButton5.isChecked()) {
                radioButton = 1;
            } else if (radioButton6.isChecked()) {
                radioButton = 2;
            } else if (radioButton4.isChecked()) {
                radioButton =3;
            }
            //super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.dismiss();
            if(aBoolean == Boolean.TRUE) {
                Toast.makeText(PublicarActivity.this, "Enviado satisfactoriamente", Toast.LENGTH_SHORT).show();
                Calendar cal = Calendar.getInstance();
                long time = cal.getTimeInMillis();
                Contacto contacto1 = contacto.get(0);
                if (detailss.isEmpty()) {
                    if (update_review == true) {
                        updateReviewNoAttachments(time,contacto1,Review.ENVIADO);
                    } else {
                        createReviewNoAttachments(time,contacto1,Review.ENVIADO);
                    }

                } else {
                    ArrayList<String> adjuntos = new ArrayList<String>();
                    for (int i = 0; i < detailss.size(); i++) {
                        adjuntos.add(detailss.get(i).get("path"));
                    }
                    if (update_review == true) {
                        updateReviewAttachments(time,contacto1,adjuntos,Review.ENVIADO);
                    } else {

                        createReviewAttachments(time,contacto1,adjuntos,Review.ENVIADO);
                    }
                }
                update_review = false;

                editText2.setText("");
                if(sms_check.isChecked()){
                    sms_check.setChecked(false);
                }
                if(correo_check.isChecked()){
                    correo_check.setChecked(false);
                }
                if(wifi_check.isChecked()){
                    wifi_check.setChecked(false);
                }
                radioGroup.check(R.id.radioButton4);
                detailss = new ArrayList<HashMap<String, String>>();
                AdjuntosAdapter adapter = new AdjuntosAdapter(PublicarActivity.this, detailss, ItemList);

                ItemList.setAdapter(adapter);

            }else if(aBoolean == Boolean.FALSE){
                //Toast.makeText(PublicarActivity.this,"No se pudo enviar",Toast.LENGTH_SHORT);

                //SE PREGUNATA SI LA QUIERE PONER EN POR ENVIAR
                //creo la queja y la guardo en review por enviar
                final AlertDialog.Builder builder = new AlertDialog.Builder(PublicarActivity.this);
                builder.setCancelable(true);

                builder.setTitle("Error al enviar opinión")
                        .setMessage("Desea guardar la opinión en las POR ENVIAR?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                Calendar cal=Calendar.getInstance();
                                long time=cal.getTimeInMillis();
                                List<Contacto> contacto = Contacto.listAll(Contacto.class);

                                if (detailss.isEmpty()) {
                                    if(update_review == true){
                                        updateReviewNoAttachments(time,contacto.get(0),Review.POR_ENVIAR);
                                    }else {

                                        createReviewNoAttachments(time,contacto.get(0),Review.POR_ENVIAR);
                                    }

                                } else {
                                    ArrayList<String> adjuntos = new ArrayList<String>();
                                    for (int i = 0; i < detailss.size(); i++) {
                                        adjuntos.add(detailss.get(i).get("path"));
                                    }
                                    if(update_review == true){
                                        updateReviewAttachments(time,contacto.get(0),adjuntos,Review.POR_ENVIAR);
                                    }else {
                                        createReviewAttachments(time,contacto.get(0),adjuntos,Review.POR_ENVIAR);
                                    }
                                }
                                update_review = false;
                                editText2.setText("");
                                if(sms_check.isChecked()){
                                    sms_check.setChecked(false);
                                }
                                if(correo_check.isChecked()){
                                    correo_check.setChecked(false);
                                }
                                if(wifi_check.isChecked()){
                                    wifi_check.setChecked(false);
                                }
                                radioGroup.check(R.id.radioButton4);
                                detailss = new ArrayList<HashMap<String, String>>();
                                AdjuntosAdapter adapter = new AdjuntosAdapter(PublicarActivity.this, detailss,ItemList);

                                ItemList.setAdapter(adapter);
                                setListViewHeightBasedOnChildren(ItemList);
                                Toast.makeText(getApplicationContext(), "Su opinión ha sido guardada en POR ENVIAR", Toast.LENGTH_LONG).show();
                                PublicarActivity.this.finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                PublicarActivity.this.finish();

                            }
                        })
                        .setIcon(R.drawable.error_para_formulario_50_copia)
                        .show();

            }
            //super.onPostExecute(aBoolean);
        }
    }

    public void updateReviewNoAttachments(long time, Contacto contacto1,int estado){
        Review review = Review.findById(Review.class, review_id);
        review.setContacto(contacto1);
        review.setAsunto((String)spinner_tema.getSelectedItem());
        review.setFecha(time);
        review.setTipo(1);
        review.setMensaje(editText2.getText().toString());
        review.setEstado(estado);
        if (radioButton5.isChecked()) {

            review.setDatos(Review.NOMBRE);

        } else if (radioButton6.isChecked()) {

            review.setDatos(Review.ALIAS);

        } else if (radioButton4.isChecked()) {

            review.setDatos(Review.TODO);

        }

        if (sms_check.isChecked()) {

            review.setEnviadoPor(Review.SMS);

        } else if (wifi_check.isChecked()) {

            review.setEnviadoPor(Review.WIFI);

        } else if (correo_check.isChecked()) {

            review.setEnviadoPor(Review.CORREO);

        }
        review.save();

    }

    public void updateReviewAttachments(long time, Contacto contacto1, ArrayList<String> adjuntos,int estado ){
        Review review = Review.findById(Review.class, review_id);
        review.setContacto(contacto1);
        review.setAsunto((String)spinner_tema.getSelectedItem());
        review.setFecha(time);
        review.setTipo(1);
        review.setMensaje(editText2.getText().toString());
        review.setAdjuntos(adjuntos);
        review.setEstado(estado);
        if (radioButton5.isChecked()) {

            review.setDatos(Review.NOMBRE);

        } else if (radioButton6.isChecked()) {

            review.setDatos(Review.ALIAS);

        } else if (radioButton4.isChecked()) {

            review.setDatos(Review.TODO);

        }

        if (sms_check.isChecked()) {

            review.setEnviadoPor(Review.SMS);

        } else if (wifi_check.isChecked()) {

            review.setEnviadoPor(Review.WIFI);

        } else if (correo_check.isChecked()) {

            review.setEnviadoPor(Review.CORREO);

        }
        review.save();

    }

    public void createReviewNoAttachments(long time, Contacto contacto1,int estado){
        Review review = new Review(contacto1, 1, (String)spinner_tema.getSelectedItem(), editText2.getText().toString(), time, null);
        review.setEstado(estado);

        if (radioButton5.isChecked()) {

            review.setDatos(Review.NOMBRE);

        } else if (radioButton6.isChecked()) {

            review.setDatos(Review.ALIAS);

        } else if (radioButton4.isChecked()) {

            review.setDatos(Review.TODO);

        }

        if (sms_check.isChecked()) {

            review.setEnviadoPor(Review.SMS);

        } else if (wifi_check.isChecked()) {

            review.setEnviadoPor(Review.WIFI);

        } else if (correo_check.isChecked()) {

            review.setEnviadoPor(Review.CORREO);

        }
        review.save();

    }

    public void createReviewAttachments(long time, Contacto contacto1, ArrayList<String> adjuntos,int estado ){

        Review review = new Review(contacto1, 1, (String)spinner_tema.getSelectedItem(), editText2.getText().toString(), time, adjuntos);
        review.setEstado(estado);
        if (radioButton5.isChecked()) {

            review.setDatos(Review.NOMBRE);

        } else if (radioButton6.isChecked()) {

            review.setDatos(Review.ALIAS);

        } else if (radioButton4.isChecked()) {

            review.setDatos(Review.TODO);

        }

        if (sms_check.isChecked()) {

            review.setEnviadoPor(Review.SMS);

        } else if (wifi_check.isChecked()) {

            review.setEnviadoPor(Review.WIFI);

        } else if (correo_check.isChecked()) {

            review.setEnviadoPor(Review.CORREO);

        }
        review.save();

    }

    int FinSpinnerItemnPos(ArrayAdapter adapter, String value){
        for(int i = 0; i < adapter.getCount(); i++){
            if(adapter.getItem(i).toString().equals(value)){
                return i;
            }
        }
        return -1;
    }




}
