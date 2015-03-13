/*
 * Created on Jan 2, 2004
 */
package scratch.danyel;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;
import edu.uci.ics.jung.algorithms.connectivity.BFSDistanceLabeler;
import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.StringLabeller.UniqueLabelException;
import edu.uci.ics.jung.graph.filters.impl.NumericDecorationFilter;
import edu.uci.ics.jung.graph.impl.UndirectedSparseGraph;
import edu.uci.ics.jung.io.PajekNetReader;
import edu.uci.ics.jung.utils.GraphUtils;

/**
 * @author danyelf
 */
public class WeirdFailure extends TestCase {

	private static NumericDecorationFilter mBetweennessFilter;

	public void testWeirdFailure() throws UniqueLabelException, IOException {
        PajekNetReader pnr = new PajekNetReader(true);
        Graph g = pnr.load("samples/datasets/smyth.net", new UndirectedSparseGraph());
        StringLabeller sl = StringLabeller.getLabeller(g, PajekNetReader.LABEL);
		Vertex v = sl.getVertex("P Smyth");

		mBetweennessFilter = new NumericDecorationFilter();
		mBetweennessFilter.setThreshold(0.05);
		mBetweennessFilter.setDecorationKey(BetweennessCentrality.CENTRALITY);

		BetweennessCentrality bc = new BetweennessCentrality(g, true);
		bc.setRemoveRankScoresOnFinalize(false);
		bc.evaluate();

		BFSDistanceLabeler bdl = new BFSDistanceLabeler();

		// now we have a subgraph
		Graph newGraph = mBetweennessFilter.filter(g).assemble();
				
		// ok, checks that every neighbor of every vertex is in the graph
		for (Iterator iter = newGraph.getVertices().iterator(); iter.hasNext();) {
			Vertex vv = (Vertex) iter.next();
			assertTrue( vv.getGraph() == newGraph );
			for (Iterator iterator = vv.getNeighbors().iterator(); iterator.hasNext();) {
				Vertex vvv = (Vertex) iterator.next();
				assertTrue( vvv.getGraph() == newGraph );
			}
		}

		Set priors = new HashSet();
		priors.add(v.getEqualVertex(newGraph));
			
		GraphUtils.copyLabels(sl,
			StringLabeller.getLabeller(newGraph));
		
		bdl.labelDistances(newGraph, priors);
	}

}
