/*
 * Created on Oct 16, 2005
 */
package scratch.danyel.simplegraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseGraph;
import edu.uci.ics.jung.graph.impl.SparseVertex;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;
import edu.uci.ics.jung.utils.UserData;

/**
 * 
 * @author danyelf
 */
public class SimpleGraph implements Collection {

	Graph underlyingGraph = new SparseGraph();
	Map objectToVertex = new HashMap();
	private static final Object OBJECT_KEY = "SimpleGraph";

	Graph getUnderlyingGraph() {
		return underlyingGraph;
	}
	
	Vertex getVertex( Object o ) {
		return (Vertex) objectToVertex.get(o);
	}
	
	/**
	 * Returns the number of Vertices
	 * 
	 * @see java.util.Collection#size()
	 */
	public int size() {
		return objectToVertex.keySet().size();
	}

	/**
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty() {
		return objectToVertex.isEmpty();
	}

	/**
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	public boolean contains(Object arg0) {
		return objectToVertex.containsKey(arg0);
	}

	/**
	 * @see java.util.Collection#iterator()
	 */
	public Iterator iterator() {
		return objectToVertex.keySet().iterator();
	}

	/**
	 * @see java.util.Collection#toArray()
	 */
	public Object[] toArray() {
		return objectToVertex.keySet().toArray();
	}

	/**
	 * @see java.util.Collection#toArray(java.lang.Object[])
	 */
	public Object[] toArray(Object[] arg0) {
		return objectToVertex.keySet().toArray(arg0);
	}

	/**
	 * @see java.util.Collection#add(java.lang.Object)
	 */
	public boolean add(Object arg0) {
		if (objectToVertex.containsKey(arg0)) {
			return false;
		} else {
			SparseVertex sv = new SparseVertex();
			sv.addUserDatum(OBJECT_KEY, arg0, UserData.REMOVE);
			underlyingGraph.addVertex(sv);
			objectToVertex.put(arg0, sv);
			return true;
		}
	}

	/**
	 * By default, adds an undirected edge 
	 */
	public void addEdge( Object v1, Object v2) {
		addEdge(v1, v2, false);
	}

	public void addEdge( Object v1, Object v2, boolean isDirected ) {
		if( objectToVertex.containsKey( v1 ) && objectToVertex.containsKey(v2)) {
			Vertex vv1 = (Vertex) objectToVertex.get( v1 );
			Vertex vv2 = (Vertex) objectToVertex.get( v2 );
			if ( isDirected ) {
				underlyingGraph.addEdge( new DirectedSparseEdge( vv1, vv2 ));
			} else {
				underlyingGraph.addEdge( new UndirectedSparseEdge( vv1, vv2 ));
			}
		} else {
			throw new IllegalArgumentException("The vertices don't exist.");
		}
	}
	
	/**
	 * @see java.util.Collection#remove(java.lang.Object)
	 */
	public boolean remove(Object arg0) {
		Vertex v = (Vertex) objectToVertex.remove( arg0 );
		if( v == null ) {
			return false;
		} else {
			underlyingGraph.removeVertex(v);
			return true;
		}
	}
	
	/**
	 * @see java.util.Collection#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection arg0) {
		return objectToVertex.keySet().containsAll( arg0 );
	}

	/**
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection arg0) {
		boolean v = false;
		for (Iterator iter = arg0.iterator(); iter.hasNext();) {
			Object e = iter.next();
			v |= add( e );
		}
		return v;
	}

	/**
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection arg0) {
		boolean v = false;
		for (Iterator iter = arg0.iterator(); iter.hasNext();) {
			Object e = iter.next();
			v |= remove( e );
		}
		return v;
	}

	/**
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection arg0) {
		Set toRemove = new HashSet();
		for (Iterator iter = objectToVertex.keySet().iterator(); iter.hasNext();) {
			Object e = iter.next();
			if(! arg0.contains( e )) {				
				toRemove.add( e );
			}
		}
		return removeAll( toRemove );
	}

	/**
	 * @see java.util.Collection#clear()
	 */
	public void clear() {
		removeAll( new HashSet( objectToVertex.keySet() ) );
	}

}