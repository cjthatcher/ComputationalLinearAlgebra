package edu.usu.math.cla.root;
import java.util.LinkedList;
import java.util.List;

import edu.usu.math.cla.Function;

/*
 * This class uses a hybrid secant-bisection method to find the roots of a 
 * function on a given interval. It does so in serial.
 */
public class SerialRootFinder {

	/*
	 * This function uses the secant method to find the roots of a given function
	 */
	public static List<Double> findRoots(Function f, double startPoint, double endPoint, int numberOfProbes, double tolerance)
	{
		double distance = Math.abs((endPoint - startPoint) / numberOfProbes);
		double[] evaluationPoints = getEvaluationPoints(startPoint, distance, numberOfProbes);
		
		List<Double> startingPointsToCheck = findRangesWhereSignsChange(f, evaluationPoints, numberOfProbes);
		
		/*
		 * For each interval where the sign changed, used the Secant method to find the root.
		 * If secant method fails, do Bisection three times on the interval and try again
		 */
		List<Double> resultList = new LinkedList<Double>();
		for (int i = 0; i < startingPointsToCheck.size(); i++)
		{
			double start = startingPointsToCheck.get(i);
			double end = start + distance;
			
			double currentLocation = (start + end) / 2;
			double oldLocation = currentLocation + 0.0005;
			
			double currentValue = f.evaluate(currentLocation);
			double nextLocation = getNextSecantLocation(f, currentLocation, oldLocation);
			double nextValue = f.evaluate(nextLocation);
			
			boolean wasFound = false;
			
			//Keep doing secant until we find a root or stall out
			while (Math.abs(nextValue) < 0.5 * Math.abs(currentValue))
			{
				if (Math.abs(nextValue) < tolerance) //We found the root. w00t!
				{
					resultList.add(nextLocation);
					wasFound = true;
					break;
				}
				
				oldLocation = currentLocation;
				currentLocation = nextLocation;
				nextLocation = getNextSecantLocation(f, currentLocation, oldLocation);
				nextValue = f.evaluate(nextLocation);
			}
			
			//If we didn't find it, do bisection three times,
			//Then add the new location to the list and try again
			if (wasFound == false)
			{
				double tempStart = start;
				double tempEnd = end;
				double mid = (tempStart + tempEnd) / 2;
				for (int j = 0; j < 3; j++)
				{
					double startVal = f.evaluate(tempStart);
					double endVal = f.evaluate(tempEnd);
					double midVal = f.evaluate(mid);
					
					if (startVal * midVal <= 0)
					{
						tempEnd = mid;
						continue;
					}
					else if (midVal * endVal <= 0)
					{
						tempStart = mid;
						continue;
					}
				}
				
				startingPointsToCheck.add(tempStart);
			}
		}
		
		return resultList;
	}
	
	/*
	 * This function probes a function over an interval to find out where the signs change.
	 * It returns a list of the starting points of all the intervals where the sign changes.
	 */
	private static List<Double> findRangesWhereSignsChange(Function f, double[] evaluationPoints, int numberOfProbes) {
		List<Double> startingPointsToCheck = new LinkedList<Double>();
		for (int i = 0; i < numberOfProbes - 1; i++)
		{
			double firstValue = f.evaluate(evaluationPoints[i]);
			double nextValue = f.evaluate(evaluationPoints[i + 1]);
			
			if (firstValue * nextValue < 0) {
				startingPointsToCheck.add(evaluationPoints[i]);
				continue;
			}
			if (firstValue * nextValue == 0){
				startingPointsToCheck.add(evaluationPoints[i]);
				i++;
				continue;
			}
		}
		return startingPointsToCheck;
	}

	/*
	 * Find all the points where we will probe the function value.
	 */
	private static double[] getEvaluationPoints(double startPoint, double distance, int numberOfProbes) {	
		double[] evaluationPoints = new double[numberOfProbes];
		for (int i =0; i < numberOfProbes; i++)
		{
			evaluationPoints[i] = startPoint + (i * distance);
		}
		return evaluationPoints;
	}
	
	/*
	 * This method uses the secant method to find x k + 1;
	 */
	public static double getNextSecantLocation(Function f, double origLocation, double previousLocation)
	{
		double origValue = f.evaluate(origLocation);
		double h = origLocation - previousLocation;
		double oldValue = f.evaluate(previousLocation);
		
		return origLocation - ((origValue * h) / (origValue - oldValue));
	}
}