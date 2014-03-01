import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JButton;

import acm.graphics.GImage;
import acm.program.GraphicsProgram;


public class BlackJack extends GraphicsProgram{
	
	/* 
	 * *****************************************************************
	 *                              CONSTANT                           *
	 * *****************************************************************
	 */
	
	private Map<Integer, List<String>> intToCard 
		= new HashMap<Integer, List<String>>();
	
	public static final int APPLICATION_HEIGHT = 600;
	public static final int APPLICATION_WIDTH = 800;
	
	public enum moves {hit, stay};
	
	private enum playerDeck {player, card1, card2};
	
	private JButton quitButton;
	private static final String QUIT_TEXT = "Quit";
	
	private int count = 0; // num games played
	
	private static final int MAX_GAMES = 10;
	private static final int MAX_SCORE_TO_WIN = 21; 
	private static final int MAX_DEALER_SCORE = 17;
	
	/** Track scores */
	private int AIScore = 0;
	private int playerScore = 0;
	
	private int numCardsDrawn = 0;
	
	private static Random rand = new Random();
	
	private static final int NUM_CARDS_IN_SUIT= 13;
	private static final int NUM_SUITS = 4;
	
	private boolean[][] cardsTaken = new boolean[NUM_CARDS_IN_SUIT][NUM_SUITS];
	
	
	/* 
	 * *****************************************************************
	 *                              CONSTANT                           *
	 * *****************************************************************
	 */
	
	
	/* 
	 * *****************************************************************
	 *                              SET-UP                             *
	 * *****************************************************************
	 */

	public static void main(String[] args) {
		new BlackJack().start(args);
	}
	
	public void run() {
		while (true) {
			playBlackjack();
			count++;
			if (count == MAX_GAMES) break;
		}
	}
	
	
	public void init() {
		setTitle("Las Vegas Blackjack");
		addButtons();
		makeMap();
		initBooleanArray(cardsTaken);
	}
	
	private void initBooleanArray(boolean[][] arr) {
		for (int row=0; row<cardsTaken.length; row++) {
			for (int col=0; col<cardsTaken[0].length; col++) {
				arr[row][col] = false;
			}
		}
	}
	
	private void makeMap() {
		intToCard.put(1, Arrays.asList("Ace"));
		intToCard.put(2, Arrays.asList("Two"));
		intToCard.put(3, Arrays.asList("Three"));
		intToCard.put(4, Arrays.asList("Four"));
		intToCard.put(5, Arrays.asList("Five"));
		intToCard.put(6, Arrays.asList("Six"));
		intToCard.put(7, Arrays.asList("Seven"));
		intToCard.put(8, Arrays.asList("Eight"));
		intToCard.put(9,Arrays.asList("Nine"));
		intToCard.put(10, Arrays.asList("Ten","Queen", "King", "Jack"));
		intToCard.put(11,  Arrays.asList("Ace"));
	}
	
	private void addButtons() {
		quitButton = new JButton(QUIT_TEXT);
		quitButton.addActionListener(this);
		add(quitButton, SOUTH);
	}
	
	public void actionPerformed(ActionEvent event) {
		System.exit(0);
	}
	
	/* 
	 * *****************************************************************
	 *                              SET-UP                             *
	 * *****************************************************************
	 */
	
	/* 
	 * *****************************************************************
	 *                            PLAY BLACKJACK                       *
	 * *****************************************************************
	 */
	
	private void playBlackjack() {
		while (AIScore <= MAX_SCORE_TO_WIN) {
			//randomly choose two AI cards
			Deck c1 = chooseOneCardRandomly();
			Deck c2 = chooseOneCardRandomly();
			AIScore += c1.number();
			AIScore += c2.number();
			System.out.println(c1);
		}
	}
	
	private Deck chooseOneCardRandomly() {
		Deck deck;
		while (true) {
			int cardNo = rand.nextInt(NUM_CARDS_IN_SUIT*NUM_SUITS);
			int suit = (int) Math.floor(cardNo/NUM_SUITS);
			int number = cardNo % NUM_SUITS;
			System.out.println("number: "+number+" suits:"+suit);
			if (cardsTaken[number][suit] == false) {
				cardsTaken[number][suit] = true;
				deck = new Deck(suit, number);
				numCardsDrawn++;
				break;
			}
		}
		return deck;
	}
	
	/* 
	 * *****************************************************************
	 *                            PLAY BLACKJACK                       *
	 * *****************************************************************
	 */
	
	
}
