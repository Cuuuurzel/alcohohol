package com.cuuuurzel.alcohohol;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

/**
 * Called to add drinks.
 */
public class AddDrinkActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_add_drink );     
	}

    public void confirmAdd( View view ) {      
        EditText edtP = (EditText) findViewById( R.id.edtP );
        TimePicker tmpT = (TimePicker) findViewById( R.id.tmpT );
        
        float p, q, t;
        p = Float.parseFloat( edtP.getText().toString() );
        q = this.getQuantityCL();
        t = tmpT.getCurrentMinute() + tmpT.getCurrentHour()*60;
        Intent resultIntent = new Intent();
        resultIntent.putExtra( "newDrink", new Drink( p/100, q, t ) );
    	setResult( Activity.RESULT_OK, resultIntent );
    	finish();
    }
        
    private float getQuantityCL() {
        RadioGroup rdgMeasure = (RadioGroup) findViewById( R.id.rdgMeasure );
        int radioID = rdgMeasure.getCheckedRadioButtonId();
        System.out.println( "radioID " + radioID );
        RadioButton rdb = (RadioButton) findViewById( radioID );
        System.out.println( "radio " + rdb );
    	String s = (String) rdb.getText();
    	float q = 0;
    	if ( s.equals( "One Shoot" )) { q = 3.5f; }
    	if ( s.equals( "Two Shoot" )) { q = 7; }
    	if ( s.equals( "A 0.2L Glass" )) { q = 20; }
    	if ( s.equals( "A 0.4L Glass" )) { q = 40; }
    	if ( s.equals( "A 0.5L Glass" )) { q = 50; }
    	if ( s.equals("One Liter" )) { q = 100; }
    	return q;
    }
}
