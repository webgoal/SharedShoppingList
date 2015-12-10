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

import sneer.android.Message;
import sneer.android.PartnerSession;

public class MainActivity extends AppCompatActivity {

	MainAdapter adapter;

	PartnerSession session;

	@Override
	protected void onDestroy() {
		session.close();
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		session = PartnerSession.join(this, new PartnerSession.Listener() {
			@Override
			public void onUpToDate() {

			}

			@Override
			public void onMessage(Message message) {
				handle(message);
			}
		});

		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		ArrayList<Item> items = new ArrayList<>();
		adapter = new MainAdapter(this, items, session);

		ListView theListView = (ListView) findViewById(R.id.theListView);
		theListView.setAdapter(adapter);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				addItem();
			}
		});
	}

	private void handle(Message message) {
		if(message.payload() instanceof ArrayList) {

			System.out.println("handle: " + message.payload());

			ArrayList<String> payload = (ArrayList<String>) message.payload();
			String action = payload.get(0);
			String name = payload.get(1);
			switch (action) {
				case "add":
					adapter.add(new Item(name, false));
					break;
				case "remove":
					adapter.remove(new Item(name, false));
					break;
			}

//			adapter.notifyDataSetChanged();
		}
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
				ArrayList<String> payload = new ArrayList<String>();
				payload.add("add");
				payload.add(input.getText().toString());
				session.send(payload);
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
