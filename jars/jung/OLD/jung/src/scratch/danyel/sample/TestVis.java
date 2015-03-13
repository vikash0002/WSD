/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.danyel.sample;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.importance.Ranking;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.EdgeWeightLabeller;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.filters.Filter;
import edu.uci.ics.jung.graph.filters.SerialFilter;
import edu.uci.ics.jung.graph.filters.impl.DropSoloNodesFilter;
import edu.uci.ics.jung.graph.filters.impl.WeightedEdgeGraphFilter;
import edu.uci.ics.jung.utils.TestGraphs;
import edu.uci.ics.jung.visualization.FadingVertexLayout;
import edu.uci.ics.jung.visualization.GraphMouseListener;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.MouseListenerTranslator;
import edu.uci.ics.jung.visualization.MultiPickedState;
import edu.uci.ics.jung.visualization.PickedState;
import edu.uci.ics.jung.visualization.SpringLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class TestVis {

	public static void main(String[] args) {

		final Graph g = TestGraphs.getDemoGraph();

        final SpringLayout sl = new SpringLayout(g);
		final FadingVertexLayout v;
		boolean SPRING_VIS = true;
		if (SPRING_VIS) {
			v = new FadingVertexLayout(10, sl);
		} else {
			v = new FadingVertexLayout(10, new CircleLayout(g));
		}

        PickedState pi = new MultiPickedState();
        final FadeBetweennessRenderer r = new FadeBetweennessRenderer( StringLabeller.getLabeller( g ), v, pi);
		final VisualizationViewer vv = new VisualizationViewer(v, r);
        vv.setPickedState(pi);
		GraphMouseListener mygel = new GraphMouseListener() {

			public void graphClicked(Vertex v, MouseEvent me) {
				System.out.println("Clicked " + v );
			}

			public void graphPressed(Vertex v, MouseEvent me) {
				// TODO Auto-generated method stub :graphPressed
				
			}

			public void graphReleased(Vertex v, MouseEvent me) {
				// TODO Auto-generated method stub :graphReleased
				
			}
			
		};

		vv.addMouseListener(new MouseListenerTranslator(mygel, vv));
		
		JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().add(vv);

		JPanel south = new JPanel();
		jf.getContentPane().add(south, BorderLayout.SOUTH);

		JButton scramble = new JButton("Scramble");

		final JToggleButton debug = new JToggleButton("DEBUG");
		south.add(scramble);
		south.add(debug);

		final WeightedEdgeGraphFilter f2 = new WeightedEdgeGraphFilter(0, EdgeWeightLabeller.getLabeller( g ));
		final Filter ff =
			new SerialFilter(f2, DropSoloNodesFilter.getInstance());

		JPanel westCoast = new JPanel();
		final JPanel wc_sub = new JPanel();
		wc_sub.setVisible(false);

		final JSlider strength = new JSlider(JSlider.VERTICAL);
		strength.setValue(0);
		strength.setPaintTicks(true);
		strength.setMaximum(100);
		jf.getContentPane().add(westCoast, BorderLayout.WEST);
		westCoast.add(strength);

		final JSlider repulsion = new JSlider(JSlider.VERTICAL);
		repulsion.setValue(0);
		repulsion.setPaintTicks(true);
		repulsion.setMaximum(300);
		wc_sub.add(repulsion);

		final JSlider stretchConstant = new JSlider(JSlider.VERTICAL);
		stretchConstant.setValue(0);
		stretchConstant.setPaintTicks(true);
		stretchConstant.setMaximum(500);
		wc_sub.add(stretchConstant);

		westCoast.add(wc_sub);

		stretchConstant.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int val = stretchConstant.getValue();
				if (val == sl.getStretch()*100) {
					return;
				}
				sl.setStretch(val/100.0);
			}
		});

		repulsion.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int val = repulsion.getValue();
				if (val == sl.getRepulsionRange()) {
					return;
				}
				sl.setRepulsionRange(val);
			}
		});

		scramble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				v.restart();
				vv.prerelax();
				vv.repaint();
			}

		});

		debug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FadeRenderer.debug = debug.isSelected();
				wc_sub.setVisible(debug.isSelected());
			}
		});

		strength.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				runFilter(g, r, v, f2, ff, strength);
			}
		});

		runFilter(g, r, v, f2, ff, strength);

		jf.pack();
		jf.show();

	}

	protected static void runFilter(
		final Graph g,
		final FadeBetweennessRenderer r,
		final Layout v,
		final WeightedEdgeGraphFilter f2,
		final Filter ff,
		final JSlider strength) {

		int val = strength.getValue();
		if (val == f2.getValue()) {
			//			return;
		}
		f2.setValue(val);
		Graph fx = ff.filter(g).assemble();

		BetweennessCentrality bc = new BetweennessCentrality(fx,true);
		bc.setRemoveRankScoresOnFinalize(false);
		bc.evaluate();

		List l = bc.getRankings();
		if (l.size() > 0) {
			Ranking rnk = (Ranking) l.get(0);
			//		 l.size()-1);

			r.setMaxBetweeness(rnk.rankScore);
		}
		v.applyFilter(fx);
		return;
	}

}
