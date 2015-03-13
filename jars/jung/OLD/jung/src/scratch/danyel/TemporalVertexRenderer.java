/*
 * Created on Apr 22, 2004
 */
package scratch.danyel;

import java.awt.Graphics;

import samples.preview_new_graphdraw.VertexRenderer;
import samples.preview_new_graphdraw.VisVertex;


/**
 * 
 * @author danyelf
 */
public class TemporalVertexRenderer implements VertexRenderer {

    protected TemporalFunction tf;
    private VertexRenderer failureVertexRenderer;
    private VertexRenderer  passVertexRenderer;
    
    public TemporalVertexRenderer( TemporalFunction tf, VertexRenderer passVertexRenderer, VertexRenderer failureVertexRenderer  ) {
        this.tf = tf;
        this.passVertexRenderer = passVertexRenderer;
        this.failureVertexRenderer = failureVertexRenderer;
    }
    /**
     * @see samples.preview_new_graphdraw.VertexRenderer#renderVertex(java.awt.Graphics, samples.preview_new_graphdraw.VisVertex)
     */
    public void renderVertex(Graphics g, VisVertex ve) {
        if( tf.acceptVertex( ve.getVertex() )) {
            passVertexRenderer.renderVertex(g, ve);
        } else {
            failureVertexRenderer.renderVertex(g, ve);
        }
    }

}
