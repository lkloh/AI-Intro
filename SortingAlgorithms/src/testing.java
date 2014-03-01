import java.util.ArrayList;
import java.util.Random;

public class testing {
	
	private static int NUM_ELEMENTS = 10;
	
	static Random rand = new Random();
	
	public static void main(String[] args) {
		ArrayList<Integer> unsortedList = randomListOfIntegers();
		System.out.println("List before: "+unsortedList);
		
		bubbleSort bs = new bubbleSort(unsortedList);
		ArrayList<Integer> sortedListbs = bs.getSortedArray();
		System.out.println("Bubble Sort, List after: "+ sortedListbs);
		
		insertionSort is = new insertionSort(unsortedList);
		ArrayList<Integer> sortedListIS = is.getSortedArray();
		System.out.println("Insertion Sort, List after: "+ sortedListIS);
		
		selectionSort ss = new selectionSort(unsortedList);
		ArrayList<Integer> sortedListSS = ss.getSortedArray();
		System.out.println("Selection Sort, List after: "+ sortedListSS);
	}
	
	private static ArrayList<Integer> randomListOfIntegers() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<NUM_ELEMENTS; i++) {
			list.add(rand.nextInt(100));
		}
		return list;
	}
	
}