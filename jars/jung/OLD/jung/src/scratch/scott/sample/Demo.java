/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.scott.sample;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.cluster.ClusterSet;
import edu.uci.ics.jung.algorithms.cluster.EdgeBetweennessClusterer;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.VertexColorFunction;
import edu.uci.ics.jung.graph.decorators.StringLabeller.UniqueLabelException;
import edu.uci.ics.jung.random.generators.EppsteinPowerLawGenerator;
import edu.uci.ics.jung.utils.UserData;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphDraw;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.graphdraw.SettableRenderer;

/**
 * @author Scott White
 */
public class Demo extends Applet {

	private static final Object DEMOKEY = "DEMOKEY";

	public static final Color[] similarColors =
		{
			new Color(216, 134, 134),
			new Color(135, 137, 211),
			new Color(134, 206, 189),
			new Color(206, 176, 134),
			new Color(194, 204, 134),
			new Color(145, 214, 134),
			new Color(133, 178, 209)};

	public static void main(String[] args) {
		final GraphDraw gd = prepareGraph();

		JFrame jf = new JFrame();
		jf.getContentPane().add(gd);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.show();
	}

	public void init() {	
	}
	
	public void start() {
		add( prepareGraph() );
	}
	
	public void stop() {	
	}

	private static GraphDraw prepareGraph() {
		EppsteinPowerLawGenerator generator =
			new EppsteinPowerLawGenerator(20, 40, 10000);
		Graph g = (Graph) generator.generateGraph();

		EdgeBetweennessClusterer clusterer = new EdgeBetweennessClusterer(10);
		ClusterSet clusterSet = clusterer.extract(g);

		int i = 0;
		for (Iterator cIt = clusterSet.iterator(); cIt.hasNext();) {

			Set vertices = (Set) cIt.next();
			for (Iterator iter = vertices.iterator(); iter.hasNext();) {
				Vertex v = (Vertex) iter.next();
				v.setUserDatum(
					DEMOKEY,
					similarColors[i % similarColors.length],
					UserData.REMOVE);
			}
			i++;
		}

		StringLabeller labeler = StringLabeller.getLabeller(g);
		try {
			labeler.assignDefaultLabels(g.getVertices(), 0);
		} catch (UniqueLabelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		final GraphDraw gd = new GraphDraw(g);
        VisualizationViewer vv = gd.getVisualizationViewer();
		vv.setGraphLayout(new FRLayout(g));
		((SettableRenderer)vv.getRenderer()).setVertexColorFunction(new VertexColorFunction() {
			public Color getBackColor(Vertex v) {
				Color k = (Color) v.getUserDatum(DEMOKEY);
				if (k != null)
					return k;
				return Color.white;
			}
			public Color getForeColor(Vertex v) {
				return Color.black;
			}

		});
		JButton scramble = new JButton("Restart");
		gd.add(scramble, BorderLayout.SOUTH);

		scramble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gd.getVisualizationViewer().restart();
			}

		});
		return gd;
	}
}
