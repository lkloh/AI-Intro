import java.awt.*;
import java.util.List;
import java.util.*;

import acm.program.*;
import acm.graphics.*;

/*
 *  http://www.cs.princeton.edu/courses/archive/spr10/cos226/assignments/8puzzle.html
 *  Solvable: http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html
 */

public class EightPuzzle extends GraphicsProgram {
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                                  CONSTANTS                                *
	 *                                                                           *
	 * ***************************************************************************
	 */

	public static final int APPLICATION_HEIGHT = 800;
	public static final int APPLICATION_WIDTH = 800;
	private static final int ROWS = 3;
	private static final double SIDE = 150;
	private static final double OFFSET = (APPLICATION_HEIGHT - ROWS * SIDE) / 2;
	private static final int FONT_SIZE = 60;
	private static final int INIT_CAPACITY = 1000;

	private State state;
	private List<State> states;
	private long numSteps;
	private GLabel label = new GLabel("");
	private GRect blackOut;
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                                  CONSTANTS                                *
	 *                                                                           *
	 * ***************************************************************************
	 */
	
	private static void testCode() {
		State state = new State();
		boolean isSolvable = state.isSolvable();
		System.out.println("is solvable: " + isSolvable);
		state.printState();
	}
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                                 RUNNING                                   *
	 *                                                                           *
	 * ***************************************************************************
	 */

	public static void main(String[] args) {
		//testCode();
		new EightPuzzle().start(args);
	}

	@Override
	public void init() {
		state = new State();
		displayState(new State());
		computeMoves();
	}
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                                 RUNNING                                   *
	 *                                                                           *
	 * ***************************************************************************
	 */
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                      SEARCH ALGORITHM IMPLEMENTATION                      *
	 *                                                                           *
	 * ***************************************************************************
	 */

	/**
	 * My ugly implementation of A* search
	 * Note how I use the comparator to build a priority queue, then use the comparator class to 
	 * do heuristic evaluation. Also, I keep track of how many states are explored in my state 
	 * space before the goal is reached. Other than Java boilerplate, nothing in this code should 
	 * be mysterious; it is just graph search.
	 * 
	 * No point kicking out previous explored states: already stored inside explored.
	 */
	private void computeMoves() {
		Set<State> explored = new HashSet<State>();
		PriorityQueue<List<State>> frontier 
			= new PriorityQueue<List<State>>(INIT_CAPACITY, new StateCompare());
		ArrayList<State> init = new ArrayList<State>();
		init.add(state);
		frontier.add(init);
		State curState;
		List<State> curStates, tempStates;
		while (true) {
			numSteps++;
			curStates = frontier.poll();
			assert curStates != null;
			curState = curStates.get(curStates.size() - 1);
			if (curState.isGoal()) break;
			explored.add(curState);
			for (State state : curState.nextStates()) {
				if (!explored.contains(state)) { // don't get same state back
					tempStates = new ArrayList<State>(curStates);
					tempStates.add(state);
					frontier.offer(tempStates);
				}
			}
		}
		states = curStates;
	}
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                      SEARCH ALGORITHM IMPLEMENTATION                      *
	 *                                                                           *
	 * ***************************************************************************
	 */
	
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                              DISPLAYING ANSWERS                           *
	 *                                                                           *
	 * ***************************************************************************
	 */

	@Override
	public void run() {
		displayMoves();
		System.out.println("hello");
		for (int i = 0; i < states.size(); i++) {
			displayState(states.get(i));
			waitForClick();
		}
		//celebrate();
		System.out.println("DONE");
	}

	/**
	 * We solved the search problem! Time to have seizures!
	 */
	private void celebrate() {
		double dt = 0;
		double t = 0;
		while(true) {
			int r = (int) (255 * Math.abs(Math.sin(dt * dt)));
			int g = (int) (255 * Math.abs(Math.sin(dt * dt * 7 - 2 * dt)));
			int b = (int) (255 * Math.abs(Math.sin(2 * dt)));;
			blackOut.setColor(new Color(r, g, b));
			dt += 0.001;
			pause(20);
			t += dt;
			if (t == 60) break;
		}
	}

