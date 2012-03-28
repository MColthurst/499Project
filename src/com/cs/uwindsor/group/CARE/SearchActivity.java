package com.cs.uwindsor.group.CARE;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.cs.uwindsor.group.CARE.db.XMLAdapter;
import com.cs.uwindsor.group.CARE.utils.SearchUtils;

/**
 * Activity for executing more detailed search queries
 * @author Matt
 *
 */
public class SearchActivity extends Activity{
	Button button;
	RadioGroup type;
	TextView id;
	TextView name;
	TextView price;
	String rating = new String();
	String sort = new String();
	String order = new String();
	String make = new String();
	String xml = new String();
	XMLAdapter xmlAdapter;
	
	/**
	 * Create method to Set up fields and button
	 * The brand menu is done slightly differently than the other spinners
	 * since it is not being built from a local resource but rather 
	 * getting a list of unique values from the server.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    
	    ArrayList<CharSequence> list = getIntent().getCharSequenceArrayListExtra("makelist");
	    
	    Spinner bSpinner = (Spinner) findViewById(R.id.brandMenu);
	    ArrayAdapter bAdapter = new ArrayAdapter(
	            this, android.R.layout.simple_spinner_item, list);
	    bSpinner.setAdapter(bAdapter);
	   
	    Log.d("makeAct", String.valueOf(list.size()));

	    Spinner rSpinner = (Spinner) findViewById(R.id.ratingMenu);
	    ArrayAdapter<CharSequence> rAdapter = ArrayAdapter.createFromResource(
	            this, R.array.rating_array, android.R.layout.simple_spinner_item);
	    rAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    rSpinner.setAdapter(rAdapter);
	    
	    Spinner sSpinner = (Spinner) findViewById(R.id.sortList);
	    ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(
	            this, R.array.sort_array, android.R.layout.simple_spinner_item);
	    sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    sSpinner.setAdapter(sAdapter);
	    
	    Spinner oSpinner = (Spinner) findViewById(R.id.orderList);
	    ArrayAdapter<CharSequence> oAdapter = ArrayAdapter.createFromResource(
	            this, R.array.order_array, android.R.layout.simple_spinner_item);
	    oAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    oSpinner.setAdapter(oAdapter);
	    
	    type = (RadioGroup)findViewById(R.id.typeSelection);
	    
	    name = (TextView)findViewById(R.id.nameText);
	    price = (TextView)findViewById(R.id.priceText);
	    
	    bSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        rSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        sSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        oSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
        button = (Button)findViewById(R.id.s_button);
        button.setOnClickListener(sListener);     
	}
	
	/**
	 * Button listener to submit the search when the button is clicked
	 */
    private OnClickListener sListener = new OnClickListener() {
        public void onClick(View v) {
        	Log.d("text is ", name.getText().toString());
        	SearchUtils.Search(getApplicationContext(), buildmap());
        }
    };

    /**
     * Listener for the various spinners in the activity, sets values when they are changed. 
     * @author Matt
     *
     */
    private class MyOnItemSelectedListener implements OnItemSelectedListener {

    	    public void onItemSelected(AdapterView<?> parent,
    	        View view, int pos, long id) {
    	    		if(parent.equals(findViewById(R.id.sortList))){
    	    			switch(pos){
    	    			case 0: sort = "name";
    	    			break;
    	    			case 1: sort = "price";
    	    			break;
    	    			case 2: sort = "rating";
    	    			break;
    	    			default: sort = "name";
    	    			}
    	    			//sort = parent.getItemAtPosition(pos).toString();
    	    		}
    	    		else if(parent.equals(findViewById(R.id.orderList))){
       	    			switch(pos){
    	    			case 0: order = "asc";
    	    			break;
    	    			case 1: order = "desc";
    	    			break;
    	    			default: order = "asc";
       	    			}
    	    			//order = parent.getItemAtPosition(pos).toString();
    	    		}
    	    		else if(parent.equals(findViewById(R.id.ratingMenu))){
    	    			rating = parent.getItemAtPosition(pos).toString();
    	    		}
    	    		else{
    	    			if(pos == 0)
    	    				make = "any";
    	    			else
    	    				make = parent.getItemAtPosition(pos).toString();
    	    		}
    	    }
        
    	    @Override
    	    public void onNothingSelected(AdapterView<?> arg0) {
    	    	// TODO Auto-generated method stub
    	    }
    }
    
    /**
     * Map builder to create map of key/value pairs to be sent to the Url builder.
     * @return
     */
	private Map<String, String> buildmap() {
    	Map<String, String> map = new TreeMap<String, String>();
		map.put("name", name.getText().toString());
		map.put("make", make);
		map.put("price", price.getText().toString().replace("$", ""));
		map.put("type", getType());
		map.put("rating", rating);
		map.put("sort", sort);
		map.put("order", order);
		return map;
	}

	/**
	 * Since the type is stored as an int in the DB here I convert the selection
	 * to its coresponding value.
	 * @return
	 */
	private String getType() {
		final int B0 = R.id.radio0;
		final int B1 = R.id.radio1; 
		final int B2 = R.id.radio2;
		int id = type.getCheckedRadioButtonId();
		switch (id){
		case B0: return "1";
		case B1: return "2";
		case B2: return "3";
		default: return "";
		}
	}
}
