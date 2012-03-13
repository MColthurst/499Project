package com.cs.uwindsor.group.CARE.utils;

import java.util.Map;

public class URLbuilder {
	
	public static String buildurl(Map<String, String> map, String baseURL) {
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
