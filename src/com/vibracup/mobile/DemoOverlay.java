package com.vibracup.mobile;
import com.google.android.maps.Overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Projection;

public class DemoOverlay extends Overlay {
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
 
		super.draw(canvas, mapView, shadow);
 
		Projection projection = mapView.getProjection();
 
		int latSpan = mapView.getLatitudeSpan();
		int lngSpan = mapView.getLongitudeSpan();
		GeoPoint mapCenter = mapView.getMapCenter();
		int mapLeftGeo = mapCenter.getLongitudeE6() - (lngSpan / 2);
		int mapRightGeo = mapCenter.getLongitudeE6() + (lngSpan / 2);
 
		int mapTopGeo = mapCenter.getLatitudeE6() - (latSpan / 2);
		int mapBottomGeo = mapCenter.getLatitudeE6() + (latSpan / 2);
 
		GeoPoint geoPoint = this.getSampleLocation();
 
		if ((geoPoint.getLatitudeE6() > mapTopGeo && geoPoint.getLatitudeE6() < mapBottomGeo)
					&& (geoPoint.getLongitudeE6() > mapLeftGeo && geoPoint.getLongitudeE6() < mapRightGeo)) {
			Point myPoint = new Point();
			projection.toPixels(geoPoint, myPoint);
			Bitmap marker = BitmapFactory.decodeResource(mapView.getContext().getResources(), R.drawable.markerblue);
			canvas.drawBitmap(marker, myPoint.x - 15, myPoint.y - 30, null);
		}
	}
	
	
 
	private GeoPoint getSampleLocation() {
 
		// Create GeoPoint to secret location....
		GeoPoint sampleGeoPoint = new GeoPoint((int) (56.27058500725475 * 1E6), (int) (-2.6984095573425293 * 1E6));
 
		return sampleGeoPoint;
	}

}
