import java.util.Scanner;


public class IfThenElse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testIf();
		testElse();
		nestedLogic();
		NumberGuesser();
	}
	
	public static void testIf() {
		int x = 10;

	    if (x < 20) {
	    	System.out.println("This is if statement");
	    }
	}
	
	public static void testElse() {
		int x = 30;

 		if (x < 20) {
     		System.out.println("This is if statement");
 		} else{
     		System.out.println("This is else statement");
 		}
	}
	
	public static void nestedLogic() {
		int x = 30;
	    int y = 10;
	    if (x == 30) {
	         if (y == 10) {
	        	 System.out.println("X = 30 and Y = 10");
	         }
		}
	}
	
	public static void NumberGuesser() {
		int secretNumber;
		secretNumber = (int) (Math.random() * 999 + 1);
		System.out.println("Secret number:  "+secretNumber);

		Scanner keyboard = new Scanner(System.in);
		int guess = -1;

		while (guess != secretNumber) {
			System.out.println("Enter a guess: ");
			guess = keyboard.nextInt();

			// print out the guess
			System.out.println("Your guess: "+guess);
			
			// check if guess is correct and print message
			if (guess == secretNumber) {
				System.out.println("Correct guess");
			} else {
				System.out.println("Wrong guess");
			}
			
			// if smaller, print smaller message
			if (guess > secretNumber)
				System.out.println("Larger than the answer.");

			// if larger, print larger message
			if (guess < secretNumber)
				System.out.println("Smaller than the answer.");
		}
	}

}
