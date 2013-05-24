package com.cuuuurzel.alcohohol;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryActivity extends Activity {

	// String adapter which will handle the list view.
	private ArrayAdapter<String> adapter;

	public void onReturnRequest(View view) {
		setResult(Activity.RESULT_OK, new Intent());
		finish();
	}

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		
		ArrayList<Drink> ds = getIntent().getParcelableArrayListExtra( "drinks" );
		ArrayList<String> drinks = new ArrayList<String>();
		for (int i = 0; i < ds.size(); i++) {
			drinks.add(ds.get(i).toString());
		}
		this.adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, drinks);

		ListView lsth = (ListView) findViewById(R.id.lstHistory);
		lsth.setAdapter(this.adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.history, menu);
		return true;
	}

}
