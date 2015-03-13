/*
 * Created on Dec 1, 2003
 */
package scratch.danyel.lazy;

import java.util.*;

import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.impl.SparseVertex;

/**
 * @author danyelf
 */
public class LazySparseVertex extends SparseVertex implements Vertex {

	public static final String STATUS_NAME_KNOWN_BUT_EMPTY = "NAME_KNOWN_BUT_EMPTY";
	public static final String STATUS_READ_BUT_NOT_NEIGHBORS = "READ_BUT_NOT_NEIGHBORS";
	public static final String STATUS_COMPLETE = "STATUS_FINAL";

	protected String current_status;
	private Object uniqueID;
	private LazySparseVertexFactory lsvf;


	/**
	 * Returns a map from the successors of this vertex to its outgoing edges.
	 * If this map has not yet been created, it creates it. This method should
	 * not be directly accessed by users.
	 */
	protected Map getNeighborsToEdges() {
		readVertexAnnotation();
		readVertexData();
		if (mNeighborsToEdges == null) {
			setNeighborsToEdges(new HashMap(5));
		}
		return mNeighborsToEdges;
	}

	/**
	 * Returns a map from the successors of this vertex to its outgoing edges.
	 * If this map has not yet been created, it creates it. This method should
	 * not be directly accessed by users.
	 */
	protected Map getSuccsToOutEdges() {
		readVertexAnnotation();
		readVertexData();
		if (mSuccsToOutEdges == null) {
			setSuccsToOutEdges(new HashMap(5));
		}
		return mSuccsToOutEdges;
	}

	protected Map getPredsToInEdges() {
		readVertexAnnotation();
		readVertexData();
		if (mPredsToInEdges == null) {
			setPredsToInEdges(new HashMap(5));
		}
		return mPredsToInEdges;
	}

	public void readVertexAnnotation() {
		if (this.current_status == STATUS_NAME_KNOWN_BUT_EMPTY) {
			// do we need to do anyhting with this method?
			lsvf.annotateVertex(this);
			this.current_status = STATUS_READ_BUT_NOT_NEIGHBORS;
		}
		return;
	}

	public void readVertexData() {
		if (this.current_status == STATUS_READ_BUT_NOT_NEIGHBORS) {
			// first, we need a list of neighbors
			Object[] o = lsvf.getNeighborsIds(this.getUniqueID());
			for (int i = 0; i < o.length; i++) {
				Edge e = lsvf.getEdge(this.getUniqueID(), o[i]);
				// lsvf calls graph.addEdge calls addNeighbor, so I won't
				// bother
			}			
			this.mNeighborsToEdges = tentativeNeighborsToEdges;
			this.mPredsToInEdges = tentativePredsToInEdges;
			this.mSuccsToOutEdges = tentativeSuccsToOutEdges;
			this.current_status = STATUS_COMPLETE;
		}
		return;
	}

	// needs to override the existing form
	protected void addNeighbor_internal(Edge e, Vertex v) {
		if (current_status == STATUS_COMPLETE)
			super.addNeighbor_internal(e, v);
		else {
			// deal only with pending requests
			if (e instanceof DirectedEdge) {
				Map stoe = tentativeSuccsToOutEdges;
				Set outEdges = (Set) stoe.get(v);
				Map ptie = tentativePredsToInEdges;
				Set inEdges = (Set) ptie.get(v);

				DirectedEdge de = (DirectedEdge) e;
				boolean added = false;
				if (this == de.getSource()) {
					if (outEdges == null) {
						outEdges = new HashSet();
						stoe.put(v, outEdges);
					}
					outEdges.add(de);
					added = true;
				}
				if (this == de.getDest()) {
					if (inEdges == null) {
						inEdges = new HashSet();
						ptie.put(v, inEdges);
					}
					inEdges.add(de);
					added = true;
				}
				if (!added)
					throw new IllegalArgumentException(
						"Internal error: " + "this vertex is not incident to " + e);
			} else if (e instanceof UndirectedEdge) {
				Map nte = tentativeNeighborsToEdges;
				Set edges = (Set) nte.get(v);

				if (edges == null) {
					edges = new HashSet();
					nte.put(v, edges);
				}
				edges.add(e);
			} else
				throw new IllegalArgumentException("Edge is neither directed" + "nor undirected");
		}
	}

	private Map tentativeSuccsToOutEdges;
	private Map tentativePredsToInEdges;
	private Map tentativeNeighborsToEdges;
	
	public LazySparseVertex(Object uniqueID, LazySparseVertexFactory lsvf) {
		super();
		this.current_status = STATUS_NAME_KNOWN_BUT_EMPTY;
		this.lsvf = lsvf;
		this.uniqueID = uniqueID;
		tentativeSuccsToOutEdges = new HashMap();
		tentativePredsToInEdges = new HashMap();
		tentativeNeighborsToEdges = new HashMap();
	}

	/**
	 * @return Returns the uniqueID.
	 */
	public Object getUniqueID() {
		return uniqueID;
	}

}
