/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/
package scratch.danyel.sample;

import java.awt.Color;
import java.awt.Graphics;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.visualization.AbstractRenderer;

public class SimpleRenderer extends AbstractRenderer { 

	private StringLabeller sl;

	public SimpleRenderer(StringLabeller sl) {
		this.sl = sl;
	}

	public void paintEdge(Graphics g, Edge e, int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);

	}
	
	public void paintVertex(Graphics g, Vertex v, int x, int y) {

		//		Object o = v.getUserData().getDatum(StringVertex.STRING_KEY);
		//		StringVertex.StringVertexData svd = (StringVertex.StringVertexData) o;
		//		String label = svd.label;
		//        String label = ((LabeledVertex)v).getLabel();
//		String label = GraphUtils.getLabel( sl, v );
        String label = sl.getLabel(v);
		
		if (isPicked(v)) {
			g.setColor(Color.ORANGE);
		} else
			g.setColor(Color.RED);

		g.fillRect(x - 8, y - 6, g.getFontMetrics().stringWidth(label) + 8, 16);
		g.setColor(Color.BLACK);
		g.drawString(label, x - 4, y + 6);
	}

}
