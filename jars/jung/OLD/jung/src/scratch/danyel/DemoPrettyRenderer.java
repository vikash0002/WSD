/*
 * Created on Apr 15, 2004
 */
package scratch.danyel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.EdgeWeightLabeller;
import edu.uci.ics.jung.graph.decorators.EdgeWeightLabellerStringer;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.StringLabeller.UniqueLabelException;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.SparseGraph;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;
import edu.uci.ics.jung.utils.TypedVertexGenerator;
import edu.uci.ics.jung.utils.VertexGenerator;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphDraw;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.contrib.Arrow;
import edu.uci.ics.jung.visualization.graphdraw.SettableRenderer;


/**
 * 
 * @author danyelf
 */
public class DemoPrettyRenderer {
    
    public static void main(String[] s ) {
        testGraph();
    }
    
    public static void testArrow() {
        final Arrow ar = new Arrow( Arrow.CLASSIC, 20, 20);
        JPanel jp = new JPanel() {
            public void paintComponent( Graphics g ) {
                Graphics2D g2d = (Graphics2D) g;
                g.drawLine( 10, 10, 60, 10 );
                ar.drawArrow(  g2d, 10, 10, 60, 10, 10);

                g.drawLine( 10, 10, 60, 60 );
                ar.drawArrow(  g2d, 10, 10, 60, 60, 10);

                
                g.drawLine( 10, 10, 10, 60 );
                ar.drawArrow(  g2d, 10, 10, 10, 60, 10);

            }
        };
        jp.setPreferredSize(new Dimension( 300, 300));
        JFrame jf = new JFrame();
        jf.getContentPane().add( jp );
        jf.pack();
        jf.show();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
    
    public static void testGraph() {
        Graph g = new SparseGraph();
        VertexGenerator vg = new TypedVertexGenerator( g.getEdgeConstraints() );
        Vertex v1 = g.addVertex((Vertex) vg.create());
        Vertex v2 = g.addVertex((Vertex) vg.create());
        Vertex v3 = g.addVertex((Vertex) vg.create());
        Edge e12 = g.addEdge( new UndirectedSparseEdge( v1, v2 ));
        Edge e23 = g.addEdge( new DirectedSparseEdge( v2, v3 ));
        Edge e31 = g.addEdge( new DirectedSparseEdge( v3, v1 ));
        Edge e13 = g.addEdge( new DirectedSparseEdge( v1, v3 ));
        Edge e11 = g.addEdge( new DirectedSparseEdge( v1, v1));
        StringLabeller sl = StringLabeller.getLabeller(g);
        try {
            sl.setLabel(v1, "Vert1");
            sl.setLabel(v2, "Vert2");
            sl.setLabel(v3, "Vert3");
        } catch (UniqueLabelException e) {
            e.printStackTrace();
        }
        EdgeWeightLabeller ewl = EdgeWeightLabeller.getLabeller(g);
        ewl.setWeight( e12, 1 );
        ewl.setWeight( e23, 2 );
        ewl.setWeight( e31, 3 );
        ewl.setWeight( e13, 4 );
        ewl.setWeight( e11, 2);

        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        
        GraphDraw gd = new GraphDraw( g );
        VisualizationViewer vv = gd.getVisualizationViewer();
        vv.setGraphLayout( new FRLayout(g));
        SettableRenderer r = new SettableRenderer( sl, new EdgeWeightLabellerStringer(ewl));
        vv.setRenderer( r );
        r.setShouldDrawUndirectedArrows( true );
        r.setEdgeColor(Color.darkGray);
        r.setShouldDrawSelfLoops(true);
        gd.setBackground(Color.white);
        jp.add( gd );
        addBottomButton( jp, r );
        
        JFrame jf = new JFrame();
        jf.getContentPane().add( jp );
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.show();
        
    }

    /**
     * @param jp
     * @param gd
     */
    private static void addBottomButton(final JPanel jp, final SettableRenderer sr) {
        JToggleButton jb = new JToggleButton("Light");
        jp.add( jb, BorderLayout.SOUTH );
        jb.setSelected(true);
        jb.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                sr.setLightDrawing( ((JToggleButton) e.getSource()).isSelected() );
                jp.repaint();
            }
            
        });
    }

}
