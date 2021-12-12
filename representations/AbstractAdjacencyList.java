package representations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.abstractions.AbstractGraph;
import core.abstractions.AbstractVertice;

public abstract class AbstractAdjacencyList {
    protected final Map<AbstractVertice, List<AbstractVertice>> adjacencyList;

    protected abstract void fillAdjacencyList(AbstractGraph relatedGraph);
    public abstract void showAdjacencyList();

    protected AbstractAdjacencyList(AbstractGraph relatedGraph) {
        adjacencyList = new HashMap<>();
        fillAdjacencyList(relatedGraph);
    }

    public Map<AbstractVertice, List<AbstractVertice>> getAdjacencyList() {
        return adjacencyList;
    }

    protected void simpleShowAdjacencyList() {
        for (Map.Entry<AbstractVertice, List<AbstractVertice>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey().getRepresentation() + " -> ");

            for (AbstractVertice vertice : entry.getValue()) {
                System.out.print(vertice.getRepresentation() + " ; ");
            }

            System.out.println();
        }
    }
}
