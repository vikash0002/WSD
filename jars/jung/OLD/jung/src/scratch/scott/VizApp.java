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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.importance.PageRank;
import edu.uci.ics.jung.algorithms.importance.Ranking;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.filters.impl.NumericDecorationFilter;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author Scott White
 */
public class VizApp extends JFrame {
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

    public VizApp(String title) throws HeadlessException {
        super(title);
        initialize();

        //KleinbergSmallWorldGenerator smallWorldGenerator = new KleinbergSmallWorldGenerator(5);
        //displayGraph(smallWorldGenerator.createSmallWorld(2));
    }

    protected void initialize() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //Build the first menu.
        JMenu menu = new JMenu("File");
        //menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        //a group of JMenuItems
        JMenuItem loadItem = new JMenuItem("Load", KeyEvent.VK_T);
        //menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menu.add(loadItem);
        loadItem.addActionListener(new OpenFileHandler(this));

        //menuItem = new JMenuItem("Save");
        //menuItem.setMnemonic(KeyEvent.VK_B);
        //menu.add(menuItem);

        createControlsPanel();
        mBetweennessFilter = new NumericDecorationFilter();
        mBetweennessFilter.setThreshold(0.1);
        mBetweennessFilter.setDecorationKey(BetweennessCentrality.CENTRALITY);

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
        controlsPanel.add(mNodeSizeBetweennessSlider);

        mNodeSizeBetweennessSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                mRenderer.setSizeKey(BetweennessCentrality.CENTRALITY);
                mRenderer.setNodeSizeScale((double) mNodeSizeBetweennessSlider.getValue() / 10.0);
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
                mRenderer.setNodeSizeScale((double) mNodeSizePageRankSlider.getValue());
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
                mVisualizer.applyFilter((mBetweennessFilter.filter(mCurrentGraph).assemble()));
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

        JButton freeze = new JButton("Freeze");
        controlsPanel.add(freeze);

        freeze.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mVizViewer.suspend();
            }

        });

        controlsPanel.revalidate();
		controlsPanel.repaint();
    }

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

        BetweennessCentrality bc = new BetweennessCentrality(graph,true);
        bc.setRemoveRankScoresOnFinalize(false);
        bc.evaluate();
        List rankingList = bc.getRankings();
        Ranking betwennessMax = (Ranking) rankingList.get(0);

        PageRank pageRank = new PageRank((DirectedGraph) graph,.8,null);
        pageRank.setRemoveRankScoresOnFinalize(false);
        pageRank.evaluate();
        rankingList = pageRank.getRankings();
        Ranking pageRankMax = (Ranking) rankingList.get(0);
        Ranking pageRankMin = (Ranking) rankingList.get(rankingList.size()-1);

        //mNodeAcceptPageRankSlider.setMaximum((int) Math.ceil(max.rankScore));
        int minScaleRatio = (int) Math.floor(1.0/pageRankMin.rankScore);
        int maxScaleRatio = (int) Math.floor(1.0/pageRankMax.rankScore);
        mNodeSizePageRankSlider.setMaximum(minScaleRatio*30);
        mNodeSizePageRankSlider.setMinimum(maxScaleRatio*10);

        //Ranking min = (Ranking) rankingList.get(rankingList.size()-1);
        mNodeAcceptBetweennessSlider.setMaximum((int) Math.ceil(betwennessMax.rankScore));

		getContentPane().add(mVizViewer);
		mVizViewer.revalidate();
		mVizViewer.repaint();
    }

    public static void main(String[] args) {

        VizApp vizApp = new VizApp("Scott's Toy Network Viewer");
        vizApp.setSize(700, 500);
        vizApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        vizApp.pack();
        vizApp.show();

    }
}
