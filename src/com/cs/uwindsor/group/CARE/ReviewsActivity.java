package com.cs.uwindsor.group.CARE;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cs.uwindsor.group.CARE.adapters.ReviewAdapter;

/**
 * Activity to display all the reviews for a given car seat.
 * @author Matt
 *
 */
public class ReviewsActivity extends Activity{
	ArrayList<String> records = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
	
	    
		records = getIntent().getStringArrayListExtra("reviews");

		
	    Adapter a = new ReviewAdapter(this, records);
	    
		ListView list = (ListView) findViewById(R.id.listview);
		list.setAdapter((ListAdapter) a);
	}
	
	
}
