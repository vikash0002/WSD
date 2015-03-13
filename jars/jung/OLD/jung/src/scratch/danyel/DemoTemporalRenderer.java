/*
 * Created on Apr 15, 2004
 */
package scratch.danyel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import samples.preview_new_graphdraw.EdgeRenderer;
import samples.preview_new_graphdraw.VertexRenderer;
import samples.preview_new_graphdraw.impl.SimpleEdgeRenderer;
import samples.preview_new_graphdraw.iter.IterableLayout;
import samples.preview_new_graphdraw.iter.LocalGraphDraw;
import samples.preview_new_graphdraw.iterablelayouts.KKLayout;
import samples.preview_new_graphdraw.staticlayouts.CircleLayout;
import samples.preview_new_graphdraw.test.CircleRenderer;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.utils.TypedVertexGenerator;
import edu.uci.ics.jung.utils.VertexGenerator;


/**
 * 
 * @author danyelf
 */
public class DemoTemporalRenderer {
    

    /**
     * 
     * @author danyelf
     */
    public static class TimeFunc implements TemporalFunction {

        /**
         * @see scratch.danyel.TemporalFunction#acceptEdge(edu.uci.ics.jung.graph.Edge)
         */
        public boolean acceptEdge(Edge edge) {
            // TODO Auto-generated method stub TimeFunc:acceptEdge
            return false;
        }

        /**
         * @see scratch.danyel.TemporalFunction#acceptVertex(edu.uci.ics.jung.graph.Vertex)
         */
        public boolean acceptVertex(Vertex vertex) {
            return false;
        }

    }
    
    public static void main(String[] s ) {
        testGraph();
    }
    
    
    public static void testGraph() {
        Graph g = new DirectedSparseGraph();
        Vertex[] vertices = new Vertex[10];
        VertexGenerator vg = new TypedVertexGenerator( g.getEdgeConstraints() );
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = g.addVertex( (Vertex) vg.create() );
        }
        
        for(int i = 0; i < vertices.length; i++ ) {
            for(int j = 0; j < vertices.length; j++ ) {
                Edge e = g.addEdge( new DirectedSparseEdge(vertices[i],vertices[j]));
            }
        }

        JFrame jf = new JFrame(" Demo of temporal graph ");
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //        GraphDraw gd = new GraphDraw(g);
        //        gd.setRenderer(new SettableRenderer(sl, true));

        VertexRenderer vr = new CircleRenderer();
        EdgeRenderer plain = new SimpleEdgeRenderer();
        Color transparent = new Color( 0, 0, 0, 0.1f);
        EdgeRenderer gray = new SimpleEdgeRenderer( transparent );
        TemporalEdgeRenderer ter = new TemporalEdgeRenderer( new TimeFunc(), plain, gray );
        
        CircleLayout cl = new CircleLayout();
        IterableLayout il = new KKLayout();

        LocalGraphDraw lgd = new LocalGraphDraw(g, cl, il, vr, ter,
                new Dimension(400, 400), true);
        lgd.start();

        
        jf.getContentPane().add(lgd.getPanel());
        jf.pack();
        jf.show();
    }

    /**
     * @param jp
     * @param gd
     */
    private static void addBottomButton(final JPanel jp, TemporalFunction tf ) {
        JToggleButton jb = new JToggleButton("Play");
        jp.add( jb, BorderLayout.SOUTH );
        jb.setSelected(true);
        jb.addActionListener( new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            }
            
        });
    }

}
