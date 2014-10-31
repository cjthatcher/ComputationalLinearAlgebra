package edu.usu.math.cla.matrix;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import org.junit.Test;


public class MatrixMultiplierTest {

	@Test
	public void test() {
		double[][] A = new double[][]{{1d,2d,2d},{3d,3d,5d},{2d,2d,1d},{5d,6d,7d}};
		double[][] B = new double[][]{{3d,2d,2d,1d},{4d,3d,5d,2d},{2d,5d,1d,3d}};
		
		double[][] C = SerialMatrixMultiplier.matrixMultiply(A, B);
		
		assertArrayEquals(C[0], new double[]{15.0, 18.0, 14.0, 11.0}, .00005);
		assertArrayEquals(C[1], new double[]{31.0, 40.0, 26.0, 24.0}, .00005);
		assertArrayEquals(C[2], new double[]{16.0, 15.0, 15.0, 9.0}, .00005);
		assertArrayEquals(C[3], new double[]{53.0, 63.0, 47.0, 38.0}, .00005);
	}

	@Test
	public void testParallel() throws InterruptedException, ExecutionException {
		double[][] A = new double[][]{{1d,2d,2d},{3d,3d,5d},{2d,2d,1d},{5d,6d,7d}};
		double[][] B = new double[][]{{3d,2d,2d,1d},{4d,3d,5d,2d},{2d,5d,1d,3d}};
		
		double[][] C = new ParallelMatrixMultiplier().matrixMultiply(A, B);
		
		assertArrayEquals(C[0], new double[]{15.0, 18.0, 14.0, 11.0}, .00005);
		assertArrayEquals(C[1], new double[]{31.0, 40.0, 26.0, 24.0}, .00005);
		assertArrayEquals(C[2], new double[]{16.0, 15.0, 15.0, 9.0}, .00005);
		assertArrayEquals(C[3], new double[]{53.0, 63.0, 47.0, 38.0}, .00005);
	}
	
	@Test
	public void testAwesomeness() throws InterruptedException, ExecutionException
	{
		
		for (int i = 0; i < 100; i++)
		{
			ParallelMatrixMultiplier pmm = new ParallelMatrixMultiplier();
			double[][] A = makeBogusMatrix((int) Math.pow(2, i));
			double[][] B = makeBogusMatrix((int) Math.pow(2, i));
			
			long serialStart = System.currentTimeMillis();
			double[][] c = SerialMatrixMultiplier.matrixMultiply(A, B);
			long serialDur = System.currentTimeMillis() - serialStart;
			
			long parallelStart = System.currentTimeMillis();
			double[][] d = pmm.matrixMultiply(A, B);
			long parallelDur = System.currentTimeMillis() - parallelStart;
			
//			for (int k = 0; k < Math.pow(2, i); k++)
//			{
//				assertArrayEquals(c[k],d[k], 0.0005);
//			}
			
			System.out.println(Math.pow(2,i) + "," + serialDur + "," + parallelDur);
		}
		
	}
	
	public double[][] makeBogusMatrix(int dimension)
	{
		double[][] C = new double[dimension][dimension];
		for (int i = 0; i < dimension; i++)
		{
			for (int j = 0; j < dimension; j++)
			{
				C[i][j] = Math.random();
			}
		}
		return C;
	}
	
	//To Freakin' Do
	
	//1. Clean up Root Finder Code for both sides
	//2. Clean up Minimizer for both sides
	//3. Comment Matrix Code
	
	//4. Run tests on Root Finder --> Write Results
	//5. Run tests on Minimizer --> Write Results
	//6. Run tests on Multiplication --> Write Results
	
	//7. Go to Bread.
}
