import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;

import acm.graphics.*;
import acm.program.*;


public class EPGYTraveler extends GraphicsProgram {
	
	/* 
	 * *****************************************************************
	 *                            CONSTANTS                            *
	 * *****************************************************************
	 */
	
	public static final int APPLICATION_HEIGHT = 600;
	public static final int APPLICATION_WIDTH = 1000;
	
	/** buttons*/
	private JButton runDijkstra;
	private static final String DIJKSTRA_TEXT = "Run Dijkstra's Algorithm";
	
	private int numLocations = 0;
	
	private static ArrayList<Loc> locations = new ArrayList<Loc>();
	private HashSet<Loc> unvisitedNodes = new HashSet<Loc>();
	private Loc startNode;
	//private static HashMap<GPoint, GOval> pointToShape = new HashMap<GPoint, GOval>();
	
	/* 
	 * *****************************************************************
	 *                            CONSTANTS                            *
	 * *****************************************************************
	 */
	
	/* 
	 * *****************************************************************
	 *                              SET-UP                             *
	 * *****************************************************************
	 */

	public static void main(String[] args) {
		new EPGYTraveler().start(args);
	}
	
	public void run() {
		
	}
	
	public void init() {
		/** setup graphics */
		addMap();
		addButtons();
		/** listening in */
		addActionListeners();
		addMouseListeners();
	}
	
	private void addButtons() {
		runDijkstra = new JButton(DIJKSTRA_TEXT);
		add(runDijkstra, SOUTH);
		runDijkstra.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (numLocations > 0) runDijkstrasAlgorithm();
		
	}

	
	private void addMap() {
		GImage img = new GImage("worldmap.jpg");
		add(img);
	}
	
	/* 
	 * *****************************************************************
	 *                              SET-UP                             *
	 * *****************************************************************
	 */
	
	/* 
	 * *****************************************************************
	 *                           SET LOCATIONS                         *
	 * *****************************************************************
	 */
	
	public void mouseClicked(MouseEvent e) {
		System.out.println("clicked!");
		
		GPoint p = new GPoint(e.getX(), e.getY());
		
		Loc l = new Loc(p, Color.BLACK, false);
		
		GOval oval = l.getObj();
		add(oval);
		
		locations.add(l);
		if (numLocations == 0) startNode = l;
		if (numLocations > 0) unvisitedNodes.add(l);

		numLocations++;
	}
	
	
	/* 
	 * *****************************************************************
	 *                           SET LOCATIONS                         *
	 * *****************************************************************
	 */
	
	/* 
	 * *****************************************************************
	 *                         DIJKSTRAS ALGORITHM                     *
	 * *****************************************************************
	 */
	
	private void runDijkstrasAlgorithm() {
		double[][] adj = createWeightedAdjacencyMatrix();
		Loc current = locations.get(0);
		Loc destination = locations.get(numLocations-1);
		while (true) {
			double leastDistance = Integer.MAX_VALUE;
			Loc minNeighbor = new Loc(new GPoint(Integer.MAX_VALUE, Integer.MAX_VALUE),
					Color.BLACK, false);
			for (Loc l:unvisitedNodes) {
				if (current.difference(l) < leastDistance) {
					leastDistance = current.difference(l);
					minNeighbor = l;
				}
			}
			//update current node
			current.setVisited(true);
			current.setColor(Color.RED);
			GOval oval = current.getObj();
			add(oval);
			GLine line = current.getLine(current, minNeighbor);
			add(line);
			unvisitedNodes.remove(current);
			//update
			current = minNeighbor;
			if (minNeighbor.equals(destination) || leastDistance == Integer.MAX_VALUE) {
				destination.setColor(Color.RED);
				break; 
			}
		}
	}
	
	private double[][] createWeightedAdjacencyMatrix() {
		double[][] adj = new double[numLocations][numLocations];
		for (int row=0; row<numLocations; row++) {
			for (int col=0; col<numLocations; col++) {
				Loc l1 = locations.get(row);
				Loc l2 = locations.get(col);
				adj[row][col] = l1.difference(l2);
			}
		}
		return adj;
	}
	
	/* 
	 * *****************************************************************
	 *                         DIJKSTRAS ALGORITHM                     *
	 * *****************************************************************
	 */
	
}
