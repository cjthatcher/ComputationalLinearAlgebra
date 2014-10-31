package edu.usu.math.cla.root;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.usu.math.cla.Function;


public class RootFinderWorker extends Thread {
	
	private List<Double> resultList = new ArrayList<Double>();
	private Function f;
	private double startPoint;
	private double endPoint;
	private int numberOfProbes;
	private double tolerance;
	
	
	/*
	 * Constructor to initialize the worker thread with all the arguments necessary to run its function
	 */
	public RootFinderWorker(Function f, double startPoint, double endPoint, int numberOfProbes, double tolerance)
	{
		this.f = f;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.numberOfProbes = numberOfProbes;
		this.tolerance = tolerance;
	}
	
	/*
	 * This method is identical to the serial root finder (hybrid method)
	 * It finds all the roots on a range of a function, and stores them in a list.
	 * Instead of returning this list it stores it in its member variable resultList
	 *    so it can be retrieved later
	 */
	@Override
	public void run()
	{
		double distance = Math.abs((endPoint - startPoint) / numberOfProbes);
		double[] evaluationPoints = getEvaluationPoints(startPoint, distance, numberOfProbes);
		
		List<Double> startingPointsToCheck = findRangesWhereSignsChange(f, evaluationPoints, numberOfProbes);
		
		/*
		 * For each interval where the sign changed, used the Secant method to find the root.
		 * If secant method fails, do Bisection three times on the interval and try again
		 */
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
	
	public List<Double> getResultList()
	{
		return resultList;
	}

}
