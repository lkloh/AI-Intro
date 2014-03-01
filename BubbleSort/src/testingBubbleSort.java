import java.util.ArrayList;


public class testingBubbleSort {
	
	private static int LENGTH = 20;
	
	public static void main(String args[]) {
		ArrayList<Integer> list = populateArray();
		System.out.println("Before sorting");
		System.out.println(list);
		
		BubbleSort bs = new BubbleSort(list);
		ArrayList<Integer> sortedList = bs.getSortedArray();
		
		System.out.println("sorted list");
		System.out.println(sortedList);
	}
	
	private static ArrayList<Integer> populateArray() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<LENGTH; i++) {
			list.add(LENGTH-i);
		}
		return list;
	}
	
}