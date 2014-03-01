import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
	
	private Set<String> lexicon;
	
	public static void main(String[] args) {
		new RecursionProblems().start(args);
	}
	
	/* 
	 * *************************************************************
	 *                        READ LEXICONS                        *
	 * *************************************************************
	 */
	
	private Set<String> getLexicon() {
		lexicon = new HashSet<String>();
		
		try {
			FileReader fr = new FileReader("FullLexicon.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while ((line = br.readLine()) != null) {
				lexicon.add(line);
				//System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lexicon;
	}
	
	/* 
	 * *************************************************************
	 *                        READ LEXICONS                        *
	 * *************************************************************
	 */
	
	/* 
	 * *************************************************************
	 *                        TESTING CODE                         *
	 * *************************************************************
	 */

	/**
	 * Put any testing code you want in here.
	 */
	private void test() {
		mnemonicPlease("92");
	}

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
		//save stuff
		getLexicon();
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
	public void run() {}

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
		System.out.println("Testing {4,6,1,2,2,3,3,1,0} (Should be solvable)");
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
		System.out.println("findAnagram returned: " + findAnagram("epgyrocks"));
		waitUntilEnter();
		System.out.println("Testing with \"intelligence\"");
		System.out.println("findAnagram returned: " + findAnagram("intelligence"));
		waitUntilEnter();
		
		System.out.println("==========================");
		System.out.println("Now Running stockCutting");
		System.out.println("==========================");
		System.out.println("Testing with requests {1,3,4,3,1} and stock length 4 (should require 3)");
		System.out.println("Result: " + cutStock(Arrays.asList(new Integer[] {1,3,4,3,1}), 4));
		waitUntilEnter();
		System.out.println("Testing with requests {3,3,3,3,3} and stock length 4 (should require 5)");
		System.out.println("Result: " + cutStock(Arrays.asList(new Integer[] {3,3,3,3,3}), 4));
		waitUntilEnter();
		System.out.println("Testing with requests {2,2,3,3} and stock length 5 (should require 2)");
		System.out.println("Result: " + cutStock(Arrays.asList(new Integer[] {2,2,3,3}), 5));
		waitUntilEnter();
		System.out.println("Testing with requests {3,3,2,2,2,2} and stock length 7 (should require 2)");
		System.out.println("Result: " + cutStock(Arrays.asList(new Integer[] {3,3,2,2,2,2}), 7));
		waitUntilEnter();
		
		System.out.println("===============================");
		System.out.println("===  ALL TESTS COMPLETE! :) ===");
		System.out.println("===============================");
		
	}
	
	/* 
	 * *************************************************************
	 *                        TESTING CODE                         *
	 * *************************************************************
	 */
	
	/* 
	 * *************************************************************
	 *                      MNEMONIC PLEASE                        *
	 * *************************************************************
	 */
	
	private HashMap<Character,String> numToChar;

	/**
	 * Prints out all TouchTone phone mnemonics that can be generated
	 * given the string of the phone digits.
	 * 
	 * @param str
	 */
	public void mnemonicPlease(String str) {
		getNumToChar();
		printAllPossibilities(str, "");
	}
	
	private void getNumToChar() {
		numToChar = new HashMap<Character, String>();
		numToChar.put('2',"abc");
		numToChar.put('3',"def");
		numToChar.put('4',"ghi");
		numToChar.put('5',"jkl");
		numToChar.put('6',"mno");
		numToChar.put('7',"pqrs");
		numToChar.put('8',"tuv");
		numToChar.put('9',"wxyz");
	}
	
	private void printAllPossibilities(String word, String built) {
		if (word.length()==0) {
			System.out.println(built);
		} else {
			char num = word.charAt(0);
			String mappedTo = numToChar.get(num);
			for (int i=0; i<mappedTo.length(); i++) {
				char ch = mappedTo.charAt(i);
				printAllPossibilities(word.substring(1), built+ch);
			}
		}
	}
	
	/* 
	 * *************************************************************
	 *                      MNEMONIC PLEASE                        *
	 * *************************************************************
	 */
	
	/* 
	 * *************************************************************
	 *                          CAN SUM TO                         *
	 * *************************************************************
	 */

	/**
	 * Determines whether a subset of the numbers in the given set
	 * can sum up to the target.
	 * 
	 * @param nums
	 * @param target
	 * @return
	 */
	public boolean canSumTo(Set<Integer> nums, int target) {
		if (nums.size() == 1) {
			Iterator<Integer> iterator = nums.iterator();
			return iterator.next() == target;
		} else {
			for (Integer n : nums) {
				Set<Integer> newNums = copySet(nums);
				System.out.println(newNums);
				newNums.remove(n);
				return canSumTo(newNums, target-n);
			}
			return true;
		}
	}
	
	private Set<Integer> copySet(Set<Integer> nums) {
		Set<Integer> newNums = new HashSet<Integer>();
		for (Integer n : nums) {
			newNums.add(n);
		}
		return newNums;
	}
	
	/* 
	 * *************************************************************
	 *                          CAN SUM TO                         *
	 * *************************************************************
	 */
	
	/* 
	 * *************************************************************
	 *                          IS SOLVABLE                        *
	 * *************************************************************
	 */

	/**
	 * Determines if the given representation of the puzzle presented in lab
	 * can be solved.
	 * 
	 * @param row
	 * @return
	 */
	public boolean isSolvable(int[] row) {
		int len = row.length;
		boolean[] isVisited = new boolean[len];
		for (int i=0; i<len; i++) {
			isVisited[i] = false;
		}
		return canSolve(row, 0, isVisited);
	}
	
	private boolean canSolve(int[] row, int index, boolean[] isVisited) {
		int len = row.length;
		if (row[index] == 0) {
			return true;
		} else {
			int index1 = index+row[index];
			int index2 = index-row[index];
			isVisited[index] = true;
			if (index1 >= 0 && index1 < len && isVisited[index1] == false) {
				return canSolve(row, index1, isVisited);
			} 
			if (index2 >= 0 && index2 < len && isVisited[index2] == false) {
				return canSolve(row, index2, isVisited);
			} 
			return false;
		}
	}
	
	/* 
	 * *************************************************************
	 *                          IS SOLVABLE                        *
	 * *************************************************************
	 */
	
	/* 
	 * *************************************************************
	 *                     HIGHEST SCORING WORD                    *
	 * *************************************************************
	 */

	/**
	 * Determines the highest-scoring Scrabble word given the letters.
	 * 
	 * @param letters
	 * @return
	 */
	public String highestScoringWord(String letters) {
		letters.toLowerCase();
		List<String> perms = new ArrayList<String>();
		getAllPermutations(letters, "", perms);
		System.out.println(perms);
		return highestScored(perms);
	}
	
	private String highestScored(List<String> perms) {
		int highestScore = 0;
		String highestWord = "";
		for (int i=0; i<perms.size(); i++) {
			String word = perms.get(i);
			//get score
			int score = 0;
			for (int j=0; j<word.length(); j++) {
				char ch = word.charAt(j);
				score += scoreCharacter(ch);
			}
			//check
			if (score > highestScore && lexicon.contains(word)) {
				highestScore = score;
				highestWord = word;
			}
		}
		return highestWord;
	}
	
	/*
	 * Gets all permutation of letters in the string "letters"
	 */
	private void getAllPermutations(String letters, String build, List<String> perms) {
		if (letters.length() == 0) {
			//System.out.println(build);
			perms.add(build);
		} else {
			for (int i=0; i<letters.length(); i++) {
				char ch = letters.charAt(i);
				String remaining = letters.substring(0,i)+letters.substring(i+1); // one char less
				//System.out.println("remain: "+remaining);
				getAllPermutations(remaining, build+ch, perms);
			}
		}
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
	
	/* 
	 * *************************************************************
	 *                     HIGHEST SCORING WORD                    *
	 * *************************************************************
	 */
	
	/* 
	 * *************************************************************
	 *                            ANAGRAMS                         *
	 * *************************************************************
	 */
	
	/**
	 * Given the string of letters, find an anagram and return it as
	 * a List of words. If no anagram is found, return null.
	 * @param word
	 * @return
	 */
	public List<String> findAnagram(String word) {
		// ------------------------------ //
		// TODO IMPLEMENT FINDANGRAM HERE //
		// ------------------------------ //
		System.out.println("NOT IMPLEMENTED");
		return null;
	}
	
	/* 
	 * *************************************************************
	 *                            ANAGRAMS                         *
	 * *************************************************************
	 */
	
	/* 
	 * *************************************************************
	 *                           CUT STOCK                         *
	 * *************************************************************
	 */
	
	/**
	 * Given the requests in a List and the stock length on hand, returns
	 * the minimum number of pieces of lumber necessary so that we can cut
	 * the stock lumber to the pieces we request.
	 * 
	 * @param requests
	 * @param stockLength
	 * @return
	 */
	public int cutStock(List<Integer> requests, int stockLength) {
		List<Integer> lumberUsed = new ArrayList<Integer>();
		return minimumPieces(requests, stockLength, lumberUsed);
	}
	
	
	private List<Integer> copyList(List<Integer> requests) {
		List<Integer> copy = new ArrayList<Integer>();
		for (int i=0; i<requests.size(); i++) {
			copy.add(requests.get(i));
		}
		return copy;
	}
	
	private int minimumPieces(List<Integer> requests, 
			int stockLength, List<Integer> lumberUsed) {
		if (requests.size() == 0) {
			return 0;
		} else {
			int minimum = Integer.MAX_VALUE;
			for (int i=0; i<requests.size(); i++) {
				int request = requests.get(i);
				List<Integer> remainingRequests = copyList(requests);
				remainingRequests.remove(i);
				//cut from new stock
				List<Integer> newLumberUsed1 = copyList(lumberUsed);
				if (stockLength > request) newLumberUsed1.add(stockLength-request);
				int numUsed1 = 1+minimumPieces(remainingRequests, stockLength, newLumberUsed1);
				if (numUsed1 < minimum) minimum = numUsed1; 
				// used old ones
				for (int j=0; j<lumberUsed.size(); j++) {
					int remainingPiece = lumberUsed.get(j);
					if (remainingPiece >= request) {
						List<Integer> newLumberUsed2 = copyList(lumberUsed);
						newLumberUsed2.remove(j);
						if (remainingPiece > request) newLumberUsed2.add(remainingPiece-request);
						int numUsed2 = minimumPieces(remainingRequests, stockLength, newLumberUsed2);
						if (numUsed2 < minimum) minimum = numUsed2;
					}
				}
			}
			return minimum;
		}
	}
	
	/* 
	 * *************************************************************
	 *                           CUT STOCK                         *
	 * *************************************************************
	 */
	
	

}
