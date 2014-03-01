import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class naiveBayes {
	
	/* constants */
	private static final String TRAINING_FILENAME = "training.txt";
	private static final String TESTING_FILENAME = "testing.txt";
	private static final int NUM_VARIABLES = 4;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<StoreCharacteristics> trainingSet = readFile(TRAINING_FILENAME);
		List<StoreCharacteristics> testingSet = readFile(TESTING_FILENAME);
		int numDataPoints = testingSet.size();
		double[][] typeAProb = getProbabilities(numDataPoints, trainingSet, '1');
		double[][] typeBProb = getProbabilities(numDataPoints, trainingSet, '0');
		//printArray(trainingSet);
		printProbs(typeAProb, numDataPoints);
	}
	
	private static void printProbs(double[][] probs, int numDataPoints) {
		for (int i=0; i<numDataPoints; i++) {
			for (int j=0; j<NUM_VARIABLES; j++) {
				System.out.print(probs[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	private static double[][] getProbabilities(int numDataPoints, 
			List<StoreCharacteristics> trainingSet, char type) {
		double[][] probs = new double[numDataPoints][NUM_VARIABLES];
		
		for (int var=0; var<NUM_VARIABLES; var++) {
			int[][] counts = new int[NUM_VARIABLES][2];
			for (int i=0; i<numDataPoints; i++) {
				StoreCharacteristics s = trainingSet.get(i);
				if (s.getCharacteristics().charAt(var) == '0') counts[var][0]++;
				if (s.getCharacteristics().charAt(var) == '1') counts[var][1]++;
			}
			probs[var][0] = (double) counts[var][0]/numDataPoints;
			probs[var][1] = (double) counts[var][1]/numDataPoints;
		}

		return probs;
	}
	
	private static void printArray(List<StoreCharacteristics> list) {
		for (int i=0; i<list.size(); i++) {
			StoreCharacteristics s = list.get(i);
			System.out.println(s.toString());
		}
	}
	
	private static List<StoreCharacteristics> readFile(String filename) {
		List<StoreCharacteristics> data = new ArrayList<StoreCharacteristics>();
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(filename));
			String line = "";
			while ((line = br.readLine()) != null) {
				//System.out.println("line: "+line+", length:"+line.length());
				data.add(new StoreCharacteristics(line));
			}
			br.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
}
