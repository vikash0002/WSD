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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Scott White
 */
public class TestGraphCollapser extends TestCase {
    public static Test suite() {
        return new TestSuite(TestGraphCollapser.class);
    }

    protected void setUp() {

    }

	public void testTheEmptyTest() {
	}

// COMMENTED OUT UNTIL FIXED

//    public void testCollapser() {
//        String edgeWeightKey = "WEIGHT";
//        DirectedSparseGraph graph = new DirectedSparseGraph();
//        graph.addVertices(8);
//
//        Edge e = graph.addEdge(graph.getVertex(0),graph.getVertex(1));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.3),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(0),graph.getVertex(2));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.7),UserData.SHARED);
//
//        e = graph.addEdge(graph.getVertex(1),graph.getVertex(0));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.7),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(1),graph.getVertex(3));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.3),UserData.SHARED);
//
//        e = graph.addEdge(graph.getVertex(2),graph.getVertex(0));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.3),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(2),graph.getVertex(3));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.4),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(2),graph.getVertex(4));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.2),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(2),graph.getVertex(5));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.1),UserData.SHARED);
//
//        e = graph.addEdge(graph.getVertex(3),graph.getVertex(1));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.7),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(3),graph.getVertex(2));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.1),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(3),graph.getVertex(5));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.2),UserData.SHARED);
//
//        e = graph.addEdge(graph.getVertex(4),graph.getVertex(2));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.1),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(4),graph.getVertex(5));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.8),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(4),graph.getVertex(6));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.1),UserData.SHARED);
//
//        e = graph.addEdge(graph.getVertex(5),graph.getVertex(3));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.5),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(5),graph.getVertex(6));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.2),UserData.SHARED);
//        e = graph.addEdge(graph.getVertex(5),graph.getVertex(7));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(0.3),UserData.SHARED);
//
//        e = graph.addEdge(graph.getVertex(7),graph.getVertex(5));
//        e.setUserDatum(edgeWeightKey,new MutableDouble(1),UserData.SHARED);
//
//        Set rootSet = new HashSet();
//        rootSet.add(graph.getVertex(0));
//        rootSet.add(graph.getVertex(1));
//
//        GraphCollapser collapser = new GraphCollapser(graph,rootSet,100,edgeWeightKey);
//        DirectedGraph graph1 = collapser.collapseNext();
//        Assert.assertTrue(graph1.numEdges() == 13);
//
//        DirectedEdge selfLoop = (DirectedEdge) ((Vertex)graph1.getVertex(-1)).findEdge((Vertex)graph1.getVertex(-1));
//        double weight = ((MutableDouble) selfLoop.getUserDatum(edgeWeightKey)).doubleValue();
//        Assert.assertTrue(NumericalPrecision.equal(weight,2.4,.00001));
//
//        DirectedEdge edge1 = (DirectedEdge) ((Vertex)graph1.getVertex(2)).findEdge((Vertex)graph1.getVertex(-1));
//        weight = ((MutableDouble) edge1.getUserDatum(edgeWeightKey)).doubleValue();
//        Assert.assertTrue(NumericalPrecision.equal(weight,0.3,.00001));
//
//        DirectedEdge edge2 = (DirectedEdge) ((Vertex)graph1.getVertex(-1)).findEdge((Vertex)graph1.getVertex(3));
//        weight = ((MutableDouble) edge2.getUserDatum(edgeWeightKey)).doubleValue();
//        Assert.assertTrue(NumericalPrecision.equal(weight,0.5,.00001));
//
//        DirectedGraph graph2 = collapser.collapseNext();
//        Assert.assertTrue(graph2.numEdges() == 12);
//
//        selfLoop = (DirectedEdge) ((Vertex)graph2.getVertex(-1)).findEdge((Vertex) graph2.getVertex(-1));
//        weight = ((MutableDouble) selfLoop.getUserDatum(edgeWeightKey)).doubleValue();
//        Assert.assertTrue(NumericalPrecision.equal(weight,0.5,.00001));
//
//        edge1 = (DirectedEdge) ((Vertex)graph2.getVertex(4)).findEdge((Vertex)graph2.getVertex(-1));
//        weight = ((MutableDouble) edge1.getUserDatum(edgeWeightKey)).doubleValue();
//        Assert.assertTrue(NumericalPrecision.equal(weight,0.2,.00001));
//
//        edge2 = (DirectedEdge) ((Vertex)graph2.getVertex(0)).findEdge((Vertex)graph2.getVertex(-1));
//        weight = ((MutableDouble) edge2.getUserDatum(edgeWeightKey)).doubleValue();
//        Assert.assertTrue(NumericalPrecision.equal(weight,0.7,.00001));
//
//        DirectedGraph graph3 = collapser.collapseNext();
//        Assert.assertTrue(graph3.numEdges() == 10);
//
//        selfLoop = (DirectedEdge) ((Vertex)graph3.getVertex(-1)).findEdge((Vertex)graph3.getVertex(-1));
//        weight = ((MutableDouble) selfLoop.getUserDatum(edgeWeightKey)).doubleValue();
//        Assert.assertTrue(NumericalPrecision.equal(weight,2.4,.00001));
//
//        edge1 = (DirectedEdge) ((Vertex)graph3.getVertex(0)).findEdge((Vertex)graph3.getVertex(-1));
//        weight = ((MutableDouble) edge1.getUserDatum(edgeWeightKey)).doubleValue();
//        Assert.assertTrue(NumericalPrecision.equal(weight,0.7,.00001));
//
//        edge2 = (DirectedEdge) ((Vertex)graph3.getVertex(-1)).findEdge((Vertex)graph3.getVertex(6));
//        weight = ((MutableDouble) edge2.getUserDatum(edgeWeightKey)).doubleValue();
//        Assert.assertTrue(NumericalPrecision.equal(weight,0.3,.00001));
//
//        DirectedGraph graph4 = collapser.collapseNext();
//        Assert.assertTrue(graph4 == null);
//
//
//
//    }
}
