import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PredecessorAdjacencyList extends BaseAdjacencyList {
    public PredecessorAdjacencyList(Graph graph) {
        super();
        List<Vertice> itemsList;

        for (Map.Entry<Vertice, Integer> entry : graph.vertices.entrySet()) {
            itemsList = new ArrayList<>();
            itemsList.add(entry.getKey());

            for (Map.Entry<Edge, Integer> innerEntry : graph.edges.entrySet()) {
                if (entry.getKey().name.compareTo(innerEntry.getKey().secondVertice.name) == 0) {
                    itemsList.add(innerEntry.getKey().firstVertice);
                }
            }

            adjacencyList.add(itemsList);
        }
    }
}
