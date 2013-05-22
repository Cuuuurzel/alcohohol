package com.cuuuurzel.alcohohol;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

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
		//configuration from the form
		result.putExtra( "startTime", Integer.parseInt( ( (EditText) findViewById( R.id.edtStartTime ) ).getText().toString() ) );
		result.putExtra( "endTime", Integer.parseInt( ( (EditText) findViewById( R.id.edtEndTime ) ).getText().toString() ) );
		result.putExtra( "minBAC", Float.parseFloat( ( (EditText) findViewById( R.id.edtMinBAC ) ).getText().toString() ) );
		result.putExtra( "maxBAC", Float.parseFloat( ( (EditText) findViewById( R.id.edtMaxBAC ) ).getText().toString() ) );
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
