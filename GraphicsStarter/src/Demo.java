import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class Demo extends GraphicsProgram {

	public static final int APPLICATION_HEIGHT = 600;
	public static final int APPLICATION_WIDTH = 400;
	private static final int NUM_COLS = 8;
	private static final int NUM_ROWS = 10;
	private static final double BRICK_SEP = 5;
	private static final double BRICK_HEIGHT = 20;
	private static final double BRICK_WIDTH = 
			(APPLICATION_WIDTH - ((NUM_COLS + 1) * BRICK_SEP)) / NUM_COLS;
	private static final double PADDLE_WIDTH = 75;
	private static final double PADDLE_HEIGHT = 25;
	private static final int BALL_RAD = 12;
	private static final double PAUSE_TIME = 2;
	private GRect paddle;
	private GOval ball;
	private double dx;
	private double dy;
	
	/**
	 * Sets up the parts of game that need to be in place before the animation starts.
	 */
	public void init() {
		this.setTitle("Fix This Breakout!");
		initBall();
		initPaddle();
		addMouseListeners();
		initBricks();
		waitForClick();
	}
	
	/**
	 * Sets the ball in place in the middle of the screen with a constant velocity. Terribly boring,
	 * don't you think?
	 */
	private void initBall() {
		double xOffset = APPLICATION_WIDTH / 2 - BALL_RAD;
		double yOffset = APPLICATION_HEIGHT / 2 - BALL_RAD;
		ball = new GOval(xOffset, yOffset, BALL_RAD * 2, BALL_RAD * 2);
		ball.setFilled(true);
		ball.setColor(Color.BLACK);
		dx = .3; // These magic numbers are bad, but maybe you can replace them with more interesting speeds
		dy = .2; // e.g., random or varying
		add(ball);
	}

	/**
	 * Adds the paddle, an instance variable, to the screen. Nothing fancy here.
	 */
	private void initPaddle() {
		double xOffset = APPLICATION_WIDTH / 2 - PADDLE_WIDTH / 2;
		double yOffset = APPLICATION_HEIGHT - PADDLE_HEIGHT - BRICK_SEP * 2;
		paddle = new GRect(xOffset, yOffset, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setColor(Color.RED);
		paddle.setFilled(true);
		add(paddle);
	}

	/**
	 * Adds all of the bricks to be broken at the top of the screen. 
	 */
	private void initBricks() {
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				initBrick(col, row);
			}
		}
	}

	/**
	 * Adds a single brick to the screen. Each brick is boring old black. Maybe you can spice it up?
	 * @param col The column number of the brick to be added.
	 * @param row The row number of the brick to be added.
	 */
	private void initBrick(int col, int row) {
		double xOffset = BRICK_SEP + (BRICK_SEP + BRICK_WIDTH) * col;
		double yOffset = BRICK_SEP + (BRICK_SEP + BRICK_HEIGHT) * row;
		GRect rect = new GRect(xOffset, yOffset, BRICK_WIDTH, BRICK_HEIGHT);
		rect.setFilled(true);
		rect.setColor(Color.BLUE);
		add(rect);
	}

	public void run() {
		while (true) {
			moveBall();
			pause(PAUSE_TIME); 
		}
	}
	
	/**
	 * Moves the ball and handles any collisions. This method is called once per time step to 
	 * animate the game.
	 */
	private void moveBall() {
		// Advance the ball forward once.
		ball.move(dx, dy);
		
		// Bounce the ball off of the ceiling.
		if (ball.getY() < 0) 
			dy *= -1;
		
		// Bounce the ball off of the sides
		if (ball.getX() < 0 || ball.getX() > (APPLICATION_WIDTH - 2 * BALL_RAD))
			dx *= -1;
		
		// Reset the ball when it passes the paddle
		if (ball.getY() > APPLICATION_HEIGHT) {
			restartGame();
		}
		
		// Handle collisions with other GObjects. This could use some work.
		GObject collider = getCollider();
		if (collider != null) {
			// Bounce off of the paddle
			if (collider.equals(paddle))
				// Funny absolute value business keeps the ball from getting "stuck" in paddle
				dy = Math.abs(dy) * (-1);
			// We have hit a brick, so remove it and bounce. Powerups?
			else {
				remove(collider);
				dy *= -1;
			}
		}
	}	

	/**
	 * Restarts the game, reseting the ball after it passes the paddle. Also displays
	 * a GLabel that delivers the bad news to the player.
	 */
	private void restartGame() {
		GLabel label = new GLabel("You have lost the game.");
		double height = label.getHeight();
		double width = label.getWidth();
		add(label, APPLICATION_WIDTH / 2 - width / 2, APPLICATION_HEIGHT / 2 - height / 2);
		ball.setLocation(APPLICATION_WIDTH / 2 - BALL_RAD, APPLICATION_HEIGHT / 2 - BALL_RAD);
		waitForClick();
	}

	/**
	 * Does the fanciest bit of work in this whole program. Detects collisions by checking 
	 * four points (the top, bottom, and sides of the ball) for overlap with other GObjects. 
	 * If no object is overlapping, then we return null. This collision detection could use some work.
	 * 
	 * @return The GObject that is overlapping with the ball, null if no object overlaps.
	 */
	private GObject getCollider() {
		double x = ball.getX();
		double y = ball.getY();
		GObject collider, temp;
		collider = null;
		temp = getElementAt(x + BALL_RAD, y - 1);
		if (temp != null) collider = temp;
		temp = getElementAt(x - 1, y + BALL_RAD);
		if (temp != null) collider = temp;
		temp = getElementAt(x + BALL_RAD * 2 + 1, y + BALL_RAD);
		if (temp != null) collider = temp;
		temp = getElementAt(x + BALL_RAD, y + BALL_RAD * 2 + 1);
		if (temp != null) collider = temp;
		return collider;
	}

	/**
	 * Moves the paddle to follow the mouse. Notice that the middle of thepaddle is attached 
	 * to the mouse and that the paddle never leaves the screen.
	 */
	public void mouseMoved(MouseEvent e) {
		double x = e.getX() - PADDLE_WIDTH / 2;
		if (x < 0) x = 0;
		if (x > APPLICATION_WIDTH - PADDLE_WIDTH) x = APPLICATION_WIDTH - PADDLE_WIDTH;
		paddle.setLocation(x, paddle.getY());
	}
}
