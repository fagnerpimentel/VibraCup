package com.vibracup.mobile;
 
import android.os.Bundle;
import com.google.android.maps.MapView;
 
// Add the following three import statements
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;
 
public class OpenMap extends MapActivity {
 
    private MapController mapController;
	private MyLocationOverlay myLocation;  
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
		// Get Mapping Controllers etc
		MapView mapView = (MapView) findViewById(R.id.map_view);
		mapController = mapView.getController();
		mapController.setZoom(11);
		mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);
		
		// Add the MyLocationOverlay
		myLocation = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocation);
		myLocation.enableMyLocation();
		myLocation.runOnFirstFix(
			new Runnable() {
				public void run() {
			    	mapController.animateTo(myLocation.getMyLocation());
				}
			}
		);
		
		DemoOverlay demoOverlay = new DemoOverlay();
		mapView.getOverlays().add(demoOverlay);
    }
    
	@Override
	protected void onResume() {
		super.onResume();
		myLocation.enableMyLocation();
	}
 
	@Override
	protected void onPause() {
		super.onPause();
		myLocation.disableMyLocation();
	}
 
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}