package com.cs.uwindsor.group.CARE;

import java.util.Map;
import java.util.TreeMap;

import com.cs.uwindsor.group.CARE.TypeActivity.MyOnItemSelectedListener;
import com.cs.uwindsor.group.CARE.utils.SearchUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class SearchActivity extends Activity{
	Button button;
	TextView id;
	TextView name;
	TextView price;
	String rating = new String();

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    
	    Spinner Spinner = (Spinner) findViewById(R.id.ratingMenu);
	    ArrayAdapter<CharSequence> Adapter = ArrayAdapter.createFromResource(
	            this, R.array.rating_array, android.R.layout.simple_spinner_item);
	    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    Spinner.setAdapter(Adapter);
	    
	    name = (TextView)findViewById(R.id.nameText);
	    id = (TextView)findViewById(R.id.idText);
	    price = (TextView)findViewById(R.id.priceText);
        Spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        button = (Button)findViewById(R.id.s_button);
        button.setOnClickListener(sListener);     
	}
	
    private OnClickListener sListener = new OnClickListener() {
        public void onClick(View v) {
        	Log.d("text is ", name.getText().toString());
        	String xml = SearchUtils.Search(getApplicationContext(), buildmap());
        	Intent i = new Intent(getApplicationContext(), ListActivity.class);
        	i.putExtra("xml", xml);
        	startActivity(i);
        }
    };

    private class MyOnItemSelectedListener implements OnItemSelectedListener {

    	    public void onItemSelected(AdapterView<?> parent,
    	        View view, int pos, long id) {
    	    		rating = parent.getItemAtPosition(pos).toString();
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
		return map;
	}
}
