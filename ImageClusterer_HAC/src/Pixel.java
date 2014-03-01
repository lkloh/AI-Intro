/**
 * The Pixel class can be used to store the RGB values of a single pixel.
 * It also allows for distance calculations 
 * 
 * @author Ben Holtz
 *
 */
public class Pixel {

	private int r, g, b, rgb, x, y;
	
	/**
	 * This constructor allows us to create a pixel specified as an RGB triple. Note that 
	 * all parameters should be in the range (0,255).
	 * @param r The red component of this pixel
	 * @param g The green component of this pixel
	 * @param b The blue component of this pixel
	 */
	public Pixel(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
		// I apologize for the use of bit operations here. Look these up online if you have
		// questions about them.
		this.rgb = r + (g << 8) + (b << 16) + (255 << 24); 
	}
	
	/**
	 * This constructor takes in an int value with all 4 bytes of pixel RGB data and copies 
	 * it into our pixel class.
	 * @param rgb The int value of the pixel
	 */
	public Pixel(int rgb) {
		this.r = rgb & 255;
		this.g = rgb & (255*256);
		this.b = rgb & (255*256*256);
		this.rgb = rgb;
	}
	
	public int getRed() {
		return this.r;
	}
	
	public int getGreen() {
		return this.g;
	}
	
	public int getBlue() {
		return this.b;
	}
	
	public int getRGB() {
		return this.rgb;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setRed(int r) {
		this.r = r;
	}

	public void setGreen(int g) {
		this.g = g;
	}

	public void setBlue(int b) {
		this.b = b;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Compute the distance between two pixels. Try changing the way this is measured
	 * to change the way that the clustering is done.
	 * @param other Another pixel to take the distance to
	 * @return The distance from this to other
	 * x, y are coordinate locations of pixel
	 */
	public double distance(Pixel other) {
		return Math.sqrt(
				Math.pow((other.getRed() - getRed()), 2) +
				Math.pow((other.getGreen() - getGreen()), 2) +
				Math.pow((other.getBlue() - getBlue()), 2) 
				);
	}
	
	@Override
	public String toString() {
		return "R,G,B: (" + r + ',' + g + ',' + b + ") RGB: " + rgb;  
	}
	

}