	/**
	 * Show how many states had to be explored before the goal was found
	 */
	private void displayMoves() {
		System.out.println("disp");
		label = new GLabel("Tooki " + numSteps + " steps");
		label.setColor(Color.RED);
		label.setLocation((APPLICATION_WIDTH - label.getWidth()) / 2, label.getHeight());
		add(label);
	}
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                              DISPLAYING ANSWERS                           *
	 *                                                                           *
	 * ***************************************************************************
	 */
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                                  GRAPHICS                                 *
	 *                                                                           *
	 * ***************************************************************************
	 */

	/**
	 * Given a state, update the display to reflect that state 
	 * @param state The current state of the puzzle
	 */
	private void displayState(State state) {
		removeAll();
		add(label);
		buildGrid();
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < ROWS; col++) {
				addNum(state.board[row][col], row, col);
			}
		}
	}

	/**
	 * Add a number to the grid in the specified row and column
	 * @param num
	 * @param col
	 * @param row
	 */
	private void addNum(int num, int col, int row) {
		if (num == -1) blackout(row, col);
		else {
			GLabel label = new GLabel("" + num);
			label.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
			label.setLocation(OFFSET + row * SIDE + (SIDE - label.getWidth()) / 2,
					OFFSET + col * SIDE + 
					(SIDE - label.getHeight() + label.getAscent() + label.getDescent()) / 2 );
			add(label);
		}
	}

	/**
	 * Add a black square to fill in the empty tile
	 * @param row The row that needs to be filled
	 * @param col The column that needs to be filled
	 */
	private void blackout(int row, int col) {
		blackOut = new GRect(SIDE, SIDE);
		blackOut.setFilled(true);
		blackOut.setColor(Color.BLACK);
		blackOut.setLocation(OFFSET + SIDE * row, OFFSET + SIDE * col);
		add(blackOut);
	}

	/**
	 * Redraw the grid
	 */
	private void buildGrid() {
		for (int row = 0; row <= ROWS; row++) {
			add(new GLine(OFFSET, OFFSET + row * SIDE, OFFSET + ROWS * SIDE, OFFSET + row * SIDE));
		}
		for (int col = 0; col <= ROWS; col++) {
			add(new GLine(OFFSET + col * SIDE, OFFSET, OFFSET + col * SIDE, OFFSET + ROWS * SIDE));
		}
	}
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                                  GRAPHICS                                 *
	 *                                                                           *
	 * ***************************************************************************
	 */
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                             COMPARATOR CLASS                              *
	 *                                                                           *
	 * ***************************************************************************
	 */
	
	/**
	 * This is a comparator class for paths through the 8 puzzle state space. 
	 * Note that lists of states are being compared, not just individual states
	 * @author benjaminholtz
	 *
	 */
	public class StateCompare implements Comparator<List<State>>{
		public StateCompare() {
			super();
		}
		
		@Override
		public int compare(List<State> o1, List<State> o2) {
			return eval(o1) - eval(o2);
		}
		
		/**
		 * Gives the estimated path cost for an optimal solution including this state.
		 * @param list The list of states in the path
		 * @return the estimated path cost for list
		 */
		private int eval(List<State> list) {
			return list.size() + heuristic(list.get(list.size() - 1));
		}

		/**
		 * The heuristic value of the given state
		 * @param state The current state of some path
		 * @return An optimistic estimate from this state to the goal
		 */
		private int heuristic(State state) {
			int heuristic = 0;	
			if (!state.isSolvable()) return Integer.MAX_VALUE;
			for (int row = 0; row < ROWS; row++) {
				for (int col = 0; col < ROWS; col++) {
					if (state.board[row][col] != State.GOAL[row][col]) heuristic++;
				}
			}
			return heuristic;
		}	
	}
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                             COMPARATOR CLASS                              *
	 *                                                                           *
	 * ***************************************************************************
	 */
    
}
