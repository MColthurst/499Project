package com.cs.uwindsor.group.CARE.adapters;

import java.util.List;

import org.w3c.dom.Element;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cs.uwindsor.group.CARE.R;

/**
 * Adapter for the list of car seats.
 * @author Matt
 *
 */
public class SimpleAdapter extends BaseAdapter {

	private Activity activity;
	private List<Element> data;
	private static LayoutInflater inflater = null;

	public SimpleAdapter(Activity a, List<Element> d) {
		activity = a;
		data = d;
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
			vi = inflater.inflate(R.layout.rows2, null);

		TextView text = (TextView) vi.findViewById(R.id.seat_name);
		//Log.d("name" , data.get(position).getElementsByTagName("name").item(0).getTextContent());
		text.setText(data.get(position).getElementsByTagName("name").item(0).getTextContent());

		text = (TextView) vi.findViewById(R.id.seat_price);
		text.setText("$" + data.get(position).getElementsByTagName("price").item(0).getTextContent());
		
		text = (TextView) vi.findViewById(R.id.seat_rating);
		text.setText(String.valueOf("Rating: " + data.get(position).getElementsByTagName("rating").item(0).getTextContent()) + "/5");

		return vi;
	}
}