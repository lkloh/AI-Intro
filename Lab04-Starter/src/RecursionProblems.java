import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;

import acm.program.GraphicsProgram;

/**
 * Starter code for the recursion problems.
 * 
 * @author Lekan Wang (lekan@lekanwang.com)
 *
 */
public class RecursionProblems extends GraphicsProgram {
	
	
	
	public static void main(String[] args) {
		new RecursionProblems().start(args);
	}


	/**
	 * Put any testing code you want in here.
	 */
	private void test() {
		// can sum to
		Set<Integer> myset = new HashSet<Integer>();
		myset.add(2);
		myset.add(3);
		myset.add(7);
		boolean val = canSumTo(myset, 10);
		System.out.println("Subset: "+val);
		//anagram
		findAnagram("hat");
		//issolvable
		int[] row = {3,6,4,1,3,4,2,5,3,0};
		int[] notSolvable = {3,6,};
		System.out.println("This puzzle is: "+isSolvable(row));
		
		System.out.println("==========================");
		System.out.println("Now Running findAnagram");
		System.out.println("Testing with requests {1,3,4,3,1} and stock length 4 (should require 3)");
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(3);
		list.add(4);
		list.add(3);
		list.add(1);
		System.out.println("Result: " + cutStock(list, 4));
		
		List<String> wordsToCheck = readToArray("InterestingWords.txt");
		for (int i=0; i<wordsToCheck.size(); i++) {
			highestScoringWord(wordsToCheck.get(i));
		}
	}
	
	/* 
	 * ****************************************************************
	 *                         MNEMONIC PLEASE                        *
	 * ****************************************************************
	 */
	
	/*
	 * Make the map
	 */
	private static HashMap<Character, HashSet<Character>> makeMap() {
		HashMap<Character, HashSet<Character>> map = new HashMap<Character, HashSet<Character>>();
		
		HashSet<Character> two = new HashSet<Character>();
		two.add('a');
		two.add('b');
		two.add('c');
		map.put('2', two);
		
		HashSet<Character> three = new HashSet<Character>();
		three.add('d');
		three.add('e');
		three.add('f');
		map.put('3',three);
		
		HashSet<Character> four = new HashSet<Character>();
		four.add('g');
		four.add('h');
		four.add('i');
		map.put('4', four);
		
		HashSet<Character> five = new HashSet<Character>();
		five.add('j');
		five.add('k');
		five.add('i');
		map.put('5', five);
		
		HashSet<Character> six = new HashSet<Character>();
		six.add('m');
		six.add('n');
		six.add('o');
		map.put('6', six);
		
		HashSet<Character> seven = new HashSet<Character>();
		seven.add('p');
		seven.add('q');
		seven.add('r');
		seven.add('s');
		map.put('7', seven);
		
		HashSet<Character> eight = new HashSet<Character>();
		eight.add('t');
		eight.add('u');
		eight.add('v');
		map.put('8',eight);
		
		HashSet<Character> nine = new HashSet<Character>();
		nine.add('w');
		nine.add('x');
		nine.add('y');
		nine.add('z');
		map.put('9', nine);

		return map;
	}
	
	private static void getStrings(HashMap<Character, 
			HashSet<Character>> mapKeyboard, String str, String possible) {
		if (str.length()==1) {
			HashSet<Character> set = mapKeyboard.get(str.charAt(0));
			Iterator<Character> iterator = set.iterator();
			while (iterator.hasNext()) {
				System.out.println(possible+iterator.next());
			}
		} else {
			System.out.println(possible);
			HashSet<Character> set = mapKeyboard.get(str.charAt(0));
			Iterator<Character> iterator = set.iterator();
			while (iterator.hasNext()) {
				getStrings(mapKeyboard, str.substring(1), possible+iterator.next());
			}
		}
	}

	/**
	 * Prints out all TouchTone phone mnemonics that can be generated
	 * given the string of the phone digits.
	 * 
	 * @param str
	 */
	public void mnemonicPlease(String str) {
		HashMap<Character, HashSet<Character>> mapKeyboard = makeMap();
		getStrings(mapKeyboard, str, "");
	}
	
	/* 
	 * ****************************************************************
	 *                         MNEMONIC PLEASE                        *
	 * ****************************************************************
	 */
	
	/* 
	 * ****************************************************************
	 *                           CAN SUM TO                           *
	 * ****************************************************************
	 */
	
