/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.scott;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JComponent;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.utils.UserData;
import edu.uci.ics.jung.visualization.Layout;

/**
 * The SpringVisualizer package represents a visualization of a set of nodes.
 * The SpringVisualizer, which is initialized with a Graph, assigns X/Y
 * locations to each node. When called relax(), the SpringVisualizer moves
 * the visualization forward one step.
 *
 *  @author danyelf
 *
 * $Log: AestheticSpringVisualizer.java,v $
 * Revision 1.18  2006/03/17 01:56:46  e-flat
 * added isLocked() to Layouts in scratch/
 *
 * Revision 1.17  2005/09/23 06:41:05  e-flat
 * renamed *LocationDecorator to *LocationFunctionadded documentation
 *
 * Revision 1.16  2005/08/04 00:34:33  tomnelson
 * removed transformer code
 *
 * Revision 1.15  2005/07/27 00:00:52  tomnelson
 * changed Layout.forceMove(Vertex v, int x, int y) toforceMove(Vertex v, double x, double y)Now that Layout is not tied to pixels on the screen, weneed the precision in extreme zoom cases
 *
 * Revision 1.14  2005/07/23 06:46:09  e-flat
 * changes to support static and heritable layouts: based on VertexLocationFunction
 *
 * Revision 1.13  2005/07/20 00:26:36  e-flat
 * fixed layout compilation problems
 *
 * Revision 1.12  2004/10/16 01:48:16  e-flat
 * SpringLayout now has setters and getters for vertex repulsion range, edge stretchiness, and edge force multiplier; those of these that were public constants are now protected instance variables
 *
 * Revision 1.11  2003/07/30 08:40:25  offkey
 * Added copyright notice (and cleaned up imports)
 *
 * Revision 1.10  2003/07/29 02:20:32  sdcoder
 * merge
 *
 * Revision 1.9  2003/07/26 01:11:20  offkey
 * tweaks on viz system
 *
 * Revision 1.8  2003/07/17 18:15:39  sdcoder
 * added Coordinates class, refactored, changed names as agreed upon
 *
 * Revision 1.7  2003/07/17 01:51:41  offkey
 * Created AbstratVisualizer; upgraded old visualizers to use it. Improved TestGraphDraw
 *
 * Revision 1.6  2003/07/08 22:38:18  e-flat
 * REMOVED:
 ArchetypeGraph.getVertexByID() signature
 ArchetypeGraph.getVertexID() signature
 AbstractSparseGraph.getVertexID()
 GraphUtils.getCorrespondingEdge(Graph, Edge)
 a lot of unused imports, some unused variables (some just commented out)
DDED:
 ArchetypeVertex.getEquivalentVertex()
 ArchetypeEdge.getEquivalentEdge() [may throw if does not uniquely exist]
HANGED:
 several unit tests no longer check/compare IDs but do check vertex/edge equivalences
 AbstractSparseGraph.getVertexByID() now package-visible method
  (this is where friend classes would be useful, as Danyel pointed out :> )
 Indexer: now defined in terms of Archetype{Graph, Vertex}
 SparseXY now called XSparseY (X: {Directed, Undirected}; Y: {Graph, Edge, Vertex})
 added to ReleaseNotes: need to put license agreement text at top of every (released)
  source file
 Sparse[Un]DirectedVertex: has separate storage for incident edge set(s); edge->vertex
  maps removed
 *
 * Revision 1.5  2003/07/03 15:36:09  sdcoder
 * added back in code
 *
 * Revision 1.4  2003/06/24 04:16:29  offkey
 * Minor fixes for updates
 *
 * Revision 1.3  2003/06/24 03:47:43  e-flat
 * changed XImpl to SparseX (e.g., DirectedGraphImpl -> DirectedSparseGraph)
moved UserData, UserDataContainer to jung.utils
 *
 * Revision 1.2  2003/06/24 02:14:32  offkey
 * ClusteringDemo of graph API possibilities
 *
 * Revision 1.1  2003/06/21 00:37:15  sdcoder
 * *** empty log message ***
 *
 * Revision 1.16  2003/05/12 15:13:22  offkey
 * *** empty log message ***
 *
 * Revision 1.15  2003/05/10 07:29:20  offkey
 * FPS calculations and debugging instrumentation
 *
 * Revision 1.14  2003/05/10 06:21:10  offkey
 * Layout has some debugging code. Set FadeRenderer.debug to FALSE if you dont want to see it.
 *
 * Revision 1.13  2003/05/09 07:44:09  offkey
 * *** empty log message ***
 *
 * Revision 1.12  2003/05/09 06:02:18  offkey
 * Working on viz updates
 *
 * Revision 1.11  2003/05/09 03:53:36  offkey
 * New filter mechanism
 *
 * Revision 1.10  2003/05/07 19:54:53  offkey
 * New filter mechanism is cool!
 *
 * Revision 1.9  2003/05/05 04:55:59  offkey
 * *** empty log message ***
 *
 * Revision 1.8  2003/05/03 06:25:44  e-flat
 * changed Edge.getVertices() to Edge.getIncidentVertices()
 *
 * Revision 1.7  2003/04/29 16:50:52  offkey
 * filter alternative tests
 *
 * Revision 1.6  2003/04/26 20:18:26  sdcoder
 * Took out unused variables, imports, etc.
 *
 * Revision 1.5  2003/04/25 00:58:25  offkey
 * *** empty log message ***
 *
 * Revision 1.3  2003/04/23 17:43:16  offkey
 * Better and better demos! Getting ready to implement fitlered graphs; already have click response working
 *
 * Revision 1.2  2003/04/23 07:37:40  offkey
 * Improvements--response to clicks now built in
 *
 * Revision 1.1  2003/04/23 04:22:55  offkey
 * Added initial visualization code
 *
 */
