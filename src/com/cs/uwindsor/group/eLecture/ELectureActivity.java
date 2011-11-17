package com.cs.uwindsor.group.eLecture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ELectureActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        Button button = (Button)findViewById(R.id.connectButton);
        button.setOnClickListener(playListener);

    }
    
    private OnClickListener playListener = new OnClickListener() {
        public void onClick(View v) {
        	
        	Intent i = new Intent(getApplicationContext(), StreamPlayer.class);
        	startActivity(i);
        }
    };

}