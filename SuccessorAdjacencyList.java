import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuccessorAdjacencyList extends BaseAdjacencyList {
    private Vertice currentVertice;
    private List<Vertice> verticeChildren;

    public SuccessorAdjacencyList(Graph graph) {
        super();

        for (Map.Entry<Vertice, Integer> entry : graph.verticesAndTheirIndices.entrySet()) {
            verticeChildren = new ArrayList<>();
            currentVertice = entry.getKey();

            fillVerticeChildren(graph);

            adjacencyList.put(entry.getKey(), verticeChildren);
        }
    }

    private void fillVerticeChildren(Graph graph) {
        for (Map.Entry<Edge, Integer> innerEntry : graph.edgesAndTheirIndices.entrySet()) {
            if (currentVertice.name.compareTo(innerEntry.getKey().firstVertice.name) == 0)
                verticeChildren.add(innerEntry.getKey().secondVertice);
        }
    }
}
