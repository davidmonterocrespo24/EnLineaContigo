package com.example.david24.mapa;


import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.ArrayItemizedOverlay;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import com.gcddm.contigo.R;
import com.gcddm.contigo.db.Mpuntos;

public class MarkersLayer extends ArrayItemizedOverlay {

    private MarkerEditListener listener;
    private ArrayList<OverlayItem> markers; // Erlaubt den Zugriff via MarkerTypeEnum
    public final static int HOME_MARKER = 0;
    public final static int TOUCH_MARKER = 1;
    public final static int GPS_MARKER = 2;
    public final static int POS_MARKER = 3;
    public final static int ALERT_MARKER = 4;
    public final static int SOURCE_MARKER = 5;
    public final static int TARGET_MARKER = 6;

    public MarkersLayer(MarkerEditListener listener, Activity activity,int cant) {
        super(null);
        this.listener = listener;
        ArrayList<Marker> mrk = new ArrayList<>();

        mrk.add(new Marker(R.drawable.ic_home_black_24dp, true));
        mrk.add(new Marker(R.drawable.ic_touch16, false));
        mrk.add(new Marker(R.drawable.ic_gps32, false));
        mrk.add(new Marker(R.drawable.ic_pos48, false));
        mrk.add(new Marker(R.drawable.ic_alert48, true));
        mrk.add(new Marker(R.drawable.ic_source32, true));
        mrk.add(new Marker(R.drawable.ic_target32, true));
        mrk.add(new Marker(R.drawable.ic_alert48, true));
        for (int i=0;i<cant;i++){
            mrk.add(new Marker(R.drawable.ic_source32,true));

        }

        int nMtes = mrk.size();
        markers = new ArrayList<>();
        for (int i = 0; i < nMtes; i++) {
            Marker mte = mrk.get(i);
            Drawable drawable = activity.getResources().getDrawable(mte.getIconId());
            if (mte.isBottomCenter()) {
                ArrayItemizedOverlay.boundCenterBottom(drawable);
            } else {
                if (drawable instanceof BitmapDrawable) {
                    drawable = new RotatableBitmapDrawable(((BitmapDrawable) drawable).getBitmap());
                }
                ArrayItemizedOverlay.boundCenter(drawable);
            }
            markers.add(new OverlayItem(null, "Titulo", null, drawable));
            addItem(markers.get(i));
        }
    }

    public void moveMarker(int marcador, GeoPoint geoPoint, float rotate) {
        OverlayItem overlayItem = markers.get(marcador);
        Drawable drawable = overlayItem.getMarker();
        if (drawable instanceof RotatableBitmapDrawable) {
            ((RotatableBitmapDrawable) drawable).rotate(rotate);
        }
        moveMarker(marcador, geoPoint);
    }

    public void moveMarker(int marcador, GeoPoint geoPoint) {
        markers.get(marcador).setPoint(geoPoint);
        requestRedraw();
    }

    public GeoPoint getMarkerPosition(int marcador) {
        return markers.get(marcador).getPoint();
    }

    public GeoPoint getLastTouchPosition() {
        return markers.get(1).getPoint();
    }

    @Override
    public boolean onLongPress(GeoPoint geoPoint, MapView mapView) {
        moveMarker(1, geoPoint);
        listener.onPositionRequest(geoPoint);
        return true;
    }

    @Override
    protected boolean onTap(final int index) {
        OverlayItem item = createItem(index);
        listener.onClicMarker("Moncada", item);
        return super.onTap(index);
    }

    public ArrayList<OverlayItem> getMarkers() {
        return markers;
    }

    public void setMarkers(ArrayList<OverlayItem> markers) {
        this.markers = markers;
    }
}
