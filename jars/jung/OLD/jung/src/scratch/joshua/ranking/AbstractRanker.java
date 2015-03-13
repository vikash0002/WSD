/*
 * Created on Oct 14, 2005
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
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.decorators.ConstantEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberEdgeValue;
import edu.uci.ics.jung.graph.decorators.NumberVertexValue;

public abstract class AbstractRanker
{
    protected final static NumberEdgeValue UNIT_VALUE = new ConstantEdgeValue(1);
    
    protected ArchetypeGraph graph;
    protected NumberEdgeValue edge_value;
    
    public AbstractRanker(ArchetypeGraph g)
    {
        this(g, UNIT_VALUE);
    }
    
    public AbstractRanker(ArchetypeGraph g, NumberEdgeValue nev)
    {
        this.graph = g;
        this.edge_value = nev;
    }
    
    protected void normalizeValues(NumberVertexValue nvv, boolean squared)
    {
        double total = 0;
        for (Object o : graph.getVertices())
            total += nvv.getNumber((ArchetypeVertex)o).doubleValue();
        
        // nothing to do: return
        if (total == 0 || total == 1)
            return;
        
        // normalize values: divide each by total
        for (Object o : graph.getVertices())
        {
            ArchetypeVertex v = (ArchetypeVertex)o;
            double value = nvv.getNumber(v).doubleValue();
            nvv.setNumber(v, value / total);
        }

    }
    
    protected void normalizeValues(NumberVertexValue nvv)
    {
        normalizeValues(nvv, false);
    }
}
