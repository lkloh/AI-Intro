import java.util.ArrayList;

public class selectionSort {
	
	private ArrayList<Integer> sortedList;
	
	public selectionSort(ArrayList<Integer> unsortedList) {
		sortedList = unsortedList;
		for (int i=0; i<unsortedList.size()-1; i++) {
			//int currentNumber = sortedList.get(i);
			int smallestNumber = Integer.MAX_VALUE;
			for (int j=i; j<unsortedList.size(); j++) {
				if (sortedList.get(j) < smallestNumber) {
					int saved = sortedList.get(j);
					smallestNumber = sortedList.get(j);
					sortedList.set(i, smallestNumber);
					sortedList.set(j, saved);
				}
			}
		}
	}
	
	public ArrayList<Integer> getSortedArray() {
		return this.sortedList;
	}
	
}