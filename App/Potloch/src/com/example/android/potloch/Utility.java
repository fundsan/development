package com.example.android.potloch;

import java.util.Date;

import java.io.File;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.widget.ImageView;

public class Utility {

	public static String getDate() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// get current date time with Date()

		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());

	}

	public static String getRealPathFromURI(Context context, Uri contentUri) {
		Cursor cursor = null;
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			cursor = context.getContentResolver().query(contentUri, proj, null,
					null, null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
	}
	public static File getTempFile(Context context, String url) {
	    File file = null;
	    try {
	        String fileName = Uri.parse(url).getLastPathSegment();
	        file = File.createTempFile(fileName, null, context.getCacheDir());
	        }
	    catch (IOException e) {
	        // Error while creating file
	    }
	    return file;
	}
	public static void savePhotoData(Photo p, ImageView img, Context context)
			throws IOException {
		assert (img != null);

		File mDir = context.getCacheDir();
		File newFile = new File(mDir, String.valueOf(p.getId()));
		Log.e("PHOTO", newFile.getPath());
		img.buildDrawingCache();
		Bitmap bmap = img.getDrawingCache();
		FileOutputStream out;

		try {
			out = new FileOutputStream(newFile);

			if (out != null) {

				bmap.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.close();
			}

		} catch (Exception e) {
			Log.e("Error reading file", e.toString());
		}

	}

}
