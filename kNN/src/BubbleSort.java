import java.util.ArrayList;


public class BubbleSort {
	
	private ArrayList<Datum> sortedThis;
	
	public BubbleSort(ArrayList<Double> sortUsing, ArrayList<Datum> sortThis) {
		this.sortedThis = sortThis;
		while (true) {
			boolean swapped = false;
			for (int i=1; i<sortThis.size(); i++) {
				if (sortUsing.get(i-1) > sortUsing.get(i)) {
					
					double temp1 = sortUsing.get(i-1);
					sortUsing.set(i-1, sortUsing.get(i));
					sortUsing.set(i, temp1);					
					
					Datum temp2 = sortedThis.get(i-1);
					sortedThis.set(i-1, sortedThis.get(i));
					sortedThis.set(i, temp2);
					
					swapped = true;
				}
			}
			if (swapped == false) break;
		}
	}
	
	public ArrayList<Datum> getSortedArray() {
		return this.sortedThis;
	}
	
}