package com.vibracup.mobile;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class LocationOverlay extends ItemizedOverlay<OverlayItem> {
	 
	private List<Place> mItems;
	private Context context;
 
	public LocationOverlay(Context context, Drawable marker) {
		super(boundCenterBottom(marker));
		this.context = context;
	}
 
	public void setItems(List<Place>items){
		mItems = items;
		populate();
	}
 
	@Override
	protected OverlayItem createItem(int i) {
		return new OverlayItem(mItems.get(i).getLocation(),null,null);
	}
 
	@Override
	public int size() {
		return mItems.size();
	}
 
	@Override
	public boolean onTap(int i) {
      CharSequence text = mItems.get(i).getName();
      int duration = Toast.LENGTH_SHORT;

      Toast toast = Toast.makeText(this.context, text, duration);
      toast.show();
		return true;
	}
 
}