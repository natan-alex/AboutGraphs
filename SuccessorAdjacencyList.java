import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuccessorAdjacencyList extends BaseAdjacencyList {
    public SuccessorAdjacencyList(Graph graph) {
        super();
        List<Vertice> itemsList;

        for (Map.Entry<Vertice, Integer> entry : graph.vertices.entrySet()) {
            itemsList = new ArrayList<>();

            for (Map.Entry<Edge, Integer> innerEntry : graph.edges.entrySet()) {
                if (entry.getKey().name.compareTo(innerEntry.getKey().firstVertice.name) == 0)
                    itemsList.add(innerEntry.getKey().secondVertice);
            }

            adjacencyList.put(entry.getKey(), itemsList);
        }
    }
}
