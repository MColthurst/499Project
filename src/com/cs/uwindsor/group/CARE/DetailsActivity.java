package com.cs.uwindsor.group.CARE;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailsActivity extends Activity{
	Activity a = new Activity();
	
	TextView name;
	TextView price;
	TextView desc;
	TextView type;
	RatingBar rating;
	Button rate;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.details);
	    
	    a = this;
	    
	    name = (TextView) findViewById(R.id.name);
	    price = (TextView) findViewById(R.id.price);
	    desc = (TextView) findViewById(R.id.desc);
	    type = (TextView) findViewById(R.id.type);
	    rating = (RatingBar) findViewById(R.id.ratingBar1);
	    rate = (Button) findViewById(R.id.rate_carseat);
	    
	    name.setText(getIntent().getStringExtra("name"));
	    price.setText("$" + getIntent().getStringExtra("price"));
	    desc.setText(getIntent().getStringExtra("desc"));
	    
	    switch (Integer.parseInt(getIntent().getStringExtra("type"))){
	    case 1: type.setText("Rear Facing Carseat");
	    break;
	    case 2: type.setText("Front Facing Carseat");
	    break;
	    case 3: type.setText("Booster Seat");
	    break;
	    default: type.setText("");
	    }
	    
	    rating.setIsIndicator(true);
	    rating.setRating(Float.parseFloat(getIntent().getStringExtra("rating")));
	    
	    rate.setOnClickListener(sListener); 
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		a.getIntent().putExtra("newReview", data.getStringArrayExtra("review"));
		a.recreate();
	}
	
	
	private OnClickListener sListener = new OnClickListener() {
        public void onClick(View v) {
    		Intent i = new Intent(getApplicationContext(), RatingActivity.class);
    		i.putExtra("id", getIntent().getStringExtra("id"));
    		i.putExtra("name", getIntent().getStringExtra("name"));	
    		startActivityForResult(i, 0);
        }
	};
}