	private static void getAllSubsets(Set<Integer> nums, 
			Set<Set<Integer>> allSubsets) {
		if (!nums.isEmpty()) {
			allSubsets.add(nums);
			Iterator<Integer> iterator = nums.iterator();
			Set<Integer> copy = new HashSet<Integer>();
			copy.addAll(nums);
			while (iterator.hasNext()) {
				int curNum = iterator.next();
				Set<Integer> local = new HashSet<Integer>();
				local.addAll(copy);
				local.remove(curNum);
				getAllSubsets(local, allSubsets);
			}
		}
	}

	/**
	 * Determines whether a subset of the numbers in the given set
	 * can sum up to the target.
	 * 
	 * @param nums
	 * @param target
	 * @return
	 */
	public boolean canSumTo(Set<Integer> nums, int target) {
		Set<Set<Integer>> allSubsets = new HashSet<Set<Integer>>();
		getAllSubsets(nums, allSubsets);
		System.out.println(allSubsets);
		
		Iterator<Set<Integer>> iterator1 = allSubsets.iterator();
		while (iterator1.hasNext()) {
			Set<Integer> currentSet = iterator1.next();
			Iterator<Integer> iterator2 = currentSet.iterator();
			int sum = 0;
			while (iterator2.hasNext()) {
				sum += iterator2.next();
			}
			if (sum == target) return true;
		}
		return false;
	}
	
	/* 
	 * ****************************************************************
	 *                           CAN SUM TO                           *
	 * ****************************************************************
	 */
	
	/* 
	 * ****************************************************************
	 *                         IS SOLVABLE                            *
	 * ****************************************************************
	 */
	
	private static boolean doWeRepeat(boolean[] isHit, int[] row, int index) {
		if (isHit[index] == true) return false;
		if (index < 0 || index > row.length-1) return false;
		if (row[index] == 0) {
			return true;
		} else {
			isHit[index] = true;
			if (index+row[index] < row.length) 
				return doWeRepeat(isHit, row, index+row[index]);
			else 
				return doWeRepeat(isHit, row, index-row[index]);
		}
	}

	/**
	 * Determines if the given representation of the puzzle presented in lab
	 * can be solved.
	 * 
	 * @param row
	 * @return
	 * 
	 * if we hit a same part twice, we are in a loop
	 */
	public boolean isSolvable(int[] row) {
		boolean[] isHit = new boolean[row.length];
		Arrays.fill(isHit, false);
		//
		return doWeRepeat(isHit, row, 0);
	}
	
	/* 
	 * ****************************************************************
	 *                         IS SOLVABLE                            *
	 * ****************************************************************
	 */
	

	/**
	 * Determines the highest-scoring Scrabble word given the letters.
	 * 
	 * @param letters
	 * @return
	 */
	public String highestScoringWord(String letters) {
		Set<String> lex = readToSet("ShorterLexicon.txt");
		Set<String> words = new HashSet<String>();
		ScrabbleWords(lex, letters, words);
		String highestWord = highestScoreWord(words);
		return "";
	}
	
	private int scoreOfWord(String str) {
		int score = 0;
		for (int i=0; i<str.length(); i++) {
			score += scoreCharacter(str.charAt(i));
		}
		return score;
	}
	
	private String highestScoreWord(Set<String> words) {
		Iterator<String> iterator = words.iterator();
		int highestScore = 0;
		String highestWord = "";
		for (; iterator.hasNext(); ) {
			String str = iterator.next();
			int score = scoreOfWord(str);
			if (score > highestScore) {
				highestScore = score;
				highestWord = str;
			}
		}
		return highestWord;
	}
	
	private void ScrabbleWords(Set<String> lex, String letters, 
			Set<String> words) {
		if (letters.length()==1) {
		} else {
			if (lex.contains(letters) && !words.contains(letters)) {
				System.out.println("hey: " + letters);
				words.add(letters);
			}
			//System.out.println(letters);
			for (int i=0; i<letters.length()-1; i++) {
				Character ch = letters.charAt(i);
				ScrabbleWords(lex, letters.substring(0,i)+letters.substring(i+1),  words);
			}
		}
	}
	
