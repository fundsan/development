package com.example.android.potloch.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.potloch.data.*;
import com.example.android.potloch.data.GiftContract.GiftEntry;

public class GiftDbHelper extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION= 1;
	public static final String DATABASE_NAME = "gifts.db";
	public GiftDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	 @Override
	 public void onCreate(SQLiteDatabase sqLiteDatabase) {
	 // Create a table to hold locations. A location consists of the string supplied in the
	 // location setting, the city name, and the latitude and longitude
	  
	 // TBD
	  
	 final String SQL_CREATE_GIFT_TABLE = "CREATE TABLE " + GiftEntry.TABLE_NAME+ " (" +
	 // Why AutoIncrement here, and not above?
	 // Unique keys will be auto-generated in either case. But for weather
	 // forecasting, it's reasonable to assume the user will want information
	 // for a certain date and all dates *following*, so the forecast data
	 // should be sorted accordingly.
	 GiftEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
	  
	 // the ID of the location entry associated with this weather data
	 GiftEntry.COLUMN_TITLE+ " TEXT NOT NULL, " +
	 GiftEntry.COLUMN_USER+ " TEXT NOT NULL, " +
	 
     GiftEntry.COLUMN_DATETEXT+ " TEXT NOT NULL, " +
     GiftEntry.COLUMN_TEXT+ " TEXT, " +
     GiftEntry.COLUMN_PARENT+" INT, " +
     GiftEntry.COLUMN_LIKES+ " INT NOT NULL  ); "; 
	  
	 sqLiteDatabase.execSQL(SQL_CREATE_GIFT_TABLE);
	
	 }
	  
	 @Override
	 public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
		 sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GiftEntry.TABLE_NAME);
		 onCreate(sqLiteDatabase);
	 }
}
