package com.cuuuurzel.alcohohol;

import java.util.Calendar;
import java.util.Locale;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Represent the main activity.
 */
public class MainActivity extends Activity {
	
	PointPlotter ppBAC;
	
	Handler viewHandler;

	final int ADD_DRINK_ACTIVITY = 0;
	final int SETTINGS_ACTIVITY = 1;
	final int HISTORY_ACTIVITY = 2;
	
	Liver l;    
	
	int startTime, endTime;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        this.viewHandler = new Handler();
        this.l = new Liver();
                
        this.startTime = Calendar.getInstance( Locale.ITALY ).get( Calendar.HOUR )*60 + 
        		         Calendar.getInstance( Locale.ITALY ).get( Calendar.MINUTE );
        this.endTime = startTime + 180;    
        
        //Paint object used by the plotter
        Paint p = new Paint();
        p.setColor( Color.BLACK );
        p.setStrokeWidth( 3 );
        
        //Plotter setup
    	ppBAC = (PointPlotter) findViewById( R.id.ppBAC );
        ppBAC.setPaint( p );
        ppBAC.setZoom( this.startTime, this.endTime, 0, 3.0f );
    }
    
    /**
     * Forces plotter redrawing.
     */
    private void drawBAC() {
        this.viewHandler.postDelayed( 
        	new Runnable() {
        		public void run() {
        			ppBAC.flush();
        			ppBAC.push( 0, 0.5f, ppBAC.getMaxX(), 0.5f );
        			float dt = 10f;

	    			System.out.println( startTime + "   " + endTime );
        			for ( float time=startTime; time<endTime; time+=dt ) {
        				ppBAC.push( time, l.alcohol(time), time+dt, l.alcohol(time+dt) );
        			}
    				ppBAC.invalidate();
        		}
        	}, 1 
        );    	
    }
    
    /**
     * Called when the user wants to add a drink.
     */
    public void addDrink( View view ) {
    	Intent intentMain = new Intent( 
    			                        MainActivity.this, 
                						AddDrinkActivity.class 
                				      );
    	MainActivity.this.startActivityForResult( intentMain, ADD_DRINK_ACTIVITY );
    }
    
    /**
     * Called when the user wants to change the settings.
     */
    public void showSettings( View view ) {
    	Intent intentMain = new Intent( 
    			                        MainActivity.this, 
                						SettingsActivity.class 
                				      );
    	MainActivity.this.startActivityForResult( intentMain, SETTINGS_ACTIVITY );
    }
    
    /**
     * Called when the user wants to check the drinks list.
     */
    public void showHistory( View view ) {
    	Intent intentMain = new Intent( 
    			                        MainActivity.this, 
                						HistoryActivity.class 
                				      );
    	intentMain.putExtra( "drinks", this.l.drinks );
    	MainActivity.this.startActivityForResult( intentMain, HISTORY_ACTIVITY );
    }
    
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) { 
    	super.onActivityResult( requestCode, resultCode, data );
    	
    	if ( resultCode == Activity.RESULT_OK ) {    		
    		switch ( requestCode ) {
	    		case ADD_DRINK_ACTIVITY : {
	    			Drink d = (Drink) data.getParcelableExtra( "newDrink" );
	    			System.out.println( d );
			    	this.l.drink( d );    	
			    	break; 
		    	}
	    		case SETTINGS_ACTIVITY : {
	    	        this.startTime = data.getIntExtra( "startTime", this.startTime );
	    	        this.endTime = data.getIntExtra( "endTime", this.endTime );
	    	        ppBAC.setZoom( this.startTime, this.endTime, 0, 3.0f );
			    	break; 
		    	}
	    		case HISTORY_ACTIVITY : {
			    	break; 
		    	}
    		}
	    	this.drawBAC();
    	}
    }
}