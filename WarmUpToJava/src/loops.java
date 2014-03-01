
public class loops {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		whileLoop(5);
		forLoop(5);
		sumAndAverage();
		Fibonacci();
	}
	
	public static void whileLoop(int end) {
		int i=0;
		while (i < end) {
			i++;
			System.out.println("While loop iteration number: "+i);
		}
	}
	
	public static void forLoop(int end) {
		for (int i=0; i<end; i++) {
			System.out.println("For loop iteration number: "+i);
		}
	}
	
	
	public static void sumAndAverage() {
		int sum = 0; 
	    double average;
	    int lb = 1;
	    int ub = 100;

	    for (int number = lb; number <= ub; ++number) { 
	    	sum += number;
	    }
	    average = sum/(ub-lb+1);

	    System.out.println("Sum: "+sum+", Average: "+average);
	}
	
	public static void Fibonacci() {
		int n = 3;          // the index n for F(n), starting from n=3
	    int fn;             // F(n) to be computed
	    int fnMinus1 = 1;   // F(n-1), init to F(2)
	    int fnMinus2 = 1;   // F(n-2), init to F(1)
	    int nMax = 20;      // maximum n, inclusive
	    int sum = fnMinus1 + fnMinus2;
	    double average;
	 
	    System.out.println("The first " + nMax + " Fibonacci numbers are:");
	 
	    while (n <= nMax) {
	    	// Compute F(n), print it and add to sum
	    	fn = fnMinus1+fnMinus2;
	    	sum += fn;
	    	System.out.println("F(n): "+fn);
	    	// Adjust the index n and shift the number
	    	n++;
	    	fnMinus1 = fnMinus2;
	    	fnMinus2 = fn;
	    }
	    
	    System.out.println("Fibonacci average: "+sum/(nMax-3+1));
	}


}
