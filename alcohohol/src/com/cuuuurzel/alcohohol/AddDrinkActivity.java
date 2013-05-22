package com.cuuuurzel.alcohohol;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Called to add drinks.
 */
public class AddDrinkActivity extends Activity {

	/**
	 * Buffer for the new drinks.
	 */
	public ArrayList<Drink> drinks = new ArrayList<Drink>();
	
	//List of elements of the list view.
	private ArrayList<String> drinksStr = new ArrayList<String>();
	
	//String adapter which will handle the list view.
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_add_drink );

		this.adapter = new ArrayAdapter<String>(
												  this,
												  android.R.layout.simple_list_item_1,
												  drinksStr
												);
		
		ListView lstHistory = (ListView) findViewById( R.id.lstHistory );
		lstHistory.setAdapter( this.adapter );	           
	}

    public void confirmAdd( View view ) {      
        EditText edtP = (EditText) findViewById( R.id.edtP );
        EditText edtQ = (EditText) findViewById( R.id.edtQ );
        EditText edtT = (EditText) findViewById( R.id.edtT );
        try {
        	float p = Float.parseFloat( edtP.getText().toString() );
        	float q = Float.parseFloat( edtQ.getText().toString() );
        	float t = Float.parseFloat( edtT.getText().toString() );
        	this.drinks.add( new Drink( p/100, q, t ) );
        	this.drinksStr.add( this.drinks.get( this.drinks.size()-1 ).toString() );
            this.adapter.notifyDataSetChanged();
        } catch ( NumberFormatException e ) {        	
        }
    }
    
    public void addEnd( View view ) { 
    	Intent resultIntent = new Intent();
    	resultIntent.putExtra( "drinks", this.drinks );
	    setResult( Activity.RESULT_OK, resultIntent );
	    finish();
    }
}
