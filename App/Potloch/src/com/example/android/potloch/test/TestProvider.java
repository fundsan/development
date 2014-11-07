package com.example.android.potloch.test;

import com.example.android.potloch.data.GiftContract.GiftEntry;
import com.example.android.potloch.data.GiftDbHelper;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

public class TestProvider extends AndroidTestCase {

	public static final String LOG_TAG = TestProvider.class.getSimpleName();

	

	public void testInsertReadProvider() throws Throwable {
		GiftDbHelper dbHelper = new GiftDbHelper(mContext);
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		ContentValues giftValues = new ContentValues();
		giftValues.put(GiftEntry.COLUMN_TITLE, "cats");
		giftValues.put(GiftEntry.COLUMN_DATETEXT, "20141205");
		giftValues.put(GiftEntry.COLUMN_USER, "user1");
		giftValues.put(GiftEntry.COLUMN_LIKES, 0);
		giftValues.put(GiftEntry.COLUMN_TEXT, "these are two cats");

		Uri giftInsertUri = mContext.getContentResolver().insert(
				GiftEntry.CONTENT_URI, giftValues);
		assertTrue(giftInsertUri != null);
		long giftId = ContentUris.parseId(giftInsertUri);
		assertTrue(giftId != -1);

		// A cursor is your primary interface to the query results.
		Cursor giftCursor = mContext.getContentResolver().query(
				GiftEntry.CONTENT_URI, null, null, null, null);

		if (!giftCursor.moveToFirst()) {
			fail("No gift data returned!");
		}

		assertEquals(giftCursor.getString(giftCursor
				.getColumnIndex(GiftEntry.COLUMN_TITLE)), "cats");
		assertEquals(giftCursor.getString(giftCursor
				.getColumnIndex(GiftEntry.COLUMN_DATETEXT)), "20141205");
		assertEquals(giftCursor.getString(giftCursor
				.getColumnIndex(GiftEntry.COLUMN_USER)), "user1");
		assertEquals(giftCursor.getLong(giftCursor
				.getColumnIndex(GiftEntry.COLUMN_LIKES)), 0);
		assertEquals(giftCursor.getString(giftCursor
				.getColumnIndex(GiftEntry.COLUMN_TEXT)), "these are two cats");

		giftCursor.close();
		dbHelper.close();
	}

	public void testGetType() {
		// content://com.example.android.sunshine.app/gifts
		String type = mContext.getContentResolver().getType(
				GiftEntry.CONTENT_URI);
		// vnd.android.cursor.dir/com.example.android.sunshine.app/gifts
		assertEquals(GiftEntry.CONTENT_TYPE, type);

	}

	public void deleteAllRecords() {
		mContext.getContentResolver().delete(GiftEntry.CONTENT_URI, null, null);

		Cursor cursor = mContext.getContentResolver().query(
				GiftEntry.CONTENT_URI, null, null, null, null);
		assertEquals(0, cursor.getCount());
		cursor.close();

	}



	// Make sure we can still delete after adding/updating stuff
	public void testDeleteRecordsAtEnd() {
		deleteAllRecords();
	}

	// Since we want each test to start with a clean slate, run deleteAllRecords
	// in setUp (called by the test runner before each test).
	public void setUp() {
		deleteAllRecords();
	}
}