package scratch.tom.simpleGraph;
import java.util.Collection;

/**
 * Graph is parameterized with vertex and edge
 * type. For example, Graph<String, DirectedEdge<String>
 * is a graph whose vertices are String instances, and
 * whose edges are DirectedEdges containing String vertices.
 * In other words, This particular Graph is compile time type-safe
 * as a 'directed graph'.
 * 
 * @author Tom Nelson - RABA Technologies
 *
 * @param <V> the type of the vertices
 * @param <E> the type of the edges
 */
public interface Graph<V, E extends Edge> {
    
    void addEdge(E edge);
    
    void addEdges(Collection<E> edges);
    
    boolean removeEdge(E edge);
    
    boolean removeEdges(Collection<E> edges);
    
    void addVertex(V vertex);
    
    void addVertices(Collection<V> vertices);
    
    boolean removeVertex(V vertex);
    
    boolean removeVertices(Collection<V> vertices);
    
    Collection<V> getVertices();
    
    Collection<E> getEdges();

}
