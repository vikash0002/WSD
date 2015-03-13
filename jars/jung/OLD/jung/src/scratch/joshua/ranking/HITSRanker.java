/*
 * Created on May 9, 2005
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

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.MapNumberVertexValue;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberVertexValue;
import edu.uci.ics.jung.utils.GraphUtils;

public class HITSRanker extends AbstractIterativeRanker
{
    public HITSRanker(Graph g, int max_iterations, double tolerance)
    {
        super(g, max_iterations, tolerance);
    }

    public HITSRanker(Graph g, NumberEdgeValue edge_value, int max_iterations, double tolerance)
    {
        super(g, edge_value, max_iterations, tolerance);
    }

    /**
     * Essentially, calls advance() until one of the termination conditions
     * is met (max iterations passed or all changes below tolerance).
     * @param g
     * @param auth_in
     * @param hub_in
     * @param auth_out
     * @param hub_out
     */
    public void evaluate(NumberVertexValue auth_in, NumberVertexValue hub_in, 
            NumberVertexValue auth_out, NumberVertexValue hub_out)
    {
        Graph g = (Graph)graph;
        int i = 0;
        double max_change = Double.MAX_VALUE ;
        NumberVertexValue auth_source = new MapNumberVertexValue();
        GraphUtils.copyValues(g, auth_in, auth_source);
        NumberVertexValue hub_source = new MapNumberVertexValue();
        GraphUtils.copyValues(g, hub_in, hub_source);
        NumberVertexValue auth_dest = null;
        NumberVertexValue hub_dest = null;
        
        while (i++ < max_iterations && max_change > tolerance)
        {
            max_change = 0;
            auth_dest = new MapNumberVertexValue();
            hub_dest = new MapNumberVertexValue();

            advance(auth_source, hub_source, auth_dest, hub_dest);
            max_change = getToleranceValue(max_change, auth_source, auth_dest);
            max_change = getToleranceValue(max_change, hub_source, hub_dest);
//            System.out.println(max_change);

            GraphUtils.copyValues(g, auth_dest, auth_source);
            GraphUtils.copyValues(g, hub_dest, hub_source);
        }
        
//        System.out.println("iterations required: " + i);
        GraphUtils.copyValues(g, auth_dest, auth_out);
        GraphUtils.copyValues(g, hub_dest, hub_out);
    }
    
    public void evaluate(NumberVertexValue auth_out, NumberVertexValue hub_out)
    {
        evaluate(getNormalizedInitialValues(graph, true), getNormalizedInitialValues(graph, true), auth_out, hub_out);
    }

    public void advance(NumberVertexValue auth_in, NumberVertexValue hub_in, 
            NumberVertexValue auth_out, NumberVertexValue hub_out)
    {
        Graph g = (Graph)graph;
        if (auth_in == auth_out || hub_in == hub_out)
            throw new IllegalArgumentException("Input and output value decorators must be distinct");
        
        for (Object av : g.getVertices())
        {
            Vertex v = (Vertex)av;
            
            double auth = 0;
            for (Object o : v.getInEdges())
            {
                Edge e = (Edge)o;
                Vertex w = e.getOpposite(v);
                auth += (hub_in.getNumber(w).doubleValue() * 
                         edge_value.getNumber(e).doubleValue());
            }
            auth_out.setNumber(v, auth);
            
            double hub = 0;
            for (Object o : v.getOutEdges())
            {
                Edge e = (Edge)o;
                Vertex x = e.getOpposite(v);
                hub += (auth_in.getNumber(x).doubleValue() * 
                        edge_value.getNumber(e).doubleValue());
            }
            hub_out.setNumber(v, hub);
        }
        
        normalizeValues(auth_out, true);
        normalizeValues(hub_out, true);
    }
    
    public void advance(NumberVertexValue auth_out, NumberVertexValue hub_out)
    {
        advance(getNormalizedInitialValues(graph, true), getNormalizedInitialValues(graph, true), auth_out, hub_out);
    }
}
