package com.cs.uwindsor.group.CARE;

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
	TextView text;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    
	    text = (TextView)findViewById(R.id.searchBox);
        button = (Button)findViewById(R.id.s_button);
        button.setOnClickListener(sListener);     
	}
	
    private OnClickListener sListener = new OnClickListener() {
        public void onClick(View v) {
        	Log.d("text is ", text.getText().toString());
        	String xml = SearchUtils.Searchbyname(getApplicationContext(), text.getText().toString());
        	Intent i = new Intent(getApplicationContext(), ListActivity.class);
        	i.putExtra("xml", xml);
        	startActivity(i);
        }
    };
}
