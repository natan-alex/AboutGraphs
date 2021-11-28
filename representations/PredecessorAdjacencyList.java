<<<<<<< HEAD:representations/PredecessorAdjacencyList.java
package AboutGraphs.representations;

import java.util.ArrayList;
import java.util.List;

import AboutGraphs.core.*;

public class PredecessorAdjacencyList extends BaseAdjacencyList {
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

    public void showPredecessorAdjacencyList() {
        System.out.println("\n\tPREDECESSOR ADJACENCY LIST\n");
        super.showAdjacencyList();
    }
=======
package AboutGraphs.representations;

import java.util.ArrayList;
import java.util.List;

import AboutGraphs.core.*;

public class PredecessorAdjacencyList extends BaseAdjacencyList {
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

    public void showPredecessorAdjacencyList() {
        System.out.println("\n\tPREDECESSOR ADJACENCY LIST\n");
        super.showAdjacencyList();
    }
>>>>>>> 1b387950559dd2ef737390340f12853d43947502:graph_representations/PredecessorAdjacencyList.java
}