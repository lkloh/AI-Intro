import java.awt.Color;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.*;
import java.util.*;


import javax.swing.JButton;
import javax.swing.JTextField;

import acm.graphics.*;
import acm.program.*;

/**
 * Who needs Google Maps when you have Pathfinder?
 * 
 * @author Ben Holtz
 *
 */
public class Pathfinder extends GraphicsProgram implements PathfinderConstants {
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                     CONSTANTS                              *
	 *                                                                            *
	 * ****************************************************************************
	 */

	enum Mode {
		EDIT, DEST, WAY, EDGE, SELECT
	}

	// Constants
	public static final int APPLICATION_WIDTH = 1000;
	public static final int APPLICATION_HEIGHT = 620;

	// setup
	private Mode mode;
	private GLabel editLabel;
	private GLine currentLine = null;
	private GObject clicked = null;
	private GObject released = null;
	private JButton editButton, waypointButton, destinationButton, edgeButton, saveButton, loadButton;
	private JButton DFSButton, BFSButton, DijkstrasButton;
	private JTextField filename;
	

	private Map<Node, List<Edge>> nodeMap = new HashMap<Node, List<Edge>>();
	private Map<GOval, Node> ovalToNode= new HashMap<GOval, Node>();
	
	private Node startNode;
	private Node finishNode;
	
	private int numWaypoints = 0;
	private int numDestinations = 0;
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                   RUN FUNCTIONS                            *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
	public static void main(String[] args) {
		new Pathfinder().start(args);
	}
	
	@Override
	public void run() {
	}
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                              INITIALIZE INTERFACE                          *
	 *                                                                            *
	 * ****************************************************************************
	 */

	@Override
	public void init() {
		mode = Mode.SELECT;
		setupMap();
		setupLabels();
		setupButtons();
		addAlgorithmButtons();
		setTitle("Ben's Pathfinder");
		addMouseListeners();
	}
	
	private void setupMap() {
		GImage image = new GImage("Stanfordmap-1000x618.png");
		add(image);
	}

	private void setupLabels() {
		editLabel = new GLabel("Edit mode");
		editLabel.setColor(Color.RED);
		editLabel.setLocation(PADDING, PADDING + editLabel.getAscent());
	}
	
	private void addAlgorithmButtons() {
		DFSButton = new JButton(DFS_BUTTON);
		DFSButton.addActionListener(this);
		add(DFSButton, WEST);
		
		BFSButton = new JButton(BFS_BUTTON);
		BFSButton.addActionListener(this);
		add(BFSButton, WEST);
		
		DijkstrasButton = new JButton(DIJKSTRAS_BUTTON);
		DijkstrasButton.addActionListener(this);
		add(DijkstrasButton, WEST);
	}

	private void setupButtons() {
		editButton = new JButton(EDIT_BUTTON);
		editButton.addActionListener(this);
		add(editButton, SOUTH);
		
		waypointButton = new JButton(WAYPOINT_BUTTON);
		waypointButton.setEnabled(true);
		waypointButton.addActionListener(this);
		add(waypointButton, SOUTH);
		
		destinationButton = new JButton(DESTINATION_BUTTON);
		destinationButton.setEnabled(true);
		destinationButton.addActionListener(this);
		add(destinationButton, SOUTH);
		
		edgeButton = new JButton(EDGE_BUTTON);
		edgeButton.addActionListener(this);
		edgeButton.setEnabled(true);
		add(edgeButton, SOUTH);
		
		filename = new JTextField(TEXT_WIDTH);
		add(filename, NORTH);
		saveButton = new JButton(SAVE_BUTTON);
		saveButton.addActionListener(this);
		add(saveButton, NORTH);
		
		loadButton = new JButton(LOAD_BUTTON);
		loadButton.addActionListener(this);
		add(loadButton, NORTH);
	}


	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                  MOUSE EVENTS                              *
	 *                                                                            *
	 * ****************************************************************************
	 */

