package aboutGraphs.representations;

import java.util.ArrayList;
import java.util.List;

import aboutGraphs.core.*;

public class SuccessorAdjacencyList extends BaseAdjacencyList {

    public SuccessorAdjacencyList(Graph graph) {
        super();

        for (Vertice vertice : graph.vertices) {
            adjacencyList.put(vertice, getVerticeChildren(vertice, graph));
        }
    }

    private List<Vertice> getVerticeChildren(Vertice vertice, Graph graph) {
        List<Vertice> verticeChildren = new ArrayList<>();

        for (Edge edge : graph.edges) {
            if (vertice.equals(edge.firstVertice))
                verticeChildren.add(edge.secondVertice);
        }

        return verticeChildren;
    }

    public void showSuccessorAdjacencyList() {
        System.out.println("\n\tSUCCESSOR ADJACENCY LIST\n");
        super.showAdjacencyList();
    }
}