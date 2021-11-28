package AboutGraphs.representations;

import AboutGraphs.core.*;

public class AllGraphRepresentations {
    public final AdjacencyMatrix adjacencyMatrix;
    public final IncidenceMatrix incidenceMatrix;
    public final SuccessorAdjacencyList successorAdjacencyList;
    public final PredecessorAdjacencyList predecessorAdjacencyList;
    public final SuccessorAdjacencyArrays successorAdjacencyArrays;
    public final PredecessorAdjacencyArrays predecessorAdjacencyArrays;
    private String graphStringRepresentation;

    public AllGraphRepresentations(Graph graph) {
        graphStringRepresentation = graph.stringRepresentation;
        adjacencyMatrix = new AdjacencyMatrix(graph);
        incidenceMatrix = new IncidenceMatrix(graph);
        successorAdjacencyList = new SuccessorAdjacencyList(graph);
        predecessorAdjacencyList = new PredecessorAdjacencyList(graph);
        successorAdjacencyArrays = new SuccessorAdjacencyArrays(graph);
        predecessorAdjacencyArrays = new PredecessorAdjacencyArrays(graph);
    }

    public void showAllRepresentations() {
        System.out.println("\n\tREPRESENTATIONS FOR GRAPH: " + graphStringRepresentation);
        adjacencyMatrix.showAdjacencyMatrix();
        incidenceMatrix.showIncidenceMatrix();
        successorAdjacencyList.showSuccessorAdjacencyList();
        predecessorAdjacencyList.showPredecessorAdjacencyList();
        successorAdjacencyArrays.showSuccessorAdjacencyArrays();
        predecessorAdjacencyArrays.showPredecessorAdjacencyArrays();
    }
}