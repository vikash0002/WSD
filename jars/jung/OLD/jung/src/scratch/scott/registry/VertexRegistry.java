/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.scott.registry;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.decorators.Decorator;
import scratch.scott.registry.UserDataRegistry;

import java.util.Iterator;

/**
 * @author Scott White
 */
public class VertexRegistry extends UserDataRegistry {

    public VertexRegistry(Graph graph) {
       super(graph);
    }

    public boolean allDataIsDecorated(Decorator decorator) {
        for (Iterator vIt = getGraph().getVertices().iterator(); vIt.hasNext();) {
            ArchetypeVertex currentVertex = (ArchetypeVertex) vIt.next();
            if (currentVertex.getUserDatum(decorator.getKey()) == null) {
                return false;
            }
        }
        return true;
    }
}
