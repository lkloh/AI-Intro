import acm.graphics.GLabel;
import acm.graphics.GRect;


public class Cards {
	
	public enum moves {hit, stay};
	
	public enum player {AL, human};
	
	public static final int NUM_STATES = 13;
	
	public static final int CARD_WIDTH = 50;
	public static final int CARD_HEIGHT = 80;
	
	private GRect card;
	private GLabel label;
	
	private int width;
	private int height;
	private String state = "";

	public Cards(int width, int height, String state) {
		this.state = state;
		this.width = width;
		this.height = height;
	}
	
	public GRect getCard(String player) {
		card = new GRect(width/2, height/4, CARD_WIDTH, CARD_HEIGHT);
		return card;
	}
	
	public GLabel getLabel(String player) {
		label = new GLabel(state.toString(), CARD_WIDTH, CARD_HEIGHT);
		label.setLocation(width/2, height/2);
		return label;
	}
	
}
