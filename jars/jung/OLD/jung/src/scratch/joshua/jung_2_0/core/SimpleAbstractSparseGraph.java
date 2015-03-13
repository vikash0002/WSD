/*
 * Created on Apr 2, 2006
 *
 * Copyright (c) 2006, the JUNG Project and the Regents of the University 
 * of California
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * http://jung.sourceforge.net/license.txt for a description.
 */
package scratch.joshua.jung_2_0.core;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

import scratch.joshua.jung_2_0.utils.Pair;


public abstract class SimpleAbstractSparseGraph<V, E> implements Graph<V,E>
{
    public int inDegree(V vertex)
    {
        return this.getInEdges(vertex).size();
    }

    public int outDegree(V vertex)
    {
        return this.getOutEdges(vertex).size();
    }

    public boolean isPredecessor(V v1, V v2)
    {
        return this.getPredecessors(v1).contains(v2);
    }

    public boolean isSuccessor(V v1, V v2)
    {
        return this.getSuccessors(v1).contains(v2);
    }

    public int numPredecessors(V vertex)
    {
        return this.getPredecessors(vertex).size();
    }

    public int numSuccessors(V vertex)
    {
        return this.getSuccessors(vertex).size();
    }

    public boolean areNeighbors(V v1, V v2)
    {
        return this.getNeighbors(v1).contains(v2);
    }

    public boolean areIncident(V vertex, E edge)
    {
        return this.getIncidentEdges(vertex).contains(edge);
    }

    public int numNeighbors(V vertex)
    {
        return this.getNeighbors(vertex).size();
    }

    public int degree(V vertex)
    {
        return this.getIncidentEdges(vertex).size();
    }

    public V getOpposite(V vertex, E edge)
    {
        Pair<V> incident = this.getEndpoints(edge); 
        V first = incident.getFirst();
        V second = incident.getSecond();
        if (vertex.equals(first))
            return second;
        else if (vertex.equals(second))
            return first;
        else 
            throw new IllegalArgumentException(vertex + " is not incident to " + edge);
    }

    public Collection<V> getIncidentVertices(E edge)
    {
        Pair<V> endpoints = this.getEndpoints(edge);
        Collection incident = new ArrayList();
        incident.add(endpoints.getFirst());
        incident.add(endpoints.getSecond());
        
        return CollectionUtils.unmodifiableCollection(incident);
    }

}
