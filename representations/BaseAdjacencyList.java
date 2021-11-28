package AboutGraphs.representations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AboutGraphs.core.*;

public abstract class BaseAdjacencyList {
    public final Map<Vertice, List<Vertice>> adjacencyList;

    protected BaseAdjacencyList() {
        adjacencyList = new HashMap<>();
    }

    protected void showAdjacencyList() {
        for (Map.Entry<Vertice, List<Vertice>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey().name + " -> ");

            for (Vertice vertice : entry.getValue()) {
                System.out.print(vertice.name + " ; ");
            }

            System.out.println();
        }
    }
}
