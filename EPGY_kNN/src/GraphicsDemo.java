import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;

import acm.graphics.*;
import acm.program.*;


public class GraphicsDemo extends GraphicsProgram {

	private GOval ball;
	private GRect paddle;
	private GLabel welcome;
	private double dx, dy;

	public static final int APPLICATION_HEIGHT = 600;
	public static final int APPLICATION_WIDTH = 400;
	public static final int WIDTH = APPLICATION_WIDTH;
	public static final int HEIGHT = APPLICATION_HEIGHT;
	public static final int NUM_TURNS = 3;
	private static final double PADDLE_HEIGHT = 10;
	private static final double PADDLE_WIDTH = 60;
	private static final double PADDLE_OFFSET = PADDLE_HEIGHT + 40;
	private static final double RADIUS = 9;
	private static final int NUM_TESTS = 10;
	private static final int NUM_ROWS = 10;
	private static final int NUM_COLS = 8;
	private static final double BRICK_WIDTH = 40;
	private static final double BRICK_HEIGHT = 10;
	private static final int MAX_COLOR = 255;
	private static final double BRICK_SEP = (WIDTH - BRICK_WIDTH * NUM_COLS) / (NUM_COLS + 1);
	private static final double PAUSE = 3.0;
	private static final double BALL_START_HEIGHT = HEIGHT * 3 / 5;


	public void init() {
		addBricks();
		paddle = initPaddle();
		addMouseListeners();
	}

	/**
	 * Create the bricks for our ball to destroy.
	 */
	private void addBricks() {
		for (int row = 0; row < NUM_ROWS; row++) {
			for (int col = 0; col < NUM_COLS; col++) {
				addBrick(row, col);
			}
		}
	}

	/**
	 * Add a single brick to the bricks. Each brick is a GRect with a random color.
	 */
	private void addBrick(int row, int col) {
		GRect brick = new GRect(BRICK_WIDTH , BRICK_HEIGHT);
		brick.setFilled(true);
		brick.setColor(getRandomColor());
		brick.setLocation((BRICK_SEP + BRICK_WIDTH) * col + BRICK_SEP, (BRICK_SEP + BRICK_HEIGHT) * row + BRICK_SEP);
		add(brick);
	}

	/**
	 * Pick a random color by choosing RGB values between 0 and 255
	 * @return A random color
	 */
	private Color getRandomColor() {
		Random random = new Random();
		return new Color(random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR), random.nextInt(MAX_COLOR));
	}

	/**
	 * Create the paddle and add it to the screen
	 * @return A reference to the GRect that is used as the padde
	 */
	private GRect initPaddle() {
		GRect rect = new GRect(0, HEIGHT - PADDLE_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
		rect.setFilled(true);
		add(rect);
		return rect;
	}

	/**
	 * Create a new ball and place it in the start position. Also reset movement
	 * @return A reference to the new ball
	 */
	private GOval initBall() {
		GOval oval = new GOval(WIDTH / 2 - RADIUS, BALL_START_HEIGHT, 2 * RADIUS, 2 * RADIUS);
		oval.setFilled(true);
		oval.setColor(Color.BLACK);
		dy = 1.0;
		dx = new Random().nextGaussian();
		add(oval);
		return oval;
	}

	/**
	 * Add the welcome label to the screen
	 * @return
	 */
	private GLabel initWelcome() {
		GLabel label = new GLabel("Click to start the next turn");
		label.setLocation((WIDTH - label.getWidth()) / 2, HEIGHT / 2);
		add(label);
		return label;
	}

	public void run() {
		for(int turn = 0; turn < NUM_TURNS; turn++) {
			playOneTurn();
		}
	}

	/**
	 * Perform all of the steps necessary to run a single turn in breakout
	 */
	private void playOneTurn() {
		ball = initBall();
		welcome = initWelcome();
		waitForClick();
		remove(welcome);
		while (true) {
			if (lostBall()) {
				remove(ball);
				break;
			}
			moveBall();
			checkForCollisions();
			pause(PAUSE);
		}
	}

	/**
	 * Check if the ball is touching any other objects and have it bounce off of them. 
	 * Checks for collisions in a circle around the ball
	 */
	private void checkForCollisions() {
		GObject collider = null;
		for (int i = 0; i < NUM_TESTS; i++) {
			GPoint test = getTestPoint(i);
			collider = this.getElementAt(test);
			if (collider == null) continue;
			else if (collider == paddle) dy = -Math.abs(dy);
			else {
				remove(collider);
				dy = -dy;
			}
			break;
		}
	}

	/**
	 * Calculate the i-th point in a circle around the ball
	 * @param i The index of the point to test
	 * @return A GPoint at the i-th test location
	 */
	private GPoint getTestPoint(int i) {
		double angle = i * 2 * Math.PI / NUM_TESTS;
		double x = RADIUS * 1.1 * Math.cos(angle) + RADIUS + ball.getX();
		double y = RADIUS * 1.1 * Math.sin(angle) + RADIUS + ball.getY();
		return new GPoint(x,y);
	}

	/**
	 * Move the ball forward one time step. Also handle bouncing off the walls
	 * and ceiling.
	 */
	private void moveBall() {
		if (ball.getX() < 0.0 || ball.getX() > getWidth() - 2 * RADIUS) dx = -dx;
		if (ball.getY() < 0.0) dy = -dy;
		ball.move(dx, dy);
	}

	/**
	 * Test if the ball has been lost
	 * @return true if the ball is lost, false otherwise
	 */
	private boolean lostBall() {
		return ball.getY() > HEIGHT - RADIUS;
	}

	/**
	 * Handle movement of the paddle
	 */
	public void mouseMoved(MouseEvent e) {
		// The following lines keep the paddle from leaving the screen
		double xPos = Math.max(0, e.getX() - PADDLE_WIDTH / 2);
		xPos = Math.min(xPos, getWidth() - PADDLE_WIDTH);
		paddle.setLocation(xPos, APPLICATION_HEIGHT - PADDLE_OFFSET);
	}

	public static void main(String[] args) {
		new GraphicsDemo().start(args);
	}
}
