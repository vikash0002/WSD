/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.danyel.sample;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.utils.Pair;
import edu.uci.ics.jung.utils.UserData;
import edu.uci.ics.jung.visualization.Layout;

/**
 * The CircleLayout package represents a visualization of a set of nodes.
 * CircleLayouts simply place all nodes in a circle around a center.
 * This is a rather trivial algorithm, and fails in a few cases.
 * In particular, it re-places nodes poorly.
 * [Possible: Steps attempt to swap pairs of vertices to improve the
 * layout]
 *
 *  @author danyelf
 */
public class CircleLayout implements Layout {

	protected Set dontmove;
	private static final Object CIRCLE_KEY =
		"edu.uci.ics.jung.Circle_Visualization_Key";

	private Object key = null;
	public Object getCircleKey() {
		if (key == null)
			key = new Pair(this, CIRCLE_KEY);
		return key;
	}

	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#getCurrentSize()
	 */
	public Dimension getCurrentSize() {
		return currentSize;
	}

	public Graph getGraph() {
		return graph;
	}

	public String getStatus() {
		return null;
	}

	public void initialize(Dimension d) {
		this.currentSize = d;
		resetLocations(graph, d);
		for (Iterator iter = graph.getEdges().iterator(); iter.hasNext();) {
			Edge e = (Edge) iter.next();
			CircleEdgeData eud = new CircleEdgeData(e);
			e.addUserDatum(CIRCLE_KEY, eud, UserData.REMOVE);
			//			calcEdgeLength(eud, f);
		}

	}

	protected Dimension currentSize;
	private Graph graph;
	private Graph showingGraph;

	//	/**
	//	 * Constructor for a SpringVisualizer for a raw graph with associated
	//	 * component. Defaults to the unit length function.
	//	 */
	//	public CircleVisualizer(Graph g, JComponent jc) {
	//		this(g, jc);
	//	}

	/**
	 * Constructor for a SpringVisualizer for a raw graph with associated
	 * dimension--the input knows how big the graph is. Defaults to the
	 * unit length function
	 */
	public CircleLayout(Graph g) {
		this.graph = g;
		this.showingGraph = g;
		this.dontmove = new HashSet();
	}

	private void resetLocations(Graph g, Dimension d) {
		int vertexCount = g.getVertices().size();
		int thisVertex = 0;
		for (Iterator iter = g.getVertices().iterator(); iter.hasNext();) {
			Vertex v = (Vertex) iter.next();
			CircleVertexData vud;
			if (v.getUserDatum(CIRCLE_KEY) == null) {
				vud = new CircleVertexData();
				v.addUserDatum(CIRCLE_KEY, vud, UserData.SHARED);
			} else {
				vud = (CircleVertexData) v.getUserDatum(CIRCLE_KEY);
			}
			initializeLocation(vud, d, thisVertex, vertexCount);

			thisVertex++;
		}

	}

	/* ------------------------- */

	/**
	 * Sets random locations for a vertex within the dimensions of the space
	 *
	 * @param	 	   vud
	 * @param d
	 */
	private void initializeLocation(
		CircleVertexData vud,
		Dimension d,
		int thisVertex,
		int totalVertices) {
		int centerX = d.width / 2;
		int centerY = d.height / 2;
		int locX =
			(int) ((d.width / 2.0)
				* Math.cos(2 * Math.PI * thisVertex / totalVertices));
		int locY =
			(int) ((d.height / 2.0)
				* Math.sin(2 * Math.PI * thisVertex / totalVertices));
		vud.x = centerX + locX;
		vud.y = centerY + locY;
	}

	//	protected void calcEdgeLength(CircleEdgeData sed, LengthFunction f) {
	////		sed.length = f.getLength(sed.e);
	//	}

	/* ------------------------- */

	/**
	* Relaxation step. Moves all nodes a smidge.
	*/
	public void advanceVisualization() {
	}

	protected Vertex getAVertex(Edge e) {
		Vertex v = (Vertex) e.getIncidentVertices().iterator().next();
		return v;
	}

	//	protected Iterator getAllEdges() {
	//		return showingGraph.getEdges().iterator();
	//	}

	public CircleVertexData getCircleData(Vertex v) {
		return (CircleVertexData) (v.getUserDatum(CIRCLE_KEY));
	}

	public CircleEdgeData getSpringData(Edge e) {
		return (CircleEdgeData) (e.getUserDatum(CIRCLE_KEY));
	}

	public double getX(Vertex v) {
		return ((CircleVertexData) v.getUserDatum(CIRCLE_KEY)).x;
	}

	public double getY(Vertex v) {
		return ((CircleVertexData) v.getUserDatum(CIRCLE_KEY)).y;
	}

	//	public double getLength(Edge e) {
	////		return ((CircleEdgeData) e.getUserDatum(CIRCLE_KEY)).length;
	//	}

