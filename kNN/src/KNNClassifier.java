import java.io.*;
import java.util.*;


/**
 * This class implements (after you write some code) a k-Nearest Neighbors
 * classifier. I generated a synthetic data set for you to work with so that
 * you can evaluate your classifier.
 * 
 * @author Ben Holtz [but put your name here!]
 *
 */
public class KNNClassifier {
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                   CONSTANTS                                    *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */

	public static final int K = 3;
	
	// the commented out constants ("knn_train.txt") are for the larger data set
//	public static final String TRAINING_DATA = "knn_train.txt";
//	public static final String TESTING_DATA = "knn_test.txt";
	public static final String TRAINING_DATA = "knn_train_small.txt";
	public static final String TESTING_DATA = "knn_test_small.txt";
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                   CONSTANTS                                    *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	public static void main(String[] args) {
		ArrayList<Datum> trainingSet = getTrainingSet();
		ArrayList<Datum> testingSet = getTestingSet();
		classify(trainingSet, testingSet);
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                   CLASSIFICATION                               *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */

	/**
	 * This method is where the interesting part of your implementation happens. For each test datum, 
	 * you should find its k nearest neighbors in the training set, then let them "vote" on a label 
	 * for the datum. Make sure you report your results with a few print statements.
	 * 
	 * @param trainingSet The set of data you are allowed to use for testing
	 * @param testingSet The data you are trying to classify
	 */
	private static void classify(ArrayList<Datum> trainingSet, ArrayList<Datum> testingSet) {
		// TODO Classify each datum in the testing set and evaluate your results
		// I highly recommend the following paradigm
		//for(Datum datum : testingSet) {
		//	// some method classifyDatum(datum, trainingSet);
		//}
		//// some method evaluateResults
		ArrayList<Integer> setTrainingDataBelongsTo = new ArrayList<Integer>();
		
		for (int i=0; i<testingSet.size(); i++) {
			Datum datumTest = testingSet.get(i);
			ArrayList<Double> distances = new ArrayList<Double>();
			
			for (int j=0; j<trainingSet.size(); j++) {
				Datum datumTrain = trainingSet.get(j);
				double dist = datumTest.distanceTo(datumTrain);
				distances.add(dist);
			} 
			
			//copy over arrays to sort
			ArrayList<Double> distances2 = copyListDouble(distances);
			ArrayList<Datum> trainingSet2 = copyListDatum(trainingSet);
			BubbleSort bs = new BubbleSort(distances2, trainingSet2);
			ArrayList<Datum> sortedList = bs.getSortedArray();
			
			//System.out.println(sortedList);
			
			//get the first k and nearest neighbors and classify this point 
			int classifiedAs = classifyByKNeighbors(K, sortedList);
			setTrainingDataBelongsTo.add(classifiedAs);
			
		}
		
		System.out.println("results");
		System.out.println(setTrainingDataBelongsTo);
		
		double accuracy = getAccuracy(trainingSet, setTrainingDataBelongsTo);
		System.out.println("Accuracy: "+accuracy);
	}
	
	private static double getAccuracy(ArrayList<Datum> trainingSet, 
			ArrayList<Integer> setTrainingDataBelongsTo) {
		int numEqual = 0;
		for (int i=0; i<trainingSet.size(); i++) {
			Datum datum = trainingSet.get(i);
			if (datum.getLabel() == setTrainingDataBelongsTo.get(i)) numEqual++;
		}
		//System.out.println("equal: "+numEqual);
		return (double)numEqual/trainingSet.size();
	}
	
	private static int classifyByKNeighbors(int K, ArrayList<Datum> sortedList) {
		int zero = 0;
		int one = 0;
		for (int i=0; i<K; i++) {
			Datum datum = sortedList.get(i);
			if (datum.getLabel() == 0) zero++;
			if (datum.getLabel() == 1) one++;
		}
		if (zero > one) return 0;
		return 1;
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                   CLASSIFICATION                               *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                             COPYING ARRAYS                                     *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	private static ArrayList<Double> copyListDouble(ArrayList<Double> toCopy) {
		ArrayList<Double> list = new ArrayList<Double>();
		for (int i=0; i<toCopy.size(); i++) {
			list.add(toCopy.get(i));
		}
		return list;
	}
	
	private static ArrayList<Datum> copyListDatum(ArrayList<Datum> toCopy) {
		ArrayList<Datum> list = new ArrayList<Datum>();
		for (int i=0; i<toCopy.size(); i++) {
			list.add(toCopy.get(i));
		}
		return list; 
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                             COPYING ARRAYS                                     *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                           READ TRAINING/ TESTING FILES                         *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	// *** Utility code that you do not need to modify ***
	private static ArrayList<Datum> getTestingSet() {
		return readFile(TRAINING_DATA);
	}

	private static ArrayList<Datum> getTrainingSet() {
		return readFile(TESTING_DATA);
	}

	private static ArrayList<Datum> readFile(String filename) {
		ArrayList<Datum> data = new ArrayList<Datum>();
		try {
			Scanner scanner = new Scanner(new FileInputStream(filename)).useDelimiter("\n");
			while(scanner.hasNext()) {
				String str = scanner.next();
				//System.out.println(str);
				Datum datum = new Datum(str);
				data.add(datum);
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                           READ TRAINING/ TESTING FILES                         *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */

}
