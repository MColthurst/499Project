package com.cs.uwindsor.group.CARE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cs.uwindsor.group.CARE.db.XMLAdapter;
import com.cs.uwindsor.group.CARE.db.XMLHelper;
import com.cs.uwindsor.group.CARE.utils.SearchUtils;

public class SearchActivity extends Activity{
	Button button;
	TextView id;
	TextView name;
	TextView price;
	String rating = new String();
	String sort = new String();
	String order = new String();
	String xml = new String();
	XMLAdapter xmlAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    
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
	    
	    name = (TextView)findViewById(R.id.nameText);
	    id = (TextView)findViewById(R.id.idText);
	    price = (TextView)findViewById(R.id.priceText);
	    
        rSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        sSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        oSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        
        button = (Button)findViewById(R.id.s_button);
        button.setOnClickListener(sListener);     
	}
	
    private OnClickListener sListener = new OnClickListener() {
        public void onClick(View v) {
        	Log.d("text is ", name.getText().toString());
        	SearchUtils.Search(getApplicationContext(), buildmap());
        }
    };

    private class MyOnItemSelectedListener implements OnItemSelectedListener {

    	    public void onItemSelected(AdapterView<?> parent,
    	        View view, int pos, long id) {
    	    		if(parent.equals(findViewById(R.id.sortList))){
    	    			sort = parent.getItemAtPosition(pos).toString();
    	    		}
    	    		else if(parent.equals(findViewById(R.id.orderList))){
    	    			order = parent.getItemAtPosition(pos).toString();
    	    		}
    	    		else{
    	    			rating = parent.getItemAtPosition(pos).toString();
    	    		}
    	    }
        
    	    @Override
    	    public void onNothingSelected(AdapterView<?> arg0) {
    	    	// TODO Auto-generated method stub
    	    }
    }
    
	private Map<String, String> buildmap() {
    	Map<String, String> map = new TreeMap<String, String>();
		map.put("name", name.getText().toString());
		map.put("id", id.getText().toString());
		map.put("price", price.getText().toString());
		map.put("rating", rating);
		map.put("sort", sort);
		map.put("order", order);
		return map;
	}
}
