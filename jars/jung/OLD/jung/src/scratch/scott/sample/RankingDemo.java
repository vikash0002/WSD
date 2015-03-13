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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import scratch.scott.BasicRenderer;
import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.importance.PageRank;
import edu.uci.ics.jung.algorithms.importance.Ranking;
import edu.uci.ics.jung.algorithms.transformation.DirectionTransformer;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.StringLabeller.UniqueLabelException;
import edu.uci.ics.jung.graph.filters.impl.NumericDecorationFilter;
import edu.uci.ics.jung.graph.impl.UndirectedSparseGraph;
import edu.uci.ics.jung.io.PajekNetReader;
import edu.uci.ics.jung.utils.GraphUtils;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author Scott White
 */
public class RankingDemo extends JFrame {
	private Graph mCurrentGraph;
	private Layout mVisualizer;
	private BasicRenderer mRenderer;
	private VisualizationViewer mVizViewer;
	private JSlider mNodeAcceptBetweennessSlider;
	private JSlider mNodeSizeBetweennessSlider;
	// private JSlider mNodeAcceptPageRankSlider;
	private JSlider mNodeSizePageRankSlider;
	private NumericDecorationFilter mBetweennessFilter;
	private NumericDecorationFilter mPageRankFilter;

	public RankingDemo(String title) throws HeadlessException, IOException, UniqueLabelException {
		super(title);
		initialize();

//		EppsteinPowerLawGenerator generator =
//			new EppsteinPowerLawGenerator(30, 80, 10000);
//		Graph g = (Graph) generator.generateGraph();
        PajekNetReader pnr = new PajekNetReader();
        Graph ug = pnr.load("samples/datasets/smyth.net", new UndirectedSparseGraph());
        Graph g = DirectionTransformer.toDirected(ug);
        GraphUtils.copyLabels(StringLabeller.getLabeller(ug, PajekNetReader.LABEL),
            StringLabeller.getLabeller(g, PajekNetReader.LABEL));
//		PajekNetFile file = new PajekNetFile();
//		DirectedGraph g = (DirectedGraph) file.load("samples/datasets/smyth.net");
		displayGraph(g);
	}

	protected void initialize() {

		createControlsPanel();
		mBetweennessFilter = new NumericDecorationFilter();
		mBetweennessFilter.setThreshold(0.1);
		mBetweennessFilter.setDecorationKey(
			BetweennessCentrality.CENTRALITY);

		mPageRankFilter = new NumericDecorationFilter();
		mPageRankFilter.setThreshold(0.1);
		mPageRankFilter.setDecorationKey(PageRank.KEY);
	}

