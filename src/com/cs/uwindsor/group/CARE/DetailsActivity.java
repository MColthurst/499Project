package com.cs.uwindsor.group.CARE;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailsActivity extends Activity{
	TextView name;
	TextView price;
	TextView type;
	RatingBar rating;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.details);
	    
	    name = (TextView) findViewById(R.id.name);
	    price = (TextView) findViewById(R.id.price);
	    type = (TextView) findViewById(R.id.type);
	    rating = (RatingBar) findViewById(R.id.ratingBar1);
	    
	    name.setText(getIntent().getStringExtra("name"));
	    price.setText("$" + getIntent().getStringExtra("price"));
	    
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
	}
	
	public void buyCarseat(View v){}
}
