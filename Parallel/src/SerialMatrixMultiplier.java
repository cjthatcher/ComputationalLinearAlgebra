import java.util.Arrays;


public class SerialMatrixMultiplier {
	
	public static double[][] matrixMultiply(double[][] A, double[][] B)
	{
		double[][] C = new double[A.length][B[0].length];
		
		for (int i = 0; i < A.length; i++)
		{
			for (int j = 0; j < B[0].length; j++)
			{
				for (int k = 0; k < B.length; k++)
				{
					C[i][j] += A[i][k] * B[k][j];
				}
			}
		}
		
		
		return C;
	}
	
	public static void main(String[] args)
	{
		double[][] A = new double[][]{{1d,2d,2d},{3d,3d,5d},{2d,2d,1d},{5d,6d,7d}};
		double[][] B = new double[][]{{3d,2d,2d,1d},{4d,3d,5d,2d},{2d,5d,1d,3d}};
		
		double[][] C = matrixMultiply(A, B);
		
		for (double[] d : C)
		{
			System.out.println(Arrays.toString(d));
		}
		
	}

}
