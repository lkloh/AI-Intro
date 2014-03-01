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
	
	
	/*
	 * ********************************************************************************
	 *                                                                                *
	 *                                    CONSTANTS                                   *                                                     
	 *                                                                                *
	 * ********************************************************************************
	 */

	/**
	 * When the button is clicked, cluster the pixels by the centers you have chosen, 
	 * then update the pixels array to match the cluster centers. My intention is for you 
	 * to implement k-means, but you are welcome to attempt HAC. Recall the steps involved
	 * in k-means:
	 * 
	 * 1. Group the pixels in the image by which center it is nearest to.
	 * 2. Recompute the centers as the average of the pixels in each group
	 * 3. Repeat this process until the centers are stable or you have performed
	 *    MAX_ITERATIONS. 
	 * 4. Update the pixels array so that pixels in the same cluster are the same color
	 * 5. The updateImage() method will redraw the currentImage using the pixels array
	 * 6. redrawCenters() just redraws the seed centers over the updated image 
	 * 
	 */
	private void clusterPixels() {
		int numClusters = centers.size();
		System.out.println(numClusters);
		int height = currentImage.getHeight();
		int width = currentImage.getWidth();
		
		int[][] clusterIdentity;
		
		//updateImage();
		//redrawCenters();
		for (int i=0; i<MAX_ITERATIONS; i++) {
			clusterIdentity = updateClusterIdentity(numClusters, height, width);
			sameClusterSameColor(numClusters, height, width, clusterIdentity);
			recomputeCenters(numClusters, height, width, clusterIdentity);
		}
		System.out.println("done");
		updateImage();
		//redrawCenters();
	}
	
	
	//stuff in the same cluster are of the same color
	private void sameClusterSameColor(int numClusters, int height, int width, 
			int[][] clusterIdentity) {
		for (int k=0; k<numClusters; k++) {
			Pixel cp = centers.get(k);
			for (int i=0; i<width; i++) {
				for (int j=0; j<height; j++) {
					if (clusterIdentity[i][j] == k) {
						Pixel newPixel = new Pixel(cp.getBlue(), cp.getGreen(), cp.getRed());
						pixels[i][j] = newPixel;
					}
				}
			}
		}
	}
	
	private void recomputeCenters(int numClusters, int height,
			int width, int[][] clusterIdentity) {
		for (int k=0; k<numClusters; k++) {
			double sumR = 0;
			double sumG = 0;
			double sumB = 0;
			int count = 0;
			//search all pixels for similar colors of that cluster
			for (int i=0; i<width; i++) {
				for (int j=0; j<height; j++) {
					Pixel p = pixels[i][j];
					if (clusterIdentity[i][j] == k) {
						sumR += p.getRed();
						sumG += p.getGreen();
						sumB += p.getBlue();
						count++;
					}
				}
			}
			
			if (count > 0) {
				double redColor = sumR/count;
				double greenColor = sumG/count;
				double blueColor = sumB/count;
				
				Pixel newPixel = new Pixel((int)redColor, (int)greenColor, (int)blueColor);
				System.out.println(newPixel.toString());
				centers.set(k, newPixel);
			}
		}
		System.out.println("");
	}
	
	// put points to clusters
	private int[][] updateClusterIdentity(int numClusters, int height, int width) {		
		int[][] clusterIdentity = new int[width][height];
		
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				Pixel p = pixels[i][j];
				double nearestDistance = Integer.MAX_VALUE;
				int clusterIs = -1;
				for (int k=0; k<numClusters; k++) {
					Pixel cp = centers.get(k);
					//System.out.println("centroid "+cp.toString());
					double dist = p.distance(cp);
					if (dist < nearestDistance) {
						nearestDistance = dist;
						clusterIs = k;
					}
				}
				clusterIdentity[i][j] = clusterIs;
			}
		}
		
		return clusterIdentity;
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
