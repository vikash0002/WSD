package scratch.tom.simpleGraph;

import java.util.Collection;

/**
 * Edge is parameterized for a particular vertex
 * type. Edge<Integer> is a type safe instance of
 * an edge containing Integer vertices.
 * 
 * There are two sub-interfaces.
 * A DirectedEdge instance would implement Edge.Directed
 * An UndirectedEdge instance would implement Edge
 * A HyperEdge instance would implement Edge.Hyper.
 * This was done so as to provide interfaces for
 * directedness and hyperness (in the context of
 * Edges) and to not steal away the use of the 
 * names DirectedEdge
 * and HyperEdge for the instance
 * classes
 *
 * @author Tom Nelson - RABA Technologies
 *
 * @param <V> the type of the vertices
 */
public interface Edge<V> {
    
    Collection<V> getEndpoints();
    
    /**
     * Directed adds two methods to Edge
     */
    interface Directed<W> extends Edge<W> {
        W getFirst();
        W getSecond();
    }
    
    /**
     * Hyper adds several methods to Edge
     */
    interface Hyper<W> extends Edge<W> {
        void add(W v);
        void addAll(Collection<W> v);
        boolean remove(W v);
        boolean removeAll(Collection<W> v);
    }

}
