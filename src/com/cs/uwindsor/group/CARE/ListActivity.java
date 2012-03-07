package com.cs.uwindsor.group.CARE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
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

import com.cs.uwindsor.group.CARE.db.XMLAdapter;
import com.cs.uwindsor.group.CARE.db.XMLHelper;

public class ListActivity extends Activity{
	List<Element> records = new ArrayList<Element>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.list); 
	    
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
	    temp = null;
	    
	    Log.d("number of Records", String.valueOf(records.size()));
	    Adapter a = new SimpleAdapter(this, records);
	    
		ListView list = (ListView) findViewById(R.id.listview);
		list.setAdapter((ListAdapter) a);
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick (AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				Log.d("clicked", records.get(arg2).toString());
				// arg2 is position;
				//m = movies[arg2];
				//MovieListUtils.handleSelection(that, m, true);
				// Cant be bothered using a real check condition
			}
		});
	}

	private XMLAdapter ParseXML(String xml) throws ParserConfigurationException, IOException, SAXException {
		
		Document xmlDoc = XMLHelper.createNewDocument();
		xmlDoc = XMLHelper.xmlStringToDocument(xml);		
		XMLAdapter xmlAdapter = new XMLAdapter(xmlDoc, "id");
		
		//Log.d("Doc Element", xmlDoc.getDocumentElement().getNodeName());
		
		return xmlAdapter;
	}
}

