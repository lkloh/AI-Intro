import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JButton;

import acm.graphics.*;
import acm.program.*;

/**
 * Who needs Google Maps when you have Pathfinder?
 * 
 * @author [YOUR NAME HERE!]
 *
 */
public class Pathfinder extends GraphicsProgram {
	
	//edit mode 
	boolean editMode;
	GLabel EditModeLabel;
	
	public static void main(String[] args) {
		new Pathfinder().start(args);
	}
	
	// Constants
	public static final int APPLICATION_WIDTH = 1000;
	public static final int APPLICATION_HEIGHT = 620;
	public static final String IMAGE_NAME = "Stanfordmap-1000x618.png";
	
	//buttons
	public JButton buttonEdit;
	public JButton destinationButton;
	
	// [YOUR DATA STRUCTURES WILL GO HERE]
	
	@Override
	public void init() {
		// Setup code goes here
		setTitle("Pathfinder BETA!");

		editMode();
		
		addDestinationButton();
		
		addMap();
		addMouseListeners();
	}
	
	private void addDestinationButton() {
		destinationButton = new JButton("Add Destination");
		destinationButton.addActionListener(this);
		add(destinationButton, SOUTH);
	}
	
	private void editMode() {
		buttonEdit = new JButton("Edit");
		buttonEdit.addActionListener(this);
		add(buttonEdit, SOUTH);
	}
	
	/* add image of the map here */
	private void addMap() {
		GImage image = new GImage(IMAGE_NAME);
		add(image);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (editMode == false && e.getActionCommand()=="Edit") {
			//System.out.println("Hello");
			editMode = true;
			EditModeLabel = new GLabel("EDIT MODE", 50, 60);
			EditModeLabel.setColor(Color.RED);
			add(EditModeLabel);
		} else {
			editMode = false;
			//System.out.println("remove");
			remove(EditModeLabel);
		}
	}
	
	@Override
	public void run() {
		ComparingExample();
	}
	
	private void ComparingExample() {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Random r = new Random();
		GLabel sillyLabel = new GLabel(":D", r.nextDouble() * getWidth(), r.nextDouble() * getHeight());
		add(sillyLabel);
	}
}
