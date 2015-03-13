package scratch.tom.simpleGraph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class UndirectedEdge<V> implements Edge<V> {

    private Collection<V> endpoints = new ArrayList<V>();
    
    public UndirectedEdge(V oneEnd, V theOther) {
        endpoints.add(oneEnd);
        endpoints.add(theOther);
    }

    public Collection<V> getEndpoints() {
        return Collections.unmodifiableCollection(endpoints);
    }
    
    public String toString() {
        return getClass().getName()+" "+endpoints;
    }

}
