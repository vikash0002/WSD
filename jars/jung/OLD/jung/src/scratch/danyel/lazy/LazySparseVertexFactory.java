/*
 * Created on Dec 1, 2003
 */
package scratch.danyel.lazy;

import java.util.HashMap;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;

/**
 * 
 * To override this class,
 * 
 * @author danyelf
 */
public abstract class LazySparseVertexFactory {

	protected HashMap idToVertex = new HashMap();

	protected HashMap idPairToEdge = new HashMap();

	private Graph graph;

	public LazySparseVertexFactory( Graph g ) {
		this.graph = g;
	}
	
	public Vertex getVertex(Object identifier) {
		if (!idToVertex.containsKey(identifier)) {
			LazySparseVertex lsv = getLazySparseVertex(identifier);
			idToVertex.put(identifier, lsv);
			graph.addVertex(lsv);
		}
		return (Vertex) idToVertex.get(identifier);
	}

	/**
	 * Creates a new LazySparseVertex tied to this identifier. The user should
	 * override this to provide the appropriate construction. The
	 * LazySparseVertex should be in either STATUS_NAME_KNOWN_BUT_EMPTY or
	 * STATUS_COMPLETE. The user may be safely guaranteed that this method will
	 * be run only once, and so the ID may be a unique ID. It is the user's
	 * responsibility to ensure that this is the only entry point to create new
	 * Vertices. (In particular, creating vertices through other methods will
	 * make getEdge not work.) 
	 * The default implementation creates an instance of LazySparseVertex with
	 * the field "UniqueID" tied to the identifier.
	 * 
	 * @return
	 */
	protected LazySparseVertex getLazySparseVertex(Object identifier) {
		return new LazySparseVertex( identifier, this );
	}

	/**
	 * Returns an edge running between these two vertices. By default, this is
	 * a DirectedSparseEdge; override for other types.
	 * 
	 * @param id1
	 * @param id2
	 * @return
	 */
	public Edge getEdge(Object id1, Object id2) {
		Pair p = new Pair(id1, id2);
		if (!idPairToEdge.containsKey(p)) {
			Vertex v1 = getVertex(id1);
			Vertex v2 = getVertex(id2);
			Edge e = getLazyEdge( v1, v2 );
			graph.addEdge( e );
			idPairToEdge.put(p, e);
		}
		return (Edge) idPairToEdge.get(p);
	}

	/**
	 * Returns the (unqiue) edge connected vertices 1 and 2.
	 * By default, merely creates a DirectedSparseEdge and
	 * returns that.
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	protected Edge getLazyEdge(Vertex v1, Vertex v2) {
		Edge e = new DirectedSparseEdge(v1, v2);
		return e;
	}

	private class Pair {
		Object id1;
		Object id2;
		private Pair(Object id1, Object id2) {
			this.id1 = id1;
			this.id2 = id2;
		}
		public int hashCode() {
			return id1.hashCode() + id2.hashCode();
		}
		public boolean equals(Object o) {
			Pair p = (Pair) o;
			return id1.equals(p.id1) && id2.equals(p.id2);
		}

	}

	/**
	 * Ok, here'a a Vertex. Do we have any information this vertex
	 * needs in order to be a complete and fully-realized vertex? 
	 * @param vertex
	 */
	public abstract void annotateVertex(LazySparseVertex vertex);

	/**
	 * Returns an array of IDs of successors.
	 *
	 * @return
	 */
	public abstract Object[] getNeighborsIds(Object o);

}
