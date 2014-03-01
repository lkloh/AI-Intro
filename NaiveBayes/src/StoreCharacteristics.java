
public class StoreCharacteristics {
	
	private char type;
	private String characteristics;

	public StoreCharacteristics(String next) {
		char ch1 = next.charAt(0);
		this.type = ch1;
		
		characteristics = next.substring(1,5);
	}
	
	public char getType() {
		return this.type;
	}
	
	public String getCharacteristics() {
		return this.characteristics;
	}
	
	public String toString() {
		return "type: "+type+", characteristics: "+characteristics;
	}
	
}
