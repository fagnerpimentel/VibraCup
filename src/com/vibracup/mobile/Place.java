package com.vibracup.mobile;

import com.google.android.maps.GeoPoint;

public class Place {
	private String name;
	private GeoPoint location;
	private int rate;
	
	public Place() {
		
	}
	public Place(String name, GeoPoint location, int rate) {
		this.name = name;
		this.location = location;
		this.rate = rate;
	}
	public GeoPoint getLocation(){
		return this.location;
	}
	public String getName(){
		return this.name;
	}
	public int getRate(){
		return this.rate;
	}
	

}
