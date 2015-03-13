package scratch.tom.simpleGraph;

import java.util.Collection;
import java.util.HashSet;

public class GraphUtils {
    
    public static <V, E extends DirectedEdge<V>> 
        void doSomethingThatNeedsDirectedGraph(Graph<V, E> graph) {
        
        // nothing to do but check method call at compile time
    }
    
    
    public static <V, E extends Edge>int getDegree(Graph<V, E> graph, V vertex) {
        Collection<E> edges = graph.getEdges();
        int degree = 0;
        for(Edge<V> edge : edges) {
            if(edge.getEndpoints().contains(vertex)) {
                degree++;
            }
        }
        return degree;
    }
    
    public static <V, E extends Edge>Collection<V> getNeighbors(Graph<V, E> graph, V vertex) {
        Collection<E> edges = graph.getEdges();
        Collection<V> neighbors = new HashSet<V>();
        for(Edge<V> edge : edges ) {
            if(edge.getEndpoints().contains(vertex)) {
                neighbors.addAll(edge.getEndpoints());
            }
        }
        neighbors.remove(vertex); // not my own neighbor!
        return neighbors;
    }
}