	@Override
	public void mousePressed(MouseEvent e) {
		switch (mode) {
		case EDGE:
			clicked = getElementAt(e.getX(), e.getY());
			if (clicked instanceof GOval) {
				double startX = clicked.getX() + clicked.getWidth() / 2;
				double startY = clicked.getY() + clicked.getHeight() / 2;
				currentLine = new GLine(startX, startY, e.getX(), e.getY());
				add(currentLine);
			}
			else clicked = null;
			break;
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (mode) {
		case EDGE:
			if (currentLine != null) {
				currentLine.setEndPoint(e.getX(), e.getY());
				currentLine.sendToBack();
				currentLine.sendForward();
			}
			break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (mode) {
		case EDGE:
			released = getElementAt(e.getX(), e.getY());
			if (currentLine != null) {
				if(released instanceof GOval) {
					double endX = released.getX() + released.getWidth() / 2;
					double endY = released.getY() + released.getHeight() / 2;
					currentLine.setEndPoint(endX, endY);
					addEdge(clicked, released);
				}
				else 
					remove(currentLine);
			}
			clicked = null;
			released = null;
			currentLine = null;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (mode) {
		case WAY:
			addWaypoint(e.getX(), e.getY());
			break;
		case DEST:
			addDestination(e.getX(), e.getY());
			break;
		default:
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//editing?
		if (e.getActionCommand().equals(EDIT_BUTTON)) editClick();
		
		//building map
		else if (e.getActionCommand().equals(DESTINATION_BUTTON)) destinationClick();
		else if (e.getActionCommand().equals(WAYPOINT_BUTTON)) waypointClick();
		else if (e.getActionCommand().equals(EDGE_BUTTON)) edgeClick();
		
		//algorithms
		else if (e.getActionCommand().equals(BFS_BUTTON)) breathFirstSearch(); 
		else if (e.getActionCommand().equals(DFS_BUTTON)) depthFirstSearch();
		else if (e.getActionCommand().equals(DIJKSTRAS_BUTTON)) dijkstrasAlgorithm();
		
		//saving graph
		//else if (e.getActionCommand().equals(SAVE_BUTTON)) saveGraph();
		//else if (e.getActionCommand().equals(LOAD_BUTTON)) loadGraph();
	}
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                  BUILDING MAP                              *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
	private GOval addOval(Point2D point, double size, Color color) {
		GOval oval = new GOval(size, size);
		oval.setLocation(point.getX() - size/ 2, point.getY() - size/ 2);
		oval.setColor(color);
		oval.setFilled(true);
		add(oval);
		return oval;
	}
	
	/*
	 * -------------------------------------------------------------------------- *
	 *                                   EDGE                                     *
	 * -------------------------------------------------------------------------- *
	 */

	private void addEdge(GObject start, GObject finish) {
		Node n1 = ovalToNode.get(start);
		Node n2 = ovalToNode.get(finish);
		Edge edge = new Edge(n1, n2);
		n1.addEdge(edge);
		n2.addEdge(edge);
		
		//add to nodeMap
		List<Edge> n1Set = nodeMap.get(n1);
		n1Set.add(edge);
		
		List<Edge> n2Set = nodeMap.get(n2);
		n2Set.add(edge);
	}
	
	/*
	 * -------------------------------------------------------------------------- *
	 *                                   EDGE                                     *
	 * -------------------------------------------------------------------------- *
	 */
	
	/*
	 * -------------------------------------------------------------------------- *
	 *                              DESTINATION                                   *
	 * -------------------------------------------------------------------------- *
	 */

	private void addDestination(double x, double y) {
		Point2D point = new Point2D.Double(x,y);
		
		GOval oval = addOval(point, DESTINATION_SIZE, DESTINATION_COLOR);
		
		numDestinations++;
		String nodeName = "Destination-" + numDestinations;
		
		Node node = new Node(nodeName, point);
		ovalToNode.put(oval, node);
		
		List<Edge> edges = new ArrayList<Edge>();
		nodeMap.put(node, edges);
		
		if (numDestinations == 1) startNode = node;
		if (numDestinations == 2) finishNode = node;
	}
	
	/*
	 * -------------------------------------------------------------------------- *
	 *                                  DESTINATION                               *
	 * -------------------------------------------------------------------------- *
	 */

	/*
	 * -------------------------------------------------------------------------- *
	 *                                  WAYPOINT                                   *
	 * -------------------------------------------------------------------------- *
	 */
	
	private void addWaypoint(double x, double y) {
		Point2D point = new Point2D.Double(x,y);
		
		GOval oval = addOval(point, WAYPOINT_SIZE, WAYPOINT_COLOR);
		
		numWaypoints++;
		String waypointName = "Waypoint-"+numWaypoints;
		
		//add node
		Node node = new Node(waypointName, point);
		ovalToNode.put(oval, node);
		
		List<Edge> edges = new ArrayList<Edge>();
		nodeMap.put(node, edges);
	}

	
	/*
	 * -------------------------------------------------------------------------- *
	 *                                 WAYPOINT                                   *
	 * -------------------------------------------------------------------------- *
	 */
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                  BUILDING MAP                              *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                               SAVE/LOADING GRAPH                           *
	 *                                                                            *
	 * ****************************************************************************
	 */

	private void saveGraph() {
		
	}

	private void loadGraph() {
		
	}

	private void clearGraph() {
		nodeMap.clear();
		numDestinations = 0;
		numWaypoints = 0;
		Iterator<?> iter = iterator();
		List<GObject> toRemove = new ArrayList<GObject>();
		while (iter.hasNext()) {
			Object obj = iter.next();
			if (obj instanceof GRect || obj instanceof GLine) toRemove.add((GObject) obj);
		}
		for (int i = 0; i < toRemove.size(); i++) remove(toRemove.get(i));
	}
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                               SAVE/LOADING GRAPH                           *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                BUILDING GRAPH                              *
	 *                                                                            *
	 * ****************************************************************************
	 */

	private void edgeClick() {
		mode = Mode.EDGE;
	}

	private void waypointClick() {
		mode = Mode.WAY;
	}

	private void destinationClick() {
		mode = Mode.DEST;
	}

	private void editClick() {
		switch (mode) {
		case SELECT:
			mode = Mode.EDIT;
			toggleEditLabel();
			break;
		case EDIT: case WAY: case DEST: case EDGE:
			mode = Mode.SELECT;
			toggleEditLabel();
			break;
		}
	}

	private void toggleEditLabel() {
		if (mode == Mode.SELECT) {
			remove(editLabel);
			destinationButton.setEnabled(false);
			waypointButton.setEnabled(false);
			edgeButton.setEnabled(false);
			System.out.println("select mode");
			repaint();
		} 
		if (mode == Mode.EDIT) {
			System.out.println("Mode: "+mode);
			add(editLabel);
			destinationButton.setEnabled(true);
			waypointButton.setEnabled(true);
			edgeButton.setEnabled(true);
			repaint();
		}
	}
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                BUILDING GRAPH                              *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                  ALGORITHMS                                *
	 *                                                                            *
	 * ****************************************************************************
	 */
	

	
	/* 
	 * -------------------------------------------------------------------------- *
	 *                           BREATH-FIRST SEARCH                              *
	 * -------------------------------------------------------------------------- *
	 */
	
	private void breathFirstSearch() {
		Set<Node> visited = new HashSet<Node>();
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(startNode);
		visited.add(startNode);
		while (!queue.isEmpty()) {
			Node node = queue.remove();
			if (!node.equals(finishNode)) {
				List<Edge> edges = nodeMap.get(node);
				for (int i=0; i<edges.size(); i++) {
					Edge edge = edges.get(i);
					Node u = edge.getNode2();
					if (!visited.contains(u)) {
						visited.add(u);
						queue.add(u);
					}
				}
			}
		}
	}
	
	/* 
	 * -------------------------------------------------------------------------- *
	 *                            DEPTH-FIRST SEARCH                              *
	 * -------------------------------------------------------------------------- *
	 */
	
	private void depthFirstSearch() {
		
	}
	
	/* 
	 * -------------------------------------------------------------------------- *
	 *                           DIJKSTRA'S ALGORITHM                             *
	 * -------------------------------------------------------------------------- *
	 */
	
	private void dijkstrasAlgorithm() {
		
	}
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                  ALGORITHMS                                *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                 COMPARATOR                                 *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
	public class compareDist implements Comparator<Edge> {
		
		public compareDist() {
			super();
		}
		
		public int compare(Edge e1, Edge e2) {
			if (e1.getDistance()-e2.getDistance() > 0) return 1;
			if (e1.getDistance()-e2.getDistance() < 0) return -1;
			return 0;
		}
	}
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                 COMPARATOR                                 *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
}
