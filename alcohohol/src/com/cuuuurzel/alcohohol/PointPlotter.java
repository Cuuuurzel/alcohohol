package com.cuuuurzel.alcohohol;

import java.util.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Plots points in a certain range.
 */
public class PointPlotter extends View {
	
	/**
	 * Used when drawing, declared here for faster drawing.
	 */
	private Paint paint = new Paint();    	
	
	/**
	 * List of points to draw.
	 */
	private ArrayList<Float> points = new ArrayList<Float>();
	
	/**
	 * List of lines to draw.
	 */
	private ArrayList<Float> lines = new ArrayList<Float>();

	/**
	 * Min representable x
	 */
	private float minx = 0;

	/**
	 * Max representable x
	 */
	private float maxx = 100;

	/**
	 * Min representable y
	 */
	private float miny = 0;

	/**
	 * Max representable y
	 */
	private float maxy = 100;	
	
	/**
	 * Creates the Plotter.
	 */
    public PointPlotter( Context context ) {
    	super( context );
    }

	/**
	 * Creates the Plotter.
	 */
    public PointPlotter( Context context, AttributeSet attr ){
    	super( context, attr );
    }

   	/**
   	 * Creates the Plotter.
   	 */
   	public PointPlotter( Context context, AttributeSet attr, int defaultStyles ){
    	super( context, attr, defaultStyles );
   	}
   	
   	/**
   	 * Inits the plotter scale.
   	 */
   	public void setZoom( float minx, float maxx, float miny, float maxy ) {
   		this.minx = minx;
   		this.miny = miny;
   		this.maxx = maxx;
   		this.maxy = maxy;
   	}
   	
   	/**
   	 * Adds a single point to the plotter queue.
   	 */
	public void push( float px, float py ) {
		float dx = Math.abs( this.maxx-this.minx );
		float dy = Math.abs( this.maxy-this.miny );
		float w = this.getMeasuredWidth();
		float h = this.getMeasuredHeight();
		this.points.add( ( ( px-this.minx ) / dx ) * w ); //x
		this.points.add( h - ( ( py-this.miny ) / dy ) * h ); //y
	}
   	
   	/**
   	 * Adds a single line segment to the plotter queue.
   	 */
	public void push( float p1x, float p1y, float p2x, float p2y ) {
		float dx = Math.abs( this.maxx-this.minx );
		float dy = Math.abs( this.maxy-this.miny );
		float w = this.getMeasuredWidth();
		float h = this.getMeasuredHeight();
		this.lines.add( ( ( p1x-this.minx ) / dx ) * w );
		this.lines.add( h - ( ( p1y-this.miny ) / dy ) * h );
		this.lines.add( ( ( p2x-this.minx ) / dx ) * w );
		this.lines.add( h - ( ( p2y-this.miny ) / dy ) * h );
	}
   	
   	/**
   	 * Draws the points.
   	 */
	@Override
    public void onDraw( Canvas canvas ) {
        for ( int i=0; i<this.points.size()-1; i+=2 ) {
	        canvas.drawPoint( 
	        		          this.points.get(i), 
	        		          this.points.get(i+1), 
	        		          paint 
	                        );
        }
        for ( int i=0; i<this.lines.size()-3; i+=4 ) {
	        canvas.drawLine( 
  		             		  this.lines.get(i), 
  		          			  this.lines.get(i+1), 
		            		  this.lines.get(i+2), 
		                      this.lines.get(i+3),  
	        		          paint 
	                        );
        }
    }
    
    /**
     * Clears the plotter queue.
     */
    public void flush() {
    	this.points.clear();
    	this.lines.clear();
    }

    
    /**
     * Delete the last point from the queue and return it.
     */
    public float[] popPoint() {
    	int s = this.points.size();
    	float[] p = new float[2];
    	p[0] = this.points.get( s-2 );
    	p[1] = this.points.get( s-1 );
    	this.points.remove( s-1 );
    	this.points.remove( s-2 );
    	return p;
    }

    
    /**
     * Delete the last line from the queue and return it.
     */
    public float[] popLine() {
    	int s = this.lines.size();
    	float[] l = new float[4];
    	l[0] = this.lines.get( s-4 );
    	l[1] = this.lines.get( s-3 );
    	l[3] = this.lines.get( s-2 );
    	l[4] = this.lines.get( s-1 );
    	this.lines.remove( s-1 );
    	this.lines.remove( s-2 );
    	this.lines.remove( s-3 );
    	this.lines.remove( s-4 );
    	return l;
    }
    
    /**
     * Avoids fake measures.
     */
    @Override
    protected void onMeasure( int widthSpec, int heightSpec ) {
	    int measuredWidth = MeasureSpec.getSize( widthSpec );	
	    int measuredHeight = MeasureSpec.getSize( heightSpec );
	    setMeasuredDimension( measuredWidth, measuredHeight );   
    }
    
    /**
     * Setter for the Paint object to use.
     */
    public void setPaint( Paint p ) {
    	this.paint = p;	
    }
    
    /**
     * Getter for the Paint object in use.
     */
    public Paint getPaint() {
    	return this.paint;	
    }

    public float getMinX() { return this.minx; }
    public float getMinY() { return this.miny; }
    public float getMaxX() { return this.maxx; }
    public float getMaxY() { return this.maxy; }
    
}