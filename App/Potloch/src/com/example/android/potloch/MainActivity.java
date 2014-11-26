package com.example.android.potloch;

import java.util.Collection;
import java.util.concurrent.Callable;



import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	public static String Clientsuser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
		
		  EditText userName_;

		
		  EditText password_;

		
		  EditText server_;
		  
		  Button submit_;
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.d("login", "in onCreateView");
			View rootView = inflater.inflate(R.layout.fragment_main,
					container, false);
			userName_ = (EditText) rootView.findViewById(R.id.userName);
			password_ =  (EditText) rootView.findViewById(R.id.password);
			server_ = (EditText) rootView.findViewById(R.id.server);
		    submit_ = (Button) rootView.findViewById(R.id.loginButton);
		    submit_.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					login();// TODO Auto-generated method stub
					
				}});
			
			return rootView;
		}
	
		public void login() {
			Log.d("login", "in onClick");
			final String user = userName_.getText().toString();
			String pass = password_.getText().toString();
			String server = server_.getText().toString();

			final PhotoSvcApi svc = PhotoSvc.init(server, user, pass);

			CallableTask.invoke(new Callable<Collection<Photo>>() {

				@Override
				public Collection<Photo> call() throws Exception {
					return svc.getPhotoList();
				}
			}, new TaskCallback<Collection<Photo>>() {

				@Override
				public void success(Collection<Photo> result) {
					// OAuth 2.0 grant was successful and we
					// can talk to the server, open up the video listing
					MainActivity.Clientsuser= user;
					startActivity(new Intent(
							getActivity(),
							FetchActivity.class));
				}

				@Override
				public void error(Exception e) {
					Log.e(MainActivity.class.getName(), "Error logging in via OAuth. "+ e.toString());
					
					Toast.makeText(
							getActivity(),
							"Login failed, check your Internet connection and credentials.",
							Toast.LENGTH_SHORT).show();
				}
			});
		}

	}

	
}


