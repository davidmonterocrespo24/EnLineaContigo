package com.gcddm.contigo;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.gcddm.contigo.Util.ArrayAdapterWithIcon;
import com.gcddm.contigo.db.Contacto;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.List;

public class  SettingsActivity extends AppCompatActivity {
    private EditText editText9;
    private EditText editText10;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private EditText editText7;
    private EditText editText8;
    private EditText editText11;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private String image_path;
    private CircularImageView user_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion_activity);

        System.gc();

        editText3 = (EditText)findViewById(R.id.editText3);
        editText4 = (EditText)findViewById(R.id.editText4);
        editText5 = (EditText)findViewById(R.id.editText5);
        editText6 = (EditText)findViewById(R.id.editText6);
        editText7 = (EditText)findViewById(R.id.editText7);
        editText8 = (EditText)findViewById(R.id.editText8);
        editText9 = (EditText)findViewById(R.id.editText9);
        editText10 = (EditText)findViewById(R.id.editText10);
        editText11  = (EditText)findViewById(R.id.editText11);
        user_image = (CircularImageView)findViewById(R.id.user_image);


        editText9.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_SPACE) {
                    if( event.getAction() == KeyEvent.ACTION_UP ) {
                        return false;
                    }
                    return true;
                }

                return false;
            }
        });



        editText3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER ) {
                    if( event.getAction() == KeyEvent.ACTION_UP ) {
                        return false;
                    }
                    return true;
                }

                return false;
            }
        });



        editText4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER ) {
                    if( event.getAction() == KeyEvent.ACTION_UP ) {
                        return false;
                    }
                    return true;
                }

                return false;
            }
        });




        editText5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_SPACE) {
                    if( event.getAction() == KeyEvent.ACTION_UP ) {
                        return false;
                    }
                    return true;
                }

                return false;
            }
        });
        editText6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER ) {
                    if( event.getAction() == KeyEvent.ACTION_UP ) {
                        return false;
                    }
                    return true;
                }

                return false;
            }
        });
        editText7.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER ) {
                    if( event.getAction() == KeyEvent.ACTION_UP ) {
                        return false;
                    }
                    return true;
                }

                return false;
            }
        });

        editText8.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER ) {
                    if( event.getAction() == KeyEvent.ACTION_UP ) {
                        return false;
                    }
                    return true;
                }

                return false;
            }
        });

        editText11.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_SPACE) {
                    if( event.getAction() == KeyEvent.ACTION_UP ) {
                        return false;
                    }
                    return true;
                }

                return false;
            }
        });




        image_path = "";

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String [] items = new String[] {"Galería", "Cámara"};
                final Integer[] icons = new Integer[] {R.drawable.add_foto_de_galeria_50_copia, R.drawable.cargar_camara_50_copia};
                ListAdapter adapter = new ArrayAdapterWithIcon(SettingsActivity.this, items, icons);

                AlertDialog alertDialog = new AlertDialog.Builder(SettingsActivity.this,R.style.AlertDialogCustom2).setTitle("Seleccionar imagen")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item ) {
                                if(item == 0){

                                    Crop.pickImage(SettingsActivity.this);

                                }else if(item == 1){

                                    Crop.takeImage(SettingsActivity.this);

                                }
                            }
                        }).show();

            }
        });

        spinner = (Spinner)findViewById(R.id.spinner3);
        adapter = ArrayAdapter.createFromResource(
                SettingsActivity.this, R.array.municipios, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


    }


    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            user_image.setImageURI(null);

            Uri output = Crop.getOutput(result);
            user_image.setImageURI(output);

            image_path = output.getPath();

            BitmapDrawable bitmapDrawable = (BitmapDrawable)user_image.getDrawable();
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

            user_image.setImageBitmap(resized);
            user_image.refreshDrawableState();

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(SettingsActivity.this,"Lo sentimos hubo un problema con la cámara",Toast.LENGTH_LONG).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Crop.REQUEST_PICK) {
                beginCrop(data.getData());
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(resultCode, data);
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ic_menu_configuracion, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        List<Contacto> contacto = Contacto.listAll(Contacto.class);
        if(!contacto.isEmpty()) {
            Contacto contacto1 = contacto.get(0);
            editText9.setText(contacto1.getNickname());
            editText3.setText(contacto1.getNombre_Apellido());
            String direccion = contacto1.getDireccion();
            chequearDireccion(direccion);
            editText8.setText(contacto1.getCorreo());


            editText10.setText(String.valueOf(contacto1.getTelefono()));
            editText11.setText(contacto1.getPassword());
            int pos = FinSpinnerItemnPos(adapter,contacto1.getMunicipio());
            spinner.setSelection(pos);
            Bitmap myBitmap = BitmapFactory.decodeFile(contacto1.getImagen());

            if(myBitmap != null) {
                Bitmap resized = Bitmap.createScaledBitmap(myBitmap, 100, 100, false);



                try {
                    ExifInterface exif = new ExifInterface(contacto1.getImagen());
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
                user_image.setImageBitmap(resized);
            }

        }
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cerrar:
                // TODO put your code here to respond to the button tap
                this.finish();
                return true;
            case R.id.ok:
                // TODO put your code here to respond to the button tap
                //almacenar contacto en la base de datos
                if(isValuesChanged()) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this, R.style.AlertDialogCustom2);

                    builder.setTitle("Guardar configuración")
                            .setMessage("Desea guardar su configuración?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (ValidateFields() == true) {
                                        if (validarPhoneNumber(editText10.getText().toString())) {
                                            if (editText8.getText().toString().matches("")) {
                                                String direccion = editText4.getText().toString() + "%" + editText5.getText().toString() + "%" + editText6.getText().toString() + "%" + editText7.getText().toString() + "%";

                                                List<Contacto> contacto = Contacto.listAll(Contacto.class);

                                                if (contacto.isEmpty()) {
                                                    Contacto contacto1 = new Contacto(editText9.getText().toString(), editText3.getText().toString(), Integer.valueOf(editText10.getText().toString()), direccion, editText8.getText().toString(), spinner.getSelectedItem().toString(), image_path, editText11.getText().toString());
                                                    contacto1.save();
                                                } else {
                                                    Contacto contacto2 = contacto.get(0);
                                                    contacto2.setNombre_apellido(editText3.getText().toString());
                                                    contacto2.setNickname(editText9.getText().toString());
                                                    contacto2.setTelefono(Integer.valueOf(editText10.getText().toString()));
                                                    contacto2.setDireccion(direccion);
                                                    contacto2.setCorreo("");
                                                    contacto2.setPassword("");
                                                    contacto2.setMunicipio(spinner.getSelectedItem().toString());
                                                    contacto2.save();
                                                }
                                                Toast.makeText(SettingsActivity.this, "Configuración guardada satisfactoriamente", Toast.LENGTH_LONG).show();
                                                SettingsActivity.this.finish();
                                            } else if (isValidEmail(editText8.getText().toString())) {

                                                String direccion = editText4.getText().toString() + "%" + editText5.getText().toString() + "%" + editText6.getText().toString() + "%" + editText7.getText().toString() + "%";

                                                List<Contacto> contacto = Contacto.listAll(Contacto.class);

                                                if (contacto.isEmpty()) {
                                                    Contacto contacto1 = new Contacto(editText9.getText().toString(), editText3.getText().toString(), Integer.valueOf(editText10.getText().toString()), direccion, editText8.getText().toString(), spinner.getSelectedItem().toString(), image_path,editText11.getText().toString());
                                                    contacto1.save();
                                                } else {
                                                    Contacto contacto2 = contacto.get(0);
                                                    contacto2.setNombre_apellido(editText3.getText().toString());
                                                    contacto2.setNickname(editText9.getText().toString());
                                                    contacto2.setTelefono(Integer.valueOf(editText10.getText().toString()));
                                                    contacto2.setDireccion(direccion);
                                                    contacto2.setCorreo(editText8.getText().toString());
                                                    contacto2.setPassword(editText11.getText().toString());
                                                    contacto2.setMunicipio(spinner.getSelectedItem().toString());

                                                    if (image_path.matches("")) {
                                                        image_path = contacto2.getImagen();
                                                    }
                                                    contacto2.setImagen(image_path);
                                                    contacto2.save();
                                                }

                                                Toast.makeText(getApplicationContext(), "Configuración guardada satisfactoriamente", Toast.LENGTH_LONG).show();
                                                SettingsActivity.this.finish();
                                            }
                                        }
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SettingsActivity.this.finish();
                                }
                            })
                            .create().show();
                }else{
                    SettingsActivity.this.finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean ValidateFields(){
        String nomAp = editText3.getText().toString();
        String calle_edificio = editText4.getText().toString();
        String reparto = editText7.getText().toString();
        String nickname = editText9.getText().toString();
        String telefono = editText10.getText().toString();
        String correo = editText8.getText().toString();
        String apto = editText5.getText().toString();
        String entre = editText6.getText().toString();
        String password = editText11.getText().toString();

        if(nomAp.equals("") || !validarEmpty(nomAp)){
            if(nomAp.equals("")){
                Toast.makeText(SettingsActivity.this,"Los campos * son obligatorios", Toast.LENGTH_LONG).show();
            }else if(!validarEmpty(nomAp)){
                Toast.makeText(SettingsActivity.this,"El nombre y apellidos no tiene el formato adecuado", Toast.LENGTH_LONG).show();
            }
            editText3.requestFocus();

            return false;
        }
        if(calle_edificio.equals("") || !validarEmpty(calle_edificio)){
            if(calle_edificio.equals("")){
                Toast.makeText(SettingsActivity.this,"Los campos * son obligatorios", Toast.LENGTH_LONG).show();
            }else if(!validarEmpty(calle_edificio)){
                Toast.makeText(SettingsActivity.this,"La calle/edificio no tiene el formato adecuado", Toast.LENGTH_LONG).show();
            }
            editText4.requestFocus();
            return false;
        }
        if(!reparto.equals("") ){
            if(!validarEmpty(reparto)){
                Toast.makeText(SettingsActivity.this,"El reparto no tiene el formato adecuado", Toast.LENGTH_LONG).show();
                editText7.requestFocus();
                return false;
            }
        }
        if(!apto.equals("") ){
            if(!validarEmpty(apto)){
                Toast.makeText(SettingsActivity.this,"El apartamento no tiene el formato adecuado", Toast.LENGTH_LONG).show();
                editText5.requestFocus();
                return false;
            }
        }

        if(!entre.equals("") ){
            if(!validarEmpty(entre)){
                Toast.makeText(SettingsActivity.this,"El entre no tiene el formato adecuado", Toast.LENGTH_LONG).show();
                editText6.requestFocus();
                return false;
            }
        }
        if(nickname.equals("") || !validarEmpty(nickname)){
            if(nickname.equals("")){
                Toast.makeText(SettingsActivity.this,"Los campos * son obligatorios", Toast.LENGTH_LONG).show();
            }else if(!validarEmpty(nickname)){
                Toast.makeText(SettingsActivity.this,"El nickmane no tiene el formato adecuado", Toast.LENGTH_LONG).show();
            }
            editText9.requestFocus();
            return false;
        }
        if(telefono.equals("") || !validarPhoneNumber(telefono)){
            if(telefono.equals("")){
                Toast.makeText(SettingsActivity.this,"Los campos * son obligatorios", Toast.LENGTH_LONG).show();
            }else if(!validarPhoneNumber(telefono)){
                Toast.makeText(SettingsActivity.this,"El teléfono no tiene el formato adecuado", Toast.LENGTH_LONG).show();
            }
            editText10.requestFocus();
            return false;
        }

        if(!correo.equals("")) {
            if (!isValidEmail(correo)) {

                Toast.makeText(SettingsActivity.this, "El correo no tiene el formato adecuado. Debe ser un correo nauta", Toast.LENGTH_LONG).show();
                editText8.requestFocus();
                return false;
            }
            if(password.equals("")){
                Toast.makeText(SettingsActivity.this, "Debe de insertar la contraseña si desea usar el correo", Toast.LENGTH_LONG).show();
                editText11.requestFocus();
                return false;
            }
        }


        return true;

    }

    public boolean validarEmpty(String temp) {
        if (temp.length() > 0) {
            if (temp.startsWith(" ") || temp.startsWith("\n")) {
                return false;
            } else {
                if (temp.trim().length() > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public boolean validarPhoneNumber(String phone){
        if(phone.startsWith("5") && phone.length() == 8){
            return true;
        }
        return false;
    }

     int FinSpinnerItemnPos(ArrayAdapter adapter, String value){
        for(int i = 0; i < adapter.getCount(); i++){
            if(adapter.getItem(i).toString().equals(value)){
                return i;
            }
        }
        return -1;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;

        } else if(android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            if(target.toString().endsWith("@nauta.cu")){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if(isValuesChanged()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this, R.style.AlertDialogCustom2);

            builder.setTitle("Guardar configuración")
                    .setMessage("Desea guardar su configuración?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean result = ValidateFields();
                            if (result) {
                                if (editText8.getText().toString().matches("")) {

                                    String direccion = editText4.getText().toString() + "%" + editText5.getText().toString() + "%" + editText6.getText().toString() + "%" + editText7.getText().toString() + "%";

                                    List<Contacto> contacto = Contacto.listAll(Contacto.class);

                                    if (contacto.isEmpty()) {
                                        Contacto contacto1 = new Contacto(editText9.getText().toString(), editText3.getText().toString(), Integer.valueOf(editText10.getText().toString()), direccion, editText8.getText().toString(), spinner.getSelectedItem().toString(), image_path,editText11.getText().toString());
                                        contacto1.save();
                                    } else {
                                        Contacto contacto2 = contacto.get(0);
                                        contacto2.setNombre_apellido(editText3.getText().toString());
                                        contacto2.setNickname(editText9.getText().toString());
                                        contacto2.setTelefono(Integer.valueOf(editText10.getText().toString()));
                                        contacto2.setDireccion(direccion);
                                        contacto2.setCorreo("");
                                        contacto2.setPassword("");
                                        contacto2.setMunicipio(spinner.getSelectedItem().toString());
                                        contacto2.save();
                                    }
                                    Toast.makeText(SettingsActivity.this, "Configuración guardada satisfactoriamente", Toast.LENGTH_LONG).show();
                                } else if (isValidEmail(editText8.getText().toString())) {


                                    String direccion = editText4.getText().toString() + "%" + editText5.getText().toString() + "%" + editText6.getText().toString() + "%" + editText7.getText().toString() + "%";

                                    List<Contacto> contacto = Contacto.listAll(Contacto.class);

                                    if (contacto.isEmpty()) {
                                        Contacto contacto1 = new Contacto(editText9.getText().toString(), editText3.getText().toString(), Integer.valueOf(editText10.getText().toString()), direccion, editText8.getText().toString(), spinner.getSelectedItem().toString(), image_path,editText11.getText().toString());
                                        contacto1.save();
                                    } else {
                                        Contacto contacto2 = contacto.get(0);
                                        contacto2.setNombre_apellido(editText3.getText().toString());
                                        contacto2.setNickname(editText9.getText().toString());
                                        contacto2.setTelefono(Integer.valueOf(editText10.getText().toString()));
                                        contacto2.setDireccion(direccion);
                                        contacto2.setCorreo(editText8.getText().toString());
                                        contacto2.setPassword(editText11.getText().toString());
                                        contacto2.setMunicipio(spinner.getSelectedItem().toString());

                                        if (image_path.matches("")) {
                                            image_path = contacto2.getImagen();
                                        }
                                        contacto2.setImagen(image_path);
                                        contacto2.save();
                                    }

                                    Toast.makeText(getApplicationContext(), "Configuración guardada satisfactoriamente", Toast.LENGTH_LONG).show();
                                    SettingsActivity.this.finish();
                                }
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SettingsActivity.this.finish();
                        }
                    })
                    .create().show();
        }else{
            SettingsActivity.this.finish();
        }

    }

    private void chequearDireccion(String direccion){
        String [] split = new String[4];
        int iter = 0;
        String val = "";
        for(int i = 0; i < direccion.length(); i++){
            if(direccion.charAt(i)=='%'){
                split[iter] = val;
                iter++;
                val = "";
            }else {
                val += direccion.charAt(i);
            }
        }
        editText4.setText(split[0]);
        editText5.setText(split[1]);
        editText6.setText(split[2]);
        editText7.setText(split[3]);
    }

    private boolean isValuesChanged(){
        List<Contacto> contactos = Contacto.listAll(Contacto.class);

        if(contactos != null) {
            if (!contactos.isEmpty()){
                Contacto contacto = contactos.get(0);
                String nomAp = editText3.getText().toString();
                String nickname = editText9.getText().toString();
                String telefono = editText10.getText().toString();
                String correo = editText8.getText().toString();
                String password = editText11.getText().toString();
                String direccion = editText4.getText().toString() + "%" + editText5.getText().toString() + "%" + editText6.getText().toString() + "%" + editText7.getText().toString() + "%";
                if (!nomAp.matches(contacto.getNombre_Apellido())) {
                    return true;
                } else if (!nickname.matches(contacto.getNickname())) {
                    return true;
                } else if (!telefono.matches(String.valueOf(contacto.getTelefono()))) {
                    return true;
                } else if (!correo.matches(contacto.getCorreo())) {
                    return true;
                } else if (!direccion.matches(contacto.getDireccion())) {
                    return true;
                }else if (!password.matches(contacto.getPassword())) {
                    return true;
                }
            }else{
                return  true;
            }
        }else{
            return  true;
        }
        if(!image_path.matches("")){
            return true;
        }

        return false;

    }
}
