/*
 * Created on Mar 4, 2004
 */
package scratch.danyel;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;

import edu.uci.ics.jung.algorithms.GraphMatrixOperations;
import edu.uci.ics.jung.algorithms.RealMatrixElementOperations;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.Indexer;
import edu.uci.ics.jung.graph.impl.LeanSparseVertex;
import edu.uci.ics.jung.random.generators.SimpleRandomGenerator;
import edu.uci.ics.jung.utils.MutableDouble;
import edu.uci.ics.jung.utils.UserData;
import edu.uci.ics.jung.visualization.contrib.KKLayout;
/**
 * created Mar 4, 2004
 * 
 * @author danyelf
 */
public class LittleTest {
	public static final int[] VERTICES = {100};
	public static final int[] EDGES = {30};
	public static void main(String[] s) throws IOException {
		// test lean
		for (int i = 0; i < VERTICES.length; i++) {
			for (int j = 0; j < EDGES.length; j++) {
				try {
//					System.out.println();
//					SimpleRandomGenerator srg = new SimpleRandomGenerator(
//							VERTICES[i], VERTICES[i] * EDGES[j]);
//					srg.setSeed(10L);
//					//////////////////////////////////////
//					// test sexy
					SimpleRandomGenerator srg2 = new SimpleRandomGenerator(
							VERTICES[i], VERTICES[i] * EDGES[j]) {
						public Vertex newVertex() {
							return new LeanSparseVertex();
						}
					};
//					srg2.setSeed(10L);
//					SimpleRandomGenerator srg3 = new SimpleRandomGenerator(
//							VERTICES[i], VERTICES[i] * EDGES[j]) {
//						public Vertex newVertex() {
//							return new SlickLeanVertex();
//						}
//					};
//					srg3.setSeed(10L);
//					SimpleRandomGenerator srg4 = new SimpleRandomGenerator(
//							VERTICES[i], VERTICES[i] * EDGES[j]) {
//						public Vertex newVertex() {
//							return new SlickLeanVertex2();
//						}
//					};
//					srg4.setSeed(10L);
//					SimpleRandomGenerator srg5 = new SimpleRandomGenerator(
//							VERTICES[i], VERTICES[i] * EDGES[j]) {
//						public Vertex newVertex() {
//							return new UndirectedSparseVertex();
//						}
//					};
//					srg5.setSeed(10L);
//					runTest(srg, "Sparse");
					runTest(srg2, "Lean");
//					runTest(srg3, "Slick");
//					runTest(srg4, "Slick2");
//					runTest(srg5, "USV");
				} catch (IllegalArgumentException iae) {
				}
			}
		}
		System.in.read();
	}
	/**
	 * @param srg
	 */
	private static void runTest(SimpleRandomGenerator srg, String type) {
		System.gc();
		try {
			Graph g = create(srg, type);
			squareTest(g, type);
			vizTest(g, type);
		} catch (OutOfMemoryError oe) {
			System.out.println("\t" + type + "\t\t" + srg.getNumVertices() + "\t"
					+ srg.getNumEdges() + "\tOut of Memory");
			System.gc();
		}
	}

	private static void vizTest(Graph g, String type) {
		long now = System.currentTimeMillis();
		KKLayout kkl = new KKLayout(g);
		kkl.initialize(new Dimension(600, 600));
		System.out.println("\t" + type + "\tKK\t" + g.numVertices() + "\t"
				+ g.numEdges() + "\t" + (System.currentTimeMillis() - now));
	}
	
	private static void squareTest(Graph g, String type) {
		long now = System.currentTimeMillis();
		// assign a key to all edges
		for (Iterator iter = g.getEdges().iterator(); iter.hasNext();) {
			Edge e = (Edge) iter.next();
			e.setUserDatum("weight", new MutableDouble(1), UserData.REMOVE);
		}
		Graph g2 = GraphMatrixOperations.square(g,
				new RealMatrixElementOperations("weight"));
		System.out.println(g2.numEdges() + "\t" + type + "\tSQ1\t"
				+ g.numVertices() + "\t" + g.numEdges() + "\t"
				+ (System.currentTimeMillis() - now));
	}
	private static Graph create(SimpleRandomGenerator srg, String type) {
		long now = System.currentTimeMillis();
		Graph g = (Graph) srg.generateGraph();
		assrt(g.getVertices().size() == srg.getNumVertices());
		assrt(g.getEdges().size() == srg.getNumEdges());
		Indexer id = Indexer.getIndexer(g);
		System.out.print(id.getVertex(0).degree() + " "
				+ id.getVertex(1).degree() + "\t");
		System.out.println(type + "\tCR\t" + g.numVertices() + "\t"
				+ g.numEdges() + "\t" + (System.currentTimeMillis() - now));
		return g;
	}
	/**
	 * @param b
	 */
	private static void assrt(boolean b) {
		if (!b)
			throw new RuntimeException("Assertion failed");
	}
}
