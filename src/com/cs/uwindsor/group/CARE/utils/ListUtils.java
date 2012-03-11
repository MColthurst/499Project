package com.cs.uwindsor.group.CARE.utils;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cs.uwindsor.group.CARE.DetailsActivity;

public class ListUtils {
	public static void viewDetails (Context that, Element e) {

		Intent intent = new Intent(that, DetailsActivity.class);
		NodeList ns = e.getChildNodes();
		for(int i=0; i<ns.getLength(); i++) {
			Log.d("Nodename" , ns.item(i).getNodeName() + " -- " + ns.item(i).getTextContent());
			intent.putExtra(ns.item(i).getNodeName(), ns.item(i).getTextContent());
		}
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		that.startActivity(intent);
	}
	
	public static void sort(String s){
		
	}
	
	public static void order(String o){
		
	}
}
