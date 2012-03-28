package com.cs.uwindsor.group.CARE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cs.uwindsor.group.CARE.adapters.ImageLoader;
import com.cs.uwindsor.group.CARE.utils.ReviewUtils;


/**
 * Most complex Activity to build since there is so many fields.
 *  also updates some fields upon recreating the Activity.
 * Uses ImageLoader to load images from the servers imgs/ folder.
 * 
 * @author Matt
 *
 */
public class DetailsActivity extends Activity{
	Activity a = new Activity();
	String imgURL = "http://care.cs.uwindsor.ca/imgs/";
	public ImageLoader imageLoader;
	
	TextView brand;
	TextView name;
	TextView price;
	TextView desc;
	TextView type;
	TextView numRatings;
	TextView reviewName;
	TextView reviewRating;
	TextView reviewComment;
	ImageView picture;
	RatingBar rating;
	Button rate;
	Button reviews;
	
	/**
	 * Create method lots of fields to populate with values from intent. 
	 */
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.details);
	    
	    a = this;
	    
	    picture = (ImageView) findViewById(R.id.picture);
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
	    numRatings.setText("("+getIntent().getStringExtra("ratingcount")+" reviews)");
	    
	    //Since type is stored as an int must parse and set here.
	    switch (Integer.parseInt(getIntent().getStringExtra("type"))){
	    case 1: type.setText("Seat Type: Rear Facing Carseat");
	    break;
	    case 2: type.setText("Seat Type: Front Facing Carseat");
	    break;
	    case 3: type.setText("Seat Type: Booster Seat");
	    break;
	    default: type.setText("");
	    }
	    
	    
	    /*Sets the review to the newly created one if one exists
	     *BUG -- there is a bug here where when a new review is made and the app returns 
	     *	to the details page if there was no name or comment entered then those fields
	     *	are left blank instead of being populated with "Anonymous" and "No Comment"
	     *	I've tried a number of ways to check if the string is empty or null or less than
	     *	1 character in length but none have worked. I assume there is an easy fix. 
	     */
	    if(getIntent().hasExtra("newReview")){
	    	String[] review = getIntent().getStringArrayExtra("newReview");
	    	reviewRating.setText("Rating: "+review[0]+"/5 Stars");
    		if(review[1].length() >= 1)
    			reviewName.setText("By: "+review[1]);
    		else
    			reviewName.setText("By: Anonymous");
    		if(review[2].length() >= 1)
    			reviewComment.setText(review[2]);
    		else
    			reviewComment.setText("No Comment");
	    }
	    //Otherwise get the first review from the list and put the values in.
	    else{
	    	String[] review = ReviewUtils.firstReview(getIntent().getStringArrayListExtra("reviews"));
	    	if(review!=null){
	    		reviewRating.setText("Rating: "+review[0]+"/5 Stars");
	    		if(review[1].length() >= 1)
	    			reviewName.setText("By: "+review[1]);
	    		else
	    			reviewName.setText("By: Anonymous");
	    		if(review[2].length() >= 1)
	    			reviewComment.setText(review[2]);
	    		else
	    			reviewComment.setText("No Comment");
	    	}
	    	//If there is no reviews then let the user know
	    	else{
	    		reviewRating.setText("");
	    		reviewName.setText("No Reviews Yet");
	    		reviewComment.setText("");
	    	}
	    		
	    }
	    
	    // Set the new avg rating upon recreating the activity
	    /*
	     * BUG -- There is a bug that is causing the new rating not to be set
	     * 	I don't know why since it is being changed in the same way as the
	     * 	review counter and the new review. There must be something with the
	     * 	android RatingBar 
	     */
	    if(getIntent().hasCategory("newRating")){
	    	Log.d("newRating", String.valueOf(getIntent().getFloatExtra("newRating", 0)));
	    	rating.setIsIndicator(false);
	    	rating.setRating(getIntent().getFloatExtra("newRating", 0));
	    	rating.setIsIndicator(true);
	    }
	    else{
	    	rating.setRating(Float.parseFloat(getIntent().getStringExtra("rating")));
	    	rating.setIsIndicator(true);
	    }
	    
	    //Get the image and display it.
		imageLoader = new ImageLoader(this.getApplicationContext());
		ImageView image = (ImageView) findViewById(R.id.picture);
		imageLoader.DisplayImage(imgURL+getIntent().getStringExtra("img"), a, image);
	    
	    rate.setOnClickListener(sListener); 
	    reviews.setOnClickListener(rListener); 
		
	}
	
	/**
	 * When the child activity successfully completes set the new values of rating, reviewcount
	 * 	and the new review and then recreate the activity.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK){
			
			int num = Integer.parseInt(a.getIntent().getStringExtra("ratingcount"));
			num++;
		
			float sum = Float.parseFloat(a.getIntent().getStringExtra("ratingsum"));
			sum = sum + Float.parseFloat(data.getStringArrayExtra("review")[0]);
		
			Log.d("avg", String.valueOf(sum/num));
		
			a.getIntent().putExtra("newRating", (sum/num));
		
			a.getIntent().removeExtra("ratingcount");
			a.getIntent().putExtra("ratingcount", String.valueOf(num));
		
			a.getIntent().putExtra("newReview", data.getStringArrayExtra("review"));
			a.recreate();
		}
	}
	
	/**
	 * Listener to listen to the Rate button and start a RatingActivity onClick
	 */
	private OnClickListener sListener = new OnClickListener() {
        public void onClick(View v) {
    		Intent i = new Intent(getApplicationContext(), RatingActivity.class);
    		i.putExtra("id", getIntent().getStringExtra("id"));
    		i.putExtra("name", getIntent().getStringExtra("name"));	
    		startActivityForResult(i, 0);
        }
	};
	
	/**
	 * Listener to listen to the Reviews button and start a ReviewsActivity onClick
	 */
	private OnClickListener rListener = new OnClickListener() {
        public void onClick(View v) {
    		Intent i = new Intent(getApplicationContext(), ReviewsActivity.class);
    		i.putExtra("reviews", getIntent().getStringArrayListExtra(("reviews")));
    		i.putExtra("name", getIntent().getStringExtra("name"));	
    		startActivity(i);
        }
	};
}
