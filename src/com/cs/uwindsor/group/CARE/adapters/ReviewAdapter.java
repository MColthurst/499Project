package com.cs.uwindsor.group.CARE.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cs.uwindsor.group.CARE.R;
import com.cs.uwindsor.group.CARE.utils.ReviewUtils;

/**
 * Adapter for the list of reviews
 * Almost the same as the Simple adapter, Would be possible to use only one
 * but it was simpler to copy and alter it for now.
 * @author Matt
 *
 */
public class ReviewAdapter extends BaseAdapter {

	private Activity activity;
	private List<String[]> data;
	private static LayoutInflater inflater = null;

	public ReviewAdapter(Activity a, ArrayList<String> d) {
		activity = a;
		data = ReviewUtils.allReviews(d);
		
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.rows, null);

		TextView text = (TextView) vi.findViewById(R.id.review_name);
		text.setText(data.get(position)[1]);

		text = (TextView) vi.findViewById(R.id.review_comment);
		text.setText(data.get(position)[2]);
		
		text = (TextView) vi.findViewById(R.id.review_rating);
		text.setText("Rating: " + data.get(position)[0] + "/5");

		return vi;
	}
}