	/* ---------------Length Function------------------ */

	/**
	 * If the edge is weighted, then override this method to
	 * show what the visualized length is.
	 *  @author danyelf
	 */
	public interface LengthFunction {
		public double getLength(Edge e);
	}

	private static final class UnitLengthFunction implements LengthFunction {
		int length;
		public UnitLengthFunction(int length) {
			this.length = length;
		}
		public double getLength(Edge e) {
			return length;
		}
	}

	public static final LengthFunction UNITLENGTHFUNCTION =
		new UnitLengthFunction(30);

	/* ---------------User Data------------------ */

	public static class CircleVertexData {
		public double edgedx;
		public double edgedy;

		public CircleVertexData() {
		}
		//		Vertex v;
		/** x location */
		public double x;
		/** y location */
		public double y;
	}

	public static class CircleEdgeData {
		public CircleEdgeData(Edge e) {
			this.e = e;
		}
		Edge e;
	}

	/* ---------------Resize handler------------------ */

	public class SpringDimensionChecker extends ComponentAdapter {
		public void componentResized(ComponentEvent e) {
			resize(e.getComponent().getSize());
		}
	}

	public void resize(Dimension size) {
		synchronized (currentSize) {
			this.currentSize = size;
			resetLocations(graph, size);
		}
	}

	public void restart() {
		//		//		System.out.println("Scramble!");
		//		for (Iterator iter = getAllVertices(); iter.hasNext();) {
		//			Vertex v = (Vertex) iter.next();
		//			if (dontmove.contains(v))
		//				continue;
		//			SpringVertexData vud = getSpringData(v);
		//			//			System.out.print( v.toString() + " " + (int) getX( v ));
		//			initializeLocation(vud, currentSize);
		//			//			System.out.println( "  " + (int) getX( v ) );
		//		}
	}

	protected Iterator getAllVertices() {
		return showingGraph.getVertices().iterator();
	}

	public Vertex getVertex(double x, double y) {
		return getVertex(x, y, Math.sqrt(Double.MAX_VALUE - 1000));
	}

	public Vertex getVertex(double x, double y, double maxDistance) {
		double minDistance = maxDistance * maxDistance;
		Vertex closest = null;
		for (Iterator iter = getAllVertices(); iter.hasNext();) {
			Vertex v = (Vertex) iter.next();
			double dx = getX(v) - x;
			double dy = getY(v) - y;
			double dist = dx * dx + dy * dy;
			if (dist < minDistance) {
				minDistance = dist;
				closest = v;
			}
		}
		return closest;
	}

	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#forcibleMove(edu.uci.ics.jung.graph.Vertex, int, int)
	 */
	public void forceMove(Vertex picked, double x, double y) {
		//		System.out.println("forciblemove");
		CircleVertexData svd = getCircleData(picked);
		svd.x = x;
		svd.y = y;
	}

	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#lockVertex(edu.uci.ics.jung.graph.Vertex)
	 */
	public void lockVertex(Vertex v) {
		dontmove.add(v);

	}
	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#unlockVertex(edu.uci.ics.jung.graph.Vertex)
	 */
	public void unlockVertex(Vertex v) {
		dontmove.remove(v);

	}

    public boolean isLocked(Vertex v)
    {
        return dontmove.contains(v);
    }
    
	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#applyFilter(edu.uci.ics.jung.algorithms.GraphFilter.FilterGraph)
	 */
	public synchronized void applyFilter(Graph g) {
		this.showingGraph = g;
	}

	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#getEdges()
	 */
	public Set getVisibleEdges() {
		return showingGraph.getEdges();
	}
	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#getVertices()
	 */
	public Set getVisibleVertices() {
		return showingGraph.getVertices();
	}

	// TODO: TEMPORARY FUNCTION EXISTS TO TEST OUT
	// VARIED ITERATOR TYPE. SEE FADING NODE VISUALIZER CODE
	public Iterator getVertexIter() {
		return getVisibleVertices().iterator();
	}

	/** Does nothing: this method is non-incremental.'
	 * @see edu.uci.ics.jung.visualization.Layout#advancePositions()
	 */
	public void advancePositions() {
	}

	/** Returns false.
	 * @see edu.uci.ics.jung.visualization.Layout#isIncremental()
	 */
	public boolean isIncremental() {
		return false;
	}

	/** Shouldn't be used. Returns false.
	 * @see edu.uci.ics.jung.visualization.Layout#incrementsAreDone()
	 */
	public boolean incrementsAreDone() {
		return false;
	}

    public Point2D getLocation(Vertex v)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public Point2D getLocation(ArchetypeVertex v)
    {
        return new Point2D.Double(getX((Vertex)v), getY((Vertex)v));
    }

    public Iterator getVertexIterator()
    {
        return getVisibleVertices().iterator();
    }

}