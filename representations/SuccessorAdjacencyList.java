<<<<<<< HEAD:representations/SuccessorAdjacencyList.java
package AboutGraphs.representations;

import java.util.ArrayList;
import java.util.List;

import AboutGraphs.core.*;

public class SuccessorAdjacencyList extends BaseAdjacencyList {

    public SuccessorAdjacencyList(Graph graph) {
        super();

        for (Vertice vertice : graph.vertices) {
            adjacencyList.put(vertice, getVerticeChildren(vertice, graph));
        }
    }

    private List<Vertice> getVerticeChildren(Vertice vertice, Graph graph) {
        List<Vertice> verticeChildren = new ArrayList<>();

        if (graph instanceof FlowNetwork) {
            for (FlowEdge flowEdge : ((FlowNetwork) graph).flowEdges) {
                if (vertice.equals(flowEdge.firstVertice))
                    verticeChildren.add(flowEdge.secondVertice);
            }
        } else {
            for (Edge edge : graph.edges) {
                if (vertice.equals(edge.firstVertice))
                    verticeChildren.add(edge.secondVertice);
            }
        }

        return verticeChildren;
    }

    public void showSuccessorAdjacencyList() {
        System.out.println("\n\tSUCCESSOR ADJACENCY LIST\n");
        super.showAdjacencyList();
    }
}
=======
package AboutGraphs.representations;

import java.util.ArrayList;
import java.util.List;

import AboutGraphs.core.*;

public class SuccessorAdjacencyList extends BaseAdjacencyList {

    public SuccessorAdjacencyList(Graph graph) {
        super();

        for (Vertice vertice : graph.vertices) {
            adjacencyList.put(vertice, getVerticeChildren(vertice, graph));
        }
    }

    private List<Vertice> getVerticeChildren(Vertice vertice, Graph graph) {
        List<Vertice> verticeChildren = new ArrayList<>();

        if (graph instanceof FlowNetwork) {
            for (FlowEdge flowEdge : ((FlowNetwork) graph).flowEdges) {
                if (vertice.equals(flowEdge.firstVertice))
                    verticeChildren.add(flowEdge.secondVertice);
            }
        } else {
            for (Edge edge : graph.edges) {
                if (vertice.equals(edge.firstVertice))
                    verticeChildren.add(edge.secondVertice);
            }
        }

        return verticeChildren;
    }

    public void showSuccessorAdjacencyList() {
        System.out.println("\n\tSUCCESSOR ADJACENCY LIST\n");
        super.showAdjacencyList();
    }
}
>>>>>>> 1b387950559dd2ef737390340f12853d43947502:graph_representations/SuccessorAdjacencyList.java
