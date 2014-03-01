
public class Deck {
	
	private int suit;
	private int number;
	
	public Deck(int suit, int number) {
		this.suit = suit;
		this.number = number;
	}

	public int suit() {
		return this.suit;
	}
	
	public int number() {
		return this.number;
	}
	
}
