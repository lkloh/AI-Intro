import java.util.ArrayList;

public class insertionSort {
	
	private ArrayList<Integer> sortedList;
	
	public insertionSort(ArrayList<Integer> unsortedList) {
		sortedList = unsortedList;
		for (int i=0; i<unsortedList.size()-1; i++) {
			for (int j=0; j<=i; j++) {
				int currentInt = sortedList.get(i);
				int nextInt = sortedList.get(i+1);
				if (currentInt > nextInt) {
					sortedList.set(i,nextInt);
					sortedList.set(i+1,currentInt);
				}
			}
		}
	}
	
	public ArrayList<Integer> getSortedArray() {
		return this.sortedList;
	}
	
}