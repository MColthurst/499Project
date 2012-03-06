package com.cs.uwindsor.group.CARE;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	Button button;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    
        button = (Button)findViewById(R.id.tButton);
        button.setOnClickListener(tListener);     
	}
	
    private OnClickListener tListener = new OnClickListener() {
        public void onClick(View v) {
        	Intent i = new Intent(getApplicationContext(), TypeActivity.class);
        	startActivity(i);
        }
    };
	
	
}