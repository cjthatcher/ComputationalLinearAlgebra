package edu.usu.math.cla.matrix;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ParallelMatrixMultiplier {
	
	public double[][] matrixMultiply(double[][] A, double[][] B) throws InterruptedException, ExecutionException
	{
		ExecutorService e = Executors.newCachedThreadPool();
		List<Future> futureList = new ArrayList<Future>(A.length * B[0].length);
		
		double[][] C = new double[A.length][B[0].length];
		
		for (int i = 0; i < A.length; i++)
		{
			for (int j = 0; j < B[0].length; j++)
			{
				Worker w = new Worker(i, j, A, B, C);
				
				futureList.add(e.submit(w));
			}
		}
		
		for (Future f : futureList)
		{
			f.get();
		}
		
		return C;
	}
	
	class Worker implements Runnable {
		
		private int row;
		private int column;
		private double[][] A;
		private double[][] B;
		private double[][] C;
		
		public Worker(int row, int column, double[][] A, double[][] B, double[][] C)
		{
			this.row = row;
			this.column = column;
			this.A = A;
			this.B = B;
			this.C = C;
		}
		
		@Override
		public void run()
		{
			for (int i = 0; i < A[0].length; i++)
			{
				C[row][column] += A[row][i] * B[i][column];
			}
		}
		
		
	}
	
	//Gameplan:
	
	//Spawn a thread for each freaking unit in the new matrix.
	//So, probably has to be a runnable.

}
