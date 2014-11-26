package com.example.android.potloch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;

import org.apache.commons.io.IOUtils;

import retrofit.client.Response;
import retrofit.mime.TypedFile;
import retrofit.mime.TypedInput;

import com.example.android.potloch.CreateActivity.PlaceholderFragment;
import com.example.android.potloch.data.GiftContract;
import com.example.android.potloch.data.GiftContract.GiftEntry;
import com.google.common.base.Optional;
import com.google.common.io.Files;
import com.google.common.io.InputSupplier;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class DetailActivity extends ActionBarActivity {

	
	public static final String ID_KEY = "gift_id";
	public static  Context ctx;
	public static long imgDataID;
	public static ImageView img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = getBaseContext();
		imgDataID = getIntent().getLongExtra(ID_KEY, -1);
		this.getDataFromServer(getIntent().getLongExtra(ID_KEY, -1));
		setContentView(R.layout.activity_detail);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			LoaderManager.LoaderCallbacks<Cursor> {
		Button likeButton; 
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			getLoaderManager().initLoader(DETAIL_LOADER, null, this);
			
			//img.setImageURI(Uri.parse(path));
			super.onActivityCreated(savedInstanceState);
		}

		private String mGift;

		protected String path;

		
		
		private static final int DETAIL_LOADER = 0;
		static SimpleCursorAdapter mGiftAdapter;
	
		// For the forecast view we're showing only a small subset of the stored
		// data.
		// Specify the columns we need.
		private static final String[] GIFT_COLUMNS = {
				// In this case the id needs to be fully qualified with a table
				// name, since
				// the content provider joins the location & weather tables in
				// the background
				// (both have an _id column)
				// On the one hand, that's annoying. On the other, you can
				// search the weather table
				// using the location set by the user, which is only in the
				// Location table.
				// So the convenience is worth it.
				GiftEntry.TABLE_NAME + "." + GiftEntry._ID,
				GiftEntry.COLUMN_DATETEXT, GiftEntry.COLUMN_TITLE,
				GiftEntry.COLUMN_USER, GiftEntry.COLUMN_TEXT,
				GiftEntry.COLUMN_LIKES, GiftEntry.COLUMN_PARENT };

		// These indices are tied to FORECAST_COLUMNS. If FORECAST_COLUMNS
		// changes, these
		// must change.
		public static final int COL_GIFT_ID = 0;
		public static final int COL_GIFT_DATE = 1;
		public static final int COL_GIFT_TITLE = 2;
		public static final int COL_GIFT_USER = 3;
		public static final int COL_GIFT_TEXT = 4;
		public static final int COL_GIFT_LIKES = 5;
		public static final int COL_GIFT_PARENT = 6;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);
			img = (ImageView) rootView.findViewById(R.id.imageDetail);
			likeButton = (Button) rootView.findViewById(R.id.detail_likes_textview);
			likeButton.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View v) {
					//saveData(new Photo(title.getText().toString(),"/"+title.getText().toString()));
					CallableTask.invoke(new Callable<Long>() {

						
						@Override
						public Long call() throws Exception {
							// TODO Auto-generated method stub
						

							PhotoSvcApi photoService= PhotoSvc.getOrShowLogin(getActivity().getBaseContext());
							
							
					        
							
							
							
							
							return p;
						
					
						}
				},new TaskCallback<Long>() {

					

					@Override
					public void error(Exception e) {
						Toast.makeText(
								PlaceholderFragment.this.getActivity(),
								e.toString(),
								Toast.LENGTH_SHORT).show();

						
					}

					@Override
					public void success(Long result) {
						Toast.makeText(
								PlaceholderFragment.this.getActivity(),
								"succefully added "+result.getName(),
								
								Toast.LENGTH_SHORT).show();
// TODO Auto-generated method stub
						
					}
				});
					}});
			return rootView;
		}

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			 Intent intent = getActivity().getIntent();
			 Log.v("detail", "In onCreateLoader");
			 if (intent == null || !intent.hasExtra(ID_KEY)) {
				 Log.e("detail", String.valueOf(intent.getLongExtra(ID_KEY, -1)));
			 return null;
			 }
			 long giftId = intent.getLongExtra(ID_KEY, -1);
			 // Sort order: Ascending, by date.
			 String sortOrder = GiftContract.GiftEntry.COLUMN_DATETEXT + " ASC";
			
			 Uri giftForIdUri = GiftContract.GiftEntry.buildGiftUri(giftId);
			 
			 Log.e("detail", MainActivity.Clientsuser);
			 Log.e("detail", String.valueOf(giftForIdUri.toString()));
			 // Now create and return a CursorLoader that will take care of
			 // creating a Cursor for the data being displayed.
			 return new CursorLoader(
			 getActivity(),
			 giftForIdUri,
			 GIFT_COLUMNS,
			 null,
			 null,
			 sortOrder
			 );
		}// TODO Auto-generated method stub
			

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			if (!data.moveToFirst()) {
				Log.e("detail", "its null");
				return;
			}
			Log.e("detail", "data position is " + String.valueOf(data.getPosition()));
			String dateString = (data.getString(data
					.getColumnIndex(GiftEntry.COLUMN_DATETEXT)));
			((TextView) getView().findViewById(R.id.detail_date_textview))
					.setText(dateString);
			String giftTitle = data.getString(data
					.getColumnIndex(GiftEntry.COLUMN_TITLE));
			((TextView) getView().findViewById(R.id.detail_title_textview))
					.setText(giftTitle);
			
			String giftDesc = data.getString(data
					.getColumnIndex(GiftEntry.COLUMN_TEXT));
			((TextView) getView().findViewById(R.id.detail_text_textview))
					.setText(giftDesc);
			String giftLikes = String.valueOf(data.getLong(data
					.getColumnIndex(GiftEntry.COLUMN_LIKES)));
			((TextView) getView().findViewById(R.id.detail_likes_textview))
					.setText(giftLikes);
			// We still need this for the share intent
			mGift = String.format("%s - %s - %s/%s", dateString,
					giftTitle, giftDesc, giftLikes);
			Log.v("Details", "Gift String: " + mGift);
			// TODO Auto-generated method stub

		}

		@Override
		public void onLoaderReset(Loader<Cursor> arg0) {
			// TODO Auto-generated method stub

		}
		
	}

