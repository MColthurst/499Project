package com.cs.uwindsor.group.CARE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.cs.uwindsor.group.CARE.utils.BrandList;

/**
 * Main Activity that displays the main screen with two button options.
 * @author Matt
 *
 */
public class MainActivity extends Activity {
	/*
	 * Buttons for Type and Search Activities
	 */
	Button buttonT;
	Button buttonS;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
        buttonT = (Button)findViewById(R.id.t_Button);
        buttonT.setOnClickListener(tListener);            
        buttonS = (Button)findViewById(R.id.s_Button);
        buttonS.setOnClickListener(sListener);  
	}
	
	/*
	 * Listener for type button starts the TypeActivity
	 */
    private OnClickListener tListener = new OnClickListener() {
        public void onClick(View v) {
        	Intent i = new Intent(getApplicationContext(), TypeActivity.class);
        	startActivity(i);
        }
    };
    
    /*
     * Listener for search button Goes through BrandList Util to properly add
     * values to the list of brands on search page.
     */
    private OnClickListener sListener = new OnClickListener() {
        public void onClick(View v) {
        	BrandList.List(getApplicationContext());
        }
    };
	
	
}