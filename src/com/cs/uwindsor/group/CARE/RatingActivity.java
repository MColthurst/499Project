package com.cs.uwindsor.group.CARE;

import java.util.HashMap;
import java.util.Map;

import com.cs.uwindsor.group.CARE.http.AsyncHttpClient;
import com.cs.uwindsor.group.CARE.http.AsyncHttpResponseHandler;
import com.cs.uwindsor.group.CARE.utils.URLbuilder;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RatingActivity extends Activity{
	private static final String baseURL = "http://care.cs.uwindsor.ca/rate.php?";
	private static AsyncHttpClient client = new AsyncHttpClient();
	Intent i = new Intent();
	String[] review = new String[3];
	TextView main;
	EditText name;
	EditText comment;
	RatingBar rating;
	Button submit;
	DetailsActivity a = new DetailsActivity();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rating);
		
		main = (TextView) findViewById(R.id.mainReviewText);
		name = (EditText) findViewById(R.id.nameField);
		comment = (EditText) findViewById(R.id.commentField);
		rating = (RatingBar) findViewById(R.id.ratingBar2);
		submit = (Button) findViewById(R.id.rateButton);
		
		main.setText("Please Rate and Review the " + getIntent().getStringExtra("name"));
		
        submit.setOnClickListener(sListener); 
        
	}
	
    private OnClickListener sListener = new OnClickListener() {
        public void onClick(View v) {
        	Map<String, String> map = new HashMap<String, String>();
        	
        	map.put("id", getIntent().getStringExtra("id"));
        	map.put("rating", Float.toString(rating.getRating()));
        	review[0] = Float.toString(rating.getRating());
        	
        	
        	if(name.getText().toString() != null){
        		map.put("name", name.getText().toString());
        		review[1] = name.getText().toString();
        	}
        	else review[1] = "Anonymous";
        	
        	if(comment.getText().toString() != null){
        		map.put("comment", comment.getText().toString());
        		review[2] = comment.getText().toString();
        	}
        	else
        		review[2] = "No Comment";
        			
        	String url = URLbuilder.buildurl(map, baseURL);
        	
        	client.post(url, new AsyncHttpResponseHandler(){
    			
    			@Override
    			public void onSuccess (String response) {
    				i.putExtra("review", review);
    				setResult(RESULT_OK, i);
    				Log.d("response: ", response);
    			}

    			@Override
    			public void onFailure (Throwable t) {

    				Toast toast = Toast.makeText(getApplicationContext(), "Internet not connected... Please connect and try again.",
    						Toast.LENGTH_LONG);
    				toast.show();
    			}

    			@Override
    			public void onFinish () {
    				finish();
    			}
    		});
        }
    };
}
