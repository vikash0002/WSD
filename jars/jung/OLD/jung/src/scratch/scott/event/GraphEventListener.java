package scratch.scott.event;

import java.util.EventListener;

/**
 * @author Scott White
 */
public interface GraphEventListener extends EventListener {
    public void onGraphEvent(GraphEvent event);
}
