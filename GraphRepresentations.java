public class GraphRepresentations {
    public final AdjacencyMatrix adjacencyMatrix;
    public final IncidenceMatrix incidenceMatrix;
    public final SuccessorAdjacencyList successorAdjacencyList;
    public final PredecessorAdjacencyList predecessorAdjacencyList;
    public final SuccessorAdjacencyArrays successorAdjacencyArrays;
    public final PredecessorAdjacencyArrays predecessorAdjacencyArrays;

    public GraphRepresentations(Graph graph) {
        successorAdjacencyList = new SuccessorAdjacencyList(graph);
        predecessorAdjacencyList = new PredecessorAdjacencyList(graph);
        adjacencyMatrix = new AdjacencyMatrix(graph);
        incidenceMatrix = new IncidenceMatrix(graph);
        successorAdjacencyArrays = new SuccessorAdjacencyArrays(graph);
        predecessorAdjacencyArrays = new PredecessorAdjacencyArrays(graph);
    }

    public void showAllRepresentations() {
        // System.out.println("\n REPRESENTATIONS FOR GRAPH: " + stringRepresentation);
        System.out.println("\n\tADJACENCY MATRIX\n");
        adjacencyMatrix.showAdjacencyMatrix();

        System.out.println("\n\tINCIDENCY MATRIX\n");
        incidenceMatrix.showIncidenceMatrix();

        System.out.println("\n\tPREDECESSOR ADJACENCY LIST\n");
        predecessorAdjacencyList.showAdjacencyList();

        System.out.println("\n\tSUCCESSOR ADJACENCY LIST\n");
        successorAdjacencyList.showAdjacencyList();

        System.out.println("\n\tPREDECESSOR ADJACENCY ARRAYS\n");
        predecessorAdjacencyArrays.showAdjacencyArraysIncreasingTheirValuesByOne();

        System.out.println("\n\tSUCCESSOR ADJACENCY ARRAYS\n");
        successorAdjacencyArrays.showAdjacencyArraysIncreasingTheirValuesByOne();
        System.out.println();
    }
}
