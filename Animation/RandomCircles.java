import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;


public class RandomCircles extends GraphicsProgram {

	private static final int PAUSE_TIME = 100;
	private static final int MAX_RADIUS = 100;
	private static final int MIN_DIAMETER = 50;
	private static final int DIAM_DIFF = 40;
	private RandomGenerator rgen = new RandomGenerator();
	GOval oval;
	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;
	public static final int APPLICATION_WIDTH = 800;
	public static final int APPLICATION_HEIGHT = 600;
	
	public static void main(String[] args) {
		new RandomCircles().start(args);
	}
	
	public void run() {
		int screenWidth = getWidth();
		int screenHeight = getHeight();
		while (true) {
			//drawRandomOval(screenWidth, screenHeight);
			drawRandomDonut(screenWidth, screenHeight);
			pause(PAUSE_TIME);
		}
	}
	
	private void drawRandomOval(int screenWidth, int screenHeight) {
		int radius = MIN_DIAMETER+rgen.nextInt(40);
		GOval oval = new GOval(rgen.nextInt(screenWidth),
				rgen.nextInt(screenHeight),
				radius, radius);
		oval.setColor(rgen.nextColor());
		oval.setFilled(true);
		add(oval);
	}
	
	private void drawRandomDonut(int screenWidth, int screenHeight) {
		int ADD = rgen.nextInt(40);
		int xloc = rgen.nextInt(screenWidth);
		int yloc = rgen.nextInt(screenHeight);
		
		GOval ovalOuter 
			= new GOval(xloc,yloc, MIN_DIAMETER+ADD, 
					MIN_DIAMETER+ADD);
		ovalOuter.setColor(rgen.nextColor());
		ovalOuter.setFilled(true);
		add(ovalOuter);
		
		GOval ovalInner = new GOval(xloc+ADD/2, yloc+ADD/2, 
				MIN_DIAMETER, MIN_DIAMETER);
		ovalInner.setColor(Color.WHITE);
		ovalInner.setFilled(true);
		add(ovalInner);
	}
	
	
}
