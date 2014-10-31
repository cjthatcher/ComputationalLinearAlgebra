package edu.usu.math.cla;

/*(
 * Abstract object wrapper for a formula. Allows me to create formulas
 * on the fly in my code without having to create a new method for them every time.
 * Allows my functions to depend on one common interface instead of having to change
 * each time I need to use a new formula. 
 */
public abstract class Function {
	
	/*
	 * Implement this method and then pass the Formula object to whatever function
	 * you want to call. 
	 * 
	 * Takes an array of doubles as arguments. Can take as many as you need.
	 */
	public abstract double evaluate(double d);

}
