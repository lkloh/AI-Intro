import java.util.ArrayList;

import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;


public class drawTriangles extends GraphicsProgram {
	
	private static final int NUM_LAYERS = 5;
	private static final int LENGTH = 50;


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new drawTriangles().start(args);
	}
	
	public void main() {
		
	}
	
	public void init() {
		ArrayList<GPoint> bottomPoints = new ArrayList<GPoint>();
		GPoint p = new GPoint(getWidth()/2, getHeight()/4);
		bottomPoints.add(p);
		
		for (int row=1; row<=NUM_LAYERS; row++) {
			ArrayList<GPoint> newList = new ArrayList<GPoint>();
			for (int i=0; i<row; i++) {
				GPoint pt = bottomPoints.get(i);
				GPoint p1 = drawPolarLine(pt, LENGTH, 2*Math.PI/3);
				GPoint p2 = drawPolarLine(pt, LENGTH, Math.PI/3);
				if (i < row-1) drawPolarLine(pt, LENGTH, 0);
				if (i==0) {
					newList.add(p1);
					newList.add(p2);
				} else {
					newList.add(p2);
				}
			}
			bottomPoints = newList;
		}
		
		//draw bottom line for last layer
		for (int i=0; i<NUM_LAYERS; i++) {
			GPoint pt = bottomPoints.get(i);
			drawPolarLine(pt, LENGTH, 0);
		}
	}

	
	private GPoint drawPolarLine(GPoint p1, double length, double theta) {
		GPoint p2 = new GPoint(p1.getX()+length*Math.cos(theta),
				p1.getY()+length*Math.sin(theta));
		GLine line = new GLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		add(line);
		return p2;
	}

}
