/*
 * Created on Apr 22, 2004
 */
package scratch.danyel;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;


/**
 * 
 * @author danyelf
 */
public interface TemporalFunction {

    /**
     * @param edge
     * @return
     */
    boolean acceptEdge(Edge edge);

    /**
     * @param vertex
     * @return
     */
    boolean acceptVertex(Vertex vertex);

}
