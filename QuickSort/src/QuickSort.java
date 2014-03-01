import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class QuickSort {
	
	ArrayList<Integer> sorted;
	
	Random rand = new Random();
	
	public QuickSort(ArrayList<Integer> sortThis) {
		 quicksort(sortThis, 0, sortThis.size()-1);
		 this.sorted = sortThis;
	}
	
	private void quicksort(ArrayList<Integer> sortThis, int start, int finish) {
		if (start < finish) {
			int pivotIndex = start + (finish-start)/2;
			
			int newPivotIndex = partition(sortThis, start, finish, pivotIndex);
			quicksort(sortThis, start, newPivotIndex-1);
			quicksort(sortThis, newPivotIndex+1, finish);
		}
	}
	
	private int partition(ArrayList<Integer> sortThis, int left, int right, int pivotIndex) {
		int pivot = sortThis.get(pivotIndex);
		exchange(sortThis, pivotIndex, right);
		int storeIndex = left;
		for (int i=left; i<right; i++) {
			if (sortThis.get(i) <= pivot) {
				exchange(sortThis, i, storeIndex);
				storeIndex++;
			}
		}
		exchange(sortThis, storeIndex, right);
		return storeIndex;
	}
	
	private void exchange(ArrayList<Integer> sortThis, int i, int j) {
		int temp1= sortThis.get(i);
		int temp2 = sortThis.get(j);
		sortThis.set(j, temp1);
		sortThis.set(i, temp2);
	}

	
	public ArrayList<Integer> getSortedArray() {
		return this.sorted;
	}
	
}