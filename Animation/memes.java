import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;


public class memes extends GraphicsProgram {

	//constant pause time
	private static final int PAUSE_TIME = 20;
	
	//Basic screen settings
	public static final int APPLICATION_WIDTH = 405;
	public static final int APPLICATION_HEIGHT = 405;
	private GLabel topText;
	private GLabel bottomText;
	
	private static Random rand = new Random();
	
	
	public static void main(String[] args) {
		new memes().start(args);
	}

	//main code logic here:
	public void run() 
	{
		List<String> fileImages = getFileImages();
		GImage img = getRandomImage(fileImages);
		String topText = getUserInput("Enter top line: ");
		String bottomText = getUserInput("Enter bottom line: ");
		addGLabelToScreen(topText, img, 0.2);
		addGLabelToScreen(bottomText, img, 0.8);
	}
	
	private void addGLabelToScreen(String text, GImage img, double offset) {
		//where is the image?
		GPoint pt = img.getLocation();
		double width = img.getWidth();
		double height = img.getHeight();
		//make label
		GLabel label = new GLabel(text);
		label.setFont(new Font("Sans-serif", Font.BOLD, 30));
		label.setLocation(pt.getX()+width*offset-label.getWidth(), 
				pt.getY()+height*offset);
		add(label);
	}
	
	private String getUserInput(String message) {
		System.out.println(message);
		Scanner in = new Scanner(System.in);
		String str = in.nextLine();
		return str;
	}
	
	private GImage getRandomImage(List<String> fileImages) {
		int index = rand.nextInt(fileImages.size());
		GImage img = new GImage(fileImages.get(index));
		add(img);
		return img;
	}
	
	private List<String> getFileImages() {
		List<String> fileImages = new ArrayList<String>();
		File dir = new File("images");
		File[] files = dir.listFiles();
		for (int i=1; i<files.length; i++) {
			fileImages.add(files[i].getName());
		}
		return fileImages;
	}
	
	public void init() {
		
	}
	
}