public class AestheticSpringVisualizer implements Layout {

	public static int RANGE = 100;
	private double FORCE_CONSTANT = 1.0 / 3.0;
	protected Set dontmove;

	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#getCurrentSize()
	 */
	public Dimension getCurrentSize() {
		return currentSize;
	}

	public void applyFilter(Graph subgraph) {
		this.showingGraph = subgraph;
	}

	public String getStatus() {
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#initialize()
	 */
	public void initialize(Dimension d) {
		return;
	}

	public static final Object SPRING_KEY =
		"edu.uci.ics.jung.Spring_Visualization_Key";

	public Graph getGraph() {
		return graph;
	}

	protected Dimension currentSize;
	private Graph graph;
	private Graph showingGraph;

	/**
	 * Constructor for a SpringVisualizer for a raw graph with associated
	 * component. Defaults to the unit length function.
	 */
	public AestheticSpringVisualizer(Graph g, JComponent jc) {
		this(g, UNITLENGTHFUNCTION, jc);
	}

	/**
	 * Constructor for a SpringVisualizer for a raw graph with associated
	 * dimension--the input knows how big the graph is. Defaults to the
	 * unit length function
	 */
	public AestheticSpringVisualizer(Graph g) {
		this(g, UNITLENGTHFUNCTION, new Dimension(100, 100));
	}

	/**
	 * Constructor for a SpringVisualizer for a raw graph with associated
	 * dimension--the input knows how big the graph is. Defaults to the
	 * unit length function
	 */
	public AestheticSpringVisualizer(Graph g, Dimension d) {
		this(g, UNITLENGTHFUNCTION, d);
	}

	/**
	 * Constructor for a SpringVisualizer for a raw graph with
	 * associated component. Registers a listener on the component
	 * to pay attention to its current size.
	 *
	 * @param g	the input Graph
	 * @param f	the Graph length function
	 * @param jc	the component to be registered
	 */
	public AestheticSpringVisualizer(
		Graph g,
		LengthFunction f,
		JComponent jc) {
		this(g, f, jc.getSize());
		jc.addComponentListener(new SpringDimensionChecker());
	}

	/**
	 * Constructor for a SpringVisualizer for a raw graph with
	 * associated component.
	 *
	 * @param g	the input Graph
	 * @param f	the Graph length function
	 * @param d	the dimension to be registered
	 */
	public AestheticSpringVisualizer(Graph g, LengthFunction f, Dimension d) {
		//		System.out.println("Spring Layout constructor " + d);
		this.currentSize = d;
		for (Iterator iter = g.getVertices().iterator(); iter.hasNext();) {
			Vertex v = (Vertex) iter.next();
			SpringVertexData vud = new SpringVertexData(v);
			v.addUserDatum(SPRING_KEY, vud, UserData.SHARED);
			initializeLocation(vud, d);
		}
		for (Iterator iter = g.getEdges().iterator(); iter.hasNext();) {
			Edge e = (Edge) iter.next();
			SpringEdgeData eud = new SpringEdgeData(e);
			e.addUserDatum(SPRING_KEY, eud, UserData.SHARED);
			calcEdgeLength(eud, f);
		}
		this.graph = g;
		this.showingGraph = g;
		this.dontmove = new HashSet();
	}

	/* ------------------------- */

	/**
	 * Sets random locations for a vertex within the dimensions of the space
	 *
	 * @param	 	   vud
	 * @param d
	 */
	private void initializeLocation(SpringVertexData vud, Dimension d) {
		double x = Math.random() * d.getWidth();
		double y = Math.random() * d.getHeight();
		vud.x = x;
		vud.y = y;
	}

	protected void calcEdgeLength(SpringEdgeData sed, LengthFunction f) {
		sed.length = f.getLength(sed.e);
	}

	/* ------------------------- */

	long relaxTime = 0;
	public static int STRETCH = 70;

	/**
	* Relaxation step. Moves all nodes a smidge.
	*/
	public void advancePositions() {
		//				System.out.println("Relax");
		for (Iterator iter = getVisibleVertices().iterator();
			iter.hasNext();
			) {
			Vertex v = (Vertex) iter.next();
			SpringVertexData svd = getSpringData(v);
			if (svd == null) {
				System.out.println("How confusing!");
				continue;
			}
			svd.dx /= 4;
			svd.dy /= 4;
			svd.edgedx = svd.edgedy = 0;
			svd.repulsiondx = svd.repulsiondy = 0;
		}

		relaxEdges();
		calculateRepulsion();
		//calculateEdgeRepulsion();
		moveNodes();
		//		long end = System.currentTimeMillis();
	}

	protected Vertex getAVertex(Edge e) {
		Vertex v = (Vertex) e.getIncidentVertices().iterator().next();
		return v;
	}

	//	/**
	//	 * Returns the length of E as measured by the distance
	//	 * between its two vertices. This is likely to be different
	//	 * from the distance between its two centers.
	//	 *
	//	 * @param e
	//	 * @return double
	//	 */
	//	private double distance(Vertex v1, Vertex v2) {
	//		double vx = getX(v1) - getX(v2);
	//		double vy = getY(v1) - getY(v2);
	//		double len = Math.sqrt(vx * vx + vy * vy);
	//		return len;
	//	}

	private void relaxEdges() {
		for (Iterator i = getAllEdges(); i.hasNext();) {
			Edge e = (Edge) i.next();

			Vertex v1 = getAVertex(e);
			Vertex v2 = (Vertex) e.getOpposite(v1);

			double vx = getX(v1) - getX(v2);
			double vy = getY(v1) - getY(v2);
			double len = Math.sqrt(vx * vx + vy * vy);

			double desiredLen = getLength(e);
			//			desiredLen *= Math.pow( 1.1,  (v1.degree() + v2.degree()) );

			// round from zero, if needed [zero would be Bad.].
			len = (len == 0) ? .0001 : len;

			// force factor: optimal length minus actual length,
			// is made smaller as the current actual length gets larger.
			// why?

			//			System.out.println("Desired : " + getLength( e ));
			double f = FORCE_CONSTANT * (desiredLen - len) / len;

			f = f * Math.pow(STRETCH / 100.0, (v1.degree() + v2.degree() - 2));

			//			f= Math.min( 0, f );

			// the actual movement distance 'dx' is the force multiplied by the distance to go.
			double dx = f * vx;
			double dy = f * vy;
			SpringVertexData v1D, v2D;
			v1D = getSpringData(v1);
			v2D = getSpringData(v2);

			SpringEdgeData sed = getSpringData(e);
			sed.f = f;

			v1D.edgedx += dx;
			v1D.edgedy += dy;
			v2D.edgedx += -dx;
			v2D.edgedy += -dy;
		}
	}

	/**
	 *
	 */
	private void calculateRepulsion() {
		for (Iterator iter = getGraph().getVertices().iterator();
			iter.hasNext();
			) {
			Vertex v = (Vertex) iter.next();
			if (dontmove.contains(v))
				continue;

			SpringVertexData svd = getSpringData(v);
			double dx = 0, dy = 0;

			for (Iterator iter2 = getGraph().getVertices().iterator();
				iter2.hasNext();
				) {
				Vertex v2 = (Vertex) iter2.next();
				if (v == v2)
					continue;
				double vx = getX(v) - getX(v2);
				double vy = getY(v) - getY(v2);
				double distance = vx * vx + vy * vy;
				if (distance == 0) {
					dx += Math.random();
					dy += Math.random();
				} else if (distance < RANGE * RANGE) {
					//					double degreeConstant = 1 + (stretch / 100.0);
					//					double factor = Math.pow( degreeConstant, v.degree() + v2.degree() - 2);
					double factor = 1;
					dx += factor * vx / Math.pow(distance, 2);
					dy += factor * vy / Math.pow(distance, 2);
				}
			}
			double dlen = dx * dx + dy * dy;
			if (dlen > 0) {
				dlen = Math.sqrt(dlen) / 2;
				svd.repulsiondx += dx / dlen;
				svd.repulsiondy += dy / dlen;
			}
		}
	}

	//	/**
	//	 * @param v
	//	 * @param v2
	//	 * @return
	//	 */
	//	private double distance(Vertex v, Vertex v2) {
	//		double deltaX = getX(v) - getX(v2);
	//		double deltaY = getY(v) - getY(v2);
	//		return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
	//	}

	protected Iterator getAllEdges() {
		return showingGraph.getEdges().iterator();
	}

	protected void moveNodes() {
		//		Dimension d = currentSize;

		synchronized (currentSize) {

			//			int showingNodes = 0;

			for (Iterator i = getAllVertices(); i.hasNext();) {
				Vertex v = (Vertex) i.next();
				if (dontmove.contains(v))
					continue;
				SpringVertexData vd = getSpringData(v);

				vd.dx += vd.repulsiondx + vd.edgedx;
				vd.dy += vd.repulsiondy + vd.edgedy;

				// keeps nodes from moving any faster than 5 per time unit
				vd.x += Math.max(-5, Math.min(5, vd.dx));
				vd.y += Math.max(-5, Math.min(5, vd.dy));

				if (vd.x < 0) {
					vd.x = 0;
				} else if (vd.x > currentSize.width) {
					vd.x = currentSize.width;
				}
				if (vd.y < 0) {
					vd.y = 0;
				} else if (vd.y > currentSize.height) {
					vd.y = currentSize.height;
				}

			}
		}

	}

	public SpringVertexData getSpringData(Vertex v) {
		return (SpringVertexData) (v.getUserDatum(SPRING_KEY));
	}

	public SpringEdgeData getSpringData(Edge e) {
		return (SpringEdgeData) (e.getUserDatum(SPRING_KEY));
	}

	public double getX(Vertex v) {
		return ((SpringVertexData) v.getUserDatum(SPRING_KEY)).x;
	}

	public double getY(Vertex v) {
		return ((SpringVertexData) v.getUserDatum(SPRING_KEY)).y;
	}

	public double getLength(Edge e) {
		return ((SpringEdgeData) e.getUserDatum(SPRING_KEY)).length;
	}

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

	public static class SpringVertexData {
		public double edgedx;
		public double edgedy;
		public double repulsiondx;
		public double repulsiondy;

		public SpringVertexData(Vertex v) {
			//			this.v = v;
		}
		//		Vertex v;
		/** x location */
		public double x;
		/** y location */
		public double y;
		/** movement speed, x */
		public double dx;
		/** movement speed, y */
		public double dy;
	}

	public static class SpringEdgeData {
		public double f;
		public SpringEdgeData(Edge e) {
			this.e = e;
		}
		Edge e;
		double length;
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
			//			System.out.println("resized component!");
		}
	}

