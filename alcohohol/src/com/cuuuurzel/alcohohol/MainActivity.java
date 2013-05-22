package com.cuuuurzel.alcohohol;

import java.util.ArrayList;

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
	
	Liver l = new Liver();    

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        
        viewHandler = new Handler();
    	ppBAC = (PointPlotter) findViewById( R.id.ppBAC );
        ppBAC.setZoom( 0, 300, 0, 3 );
        Paint p = new Paint();
        p.setColor( Color.BLACK );
        p.setStrokeWidth( 3 );
        ppBAC.setPaint( p );    	
    	this.drawBAC();
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
        			for ( float time=0; time<300; time+=dt ) {
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
    
    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) { 
    	super.onActivityResult( requestCode, resultCode, data );
    	
    	if ( resultCode == Activity.RESULT_OK ) {    		
    		switch ( requestCode ) {
	    		case ADD_DRINK_ACTIVITY : {
			    	ArrayList<Drink> drinks = data.getParcelableArrayListExtra( "drinks" );
			    	this.l.drink( drinks );    	
			    	break; 
		    	}
	    		case SETTINGS_ACTIVITY : {
	    	        ppBAC.setZoom( data.getIntExtra( "startTime", 0 ),
	    	        		 	   data.getIntExtra( "endTime", 300 ),
	    	        		       data.getFloatExtra( "minBAC", 0 ),
	    	        		       data.getFloatExtra( "maxBAC", 3.0f )
	    	        		     );
			    	break; 
		    	}
    		}
	    	this.drawBAC();
    	}
    }
}