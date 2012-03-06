package com.cs.uwindsor.group.CARE;

import java.util.Map;
import java.util.TreeMap;

import com.cs.uwindsor.group.CARE.utils.SearchUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity{
	Button button;
	TextView name;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    
	    name = (TextView)findViewById(R.id.nameText);
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

		private Map<String, String> buildmap() {
        	Map<String, String> map = new TreeMap<String, String>();
			map.put("name", name.getText().toString());
			return map;
		}
    };
}
