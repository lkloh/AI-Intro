import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A representation of a graph node.
 * 
 * @author Lekan Wang (lekan@lekanwang.com)
 *
 */
public class Node implements Serializable{

	private static final long serialVersionUID = 7659857558749627122L;
	
	private List<Edge> edges;
	String name;
	
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
	public Node(String name) {
		this();
		this.name = name;
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
