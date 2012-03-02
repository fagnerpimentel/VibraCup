package com.vibracup.mobile;
 
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RatingBar;

import com.google.android.maps.MapView;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
 
import josecgomez.com.android.dev.webservice.WebService;
import josecgomez.com.android.dev.webservice.objects.alerts;

import com.vibracup.webservice.PlaceJSON;

public class OpenMap extends MapActivity {
 
    private MapController mapController;
	private MyLocationOverlay myLocation;
	private MapView mapView; 
	private RatingBar ratingBar;  
	private Places db;
	   	
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
  
        //povoando banco de dados
        this.db = new Places(this);
        this.db.deleteAll();

        this.db.insert("UNEB-Reitoria",(int)(-12.952634 * 1E6),(int)(-38.459678 * 1E6),5);
        this.db.insert("UNEB-DCET",(int)(-12.951834 * 1E6),(int)(-38.459074 * 1E6),5);
        this.db.insert("UNEB-DCH",(int)(-12.951319 * 1E6),(int)(-38.46004 * 1E6),5);
        this.db.insert("UNEB-ConsultJr",(int)(-12.951084 * 1E6),(int)(-38.459689 * 1E6),5);
        this.db.insert("UNEB-Teatro",(int)(-12.952917 * 1E6),(int)(-38.459238 * 1E6),5);
        this.db.insert("UNEB-Biblioteca",(int)(-12.953288 * 1E6),(int)(-38.458495 * 1E6),5);
        this.db.insert("UNEB-Quiosque1",(int)(-12.95145 * 1E6),(int)(-38.459149 * 1E6),5);
        this.db.insert("UNEB-Quiosque2",(int)(-12.951544 * 1E6),(int)(-38.458667 * 1E6),5);
        this.db.insert("UNEB-Quiosque3",(int)(-12.952454 * 1E6),(int)(-38.460576 * 1E6),5);
        
        this.db.insert("UNEB-Colegiado",(int)(-12.950985 * 1E6),(int)(-38.459629 * 1E6),3);
        this.db.insert("UNEB-ACSO",(int)(-12.951293 * 1E6),(int)(-38.459515 * 1E6),5);
        this.db.insert("Home",(int)(-12.951536 * 1E6),(int)(-38.4611421 * 1E6),4);
        
        //padrões de vibração
        final long[] VibOne   = {0, 200, 200};
        final long[] VibTwo   = {0, 200, 200, 200, 200};
        final long[] VibThree = {0, 200, 200, 200, 200, 200, 200};
        final long[] VibFour  = {0, 200, 200, 200, 200, 200, 200, 200, 200};
        final long[] VibFive  = {0, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200};
		
     
        //imprime locations
        final List<Place> all = this.db.selectAll();
        for (Place one : all) {      	
        	Log.d(one.getName(), one.getLocation().toString());
        }

// -------------TESTE WEB SERVICE-----------------------//        
        
     // Instantiate the Web Service Class with he URL of the web service not that you must pass
        
        WebService webService = new WebService("http://www.sumasoftware.com/alerts/GetAlerts.php");
        //WebService webService = new WebService("http://localhost:8081/VibracupWebservice/");
        
        //Pass the parameters if needed , if not then pass dummy one as follows
        Map<String, String> params = new HashMap<String, String>();
        params.put("var", "");
  
        //Get JSON response from server the "" are where the method name would normally go if needed example
        // webService.webGet("getMoreAllerts", params);
        String response = webService.webGet("", params);
  
        try
        {
            //Parse Response into our object
//            Type collectionType = new TypeToken<List<PlaceJSON>>(){}.getType();
//            List<PlaceJSON> server_places = new Gson().fromJson(response, collectionType);
//            
//            for (PlaceJSON a : server_places) {      	
//            	Log.d("Place!! ", a.toString());
//            }
        	Type collectionType = new TypeToken<List<alerts>>(){}.getType();
            List<alerts> alrts = new Gson().fromJson(response, collectionType);
            
            for (alerts a : alrts) {      	
            	Log.d("Alert!! ", a.toString());
            }
  
        }
        catch(Exception e)
        {
            Log.d("Error: ", e.getMessage());
        }
        
        
// -------------TESTE WEB SERVICE-----------------------//        
        
        
        
