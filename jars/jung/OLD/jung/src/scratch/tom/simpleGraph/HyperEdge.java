package scratch.tom.simpleGraph;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * a hyper edge
 * @author Tom Nelson - RABA Technologies
 *
 * @param <V> type of the vertices
 */
public class HyperEdge<V> implements Edge.Hyper<V> {

    private Collection<V> endpoints = new LinkedHashSet<V>();
    
    public HyperEdge() {
    }
    
    public HyperEdge(V... v) {
        endpoints.addAll(Arrays.asList(v));
    }
    
    public HyperEdge(Collection<V> v) {
        endpoints.addAll(v);
    }

    public Collection<V> getEndpoints() {
        return Collections.unmodifiableCollection(endpoints);
    }

    public void add(V v) {
        endpoints.add(v);
    }

    public void addAll(Collection<V> v) {
        endpoints.addAll(v);
    }

    public boolean remove(V v) {
        return endpoints.remove(v);
    }

    public boolean removeAll(Collection<V> v) {
        return endpoints.removeAll(v);
    }
}
