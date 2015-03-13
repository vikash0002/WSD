/*
 * Created on Dec 10, 2003
 */
package scratch.danyel;

import java.awt.BorderLayout;

import javax.swing.*;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.utils.TestGraphs;
import edu.uci.ics.jung.visualization.GraphDraw;
import edu.uci.ics.jung.visualization.contrib.DAGLayout;

/**
 * 
 * @author danyelf
 */
public class TestDag {
	
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		final Graph g = TestGraphs.createDirectedAcyclicGraph( 6, 7, 0.5 );
		final GraphDraw gd = new GraphDraw(g);
		gd.getVisualizationViewer().setGraphLayout(new DAGLayout(g));
		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		jp.add(gd, BorderLayout.CENTER);

		jf.getContentPane().add(jp);
		jf.pack();
		jf.show();
	}

}
