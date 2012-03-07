package com.cs.uwindsor.group.CARE.utils;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cs.uwindsor.group.CARE.db.XMLHelper;
import com.cs.uwindsor.group.CARE.http.AsyncHttpClient;
import com.cs.uwindsor.group.CARE.http.AsyncHttpResponseHandler;


public class SearchUtils {
	private static final String baseURL = "http://care.cs.uwindsor.ca/?";
	private static AsyncHttpClient client = new AsyncHttpClient();
	static Document rValue;

	
	public static Document Search(Context context, Map<String, String> map){
		
		final Context that = (Context)context;
		
		try {
			rValue = XMLHelper.createNewDocument();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String url = buildurl(map);
		Log.d("url ", url);

		client.get(url, new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess (String response) {
				Log.i("xml ", response);
				try {
					rValue = XMLHelper.xmlStringToDocument(response);
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
		return rValue;
	}


	private static String buildurl(Map<String, String> map) {
		String url = baseURL;
		for (String key : map.keySet()) {
			if(map.get(key) != null){
				url = url.concat(key + "=");
				String temp = map.get(key);
				temp = temp.replaceAll("\\s", "+");
				url = url.concat(temp + "&");
			}
		}
		return url;
	}
}
