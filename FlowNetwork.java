import java.util.ArrayList;
import java.util.List;

public class FlowNetwork extends Graph {
    public final List<FlowEdge> flowEdges;

    public FlowNetwork(String graphRepresentation) throws IllegalArgumentException {
        super(graphRepresentation);
        flowEdges = new ArrayList<>(numberOfEdges);

        for (Edge edge : edges) {
            flowEdges.add(new FlowEdge(edge.stringRepresentation, edge.value));
        }
    }

}
