import java.util.ArrayList;

public class QuickSortTest {
	
	private static int LENGTH = 10;
	
	public static void main(String args[]) {
		ArrayList<Integer> list = populateArray();
		System.out.println("Before sorting");
		System.out.println(list);
		
		QuickSort qs = new QuickSort(list);
		ArrayList<Integer> sortedList = qs.getSortedArray();
		
		System.out.println("sorted list");
		System.out.println(sortedList);
	}
	
	private static ArrayList<Integer> populateArray() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<LENGTH; i++) {
			list.add(LENGTH-i-1);
		}
		return list;
	}
	
}