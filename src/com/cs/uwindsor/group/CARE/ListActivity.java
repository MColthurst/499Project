package com.cs.uwindsor.group.CARE;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

import com.cs.uwindsor.group.CARE.db.XMLAdapter;

import android.app.Activity;
import android.os.Bundle;

public class ListActivity extends Activity{
	List<Element> records = new ArrayList<Element>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.list); 
	    
	    XMLAdapter temp = getIntent().getParcelableExtra("xml");
	    records = temp.getAllRecords();
	    temp = null;
	}
}
