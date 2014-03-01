import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author LK Loh
 *
 */
public class EPGYAuthor {
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                    CONSTANTS                                   *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	private static Map<String, ArrayList<Character>> mapText;
	private static int ORDER = 0;
	private static final int NUM_CHARACTERS_TO_GENERATE = 2000;
	
	private static Random random = new Random();
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                      BUILD MODEL                               *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	
	public static void main(String[] args) {
		mapText = new HashMap<String, ArrayList<Character>>();
		BufferedReader text = getText();
		getOrder();
		System.out.println("order: "+ORDER);
		String allText = getText(text);
		//System.out.println(allText);
		buildModel(allText);
		
		System.out.println(mapText);
		System.out.println("------------------");
		generateRandomText();
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                           GENERATE RANDOM TEXT                                 *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */

	private static String getSeed() {
		Set<String> allChunks = mapText.keySet();
		Iterator<String> iterator = allChunks.iterator();
		int highestFreq = 0;
		String highestFreqChunk = "";
		while (iterator.hasNext()) {
			String currentKey = iterator.next();
			ArrayList<Character> list = mapText.get(currentKey);
			if (list.size() > highestFreq) {
				highestFreqChunk = currentKey;
				highestFreq = list.size();
			}
		}
		return highestFreqChunk;
	}
	
	private static void generateRandomText() {
		String seed = getSeed();
		
		String randomText = seed;
		String currentSeed = seed;
		for (int i=0; i<NUM_CHARACTERS_TO_GENERATE-ORDER; i++) {
			if (mapText.containsKey(currentSeed)) {
				ArrayList<Character> list = mapText.get(currentSeed);
				if (list.size() > 0) {
					int index = random.nextInt(list.size());
					Character ch = list.get(index);
					randomText += ch;
					currentSeed = currentSeed.substring(1)+ch;
					//System.out.println(currentSeed);
					//System.out.println(currentSeed.length());
				}
			}
		}
		System.out.println(randomText);
	}
			
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                           GENERATE RANDOM TEXT                                 *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                      BUILD MODEL                               *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	private static void buildModel(String str) {
		for (int i=0; i<str.length()-ORDER; i++) {
			String chunk = "";
			for (int j=i; j<i+ORDER; j++) {
				chunk += str.charAt(j);
			}
			char charAfter = str.charAt(i+ORDER);
			insertToMap(chunk, charAfter);
			//System.out.println("HI "+charAfter);
		}
	}
	
	//stick that into the map
	private static void insertToMap(String chunk, char charAfter) {
		if (mapText.containsKey(chunk)) { //already in map
			ArrayList<Character> list = mapText.get(chunk);
			list.add(charAfter);
			mapText.put(chunk, list);
		} else { //not in map
			ArrayList<Character> list = new ArrayList<Character>();
			list.add(charAfter);
			mapText.put(chunk, list);
		}
	}

	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                      BUILD MODEL                               *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                             GET INPUT FROM USER                                *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	/**
	 * Using the text in the reader, and the order specified, build a Markov model
	 * and use it to randomly write text
	 * @param text
	 * @param order
	 */
	private static String getText(BufferedReader text) {
		String str = "";
		try {
			int c = 0;
			while ((c = text.read()) != -1) {
				str += (char)c;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * Prompt the user for the name of the text to use to build your model
	 * @return A scanner for reading the file.
	 */
	private static BufferedReader getText() {
		System.out.println("Enter the name of the file you want to read:");
		while (true) {
			try {
				Scanner scanner = new Scanner(System.in);
				return new BufferedReader(new FileReader(scanner.nextLine()));
			}
			catch (FileNotFoundException e) {
				System.out.println("Could not open file! Try again.");
			}
		}
	}
	

	/**
	 * Prompt the user to specify the order of the Markov model you want to use
	 * @return The order of the Markov model
	 */
	private static void getOrder() {
		System.out.println("Enter the order of the Markov model to build:");
		int order = 0;
		while (true) {
			try {
				Scanner scanner = new Scanner(System.in);
				order = scanner.nextInt();
				if (order > 0) {
					ORDER = order;
					return;
				}
				else System.out.println("Number must be greater than 0! Try again.");
			}
			catch (InputMismatchException e) {
				System.out.println("Could not parse input! Try again.");
			}
		}
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                             GET INPUT FROM USER                                *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */

}
