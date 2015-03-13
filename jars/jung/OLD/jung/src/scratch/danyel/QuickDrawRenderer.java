/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 */
package scratch.danyel;

import java.awt.Color;
import java.awt.Graphics;

import samples.preview_new_graphdraw.*;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.StringLabeller;

/**
 * A rather fixed renderer for generating VERY quick demos. Use this code for
 * debugging your system: this isn't the prettiest renderer, just a fairly
 * functional one.
 * <p>
 * 
 * @author danyelf
 */
public class QuickDrawRenderer implements VertexRenderer, EdgeRenderer {

    private static final Color vertexFGColor = Color.BLACK;

    private static final Color vertexBGColor = new Color(0.8f, 0.8f, 1.0f, 1.0f);

    private static final Color edgeColor = Color.DARK_GRAY;

    private StringLabeller sl;

    /**
     * Creates a QuickDrawRenderer that will be drawn in the "light" style: a
     * colored box next to text, instead of text overlaying the box. The second
     * argument doesn't matter how you set it.
     * 
     * @param sl
     * @param light
     */
    public QuickDrawRenderer(StringLabeller sl) {
        this.sl = sl;
    }

    public void setStringLabeller(StringLabeller sl) {
        this.sl = sl;
    }

    /**
     * Simple label function returns the StringLabeller's notion of v's label.
     * It may be sometimes useful to override this.
     * 
     * @param v
     *            a vertex
     * @return the label on the vertex.
     */
    protected String getLabel(Vertex v) {
        String s = sl.getLabel(v);
        if (s == null) {
            return "?";
        } else {
            return s;
        }
    }

    /**
     * Paints the vertex, using the settings above (VertexColors, etc). In this
     * implmenetation, vertices are painted as filled squares with textual
     * labels over the filled square.
     */
    public void renderVertex(Graphics g, VisVertex vc) {
        Vertex v = vc.getVertex();
        int x = (int) vc.x;
        int y = (int) vc.y;
        String label = getLabel(v);
        paintLightVertex(g, v, x, y, label);
        return;
    }

    /**
     * @param g
     * @param v
     * @param x
     * @param y
     */
    private void paintLightVertex(Graphics g, Vertex v, int x, int y,
            String label) {
        Color bg = vertexBGColor;
        Color fg = vertexFGColor;

        g.setColor(fg);
        g.fillRect(x - 6, y - 6, 12, 12);
        g.setColor(bg);
        g.fillRect(x - 5, y - 5, 10, 10);
        if (label.equals("?")) return;
        g.setColor(fg);
        g.drawString(label, x + 8, y + 6);
    }

    /**
     * @see samples.preview_new_graphdraw.VertexRenderer#renderVertex(java.awt.Graphics,
     *      samples.preview_new_graphdraw.VisVertex)
     */

    /**
     * @see samples.preview_new_graphdraw.EdgeRenderer#renderEdge(java.awt.Graphics,
     *      samples.preview_new_graphdraw.VisEdge)
     */
    public void renderEdge(Graphics g, VisEdge ec) {
        g.setColor(Color.gray);
        if (ec.getFront() == ec.getBack()) {
            int x = (int) ec.getFront().x;
            int y = (int) ec.getBack().y;
            // self-loops
            g.drawOval(x - 15, y - 30, 30, 30);
        } else {
            g.drawLine((int) ec.getFront().x, (int) ec.getFront().y, (int) ec
                    .getBack().x, (int) ec.getBack().y);
        }
    }

}