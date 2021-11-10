import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuccessorAdjacencyList extends BaseAdjacencyList implements GraphRepresentation {
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
            if (currentVertice.equals(innerEntry.getKey().firstVertice))
                verticeChildren.add(innerEntry.getKey().secondVertice);
        }
    }

    @Override
    public void show() {
        System.out.println("\n\tSUCCESSOR ADJACENCY LIST\n");
        super.showAdjacencyList();
    }
}
