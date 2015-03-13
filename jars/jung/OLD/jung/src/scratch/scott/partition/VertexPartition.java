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

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.ArchetypeGraph;

import java.util.Iterator;

/**
 * @author Scott White
 */
public class VertexPartition extends AbstractPartition {

    public VertexPartition(ArchetypeGraph g) {
        initialize(g);
    }

    public VertexPartition(ArchetypeGraph g, int numClasses) {
        initialize(g);
        if (numClasses > 1) {
            addNewPartitionClasses(numClasses-1);
        }
    }

    AbstractPartitionClass createNewPartitionClass() {
        return new VertexPartitionClass(this);
    }

    protected void initialize(ArchetypeGraph g) {
        super.initialize(g);
        AbstractPartitionClass pc = addNewPartitionClass();
        for (Iterator vertexIt = g.getVertices().iterator();vertexIt.hasNext();) {
            ArchetypeVertex currentVertex = (ArchetypeVertex) vertexIt.next();
            pc.add(currentVertex);
            getElementToClassMap().put(currentVertex,pc);
        }
    }
}
