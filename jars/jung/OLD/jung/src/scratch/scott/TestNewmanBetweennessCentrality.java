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

import java.io.StringReader;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.Indexer;
import edu.uci.ics.jung.io.GraphMLFile;

/**
 * @author Scott White
 */
public class TestNewmanBetweennessCentrality extends TestCase {
    public static Test suite() {
        return new TestSuite(TestNewmanBetweennessCentrality.class);
    }

    protected void setUp() {

    }

    public void testRanker() {
        /*
        DirectedSparseGraph graph = new DirectedSparseGraph();
        GraphUtils.addVertices(graph, 5);
        Indexer id = Indexer.getIndexer(graph);

        GraphUtils.addEdge(graph, id.getVertex(0), id.getVertex(1));
        GraphUtils.addEdge(graph, id.getVertex(1), id.getVertex(0));
        GraphUtils.addEdge(graph, id.getVertex(0), id.getVertex(2));
        GraphUtils.addEdge(graph, id.getVertex(2), id.getVertex(0));
        GraphUtils.addEdge(graph, id.getVertex(0), id.getVertex(3));
        GraphUtils.addEdge(graph, id.getVertex(3), id.getVertex(0));
        GraphUtils.addEdge(graph, id.getVertex(1), id.getVertex(2));
        GraphUtils.addEdge(graph, id.getVertex(2), id.getVertex(1));
        GraphUtils.addEdge(graph, id.getVertex(1), id.getVertex(4));
        GraphUtils.addEdge(graph, id.getVertex(4), id.getVertex(1));
        GraphUtils.addEdge(graph, id.getVertex(2), id.getVertex(3));
        GraphUtils.addEdge(graph, id.getVertex(3), id.getVertex(2));
        GraphUtils.addEdge(graph, id.getVertex(2), id.getVertex(4));
        GraphUtils.addEdge(graph, id.getVertex(4), id.getVertex(2));
        GraphUtils.addEdge(graph, id.getVertex(3), id.getVertex(4));
        GraphUtils.addEdge(graph, id.getVertex(4), id.getVertex(3));

        BetweennessCentrality bc = new BetweennessCentrality(graph, true);
        bc.setRemoveRankScoresOnFinalize(false);
        bc.evaluate();

        Assert.assertTrue(NumericalPrecision.equal(bc.getRankScore(id.getVertex(0)), 0.6666, .001));

        Assert.assertTrue(NumericalPrecision.equal(bc.getRankScore(id.getVertex(1)), 0.6666, .001));

        Assert.assertTrue(NumericalPrecision.equal(bc.getRankScore(id.getVertex(2)), 1.3333, .001));

        Assert.assertTrue(NumericalPrecision.equal(bc.getRankScore(id.getVertex(3)), 0.6666, .001));

        Assert.assertTrue(NumericalPrecision.equal(bc.getRankScore(id.getVertex(4)), 0.6666, .001));
        */

    }

    public void testRanker2() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>\n");
        buffer.append("<graph edgedefault=\"undirected\">\n");
        buffer.append("<node id=\"1\"/>\n");
        buffer.append("<node id=\"2\"/>\n");
        buffer.append("<node id=\"3\"/>\n");
        buffer.append("<node id=\"4\"/>\n");
        buffer.append("<node id=\"5\"/>\n");
        buffer.append("<node id=\"6\"/>\n");
        buffer.append("<node id=\"7\"/>\n");
        buffer.append("<node id=\"8\"/>\n");
        buffer.append("<node id=\"9\"/>\n");
        buffer.append("<edge source=\"1\" target=\"2\"/>\n");
        buffer.append("<edge source=\"1\" target=\"7\"/>\n");
        buffer.append("<edge source=\"2\" target=\"3\"/>\n");
        buffer.append("<edge source=\"2\" target=\"4\"/>\n");
        buffer.append("<edge source=\"3\" target=\"5\"/>\n");
        buffer.append("<edge source=\"4\" target=\"5\"/>\n");
        buffer.append("<edge source=\"5\" target=\"6\"/>\n");
        buffer.append("<edge source=\"6\" target=\"9\"/>\n");
        buffer.append("<edge source=\"7\" target=\"8\"/>\n");
        buffer.append("<edge source=\"8\" target=\"9\"/>\n");
        buffer.append("</graph>\n");

        GraphMLFile graphmlFile = new GraphMLFile();
        Graph graph = graphmlFile.load(new StringReader(buffer.toString()));
        Indexer id = Indexer.getIndexer(graph);

        BetweennessCentrality bc = new BetweennessCentrality(graph,false);
        bc.setRemoveRankScoresOnFinalize(false);
        bc.evaluate();

        for (Iterator eIt = graph.getEdges().iterator(); eIt.hasNext();) {
            Edge e = (Edge) eIt.next();
            Vertex from = (Vertex) e.getIncidentVertices().iterator().next();
            Vertex to = (Vertex) e.getOpposite(from);
            //System.out.println(id.getIndex(from) + "->" + id.getIndex(to) + ":" + bc.getRankScore(e)/2.0);
        }
        /*
      Assert.assertTrue(NumericalPrecision.equal(bc.getRankScore( id.getVertex(0)),0.6666,.001));

      Assert.assertTrue(NumericalPrecision.equal(bc.getRankScore(id.getVertex(1)),0.6666,.001));

      Assert.assertTrue(NumericalPrecision.equal(bc.getRankScore(id.getVertex(2)),1.3333,.001));

      Assert.assertTrue(NumericalPrecision.equal(bc.getRankScore(id.getVertex(3)),0.6666,.001));

      Assert.assertTrue(NumericalPrecision.equal(bc.getRankScore(id.getVertex(4)),0.6666,.001));
      */

    }
}
