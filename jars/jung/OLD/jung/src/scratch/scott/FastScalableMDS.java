/*
 * Created on Dec 26, 2001
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package scratch.scott;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.visualization.AbstractLayout;

/**
 * @author Scott White
 *
 */
public class FastScalableMDS extends AbstractLayout {

	/**
	 * The diameter of the visible graph. In other words, length of
	 * the longest shortest path between any two vertices of the visible graph.
	 */
	protected double diameter;
	/**
	 * Stores graph distances between vertices of the visible graph
	 */
	protected UnweightedShortestPath unweightedShortestPaths;

	public FastScalableMDS(Graph g) {
		super(g);
	}

	public void advancePositions() {
		
	}

	public String getStatus() {
		return "";
	}

	/**
	 * Returns true once the current iteration has passed the maximum count.
	 */
	public boolean incrementsAreDone() {
		if (2 > 1) {
			return true;
		}
		return false;
	}

	protected void initialize_local() {
	}

	protected void initialize_local_vertex(Vertex v) {
	}

	protected void initializeLocations() {
		super.initializeLocations();
	
	}

	/**
	 * This one is an incremental visualization.
	 */
	public boolean isIncremental() {
		return true;
	}

	public void setMaxIterations(int maxIterations) {
		
	}
}
