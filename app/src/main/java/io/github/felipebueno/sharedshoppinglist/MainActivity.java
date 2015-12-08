package io.github.felipebueno.sharedshoppinglist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	MainAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ArrayList<Item> items = new ArrayList<>();
		adapter = new MainAdapter(this, items);

		ListView theListView = (ListView) findViewById(R.id.theListView);
		theListView.setAdapter(adapter);

		// START Dummy data
		String[] itemList = {
				"8 pães",
				"1kg laranja",
				"1pct arroz",
				"1pct feijão",
				"1 azeite"
		};

		for (String name : itemList) {
			Item item = new Item(name, false);
			adapter.add(item);
		}
		// END Dummy data

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				addItem();
			}
		});
	}

	private void addItem() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("What?");

		final EditText input = new EditText(this);
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(input);

		builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Item item = new Item(input.getText().toString(), false);
				adapter.add(item);
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		builder.show();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_sort_by_name) {
			Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
			return true;
		} else if (id == R.id.action_sort_by_done) {
			Toast.makeText(this, "Not implemented yet", Toast.LENGTH_SHORT).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
