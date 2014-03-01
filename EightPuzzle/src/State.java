import java.util.*;

/**
 * The state class encodes a single state of the 8 puzzle
 * @author benjaminholtz
 *
 */
public class State {

	private static final int EMPTY = -1;
	public static final int[][] GOAL = {{1,2,3},{4,5,6},{7,8,EMPTY}};
	//public static final int[][] GOAL = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,EMPTY}};
	public static final int ROWS = 3;
	public static final int COLS = ROWS;

	final int[][] board;
	private int emptyRow, emptyCol;



	public State() {
		board = initBoard();
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == EMPTY) {
					emptyRow = row;
					emptyCol = col;
				}
			}
		}
	}
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                             IS SOLVABLE                                   *
	 *                                                                           *
	 * ***************************************************************************
	 */
	
	/**
	 * Is this state solvable?
	 * @return 
	 */
	public boolean isSolvable() {
		List<Integer> list = boardToArray();
		boolean parity = evenInversions(list);
		System.out.println("Parity: "+parity);
		if ((ROWS % 2) == 0) { //grid width even 
			return evenInversions(list) && blankOnOddRowFromBottom();
		}
		return evenInversions(list); // odd rows;
	}
	
	/**
	 * Already know its even number of rows
	 * @return
	 */
	private boolean blankOnOddRowFromBottom() {
		for (int row = ROWS-2; row >= 0; row = row-2) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] == EMPTY) return true;
			}
		}
		return false;
	}
	
	private boolean evenInversions(List<Integer> list) {
		int numInversions = 0;
		System.out.println(list);
		for (int i=0; i<list.size(); i++) {
			int currentInt = list.get(i);
			for (int j=i+1; j<list.size(); j++) {
				if (currentInt > list.get(j) && 
						currentInt != EMPTY && 
						list.get(j) != EMPTY) numInversions++;
			}
		}
		System.out.println("numInversions "+numInversions);
		return numInversions % 2 == 0;
	}
	
	private List<Integer> boardToArray() {
		List<Integer> list = new ArrayList<Integer>();
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				list.add(board[row][col]);
			}
		}
		return list;
	}
	
	/*
	 * ***************************************************************************
	 *                                                                           *
	 *                             IS SOLVABLE                                   *
	 *                                                                           *
	 * ***************************************************************************
	 */
	
	/**
	 * Print out the state saved in board
	 */
	public void printState() {
		System.out.println("====== STATE =======");
		for (int row = 0; row < ROWS; row++) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int col = 0; col < COLS; col++) {
				list.add(board[row][col]);
				System.out.println(board[row][col]);
			}
			//System.out.println(list);
		}
		System.out.println("====== STATE =======");
	}

	
	/**
	 * This is a copy constructor that allows for swapping positions in a state
	 * @param state The state to be copied
	 * @param row1 The row of the empty tile
	 * @param col1 The column of the empty tile
	 * @param row2 The row of the tile to be swapped
	 * @param col2 The column of the tile to be swapped
	 */
	private State(State state, int row1, int col1, int row2, int col2) {
		emptyRow = row2;
		emptyCol = col2;
		board = new int[ROWS][COLS];
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (row == row1 && col ==col1) board[row][col] = state.board[row2][col2];
				else if (row == row2 && col == col2) board[row][col] = state.board[row1][col1];
				else board[row][col] = state.board[row][col];
			}
		}
	}

	/**
	 * Create a new initial board for the search
	 * @return An initial board
	 */
	private int[][] initBoard() {
		// Here are some simple initial states to try out
		int[][] board = {{8, 3, 2},
				         {4, 5, EMPTY},
				         {7, 1, 6}};
		
		/*int[][] board = {{7, 1, 2},
					     {5, EMPTY, 9},
					     {8, 3, 6}};*/
		
		// This is the default initial state you should test with
//		int[][] board = {{1, 2, 3},
//                         {4, EMPTY, 5},
//                         {7, 8, 6}};
		//int[][] board = {{1,10,5,4},{7,14,6,3},{2,9,11,12},{EMPTY,8,13,15}};
		return board;
	}

	public boolean isGoal() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (board[row][col] != GOAL[row][col] ) return false;
			}
		}
		return true;
	}

	/**
	 * Given this state, find all possible swaps we can perform 
	 * @return A list of possible next states
	 */
	public List<State> nextStates() {
		List<State> next = new ArrayList<State>();
		if (emptyRow > 0) next.add(new State(this, emptyRow, emptyCol, emptyRow - 1, emptyCol)) ;
		if (emptyRow < ROWS - 1) next.add(new State(this, emptyRow, emptyCol, emptyRow + 1, emptyCol)) ;
		if (emptyCol > 0) next.add(new State(this, emptyRow, emptyCol, emptyRow, emptyCol - 1)) ;
		if (emptyCol < COLS - 1) next.add(new State(this, emptyRow, emptyCol, emptyRow, emptyCol + 1)) ;
		assert next.size() > 0;
		return next;
	}
	
	/**
	 * We need to override hashCode here so that we can use a hashset to store our explored
	 * nodes. Without this, we might not get proper hashing.
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < ROWS; i++) {
			hash ^= Arrays.hashCode(board[i]); 
		}
		return hash;
	}
	
	/**
	 * We override equals for the same reason as hashCode!
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof State)) return false;
		else {
			State other = (State) obj;
			for (int i = 0; i < 9; i++) {
				if (this.board[i / 3][i % 3] != other.board[i / 3][i % 3]) return false;
			}
			return true;
		}
	}
}
