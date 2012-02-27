package com.vibracup.mobile;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.google.android.maps.GeoPoint;

public class Places {

   private static final String DATABASE_NAME = "places.db";
   private static final int DATABASE_VERSION = 1;
   private static final String TABLE_NAME = "places";

   private Context context;
   private SQLiteDatabase db;

   private SQLiteStatement insertStmt;
   private static final String INSERT = "insert into "
      + TABLE_NAME + "(name,latitude,longitude,rate) values (?,?,?,?)";

   public Places(Context context) {
      this.context = context;
      OpenHelper openHelper = new OpenHelper(this.context);
      this.db = openHelper.getWritableDatabase();
      this.insertStmt = this.db.compileStatement(INSERT);
   }

   public long insert(String name, int latitude, int longitude, int rate) {

      this.insertStmt.bindString(1, name);
      this.insertStmt.bindDouble(2, latitude);
      this.insertStmt.bindDouble(3, longitude);
      this.insertStmt.bindDouble(4, rate);
      return this.insertStmt.executeInsert();
   }

   public void deleteAll() {
      this.db.delete(TABLE_NAME, null, null);
   }

   public List<Place> selectAll() {
      List<Place> list = new ArrayList<Place>();
      Cursor cursor = this.db.query(TABLE_NAME, new String[] { "name", "latitude", "longitude", "rate" },
        null, null, null, null, "name desc");
      Place p;
      if (cursor.moveToFirst()) {
         do {
        	p = new Place(cursor.getString(0), new GeoPoint(cursor.getInt(1), cursor.getInt(2)), cursor.getInt(3));
            list.add(p);
            
         } while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed()) {
         cursor.close();
      }
      return list;
   }
   
   private static class OpenHelper extends SQLiteOpenHelper {

      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }

      @Override
      public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE " + TABLE_NAME + "" +
         		"(id INTEGER PRIMARY KEY, name TEXT, latitude INTEGER, longitude INTEGER, rate int)");
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
         db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
         onCreate(db);
      }
   }
}
