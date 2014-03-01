import acm.graphics.*;
import acm.util.*;
import acm.program.*;
import java.awt.*;


public class Seurat extends GraphicsProgram{

	private static final int NUM_PICTURES = 1;
	private static final int SPLOTCH_DIAMETER = 6;
	private RandomGenerator rgen = new RandomGenerator();
	private GImage img;
	private static final int MAX_POINTS = 100000;
	
	public static void main(String[] args) {
		new Seurat().start(args);
	}
	
	public void run() {
		getImage();
	}
	
	private void getImage() {
		img = new GImage("16496741.jpg");
		
		drawSeurat();
	}
	
	private void drawSeurat() {
		int[][] pixelArray = img.getPixelArray();
		int WIDTH = pixelArray[0].length;
		int HEIGHT = pixelArray.length;
		int count = 0;
		while (true) {
			int xPos = rgen.nextInt(WIDTH);
			int yPos = rgen.nextInt(HEIGHT);
			Color circleColor = getColorAt(img, xPos, yPos);
			drawCircle(xPos, yPos, circleColor);
			count++;
			if (count == MAX_POINTS) break;
		}			
	}
	

	private void drawCircle(int xPos, int yPos, Color circleColor) {
		GOval oval = new GOval(xPos, yPos, 
				SPLOTCH_DIAMETER, SPLOTCH_DIAMETER);
		oval.setColor(circleColor);
		oval.setFilled(true);
		add(oval);
	}
	
	/**
	 * 
	 * @param image
	 * @param x of a pixel
	 * @param y of a pixel
	 * @return the color found at a specific pixel
	 */
	private Color getColorAt(GImage image, int x, int y) {
		// feel free to ignore how the program is looking up the pixel color
		// this syntax is not very nice and you don't need to understand it.
		return new Color(image.getPixelArray()[y][x]);
	}

	
	
	
	
}