public void getDataFromServer(long id){
	
	CallableTask.invoke(new Callable<Bitmap>() {
		ImageView imageView = null;
		@Override
		public Bitmap call() throws Exception {
			// TODO Auto-generated method stub
		
			Intent intent = getIntent();
			long giftId = intent.getLongExtra(ID_KEY, -1);
			Log.e("detail", String.valueOf(giftId));
			
			
			
			
			
			PhotoSvcApi photoService= PhotoSvc.getOrShowLogin(ctx);
			byte[] response = photoService.getData(giftId);
			
			
			Log.e("detail", "the length of the bitmap is " +String.valueOf(response.length));
			Bitmap bitmap =  BitmapFactory.decodeByteArray(response, 0, response.length);
			if(bitmap == null){
				Log.e("detail", "bitmap is in fact null - fix it");
			}
			
			
			
			
			return bitmap;
		
	
		}
},new TaskCallback<Bitmap>() {

	

	@Override
	public void error(Exception e) {
		Toast.makeText(
				getBaseContext(),
				e.toString(),
				Toast.LENGTH_SHORT).show();
				Log.e("detail", e.toString());

		
	}

	@Override
	public void success(Bitmap result) {
		if (result == null)Log.d("detail", "succuss but null");
		img.setImageBitmap(result);
	
		//Log.d("detail", result.toString());
		
//TODO Auto-generated method stub
		
	}
});
	
	
	}
	
	
}
