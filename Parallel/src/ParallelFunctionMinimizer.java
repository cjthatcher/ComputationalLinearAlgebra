import java.util.Comparator;
import java.util.List;

import edu.usu.math.cla.Function;
import edu.usu.math.cla.root.ParallelRootFinder;


public class ParallelFunctionMinimizer {

	/*
	 * This function finds the global minimum of a function f on a given interval. 
	 * It uses the secant method to find the roots of f', and then evalutes them at f'' to see if they are minima
	 * It then finds the smallest minimum.
	 */
	public static double getMinimumValue(Function f, Function fPrime, Function fDoublePrime, double startPoint, double endPoint) throws InterruptedException
	{
		List<Double> rootList = ParallelRootFinder.findRoots(64, fPrime, startPoint, endPoint, 1000, Math.pow(10, -8));
		
		for (Double d : rootList)
		{
			//System.out.println(d);
		}
		
		return rootList.stream()
						.filter(d -> fDoublePrime.evaluate(d) > 0)
						.map(d -> f.evaluate(d))
						.min(Comparator.naturalOrder())
						.orElse(Double.MAX_VALUE);
	}
}
