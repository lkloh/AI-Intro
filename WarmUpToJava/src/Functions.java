
public class Functions {
	
	private static int[] ARRAY = {2,5,3,7,8,3,4};

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int first = 5;
		int second = 10;
		int result = addInts(first, second);
		System.out.println(result);
		
		System.out.println(sumSeveralNumbers());
		
		printMessage();
	}
	
	private static void printMessage() {
		System.out.println("Hello, world!");
	}
	
	private static int sumSeveralNumbers() {
		int sum = 0;
		for (int i=0; i< ARRAY.length; i++) {
			sum += ARRAY[i];
		}
		return sum;
	}

	private static int addInts(int one, int two) {
		return one+two;
	}
	
}
