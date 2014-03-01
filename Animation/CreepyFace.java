import java.awt.Color;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class CreepyFace extends GraphicsProgram {

	//Basic screen settings
	public static final int APPLICATION_WIDTH = 500;
	public static final int APPLICATION_HEIGHT = 400;
	
	//face shape constants
	public static final int EYE_HEIGHT = 100;
	public static final int EYE_WIDTH = 100;
	public static final int NOSE_HEIGHT = 150;
	public static final int NOSE_WIDTH = 100;
	public static final int BEARD_HEIGHT = 125;
	public static final int BEARD_WIDTH = 50;
	
	//starting position
	public static final int START_EYES = 50;
	public static final int START_NOSE = 150;
	public static final int START_BEARD = 200;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CreepyFace().start(args);
	}
	
	public void run() {
		
	}
	
	public void init() {
		setBackground(Color.GREEN);
		addEye(100);
		addEye(300);
		addNose();
		addBeard(150);
		addBeard(300);
	}
	
	private void addBeard(int startX) {
		GRect beard = new GRect(startX, START_BEARD, BEARD_WIDTH, BEARD_HEIGHT);
		beard.setColor(Color.BLACK);
		beard.setFilled(true);
		add(beard);
	}
	
	private void addEye(int startX) {
		GRect eye = new GRect(startX, START_EYES, EYE_WIDTH, EYE_HEIGHT);
		eye.setColor(Color.BLACK);
		eye.setFilled(true);
		add(eye);
	}
	
	private void addNose() {
		GRect nose = new GRect(200, START_NOSE, NOSE_WIDTH, NOSE_HEIGHT);
		nose.setColor(Color.BLACK);
		nose.setFilled(true);
		add(nose);
	}

}
