package com.example.android.potloch.test;

import com.example.android.potloch.data.GiftContract.GiftEntry;
import com.example.android.potloch.data.GiftDbHelper;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class TestDb extends AndroidTestCase {

	public static final String LOG_TAG = TestDb.class.getSimpleName();

	public void testCreateDb() throws Throwable {
		mContext.deleteDatabase(GiftDbHelper.DATABASE_NAME);
		SQLiteDatabase db = new GiftDbHelper(this.mContext)
				.getWritableDatabase();
		assertEquals(true, db.isOpen());
		db.close();
	}
	
	public void testInsertReadDb() throws Throwable {
		GiftDbHelper dbHelper = new GiftDbHelper(mContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		 ContentValues giftValues = new ContentValues();
		 giftValues.put(GiftEntry.COLUMN_TITLE, "cats");
		 giftValues.put(GiftEntry.COLUMN_DATETEXT, "20141205");
		 giftValues.put(GiftEntry.COLUMN_USER, "user1");
		 giftValues.put(GiftEntry.COLUMN_LIKES, 0);
		 giftValues.put(GiftEntry.COLUMN_TEXT, "these are two cats");
		  
		 
		 long giftRowId = db.insert(GiftEntry.TABLE_NAME, null, giftValues);
		 
		 assertTrue(giftRowId != -1);
		  
		 // A cursor is your primary interface to the query results.
		 Cursor giftCursor = db.query(
		 GiftEntry.TABLE_NAME, // Table to Query
		 null, // leaving "columns" null just returns all the columns.
		 null, // cols for "where" clause
		 null, // values for "where" clause
		 null, // columns to group by
		 null, // columns to filter by row groups
		 null // sort order
		 );
		  
		 if (!giftCursor.moveToFirst()) {
		 fail("No gift data returned!");
		 }
		  
		 assertEquals(giftCursor.getLong(
		 giftCursor.getColumnIndex(GiftEntry._ID)), giftRowId);
		 assertEquals(giftCursor.getString(
		 giftCursor.getColumnIndex(GiftEntry.COLUMN_TITLE)),"cats");
		 assertEquals(giftCursor.getString(
				 giftCursor.getColumnIndex(GiftEntry.COLUMN_DATETEXT)), "20141205");
		 assertEquals(giftCursor.getString(
				 giftCursor.getColumnIndex(GiftEntry.COLUMN_USER)), "user1");
		 assertEquals(giftCursor.getLong(
				 giftCursor.getColumnIndex(GiftEntry.COLUMN_LIKES)), 0);
		 assertEquals(giftCursor.getString(
				 giftCursor.getColumnIndex(GiftEntry.COLUMN_TEXT)), "these are two cats");
		
		  
		 giftCursor.close();
		 dbHelper.close();
	}
}