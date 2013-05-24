package com.cuuuurzel.alcohohol;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	//Pass the new configuration to the main activity and exit
	public void saveAndExit( View view ) {
		Intent result = new Intent();
		TimePicker tmpFrom = (TimePicker) findViewById( R.id.tmpFrom );
		EditText edtMinutes = (EditText) findViewById( R.id.edtMinutes );
		//configuration from the form
		int startTime = tmpFrom.getCurrentHour()*60 + tmpFrom.getCurrentMinute();
		result.putExtra( "startTime", startTime );
		result.putExtra( "endTime", startTime + Integer.parseInt( edtMinutes.getText().toString() ) );
		//rest of configuration
	    setResult( Activity.RESULT_OK, result );
	    finish();
	}
	
	//Exit without doing nothing
	public void goBack( View view ) {
	    setResult( Activity.RESULT_CANCELED, new Intent() );
	    finish();
	}
}
