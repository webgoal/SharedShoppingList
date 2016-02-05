package br.com.webgoal.sharedshoppinglist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.webgoal.sharedshoppinglist.actions.ItemAction;
import br.com.webgoal.sharedshoppinglist.actions.ItemAddAction;
import br.com.webgoal.sharedshoppinglist.actions.ItemCheckAction;
import br.com.webgoal.sharedshoppinglist.actions.ItemRemoveAction;
import br.com.webgoal.sharedshoppinglist.actions.ItemUncheckAction;
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
