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

import edu.uci.ics.jung.graph.ArchetypeGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.decorators.MapNumberVertexValue;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberVertexValue;
import edu.uci.ics.jung.utils.GraphUtils;

public abstract class SingleValueRanker extends AbstractIterativeRanker
{
    public SingleValueRanker(ArchetypeGraph g, int max_iterations,
            double tolerance)
    {
        this(g, UNIFORM_OUT, max_iterations, tolerance);
    }

    public SingleValueRanker(ArchetypeGraph g, NumberVertexValue priors,
            int max_iterations, double tolerance)
    {
        this(g, priors, UNIFORM_OUT, max_iterations, tolerance);
    }
    
    public SingleValueRanker(ArchetypeGraph g, NumberEdgeValue edge_value,
            int max_iterations, double tolerance)
    {
        super(g, edge_value, max_iterations, tolerance);
    }

    public SingleValueRanker(ArchetypeGraph g, NumberVertexValue priors, 
            NumberEdgeValue edge_value, int max_iterations, double tolerance)
    {
        super(g, priors, edge_value, max_iterations, tolerance);
    }
    
    
    public void evaluate(NumberVertexValue in, NumberVertexValue out)
    {
        Graph g = (Graph)graph;
        int i = 0;
        double tolerance_value = Double.MAX_VALUE;
        NumberVertexValue source = new MapNumberVertexValue();
        GraphUtils.copyValues(g, in, source);
        NumberVertexValue dest = null;
        while (i++ < max_iterations && tolerance_value > tolerance)
        {
            dest = new MapNumberVertexValue();
            advance(source, dest);
            tolerance_value = getToleranceValue(0, source, dest);
//            System.out.println(tolerance_value);
            GraphUtils.copyValues(g, dest, source);
        }
        
//        System.out.println("iterations required: " + i);
        GraphUtils.copyValues(g, dest, out);
    }

    public void evaluate(NumberVertexValue out)
    {
        evaluate(getNormalizedInitialValues(graph), out);
    }
    
    public abstract void advance(NumberVertexValue in, NumberVertexValue out);

    public void advance(NumberVertexValue out)
    {
        advance(getNormalizedInitialValues(graph), out);
    }


}
