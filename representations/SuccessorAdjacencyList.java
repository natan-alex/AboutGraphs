package representations;

import java.util.ArrayList;
import java.util.List;

import core.abstractions.AbstractEdge;
import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;

public class SuccessorAdjacencyList extends AbstractAdjacencyList {
    public SuccessorAdjacencyList(AbstractGraph relatedGraph) {
        super(relatedGraph);
    }

    @Override
    protected void fillAdjacencyList(AbstractGraph relatedGraph) {
        for (AbstractVertice vertice : relatedGraph.getVertices()) {
            adjacencyList.put(vertice, getVerticeChildren(vertice, relatedGraph));
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
