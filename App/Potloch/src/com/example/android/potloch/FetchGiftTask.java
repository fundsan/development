package com.example.android.potloch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.android.potloch.data.GiftContract.GiftEntry;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;

public class FetchGiftTask extends AsyncTask<String, Void, String[]> {

	private static final boolean DEBUG = true;
	private final String LOG_TAG = FetchGiftTask.class.getSimpleName();
	
	
	private final Context mContext;

	public FetchGiftTask(Context context) {
		mContext = context;
	}

	/*
	 * The date/time conversion code is going to be moved outside the asynctask
	 * later, so for convenience we're breaking it out into its own method now.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private String[] getGiftData(Collection<Photo> photos
			) {
	
		// Insert the location into the database.
		
		// Get and insert the new weather information into the database
		Vector<ContentValues> cVVector = new Vector<ContentValues>(
				photos.size());
		String[] resultStrs = new String[photos.size()];
		Photo[] photosArr = (Photo[]) photos.toArray();
		for (int i = 0; i < photosArr.length; i++) {
			// These are the values that will be collected.
			Photo currentPhoto = photosArr[i];
			String title = currentPhoto.getName(); 
			String detail = currentPhoto.getText(); 
			long likes = currentPhoto.getLikes();
			String user = currentPhoto.getUser(); 
			String date = currentPhoto.getDate();
			
			long parentID = currentPhoto.getParentPhoto();
			
			ContentValues giftValues = new ContentValues();
			giftValues.put(GiftEntry.COLUMN_TITLE, title);
			giftValues.put(GiftEntry.COLUMN_TEXT, detail);
			giftValues.put(GiftEntry.COLUMN_USER, user);
			giftValues.put(GiftEntry.COLUMN_LIKES, likes);
			giftValues.put(GiftEntry.COLUMN_DATETEXT, date);
			giftValues.put(GiftEntry.COLUMN_PARENT, parentID);
			
			
			cVVector.add(giftValues);
			
		}
		if (cVVector.size() > 0) {
			ContentValues[] cvArray = new ContentValues[cVVector.size()];
			cVVector.toArray(cvArray);
			int rowsInserted = mContext.getContentResolver().bulkInsert(
					GiftEntry.CONTENT_URI, cvArray);
			Log.v(LOG_TAG, "inserted " + rowsInserted + " rows of weather data");
			// Use a DEBUG variable to gate whether or not you do this, so you
			// can easily
			// turn it on and off, and so that it's easy to see what you can rip
			// out if
			// you ever want to remove it.
			if (DEBUG) {
				Cursor weatherCursor = mContext.getContentResolver().query(
						GiftEntry.CONTENT_URI, null, null, null, null);
				if (weatherCursor.moveToFirst()) {
					ContentValues resultValues = new ContentValues();
					DatabaseUtils.cursorRowToContentValues(weatherCursor,
							resultValues);
					Log.v(LOG_TAG, "Query succeeded! **********");
					for (String key : resultValues.keySet()) {
						Log.v(LOG_TAG,
								key + ": " + resultValues.getAsString(key));
					}
				} else {
					Log.v(LOG_TAG, "Query failed! :( **********");
				}
			}
		}
		return resultStrs;
	}

	@Override
	protected String[] doInBackground(String... params) {
		String titleText = params[0];
		Collection<Photo> photos = PhotoSvc.getOrShowLogin(mContext).findByNameContaining(titleText);
		Log.e("Fetch", String.valueOf(photos.size()));
		String[] results = new String[photos.size()];
		int i = 0;
		for (Photo photo : photos) {
			results[i] = photo.getName();
			long gitsID = addGift(photo.getName(), photo.getText(),
					photo.getUser(), photo.getLikes(), photo.getDate(),photo.getParentPhoto());
			i++;
		}

		return results;
	}

	private long addGift(String title, String detail, String user, long likes,
			String date, long parentID) {
		Cursor cursor = mContext.getContentResolver().query(
				GiftEntry.CONTENT_URI, new String[] { GiftEntry._ID },
				GiftEntry.COLUMN_TITLE + " = ?", new String[] { title }, null);

		if (cursor.moveToFirst()) {
			Log.v(LOG_TAG, "Found it in the database!");
			int giftIdIndex = cursor.getColumnIndex(GiftEntry._ID);
			return cursor.getLong(giftIdIndex);
		} else {
			Log.v(LOG_TAG, "Didn't find it in the database, inserting now!");
			ContentValues giftValues = new ContentValues();
			giftValues.put(GiftEntry.COLUMN_TITLE, title);
			giftValues.put(GiftEntry.COLUMN_TEXT, detail);
			giftValues.put(GiftEntry.COLUMN_USER, user);
			giftValues.put(GiftEntry.COLUMN_LIKES, likes);
			giftValues.put(GiftEntry.COLUMN_DATETEXT, date);
			giftValues.put(GiftEntry.COLUMN_PARENT, parentID);
			Uri giftInsertUri = mContext.getContentResolver().insert(
					GiftEntry.CONTENT_URI, giftValues);
			return ContentUris.parseId(giftInsertUri);
		}
	}
}
