package com.cuuuurzel.alcohohol;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represent a single drink.
 */
public class Drink implements Parcelable {

	/**
	 * When the drink has been consumed.
	 */
	private float t;

	/**
	 * Alcohol percentage in the drink;
	 */
	private float p;
	
	/**
	 * Liquid quantity.
	 */
	private float q;
	
	/** Parameter of the metabolism curve. */
	protected static final float t1s = 0.05f;
	/** Parameter of the metabolism curve. */
	protected static final float t2s = 0.1f;	
	/** Parameter of the metabolism curve. */
	protected static final float t3s = 0.4f;

	/**
	 * Creates the object.
	 * @param float aq, as alcohol quantity in grams.
	 * @param float t, as the time, in minutes, at the drink consumption.
	 */
	public Drink( float p, float q, float t ) {
		this.p = p;
		this.q = q;
		this.t = t;
	}
	
	/**
	 * Needed to implements Parcelable.
	 */
	public Drink( Parcel p ) {
		this( p.readFloat(), p.readFloat(), p.readFloat() );
	}
	
	/**
	 * Asks the object for the amount of alcohol remaining.
	 * @param time, as the number of minutes passed from the drink.
	 * @param absSpeed, as the alcohol absorption rate.
	 * @param r, as the susceptibility of the user.
	 */
	//TODO: UPDATE!!!
	public float alcohol( float time, float absSpeed, float r ) {
		return this.getAq() * this.alcoholP( time, absSpeed, r );
	}
	
	/**
	 * Asks the object for the percentage of alcohol remaining.
	 * @param time, as the number of minutes passed from the drink.
	 * @param absSpeed, as the alcohol absorption rate.
	 * @param r, as the susceptibility of the user.
	 */
	//TODO: UPDATE!!!
	public float alcoholP( float time, float absSpeed, float r ) {
		//Susceptibility to alcohol change in base of different alcohol percentage. 
		float d = 0.00421f*this.p + 0.57895f;		
		return 100f * this.alcoholSTD( (time-this.t)/600f ) / (r+d);
	}
	
	/**
	 * Defines the standard metabolism curve.
	 * @param x, some time measure, between 0 and 1.
	 */
	private float alcoholSTD( float x ) {
		float y = 0;
		if ( x > 0 ) {
			if ( x < t1s ) {
				y = 20*x;
			} else {
				if ( x < t2s ) {
					y = -10*x + 1.5f;
				} else {
					if ( x < t3s ) {
						y = -4*x/3 + 0.63f;
					} else {
						if ( x < 1 ) {
							y = -x*0.17f + 0.1666f;
						}
					}
				}
			}
		}
		return y;
	}
	
	/**
	 * Returns the total alcohol amount in grams.
	 */
	public float getAq() { 
		return this.p * this.q; 
	}

	/**
	 * Time in minutes, when the drink has been consumed.
	 */
	public float getTime() {
		return t;
	}
	
	/**
	 * Returns a string containing information about the object.
	 */
	public String toString() {
		return "q: " + this.getAq() + ", t: " + this.t; 
	}

    @Override
    public void writeToParcel( Parcel out, int flags ) {
        out.writeFloat( this.p );
        out.writeFloat( this.q );
        out.writeFloat( this.t );
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {
        public Drink createFromParcel(Parcel in) {
            return new Drink( in );
        }

        public Drink[] newArray( int size ) {
            return new Drink[size];
        }
    };

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}