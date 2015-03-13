/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
/*
 * Created on Jun 23, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package scratch.biao;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.connectivity.KNeighborhoodExtractor;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.StringLabeller.UniqueLabelException;
import edu.uci.ics.jung.io.PajekNetReader;
import edu.uci.ics.jung.utils.GraphUtils;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphDraw;
/**
 * @author danyelf
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TwoGraphsFloating {

	static GraphDraw gd;
	static GraphDraw gd2;

	public static void main(String[] args) throws IOException {
		JFrame jf = new JFrame();
		JFrame jf2 = new JFrame();
		Graph g = getGraph();
		StringLabeller sl = StringLabeller.getLabeller(g, PajekNetReader.LABEL);
		
		/*
		for (Iterator vIt = g.getVertices().iterator(); vIt.hasNext();) {
			Vertex vv = (Vertex) vIt.next();
			//sl.setLabel(vv, String.valueOf(labelIdx));
			//sl.setLabel(vv, sl.getLabel(vv));
			System.out.println(sl.getLabel(vv));
		}
		*/
		
		gd = new GraphDraw( g );
		gd2 = new GraphDraw( g );
		//gd.setVisualizer(new FRVisualizer(g));
		//gd.setVisualizer(new FRVisualizerX(g));
		//gd.setVisualizer(new SpringVisualizer(g));
		//gd.setVisualizer(new ISOMVisualizer(g));
		jf.getContentPane().add( gd );
		
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.show();

		//jf2.getContentPane().add( gd2 );
		//jf2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//jf2.pack();
		//jf2.show();
		
		Vertex v = sl.getVertex("in1");
		Set s = new HashSet();
		s.add(v);
		Graph sub = KNeighborhoodExtractor.extractNeighborhood(g, s, 1);
		
		StringLabeller sl2 = StringLabeller.getLabeller(sub);
		try {
			sl2.assignDefaultLabels(sub.getVertices(), 0);
		} catch (UniqueLabelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
            GraphUtils.copyLabels(sl, sl2);
        } catch (UniqueLabelException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
		
		Vertex v2 = sl.getVertex("in1");
		//if (v2 == null) System.out.println("BAD");
		
		System.out.println(shortDistToAllVertices(v));
		System.out.println(shortDistToAllVertices(v2));
		
		//if (v.getGraph() == v2.getGraph()) System.out.println("YAH!");
		
		JFrame blah = new JFrame();
		GraphDraw gd2 = new GraphDraw(sub);
		blah.getContentPane().add(gd2);
		gd2.getVisualizationViewer().setGraphLayout(new FRLayout(g));
		blah.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		blah.pack();
		blah.show();
	}
	
	private static int shortDistToAllVertices(Vertex v) {
		UnweightedShortestPath usp = new UnweightedShortestPath((Graph) v.getGraph());
		Map m = usp.getDistanceMap(v);
		int maxDist = Integer.MIN_VALUE;
		for (Iterator iter = m.values().iterator(); iter.hasNext();) {
			int i = ((Number)iter.next()).intValue();
			if (i > maxDist) maxDist = i;
		}
		return maxDist;
	}
	
	private static Graph getGraph() throws IOException {
        PajekNetReader pnr = new PajekNetReader();
        Graph g = pnr.load("samples/datasets/simple.net");
//		PajekNetFile file = new PajekNetFile();
//		Graph g = file.load("samples/datasets/simple.net");
		return g;
	}
}

