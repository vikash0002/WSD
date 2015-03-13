/*
 * Created on Jan 7, 2004
 */
package scratch.danyel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;

import samples.preview_new_graphdraw.event.ClickEvent;
import samples.preview_new_graphdraw.event.ClickListener;
import samples.preview_new_graphdraw.impl.GraphLayoutPanel;
import samples.preview_new_graphdraw.impl.SimpleEdgeRenderer;
import samples.preview_new_graphdraw.iter.LocalGraphDraw;
import samples.preview_new_graphdraw.iterablelayouts.KKLayout;
import samples.preview_new_graphdraw.staticlayouts.CircleLayout;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.KPartiteGraph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.io.BipartiteGraphReader;

/**
 * @author danyelf
 */
public class BabySoWoBi extends JPanel {
	private StringLabeller dates;
	private StringLabeller women;
    private KPartiteGraph bpg;
//	private BipartiteGraph bpg;

	/**
	 * @param fr
	 */
	public BabySoWoBi(Reader fr) throws IOException {
        BipartiteGraphReader bgr = new BipartiteGraphReader(true, false, false);
		bpg = bgr.load(fr);

		women = StringLabeller.getLabeller(bpg, BipartiteGraphReader.PART_A);
		dates = StringLabeller.getLabeller(bpg, BipartiteGraphReader.PART_B);

		LocalGraphDraw lgd = createVisualization();

		setLayout(new BorderLayout());
		add(lgd.getPanel());
	}

	public static void main(String[] s) {
		try {
			FileReader fr =
				new FileReader("samples/datasets/southern_women_data.txt");

			JPanel jp = new BabySoWoBi(fr);
			JFrame jf = new JFrame();
			jf.getContentPane().add(jp);
			jf.pack();
			jf.show();
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static DateFormat df = new SimpleDateFormat("d-MMM");

	/**
	 * @param bpg
	 * @param women
	 * @param dates
	 */
	private LocalGraphDraw createVisualization() {
		SimpleEdgeRenderer er = new SimpleEdgeRenderer(Color.lightGray);
		final BPVR wellKnownRenderer = new BPVR(bpg, women, dates);
		Dimension d = new Dimension(500, 500);
		LocalGraphDraw lgd =
			new LocalGraphDraw(
				bpg,
				new CircleLayout(),
				new KKLayout(),
				wellKnownRenderer,
				er,
				d, true);

		lgd.getPanel().setClickPolicy(GraphLayoutPanel.EDGE_AND_VERTEX_BOTH);
//		lgd.setMouseDrag(true);

		lgd.getPanel().addClickListener(new ClickListener() {

			public void edgeClicked(ClickEvent ece) {
				Edge e = (Edge) ece.getGraphObject();
				Vertex v1 = (Vertex) e.getEndpoints().getFirst();
				Vertex v2 = (Vertex) e.getEndpoints().getSecond();
				wellKnownRenderer.setClickedEdge(v1);
				wellKnownRenderer.setClickedEdge(v2);
			}

			public void vertexClicked(ClickEvent vce) {
				wellKnownRenderer.resetClicks();
				wellKnownRenderer.setClicked((Vertex) vce.getGraphObject());
			}

		});

		lgd.start();
		return lgd;
	}

}
