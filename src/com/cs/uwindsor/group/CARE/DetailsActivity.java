package com.cs.uwindsor.group.CARE;

import com.cs.uwindsor.group.CARE.utils.ReviewUtils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailsActivity extends Activity{
	Activity a = new Activity();
	
	TextView ratingtop;
	TextView brand;
	TextView name;
	TextView price;
	TextView desc;
	TextView type;
	TextView numRatings;
	TextView reviewName;
	TextView reviewRating;
	TextView reviewComment;
	RatingBar rating;
	Button rate;
	Button reviews;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.details);
	    
	    a = this;
	    
	    ratingtop = (TextView) findViewById(R.id.samplereview);
	    name = (TextView) findViewById(R.id.name);
	    brand = (TextView) findViewById(R.id.brand);
	    price = (TextView) findViewById(R.id.price);
	    desc = (TextView) findViewById(R.id.desc);
	    type = (TextView) findViewById(R.id.type);
	    rating = (RatingBar) findViewById(R.id.ratingBar1);
	    numRatings = (TextView) findViewById(R.id.numratings);
	    reviewName = (TextView) findViewById(R.id.samplereviewname);
	    reviewRating = (TextView) findViewById(R.id.samplereviewrating);
	    reviewComment = (TextView) findViewById(R.id.samplereviewcomment);
	    rate = (Button) findViewById(R.id.rate_carseat);
	    reviews = (Button) findViewById(R.id.see_reviews);
	    
	    name.setText(getIntent().getStringExtra("name"));
	    price.setText("Price: $" + getIntent().getStringExtra("price"));
	    desc.setText("Product Description: \n" + getIntent().getStringExtra("desc"));
	    desc.setMovementMethod(new ScrollingMovementMethod());
	    brand.setText("Manufacturer: "+getIntent().getStringExtra("make"));
	    numRatings.setText("("+getIntent().getStringExtra("ratingcount")+" Ratings)");
	    
	    
	    switch (Integer.parseInt(getIntent().getStringExtra("type"))){
	    case 1: type.setText("Seat Type: Rear Facing Carseat");
	    break;
	    case 2: type.setText("Seat Type: Front Facing Carseat");
	    break;
	    case 3: type.setText("Seat Type: Booster Seat");
	    break;
	    default: type.setText("");
	    }
	    
	    if(getIntent().hasExtra("newReview")){
	    	String[] review = getIntent().getStringArrayExtra("newReview");
	    	reviewRating.setText("Rating: "+review[0]+"/5");
	    	reviewName.setText("By: "+review[1]);
	    	reviewComment.setText(review[2]);
	    }
	    else{
	    	String[] review = ReviewUtils.firstReview(getIntent().getStringArrayListExtra("reviews"));
	    	if(review!=null){
	    		reviewRating.setText("Rating: "+review[0]+"/5");
	    		reviewName.setText("By: "+review[1]);
	    		reviewComment.setText(review[2]);
	    	}
	    	else{
	    		reviewRating.setText("");
	    		reviewName.setText("No Reviews Yet");
	    		reviewComment.setText("");
	    	}
	    		
	    }
	    	

	    rating.setRating(Float.parseFloat(getIntent().getStringExtra("rating")));
	    rating.setIsIndicator(true);
	    
	    rate.setOnClickListener(sListener); 
	    reviews.setOnClickListener(rListener); 
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		int num = Integer.parseInt(a.getIntent().getStringExtra("ratingcount"));
		num++;
		
		float sum = Float.parseFloat(a.getIntent().getStringExtra("ratingsum"));
		sum = sum + Float.parseFloat(data.getStringArrayExtra("review")[0]);
		
		Log.d("avg", String.valueOf(sum/num));
		
		a.getIntent().removeExtra("rating");
		a.getIntent().putExtra("rating", String.valueOf(sum/num));
		
		a.getIntent().removeExtra("ratingcount");
		a.getIntent().putExtra("ratingcount", String.valueOf(num));
		
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
	
	private OnClickListener rListener = new OnClickListener() {
        public void onClick(View v) {
    		Intent i = new Intent(getApplicationContext(), ReviewsActivity.class);
    		i.putExtra("reviews", getIntent().getStringArrayListExtra(("reviews")));
    		i.putExtra("name", getIntent().getStringExtra("name"));	
    		startActivity(i);
        }
	};
}
