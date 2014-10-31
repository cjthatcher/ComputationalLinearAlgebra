package edu.usu.math.cla.root;

import java.util.LinkedList;
import java.util.List;

import edu.usu.math.cla.Function;

public class Driver {

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
		List<Double> resultList = new LinkedList<Double>();
		
		for (int i = 0; i < 10; i++)
		{
			int cores = (int) Math.pow(2,i);
			System.out.println(cores);
			long start = System.currentTimeMillis();
			resultList = ParallelRootFinder.findRoots(cores, f, -10, 10, 10000000, 0.0000000000005);
			long dur = System.currentTimeMillis() - start;
			
//			for (double d : resultList)
//			{
//				System.out.println(d);
//			}
			
			System.out.println("Parallel took: " + dur);
			
			start = System.currentTimeMillis();
			resultList = SerialRootFinder.findRoots(f, -10, 10, 10000000, 0.0000000000005);
			dur = System.currentTimeMillis() - start;
			
//			for (double d : resultList)
//			{
//				System.out.println(d);
//			}
			
			System.out.println("Serial took: " + dur);
		}
		
	}
}
