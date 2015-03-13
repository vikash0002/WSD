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
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.ArchetypeGraph;

import java.util.*;

/**
 * @author Scott White
 */
public class VertexPartitionClass extends AbstractPartitionClass {

    public VertexPartitionClass(AbstractPartition p) {
        super(p);
    }

    public ArchetypeGraph constructGraph() {
        ArchetypeGraph newGraph = getUnderlyingPartition().getUnderlyingGraph().newInstance();

        Map correspondingVertexMap = new HashMap();

        for (Iterator vIt=getElements().iterator();vIt.hasNext();) {
            ArchetypeVertex currentVertex = (ArchetypeVertex) vIt.next();
            ArchetypeVertex newVertex = currentVertex.copy(newGraph);
             //Add explicit call to newGraph.addVertex(newVertex)?
            correspondingVertexMap.put(currentVertex,newVertex);
        }

        ArchetypeGraph originalGraph = getUnderlyingPartition().getUnderlyingGraph();
        for (Iterator edgeIt=originalGraph.getEdges().iterator();edgeIt.hasNext();) {
            ArchetypeEdge currentEdge = (ArchetypeEdge) edgeIt.next();

            Set connectedVertices = currentEdge.getIncidentVertices();

            for (Iterator vIt = connectedVertices.iterator();vIt.hasNext();) {
                ArchetypeVertex currentVertex = (ArchetypeVertex) vIt.next();
                if (correspondingVertexMap.get(currentVertex) == null) {
                    break;
                }
            }

            List newVertices = new ArrayList();
            for (Iterator vIt = connectedVertices.iterator();vIt.hasNext();) {
                ArchetypeVertex currentVertex = (ArchetypeVertex) vIt.next();
                newVertices.add(correspondingVertexMap.get(currentVertex));
            }

            // changed call to new version of copy; see AbstractEdge.copy()
            // for details (Joshua, 2 May 2003)
            // ArchetypeEdge newEdge = currentEdge.copy(newGraph,newVertices);
            ArchetypeEdge newEdge = currentEdge.copy(newGraph);
            //Add explicit call to newGraph.add(newEdge)?

        }


        return newGraph;
    }

}
