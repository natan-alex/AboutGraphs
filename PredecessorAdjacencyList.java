import java.util.ArrayList;
import java.util.List;

public class PredecessorAdjacencyList extends BaseAdjacencyList implements GraphRepresentation {
    public PredecessorAdjacencyList(Graph graph) {
        super();

        for (Vertice vertice : graph.vertices) {
            adjacencyList.put(vertice, getVerticeChildren(vertice, graph));
        }
    }

    private List<Vertice> getVerticeChildren(Vertice vertice, Graph graph) {
        List<Vertice> verticeChildren = new ArrayList<>();

        for (Edge edge : graph.edges) {
            if (vertice.equals(edge.secondVertice))
                verticeChildren.add(edge.firstVertice);
        }

        return verticeChildren;
    }

    @Override
    public void show() {
        System.out.println("\n\tPREDECESSOR ADJACENCY LIST\n");
        super.showAdjacencyList();
    }
}