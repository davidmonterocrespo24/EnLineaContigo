package com.example.david24.mapa;

import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.core.GeoPoint;

public interface MarkerEditListener {
	void onPositionRequest(GeoPoint geoPoint);
	void onMarkerAction(int marker, int action);
	void onClicMarker(String titulo, OverlayItem item);
}
