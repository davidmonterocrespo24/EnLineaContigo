package com.gcddm.contigo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.gcddm.contigo.Util.ArrayAdapterWithIcon;
import com.gcddm.contigo.db.Contacto;
import com.soundcloud.android.crop.Crop;

import java.util.List;

/**
 * Created by Lizzy on 5/9/2017.
 */
public class ContactDialogManager {

    private Activity activity;
    private EditText editText9;
    private EditText editText10;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private EditText editText7;
    private EditText editText8;
    private EditText editText11;
    private CircularImageView user_image;
    private Spinner spinner;
    private AlertDialog custom_object_query;
    private AlertDialog.Builder builder;
    private String image_path = "";
    private ArrayAdapter<CharSequence> adapter;



    public ContactDialogManager(final Activity activity, final Context context) {
        this.activity = activity;
        //insertar objeto en base de datos y realizar el focallength
        // con este tema personalizado evitamos los bordes por defecto
        builder = new  AlertDialog.Builder(this.activity,R.style.AlertDialogCustom2);


        //obligamos al usuario a pulsar los botones para cerrarlo
        builder.setCancelable(false);

        builder.setTitle("Mis datos");

        builder.setNegativeButton(R.string.button_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                custom_object_query.dismiss();

            }
        });



        builder.setPositiveButton(R.string.button_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(ValidateFields() == true) {
                    if (validarPhoneNumber(editText10.getText().toString())){
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
                            Toast.makeText(builder.getContext(), "Configuración guardada satisfactoriamente", Toast.LENGTH_LONG).show();
                            custom_object_query.dismiss();
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

                            Toast.makeText(builder.getContext(), "Configuración guardada satisfactoriamente", Toast.LENGTH_LONG).show();
                            custom_object_query.dismiss();
                        }
                    }
                }


            }
        });



        LayoutInflater inflater = activity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.contact_layout, null);
        builder.setView(alertLayout);

        spinner = (Spinner)alertLayout.findViewById(R.id.spinner3);
        adapter = ArrayAdapter.createFromResource(
                activity, R.array.municipios, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        editText3 = (EditText)alertLayout.findViewById(R.id.editText3);
        editText4 = (EditText)alertLayout.findViewById(R.id.editText4);
        editText5 = (EditText)alertLayout.findViewById(R.id.editText5);
        editText6 = (EditText)alertLayout.findViewById(R.id.editText6);
        editText7 = (EditText)alertLayout.findViewById(R.id.editText7);
        editText8 = (EditText)alertLayout.findViewById(R.id.editText8);
        editText9 = (EditText)alertLayout.findViewById(R.id.editText9);
        editText10 = (EditText)alertLayout.findViewById(R.id.editText10);
        editText11  = (EditText)alertLayout.findViewById(R.id.editText11);
        user_image = (CircularImageView) alertLayout.findViewById(R.id.user_image);
        image_path = "";

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String [] items = new String[] {"Galería", "Cámara"};
                final Integer[] icons = new Integer[] {R.drawable.add_foto_de_galeria_50_copia, R.drawable.cargar_camara_50_copia};
                ListAdapter adapter = new ArrayAdapterWithIcon(activity, items, icons);

                AlertDialog alertDialog = new AlertDialog.Builder(activity,R.style.AlertDialogCustom2).setTitle("Seleccionar imagen")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item ) {
                                if(item == 0){

                                    Crop.pickImage(activity);

                                }else if(item == 1){
                                    Crop.takeImage(activity);
                                }
                            }
                        }).show();
            }
        });

        custom_object_query = builder.create();


    }
    public void MostraDialog(){
        List<Contacto> contacto = Contacto.listAll(Contacto.class);
        if(!contacto.isEmpty()) {
            final Contacto contacto1 = contacto.get(0);
            editText9.setText(contacto1.getNickname());
            editText3.setText(contacto1.getNombre_Apellido());
            String direccion = contacto1.getDireccion();
            chequearDireccion(direccion);
            editText8.setText(contacto1.getCorreo());
            editText10.setText(String.valueOf(contacto1.getTelefono()));
            editText11.setText(contacto1.getPassword());
            image_path = "";

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

        custom_object_query.show();

    }
    int FinSpinnerItemnPos(ArrayAdapter adapter, String value){
        for(int i = 0; i < adapter.getCount(); i++){
            if(adapter.getItem(i).toString().equals(value)){
                return i;
            }
        }
        return -1;
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
                Toast.makeText(builder.getContext(),"Los campos * son obligatorios", Toast.LENGTH_LONG).show();
            }else if(!validarEmpty(nomAp)){
                Toast.makeText(builder.getContext(),"El nombre y apellidos no tiene el formato adecuado", Toast.LENGTH_LONG).show();
            }
            editText3.requestFocus();


            return false;
        }
        if(calle_edificio.equals("") || !validarEmpty(calle_edificio)){
            if(calle_edificio.equals("")){
                Toast.makeText(builder.getContext(),"Los campos * son obligatorios", Toast.LENGTH_LONG).show();
            }else if(!validarEmpty(calle_edificio)){
                Toast.makeText(builder.getContext(),"La calle/edificio no tiene el formato adecuado", Toast.LENGTH_LONG).show();
            }
            editText4.requestFocus();

            return false;
        }
        if(!reparto.equals("") ){
            if(!validarEmpty(reparto)){
                Toast.makeText(builder.getContext(),"El reparto no tiene el formato adecuado", Toast.LENGTH_LONG).show();
                editText7.requestFocus();

                return false;
            }

        }
        if(!apto.equals("") ){
            if(!validarEmpty(apto)){
                Toast.makeText(builder.getContext(),"El apartamento no tiene el formato adecuado", Toast.LENGTH_LONG).show();
                editText5.requestFocus();

                return false;
            }

        }

        if(!entre.equals("") ){
            if(!validarEmpty(entre)){
                Toast.makeText(builder.getContext(),"El entre no tiene el formato adecuado", Toast.LENGTH_LONG).show();
                editText6.requestFocus();

                return false;
            }

        }
        if(nickname.equals("") || !validarEmpty(nickname)){
            if(nickname.equals("")){
                Toast.makeText(builder.getContext(),"Los campos * son obligatorios", Toast.LENGTH_LONG).show();
            }else if(!validarEmpty(nickname)){
                Toast.makeText(builder.getContext(),"El nickmane no tiene el formato adecuado", Toast.LENGTH_LONG).show();
            }
            editText9.requestFocus();

            return false;
        }
        if(telefono.equals("") || !validarPhoneNumber(telefono)){
            if(telefono.equals("")){
                Toast.makeText(builder.getContext(),"Los campos * son obligatorios", Toast.LENGTH_LONG).show();
            }else if(!validarPhoneNumber(telefono)){
                Toast.makeText(builder.getContext(),"El teléfono no tiene el formato adecuado", Toast.LENGTH_LONG).show();
            }
            editText10.requestFocus();

            return false;
        }
        if(!correo.equals("")) {
            if (!isValidEmail(correo)) {

                Toast.makeText(builder.getContext(), "El correo no tiene el formato adecuado. Debe ser un correo nauta", Toast.LENGTH_LONG).show();
                editText8.requestFocus();
                return false;
            }
            if(password.equals("")){
                Toast.makeText(builder.getContext(), "Debe de insertar la contraseña si desea usar el correo", Toast.LENGTH_LONG).show();
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

    public  String getImage_path() {
        return image_path;
    }

    public  void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public CircularImageView getUser_image() {
        return user_image;
    }

    public void setUser_image(Uri user_image) {
        this.user_image.setImageURI(user_image);
    }

    public void setUser_image_Bitmap(Bitmap user_image) {
        this.user_image.setImageBitmap(user_image);
    }
}