	private List<String> readToArray(String filename) {
		List<String> lines = new ArrayList<String>();
        FileReader fileReader;
		try {
			fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);	        
	        String line = null;
	        try {
				while ((line = bufferedReader.readLine()) != null) {
				    lines.add(line);
				}
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        return lines;
	}
	
	private static Set<String> readToSet(String filename) {
		Set<String> myset = new HashSet<String>();
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				//System.out.println(strLine);
				myset.add(strLine);
			}
			br.close();
		} catch (Exception e){
			System.out.println("Error: "+e.getMessage());
		}
		return myset;
	}
	
	/* 
	 * ****************************************************************
	 *                              ANAGRAMS                          *
	 * ****************************************************************
	 */
	
	private static void shuffle(String word, String build) {
		if (word.length()==0) {
			System.out.println(build);
		} else {
			for (int i=0; i<word.length(); i++) {
				shuffle(word.substring(0,i)+word.substring(i+1),
						word.charAt(i)+build);
			}
		}
	}
	
	/**
	 * Given the string of letters, find an anagram and return it as
	 * a List of words. If no anagram is found, return null.
	 * @param word
	 * @return
	 */
	public void findAnagram(String word) {
		shuffle(word, "");
	}
	
	/* 
	 * ****************************************************************
	 *                              ANAGRAMS                          *
	 * ****************************************************************
	 */
	
	List<Integer> copyList(List<Integer> copyThis) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < copyThis.size(); i++) {
			int temp = copyThis.get(i);
			list.add(temp);
		}
		return list;
	}
	
	private int getMinimum(List<Integer> requests, int stockLength, 
			List<Integer> remainderPieces) {
		if (requests.size() == 0) {
			return 0;
		} else {
			int overallMin = Integer.MAX_VALUE;
			
			for (int i=0; i<requests.size(); i++) {
				//cut from new stock
				List<Integer> tempRequests = copyList(requests);	
				System.out.println("hello"+tempRequests);
				System.out.println("what "+requests);
				int requestedPiece = requests.get(i);
				tempRequests.remove(i);
				
				List<Integer> tempRemainderPieces1 = copyList(remainderPieces);
				
				if (stockLength-requestedPiece>0) {
					tempRemainderPieces1.add(stockLength-requestedPiece);
					int minFromNewPiece = 1+getMinimum(tempRequests, stockLength, tempRemainderPieces1);				
					if (minFromNewPiece < overallMin) overallMin = minFromNewPiece;
				}
				
				if (stockLength-requestedPiece == 0) {
					int minFromNewPiece = 1+getMinimum(tempRequests, stockLength, tempRemainderPieces1);
					if (minFromNewPiece < overallMin) overallMin = minFromNewPiece;
				}
				
				//cut from remainder pieces
				for (int j=0; j<remainderPieces.size(); j++) {
					List<Integer> tempRemainderPieces2 = copyList(remainderPieces);
					int usedStock = remainderPieces.get(j);
					
					if (usedStock > requestedPiece) {
						tempRemainderPieces2.remove(j);
						tempRemainderPieces2.add(usedStock-requestedPiece); //add back
						int minFromRemainderPiece = getMinimum(tempRequests, stockLength, tempRemainderPieces2);
						if (minFromRemainderPiece < overallMin) overallMin = minFromRemainderPiece;
					} 
					
					if (usedStock == requestedPiece) {
						tempRemainderPieces2.remove(j);
						int minFromRemainderPiece = getMinimum(tempRequests, stockLength, tempRemainderPieces2);
						if (minFromRemainderPiece < overallMin) overallMin = minFromRemainderPiece;
					}
				}
			}
			System.out.println("overall min: "+overallMin);
			return overallMin;
		}
	}
	
	/**
	 * Given the requests in a List and the stock length on hand, returns
	 * the minimum number of pieces of lumber necessary so that we can cut
	 * the stock lumber to the pieces we request.
	 * 
	 * @param list
	 * @param stockLength
	 * @return
	 */
	public int cutStock(List<Integer> list, int stockLength) {
		List<Integer> remainderPieces = new ArrayList<Integer>();
		return getMinimum(list, stockLength, remainderPieces);
	}
	
	/**
	 * Given a character, will return the Scrabble score for that character.
	 * A non-letter character will return a score of 0.
	 * 
	 * @param c
	 * @return
	 */
	private int scoreCharacter(char c) {
		Character.toLowerCase(c);
		switch (c) {
		case 'a': case 'e': case 'i': case 'o': case 'u':
		case 'l': case 'n': case 'r': case 't': case 's':
			return 1;

		case 'd': case 'g':
			return 2;

		case 'b': case 'c': case 'm': case 'p':
			return 3;

		case 'f': case 'h': case 'v': case 'w': case 'y':
			return 4;

		case 'k':
			return 5;

		case 'j': case 'x':
			return 8;

		case 'q': case 'z':
			return 10;

		default:
			return 0;

		}
	}
	// EVERYTHING BELOW IS SETUP CODE AND CODE THAT DOESN'T NEED
	// TO BE CHANGED.

	private final static String BUTTON_RUN = "Run all!";
	private final static String BUTTON_TEST = "Test";

	/**
	 * Nothing fancy. Just adds two buttons to test and run.
	 */
	@Override
	public void init() {
		setTitle("Recursion");
		JButton run = new JButton(BUTTON_RUN);
		JButton test = new JButton(BUTTON_TEST);
		run.addActionListener(this);
		test.addActionListener(this);
		add(run, NORTH);
		add(test, NORTH);
	}

	/**
	 * Either runs tests or runs all problems depending on button
	 * clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(BUTTON_RUN)) {
			runAllProblems();
		} else if (e.getActionCommand().equals(BUTTON_TEST)) {
			test();
		}
	}

	/**
	 * Blocks until text is read.
	 */
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private static void waitUntilEnter() {
		System.out.println("Press enter to continue...");
		try {
			in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Nothing.
	 */
	@Override
	public void run() {
		
	}

	/**
	 * Runs all the recursion problems in order. Do not change this.
	 */
	private void runAllProblems() {
		System.out.println("==========================");
		System.out.println("Now Running mnemonicPlease");
		System.out.println("==========================");
		System.out.println("Testing with 92");
		mnemonicPlease("92");
		waitUntilEnter();
		System.out.println("Testing with 748");
		mnemonicPlease("748");
		waitUntilEnter();

		System.out.println("==========================");
		System.out.println("Now Running canSumTo");
		System.out.println("==========================");
		System.out.println("Testing {2,2,5,-4,-3} with target 0 (Should be true)");
		System.out.println("Result: " + canSumTo(new HashSet<Integer>(Arrays.asList(new Integer[] {2,2,5,-4,-3})), 0));
		waitUntilEnter();
		System.out.println("Testing {4,5,1,-12,0} with target 3 (Should be false)");
		System.out.println("Result: " + canSumTo(new HashSet<Integer>(Arrays.asList(new Integer[] {4,5,1,-12,0})), 3));
		waitUntilEnter();

		System.out.println("==========================");
		System.out.println("Now Running isSolvable");
		System.out.println("==========================");
		System.out.println("Testing {4" +
				",6,1,2,2,3,3,1,0} (Should be solvable)");
		System.out.println("Solvable? " + isSolvable(new int[] {4,6,1,2,2,3,3,1,0})); // Is solvable
		waitUntilEnter();
		System.out.println("Testing {4,1,4,5,1,5,1,0} (Not solvable)");
		System.out.println("Solvable? " + isSolvable(new int[] {4,1,4,5,1,5,1,0})); // Not solvable
		waitUntilEnter();

		System.out.println("==========================");
		System.out.println("Now Running highestScoringWord");
		System.out.println("==========================");
		System.out.println("Testing with \"einrfgm\". (FINGER or FRINGE or FERMI all worth 10 points)");
		System.out.println("Highest scoring word: " + highestScoringWord("einrfgm"));
		waitUntilEnter();
		System.out.println("Testing with \"AQFLIB\". (QI worth 11 points)");
		System.out.println("Highest scoring word: " + highestScoringWord("AQFLIB"));
		waitUntilEnter();
		
		System.out.println("==========================");
		System.out.println("Now Running findAnagram");
		System.out.println("==========================");
		System.out.println("Testing with \"epgyrocks\"");
		//System.out.println("findAnagram returned: " + findAnagram("epgyrocks"));
		waitUntilEnter();
		System.out.println("Testing with \"intelligence\"");
		//System.out.println("findAnagram returned: " + findAnagram("intelligence"));
		waitUntilEnter();
		

		
		System.out.println("===============================");
		System.out.println("===  ALL TESTS COMPLETE! :) ===");
		System.out.println("===============================");
		
	}
}
