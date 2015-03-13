/*
 * Created on Nov 11, 2003
 */
package scratch.danyel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.VertexColorFunction;
import edu.uci.ics.jung.graph.filters.UnassembledGraph;
import edu.uci.ics.jung.io.PajekNetReader;
import edu.uci.ics.jung.visualization.AbstractRenderer;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphDraw;
import edu.uci.ics.jung.visualization.VisualizationViewer;

/**
 * @author danyelf
 */
public class TMAMiniGraph extends JPanel {

	//	public TMAMiniGraph( Name name, APIFirstClass apif ) {
	//		NameVertex nv = apif.getNameVertex( name );
	//		Set vertices = new HashSet( nv.getNeighbors() );
	//		vertices.add( nv );
	//		Graph subgraph = new UnassembledGraph("LocalGraph", vertices,
	// nv.getGraph().getEdges(), (Graph) nv.getGraph()).assemble();
	//		StringLabeller sl = StringLabeller.getLabeller( subgraph );
	//		for (Iterator iter = vertices.iterator(); iter.hasNext();) {
	//			NameVertex n = (NameVertex) iter.next();
	//			try {
	//				sl.setLabel(n, n.getName().toString());
	//			} catch (UniqueLabelException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//		this.add( new GraphDraw(subgraph) );
	//	}

	private GraphDraw gd;
	String name;

	public TMAMiniGraph(Vertex v) {
		//		System.out.println("NEW!");
		Set vertices = new HashSet(v.getNeighbors());
		vertices.add(v);
		Graph subgraph =
			new UnassembledGraph(
				"LocalGraph",
				vertices,
				v.getGraph().getEdges(),
				(Graph) v.getGraph())
				.assemble();
		gd = new GraphDraw(subgraph);
        VisualizationViewer vv = gd.getVisualizationViewer();
		vv.setGraphLayout( new FRLayout( subgraph ) );
		vv.setRenderer(renderer);
		renderer.setVertexColorFunction(vcf);
		vcf.setCenter((Vertex) v.getEqualVertex(subgraph));
		this.add(gd);
		name = (StringLabeller.getLabeller((Graph) v.getGraph()).getLabel(v));
		System.out.println(name);
	}

	static class MyVertexColorFunction implements VertexColorFunction {

		private Vertex center;

		public void setCenter(Vertex v) {
			this.center = v;
		}
		
		public Vertex getCenter() {
			return center;
		}

		public Color getForeColor(Vertex v) {
			return Color.BLACK;
		}

		public Color getBackColor(Vertex v) {
			if (v.equals(center)) {
				return Color.CYAN;
			} else {
				return Color.GRAY;
			}
		}

	}

	static class MyRenderer extends AbstractRenderer {
		private VertexColorFunction vcf;
		private StringLabeller sl;

		public MyRenderer(StringLabeller sl) {
			this.sl = sl;
		}

		public void setVertexColorFunction(VertexColorFunction vcf) {
			this.vcf = vcf;
		}
		public void paintVertex(Graphics g, Vertex v, int x, int y) {

			String label = sl.getLabel(v);

			Color fg = vcf.getForeColor(v);

			g.setColor(vcf.getBackColor(v));

			g.fillRect(x - 6, y - 6, 12, 12 );
			g.setColor(fg);
			g.drawString(label, x - 6, y + 6 + g.getFontMetrics().getHeight());
		}

		public void paintEdge(Graphics g, Edge e, int x1, int y1, int x2, int y2) {
			g.setColor (Color.GRAY );
			g.drawLine( x1, y1, x2, y2 );
		}

	}

	static List allVertices;
	static int index = 0;
	private static MyRenderer renderer;
	private static MyVertexColorFunction vcf;

	private static TMAMiniGraph tma;

	public static void main(String[] main) throws IOException {
        PajekNetReader pnr = new PajekNetReader();
        Graph graph = pnr.load("samples/datasets/smyth.net");
//		PajekNetFile file = new PajekNetFile();
//		Graph graph = file.load("samples/datasets/smyth.net");
		JFrame jf = new JFrame();
		final JPanel panel = new JPanel();

		renderer = new MyRenderer(StringLabeller.getLabeller(graph, PajekNetReader.LABEL));
		vcf = new MyVertexColorFunction();

		JButton next = new JButton("next!");
		jf.getContentPane().add(panel, BorderLayout.CENTER);
		jf.getContentPane().add(next, BorderLayout.SOUTH);

		next.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				tma.stop();
				panel.removeAll();
				Vertex v = (Vertex) allVertices.get(index++);
				panel.add(tma = new TMAMiniGraph(v));
				panel.revalidate();
			}

		});

		allVertices = new ArrayList(graph.getVertices());

		Vertex v = (Vertex) allVertices.get(index++);
		panel.add(tma = new TMAMiniGraph(v));
		jf.pack();
		jf.show();
	}

	protected void stop() {
		//		System.out.println("STOP " + name);
		gd.getVisualizationViewer().stop();
	}

}
