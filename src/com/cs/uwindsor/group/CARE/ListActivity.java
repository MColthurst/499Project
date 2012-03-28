package com.cs.uwindsor.group.CARE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.cs.uwindsor.group.CARE.adapters.SimpleAdapter;
import com.cs.uwindsor.group.CARE.db.XMLAdapter;
import com.cs.uwindsor.group.CARE.db.XMLHelper;
import com.cs.uwindsor.group.CARE.utils.ListUtils;

/**
 * Activity to display the list of all car seats returned by a search.
 * @author Matt
 *
 */
public class ListActivity extends Activity{
	List<Element> records = new ArrayList<Element>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.list); 
	    
        //Parse the XML document 
	    String temp = getIntent().getStringExtra("xml");
	    try {
			records = ParseXML(temp).getAllRecords();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Log.d("number of Records", String.valueOf(records.size()));
	    Adapter a = new SimpleAdapter(this, records);
	    
		ListView list = (ListView) findViewById(R.id.listview);
		list.setAdapter((ListAdapter) a);
		list.setOnItemClickListener(new OnItemClickListener() {

			//When an item is clicked call viewDetails to start a DetailsActivity.
			public void onItemClick (AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				//Log.d("clicked", records.get(arg2).getChildNodes().item(0).toString());
				Element e = records.get(arg2);
				//Log.d("ID: ", e.getChildNodes().item(1).getTextContent());
				ListUtils.viewDetails(getApplicationContext(), e.getChildNodes().item(1).getTextContent());
			}
		}); 
	}

	/**
	 * Small utility method to parse an xml string and get its xmlAdapter for processing.
	 * 
	 * @param xml
	 * @return	
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	private XMLAdapter ParseXML(String xml) throws ParserConfigurationException, IOException, SAXException {
		
		Document xmlDoc = XMLHelper.createNewDocument();
		xmlDoc = XMLHelper.xmlStringToDocument(xml);		
		XMLAdapter xmlAdapter = new XMLAdapter(xmlDoc, "id");
		
		//Log.d("Doc Element", xmlDoc.getDocumentElement().getNodeName());
		
		return xmlAdapter;
	}
}

