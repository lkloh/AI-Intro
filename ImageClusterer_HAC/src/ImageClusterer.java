import java.awt.Color;

import java.awt.event.*;
import java.util.*;

import javax.swing.JTextField;
import org.lekan.graphics.*;


/**
 * This program should allow us to supply the name of an image file, click to select
 * seed values, then cluster the pixels in the image. Remember the demos that we saw in 
 * class on Thursday.
 * 
 * @author Ben Holtz
 *
 */
public class ImageClusterer extends GraphicsProgram implements ImageClustererConstants {
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                    CONSTANTS                                   *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */

	/** The JTextField that is used for inputting a filename */
	private JTextField imageNameField;
	
	/** The image that is currently being displayed */
	private SGImage currentImage;
	
	/** The list of ellipses that are being used to show where the seed centers are */
	private List<SGEllipse> points = new ArrayList<SGEllipse>();
	
	/** The pixel values of the seed centers */
	private List<Pixel> centers = new ArrayList<Pixel>();
	
	/** The current image stored as pixels */
	private Pixel[][] pixels;
	
	private double[][] euclidean;
	
	private Pixel[] pixelsInColumn;
	
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                    CONSTANTS                                   *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */

	/**
	 * HAC Clustering
	 * 
	 * 1. Calculate similarity matrix
	 * 2. For each point, find the other point most similar to it,
	 * 	  and put them into a cluster
	 * n. For each cluster, find another cluster most similar to it,
	 *    and put them into a new, bigger cluster
	 * (end) Terminate when everything is in one cluster 
	 */
	private void clusterPixels() {
		int width = currentImage.getWidth();
		int height = currentImage.getHeight();
		pixelsInOneColumn(width, height);
		System.out.println(pixelsInColumn);
		euclideanMatrix(width, height);
		double[][] similarity = similarityMatrix(width, height);
		System.out.println(similarity);
	}
	
	/*
	 * Put all pixels of the image into one column
	 */
	private void pixelsInOneColumn(int width, int height) {
		pixelsInColumn = new Pixel[width*height];
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				pixelsInColumn[i*width+j] = pixels[i][j];
			}
		}
	}
	
	private void euclideanMatrix(int width, int height) {
		euclidean = new double[width*height][width*height];
		for (int i=0; i<width*height; i++) {
			for (int j=0; j<width*height; j++) {
				Pixel p1 = pixelsInColumn[i];
				Pixel p2 = pixelsInColumn[j];
				double dist = p1.distance(p2);
				euclidean[i][j] = dist;
			}
		}
	}

	private double[][] similarityMatrix(int width, int height) {
		double[][] similarity = new double[width*height][width*height];
		for (int i=0; i<width*height; i++) {
			for (int j=0; j<width*height; j++) {
				similarity[i][j] = Math.exp(-euclidean[i][j]);
			}
		}
		return similarity;
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                     MAIN                                       *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	
	public static void main(String[] args) {
		new ImageClusterer();
	}
	
	public void run() {

	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                     MAIN                                       *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                     SETUP                                      *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */

	public void setup() {
		this.getFrame().setTitle("Cluster an Image");
		this.getFrame().setDimensions(WINDOW_WIDTH, WINDOW_HEIGHT);
		setupTextField();
		setupButtons();
	}

	private void setupButtons() {
		this.addButton(CLEAR_TEXT, SOUTH);
		this.addButton(CLUSTER_TEXT, SOUTH);
	}

	private void setupTextField() {
		imageNameField = new JTextField(TEXT_FIELD_WIDTH);
		imageNameField.addKeyListener(this);
		this.addJComponent(imageNameField, SOUTH);
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                     SETUP                                      *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                            USER INTERACTION                                    *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(CLEAR_TEXT)) clearPoints();
		else if (command.equals(CLUSTER_TEXT)) clusterPixels();
	}

	public void mouseClicked(MouseEvent e) {
		if (currentImage != null) {
			int x = e.getX();
			int y = e.getY();
			if (x < currentImage.getWidth() && y < currentImage.getHeight()) 
				addPoint(x, y);
		}
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                            USER INTERACTION                                    *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                           INITIAL GRAPHICS SETUP                               *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	/**
	 * Clear the centers from the frame and the data structures maintaining them
	 */
	private void clearPoints() {
		for (SGEllipse point : points) this.getFrame().removeObject(point);
		points.clear();
		centers.clear();
	}
	
	/**
	 * Add and store the new seed center point
	 * @param x The x coordinate of the seed point
	 * @param y The y coordinate of the seed point
	 */
	private void addPoint(int x, int y) {
		this.getFrame().setForegroundColor(Color.MAGENTA);
		SGEllipse point = new SGEllipse(x, y, POINT_RADIUS, POINT_RADIUS);
		points.add(point);
		centers.add(new Pixel(currentImage.getPixel(x, y)));
		this.getFrame().addObject(point);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == RETURN_CODE) {
			displayImage(imageNameField.getText());
			resetPixels();
		}
	}

	/**
	 * Set the pixels container to hold the pixel values for the 
	 * currentImage
	 */
	private void resetPixels() {
		int height = currentImage.getHeight();
		int width = currentImage.getWidth();
		pixels = new Pixel[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Pixel p = new Pixel(currentImage.getPixel(i, j));
				p.setX(i);
				p.setY(j);
				pixels[i][j] = p;
			}
		}
	}

	/**
	 * Display the image with the given filename on the frame
	 * @param filename The name of the file with the image to be displayed
	 */
	private void displayImage(String filename) {
		currentImage = new SGImage(0, 0, filename);
		if (currentImage != null) {
			int width = currentImage.getWidth();
			int height = currentImage.getHeight();
			this.getFrame().setDimensions(width, height);
			this.getFrame().clearGraphics();
			this.getFrame().addObject(currentImage);
			clearPoints(); // We want a new set of seed centers too!
		}
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                           INITIAL GRAPHICS SETUP                               *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                             UPDATE CLUSTERS                                    *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
	/**
	 * Reset the currentImage to reflect the pixel data stored in pixels
	 */
	private void updateImage() {
		int height = currentImage.getHeight();
		int width = currentImage.getWidth();
		this.getFrame().removeObject(currentImage);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				currentImage.setPixel(i, j, pixels[i][j].getRGB());
			}
		}
		this.getFrame().addObject(currentImage);
	}
	
	/**
	 * Redraw the seed centers in the foreground.
	 */
	private void redrawCenters() {
		if (points != null) {
			for (SGEllipse point : points) {
				this.getFrame().removeObject(point);
				this.getFrame().addObject(point);
			}
		}
	}
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                             UPDATE CLUSTERS                                    *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */
	
}
