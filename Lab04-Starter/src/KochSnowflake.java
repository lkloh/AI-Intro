import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.Point2D;

import acm.graphics.*;
import acm.program.*;

/**
 * Starter file for Koch Snowflake application.
 * @author Lekan Wang (lekan@lekanwang.com)
 *
 */
public class KochSnowflake extends GraphicsProgram {
	
	private static final int ORDER = 3;
	private static final int LENGTH = 200;
	private static final double ANGLE = Math.PI/3;
	
	public static void main(String[] args) {
		new KochSnowflake().start(args);
	}
	
	private Point2D drawPolarLine(Point2D pt, double len, double theta) {
		Point2D pt2 = new Point2D.Double(pt.getX()+len*Math.cos(theta), 
				pt.getY()+len*Math.sin(theta));
		GLine line = new GLine(pt.getX(), pt.getY(), pt2.getX(), pt2.getY());
		add(line);
		return pt2;
	}
	

	private Point2D subdivideTriangleSide(Point2D startPt, double len, double theta, int order) {
		if (order == 0) {
			return drawPolarLine(startPt, len, theta);
		} else {
			Point2D pt1 = subdivideTriangleSide(startPt, len/3, theta, order-1);
			Point2D pt2 = subdivideTriangleSide(pt1, len/3, theta+Math.PI/3, order-1);
			Point2D pt3 = subdivideTriangleSide(pt2, len/3, theta-Math.PI/3, order-1);
			return subdivideTriangleSide(pt3, len/3, theta, order-1);
		}
	}
	
	@Override
	public void init() {
		setTitle("Koch Snowflake");
		GLabel label = new GLabel("Let it snow, let it snow, let it snow!", 10, 100);
		add(label);
	}
	
	private void drawOval(Point2D pt) {
		GOval oval = new GOval(pt.getX(), pt.getY(), 10, 10);
		oval.setFilled(true);
		add(oval);
	}
	
	@Override
	public void run() {
		GOval oval = new GOval(getWidth()/2, getHeight()/2, 20,20);
		//add(oval);
		
		Point2D pt = new Point2D.Double(getWidth()/2-LENGTH*Math.cos(Math.PI/6)/2, 
				getHeight()/2+LENGTH*Math.sin(Math.PI/6));
		
		pt = subdivideTriangleSide(pt, LENGTH, 0, 2);
		pt = subdivideTriangleSide(pt, LENGTH, -2*Math.PI/3, 2);
		pt = subdivideTriangleSide(pt, LENGTH, 2*Math.PI/3, 2);
	}
}
