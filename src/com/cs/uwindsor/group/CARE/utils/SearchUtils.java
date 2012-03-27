package com.cs.uwindsor.group.CARE.utils;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cs.uwindsor.group.CARE.ListActivity;
import com.cs.uwindsor.group.CARE.http.AsyncHttpClient;
import com.cs.uwindsor.group.CARE.http.AsyncHttpResponseHandler;


public class SearchUtils {
	private static final String baseURL = "http://care.cs.uwindsor.ca/search.php?";
	private static AsyncHttpClient client = new AsyncHttpClient();
	static String xml = new String();
	static String url = new String();

	
	public static void Search(Context context, Map<String, String> map){
		
		final Context that = (Context)context;
		
		url = URLbuilder.buildurl(map, baseURL);
		Log.d("url: ", url);

		client.get(url, new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess (String response) {
				Log.d("xml ", response);
				xml = response;
				
	        	Intent i = new Intent(that, ListActivity.class);
	        	i.putExtra("xml", xml);
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
