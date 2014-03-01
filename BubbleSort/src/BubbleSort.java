import java.util.ArrayList;


public class BubbleSort {
	
	private ArrayList<Integer> sorted;
	
	public BubbleSort(ArrayList<Integer> sortThis) {
		this.sorted = sortThis;
		while (true) {
			boolean swapped = false;
			for (int i=1; i<sortThis.size(); i++) {
				if (sorted.get(i-1) > sorted.get(i)) {
					int temp = sorted.get(i-1);
					sorted.set(i-1, sorted.get(i));
					sorted.set(i, temp);
					swapped = true;
				}
			}
			if (swapped == false) break;
		}
	}
	
	public ArrayList<Integer> getSortedArray() {
		return this.sorted;
	}
	
}