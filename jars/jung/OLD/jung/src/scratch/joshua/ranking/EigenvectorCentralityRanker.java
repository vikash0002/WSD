/*
 * Created on May 18, 2005
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

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberVertexValue;

public class EigenvectorCentralityRanker extends PageRankRanker
{

    public EigenvectorCentralityRanker(Graph g, int max_iterations,
            double tolerance)
    {
        super(g, max_iterations, tolerance, 0);
    }

    public EigenvectorCentralityRanker(Graph g, NumberEdgeValue edge_value,
            int max_iterations, double tolerance)
    {
        super(g, edge_value, max_iterations, tolerance, 0);
    }

    public EigenvectorCentralityRanker(Graph g, NumberVertexValue priors,
            NumberEdgeValue edge_value, int max_iterations, double tolerance)
    {
        super(g, priors, edge_value, max_iterations, tolerance, 0);
    }

}
