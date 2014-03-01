import java.awt.Color;
import java.awt.event.*;
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

	// Instance variables
	private Mode mode;
	private GLabel editLabel;
	private GLine currentLine = null;
	private GObject clicked = null;
	private GObject released = null;
	private JButton editButton, waypointButton, destinationButton, edgeButton, saveButton, loadButton;
	private JButton DFSButton, BFSButton, DijkstrasButton;
	private JTextField filename;
	
	private Map<GObject, Node> nodeMap = new HashMap<GObject, Node>();
	private Map<Edge, Pair<GObject>> edges = new HashMap<Edge, Pair<GObject>>();
	
	private GObject startNode;
	private GObject finishNode;
	
	private int numDestinations = 0;
	private int numWaypoints = 0;

	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                     CONSTANTS                              *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
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
	 *                                   RUN FUNCTIONS                            *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
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
		setupLabels();
		setupButtons();
		addAlgorithmButtons();
		setupMap();
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
	 *                              INITIALIZE INTERFACE                          *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
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
			if (clicked instanceof GRect) {
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
				if(released instanceof GRect) {
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
		if (e.getActionCommand().equals(EDIT_BUTTON)) editClick();
		else if (e.getActionCommand().equals(DESTINATION_BUTTON)) destinationClick();
		else if (e.getActionCommand().equals(WAYPOINT_BUTTON)) waypointClick();
		else if (e.getActionCommand().equals(EDGE_BUTTON)) edgeClick();
		//else if (e.getActionCommand().equals(SAVE_BUTTON)) saveGraph();
		//else if (e.getActionCommand().equals(LOAD_BUTTON)) loadGraph();
	}
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                  MOUSE EVENTS                              *
	 *                                                                            *
	 * ****************************************************************************
	 */
	
	/* 
	 * ****************************************************************************
	 *                                                                            *
	 *                                  BUILDING MAP                              *
	 *                                                                            *
	 * ****************************************************************************
	 */

	private void addEdge(GObject start, GObject finish) {
		Node n1 = nodeMap.get(start);
		Node n2 = nodeMap.get(finish);
		Edge edge = new Edge(n1, n2);
		n1.addEdge(edge);
		n2.addEdge(edge);
		edges.put(edge, new Pair<GObject>(start, finish));
	}

	private void addDestination(double x, double y) {
		GRect node = new GRect(DESTINATION_SIZE, DESTINATION_SIZE);
		node.setLocation(x - DESTINATION_SIZE / 2, y - DESTINATION_SIZE / 2);
		node.setColor(DESTINATION_COLOR);
		node.setFilled(true);
		addNode(node, nextDestinationName());
		if (numDestinations == 1) startNode = node;
		if (numDestinations == 2) finishNode = node;
	}

	private String nextDestinationName() {
		String name = "Destination-" + numDestinations;
		numDestinations++;
		return name;
	}

	private void addWaypoint(double x, double y) {
		GRect node = new GRect(WAYPOINT_SIZE, WAYPOINT_SIZE);
		node.setLocation(x - WAYPOINT_SIZE / 2, y - WAYPOINT_SIZE / 2);
		node.setColor(WAYPOINT_COLOR);
		node.setFilled(true);
		addNode(node, nextWaypointName());
	}

	private void addNode(GObject node, String name) {
		nodeMap.put(node, new Node(name));
		add(node);
	}

	private String nextWaypointName() {
		String name = "Waypoint-" + numWaypoints;
		numWaypoints++;
		return name;
	}
	
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
		}
		else {
			add(editLabel);
			destinationButton.setEnabled(true);
			waypointButton.setEnabled(true);
			edgeButton.setEnabled(true);
		}
		repaint();
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
		System.out.println(startNode.toString());
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
	
	
}
