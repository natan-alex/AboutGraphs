public class AllGraphRepresentations {
    public AdjacencyMatrix adjacencyMatrix;
    public IncidenceMatrix incidenceMatrix;
    public SuccessorAdjacencyList successorAdjacencyList;
    public PredecessorAdjacencyList predecessorAdjacencyList;
    public SuccessorAdjacencyArrays successorAdjacencyArrays;
    public PredecessorAdjacencyArrays predecessorAdjacencyArrays;
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
        adjacencyMatrix.show();
        incidenceMatrix.show();
        successorAdjacencyList.show();
        predecessorAdjacencyList.show();
        successorAdjacencyArrays.show();
        predecessorAdjacencyArrays.show();
    }
}
