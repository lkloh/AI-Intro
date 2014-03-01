import java.awt.geom.Point2D;
import java.io.Serializable;

/**
 * A representation of a graph node.
 * 
 * @author Lekan Wang (lekan@lekanwang.com)
 *
 */
public class Edge implements Serializable {
	
	private static final long serialVersionUID = 1575161649565464886L;
	
	private Node n1, n2;
	
	/**
	 * Creates an edge that connects nodes n1 and n2.
	 */
	public Edge(Node n1, Node n2) {
		this.n1 = n1;
		this.n2 = n2;
	}
	
	/**
	 * Gets both nodes as a Pair<Node> object. You can access
	 * the individual Nodes by saying pair.left, and pair.right
	 * once you have the pair.
	 * 
	 * This method does not guarantee any ordering on the nodes.
	 * Hence, this could be useful when working with undirected
	 * graphs. (*hint hint*)
	 * 
	 * @return
	 */
	public Pair<Node> getNodes() {
		return new Pair<Node>(n1, n2);
	}
	
	/**
	 * Returns the first node. Because this guarantees an order,
	 * it could be useful when working with directed graphs.
	 * 
	 * @return
	 */
	public Node getNode1() {
		return this.n1;
	}
	
	/**
	 * Returns the second node. Because this guarantees an order,
	 * it could be useful when working with directed graphs.
	 * 
	 * @return
	 */
	public Node getNode2() {
		return this.n2;
	}
	
	public double getDistance() {
		Point2D point1 = n1.getCoordinates();
		Point2D point2 = n2.getCoordinates();
		return Math.sqrt(Math.pow(point1.getX()-point2.getX(), 2)
				+ Math.pow(point1.getX()-point2.getY(), 2));
	}
}
