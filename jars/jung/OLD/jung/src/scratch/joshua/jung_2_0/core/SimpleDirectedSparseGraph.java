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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import scratch.joshua.jung_2_0.utils.Pair;


public class SimpleDirectedSparseGraph<V,E extends DirectedEdge> 
    extends SimpleAbstractSparseGraph<V,E>
    implements DirectedGraph<V,E>
{
    protected Map<V, Pair<Set<E>>> vertices; // Map of vertices to Pair of adjacency sets {incoming, outgoing}
    protected Map<E, Pair<V>> edges;            // Map of edges to incident vertex pairs

    public SimpleDirectedSparseGraph()
    {
        vertices = new HashMap();
        edges = new HashMap();
    }
    
    public Collection getEdges()
    {
        return CollectionUtils.unmodifiableCollection(edges.keySet());
    }

    public Collection getVertices()
    {
        return CollectionUtils.unmodifiableCollection(vertices.keySet());
    }

    public boolean addVertex(V vertex)
    {
        if (!vertices.containsKey(vertex))
        {
            vertices.put(vertex, new Pair<Set<E>>(new HashSet<E>(), new HashSet<E>()));
            return true;
        }
        else
            return false;
    }

    public boolean removeVertex(V vertex)
    {
        Pair<Set<E>> adj_set = vertices.remove(vertex);
        if (adj_set == null)
            return false;
        
        for (E edge : adj_set.getFirst())
            removeEdge(edge);
        for (E edge : adj_set.getSecond())
            removeEdge(edge);
        
        return true;
    }
    
    public boolean removeEdge(E edge)
    {
        if (!edges.containsKey(edge))
            return false;
        
        Pair<V> endpoints = this.getEndpoints(edge);
        V source = endpoints.getFirst();
        V dest = endpoints.getSecond();
        
        // remove edge from incident vertices' adjacency sets
        vertices.get(source).getSecond().remove(edge);
        vertices.get(dest).getFirst().remove(edge);
        
        edges.remove(edge);
        return true;
    }

    
    public Collection<E> getInEdges(V vertex)
    {
        return CollectionUtils.unmodifiableCollection(vertices.get(vertex).getFirst());
    }

    public Collection<E> getOutEdges(V vertex)
    {
        return CollectionUtils.unmodifiableCollection(vertices.get(vertex).getSecond());
    }

    public Collection<V> getPredecessors(V vertex)
    {
        Set<E> incoming = vertices.get(vertex).getFirst();        
        Set<V> preds = new HashSet();
        for (E edge : incoming)
            preds.add(this.getSource(edge));
        
        return CollectionUtils.unmodifiableCollection(preds);
    }

    public Collection<V> getSuccessors(V vertex)
    {
        Set<E> outgoing = vertices.get(vertex).getSecond();        
        Set<V> succs = new HashSet();
        for (E edge : outgoing)
            succs.add(this.getDest(edge));
        
        return CollectionUtils.unmodifiableCollection(succs);
    }

    public Collection getNeighbors(V vertex)
    {
        return CollectionUtils.union(this.getPredecessors(vertex), this.getSuccessors(vertex));
    }

    public Collection getIncidentEdges(V vertex)
    {
        return CollectionUtils.union(this.getInEdges(vertex), this.getOutEdges(vertex));
    }

    public E findEdge(V v1, V v2)
    {
        Set<E> outgoing = vertices.get(v1).getSecond();
        for (E edge : outgoing)
            if (this.getDest(edge).equals(v2))
                return edge;
        
        return null;
    }

    /**
     * Adds <code>edge</code> to the graph.  Also adds 
     * <code>source</code> and <code>dest</code> to the graph if they
     * are not already present.  Returns <code>false</code> if 
     * the specified edge is 
     */
    public boolean addDirectedEdge(E edge, V source, V dest)
    {
        if (edges.containsKey(edge))
        {
            Pair<V> endpoints = edges.get(edge);
            Pair<V> new_endpoints = new Pair<V>(source, dest);
            if (!endpoints.equals(new_endpoints))
                throw new IllegalArgumentException("Edge " + edge + 
                        " exists in this graph with endpoints " + source + ", " + dest);
            else
                return false;
        }
        
        if (!vertices.containsKey(source))
            this.addVertex(source);
        
        if (!vertices.containsKey(dest))
            this.addVertex(dest);
        
        Pair<V> endpoints = new Pair<V>(source, dest);
        edges.put(edge, endpoints);
        vertices.get(source).getSecond().add(edge);        
        vertices.get(dest).getFirst().add(edge);        
        
        return true;
    }

    public V getSource(E edge)
    {
        return this.getEndpoints(edge).getFirst();
    }

    public V getDest(E edge)
    {
        return this.getEndpoints(edge).getSecond();
    }

    public boolean isSource(V vertex, E edge)
    {
        return vertex.equals(this.getEndpoints(edge).getFirst());
    }

    public boolean isDest(V vertex, E edge)
    {
        return vertex.equals(this.getEndpoints(edge).getSecond());
    }

    public Pair<V> getEndpoints(E edge)
    {
        return edges.get(edge);
    }
}
