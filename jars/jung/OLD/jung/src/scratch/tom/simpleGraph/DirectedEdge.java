package scratch.tom.simpleGraph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * a directed edge with vertex parameters specified
 * @author Tom Nelson - RABA Technologies
 *
 * @param <V> the type of the vertices in this edge
 */
public class DirectedEdge<V> implements Edge.Directed<V> {

    private List<V> endpoints = new ArrayList<V>(2);
    
    public DirectedEdge(V first, V second) {
        endpoints.add(first);
        endpoints.add(second);
    }

    public Collection<V> getEndpoints() {
        return Collections.unmodifiableCollection(endpoints);
    }

    public V getFirst() {
        return endpoints.get(0);
    }

    public V getSecond() {
        return endpoints.get(1);
    }
}
