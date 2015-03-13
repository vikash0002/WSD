package scratch.tom.simpleGraph;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * an example implementation of Graph
 * 
 * @author Tom Nelson - RABA Technologies
 *
 * @param <V> type of the vertices
 * @param <E> type of the edges
 */
public class DefaultGraph<V, E extends Edge<V>> implements Graph<V, E> {

    private Collection<V> vertices = new LinkedHashSet<V>();
    private Collection<E> edges = new LinkedHashSet<E>();

    public Collection<V> getVertices() {
        return Collections.unmodifiableCollection(vertices);
    }

    public Collection<E> getEdges() {
        return Collections.unmodifiableCollection(edges);
    }

    public int getVertexCount() {
        return vertices.size();
    }

    public int getEdgeCount() {
        return edges.size();
    }

    public void addVertex(V vertex) {
        vertices.add(vertex);
    }

    /**
     * remove the vertex. If the vertex is an endpoint of an
     * existing edge, remove the edge
     */
    public boolean removeVertex(V vertex) {
        for(Iterator<E> iterator=edges.iterator(); iterator.hasNext(); ) {
            Edge<V> edge = iterator.next();
            if(edge.getEndpoints().contains(vertex)) {
                iterator.remove();
            }
        }
        return vertices.remove(vertex);
    }

    public void addVertices(Collection<V> vertices) {
        vertices.addAll(vertices);
    }

    /**
     * remove all vertices in the passed collection,
     * If any existing edges contain any vertices from the
     * passed collection, remove those edges, too.
     */
    public boolean removeVertices(Collection<V> vertices) {
        for(Iterator<E> iterator=edges.iterator(); iterator.hasNext(); ) {
            Edge<V> edge = iterator.next();
            for(V vertex : vertices) {
                if(edge.getEndpoints().contains(vertex)) {
                    iterator.remove();
                }
            }
        }
        return vertices.removeAll(vertices);
    }

    /**
     * note that vertices in the new edge are added to the graph
     * if not there already
     */
    public void addEdge(E edge) {
        vertices.addAll(edge.getEndpoints());
        edges.add(edge);
    }

    public boolean removeEdge(E edge) {
        return edges.remove(edge);
    }

    /**
     * note that vertices in the new edges are added to the graph
     * if not there already
     */
    public void addEdges(Collection<E> edges) {
        for(Edge<V> edge : edges) {
            vertices.addAll(edge.getEndpoints());
        }
        edges.addAll(edges);
    }

    public boolean removeEdges(Collection<E> edges) {
        return edges.removeAll(edges);
    }
}
