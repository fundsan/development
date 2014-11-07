package com.example.android.potloch;

import java.util.Collection;
import java.util.concurrent.Callable;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;

public class FindChainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_chain);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.find_chain, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private EditText chainEdit;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_find_chain,
					container, false);
			
			chainEdit = (EditText) rootView.findViewById(R.id.findChainEditTitle);
			chainEdit.addTextChangedListener(new TextWatcher(){

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
					
					CallableTask.invoke(new Callable<Collection<Photo>>() {

						@Override
						public Collection<Photo> call() throws Exception {
							PhotoSvcApi photoService= RestServer.getInstance();
							// TODO Auto-generated method stub
							return photoService.findByTitle(chainEdit.getText().toString());
						}},  new TaskCallback<Collection<Photo>>(){

							@Override
							public void error(Exception e) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void success(Collection<Photo> result) {
								// TODO Auto-generated method stub
								
							}

							
							
				
			
			});
				}
			
			});
		
			return rootView;
		}
		
	}
}
