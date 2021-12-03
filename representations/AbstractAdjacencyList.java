package representations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.*;
import core.abstractions.AbstractVertice;

public abstract class AbstractAdjacencyList {
    public final Map<AbstractVertice, List<AbstractVertice>> adjacencyList;

    protected AbstractAdjacencyList() {
        adjacencyList = new HashMap<>();
    }

    protected void showAdjacencyList() {
        for (Map.Entry<AbstractVertice, List<AbstractVertice>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey().getRepresentation() + " -> ");

            for (AbstractVertice vertice : entry.getValue()) {
                System.out.print(vertice.getRepresentation() + " ; ");
            }

            System.out.println();
        }
    }
}
