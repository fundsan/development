package com.example.android.potloch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
	static ArrayAdapter<String> mGiftAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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
        	startActivity(new Intent(this,SettingsActivity.class));
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            
            String[] giftArray = {"This is the first","this is the second","this is the third",
                                  "this is the fourth","This is the fifth"};
            List<String> giftList = new ArrayList<String>(Arrays.asList(giftArray));
            
            mGiftAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_gift, 
            		R.id.list_item_gift_textview, giftList);
            
            ListView listView = (ListView) rootView.findViewById(R.id.listview_gifts);
            listView.setAdapter(mGiftAdapter);
            
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String giftText = mGiftAdapter.getItem(position);
					
					Intent intent = new Intent(getActivity(), DetailActivity.class)
					.putExtra(Intent.EXTRA_TEXT, giftText);
					startActivity(intent);
					
					
				}
			
            	
			});
            Button createButton = (Button) rootView.findViewById(R.id.main_create_button);
            createButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(getActivity(),CreateActivity.class);
					startActivity(intent);
					
				}
			});
            return rootView;
        }
    }
}

