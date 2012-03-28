package com.cs.uwindsor.group.CARE;

import java.util.HashMap;
import java.util.Map;

import com.cs.uwindsor.group.CARE.utils.SearchUtils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * Activity for searching based only on Age, Weight and Height
 * @author Matt
 *
 */
public class TypeActivity extends Activity{
	Button button;
	String Age = new String();
	String Height = new String();
	String Weight = new String();

	/**
	 * Setup Spinners for the three fields and button to submit.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.typeselector);

	    Spinner hSpinner = (Spinner) findViewById(R.id.Height);
	    ArrayAdapter<CharSequence> hAdapter = ArrayAdapter.createFromResource(
	            this, R.array.height_array, android.R.layout.simple_spinner_item);
	    hAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    hSpinner.setAdapter(hAdapter);
	
	    Spinner wSpinner = (Spinner) findViewById(R.id.Weight);
	    ArrayAdapter<CharSequence> wAdapter = ArrayAdapter.createFromResource(
	            this, R.array.weight_array, android.R.layout.simple_spinner_item);
	    wAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    wSpinner.setAdapter(wAdapter);
	    
	    Spinner aSpinner = (Spinner) findViewById(R.id.Age);
	    ArrayAdapter<CharSequence> aAdapter = ArrayAdapter.createFromResource(
	            this, R.array.ages_array, android.R.layout.simple_spinner_item);
	    aAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    aSpinner.setAdapter(aAdapter);
	    
        button = (Button)findViewById(R.id.wButton);
        button.setOnClickListener(tListener);     
        aSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        hSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        wSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
	}
	
	/**
	 * 
	 * Changes the value of Height, Weight or Age dependent on which Parent Spinner is changed.
	 * @author Matt
	 *
	 */
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	if(parent.equals(findViewById(R.id.Height))){
	    		Height = parent.getItemAtPosition(pos).toString();
	    		//Log.d("Spinner", "Height was set to " + Height);
	    	}
	    	else if(parent.equals(findViewById(R.id.Weight))){
	    		Weight = parent.getItemAtPosition(pos).toString();
	    		//Log.d("Spinner", "Weight was set to " + Weight);
	    	}
	    	else{
	    		Age = parent.getItemAtPosition(pos).toString();
	    		//Log.d("Spinner", "Age was set to " + Age);
	    	}
	    }

	    public void onNothingSelected(AdapterView<?> parent) {
	      // Do nothing.
	    }
	}
	
	/**
	 * When submit button is clicked calculate the type of car seat needed
	 * also check if one or more values not set and give a warning.
	 */
    private OnClickListener tListener = new OnClickListener() {
        public void onClick(View v) {
        	Map<String, String> map = new HashMap<String, String>();
        	int hValue;
        	int wValue;
        	int aValue;
        	
        	//check if value was chosen
        	if(Height.isEmpty() || Weight.isEmpty() || Age.isEmpty())
        		Toast.makeText(getApplicationContext(), "One or more values not set", Toast.LENGTH_LONG).show();
        	else if( Height.contains("--") || Weight.contains("--") || Age.contains("--"))
        		Toast.makeText(getApplicationContext(), "One or more values not set", Toast.LENGTH_LONG).show();
        	else{
        		
        		//check first index set high/low accordingly or parse int if possible
        		if(Height.contains("<")) hValue = 0;
        		else if(Height.contains(">")) hValue = 1000;
        		else hValue = Integer.parseInt(Height);
        	
        		if(Weight.contains("<")) wValue = 0;
        		else if(Weight.contains(">")) wValue = 1000;
        		else wValue = Integer.parseInt(Weight);
        		
        		if(Age.contains("<")) aValue = 0;
        		else if(Age.contains(">")) aValue = 1000;
        		else aValue = Integer.parseInt(Age);
        	
        		if(wValue < 20){
        			map.put("type", "1");
        			SearchUtils.Search(getApplicationContext(), map);}
        			//Toast.makeText(getApplicationContext(), "Your Child Needs to be in a rear facing car seat", Toast.LENGTH_LONG).show();
        		else if(wValue >= 20 && wValue < 40){
        			map.put("type", "2");
    				SearchUtils.Search(getApplicationContext(), map);}
        			//Toast.makeText(getApplicationContext(), "Your Child Needs to be in a front facing car seat", Toast.LENGTH_LONG).show();
        		else if(wValue < 80 || hValue < 145 || aValue <8){
        			map.put("type", "3");
    				SearchUtils.Search(getApplicationContext(), map);}
        			//Toast.makeText(getApplicationContext(), "Your Child Needs to be in a Booster seat", Toast.LENGTH_LONG).show();
        		else
        			Toast.makeText(getApplicationContext(), "Your Child does not need a car seat", Toast.LENGTH_LONG).show();

        	}
        }
    };
}
