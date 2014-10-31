

import java.util.Comparator;
import java.util.List;

import edu.usu.math.cla.Function;
import edu.usu.math.cla.root.SerialRootFinder;

public class SerialFunctionMinimizer {
	
	/*
	 * This function finds the global minimum of a function f on a given interval. 
	 * It uses the secant method to find the roots of f', and then evalutes them at f'' to see if they are minima
	 * It then finds the smallest minimum.
	 */
	public static double getMinimumValue(Function f, Function fPrime, Function fDoublePrime, double startPoint, double endPoint)
	{
		List<Double> rootList = SerialRootFinder.findRoots(fPrime, startPoint, endPoint, 1000, Math.pow(10, -8));
		
		return rootList.stream()
				.filter(d -> fDoublePrime.evaluate(d) > 0)  //Find roots with f''(x) > 0;
				.map(d -> f.evaluate(d)) // Create a list of all function values at roots
				.min(Comparator.naturalOrder()) // Take the min
				.orElse(Double.MAX_VALUE); // If the min doesn't exist, return Double.max
	}
	
	
	public static void testOne()
	{
		Function f = new Function() {

			@Override
			public double evaluate(double x) {
				return Math.sin(x) + (x / 2);
			}
		};
		
		Function fPrime = new Function() {

			@Override
			public double evaluate(double x) {
				return Math.cos(x) + 0.5;
			}
			
		};
		
		Function fDoublePrime = new Function() {

			@Override
			public double evaluate(double x) {
				return -1 * Math.sin(x);
			}
			
		};
		
		getMinimumValue(f, fPrime, fDoublePrime, -10, 10);
	}
	public static void main(String[] args)
	{

		
		Function f = new Function() {

			@Override
			public double evaluate(double x) {
				return Math.sin(x) + (x / 2);
			}
		};
		
		Function fPrime = new Function() {

			@Override
			public double evaluate(double x) {
				return Math.cos(x) + 0.5;
			}
			
		};
		
		Function fDoublePrime = new Function() {

			@Override
			public double evaluate(double x) {
				return -1 * Math.sin(x);
			}
			
		};
		
		getMinimumValue(f, fPrime, fDoublePrime, -10, 10);
	}

}
