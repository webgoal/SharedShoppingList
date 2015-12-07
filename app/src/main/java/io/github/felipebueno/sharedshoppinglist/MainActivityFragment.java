package io.github.felipebueno.sharedshoppinglist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import static io.github.felipebueno.sharedshoppinglist.MainActivity.*;
import static io.github.felipebueno.sharedshoppinglist.MainActivity.toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

	private ListView itemList;
	private Context context;

	public MainActivityFragment() {

	}

	@Override
	public void onAttach(Context context) {
		this.context = context;
		super.onAttach(this.context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		toast(this.context, "hello from fragment");
		return inflater.inflate(R.layout.fragment_main, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		String[] items = {"blah 0", "blah 1", "blah 2", "blah 3", "blah 4", "blah5"};

		ListAdapter la = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items);

		lv.setAdapter(la);

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				toast(context, "hello from item->" + id);
			}
		});

	}
}
