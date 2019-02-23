package com.example.david24.mapa;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gcddm.contigo.R;
import com.gcddm.contigo.Util.SearchableSpinner;
import com.gcddm.contigo.db.Mpuntos;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import de.cm.osm2po.sd.guide.SdEvent;
import de.cm.osm2po.sd.guide.SdForecast;
import de.cm.osm2po.sd.guide.SdGuide;
import de.cm.osm2po.sd.guide.SdLocation;
import de.cm.osm2po.sd.guide.SdMessage;
import de.cm.osm2po.sd.guide.SdMessageResource;
import de.cm.osm2po.sd.routing.SdGraph;
import de.cm.osm2po.sd.routing.SdPath;
import de.cm.osm2po.sd.routing.SdRouter;
import de.cm.osm2po.sd.routing.SdTouchPoint;

import static android.telephony.TelephonyManager.CALL_STATE_IDLE;
import static android.telephony.TelephonyManager.CALL_STATE_OFFHOOK;
import static android.telephony.TelephonyManager.CALL_STATE_RINGING;
import static com.example.david24.mapa.MarkersLayer.GPS_MARKER;
import static com.example.david24.mapa.MarkersLayer.HOME_MARKER;
import static com.example.david24.mapa.MarkersLayer.POS_MARKER;
import static com.example.david24.mapa.MarkersLayer.SOURCE_MARKER;
import static com.example.david24.mapa.MarkersLayer.TARGET_MARKER;
import static com.example.david24.mapa.MarkersLayer.TOUCH_MARKER;
import static de.cm.osm2po.sd.guide.SdMessageResource.MSG_ERR_POINT_FIND;
import static de.cm.osm2po.sd.guide.SdMessageResource.MSG_ERR_ROUTE_CALC;
import static de.cm.osm2po.sd.guide.SdMessageResource.MSG_ERR_ROUTE_LOST;
import static de.cm.osm2po.sd.guide.SdMessageResource.MSG_INF_ROUTE_CALC;
import static java.lang.Thread.sleep;


public class MapaActivity extends MapActivity implements MarkerEditListener, AppListener {

    private final static int CONTACT_SELECTED = 4711;
    private final static int ACTION_MOVE = 1;
    private final static int ACTION_GOTO = 2;

    private AppState appState;
    private MapView mapView;
    private RoutesLayer routesLayer;
    private MarkersLayer markersLayer;

    private long nGpsCalls;
    private ProgressDialog progressDialog;
    private MarkerSelectDialog markerSelectDialog;


    /////////////////////////////////////////////////////////////////////
    private final SmsManager smsMan = SmsManager.getDefault();

    // private TextToSpeech tts;
    private SdGraph graph;
    private SdGuide guide;
    private File mapFile; // Mapsforge
    private AppListener appListener; // there is only one
    private Thread routingThread;
    private SdRouter router;
    private int nJitters;
    private boolean gpsProviderActive;
    Long idNombre;
    GPSTracker gps;
    double latitude = 0;
    double longitude = 0;
    Location t;
    /////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);


        progressDialog = new ProgressDialog(this, R.style.StyledDialog) {
            @Override
            public void onBackPressed() {
                cancelRouteCalculation();
                progressDialog.dismiss();
                toast("Calculation cancelled");
            }
        };
        progressDialog.setMessage("Calculating Route...");
        progressDialog.setCancelable(false);

        markerSelectDialog = new MarkerSelectDialog();

        crear();
        appState = getAppState();
        if (isRouterBusy()) progressDialog.show();


        SearchableSpinner s = (SearchableSpinner) findViewById(R.id.spinner);
        List<Mpuntos> mpuntoses = Mpuntos.listAll(Mpuntos.class);


