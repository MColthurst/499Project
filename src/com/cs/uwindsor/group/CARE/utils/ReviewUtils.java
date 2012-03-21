package com.cs.uwindsor.group.CARE.utils;

import java.util.ArrayList;
import java.util.List;

public class ReviewUtils {

	public static String[] firstReview(ArrayList<String> reviews){
		String[] review = new String[3];
		if(reviews.size() != 0){
			String unParsedReview = reviews.get(reviews.size()-1);
			review = unParsedReview.split("#!#");
			return review;
		}
		else{
			return null;
		}
			
	}
	
	public static List<String[]> allReviews(ArrayList<String> reviews){
		List<String[]> reviewsRet = new ArrayList<String[]>();
		for (int i=0; i<reviews.size(); i++) {
			String[] temp = new String[3];
			String unParsedReview = reviews.get(i);
			temp = unParsedReview.split("#!#");
			reviewsRet.add(temp);
		}
		return reviewsRet;
	}
}
