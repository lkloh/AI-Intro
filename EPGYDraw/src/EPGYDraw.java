import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import acm.graphics.*;
import acm.program.*;


/**
 * Here's an intro/review to using Java, combining many concepts and parts of SimpleGraphics.
 * 
 * Your task: build a simple drawing program that does the following:
 *  (1) Buttons that lets you choose whether to draw an ellipse, a rectangle, a line, or a rounded rect
 *  (2) When the program first starts, the main canvas should display a nice welcoming message and/or graphics.
 *  (3) As soon as the user starts drawing, this welcome message should disappear
 *  (4) To draw, click and drag the mouse. The shape should display as you are dragging.
 *  (5) When released, the shape will stay on the screen.
 *  (6) A clear button will clear everything on the screen.
 *  (7) Any other awesome stuff you can think of!
 * 
 * @author [YOUR NAME(S) HERE!]
 *
 */
@SuppressWarnings("serial")
public class EPGYDraw extends GraphicsProgram {
	
	/* 
	 * ********************************************************
	 *                      VARIABLES                         *
	 * ******************************************************** 
	 */
	
	Dimension screenSize;
	
	JRadioButton ellipseButton;
	JRadioButton rectangleButton;
	JRadioButton lineButton;
	JRadioButton roundedRectButton;
	JRadioButton clearButton;
	
	private Point start;
	
	private GOval oval;
	private GRect rect;
	private GLine line;
	private GRoundRect roundRect;
	
	String selected; // save which one is drawn on screen 
	
	/* 
	 * ********************************************************
	 *                       METHODS                          *
	 * ******************************************************** 
	 */
	
	public static void main(String[] args) {
		new EPGYDraw().start(args);
	}
	
	/**
	 * Put any setup code here!
	 */
	@Override
	public void init() {
		start = new Point();
		
		setTitle("EPGYDraw!");
		addButtons();
		addMouseListeners();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (ellipseButton.isSelected()) {
			selected = "ellipse";
		} else if (rectangleButton.isSelected()) {
			selected = "rectangle";
		} else if (lineButton.isSelected()) {
			selected = "line";
		} else if (roundedRectButton.isSelected()) {
			selected = "roundedRectangle";
		} else if (clearButton.isSelected()) {
			removeAll();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		// Make your new GOval
		start = e.getPoint();
		if (selected == "ellipse") {
			oval = new GOval(start.x, start.y, 0, 0);
			add(oval);
		} else if (selected == "rectangle") {
			rect = new GRect(start.x, start.y, 0, 0);
			add(rect);
		} else if (selected == "line") {
			line = new GLine(start.x, start.y, start.x, start.y);
			add(line);
		} else if (selected == "roundedRectangle") {
			roundRect = new GRoundRect(start.x, start.y, 0, 0, 50);
			add(roundRect);
		}
	}
	
	
	public void mouseDragged(MouseEvent e) {
		if (selected == "ellipse") {
			oval.setBounds(start.x, start.y, Math.abs(e.getX()-start.x), 
					Math.abs(e.getY()-start.y));
			oval.setFilled(true);
		} else if (selected == "rectangle") {
			rect.setBounds(start.x, start.y, Math.abs(e.getX()-start.x), 
					Math.abs(e.getY()-start.y));
			rect.setFilled(true);
		} else if (selected == "line") {
			line.setStartPoint(start.x, start.y);
			line.setEndPoint(e.getX(), e.getY());
		} else if (selected == "roundedRectangle") {
			roundRect.setBounds(start.x, start.y, Math.abs(e.getX()-start.x), 
					Math.abs(e.getY()-start.y));
			roundRect.setFilled(true);
		}
	}
	
	
	public void addButtons() {
		ellipseButton = new JRadioButton("Ellipse");
		ellipseButton.addActionListener(this);
		add(ellipseButton, WEST);
		
		rectangleButton = new JRadioButton("Rectangle");
		rectangleButton.addActionListener(this);
		add(rectangleButton, WEST);
		
		lineButton = new JRadioButton("Line");
		lineButton.addActionListener(this);
		add(lineButton, WEST);
		
		roundedRectButton = new JRadioButton("Rounded Rectangle");
		roundedRectButton.addActionListener(this);
		add(roundedRectButton, WEST);
		
		clearButton = new JRadioButton("Clear");
		clearButton.addActionListener(this);
		add(clearButton, WEST);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(ellipseButton);
		bg.add(rectangleButton);
		bg.add(lineButton);
		bg.add(roundedRectButton);
		bg.add(clearButton);
	}
	
	private void addWelcomeMessage() {
		GLabel welcomeLabel = new GLabel("Welcome to EPGY Draw!", 10, 20);
		//System.out.println(screenSize.getHeight()/2);
		welcomeLabel.setColor(Color.BLACK);
		add(welcomeLabel);
	}
	
	
	
	/**
	 * Put your program run code here! Most of your code should be in here.
	 */
	@Override
	public void run() {
		addWelcomeMessage();
		waitForClick();
	}
}
