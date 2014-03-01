import java.awt.Color;

import acm.graphics.GOval;
import acm.program.GraphicsProgram;

public class RecurseDots extends GraphicsProgram {

	//constant pause time
	private static final int PAUSE_TIME = 20;
	
	//Initial Ball Radius
	private static final int BALL_RADIUS = 200;
	
	//Our Recursion Tree Depth
	private static final int DEPTH = 7;
	
	//Basic screen settings
	public static final int APPLICATION_WIDTH = 1440;
	public static final int APPLICATION_HEIGHT = 980;
	
	public static final int TRIANGLE_SIDE = 100;
	
	//start our Recurse objects
	public static void main(String[] args) {
		new RecurseDots().start(args);
	}
	
	
	//main code logic here:
	public void run() {
		GOval ball = addDot(getWidth()/2, getHeight()/2);
		TriangleSplit(DEPTH, ball, TRIANGLE_SIDE);
	}
	
	/**
	 * @param n is how many splits we want to do.
	 * @param ball is the first ball to be split
	 * @return void
	 * Method takes a ball and splits it into four
	 */
	public void TriangleSplit(int depth, GOval ball, double separation) {
		if (depth == 0) {
			//do nothing, ball already added
		} else {
			//copy info
			double xloc = ball.getX();
			double yloc = ball.getY();
			//add balls
			GOval ball1 = addDot(xloc, yloc-separation/Math.sqrt(3));
			GOval ball2 = addDot(xloc-separation/2, 
					yloc+separation/(2*Math.sqrt(3)));
			GOval ball3 = addDot(xloc+separation/2, 
					yloc+separation/(2*Math.sqrt(3)));
			//move balls
			ball.setVisible(false);
			//recursive call
			TriangleSplit(depth-1, ball1, separation/3);
			TriangleSplit(depth-1, ball2, separation/3);
			TriangleSplit(depth-1, ball3, separation/3);
		}
	}
	
	/**
	 * 
	 * @param x ball x location
	 * @param y ball y location
	 * @return the ball to add
	 */
	public GOval addDot(double x,double y)
	{
		GOval aball = new GOval(x,y,5,5);
		aball.setFilled(true);
		aball.setColor(Color.BLACK);
		add(aball);
		return aball;
	}
	
}
