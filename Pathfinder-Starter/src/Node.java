import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A representation of a graph node.
 * 
 * @author Lekan Wang (lekan@lekanwang.com)
 *
 */
public class Node {
	private List<Edge> edges;
	double value; // An example of a value stored in the node.
	String name; //name of destination, null for waypoint
	Point p; // store coordinates of note
	
	/**
	 * Creates an empty node with no edges, and a default 0 value;
	 */
	public Node() {
		this.edges = new ArrayList<Edge>();
	}
	
	/**
	 * Creates a node with no edges, and the given value.
	 * @param value
	 */
	public Node(double value, String name, Point p) {
		this();
		this.value = value;
		this.name = name;
		this.p = p;
	}
	
	
	/**
	 * Adds the given edge to this node. The edge must be not null
	 * and valid.
	 * 
	 * @param edge
	 */
	public void addEdge(Edge edge) {
		assert (edge != null);
		edges.add(edge);
	}
	
	/**
	 * Gets the list of edges as an unmodifiable list.
	 */
	public List<Edge> getEdges() {
		return Collections.unmodifiableList(edges);
	}
	
}
