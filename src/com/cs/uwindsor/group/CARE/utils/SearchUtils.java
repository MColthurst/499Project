package com.cs.uwindsor.group.CARE.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cs.uwindsor.group.CARE.http.*;


public class SearchUtils {
	private final static String baseURL = "http://care.cs.uwindsor.ca/?";
	private static AsyncHttpClient client = new AsyncHttpClient();
	static String rValue = null;

	
	public static String Searchbyname(Context context, String name){
		
		final Context that = (Context)context;
		
		RequestParams params = new RequestParams();
		params.put("name", name);
		client.post(baseURL, params, new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess (String response) {
				Log.i("xml ", response);
				rValue = response;
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
}
