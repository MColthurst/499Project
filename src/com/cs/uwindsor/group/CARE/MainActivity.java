package com.cs.uwindsor.group.CARE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
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
	
    private OnClickListener tListener = new OnClickListener() {
        public void onClick(View v) {
        	Intent i = new Intent(getApplicationContext(), TypeActivity.class);
        	startActivity(i);
        }
    };
    
    private OnClickListener sListener = new OnClickListener() {
        public void onClick(View v) {
        	Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        	startActivity(i);
        }
    };
	
	
}