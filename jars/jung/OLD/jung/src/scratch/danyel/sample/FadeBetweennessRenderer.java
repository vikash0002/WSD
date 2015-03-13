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

import edu.uci.ics.jung.algorithms.importance.BetweennessCentrality;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;
import edu.uci.ics.jung.utils.MutableDouble;
import edu.uci.ics.jung.visualization.FadingVertexLayout;
import edu.uci.ics.jung.visualization.PickedInfo;

public class FadeBetweennessRenderer extends FadeRenderer {
	private double maxBetweeness;
    protected PickedInfo pi;

	public FadeBetweennessRenderer( StringLabeller sl, FadingVertexLayout fvl, PickedInfo pi ) {
		super( sl, fvl );
        this.pi = pi;
	}

	protected void drawNode(
		Graphics g,
		Vertex v,
		int x,
		int y,
		boolean isHidden,
		int level,
		String label) {

		Color c = Color.ORANGE;
		//		try {
		//			FilteredGraph fg = (FilteredGraph) v.getGraph();
		//			Vertex vv = (Vertex) fg.getFilteredVertex(v);
		try {
			MutableDouble md =
				(MutableDouble) v.getUserDatum(BetweennessCentrality.CENTRALITY);

			double val = md.doubleValue();
			if (val > maxBetweeness) {
				System.out.println("Oops! MaxBetweeneess = " + maxBetweeness + " but val = " + val );
				maxBetweeness = val;
			}

			c =
				new Color(
					(float) (md.doubleValue() / maxBetweeness),
					(float) (md.doubleValue() / maxBetweeness),
					0.75f);
		} catch (NullPointerException npe) {
		}
		//		} catch (ClassCastException e) {
		//			System.out.println( e );
		//		}

//		Boolean picked =
//			(Boolean) v.getUserDatum(VisualizationViewer.VIS_KEY);
        boolean picked = pi.isPicked(v);
        
//		if (picked == null || !picked.booleanValue()) {
        if (!picked)
        {
			g.setColor(pickColor(isHidden, level, c));
		} else
			g.setColor(pickColor(isHidden, level, Color.RED));

		if (label != null ) {
			g.fillRect(x - 8, y - 6, g.getFontMetrics().stringWidth(label) + 8, 16);
			g.setColor(pickColor(isHidden, level, Color.BLACK));
			g.drawString(label, x - 4, y + 6);
		} else {
			g.fillRect(x - 8, y - 6, 16, 16);
		}

	}

	public void setMaxBetweeness(double d) {
		this.maxBetweeness = d;
	}

}
