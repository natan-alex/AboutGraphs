package representations;

import java.util.ArrayList;
import java.util.List;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;

public class SuccessorAdjacencyList extends AbstractAdjacencyList {
    public SuccessorAdjacencyList(AbstractGraph graph) {
        super();

        for (AbstractVertice vertice : graph.getVertices()) {
            adjacencyList.put(vertice, getVerticeChildren(vertice, graph));
        }
    }

    private List<AbstractVertice> getVerticeChildren(AbstractVertice vertice, AbstractGraph graph) {
        List<AbstractVertice> verticeChildren = new ArrayList<>();

        for (AbstractEdge edge : graph.getEdges()) {
            if (vertice.equals(edge.getFirstVertice()))
                verticeChildren.add(edge.getSecondVertice());
        }

        return verticeChildren;
    }

    public void showSuccessorAdjacencyList() {
        System.out.println("\n\tSUCCESSOR ADJACENCY LIST\n");
        super.showAdjacencyList();
    }
}
