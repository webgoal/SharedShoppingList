package io.github.felipebueno.sharedshoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MainAdapter extends ArrayAdapter<String> {

	public MainAdapter(Context context, String[] values) {
		super(context, R.layout.row_layout, values);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		View v = inflater.inflate(R.layout.row_layout, parent, false);

		String thing = getItem(position);
		TextView tv = (TextView) v.findViewById(R.id.textView1);
		tv.setText(thing);

		
		return v;
	}
}
