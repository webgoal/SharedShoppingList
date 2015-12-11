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

import io.github.felipebueno.sharedshoppinglist.actions.ItemAction;
import io.github.felipebueno.sharedshoppinglist.actions.ItemAddAction;
import io.github.felipebueno.sharedshoppinglist.actions.ItemCheckAction;
import io.github.felipebueno.sharedshoppinglist.actions.ItemRemoveAction;
import io.github.felipebueno.sharedshoppinglist.actions.ItemUncheckAction;
import sneer.android.Message;
import sneer.android.PartnerSession;

public class MainActivity extends AppCompatActivity implements PartnerSession.Listener {
	ArrayList<Item> items;
	MainAdapter adapter;
	ListView theListView;

	PartnerSession session;

	@Override
	protected void onDestroy() {
		session.close();
		super.onDestroy();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		session = PartnerSession.join(this, this);

		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		items = new ArrayList<>();
		adapter = new MainAdapter(this, items);

		theListView = (ListView) findViewById(R.id.theListView);
		theListView.setAdapter(adapter);

		adapter.setOnItemCheckListener(new MainAdapter.OnItemCheckChangeListener() {
			@Override
			public void onItemCheckChange(Item item, boolean isChecked) {
				ItemAction action = isChecked ? new ItemCheckAction(item.name) : new ItemUncheckAction(item.name);
				MainActivity.this.sendToSession(action);
			}
		});

		adapter.setOnItemRemoveListener(new MainAdapter.OnItemRemoveListener() {
			@Override
			public void onItemRemove(Item item) {
				MainActivity.this.sendToSession(new ItemRemoveAction(item.name));
			}
		});

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				addItem();
			}
		});
	}

	private void handle(Message message) {
		String messageJsonString = (String)message.payload();
		ItemAction.fromJson(messageJsonString).run(items);
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
				sendToSession(new ItemAddAction(input.getText().toString()));
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

	@Override
	public void onUpToDate() {
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onMessage(Message message) {
		handle(message);
	}

	public void sendToSession(ItemAction object){
		session.send(object.toJson());
	}
}
