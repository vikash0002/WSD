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

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.decorators.EdgeThicknessFunction;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.StringLabeller.UniqueLabelException;
import edu.uci.ics.jung.random.generators.GraphGenerator;
import edu.uci.ics.jung.random.generators.WattsBetaSmallWorldGenerator;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphDraw;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.graphdraw.SettableRenderer;

/**
 * This simple app demonstrates how one can use our algorithms and visualization libraries in unison.
 * In this case, we generate use the Zachary karate club data set, widely known in the social networks literature, then
 * we cluster the vertices using an edge-betweenness clusterer, and finally we visualize the graph using
 * Fruchtermain-Rheingold layout and provide a slider so that the user can adjust the clustering granularity.
 * @author Scott White
 */
public class GraphGeneratorDemo extends Applet {

	private static final Object DEMOKEY = "DEMOKEY";


	static JLabel eastSize;
    private static Object WattsSmallWorldGenerator;

    public static void main(String[] args) {
		// Add a restart button so the graph can be redrawn to fit the size of the frame
        JFrame jf = new JFrame();
        jf.getContentPane().add(setUp());

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.show();
	}

	public void start() {
		System.out.println("Starting in applet mode.");
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("samples/datasets/zachary.net");
		BufferedReader br = new BufferedReader( new InputStreamReader( is ));
		final GraphDraw gd = setUp();
		add( gd );
	}

	private static GraphDraw setUp() {
		GraphGenerator generator = new WattsBetaSmallWorldGenerator(10,0.0,2);
        final Graph graph = (Graph) generator.generateGraph();
		//Label the nodes with default integer labels
		StringLabeller labeler = StringLabeller.getLabeller(graph);
		try {
			labeler.assignDefaultLabels(graph.getVertices(), 0);
		} catch (UniqueLabelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//Create a simple layout frame
		final GraphDraw gd = new GraphDraw(graph);
		gd.setBackground( Color.white );
		//specify the Fruchterman-Rheingold layout algorithm
        VisualizationViewer vv = gd.getVisualizationViewer();
		vv.setGraphLayout(new FRLayout(graph));


        //TODO: my edge thickness addition: does it help distinguish edges?
        
        ((SettableRenderer)vv.getRenderer()).setEdgeThicknessFunction(new EdgeThicknessFunction()
            {
                public float getEdgeThickness(Edge e)
                {
                    return 1;
                }
            });

		//add restart button
		JButton scramble = new JButton("Restart");
		gd.add(scramble, BorderLayout.SOUTH);
		scramble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gd.getVisualizationViewer().restart();
			}

		});

        //Create slider to adjust the number of edges to remove when clustering
		final JSlider kSlider = new JSlider(JSlider.HORIZONTAL);
        kSlider.setBackground(Color.WHITE);
		kSlider.setPreferredSize(new Dimension(50, 50));
		kSlider.setPaintTicks(true);
		kSlider.setMaximum(4);
		kSlider.setMinimum(1);
		kSlider.setValue(1);
		kSlider.setMajorTickSpacing(1);
		kSlider.setPaintLabels(true);
		kSlider.setPaintTicks(true);
		kSlider.setBorder(BorderFactory.createLineBorder(Color.black));
		//TO DO: edgeBetweennessSlider.add(new JLabel("Node Size (PageRank With Priors):"));
		//I also want the slider value to appear
		final JPanel eastControls = new JPanel();
        eastControls.setBackground(Color.WHITE);
		eastControls.setOpaque(true);
		eastControls.setLayout(new BoxLayout(eastControls, BoxLayout.Y_AXIS));
		eastControls.add(Box.createVerticalGlue());
		eastControls.add(kSlider);
		//		final String COMMANDSTRING = "Node Size (PageRank With Priors): ";
		final String COMMANDSTRING = "Average degree per node: ";
		eastSize = new JLabel(COMMANDSTRING + kSlider.getValue());
		eastControls.add(eastSize);
		eastControls.add(Box.createVerticalGlue());
		gd.add(eastControls, BorderLayout.EAST);

		kSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					int k = source.getValue();

                    GraphGenerator generator = new WattsBetaSmallWorldGenerator(10,0.0,k*2);
                    Graph g = (Graph) generator.generateGraph();
                    graph.removeAllEdges();

                    for (Iterator eIt = g.getEdges().iterator(); eIt.hasNext();) {
                        Edge edge = (Edge) eIt.next();

                        /*
                        Vertex v1 = (Vertex) edge.getIncidentVertices().iterator().next();
                        Vertex v2 = edge.getOpposite(v1);
                        Vertex u1 = (Vertex) v1.getEquivalentVertex(graph);
                        Vertex u2 = (Vertex) v2.getEquivalentVertex(graph);
                        */

                    }
                    //graph.
                    //Label the nodes with default integer labels
                    //StringLabeller labeler = StringLabeller.getLabeller(graph);
                    //labeler.assignDefaultLabels(graph.getVertices(), 0);

					eastSize.setText(
						COMMANDSTRING + kSlider.getValue());
					gd.validate();
					gd.repaint();
				}
			}
		});



		return gd;
	}
}
