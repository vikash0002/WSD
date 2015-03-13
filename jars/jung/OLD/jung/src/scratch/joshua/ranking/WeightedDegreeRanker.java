/*
 * Created on May 26, 2005
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

import java.util.Iterator;

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.ConstantEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberVertexValue;


public class WeightedDegreeRanker extends AbstractRanker
{
    protected boolean normalized;
    
    public WeightedDegreeRanker(Graph g, NumberEdgeValue edge_weights, boolean normalized)
    {
        super(g, edge_weights);
        this.normalized = normalized;
    }
    
    public WeightedDegreeRanker(Graph g, NumberEdgeValue edge_weights)
    {
        this(g, edge_weights, false);
    }
    
    public WeightedDegreeRanker(Graph g)
    {
        this(g, new ConstantEdgeValue(1.0));
    }
    
    public WeightedDegreeRanker(Graph g, boolean normalized)
    {
        this(g, new ConstantEdgeValue(1.0), normalized);
    }

    public void evaluate(NumberVertexValue degrees)
    {
        for (Iterator iter = graph.getVertices().iterator(); iter.hasNext(); )
        {
            Vertex v = (Vertex)iter.next();
            double in_sum = 0;
            for (Iterator i_iter = v.getIncidentEdges().iterator(); i_iter.hasNext(); )
            {
                ArchetypeEdge e = (ArchetypeEdge)i_iter.next();
                in_sum += edge_value.getNumber(e).doubleValue();
            }
            degrees.setNumber(v, new Double(in_sum));
        }
        if (normalized)
            normalizeValues(degrees);
    }
    
    public void evaluateIncoming(NumberVertexValue in)
    {
        for (Iterator iter = graph.getVertices().iterator(); iter.hasNext(); )
        {
            Vertex v = (Vertex)iter.next();
            double in_sum = 0;
            for (Iterator i_iter = v.getInEdges().iterator(); i_iter.hasNext(); )
            {
                Edge e = (Edge)i_iter.next();
                in_sum += edge_value.getNumber(e).doubleValue();
            }
            in.setNumber(v, new Double(in_sum));
        }
        if (normalized)
            normalizeValues(in);
    }

    public void evaluateOutgoing(NumberVertexValue out)
    {
        for (Iterator iter = graph.getVertices().iterator(); iter.hasNext(); )
        {
            Vertex v = (Vertex)iter.next();
            double out_sum = 0;
            for (Iterator i_iter = v.getOutEdges().iterator(); i_iter.hasNext(); )
            {
                Edge e = (Edge)i_iter.next();
                out_sum += edge_value.getNumber(e).doubleValue();
            }
            out.setNumber(v, new Double(out_sum));
        }
        if (normalized)
            normalizeValues(out);
    }
    
    public void evaluate(NumberVertexValue in, NumberVertexValue out)
    {
        for (Iterator iter = graph.getVertices().iterator(); iter.hasNext(); )
        {
            Vertex v = (Vertex)iter.next();
            double in_sum = 0;
            for (Iterator i_iter = v.getInEdges().iterator(); i_iter.hasNext(); )
            {
                Edge e = (Edge)i_iter.next();
                in_sum += edge_value.getNumber(e).doubleValue();
            }
            in.setNumber(v, new Double(in_sum));
            
            double out_sum = 0;
            for (Iterator i_iter = v.getOutEdges().iterator(); i_iter.hasNext(); )
            {
                Edge e = (Edge)i_iter.next();
                out_sum += edge_value.getNumber(e).doubleValue();
            }
            out.setNumber(v, new Double(out_sum));
        }
        if (normalized)
        {
            normalizeValues(in);
            normalizeValues(out);
        }
    }
}
