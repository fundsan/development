package com.example.android.potloch.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class GiftContract {

	public static final String CONTENT_AUTHORITY = "com.example.android.potloch";

	public static final Uri BASE_CONTENT_URI = Uri.parse("content://"
			+ CONTENT_AUTHORITY);

	public static final String PATH_GIFTS = "gifts";

	/* Inner class that defines the table contents of the gift table */
	public static final class GiftEntry implements BaseColumns {

		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
				.appendPath(PATH_GIFTS).build();

		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/"
				+ CONTENT_AUTHORITY + "/" + PATH_GIFTS;

		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/"
				+ CONTENT_AUTHORITY + "/" + PATH_GIFTS;

		public static final String TABLE_NAME = "gift";

		// Date, stored as Text with format yyyy-MM-dd
		public static final String COLUMN_DATETEXT = "date";

		public static final String COLUMN_TITLE = "title";

		public static final String COLUMN_TEXT = "text";

		// user that posted
		public static final String COLUMN_USER = "user";

		// number of likes
		public static final String COLUMN_LIKES = "likes";

		// who liked it
		// public static final String COLUMN_LIKEDBY = "likedby";
		public static Uri buildGiftUri(long id) {
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}

		

		public static String getDateFromUri(Uri uri) {
			return uri.getPathSegments().get(2);
		}

		public static String getStartDateFromUri(Uri uri) {
			return uri.getQueryParameter(COLUMN_DATETEXT);
		}

	}
}