	public void restart() {
		//		System.out.println("Scramble!");
		for (Iterator iter = getAllVertices(); iter.hasNext();) {
			Vertex v = (Vertex) iter.next();
			if (dontmove.contains(v))
				continue;
			SpringVertexData vud = getSpringData(v);
			//			System.out.print( v.toString() + " " + (int) getX( v ));
			initializeLocation(vud, currentSize);
			//			System.out.println( "  " + (int) getX( v ) );
		}
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
		SpringVertexData svd = getSpringData(picked);
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
		SpringVertexData svd = getSpringData(v);
		svd.dx = 0;
		svd.dy = 0;
	}

    /**
     * @see edu.uci.ics.jung.visualization.Layout#isLocked(Vertex)
     */
    public boolean isLocked(Vertex v)
    {
        return dontmove.contains(v);
    }
    
	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#getEdges()
	 */
	public synchronized Set getVisibleEdges() {
		return showingGraph.getEdges();
	}
	/* (non-Javadoc)
	 * @see edu.uci.ics.jung.visualization.Layout#getVertices()
	 */
	public synchronized Set getVisibleVertices() {
		return showingGraph.getVertices();
	}

	private void calculateEdgeRepulsion(
		Vertex v11,
		Vertex v12,
		Vertex v21,
		Vertex v22) {

		double x1 = getX(v11);
		double y1 = getY(v11);
		double x2 = getX(v12);
		double y2 = getY(v12);

		double u1 = getX(v21);
		double v1 = getY(v21);
		double u2 = getX(v22);
		double v2 = getY(v22);

		double b1 = (y2 - y1) / (x2 - x1);
		double b2 = (v2 - v1) / (u2 - u1);
		double a1 = y1 - b1 * x1;
		double a2 = v1 - b2 * u1;

		double xi = - (a1 - a2) / (b1 - b2);
		double yi = a1 + b1 * xi;

		if (((x1 == u1 && y1 == v1) && (x2 != u2 && y2 != v2))
			|| ((x1 != u1 && y1 != v1) && (x2 == u2 && y2 == v2))
			|| ((x1 == u2 && y1 == v2) && (x2 != u1 && y2 != v1))
			|| ((x1 != u2 && y1 != v2) && (x2 == u1 && y2 == v1))) {
			return;
		}

		if ((x1 - xi) * (xi - x2) >= 0
			&& (u1 - xi) * (xi - u2) >= 0
			&& (y1 - yi) * (yi - y2) >= 0
			&& (v1 - yi) * (yi - v2) >= 0) {
			// System.out.println("Line (" + (int) x1 + "," + (int) y1 + ") -> ("+ (int) x2 + "," + (int) y2 + ") intersects with line (" + (int) u1 + "," + (int) v1 + ") -> ("+ (int) u2 + "," + (int) v2 + ")");
			double midx1 = (x1 + x2) / 2;
			double midy1 = (y1 + y2) / 2;
			double midu1 = (u1 + u2) / 2;
			double midv2 = (v1 + v2) / 2;

			double deltaX = midu1 - midx1;
			double deltaY = midv2 - midy1;
			double mag =
				Math.sqrt(Math.pow(deltaX, 2.0) + Math.pow(deltaY, 2.0));
			mag = Math.max(mag, 1e-8); /* avoid div by 0 */
			deltaX *= 1.0 / mag;
			deltaY *= 1.0 / mag;

			SpringVertexData v11Data = getSpringData(v11);
			v11Data.dx -= deltaX;
			v11Data.dy -= deltaY;
			SpringVertexData v12Data = getSpringData(v12);
			v12Data.dx -= deltaX;
			v12Data.dy -= deltaY;
			SpringVertexData v21Data = getSpringData(v21);
			v21Data.dx += deltaX;
			v21Data.dy += deltaY;
			SpringVertexData v22Data = getSpringData(v22);
			v22Data.dx += deltaX;
			v22Data.dy += deltaY;
		}
	}

