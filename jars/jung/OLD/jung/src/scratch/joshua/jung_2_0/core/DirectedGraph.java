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

public interface DirectedGraph<V,E extends DirectedEdge> extends Graph<V,E>
{
    boolean addDirectedEdge(E edge, V source, V dest);
    
    V getSource(E directed_edge);

    V getDest(E directed_edge);
    
    boolean isSource(V vertex, E edge); // get{Source, Dest}(e) == v
    
    boolean isDest(V vertex, E edge); // get{Source, Dest}(e) == v

}
