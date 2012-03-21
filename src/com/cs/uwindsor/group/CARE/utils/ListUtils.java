package com.cs.uwindsor.group.CARE.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.cs.uwindsor.group.CARE.DetailsActivity;
import com.cs.uwindsor.group.CARE.db.XMLAdapter;
import com.cs.uwindsor.group.CARE.db.XMLHelper;
import com.cs.uwindsor.group.CARE.http.AsyncHttpClient;
import com.cs.uwindsor.group.CARE.http.AsyncHttpResponseHandler;

public class ListUtils {
	private static AsyncHttpClient client = new AsyncHttpClient();
	static String xml = new String();
	static int r;
	static List<Element> elementL = new ArrayList<Element>();
	static ArrayList<String> reviewsList = new ArrayList<String>();
	
	public static void viewDetails (Context context, String id) {
		
		final Context that = (Context)context;
		String url = "http://care.cs.uwindsor.ca/view.php?id=".concat(id);
		
		Log.d("View URL: ", url);
		
		client.get(url, new AsyncHttpResponseHandler(){
			
			@Override
			public void onSuccess (String response) {
				Log.d("xml ", response);
				xml = response;
				try {
					XMLAdapter xmlA = new XMLAdapter(XMLHelper.xmlStringToDocument(xml), "id");
					elementL = xmlA.getAllRecords();
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
				Intent intent = new Intent(that, DetailsActivity.class);
				for(int i=0; i<elementL.size(); i++) {
					if(!elementL.get(i).getNodeName().equals("reviews")){
					Log.d("Nodename" , elementL.get(i).getNodeName() + " -- " + elementL.get(i).getTextContent());
					intent.putExtra(elementL.get(i).getNodeName(), elementL.get(i).getTextContent());
					}
					else{
						reviewsList = getReviews(elementL.get(i));
						r = i;
					}

				}
				
				intent.putStringArrayListExtra("reviews", reviewsList);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				that.startActivity(intent);	
			}
		});
	}
	
	private static ArrayList<String> getReviews(Element e){
		ArrayList<String> returnList = new ArrayList<String>();
		NodeList reviews = e.getChildNodes();
		
		Log.d("reviews", String.valueOf(e.getChildNodes().getLength()));
		for(int k = 0 ; k<reviews.getLength(); k++)
			Log.d("Nodename" , reviews.item(k).getNodeName() + " -- " + reviews.item(k).getTextContent());

		for(int i = 0 ; i<reviews.getLength(); i++){
				if(reviews.item(i).getNodeName().equals("review")){
					String temp = "";
					NodeList review = reviews.item(i).getChildNodes();
					
					Log.d("review", String.valueOf(reviews.item(1).getChildNodes().getLength()));
					for(int k = 0 ; k<review.getLength(); k++)
						Log.d("Nodename" , review.item(k).getNodeName() + " -- " + review.item(k).getTextContent());

					temp = review.item(1).getTextContent();
					temp = temp.concat("#!#");
					temp = temp.concat(review.item(3).getTextContent());
					temp = temp.concat("#!#");
					temp = temp.concat(review.item(5).getTextContent());
					returnList.add(temp);
				}
			}
		Log.d("SIZE", String.valueOf(returnList.size()));
		return returnList;
	}
}
