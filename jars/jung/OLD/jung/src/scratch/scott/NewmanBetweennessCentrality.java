/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.scott;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import edu.uci.ics.jung.algorithms.connectivity.BFSDistanceLabeler;
import edu.uci.ics.jung.algorithms.importance.AbstractRanker;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.NumericDecorator;
import edu.uci.ics.jung.utils.MutableDouble;
import edu.uci.ics.jung.utils.UserData;

/**
 * Computes betweenness centrality for each vertex and edge in the graph.
 * The result is that each vertex and edge has a <code>UserData</code> element of type <code>MutableDouble</code> whose key is "centrality.BetweennessCentrality."
 * Note: Many social network researchers like to normalize the betweenness values by dividing the values by (n-1)(n-2)/2.
 * The values given here are unnormalized.
 * <p>
 *
 * A simple example of usage is:
 * <pre>
 * BetweennessCentrality ranker = new BetweennessCentrality(someGraph,true);
 * ranker.evaluate();
 * ranker.printRankings();
 * </pre>
 *
 * Running time is: O(mn)
 * @see "Mark Newman: Who is the best connected scientist? A study of scientific coauthorship networks. Physics Review, E64, 2001."
 *  @author Scott White
 */
public class NewmanBetweennessCentrality extends AbstractRanker {
    public static final String CENTRALITY = "centrality.BetweennessCentrality";

    /**
     * Constructor which initializes the algorithm
     * @param g graph whose nodes are to be analysed
     * @param rankNodes if true, computes node betweenness centrality; if false, computed edge betweenness centrality
     */
//    public NewmanBetweennessCentrality(Graph g, boolean rankNodes) {
//        initialize(g, rankNodes);
//    }
    
    public NewmanBetweennessCentrality(Graph g, boolean rankNodes, 
        boolean rankEdges)
    {
        initialize(g, rankNodes, rankEdges);
    }

    protected void computeBetweenness(Graph graph) {

        String distanceKey = "DISTANCE";
        BFSDistanceLabeler distanceLabeler = new BFSDistanceLabeler(distanceKey);

        NumericDecorator betweennessDecorator = new NumericDecorator(CENTRALITY, UserData.SHARED);

        String currentBCKey = "centrality.CURRENT_BC";
        NumericDecorator currentBetweennessDecorator = new NumericDecorator(currentBCKey, UserData.SHARED);

        for (Iterator vIt = graph.getVertices().iterator(); vIt.hasNext();) {
            betweennessDecorator.setValue(new MutableDouble(0), (Vertex) vIt.next());
        }

        for (Iterator vIt = graph.getVertices().iterator(); vIt.hasNext();) {
            Vertex currentVertex = (Vertex) vIt.next();

            for (Iterator v2It = graph.getVertices().iterator(); v2It.hasNext();) {
                Vertex v = (Vertex) v2It.next();
                currentBetweennessDecorator.setValue(new MutableDouble(1), v);
            }

            distanceLabeler.labelDistances(graph, currentVertex);
            List visitedVertices = distanceLabeler.getVerticesInOrderVisited();
            Collections.reverse(visitedVertices);
            for (Iterator targetIt = visitedVertices.iterator(); targetIt.hasNext();) {
                Vertex currentTarget = (Vertex) targetIt.next();
                Set predecessors = distanceLabeler.getPredecessors(currentTarget);
                int numPredecessors = predecessors.size();

                double targetValue = currentBetweennessDecorator.getValue(currentTarget).doubleValue() / numPredecessors;

                for (Iterator predIt = predecessors.iterator(); predIt.hasNext();) {
                    Vertex predecessor = (Vertex) predIt.next();

                    if (isRankingEdges()) {
                        Edge currentEdge = predecessor.findEdge(currentTarget);
                        //MutableDouble edgeValue = (MutableDouble) betweennessDecorator.getValue(currentEdge);
                        //edgeValue.add(targetValue);
                    }

                    if (predecessor == currentVertex) {
                        continue;
                    }

                    MutableDouble predecessorValue = (MutableDouble) currentBetweennessDecorator.getValue(predecessor);
                    predecessorValue.add(targetValue);
                }
            }
            if (isRankingNodes()) {
                for (Iterator v2It = graph.getVertices().iterator(); v2It.hasNext();) {
                    Vertex v = (Vertex) v2It.next();
                    double currentVal = currentBetweennessDecorator.getValue(v).doubleValue();
                    MutableDouble currentBC = (MutableDouble) betweennessDecorator.getValue(v);
                    currentBC.add(currentVal - 1.0);
                }
            }
        }

        for (Iterator vIt = graph.getVertices().iterator(); vIt.hasNext();) {
            Vertex v = (Vertex) vIt.next();
            if (graph instanceof UndirectedGraph) {
                MutableDouble bcValue = (MutableDouble) betweennessDecorator.getValue(v);
                if (bcValue != null) {
                    bcValue.setDoubleValue(bcValue.doubleValue() / 2.0);
                }

            }
            v.removeUserDatum(distanceKey);
            currentBetweennessDecorator.removeValue(v);
        }
    }

    /**
     * The user datum key used to store the rank scores.
     * @return the key
     */
    public String getRankScoreKey() {
        return CENTRALITY;
    }

    protected double evaluateIteration() {
        computeBetweenness(getGraph());
        return 0;
    }
}
