public class GraphRepresentations {
    public final AdjacencyMatrix adjacencyMatrix;
    public final IncidenceMatrix incidenceMatrix;
    public final SuccessorAdjacencyList successorAdjacencyList;
    public final PredecessorAdjacencyList predecessorAdjacencyList;
    public final SuccessorAdjacencyArrays successorAdjacencyArrays;
    public final PredecessorAdjacencyArrays predecessorAdjacencyArrays;
    public final String graphStringRepresentation;

    public GraphRepresentations(Graph graph) {
        graphStringRepresentation = graph.stringRepresentation;
        successorAdjacencyList = new SuccessorAdjacencyList(graph);
        predecessorAdjacencyList = new PredecessorAdjacencyList(graph);
        adjacencyMatrix = new AdjacencyMatrix(graph);
        incidenceMatrix = new IncidenceMatrix(graph);
        successorAdjacencyArrays = new SuccessorAdjacencyArrays(graph);
        predecessorAdjacencyArrays = new PredecessorAdjacencyArrays(graph);
    }

    public void showAllRepresentations() {
        System.out.println("\n REPRESENTATIONS FOR GRAPH: " + graphStringRepresentation);

        showAdjacencyMatrix();
        showIncidenceMatrix();

        showPredecessorAdjacencyList();
        showSuccessorAdjacencyList();

        showPredecessorAdjacencyArrays();
        showSuccessorAdjacencyArrays();

        System.out.println();
    }

    public void showAdjacencyMatrix() {
        System.out.println("\n\tADJACENCY MATRIX\n");
        adjacencyMatrix.showAdjacencyMatrix();
    }

    public void showIncidenceMatrix() {
        System.out.println("\n\tINCIDENCE MATRIX\n");
        incidenceMatrix.showIncidenceMatrix();
    }

    public void showPredecessorAdjacencyList() {
        System.out.println("\n\tPREDECESSOR ADJACENCY LIST\n");
        predecessorAdjacencyList.showAdjacencyList();
    }

    public void showSuccessorAdjacencyList() {
        System.out.println("\n\tSUCCESSOR ADJACENCY LIST\n");
        successorAdjacencyList.showAdjacencyList();
    }

    public void showPredecessorAdjacencyArrays() {
        System.out.println("\n\tPREDECESSOR ADJACENCY ARRAYS\n");
        predecessorAdjacencyArrays.showAdjacencyArraysIncreasingTheirValuesByOne();
    }

    public void showSuccessorAdjacencyArrays() {
        System.out.println("\n\tSUCCESSOR ADJACENCY ARRAYS\n");
        successorAdjacencyArrays.showAdjacencyArraysIncreasingTheirValuesByOne();
    }
}
