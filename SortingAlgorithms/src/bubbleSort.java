import java.util.ArrayList;

public class bubbleSort {
	
	private ArrayList<Integer> sorted;
	
	public bubbleSort(ArrayList<Integer> sortThis) {
		sorted = sortThis;
		while (true) {
			boolean madeSwap = false;
			for (int i=0; i<sorted.size()-1; i++) {
				if (sortThis.get(i) > sorted.get(i+1)) {
					int temp1 = sorted.get(i);
					int temp2 = sorted.get(i+1);
					sorted.set(i, temp2);
					sorted.set(i+1, temp1);
					madeSwap = true;
				}
			}
			if (madeSwap == false) break;
		}
	}
	
	public ArrayList<Integer> getSortedArray() {
		return sorted;
	}
	
}