import edu.usu.math.cla.Function;


public class Driver {
	
	private static Function f = new Function() {public double evaluate(double d) {return Math.sin(d) + d / 2;}};
	private static Function fPrime = new Function() {public double evaluate(double d) {return Math.cos(d) + 0.5;}};
	private static Function fDoublePrime = new Function() {public double evaluate(double d) {return -1 * Math.sin(d);}};
	
	public static void main(String[] args) throws InterruptedException
	{
		
		System.out.println(SerialFunctionMinimizer.getMinimumValue(f, fPrime, fDoublePrime, -10, 10));
		System.out.println(ParallelFunctionMinimizer.getMinimumValue(f, fPrime, fDoublePrime, -10, 10));
		
	}

}
