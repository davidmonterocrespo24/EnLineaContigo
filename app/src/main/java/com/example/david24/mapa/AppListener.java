package com.example.david24.mapa;

public interface  AppListener {
	void onGpsSignal(double lat, double lon, float bearing);
	void onPathPositionChanged(double lat, double lon, float bearing);
	void onModeChanged();
	void onRouteChanged();
	void onRouteLost();
}
