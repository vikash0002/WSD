/*
 * Created on Apr 22, 2004
 */
package scratch.danyel;

import java.awt.Graphics;

import samples.preview_new_graphdraw.EdgeRenderer;
import samples.preview_new_graphdraw.VisEdge;


/**
 * 
 * @author danyelf
 */
public class TemporalEdgeRenderer implements EdgeRenderer {

    protected TemporalFunction tf;
    private EdgeRenderer failureEdgeRenderer;
    private EdgeRenderer passEdgeRenderer;
    
    public TemporalEdgeRenderer( TemporalFunction tf, EdgeRenderer passEdgeRenderer, EdgeRenderer failureEdgeRenderer  ) {
        this.tf = tf;
        this.passEdgeRenderer = passEdgeRenderer;
        this.failureEdgeRenderer = failureEdgeRenderer;
    }
    /**
     * @see samples.preview_new_graphdraw.VertexRenderer#renderVertex(java.awt.Graphics, samples.preview_new_graphdraw.VisVertex)
     */
    public void renderEdge(Graphics g, VisEdge ve) {
        if( tf.acceptEdge( ve.getEdge() )) {
            passEdgeRenderer.renderEdge(g, ve);
        } else {
            failureEdgeRenderer.renderEdge(g, ve);
        }
    }

}
