package edu.usu.math.cla.root;
import java.util.ArrayList;
import java.util.List;

import edu.usu.math.cla.Function;

public class ParallelRootFinder {
	
	/*
	 * Overarching plan: 
	 * 		Split the range you're given into n subsets
	 * 		Create n worker threads --> Each worker solves its subset of the problem serially
	 * 		Start all n worker threads working in parallel
	 * 		Gather the results from each thread
	 * 		Return results
	 */
	
	/*
	 * This Method takes the number of threads you want to spawn, n
	 * It then divides the range of values to check into smaller chunks (one for each thread)
	 * It then creates n worker threads that check their range for roots
	 * It then retrieves the list of results from each worker thread, puts it into one big list, and returns.
	 */
	public static List<Double> findRoots(int numCores, Function f, double startPoint, double endPoint, int numberOfProbes, double tolerance) throws InterruptedException
	{	
		double range = (endPoint - startPoint) / numCores;
		int myNumProbes = (int) Math.round(numberOfProbes / (double) numCores);
		
		RootFinderWorker[] rfwArray = new RootFinderWorker[numCores];
		
		/*
		 * Create n worker threads, and initialize each with their subset of the range
		 * Start each thread
		 */
		for (int i = 0; i < numCores; i++)
		{
			rfwArray[i] = new RootFinderWorker(f, startPoint + (range * i), startPoint + (range * (i+1)), myNumProbes, tolerance);
			rfwArray[i].start();
		}
		
		/*
		 * Wait for each thread to complete
		 */
		for (int i = 0; i < numCores; i++)
		{
			rfwArray[i].join();
		}
		
		List<Double> resultList = new ArrayList<Double>();
		
		/*
		 * Retrieve the results from each thread, and put them in a big list
		 */
		for (int i = 0; i < numCores; i++)
		{
			resultList.addAll(rfwArray[i].getResultList());
		}
		
		return resultList;
	
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		Function f = new Function() {

			@Override
			public double evaluate(double x) {
				if (Double.compare(x, 0) != 0)
				{
					return Math.sin(x) / x;
				}
				else
				{
					return 1;
				}
			}
			
		};
		long start = System.currentTimeMillis();
		
		List<Double> resultList = findRoots(32, f, -10, 10, 100000000, 0.0000000000005);
		
		long dur = System.currentTimeMillis() - start;
		
		for (double d : resultList)
		{
			System.out.println(d);
		}
		
		System.out.println(dur);
		
	}

}
