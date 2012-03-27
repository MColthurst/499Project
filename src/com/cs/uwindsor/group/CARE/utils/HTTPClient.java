package com.cs.uwindsor.group.CARE.utils;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/* 
	Class used to make HTTP requests to our PHP scripts/mySQL database. 
	GET or POST methods are available.
	TODO: Provide methods for parsing the XML responses?
*/
public class HTTPClient {
	private HttpClient client;
	private HttpResponse response; 
	
	public HTTPClient(){
		client = new DefaultHttpClient();
	}
	
	/* 
		Method performs a GET request. 
		Input: Pass it the URL to query with parameters encoded.
		Output: Response returned from the request, as a string.
		Example Usage: 
			getRequest("http://care.cs.uwindsor.ca/query.php?price=100&rating=2");
	*/
    public String getRequest(String url){
    	try {
            HttpGet get = new HttpGet(url);
            response = client.execute(get);  
            HttpEntity resEntity = response.getEntity();  
            if (resEntity != null)
            	return EntityUtils.toString(resEntity);
			else
				return "";
	    } catch (Exception e) {
	        e.printStackTrace();
			return "Connection Error.";
	    }
    }
    
	/* 
		Method performs a POST request. 
		Input: Pass it the URL to query, and parameters as an Arraylist<NameValuePair>.
		Output: Response returned from the request, as a string.
		Example Usage: 
			params.add(new BasicNameValuePair("id", "123"));
			params.add(new BasicNameValuePair("rating", "3"));
			postRequest("http://care.cs.uwindsor.ca/query.php", params);
	*/
    public String postRequest(String url, List<NameValuePair> params) {
        HttpPost post = new HttpPost(url);

        try {
            post.setEntity(new UrlEncodedFormEntity(params));
            response = client.execute(post);
            HttpEntity resEntity = response.getEntity();  
            if (resEntity != null)
            	return EntityUtils.toString(resEntity);
            else 
				return "Connection Error.";
			
        } catch (ClientProtocolException e) {
			return "Connection Error.";
        } catch (IOException e) {
			return "Connection Error.";
        }
    } 
}
