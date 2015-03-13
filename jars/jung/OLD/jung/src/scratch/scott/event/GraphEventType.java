package scratch.scott.event;

/**
 * @author Scott White
 */
public interface GraphEventType {
    static class NodeAddition implements GraphEventType {
    }
    static class NodeRemoval implements GraphEventType {
    }
    static class NodeCreation implements GraphEventType {
    }
    static class EdgeAddition implements GraphEventType {
    }
    static class EdgeRemoval implements GraphEventType {
    }
    static class EdgeCreation implements GraphEventType {
    }
}
