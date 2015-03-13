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
import java.text.NumberFormat;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.visualization.AbstractRenderer;
import edu.uci.ics.jung.visualization.FadingVertexLayout;

public class FadeRenderer extends AbstractRenderer {

	NumberFormat nf;
	StringLabeller sl;
	public static boolean debug = false;
	private FadingVertexLayout fvl;
    protected static int RANGE = 100;

	public FadeRenderer(StringLabeller sl, FadingVertexLayout fvl) {
		this.fvl = fvl;
		nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		this.sl = sl;
	}

	public void paintEdge(Graphics g, Edge e, int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
	}

	public void paintVertex(Graphics g, Vertex v, int x, int y) {

		boolean isHidden = fvl.isHidden(v);
		int level = fvl.getFadeLevel(v);

		if (isHidden && level == fvl.getMaxLevel())
			return;

//		String label = GraphUtils.getLabel(sl, v);
        String label = sl.getLabel(v);

		if (debug) {

			g.setColor(new Color(100, 100, 100));
			g.drawOval(
				x - RANGE / 2,
				y - RANGE / 2,
				RANGE,
				RANGE);
		}

		drawNode(g, v, x, y, isHidden, level, label);

	}

	protected void drawNode(
		Graphics g,
		Vertex v,
		int x,
		int y,
		boolean isHidden,
		int level,
		String label) {

		if (isPicked(v))
			g.setColor(pickColor(isHidden, level, Color.ORANGE));
		else
			g.setColor(pickColor(isHidden, level, Color.RED));

		g.fillRect(x - 8, y - 6, g.getFontMetrics().stringWidth(label) + 8, 16);
		g.setColor(pickColor(isHidden, level, Color.BLACK));
		g.drawString(label, x - 4, y + 6);
	}

	/**
	 * @param isHidden
	 * @param level
	 * @param color
	 * @return
	 */
	protected Color pickColor(boolean isHidden, int level, Color color) {
		// level runs from 0 to 10
		// thus, 10 is opaque (if not hidden) and transparent (if hidden)
		// and go across the gamut

		int max = fvl.getMaxLevel();
		double step = 255.0 / max;

		if (isHidden) {
			level = max - level;
		}
		//		if (level != 10) System.out.println( level );
		Color rv =
			new Color(
				color.getRed(),
				color.getGreen(),
				color.getBlue(),
				(int) (step * level));
		return rv;
	}
}
