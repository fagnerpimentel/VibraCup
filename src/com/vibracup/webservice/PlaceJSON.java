package com.vibracup.webservice;

public class PlaceJSON {
	private String name;
	private int latitude, longitude;
	private int rate;
	
	public PlaceJSON() {
		
	}
	public PlaceJSON(String name, int latitude, int longitude, int rate) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.rate = rate;
	}
	public int getLatitude(){
		return this.latitude;
	}
	public int getLongitude(){
		return this.longitude;
	}
	public String getName(){
		return this.name;
	}
	public int getRate(){
		return this.rate;
	}
	@Override
    public String toString()
    {
        return "Place JSON: "+this.name+ " GeoPoint(lat,long): ("+this.latitude+ ", "+ this.longitude+
                ") Rate: "+this.rate;
 
    }

}