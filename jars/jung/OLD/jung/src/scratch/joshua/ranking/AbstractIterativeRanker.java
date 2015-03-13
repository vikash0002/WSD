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

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.ArchetypeGraph;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.DirectedEdge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.MapNumberVertexValue;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberVertexValue;

public abstract class AbstractIterativeRanker extends AbstractRanker
{
    protected static final NumberEdgeValue UNIFORM_OUT = new UniformOut();
    protected static final EdgeVertexNumberFunction UNIFORM_INCIDENT = new UniformIncident();

    protected NumberVertexValue priors;
    protected int max_iterations;
    protected double tolerance;
    
    public AbstractIterativeRanker(ArchetypeGraph g, int max_iterations, double tolerance)
    {
        this(g, UNIT_VALUE, max_iterations, tolerance);
    }

    public AbstractIterativeRanker(ArchetypeGraph g, NumberVertexValue priors,
            int max_iterations, double tolerance)
    {
        this(g, priors, UNIT_VALUE, max_iterations, tolerance);
    }
    
    public AbstractIterativeRanker(ArchetypeGraph g, NumberEdgeValue edge_value, 
            int max_iterations, double tolerance)
    {
        this(g, getNormalizedInitialValues(g), edge_value, max_iterations, tolerance);
    }

    public AbstractIterativeRanker(ArchetypeGraph g, NumberVertexValue priors, 
            NumberEdgeValue edge_value, int max_iterations, double tolerance)
    {
        super(g, edge_value);
        this.priors = priors;
        this.max_iterations = max_iterations;
        this.tolerance = tolerance;
    }

    
    /**
     * Returns the maximum of largest single difference between any value in
     * <code>old_value</code> and the corresponding value in 
     * <code>new_value</code>.
     * Override this if you want to calculate the tolerance value in a
     * different way (e.g., mean difference over all vertices).
     * @param cur_tolerance
     * @param old_value
     * @param new_value
     * @return
     */
    public double getToleranceValue(double cur_tolerance, NumberVertexValue old_value, NumberVertexValue new_value)
    {
        double max_change = cur_tolerance;
        for (Object o : graph.getVertices())
        {
            ArchetypeVertex v = (ArchetypeVertex)o;
            max_change = Math.max(max_change, 
                    old_value.getNumber(v).doubleValue() - new_value.getNumber(v).doubleValue());
        }
        return max_change;
    }
    
    protected boolean hasConverged(ArchetypeVertex v, NumberVertexValue first, NumberVertexValue second)
    {
        return Math.abs(first.getNumber(v).doubleValue() - second.getNumber(v).doubleValue()) < tolerance;
    }
    
    /**
     * Returns <code>NumberVertexValue</code> with uniformly distributed values
     * such that their sum (of their squares if <code>squared</code> is 
     * <code>true</code>) over all vertices in <code>g</code> is 1. 
     */
    protected static NumberVertexValue getNormalizedInitialValues(ArchetypeGraph graph, boolean squared)
    {
        NumberVertexValue nvv = new MapNumberVertexValue();
        
        // values are normalized so that sum of (squared?) values = 1
        double value = 1.0 / graph.numVertices();
        if (squared)
            value = Math.sqrt(value);
        for (Object o : graph.getVertices())
            nvv.setNumber((ArchetypeVertex)o, value);
        
        return nvv;
    }

    /**
     * Returns <code>NumberVertexValue</code> with uniformly distributed values
     * such that their sum, over all vertices in <code>g</code>, is 1. 
     */
    protected static NumberVertexValue getNormalizedInitialValues(ArchetypeGraph g)
    {
        return getNormalizedInitialValues(g, false);
    }
    
    
    protected static class UniformOut implements NumberEdgeValue
    {
        public Number getNumber(ArchetypeEdge ae)
        {
            DirectedEdge e = (DirectedEdge)ae;
            Vertex source = e.getSource();
            return new Double(1.0 / source.outDegree());
        }

        public void setNumber(ArchetypeEdge e, Number n)
        {
            throw new UnsupportedOperationException();
        }
    }
    
    protected static class UniformIncident implements EdgeVertexNumberFunction
    {
        public Number getNumber(ArchetypeEdge e, ArchetypeVertex v)
        {
            if (!e.isIncident(v))
                throw new IllegalArgumentException("edge and vertex must be incident");
            if (e instanceof DirectedEdge)
                return getNumber(e);
            return new Double(1.0 / v.degree());
        }

        public Number getNumber(ArchetypeEdge ae)
        {
            DirectedEdge e = (DirectedEdge)ae;
            Vertex source = e.getSource();
            return new Double(1.0 / source.outDegree());
        }
        
        public void setNumber(ArchetypeEdge e, ArchetypeVertex v, Number n)
        {
            throw new UnsupportedOperationException();
        }

        public void setNumber(ArchetypeEdge e, Number n)
        {
            throw new UnsupportedOperationException();
        }
    }
}


