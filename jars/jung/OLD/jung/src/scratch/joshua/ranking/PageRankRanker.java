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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.DirectedEdge;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberVertexValue;

public class PageRankRanker extends SingleValueRanker
{
    protected double alpha;
    
    public PageRankRanker(Graph g, int max_iterations, double tolerance, double alpha)
    {
        this(g, UNIFORM_INCIDENT, max_iterations, tolerance, alpha);
    }

    public PageRankRanker(Graph g, NumberEdgeValue edge_value,
            int max_iterations, double tolerance, double alpha)
    {
        super(g, edge_value, max_iterations, tolerance);
        initialize(alpha);
    }

    public PageRankRanker(Graph g, NumberVertexValue priors, NumberEdgeValue edge_value, 
            int max_iterations, double tolerance, double alpha)
    {
        super(g, priors, edge_value, max_iterations, tolerance);
        initialize(alpha);
    }

    protected void initialize(double alpha)
    {
        if (alpha < 0 || alpha > 1.0)
            throw new IllegalArgumentException("alpha must be in [0, 1]");
        this.alpha = alpha;
    }
    
    public void advance(NumberVertexValue in, NumberVertexValue out)
    {
        Graph g = (Graph)graph;
        if (in == out)
            throw new IllegalArgumentException("Input and output value decorators must be distinct");
        
        double disappearing_potential = 0;
        
        for (Object o : g.getVertices())
        {
            Vertex v = (Vertex)o;
            
            if (v.outDegree() == 0)
                disappearing_potential += in.getNumber(v).doubleValue();
            
            double total_input = 0;
            for (Object i : v.getInEdges())
            {
                Edge e = (Edge)i;
                Vertex w = e.getOpposite(v);
                double value;
                if (edge_value instanceof EdgeVertexNumberFunction)
                    value = ((EdgeVertexNumberFunction)edge_value).getNumber(e, w).doubleValue();
                else
                    value = edge_value.getNumber(e).doubleValue();
                total_input += (in.getNumber(w).doubleValue() * value);
            }
            
            // modify total_input according to alpha
            out.setNumber(v, total_input * (1 - alpha) + 
                    priors.getNumber(v).doubleValue() * alpha);
        }
        
        // give everyone an equal share of the disappearing potential
        if (disappearing_potential > 0)
        {
            double to_add = (1 - alpha) * (disappearing_potential / g.numVertices());
            for (Object o : g.getVertices())
            {
                Vertex v = (Vertex)o;
                
                double value = out.getNumber(v).doubleValue();
                out.setNumber(v, value + to_add);
    
            }
        }
    }
    
    public static class SourceNormalizedEdgeValue implements NumberEdgeValue
    {
        protected NumberEdgeValue weights;
        protected Map normalized_weights = new HashMap();

        public SourceNormalizedEdgeValue(NumberEdgeValue weights)
        {
            this.weights = weights;
        }

        public Number getNumber(ArchetypeEdge ae)
        {
            Number n = (Number) normalized_weights.get(ae);
            if (n == null)
            {
                DirectedEdge e = (DirectedEdge) ae;
                Set out_edges = e.getSource().getOutEdges();

                // take sum of weights of related outgoing edges...
                double weight_sum = 0;
                for (Iterator iter = out_edges.iterator(); iter.hasNext();)
                {
                    Edge f = (Edge) iter.next();
                    weight_sum += weights.getNumber(f).doubleValue();
                }

                // ...and set this weight for each one to be this weight divided
                // by the sum
                for (Iterator iter = out_edges.iterator(); iter.hasNext();)
                {
                    Edge f = (Edge) iter.next();
                    double weight = weights.getNumber(f).doubleValue();
                    setNumber(f, new Double(weight / weight_sum));
                }
                n = (Number) normalized_weights.get(ae);
            }
            return n;
        }

        public void setNumber(ArchetypeEdge e, Number n)
        {
            normalized_weights.put(e, n);
        }

    }

}
