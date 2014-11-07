package com.example.android.potloch.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class GiftProvider extends ContentProvider {
	private static final int GIFT = 100;
	private static final int GIFT_WITH_TEXT = 101;
	private static final int GIFT_WITH_TEXT_AND_DATE = 102;
	private GiftDbHelper mOpenHelper;
	private UriMatcher sUriMatcher = buildUriMatcher();

	@Override
	public boolean onCreate() {
		mOpenHelper = new GiftDbHelper(getContext());
		return true;// TODO Auto-generated method stub

	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// Here's the switch statement that, given a URI, will determine what
		// kind of request it is,
		// and query the database accordingly.
		Cursor retCursor;
		switch (sUriMatcher.match(uri)) {
		// "weather/*/*"

		case GIFT: {
			retCursor = mOpenHelper.getReadableDatabase().query(
					GiftContract.GiftEntry.TABLE_NAME, projection, selection,
					selectionArgs, null, null, sortOrder);
			break;
		}

		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		retCursor.setNotificationUri(getContext().getContentResolver(), uri);
		return retCursor;
	}

	@Override
	public String getType(Uri uri) {

		// Use the Uri Matcher to determine what kind of URI this is.
		final int match = sUriMatcher.match(uri);

		switch (match) {
		case GIFT_WITH_TEXT_AND_DATE:
			return GiftContract.GiftEntry.CONTENT_ITEM_TYPE;
		case GIFT_WITH_TEXT:
			return GiftContract.GiftEntry.CONTENT_TYPE;
		case GIFT:
			return GiftContract.GiftEntry.CONTENT_TYPE;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		Uri returnUri;

		switch (match) {
		case GIFT: {
			long _id = db.insert(GiftContract.GiftEntry.TABLE_NAME, null,
					values);
			if (_id > 0)
				returnUri = GiftContract.GiftEntry.buildGiftUri(_id);
			else
				throw new android.database.SQLException(
						"Failed to insert row into " + uri);
			break;
		}

		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		return returnUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		int rowsDeleted;
		switch (match) {
		case GIFT:
			rowsDeleted = db.delete(GiftContract.GiftEntry.TABLE_NAME,
					selection, selectionArgs);
			break;

		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		// Because a null deletes all rows
		if (selection == null || rowsDeleted != 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		int rowsUpdated;

		switch (match) {
		case GIFT:
			rowsUpdated = db.update(GiftContract.GiftEntry.TABLE_NAME, values,
					selection, selectionArgs);
			break;

		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
		if (rowsUpdated != 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return rowsUpdated;
	}

	private static UriMatcher buildUriMatcher() {
		// I know what you're thinking. Why create a UriMatcher when you can use
		// regular
		// expressions instead? Because you're not crazy, that's why.

		// All paths added to the UriMatcher have a corresponding code to return
		// when a match is
		// found. The code passed into the constructor represents the code to
		// return for the root
		// URI. It's common to use NO_MATCH as the code for this case.
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = GiftContract.CONTENT_AUTHORITY;

		// For each type of URI you want to add, create a corresponding code.
		matcher.addURI(authority, GiftContract.PATH_GIFTS, GIFT);

		matcher.addURI(authority, GiftContract.PATH_GIFTS + "/*",
				GIFT_WITH_TEXT);
		matcher.addURI(authority, GiftContract.PATH_GIFTS + "/*/*",
				GIFT_WITH_TEXT_AND_DATE);

		return matcher;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		final int match = sUriMatcher.match(uri);
		switch (match) {
		case GIFT:
			db.beginTransaction();
			int returnCount = 0;
			try {
				for (ContentValues value : values) {
					long _id = db.insert(
							GiftContract.GiftEntry.TABLE_NAME, null,
							value);
					if (_id != -1) {
						returnCount++;
					}
				}
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
			getContext().getContentResolver().notifyChange(uri, null);
			return returnCount;
		default:
			return super.bulkInsert(uri, values);
		}
	}

}
