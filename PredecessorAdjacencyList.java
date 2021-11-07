import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PredecessorAdjacencyList extends BaseAdjacencyList {
    public PredecessorAdjacencyList(Graph graph) {
        super();
        List<Vertice> itemsList;

        for (Map.Entry<Vertice, Integer> entry : graph.verticesAndTheirIndices.entrySet()) {
            itemsList = new ArrayList<>();

            for (Map.Entry<Edge, Integer> innerEntry : graph.edgesAndTheirIndices.entrySet()) {
                if (entry.getKey().name.compareTo(innerEntry.getKey().secondVertice.name) == 0) {
                    itemsList.add(innerEntry.getKey().firstVertice);
                }
            }

            adjacencyList.put(entry.getKey(), itemsList);
        }
    }
}