        final String[] data = new String[mpuntoses.size()];
        for (int i = 0; i < mpuntoses.size(); i++) {
            data[i] = mpuntoses.get(i).getNombre();
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter(
                MapaActivity.this, android.R.layout.simple_spinner_item,
                data);
        s.setAdapter(adapter1);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                List<Mpuntos> p = Mpuntos.find(Mpuntos.class, "nombre = ?", data[position]);
                GeoPoint g = new GeoPoint(p.get(0).getLat(), p.get(0).getLon());
                mapView.setCenter(g);
                mapView.getController().setZoom(18);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        mapView = (MapView) findViewById(R.id.mapView);
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setMapFile(getMapFile());
        mapView.getController().setZoom(25);


        routesLayer = new RoutesLayer();
        mapView.getOverlays().add(routesLayer);


        markersLayer = new MarkersLayer(this, this, mpuntoses.size());

        for (int i = 0; i < mpuntoses.size(); i++) {
            Mpuntos a = mpuntoses.get(i);
            GeoPoint geoPoint = new GeoPoint(a.getLat(), a.getLon());
            markersLayer.moveMarker(8 + i, geoPoint);
        }


        mapView.getOverlays().add(markersLayer);
        gps = new GPSTracker(MapaActivity.this);
        setAppListener(this);
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    // check if GPS enabled
                     t=  gps.getLocation();

                    // \n is for new line
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            gps = new GPSTracker(MapaActivity.this);

                            // check if GPS enabled
                            if(gps.canGetLocation()){

                                  gps.getLatitude();
                              gps.getLongitude();

                                // \n is for new line
                                Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                            }else{
                                // can't get location
                                // GPS or Network is not enabled
                                // Ask user to enable GPS/network in settings
                                gps.showSettingsAlert();
                            }

                        }
                    });


                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //


                }
            }
        }).start();

        mapView.setCenter(new GeoPoint(20.020402, -75.827826));
        mapView.getController().setZoom(25);
        appState.setNavMode(true);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveViewState();
        setAppListener(null); // Important! Decouple from App!
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // Empty for portrait-mode
    }

    @Override
    public void onModeChanged() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        onModeChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onGpsSignal(double lat, double lon, float bearing) {
        GeoPoint geoPoint = new GeoPoint(lat, lon);
        markersLayer.moveMarker(GPS_MARKER, geoPoint, bearing);
        appState.setGpsPos(geoPoint);
        if (appState.isPanMode()) {
            if (nGpsCalls == 0) mapView.setCenter(geoPoint);
            if (++nGpsCalls > 10) nGpsCalls = 0;
        }
    }

    @Override
    public void onPathPositionChanged(double lat, double lon, float bearing) {
        GeoPoint geoPoint = new GeoPoint(lat, lon);
        //  lblSpeed.setText(getKmh() + " km/h");
        markersLayer.moveMarker(POS_MARKER, geoPoint, bearing);
    }

    @Override
    public void onPositionRequest(GeoPoint geoPoint) {
        if (!appState.isNavMode()) {

            markerSelectDialog.show(ACTION_MOVE, getFragmentManager());
        }
    }

    @Override
    public void onMarkerAction(int marker, int action) {
        switch (action) {
            case ACTION_MOVE:
                moveMarker(marker);
                break;
            default:
                gotoMarker(marker);
                break;
        }
    }

    public void gotoMarker(int marker) {
        GeoPoint geoPoint = markersLayer.getMarkerPosition(marker);
        if (geoPoint != null) {
            mapView.setCenter(geoPoint);
        } else {
            toast("Marker not yet set");
        }
    }

    public void moveMarker(int marker) {
        GeoPoint geoPoint = markersLayer.getLastTouchPosition();
        double lat = geoPoint.getLatitude();
        double lon = geoPoint.getLongitude();

        if (GPS_MARKER == marker) {
            markersLayer.moveMarker(GPS_MARKER, geoPoint);
            appState.setGpsPos(geoPoint);
            navigate(lat, lon); // Simulation
            return;
        }

        if (HOME_MARKER == marker) {
            markersLayer.moveMarker(marker, geoPoint);
            return;
        }

        SdTouchPoint tp = SdTouchPoint.create(
                getGraph(), (float) lat, (float) lon, !appState.isBikeMode());

        if (marker == SOURCE_MARKER) {
            appState.setSource(tp);
        } else if (marker == TARGET_MARKER) {
            appState.setTarget(tp);
        }

        if (tp != null) {
            geoPoint = new GeoPoint(tp.getLat(), tp.getLon());
            markersLayer.moveMarker(marker, geoPoint);
        } else {
            speak(toast(MSG_ERR_POINT_FIND.getMessage()));
        }

        route();
    }

    private void route() {
        if (runRouteCalculation()) {
            progressDialog.show();
            // lblSpeed.setText(null);
        }
    }

    @Override
    public void onRouteLost() {
        markersLayer.moveMarker(TOUCH_MARKER, appState.getGpsPos()); // fake
        onMarkerAction(SOURCE_MARKER, ACTION_MOVE); // Fake
    }


    private String toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        return msg;
    }

    @Override
    public void onRouteChanged() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                SdPath path = appState.getPath();
                if (null == path) {
                    speak(toast(MSG_ERR_ROUTE_CALC.getMessage()));
                }
                routesLayer.drawPath(getGraph(), path);
            }
        });
    }

    private void saveViewState() {
        GeoPoint gpMap = mapView.getMapPosition().getMapCenter();
        appState.setMapPos(gpMap);
        GeoPoint gpHome = markersLayer.getMarkerPosition(HOME_MARKER);
        appState.setHomePos(gpHome);
        GeoPoint gpGps = markersLayer.getMarkerPosition(GPS_MARKER);
        appState.setGpsPos(gpGps);
        appState.setMapZoom(mapView.getMapPosition().getZoomLevel());

    }

    private void restoreViewState() {

        int zoom = appState.getMapZoom();
        if (zoom > 0) mapView.getController().setZoom(zoom);
        GeoPoint gpMap = appState.getMapPos();
        if (gpMap != null) mapView.setCenter(gpMap);
        GeoPoint gpHome = appState.getHomePos();
        if (gpHome != null) markersLayer.moveMarker(HOME_MARKER, gpHome);
        GeoPoint gpGps = appState.getGpsPos();
        if (gpGps != null) markersLayer.moveMarker(GPS_MARKER, gpGps);

       /* tglCarOrBike.setChecked(!appState.isBikeMode());
        tglToneOrQuiet.setChecked(!appState.isQuietMode());
        tglPanOrHold.setChecked(appState.isPanMode());
        tglNaviOrEdit.setChecked(appState.isNavMode());*/

        SdTouchPoint source = appState.getSource();
        if (source != null) {
            GeoPoint geoPoint = new GeoPoint(source.getLat(), source.getLon());
            markersLayer.moveMarker(SOURCE_MARKER, geoPoint);
        }

        SdTouchPoint target = appState.getTarget();
        if (target != null) {
            GeoPoint geoPoint = new GeoPoint(target.getLat(), target.getLon());
            markersLayer.moveMarker(TARGET_MARKER, geoPoint);
        }

        routesLayer.drawPath(getGraph(), appState.getPath());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO this code is not optimal
        if (CONTACT_SELECTED == requestCode && resultCode != 0) {
            if (data != null) {
                Uri uri = data.getData();

                if (uri != null) {
                    Cursor c = null;
                    try {
                        c = getContentResolver().query(uri, new String[]{
                                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                                        ContactsContract.CommonDataKinds.Phone.TYPE},
                                null, null, null);

                        if (c != null && c.moveToFirst()) {
                            String number = c.getString(0);
                            //int type = c.getInt(1);
                            toast("Position sent to " + number);

                        }
                    } finally {
                        if (c != null) {
                            c.close();
                        }
                    }
                }
            }
        }
    }


    @Override
    public void onClicMarker(String titulo, OverlayItem item) {
        GeoPoint overlayItem = item.getPoint();
        String[] array = new String[2];
        array[0] = String.valueOf(overlayItem.getLatitude());
        array[1] = String.valueOf(overlayItem.getLongitude());

        List<Mpuntos> mpuntosList = Mpuntos.find(Mpuntos.class, "lat = ? and lon = ?", array);

        if (item != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setCancelable(true);

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.dialogo_punto, null);

            TextView t = (TextView) v.findViewById(R.id.textView7);


            TextView t2 = (TextView) v.findViewById(R.id.dialogdireccion);


            // toast(String.valueOf(item.getPoint().getLatitude()), String.valueOf(item.getPoint().getLongitude());
            if (mpuntosList.size() != 0) {
                //   builder.setTitle();
                t.setText(mpuntosList.get(0).getNombre());
                t2.setText(mpuntosList.get(0).getDireccion());
                idNombre = mpuntosList.get(0).getId();
                builder.setView(v);
                builder.setMessage(item.getSnippet());
                builder.setPositiveButton("Ver Más", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(MapaActivity.this, Informacion.class);
                        i.putExtra("id", idNombre.toString());
                        startActivity(i);

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }
    }


    void crear() {

        copiar();

        File pathCacheFile = new File(getCacheDir(), "contigo.tmp");
        File[] files = getAppDir().listFiles();
        for (int i = 0; i < files.length; i++) {
            int index = files[i].getPath().lastIndexOf('.');
            if (files[i].getPath().substring(index + 1).equals("gpt")) {
                graph = new SdGraph(files[i]);
            }
            if (files[i].getPath().substring(index + 1).equals("map")) {
                mapFile = files[i];
            }
        }


        appState = new AppState().restoreAppState(graph.getId());
        router = new SdRouter(graph, pathCacheFile);

        SdPath path = appState.getPath();
        boolean bikeMode = appState.isBikeMode();
        guide = (null == path) ? null : new SdGuide(
                SdForecast.create(SdEvent.create(graph, path, !bikeMode, !bikeMode)));


        TelephonyManager tm = (TelephonyManager) getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        int events = PhoneStateListener.LISTEN_CALL_STATE;
        tm.listen(new PhoneListener(), events);


    }


    /************************************************************************************************/
    public final File getAppDir() {
        File sdcard = Environment.getExternalStorageDirectory();
        File sdDir = new File(sdcard, "Contigo");
        if (!sdDir.exists()) {
            sdDir.mkdir();
        }
        return sdDir;
    }

    private void registerTracks() {
        File dir = new File(getAppDir(), "tracks");
        for (SdMessageResource msg : SdMessageResource.values()) {
            String key = msg.getKey();
            if (null == key) continue;

            String audioFileName = key.replace('.', '_') + ".mp3";
            File audioFile = new File(dir, audioFileName);
            if (audioFile.exists()) {
                //  tts.addSpeech(msg.getMessage(), audioFile.getAbsolutePath());
            }
        }
    }

    public File getMapFile() {
        return mapFile;
    }

    public SdGraph getGraph() {
        return graph;
    }

    public void setAppListener(AppListener appListener) {
        this.appListener = appListener;
    }


    public void activateGps() {
        //  if (!isGpsOn()) {
        Intent gpsSettings = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        gpsSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(gpsSettings);
        Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        sendBroadcast(intent);
        //  }
    }


    public AppState getAppState() {
        return appState;
    }


    /********************************
     * SD
     **********************************/

    public boolean isRouterBusy() {
        return routingThread != null && routingThread.isAlive();
    }

    public boolean runRouteCalculation() {

        if (isRouterBusy() || null == appState.getSource() || null == appState.getTarget()) {
            return false;
        }

        speak(MSG_INF_ROUTE_CALC.getMessage());

        routingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    calculateRoute();
                } catch (Exception e) {
                }
            }
        });
        routingThread.setPriority(Thread.MAX_PRIORITY);
        routingThread.start();
        return true;
    }

    private void calculateRoute() {
        boolean bikeMode = appState.isBikeMode();
        SdPath path = router.findPath(
                appState.getSource(), appState.getTarget(), !bikeMode);
        appState.setPath(path);
        guide = (null == path) ? null : new SdGuide(
                SdForecast.create(SdEvent.create(graph, path, !bikeMode, !bikeMode)));
        if (appListener != null) appListener.onRouteChanged();
    }

    public void cancelRouteCalculation() {
        router.cancel();
    }

    /********************************
     * GPS
     *********************************/

    public int getKmh() {
        return guide.getKmh();
    }

    private boolean isNavigateBusy;

    /**
     * This method will be called by real GPS and Simulation.
     *
     * @param lat double Latitude
     * @param lon double Longitude
     */
    public void navigate(double lat, double lon) {
        try {
            appState.setGpsPos(new GeoPoint(lat, lon));

            if (appListener != null) {

                if (isNavigateBusy || isRouterBusy()) return;
                isNavigateBusy = true;

                if (guide != null) {
                    SdLocation loc = SdLocation.snap(graph, appState.getPath(), lat, lon);
                    if (loc.getJitter() < 50) {
                        nJitters = 0;

                        appListener.onPathPositionChanged(loc.getLat(), loc.getLon(), loc.getBearing());

                        SdMessage[] msgs = guide.lookAhead(loc.getMeter(), false);
                        if (msgs != null) {
                            if (appState.isQuietMode()) {
                                String s = "";
                                for (SdMessage msg : msgs) s += msg.getMessage() + " ";
                                toast(s);
                            } else {
                                for (SdMessage msg : msgs) speak(msg.getMessage());
                            }
                        }

                    } else {
                        if (++nJitters > 10) {
                            speak(MSG_ERR_ROUTE_LOST.getMessage());
                            this.appListener.onRouteLost();
                            nJitters = 0;
                        }
                    }
                }
            }

        } catch (Throwable t) {
            // if (tts.isSpeaking()) tts.stop();
            // speak("Error " + t.getMessage());

        } finally {
            isNavigateBusy = false;
        }
    }


    /********************************
     * TTS
     *********************************/

    public void setQuietMode(boolean quietMode) {
        appState.setQuietMode(quietMode);
        if (quietMode) {
            //   if (tts.isSpeaking()) tts.stop();
        }
    }

    public boolean isQuietMode() {
        return appState.isQuietMode();
    }

    public void speak(String msg) {
        if (appState.isQuietMode()) return;
        if (null == msg) return;

        //tts.speak(msg, QUEUE_ADD, null);
    }


    /************************** Toast *************************************/


    /***********************
     * Phone observer
     *******************************/

    private class PhoneListener extends PhoneStateListener {
        private boolean restoreTone;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case CALL_STATE_RINGING:
                    // speak("Telefon " + incomingNumber);
                    break;
                case CALL_STATE_OFFHOOK:

                    restoreTone = !isQuietMode();
                    setQuietMode(true);
                    break;
                case CALL_STATE_IDLE:
                    if (restoreTone) setQuietMode(false);
                    restoreTone = false;
                    break;
            }
        }

    }

    void copiar() {
        if (!new File(getAppDir() + "/mapa.map").exists()) {
            try {

                InputStream inputStream = getAssets().open("mapa.map");
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                File targetFile = new File(getAppDir() + "/mapa.map");
                OutputStream outStream = new FileOutputStream(targetFile);
                outStream.write(buffer);
                outStream.close();

            } catch (IOException e) {
                toast("No hay suficiente espacio para mostrar los mapas");
                finish();
            }
        }

        if (!new File(getAppDir() + "/ruta.gpt").exists()) {
            try {
                InputStream inputStream = getAssets().open("ruta.gpt");
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                File targetFile = new File(getAppDir() + "/ruta.gpt");
                OutputStream outStream = new FileOutputStream(targetFile);
                outStream.write(buffer);
                outStream.close();
            } catch (IOException e) {

                finish();
            }
        }

    }


}