	private void calculateEdgeRepulsion() {
		for (Iterator iter = getGraph().getEdges().iterator();
			iter.hasNext();
			) {
			Edge e1 = (Edge) iter.next();

			Set list1 = e1.getIncidentVertices();
			Iterator i = list1.iterator();
			Vertex v11 = (Vertex) i.next();
			Vertex v12 = (Vertex) i.next();

			if (dontmove.contains(v11) || dontmove.contains(v12))
				continue;

			for (Iterator iter2 = getGraph().getEdges().iterator();
				iter2.hasNext();
				) {
				Edge e2 = (Edge) iter2.next();

				if (e1 == e2) {
					continue;
				}

				Set list2 = e2.getIncidentVertices();
				i = list2.iterator();
				Vertex v21 = (Vertex) i.next();
				Vertex v22 = (Vertex) i.next();

				if (dontmove.contains(v21) || dontmove.contains(v22))
					continue;

				if (v11 == v22 && v12 == v21)
					continue;

				calculateEdgeRepulsion(v11, v12, v21, v22);
			}
		}
	}

	/** This is an incremental visualization.
	 * @see edu.uci.ics.jung.visualization.Layout#isIncremental()
	 */
	public boolean isIncremental() {
		return true;
	}
	/** This incremental version doesn't end.
	 * @see edu.uci.ics.jung.visualization.Layout#incrementsAreDone()
	 */
	public boolean incrementsAreDone() {
		return false;
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