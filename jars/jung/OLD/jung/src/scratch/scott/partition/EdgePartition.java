/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.scott.partition;

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.ArchetypeGraph;

import java.util.Iterator;

/**
 * @author Scott White
 */
public class EdgePartition extends AbstractPartition {

    public EdgePartition(ArchetypeGraph g) {
        initialize(g);
    }

    AbstractPartitionClass createNewPartitionClass() {
        return new EdgePartitionClass(this);
    }

    protected void initialize(ArchetypeGraph g) {
        super.initialize(g);
        AbstractPartitionClass pc = addNewPartitionClass();
        for (Iterator edgeIt = g.getEdges().iterator();edgeIt.hasNext();) {
            ArchetypeEdge currentEdge = (ArchetypeEdge) edgeIt.next();
            pc.add(currentEdge);
            getElementToClassMap().put(currentEdge,pc);
        }

    }
}
