/*
 * Created on Nov 3, 2005
 *
 * Copyright (c) 2005, the JUNG Project and the Regents of the University 
 * of California
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * http://jung.sourceforge.net/license.txt for a description.
 */
package scratch.joshua.ranking;

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;

/**
 * An interface for classes that can store and retrieve a
 * value for an edge with respect to a specific incident vertex.
 * This is useful, for example, for generating normalized 
 * edge values (such that all incident edge weights must sum to 1)
 * for undirected edges.
 * 
 * @author Joshua O'Madadhain
 */
public interface EdgeVertexNumberFunction extends NumberEdgeValue
{
    /**
     * 
     * @param e
     * @param v
     * @return
     * @throws IllegalArgumentException
     */
    public Number getNumber(ArchetypeEdge e, ArchetypeVertex v);
    
    /**
     * 
     * @param e
     * @param v
     * @param n
     * @throws IllegalArgumentException
     */
    public void setNumber(ArchetypeEdge e, ArchetypeVertex v, Number n);
}
