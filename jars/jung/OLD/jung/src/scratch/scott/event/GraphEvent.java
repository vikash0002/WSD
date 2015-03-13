package scratch.scott.event;

import java.util.EventObject;

import edu.uci.ics.jung.graph.ArchetypeEdge;
import edu.uci.ics.jung.graph.ArchetypeGraph;
import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.utils.UserDataContainer;

/**
 * @author Scott White
 */
public class GraphEvent extends EventObject {
    private GraphEventType mEventType;
    private UserDataContainer mGraphElement;

    public GraphEvent(GraphEventType eventType, UserDataContainer graphElement) {
        super(extractGraph(graphElement));

        mEventType = eventType;
        mGraphElement = graphElement;
    }

    public GraphEventType getEventType() {
        return mEventType;
    }

    public UserDataContainer getGraphElement() {
        return mGraphElement;
    }

    public ArchetypeGraph getGraph() {
        return (ArchetypeGraph) getSource();
    }

    private static ArchetypeGraph extractGraph(UserDataContainer graphElement) {
        if (graphElement instanceof ArchetypeVertex) {
            return ((ArchetypeVertex) graphElement).getGraph();
        } else if (graphElement instanceof ArchetypeEdge) {
             return ((ArchetypeEdge) graphElement).getGraph();
        } else {
            throw new IllegalArgumentException("Graph Element must be either e node or an edge.");
        }
    }
}
