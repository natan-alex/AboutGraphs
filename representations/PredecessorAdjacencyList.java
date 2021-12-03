package representations;

import java.util.ArrayList;
import java.util.List;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;

public class PredecessorAdjacencyList extends AbstractAdjacencyList {
    public PredecessorAdjacencyList(AbstractGraph graph) {
        super();

        for (AbstractVertice vertice : graph.getVertices()) {
            adjacencyList.put(vertice, getVerticeChildren(vertice, graph));
        }
    }

    private List<AbstractVertice> getVerticeChildren(AbstractVertice vertice, AbstractGraph graph) {
        List<AbstractVertice> verticeChildren = new ArrayList<>();

        for (AbstractEdge edge : graph.getEdges()) {
            if (vertice.equals(edge.getSecondVertice()))
                verticeChildren.add(edge.getFirstVertice());
        }

        return verticeChildren;
    }

    public void showPredecessorAdjacencyList() {
        System.out.println("\n\tPREDECESSOR ADJACENCY LIST\n");
        super.showAdjacencyList();
    }
}