package com.example.android.potloch;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;





import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.ApacheClient;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CreateActivity extends ActionBarActivity {
	
	static ImageView imgBut;
	static EditText extraText;
	static Button subBut;
	static TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_create);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create, menu);
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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			View rootView = inflater.inflate(R.layout.fragment_create,
					container, false);
			
			imgBut = (ImageView) rootView.findViewById(R.id.imageButtonCreate);
			extraText = (EditText) rootView.findViewById(R.id.createEditDescription);
			subBut = (Button) rootView.findViewById(R.id.buttonCreateSubmit);
			title = (TextView) rootView.findViewById(R.id.createEditTitle);
			imgBut.setOnClickListener(new View.OnClickListener(){
			
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(Intent.createChooser(intent,"choose content image"),1);
					
					
					
				}
			});
			subBut.setOnClickListener(new View.OnClickListener(){
			
		    
				@Override
				public void onClick(View v) {
					//saveData(new Photo(title.getText().toString(),"/"+title.getText().toString()));
					CallableTask.invoke(new Callable<Photo>() {

						@Override
						public Photo call() throws Exception {
							// TODO Auto-generated method stub
						

							PhotoSvcApi photoService= RestServer.getInstance();
							Intent intent = getActivity().getIntent();
							Photo p = new Photo(title.getText().toString(),"/"+title.getText().toString(), intent.getExtras().getLong("PARENTID"));
							
							photoService.addPhoto(p);
							
							
							 return p;
						}
					
					
				},new TaskCallback<Photo>() {

					

					@Override
					public void error(Exception e) {
						Toast.makeText(
								PlaceholderFragment.this.getActivity(),
								e.toString(),
								Toast.LENGTH_SHORT).show();

						
					}

					@Override
					public void success(Photo result) {
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
		public void onActivityResult(int reqCode, int resCode, Intent data) {
			super.onActivityResult(reqCode, resCode, data);
			if(resCode== RESULT_OK){
				if(reqCode == 1){
				imgBut.setImageURI(data.getData());
				extraText.setVisibility(EditText.VISIBLE);
				subBut.setVisibility(Button.VISIBLE);
				
				
			    }
			}
		}
		public void saveData(Photo p){
			PhotoSvcApi videoService = new RestAdapter.Builder()
			.setEndpoint(PhotoSvcApi.TEST_URL)
			.setLogLevel(LogLevel.FULL)
			.build()
			.create(PhotoSvcApi.class);
			videoService.addPhoto(p);
		
		}
	}
}
