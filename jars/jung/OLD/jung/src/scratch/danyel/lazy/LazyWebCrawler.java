/*
 * Created on Dec 11, 2003
 */
package scratch.danyel.lazy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.parser.ParserDelegator;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.graph.decorators.StringLabeller.UniqueLabelException;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.utils.UserData;

/**
 * @author danyelf
 */
public class LazyWebCrawler {

	private static final LazySparseVertex[] vv = new LazySparseVertex[0];

	static Map uniqueUrls = new HashMap();
	
	static URL getUniqueUrl(String string) throws MalformedURLException {
		if(! uniqueUrls.containsKey(string)) {
			URL un = new URL( string );
			uniqueUrls.put(string, un);
		}
		return (URL) uniqueUrls.get( string );
	}
	
	
	public static void main(String[] args) throws MalformedURLException {
		Graph g = new DirectedSparseGraph();
		WebCrawlerFactory wcf = new WebCrawlerFactory(g);
		wcf.getVertex(getUniqueUrl("http://www.ics.uci.edu"));
		System.out.println(g.getVertices().size());
		while (g.getVertices().size() < 20) {
			// pick a random vertex
			LazySparseVertex[] varr = (LazySparseVertex[]) g.getVertices().toArray(vv);
			LazySparseVertex random = varr[(int) (Math.random() * varr.length)];

			System.out.println(">" + random.getUniqueID());
			System.out.println(">>" + random.getNeighbors());

		}
	}

	public static class WebCrawlerFactory extends LazySparseVertexFactory {

		private CallbackHandler ch;
		private Graph g;

		public WebCrawlerFactory(Graph g) {
			super(g);
			this.g = g;
			ch = new CallbackHandler();
		}

		/**
		 * @see scratch.danyel.lazy.LazySparseVertexFactory#annotateVertex(scratch.danyel.lazy.LazySparseVertex)
		 */
		public void annotateVertex(LazySparseVertex vertex) {
			readVertex(vertex);
		}

		/**
		 * @param vertex
		 */
		private void readVertex(LazySparseVertex vertex) {
			try {
				URL url = (URL) vertex.getUniqueID();
				System.out.println("Now reading " + url);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				// let's parse it!
				ParserDelegator kit = new ParserDelegator();
				kit.parse(in, ch, true);
				vertex.addUserDatum("LAZY_WEB", new HashSet(ch.neighbors), UserData.REMOVE);
				StringLabeller.getLabeller(g).setLabel(vertex, ch.title);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (UniqueLabelException e) {
				e.printStackTrace();
			}
		}

		/**
		 * @see scratch.danyel.lazy.LazySparseVertexFactory#getNeighborsIds(java.lang.Object)
		 */
		public Object[] getNeighborsIds(Object o) {
			Vertex v = getVertex(o);
			Set neighbors = (Set) v.getUserDatum("LAZY_WEB");
			if (neighbors == null)
				return new Object[0];
			return neighbors.toArray();
		}

		
		
	}

	private static class CallbackHandler extends HTMLEditorKit.ParserCallback {
		
		
		List neighbors;
		private boolean readtitle = false;
		private String title = "no title";

		/**
		 * Creates the CallbackHandler.
		 */
		public CallbackHandler() {
			neighbors = new ArrayList();
			;
		}

		/**
		 * Invoked when a start tag is encountered. Based on the tag this may
		 * update the BookmarkEntry and state, or update the parentDate.
		 */
		public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
			readtitle = false;
			if (t == HTML.Tag.TITLE) {
				readtitle = true;
			}
			if (t == HTML.Tag.A) {
				// URL
				URL url;
				try {
					url = getUniqueUrl(((String) a.getAttribute(HTML.Attribute.HREF)));
					neighbors.add(url);
//					System.out.println("Adding " + url);
				} catch (MalformedURLException murle) {
					url = null;
				}
			}
		}

		/**
		 * @see javax.swing.text.html.HTMLEditorKit.ParserCallback#handleEndTag(javax.swing.text.html.HTML.Tag,
		 *      int)
		 */
		public void handleEndTag(Tag t, int arg1) {
			if (t == HTML.Tag.TITLE) {
				readtitle = false;
			}
		}

		/**
		 * @see javax.swing.text.html.HTMLEditorKit.ParserCallback#handleText(char[],
		 *      int)
		 */
		public void handleText(char[] arg0, int arg1) {
			if (readtitle) {
				title = new String(arg0);
			}
		}

	}
}
