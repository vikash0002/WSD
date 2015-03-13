/*
 * Created on Apr 13, 2004
 */
package scratch.danyel;

import java.awt.Dimension;
import java.util.Date;

import javax.swing.JFrame;

import samples.preview_new_graphdraw.iter.IterableLayout;
import samples.preview_new_graphdraw.iter.LocalGraphDraw;
import samples.preview_new_graphdraw.iterablelayouts.KKLayout;
import samples.preview_new_graphdraw.staticlayouts.CircleLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.Indexer;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.ToStringLabeller;
import edu.uci.ics.jung.graph.impl.UndirectedSparseEdge;
import edu.uci.ics.jung.utils.TestGraphs;

/**
 * Three easy methods for quickly and painlessly popping a graph on screen for
 * debugging. WARNING: this will start the AWT/Swing thread. Your program will
 * not end when the last window is closed, nor will it end when your main thread
 * finishes. You will need to explicitly call <pre>System.exit(1)</pre> in order
 * to terminate the program. (Java is annoying like that.)
 * 
 * @author danyelf
 */
public class QuickDraw {

    private static final String TOSTRINGLABELLER = "QuickDraw.TOSTRINGLABELLER";

    /**
     * Draws a graph quickly with a ToString labeller and the SettableRenderer
     * with the "light" style.
     */
    public static void quickDrawWithToString(Graph g) {
        StringLabeller sl = ToStringLabeller.setLabellerTo(g, null);
        quickDraw(g, sl);
    }

    /**
     * Draws a graph quickly with its default StringLabeller (or nothing) and
     * the SettableRenderer with the "light" style.
     */
    public static void quickDraw(Graph g) {
        StringLabeller sl = StringLabeller.getLabeller(g);
        quickDraw(g, sl);
    }

    /**
     * Draws a graph quickly with the given StringLabeller and the
     * SettableRenderer with the "light" style.
     */
    public static void quickDraw(Graph g, StringLabeller sl) {
        JFrame jf = new JFrame("" + new Date());
        jf.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        //        GraphDraw gd = new GraphDraw(g);
        //        gd.setRenderer(new SettableRenderer(sl, true));

        QuickDrawRenderer sr = new QuickDrawRenderer(sl);
        CircleLayout cl = new CircleLayout();
        IterableLayout il = new KKLayout();

        LocalGraphDraw lgd = new LocalGraphDraw(g, cl, il, sr, sr,
                new Dimension(400, 400), true);
        lgd.start();

        jf.getContentPane().add(lgd.getPanel());
        jf.pack();
        jf.show();
    }

    public static void main(String[] s) {
        System.out.println("Testing quickDraw");
        Graph g = TestGraphs.getDemoGraph();
        Indexer id = Indexer.getIndexer(g);
        Vertex self = (Vertex) id.getVertex(0);
        g.addEdge(new UndirectedSparseEdge(self, self));
        quickDraw(g);
        quickDrawWithToString(g);

    }

}