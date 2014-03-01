import java.awt.Color;


public interface PathfinderConstants {
	// Names
	static final String EDIT_BUTTON = "Edit mode";
	static final String WAYPOINT_BUTTON = "Add waypoint";
	static final String DESTINATION_BUTTON = "Add destination";
	static final String EDGE_BUTTON = "Add edges";
	static final String SAVE_BUTTON = "Save";
	static final String LOAD_BUTTON = "Load";
	
	static final String BFS_BUTTON = "Breath-First Search";
	static final String DFS_BUTTON = "Depth-First Search";
	static final String DIJKSTRAS_BUTTON = "Dijkstra's Algorithm";
	
	static final String DELIMITER = "######";
	
	static int INIT_CAPACITY = 10;
	
	// Graphics constants
	static final int TEXT_WIDTH = 15;
	static final double PADDING = 5;
	static final double WAYPOINT_SIZE = 8;
	static final double DESTINATION_SIZE = 10;
	static final Color WAYPOINT_COLOR = Color.BLACK;
	static final Color DESTINATION_COLOR = Color.BLUE;
}
