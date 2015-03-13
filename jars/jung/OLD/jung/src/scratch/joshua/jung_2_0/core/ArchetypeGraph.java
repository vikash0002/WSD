/*
 * Created on Oct 17, 2005
 *
 * Copyright (c) 2005, the JUNG Project and the Regents of the University 
 * of California
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * http://jung.sourceforge.net/license.txt for a description.
 */
package scratch.joshua.jung_2_0.core;

import java.util.Collection;

public interface ArchetypeGraph<V, E>
{
    public Collection<E> getEdges();
    
    public Collection<V> getVertices();

    public Collection<V> getNeighbors(V vertex);
    
    Collection<E> getIncidentEdges(V vertex);
    
    Collection<V> getIncidentVertices(E edge);
    
    E findEdge(V v1, V v2);
    
    boolean addVertex(V vertex);
    
    boolean removeVertex(V vertex);
    
    boolean removeEdge(E edge);
    
    
    // "optional" methods (semantic sugar for operations which can be defined in terms of above operations)

    boolean areNeighbors(V v1, V v2); // getNeighbors(v).contains(w)

    boolean areIncident(V vertex, E edge); // getIncidentEdges(v).contains(e) OR getIncidentVertices(e).contains(v)
    
    int degree(V vertex); // getIncidentEdges(v).size()
    
    int numNeighbors(V vertex); // getNeighbors(v).size()

}
