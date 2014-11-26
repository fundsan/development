package com.example.android.potloch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.android.potloch.data.GiftContract;
import com.example.android.potloch.data.GiftContract.GiftEntry;


import android.content.Intent;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class FetchActivity extends ActionBarActivity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fetch);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		@Override
		public void onResume() {
			Log.e("update","onResume called ");
			this.updateInfo();
			super.onResume();
		}

		@Override
		public void onStart() {
			super.onStart();
			Log.e("searching","searching title is "+ searchingTitle);
			updateInfo();// TODO Auto-generated method stub
			
		}
		private static final int GIFT_LOADER = 0;
		static SimpleCursorAdapter mGiftAdapter;
		private String searchingTitle = "cats";
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
			
			View rootView = inflater.inflate(R.layout.fragment_fetch, container,
					false);

			

			mGiftAdapter = new SimpleCursorAdapter(getActivity(),
					R.layout.list_item_gift, null,
					// the column names to use to fill the textviews
					new String[] { GiftEntry.COLUMN_DATETEXT,
							GiftEntry.COLUMN_TITLE,
							GiftEntry.COLUMN_TEXT,
							GiftEntry.COLUMN_LIKES },
					// the textviews to fill with the data pulled from the
					// columns above
					new int[] { R.id.list_item_date_textview,
							R.id.list_item_title_textview,
							R.id.list_item_text_textview,
							R.id.list_item_likes_textview }, 0);
			mGiftAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){

				@Override
				public boolean  setViewValue(View view, Cursor cursor, int columnIndex) {
					
					return false;
					}});
			ListView listView = (ListView) rootView
					.findViewById(R.id.listview_gifts);
			listView.setAdapter(mGiftAdapter);

			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					
					Cursor cursor = mGiftAdapter.getCursor();
					if (cursor != null && cursor.moveToPosition(position)) {
					long idKey = cursor.getLong(COL_GIFT_ID);
					Log.e("Main", String.valueOf(idKey));
					 Intent intent = new Intent(getActivity(),
					DetailActivity.class).putExtra(DetailActivity.ID_KEY, idKey);
			       
					 startActivity(intent);
				    	}
					}
			      }
				);
			Button createButton = (Button) rootView
					.findViewById(R.id.main_create_button);
			createButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(),
							CreateActivity.class);

					startActivity(intent);

				}
			});
			EditText searchText = (EditText) rootView
					.findViewById(R.id.main_search_gifts);
			searchText.addTextChangedListener(new TextWatcher() {

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					searchingTitle = s.toString();
					Log.e("Searching","searching title is "+ searchingTitle);
					
					updateInfo();
					
					getActivity().getContentResolver().notifyChange(GiftContract.GiftEntry.CONTENT_URI, null);
				}
			});
			return rootView;
		}


		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			getLoaderManager().initLoader(GIFT_LOADER, null,
					 this);
			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public CursorLoader onCreateLoader(int id, Bundle args) {
			// This is called when a new Loader needs to be created. This
			// fragment only uses one loader, so we don't care about checking
			// the id.
			// To only show current and future dates, get the String
			// representation for today,
			// and filter the query to return weather only for dates after or
			// including today.
			// Only return data after today.

			// Sort order: Ascending, by date.
			String sortOrder = GiftEntry.COLUMN_DATETEXT + " ASC";
			
			Uri giftUri = GiftEntry.buildGiftUriTitle(this.searchingTitle);
			// Now create and return a CursorLoader that will take care of
			// creating a Cursor for the data being displayed.
			Log.e("update","onCreate called "+  giftUri.toString());
			return new CursorLoader(getActivity(), giftUri, GIFT_COLUMNS, null,
					null, sortOrder);
		}


		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data)  {
			// TODO Auto-generated method stub
			mGiftAdapter.swapCursor(data);
		}

		@Override
		public void onLoaderReset(Loader<Cursor> loader){
			// TODO Auto-generated method stub
			mGiftAdapter.swapCursor(null);
			
	}
		private void updateInfo(){
			
			new FetchGiftTask(getActivity()).execute(this.searchingTitle);
			Log.e("update","update called");
			getLoaderManager().restartLoader(GIFT_LOADER, null, this);
		}
  }
}
