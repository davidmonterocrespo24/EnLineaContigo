package com.gcddm.contigo.Util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gcddm.contigo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileActivity extends Activity implements OnItemClickListener{
	public static final int REQUEST_STORAGE = 9888;
	private List<String> lItem = null;
	private List<String> lPath = null;
	private String strRoot;
	private TextView tvPath;
	private ListView lvList;
	private View view;
	ArrayList<Lista> datosItem = null;
	//para saber si estoy en la carpeta raiz de la microSD/ carpeta raiz memoria del telefono
	private boolean bStorageRoot,
			bPhoneRoot;
	private String Path;

	Comparator<File> fileComparator = new Comparator<File>() {

		@Override
		public int compare(File file1, File file2) { //Ordenar los subtemas, primero las carpetas y despues los archivos
			if (file1.isDirectory()){
				if (file2.isDirectory()){
					return file1.getName().toLowerCase().compareTo(file2.getName().toLowerCase());
				}
				else {
					return -1;
				}
			}
			else {
				if (file2.isDirectory()){
					return 1;
				}
				else {
					return file1.getName().toLowerCase().compareTo(file2.getName().toLowerCase());
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_main);

		view = (View) findViewById(R.id.view);
		tvPath = (TextView)findViewById(R.id.path);
		lvList = (ListView)findViewById(R.id.list);
		lvList.setOnItemClickListener(this);

		if (Build.VERSION.SDK_INT >= 23) {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
					== PackageManager.PERMISSION_GRANTED &&
					ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
					== PackageManager.PERMISSION_GRANTED) {
				ShowPhoneStorages();
			} else {
				ActivityCompat.requestPermissions(this,
						new String[] { Manifest.permission.READ_EXTERNAL_STORAGE,
								Manifest.permission.WRITE_EXTERNAL_STORAGE },
						REQUEST_STORAGE);
			}
		}
		else{
			ShowPhoneStorages();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (requestCode == REQUEST_STORAGE){
			if (grantResults.length == 2 &&
					grantResults[0] == PackageManager.PERMISSION_GRANTED &&
					grantResults[1] == PackageManager.PERMISSION_GRANTED){
				ShowPhoneStorages();
			}
			else{
				Snackbar.make(view, "Sin el permiso, no puedo realizar la" +
						"acción", Snackbar.LENGTH_SHORT).show();

			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		if (bStorageRoot && lPath.get(position).equals(".../")) {
			ShowPhoneStorages(); //Mostrar la raiz del telef y de la MicroSD
			return;
		}

		if (bPhoneRoot){
			strRoot = lPath.get(position);
			bPhoneRoot = false;
		}

		final File file = new File(lPath.get(position)); //Tomo la nueva posicion del directorio

		if (file.isDirectory()){
			if (file.canRead()){
				getDir(lPath.get(position));
			}
			else {
				new AlertDialog.Builder(this)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle("[" + file.getName() + "] no se puede leer el directorio.")
						.setPositiveButton("Aceptar", null)
						.show();
			}
		}
		else {
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setMessage("¿Este es el comprimido que desea cargar?")
					.setTitle("[" + file.getName() + "]")
					.setPositiveButton("Sí", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Path += "/" +  file.getName();

							Intent intent = new Intent();
							intent.putExtra("compactado", Path);

							setResult(RESULT_OK, intent);
							finish();
						}
					})
					.setNegativeButton("No", null)
					.show();
		}
	}
	public  int  check_where_are_we(String ruta){
		int  resp = 0;
		if(!ruta.equals("")) {
			if(ruta.equals(Environment.getExternalStorageDirectory().getPath())){
				resp = 0;
			}else if (ruta.equals("/storage/sdcard0")) {
				resp = 1;
			}else if (ruta.equals("/storage/sdcard1")) {
				resp = 2;
			}else if (ruta.equals("/storage/sdcard2")) {
				resp = 3;
			}else if (ruta.equals("/storage/extsd")) {
				resp = 4;
			}else if (ruta.equals("/storage/usbhost1")) {
				resp = 5;
			}
		}
		return  resp;
	}
	private void getDir(String strPath) {
		tvPath.setText("Ubicación: " + strPath);
		Path = strPath;

		//lItem = new ArrayList<String>();
		datosItem = new ArrayList<Lista>();
		lPath = new ArrayList<String>();
		File f = new File(strPath);

		File files[] = f.listFiles(); //retorna un arreglo con los archivos q contiene la carpeta

		/*if (!strPath.equals(strRoot)){
			lItem.add(strRoot);
			lPath.add(strRoot);
			lItem.add(".../"); //para ir hacia atras
			lPath.add(f.getParent());
			bStorageRoot = false;
		}
		else {
			lItem.add(".../");
			lPath.add(".../");
			bStorageRoot = true;
		}*/
		if (!strPath.equals(strRoot)){
			if(check_where_are_we(strRoot)==0) {
				datosItem.add(new Lista(R.drawable.sdcard1, "Almacenamiento primario", ""));
			}else if(check_where_are_we(strRoot)==1){
				datosItem.add(new Lista(R.drawable.sdcard1, "Tarjeta SD 0", ""));
			}else if(check_where_are_we(strRoot)==2){
				datosItem.add(new Lista(R.drawable.sdcard1, "Tarjeta SD 1", ""));
			}else if(check_where_are_we(strRoot)==3){
				datosItem.add(new Lista(R.drawable.sdcard1, "Tarjeta SD 2", ""));
			}else if(check_where_are_we(strRoot)==4){
				datosItem.add(new Lista(R.drawable.sdcard1, "Tarjeta SD Externa", ""));
			}else if(check_where_are_we(strRoot)==5){
				datosItem.add(new Lista(R.drawable.sdcard1, "Memoria USB 1", ""));
			}

			lPath.add(strRoot);
			datosItem.add(new Lista(R.drawable.atras,"Atrás",""));
			lPath.add(f.getParent());
			bStorageRoot = false;
		}
		else {
			if(strPath.equals(Environment.getExternalStorageDirectory().getPath())){
				//lItem.add(".../");
				datosItem.add(new Lista(R.drawable.sdcard1,".../",""));
			}
			if(strPath.equals("/storage/sdcard0") && !strPath.equals(Environment.getExternalStorageDirectory().getPath())){
				// lItem.add(".../");
				datosItem.add(new Lista(R.drawable.sdcard1,".../",""));
			}
			if(strPath.equals("/storage/sdcard1") && !strPath.equals(Environment.getExternalStorageDirectory().getPath())){
				// lItem.add(".../");
				datosItem.add(new Lista(R.drawable.sdcard1,".../",""));
			}
			if(strPath.equals("/storage/sdcard2") && !strPath.equals(Environment.getExternalStorageDirectory().getPath())){
				// lItem.add(".../");
				datosItem.add(new Lista(R.drawable.sdcard1,".../",""));
			}
			if(strPath.equals("/storage/extsd") && !strPath.equals(Environment.getExternalStorageDirectory().getPath())){
				// lItem.add(".../");
				datosItem.add(new Lista(R.drawable.sdcard1,".../",""));
			}
			if(strPath.equals("/storage/usbhost1") && !strPath.equals(Environment.getExternalStorageDirectory().getPath())){
				// lItem.add(".../");
				datosItem.add(new Lista(R.drawable.sdcard1,".../",""));
			}

			lPath.add(".../");
			bStorageRoot = true;
		}

		//Ordenar los archivos
		Arrays.sort(files, fileComparator); //sort ordena por el criterio del fileComparator

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (!file.isHidden() && file.canRead()){ //isHidden para saber si esta oculta
				lPath.add(file.getPath());

				if (file.isDirectory()){ // si es un directorio
					//lItem.add(file.getName() + "/");
					datosItem.add(new Lista(R.drawable.carpet,file.getName()+"/",""));
				}
				else { //si es un archivo
					//lItem.add(file.getName());
					if(file.getName().endsWith(".zip")){
						datosItem.add(new Lista(R.drawable.zip,file.getName(),""));
					}
					else{
						datosItem.add(new Lista(R.drawable.file,file.getName(),""));
					}

				}
			}
		}

		//ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lItem);
		//lvList.setAdapter(arrAdapter);
		lvList = (ListView) findViewById(R.id.list);
		lvList.setAdapter(new ListaAdapter(this, R.layout.texto_fila, datosItem){

			public void onEntrada(Object entrada, View view) {
				if (entrada != null) {
					TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
					if (texto_superior_entrada != null)
						texto_superior_entrada.setText(((Lista) entrada).get_textoEncima());

					TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
					if (texto_inferior_entrada != null)
						texto_inferior_entrada.setText(((Lista) entrada).get_textoDebajo());

					ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
					if (imagen_entrada != null)
						imagen_entrada.setImageResource(((Lista) entrada).get_idImagen());
				}
			}
		});
	}

	private void ShowPhoneStorages(){
		tvPath.setText("Ubicación: Raíz");

		//lItem = new ArrayList<String>();
		lPath = new ArrayList<String>();
		datosItem = new ArrayList<Lista>();
		//Environment.getRootDirectory() devuelve el camino del directorio raiz
		//lItem.add(Environment.getRootDirectory().getPath());
		//lPath.add(Environment.getRootDirectory().getPath());
		String ext="";
		if (isExternalStorageReady()){
			//lItem.add(Environment.getExternalStorageDirectory().getPath());
			//lPath.add(Environment.getExternalStorageDirectory().getPath());
			ext=Environment.getExternalStorageDirectory().getPath();
			lPath.add(ext);
			datosItem.add(new Lista(R.drawable.sdcard1,"Almacenamiento primario",""));
		}
		String sd00="/storage/sdcard0";
		if(!sd00.equals(ext)) {
			File sd0 = new File(sd00);
			if (sd0.isDirectory()) {
				if (sd0.canRead()) {
					lPath.add(sd00);
					datosItem.add(new Lista(R.drawable.sdcard1, "Tarjeta SD 0", ""));
				}
			}
		}
		String sd11="/storage/sdcard1";
		if(!sd11.equals(ext)) {
			File sd1 = new File(sd11);
			if (sd1.isDirectory()) {
				if (sd1.canRead()) {
					lPath.add(sd11);
					datosItem.add(new Lista(R.drawable.sdcard1, "Tarjeta SD 1", ""));
				}
			}
		}

		String sd22="/storage/sdcard2";
		if(!sd22.equals(ext)) {
			File sd2 = new File(sd22);
			if (sd2.isDirectory()) {
				if (sd2.canRead()) {
					lPath.add(sd22);
					datosItem.add(new Lista(R.drawable.sdcard1, "Tarjeta SD 2", ""));
				}
			}
		}

		String extsd="/storage/extsd";
		if(!extsd.equals(ext)) {
			File sd3 = new File(extsd);
			if (sd3.isDirectory()) {
				if (sd3.canRead()) {
					lPath.add(extsd);
					datosItem.add(new Lista(R.drawable.sdcard1, "Tarjeta SD Externa", ""));
				}
			}
		}

		String usb="/storage/usbhost1";
		if(!usb.equals(ext)) {
			File sd4 = new File(usb);
			if (sd4.isDirectory()) {
				if (sd4.canRead()) {
					lPath.add(usb);
					datosItem.add(new Lista(R.drawable.sdcard1, "Memoria USB 1", ""));
				}
			}
		}


		//ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lItem);
		//	lvList.setAdapter(arrAdapter);
		//	bStorageRoot = false;
		//	bPhoneRoot = true;
		lvList = (ListView) findViewById(R.id.list);
		lvList.setAdapter(new ListaAdapter(this, R.layout.texto_fila, datosItem){

			public void onEntrada(Object entrada, View view) {
				if (entrada != null) {
					TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
					if (texto_superior_entrada != null)
						texto_superior_entrada.setText(((Lista) entrada).get_textoEncima());

					TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
					if (texto_inferior_entrada != null)
						texto_inferior_entrada.setText(((Lista) entrada).get_textoDebajo());

					ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
					if (imagen_entrada != null)
						imagen_entrada.setImageResource(((Lista) entrada).get_idImagen());
				}
			}
		});
		bStorageRoot = false;
		bPhoneRoot = true;
	}
	//Comprueba q la microSD estï¿½ lista para usarse
	private boolean isExternalStorageReady(){
		String state = Environment.getExternalStorageState();
//Para saber si esta montada
		if (state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}
		else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
			return true;
		}

		return false;
	}
}