		// Get Mapping Controllers etc
		mapView = (MapView) findViewById(R.id.map_view);
		mapController = mapView.getController();
		mapController.setZoom(17);
		mapView.setBuiltInZoomControls(true);
		
		
		// Add the myLocationOverlay
		myLocation = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocation);
		myLocation.enableMyLocation();

		DemoOverlay demoOverlay = new DemoOverlay();
		mapView.getOverlays().add(demoOverlay);

		myLocation.runOnFirstFix(
			new Runnable() {
				public void run() {
					Log.d("mylocation", myLocation.getMyLocation().toString());
					mapController.animateTo(myLocation.getMyLocation());
					
					mapView.getOverlays().clear();
					mapView.getOverlays().add(myLocation);

			        ArrayList<Place> locais = new ArrayList<Place>();
					LocationOverlay myOverlay =
			        		new LocationOverlay(getApplicationContext(), 
			        				getResources().getDrawable(R.drawable.markerblue));

					int precision = 1000;
					for (Place one : all) {
						//locais.add(one);		

						if(myLocation.getMyLocation().getLatitudeE6()/precision == one.getLocation().getLatitudeE6()/precision
				    	&& myLocation.getMyLocation().getLongitudeE6()/precision == one.getLocation().getLongitudeE6()/precision){
							locais.add(one);		
							ratingBar = (RatingBar) findViewById(R.id.RatingBar);
					    	ratingBar.setRating(one.getRate());
					    	Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					    	switch (one.getRate()) {
							case 1:
								v.vibrate(VibOne, -1);
								break;
							case 2:
								v.vibrate(VibTwo, -1);
								break;
							case 3:
								v.vibrate(VibThree, -1);
								break;
							case 4:
								v.vibrate(VibFour, -1);
								break;
							case 5:
								v.vibrate(VibFive, -1);
								break;
							default:
								break;
							}					    	
				    	}	
					}
			        myOverlay.setItems(locais);
			        mapView.getOverlays().add(myOverlay);

				}
			}
		);
		
        

		
		
		
 
		

		
//		//VIBRAR
//		// Get instance of Vibrator from current Context
//		Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//		// This example will cause the phone to vibrate "SOS" in Morse Code
//		// In Morse Code, "s" = "dot-dot-dot", "o" = "dash-dash-dash"
//		// There are pauses to separate dots/dashes, letters, and words
//		// The following numbers represent millisecond lengths
//		int dot = 200;      // Length of a Morse Code "dot" in milliseconds
//		int dash = 500;     // Length of a Morse Code "dash" in milliseconds
//		int short_gap = 200;    // Length of Gap Between dots/dashes
//		int medium_gap = 500;   // Length of Gap Between Letters
//		int long_gap = 1000;    // Length of Gap Between Words
//		long[] pattern = {
//		    0,  // Start immediately
//		    dot, short_gap, dot, short_gap, dot,    // s
//		    medium_gap,
//		    dash, short_gap, dash, short_gap, dash, // o
//		    medium_gap,
//		    dot, short_gap, dot, short_gap, dot,    // s
//		    long_gap
//		};
//		// Only perform this pattern one time (-1 means "do not repeat")
//		v.vibrate(pattern, -1);

    }
    
	@Override
	protected void onResume() {
		super.onResume();
		//myLocation.enableMyLocation();
	}
 
	@Override
	protected void onPause() {
		super.onPause();
		//myLocation.disableMyLocation();
	}
 
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.layout.menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case R.id.Satelite:
	            if (item.isChecked()) {
	            	item.setChecked(false);
	                mapView.setSatellite(false);
	            }
	            else {
	            	item.setChecked(true);
	                mapView.setSatellite(true);
	            }
	            return true;
	        case R.id.Mapas_Turisticos:
	            setContentView(R.layout.mapas);
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
}
