package com.cs.uwindsor.group.CARE.utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cs.uwindsor.group.CARE.SearchActivity;
import com.cs.uwindsor.group.CARE.db.XMLAdapter;
import com.cs.uwindsor.group.CARE.db.XMLHelper;
import com.cs.uwindsor.group.CARE.http.AsyncHttpClient;
import com.cs.uwindsor.group.CARE.http.AsyncHttpResponseHandler;

public class BrandList {
	private static final String baseURL = "http://care.cs.uwindsor.ca/listmakes.php";
	private static AsyncHttpClient client = new AsyncHttpClient();
	static String xml = new String();
	static ArrayList<CharSequence> list = new ArrayList<CharSequence>();
	
	/**
	 * Method to get Brand list from server and create a new SearchActivity with that list.
	 * @param context
	 */
	public static void List(Context context){
			
			final Context that = (Context)context;
			
			list.add("Any");
			
			client.get(baseURL, new AsyncHttpResponseHandler(){
				
				@Override
				public void onSuccess (String response) {
					Log.d("xml ", response);
					xml = response;
					
					XMLAdapter adpt;
					try {
						adpt = new XMLAdapter(XMLHelper.xmlStringToDocument(xml), "make");
						List<Element> tmp = adpt.getAllRecords();
						for(int i=0 ; i<tmp.size(); i++){
							Log.d("make",tmp.get(i).getTextContent());
							list.add(tmp.get(i).getTextContent());
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SAXException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
		        	Intent i = new Intent(that, SearchActivity.class);
		        	i.putCharSequenceArrayListExtra("makelist", list);
		        	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        	that.startActivity(i);
				}

				@Override
				public void onFailure (Throwable t) {

					Toast toast = Toast.makeText(that, "Internet not connected... Please connect and try again.",
							Toast.LENGTH_LONG);
					toast.show();
				}

				@Override
				public void onFinish () {

				}
			});
	}
}
