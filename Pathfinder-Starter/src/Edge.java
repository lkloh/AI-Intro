/**
 * A representation of a graph node.
 * 
 * @author Lekan Wang (lekan@lekanwang.com)
 *
 */
public class Edge {
	
	private Node n1, n2;
	// TODO You will probably want to add more stuff here.
	
	/**
	 * Creates an edge that connects nodes n1 and n2.
	 */
	public Edge(Node n1, Node n2) {
		
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
}
