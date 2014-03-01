
public class HelloWorld {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		lesson1();
		lesson2();
		lesson3();
	}
	
	private static void lesson1() {
		System.out.println("Hello, World!");
	}
	
	private static void lesson2() {
		//integers
		int var1 = 2;
		int var2 = 5;
		int add = var1+var2;
		int subtract = var1-var2;
		int multiply = var1*var2;
		int divide = var2/var1;
		System.out.println("INTEGERS: add: "+add 
				+", subtract: "+subtract
				+", multiply: "+multiply
				+", divide:"+divide);
		
		//float types
		float varf1 = 2;
		float varf2 = 5;
		float addf = var1+var2;
		float subtractf = var1-var2;
		float multiplyf = var1*var2;
		float dividef = var2/var1;
		System.out.println("FLOATS: add: "+addf
				+", subtract: "+subtractf
				+", multiply: "+multiplyf
				+", divide:"+dividef);
		
		//creating
		int var3 = 2;
		for (int i=0; i<10; i++) {
			var3 *= 2;
			System.out.println(var3);
		}
	}
	
	private static void lesson3() {
		
	}

}
