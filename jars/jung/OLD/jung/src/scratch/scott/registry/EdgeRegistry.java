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
import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.decorators.Decorator;

import java.util.Iterator;

/**
 * @author Scott White
 */
public class EdgeRegistry extends UserDataRegistry {

    public EdgeRegistry(Graph graph) {
       super(graph);
    }

    public boolean allDataIsDecorated(Decorator decorator) {
        for (Iterator vIt = getGraph().getEdges().iterator(); vIt.hasNext();) {
            ArchetypeEdge currentEdge = (ArchetypeEdge) vIt.next();
            if (currentEdge.getUserDatum(decorator.getKey()) == null) {
                return false;
            }
        }
        return true;
    }
}
