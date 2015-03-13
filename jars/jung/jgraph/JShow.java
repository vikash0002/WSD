/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 * 
 */
package jgraph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
//import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.annotations.AnnotationControls;
import edu.uci.ics.jung.visualization.control.*;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

/**
 * Shows how  to create a graph editor with JUNG.
 * Mouse modes and actions are explained in the help text.
 * The application version of JShow provides a
 * File menu with an option to save the visible graph as
 * a jpeg file.
 * 
 * @author Tom Nelson
 * changes -- Paul Tarau, April 2011
 */
public class JShow extends JApplet implements Printable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 220L;

	/**
     * the graph
     */
    Graph<Object,Object> graph;
    
    AbstractLayout<Object,Object> layout;

    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer<Object,Object> vv;
    
    String instructions =
        "<html>"+
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
        "<li>Mouse1 double-click on a vertex or edge allows you to edit the label"+
        "</html>";
    
    /**
     * create an instance of a simple graph with popup controls to
     * create a graph.
     * 
     */
    public JShow(DirectedOrderedSparseMultigraph<Object,Object> graph) {
        this.graph=graph;
      
        this.layout = new CircleLayout(graph);layout.setSize(new Dimension(600,600));
      
        vv =  new VisualizationViewer<Object,Object>(layout);
        vv.setBackground(Color.white);

        vv.getRenderContext().setVertexLabelTransformer(MapTransformer.<Object,String>getInstance(
        		LazyMap.<Object,String>decorate(new HashMap<Object,String>(), new ToStringLabeller<Object>())));
        
        vv.getRenderContext().setEdgeLabelTransformer(MapTransformer.<Object,String>getInstance(
        		LazyMap.<Object,String>decorate(new HashMap<Object,String>(), new ToStringLabeller<Object>())));

        vv.setVertexToolTipTransformer(vv.getRenderContext().getVertexLabelTransformer());
        

        Container content = getContentPane();
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        content.add(panel);
       
        final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        vv.setGraphMouse(graphMouse);
      
        vv.setGraphMouse(graphMouse);
        vv.addKeyListener(graphMouse.getModeKeyListener());

        graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
        
        final ScalingControl scaler = new CrossoverScalingControl();
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });
        
        JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vv, instructions);
            }});

     
        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        JComboBox modeBox = graphMouse.getModeComboBox();
        controls.add(modeBox);
       
        controls.add(help);
        content.add(controls, BorderLayout.SOUTH);
    }
    
    /**
     * copy the visible part of the graph to a file as a jpeg image
     * @param file
     */
    public void writeJPEGImage(File file) {
        int width = vv.getWidth();
        int height = vv.getHeight();

        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();
        vv.paint(graphics);
        graphics.dispose();
        
        try {
            ImageIO.write(bi, "jpeg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int print(java.awt.Graphics graphics,
            java.awt.print.PageFormat pageFormat, int pageIndex)
            throws java.awt.print.PrinterException {
        if (pageIndex > 0) {
            return (Printable.NO_SUCH_PAGE);
        } else {
            java.awt.Graphics2D g2d = (java.awt.Graphics2D) graphics;
            vv.setDoubleBuffered(false);
            g2d.translate(pageFormat.getImageableX(), pageFormat
                    .getImageableY());

            vv.paint(g2d);
            vv.setDoubleBuffered(true);

            return (Printable.PAGE_EXISTS);
        }
    }
    
 
  

    /**
     * a driver for this demo
     */
    @SuppressWarnings("serial")
	  public static void showtest(DirectedOrderedSparseMultigraph<Object,Object> graph) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JShow demo = new JShow(graph);
        
        JMenu menu = new JMenu("Snapshot");
        menu.add(new AbstractAction("To JPEG") {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser  = new JFileChooser();
                int option = chooser.showSaveDialog(demo);
                if(option == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    demo.writeJPEGImage(file);
                }
            }});
        menu.add(new AbstractAction("Print") {
            public void actionPerformed(ActionEvent e) {
                    PrinterJob printJob = PrinterJob.getPrinterJob();
                    printJob.setPrintable(demo);
                    if (printJob.printDialog()) {
                        try {
                            printJob.print();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
            }});
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(demo);
        frame.pack();
        frame.setVisible(true);
    }
}


