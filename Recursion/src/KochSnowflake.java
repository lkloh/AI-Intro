import acm.graphics.*;
import acm.program.*;

/**
 * Starter file for Koch Snowflake application.
 * @author Lekan Wang (lekan@lekanwang.com)
 *
 */
public class KochSnowflake extends GraphicsProgram {
	
	private static final int LENGTH = 200;
	private static final int DEPTH = 7;
	
	public static final int APPLICATION_WIDTH = 800;
	public static final int APPLICATION_HEIGHT = 700;
	
	public static void main(String[] args) {
		new KochSnowflake().start(args);
	}
	
	/*
	 *          p3
	 *          %
	 *        %   %
	 *      %       %
	 *    %           %
	 *  % % % % % % % % %
	 *  p1              p2        
	 */
	
	@Override
	public void init() {
		setTitle("Koch Snowflake");
		GLabel label = new GLabel("Let it snow, let it snow, let it snow!", 10, 100);
		add(label);
		
		//draw triangle
		GPoint p1 = new GPoint(getWidth()/2-LENGTH/2, getHeight()/2+LENGTH/(2*Math.sqrt(3)));

		GPoint p2 = drawSide(DEPTH, LENGTH, p1, 0);
		GPoint p3 = drawSide(DEPTH, LENGTH, p2, -2*Math.PI/3);
		drawSide(DEPTH, LENGTH, p3, 2*Math.PI/3);
	}
	
	private GPoint drawPolarLine(GPoint p1, double length, double theta) {
		GPoint p2 = new GPoint(p1.getX()+length*Math.cos(theta),
				p1.getY()+length*Math.sin(theta));
		GLine line = new GLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
		add(line);
		return p2;
	}
	
	 
	/* start                   end
	 * =                        =
	 * p1   p2           p4     p5
	 * % % % %            % % % %
	 *         %        %
	 *            %   %
	 *              %
	 *             p3
	 */
	GPoint drawSide(int depth, double length, GPoint pt, double theta) {
		if (depth == 0) {
			return drawPolarLine(pt, length, theta);
		} else {
			GPoint p2 = drawSide(depth-1, length/3, pt, theta);
			GPoint p3 = drawSide(depth-1, length/3, p2, theta+Math.PI/3);
			GPoint p4 = drawSide(depth-1, length/3, p3, theta-Math.PI/3);
			return drawSide(depth-1, length/3, p4, theta);
		}
	}
	
	private void addPoint(GPoint p) {
		GOval oval = new GOval(p.getX(), p.getY(), 5, 5);
		oval.setFilled(true);
		add(oval);
	}
	
	@Override
	public void run() {
		
	}
}