	private void createControlsPanel() {

		JPanel controlsPanel = new JPanel();
		FlowLayout layoutMgr = new FlowLayout(FlowLayout.CENTER, 20, 10);
		controlsPanel.setPreferredSize(new Dimension(200, 400));
		controlsPanel.setLayout(layoutMgr);
		controlsPanel.setBackground(Color.WHITE);

		//------------------------------------------------------------
		controlsPanel.add(new JLabel("Node Size (Betweenness):"));
		getContentPane().add(controlsPanel, BorderLayout.EAST);

		mNodeSizeBetweennessSlider = new JSlider(JSlider.HORIZONTAL);
		mNodeSizeBetweennessSlider.setPreferredSize(new Dimension(180, 40));
		mNodeSizeBetweennessSlider.setPaintTicks(true);
		mNodeSizeBetweennessSlider.setBackground(Color.WHITE);
		mNodeSizeBetweennessSlider.setValue(0);
		controlsPanel.add(mNodeSizeBetweennessSlider);

		mNodeSizeBetweennessSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mRenderer.setSizeKey(BetweennessCentrality.CENTRALITY);
				mRenderer.setNodeSizeScale(
					(double) mNodeSizeBetweennessSlider.getValue() / 10.0);
				repaint();
			}
		});

		//------------------------------------------------------------
		controlsPanel.add(new JLabel("Node Size (PageRank):"));
		getContentPane().add(controlsPanel, BorderLayout.EAST);

		mNodeSizePageRankSlider = new JSlider(JSlider.HORIZONTAL);
		mNodeSizePageRankSlider.setPreferredSize(new Dimension(180, 40));
		mNodeSizePageRankSlider.setPaintTicks(true);
		mNodeSizePageRankSlider.setBackground(Color.WHITE);
		controlsPanel.add(mNodeSizePageRankSlider);

		mNodeSizePageRankSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mRenderer.setSizeKey(PageRank.KEY);
				mRenderer.setNodeSizeScale(
					(double) mNodeSizePageRankSlider.getValue());
				repaint();
			}
		});

		//------------------------------------------------------------
		controlsPanel.add(new JLabel("Filter nodes (Betweenness):"));
		mNodeAcceptBetweennessSlider = new JSlider(JSlider.HORIZONTAL);
		mNodeAcceptBetweennessSlider.setPreferredSize(new Dimension(180, 40));
		mNodeAcceptBetweennessSlider.setValue(0);
		mNodeAcceptBetweennessSlider.setPaintTicks(true);
		mNodeAcceptBetweennessSlider.setBackground(Color.WHITE);
		controlsPanel.add(mNodeAcceptBetweennessSlider);

		mNodeAcceptBetweennessSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int val = mNodeAcceptBetweennessSlider.getValue();
				mBetweennessFilter.setThreshold(val);
				mVisualizer.applyFilter(
					(mBetweennessFilter.filter(mCurrentGraph).assemble()));
				repaint();
			}
		});

		//------------------------------------------------------------

		JButton scramble = new JButton("Restart");
		controlsPanel.add(scramble);

		scramble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mVisualizer.restart();
				mVizViewer.prerelax();
				mVizViewer.repaint();
			}

		});
		//------------------------------------------------------------

		//        JButton freeze = new JButton("Freeze");
		//        controlsPanel.add(freeze);
		//
		//        freeze.addActionListener(new ActionListener() {
		//            public void actionPerformed(ActionEvent arg0) {
		//                mVizViewer.suspend();
		//            }
		//
		//        });
		//
		controlsPanel.revalidate();
		controlsPanel.repaint();
	}

//	protected void displayGraph(DirectedGraph graph) {
    protected void displayGraph(Graph graph) {
		if (mVizViewer != null) {
			//mVizViewer.suspend();
		}
		mCurrentGraph = graph;
		//mVisualizer = new AestheticSpringVisualizer(mCurrentGraph);
		mVisualizer = new FRLayout(mCurrentGraph);
		mRenderer = new BasicRenderer();
		mRenderer.setLabel("LABEL");
		//mRenderer.setSizeKey(BrandesBetweennessCentrality.CENTRALITY);
		mRenderer.setSizeKey(PageRank.KEY);
		if (mVizViewer != null) {
			getContentPane().remove(mVizViewer);
		}
		mVizViewer = new VisualizationViewer(mVisualizer, mRenderer);
		mVizViewer.setBackground(Color.WHITE);

		BetweennessCentrality bc =
			new BetweennessCentrality(graph,true);
		bc.setRemoveRankScoresOnFinalize(false);
		bc.evaluate();
		List rankingList = bc.getRankings();
		Ranking betwennessMax = (Ranking) rankingList.get(0);

		PageRank pageRank = new PageRank((DirectedGraph)graph, .8);
		pageRank.setRemoveRankScoresOnFinalize(false);
		pageRank.evaluate();
		rankingList = pageRank.getRankings();
		Ranking pageRankMax = (Ranking) rankingList.get(0);
		Ranking pageRankMin = (Ranking) rankingList.get(rankingList.size() - 1);

		//mNodeAcceptPageRankSlider.setMaximum((int) Math.ceil(max.rankScore));
		int minScaleRatio = (int) Math.floor(1.0 / pageRankMin.rankScore);
		int maxScaleRatio = (int) Math.floor(1.0 / pageRankMax.rankScore);
		mNodeSizePageRankSlider.setMaximum(minScaleRatio * 30);
		mNodeSizePageRankSlider.setMinimum(maxScaleRatio * 10);

		//Ranking min = (Ranking) rankingList.get(rankingList.size()-1);
		mNodeAcceptBetweennessSlider.setMaximum(
			(int) Math.ceil(betwennessMax.rankScore));

		getContentPane().add(mVizViewer);
		mVizViewer.revalidate();
		mVizViewer.repaint();
	}

	public static void main(String[] args) throws IOException, UniqueLabelException {

		RankingDemo vizApp = new RankingDemo("Scott's Toy Network Viewer");
		vizApp.setSize(700, 500);
		vizApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		vizApp.pack();
		vizApp.show();

	}
}
