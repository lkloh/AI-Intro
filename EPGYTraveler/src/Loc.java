import java.awt.Color;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GPoint;

public class Loc {
	
	private GPoint p;
	private GOval oval;
	private Color color = Color.black;
	private boolean visited = false;

	
	public Loc(GPoint p, Color color, boolean visited) {
		this.p = p;
		this.color = color;
		this.visited = visited;
		
		oval = new GOval(p.getX(), p.getY(), 5, 5);
		oval.setFilled(true);
		oval.setColor(this.color);
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public boolean getVisited() {
		return this.visited;
	}
	
	public GOval getObj() {
		return this.oval;
	}
	
	
	public GPoint getLoc() {
		return this.p;
	}

	public void setColor(Color color) {
		this.color = color;
		oval.setColor(color);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public double difference(Loc other) {
		GPoint po = other.getLoc();
		return Math.sqrt(Math.pow(p.getX()-po.getX(),2)+
				Math.pow(p.getY()-po.getY(),2));
	}
	
	public boolean isEqual(Loc other) {
		return p.equals(other.getLoc());
	}
	
	public GLine getLine(Loc l1, Loc l2) {
		GLine line = new GLine(l1.getLoc().getX(), l1.getLoc().getY(),
				l2.getLoc().getX(), l2.getLoc().getY());
		line.setColor(Color.RED);
		return line;
	}
	
}
