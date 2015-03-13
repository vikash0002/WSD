/*
 * Created on Nov 3, 2005
 *
 * Copyright (c) 2005, the JUNG Project and the Regents of the University 
 * of California
 * All rights reserved.
 *
 * This software is open-source under the BSD license; see either
 * "license.txt" or
 * http://jung.sourceforge.net/license.txt for a description.
 */
package scratch.joshua.ranking;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import scratch.joshua.ranking.PageRankRanker;
import scratch.joshua.ranking.WeightedDegreeRanker;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.decorators.AbstractVertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.DefaultToolTipFunction;
import edu.uci.ics.jung.graph.decorators.InterpolatingVertexSizeFunction;
import edu.uci.ics.jung.graph.decorators.MapNumberVertexValue;
import edu.uci.ics.jung.graph.decorators.NumberVertexValue;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.graph.impl.SparseGraph;
import edu.uci.ics.jung.visualization.AbstractLayout;
import edu.uci.ics.jung.visualization.DefaultSettableVertexLocationFunction;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.ShapePickSupport;
import edu.uci.ics.jung.visualization.StaticLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class RankerDemo extends JApplet 
{

    /**
     * the graph
     */
    Graph graph;
    
    AbstractLayout layout;

    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer vv;
    
    DefaultSettableVertexLocationFunction vertexLocations;
    
    NumberVertexValue[] values = {
            new NumberVertexValue() {
                public Number getNumber(ArchetypeVertex v) { return new Double(1.0); }
                public void setNumber(ArchetypeVertex v, Number n) { throw new UnsupportedOperationException(); }
            },
            new MapNumberVertexValue(),
            new MapNumberVertexValue()
    };
    
    String[] rankers = { "None", "Degree", "PageRank" };
    PageRankRanker prr;
    WeightedDegreeRanker wdr;
    
    int rank_id = 0;

    InterpolatingVertexSizeFunction vertex_sizes = new InterpolatingVertexSizeFunction(values[rank_id], 10, 50);
    
    
    String instructions =
        "<html>"+
        "<h3>All Modes:</h3>"+
        "<ul>"+
        "<li>Right-click an empty area for <b>Create Vertex</b> popup"+
        "<li>Right-click on a Vertex for <b>Delete Vertex</b> popup"+
        "<li>Right-click on a Vertex for <b>Add Edge</b> menus <br>(if there are selected Vertices)"+
        "<li>Right-click on an Edge for <b>Delete Edge</b> popup"+
        "<li>Mousewheel scales with a crossover value of 1.0.<p>"+
        "     - scales the graph layout when the combined scale is greater than 1<p>"+
        "     - scales the graph view when the combined scale is less than 1"+

        "</ul>"+
        "<h3>Editing Mode:</h3>"+
        "<ul>"+
        "<li>Left-click an empty area to create a new Vertex"+
        "<li>Left-click on a Vertex and drag to another Vertex to create an Undirected Edge"+
        "<li>Shift+Left-click on a Vertex and drag to another Vertex to create a Directed Edge"+
        "</ul>"+
        "<h3>Picking Mode:</h3>"+
        "<ul>"+
        "<li>Mouse1 on a Vertex selects the vertex"+
        "<li>Mouse1 elsewhere unselects all Vertices"+
        "<li>Mouse1+Shift on a Vertex adds/removes Vertex selection"+
        "<li>Mouse1+drag on a Vertex moves all selected Vertices"+
        "<li>Mouse1+drag elsewhere selects Vertices in a region"+
        "<li>Mouse1+Shift+drag adds selection of Vertices in a new region"+
        "<li>Mouse1+CTRL on a Vertex selects the vertex and centers the display on it"+
        "</ul>"+
        "<h3>Transforming Mode:</h3>"+
        "<ul>"+
        "<li>Mouse1+drag pans the graph"+
        "<li>Mouse1+Shift+drag rotates the graph"+
        "<li>Mouse1+CTRL(or Command)+drag shears the graph"+
        "</ul>"+
        "</html>";
    
    /**
     * create an instance of a simple graph with popup controls to
     * create a graph.
     * 
     */
    public RankerDemo() {

        rank_id = 0;
        
        vertexLocations = new DefaultSettableVertexLocationFunction();
        
        // create a simple graph for the demo
        graph = new SparseGraph();

        // create rankers
//        prr = new PageRankRanker(graph, 100, .001, .1);
        // NOTE: we don't create prr here because the graph can change and thus screw up the normalization.
        // do we want to set it up so that evaluate takes the graph as a parameter?  (closer to stateless?)
        wdr = new WeightedDegreeRanker(graph, true);
        
        PluggableRenderer pr = new PluggableRenderer();
        this.layout = new StaticLayout(graph);
        layout.initialize(new Dimension(600,600), vertexLocations);
        
        vv =  new VisualizationViewer(layout, pr);
        vv.setPickSupport(new ShapePickSupport());
        AbstractVertexShapeFunction vsf = (AbstractVertexShapeFunction)pr.getVertexShapeFunction();
        vsf.setSizeFunction(vertex_sizes);
//        pr.setVertexStringer(new VertexStringer() {
//            public String getLabel(ArchetypeVertex v) {
//                return v.toString();
//            }});
        pr.setVertexStringer(new VertexStringer()
        {
            public String getLabel(ArchetypeVertex v)
            {
                Number n = values[rank_id].getNumber(v);
                return v.toString() + " (" + (n == null ? 0 : n.floatValue()) + ")";
            }
        });

        vv.setToolTipFunction(new DefaultToolTipFunction());
        
        Container content = getContentPane();
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        content.add(panel);
        final EditingModalGraphMouse graphMouse = new EditingModalGraphMouse();
        graphMouse.setVertexLocations(vertexLocations);
        vv.setGraphMouse(graphMouse);
        graphMouse.add(new EditingPopupGraphMousePlugin(vertexLocations));
        graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
        
        JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vv, instructions);
            }});

        JComboBox ranker_box = new JComboBox(rankers);
        ranker_box.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                JComboBox cb = (JComboBox)e.getSource();
                rank_id = cb.getSelectedIndex();
                vertex_sizes.setNumberVertexValue(values[rank_id]);
                switch (rank_id)
                {
                    case 0: break;
                    case 1: wdr.evaluate(values[rank_id]); break;
                    case 2:
                        prr = new PageRankRanker(graph, 100, .001, 0.10);
                        prr.evaluate(values[rank_id]); break;
                }
                
                vv.repaint();
            }
        });
        
        JPanel controls = new JPanel();
        JComboBox modeBox = graphMouse.getModeComboBox();
        controls.add(ranker_box);
        controls.add(modeBox);
        controls.add(help);
        content.add(controls, BorderLayout.SOUTH);
    }
    
    /**
     * a driver for this demo
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final RankerDemo demo = new RankerDemo();
        
//        JMenu menu = new JMenu("File");
//        menu.add(new AbstractAction("Print") {
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser chooser  = new JFileChooser();
//                int option = chooser.showSaveDialog(demo);
//                if(option == JFileChooser.APPROVE_OPTION) {
//                    File file = chooser.getSelectedFile();
//                    demo.writeJPEGImage(file);
//                }
//            }});
//        JMenuBar menuBar = new JMenuBar();
//        menuBar.add(menu);
//        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(demo);
        frame.pack();
        frame.setVisible(true);
    }
}
