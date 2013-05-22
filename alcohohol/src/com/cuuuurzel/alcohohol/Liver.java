package com.cuuuurzel.alcohohol;

import java.util.ArrayList;

/**
 * Manages an array of drinks.
 */
public class Liver {

	/**
	 * Average age of an user.
	 */
	public static final float AVG_AGE = 30f;
	
	/**
	 * Average age of an user.
	 */
	public static final float AVG_WEIGHT = 72.5f;
	
	/**
	 * Store for the drinks.
	 */
	protected ArrayList<Drink> drinks = new ArrayList<Drink>(); 

	/**
	 * The user's alcohol absorption rate.
	 */
	protected float absSpeed;	 
	
	/**
	 * The user's susceptibility to alcohol.
	 */
	protected float r;	 

	/**
	 * Creates the liver object with the standard configuration.
	 */
	public Liver() {
		this( AVG_WEIGHT, false, 0.5f, AVG_AGE );
	}
	
	/**
	 * Creates the liver object with the give configuration.
	 * @param weight, the weight of the user.
	 * @param isFemale, 'true' if the user is a woman.
	 * @param satiety, the 'satiety index' of the user, between 0 and 1.
	 * @param age, the age, in month, of the user.
	 */
	public Liver( float weight, boolean isFemale, float satiety, float age ) {
		float dr = 1.0f;
		//Sex influence on the alcohol susceptibility.
		if ( !isFemale ) { dr += 0.1f; }
		//Satiety influence on the alcohol susceptibility.
		dr = dr /( -2*satiety/5 + 1);
		//Weight influcence on the alcohol susceptibility.
		dr = dr * ( 3 * (weight/AVG_WEIGHT) - 2 );
		//Age influence on the alcohol susceptibility.
		//BOOOH!
		this.r = dr;
		
		float da = 1;
		//Sex influence on the alcohol absorption rate.
		if ( isFemale ) { da += 0.2f; }
		//Satiety influence on the alcohol absorption rate.
		//BOOOH!
		//Weight influcence on the alcohol absorption rate.
		//BOOOH!
		//Age influence on the alcohol absorption rate.
		//BOOOH!
		this.absSpeed = da;	
	}
	
	/**
	 * Adds a drink to the drinks list.
	 */
	public void drink( Drink d ) {
		this.drinks.add( d );		
	}
	
	/**
	 * Adds a list of drinks to the list.
	 */
	public void drink( ArrayList<Drink> d ) {
		this.drinks.addAll( d );		
	}
	
	/**
	 * Returns the total amount of alcohol given a time in minutes.
	 */
	public float alcohol( float time ) {	
		float alcohol = 0;
		Drink currentDrink;
		for ( int i=0; i<this.drinks.size(); i++ ) {
			currentDrink = this.drinks.get(i);
			alcohol += currentDrink.alcohol( time, absSpeed, r );
		}
		//g --> g/ml
		return alcohol/50;
	}
	
	/**
	 * Returns the total amount of alcohol given a time in minutes.
	 */
	public float alcoholP( float time ) {	
		float alcohol = 0;
		Drink currentDrink;
		for ( int i=0; i<this.drinks.size(); i++ ) {
			currentDrink = this.drinks.get(i);
			alcohol += currentDrink.alcoholP( time, absSpeed, r );
		}
		return alcohol/this.drinks.size();
	}
	
	/**
	 * Return the total amount of alcohol in the liver.
	 */
	public float getAq() {
		float aq = 0;
		for ( int i=0; i<this.drinks.size(); i++ ) {
			aq += this.drinks.get(i).getAq();
		}		
		return aq;
	}
}