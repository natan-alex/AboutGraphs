import java.util.Map;

public class IncidenceMatrix extends BaseMatrix {
    private Graph relatedGraph;
    private int currentColumn;
    private int currentLine;
    private Edge currentEdge;
    private Vertice currentVertice;
    private int currentMatrixItem;
    private int indexOfSecondVertice;

    public IncidenceMatrix(Graph graph) {
        super(graph.numberOfVertices, graph.numberOfEdges);
        relatedGraph = graph;

        fillIncidenceMatrix();
    }

    private void fillIncidenceMatrix() {
        currentColumn = 0; 
        currentLine = 0;

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            currentColumn = 0;
            currentVertice = entry.getKey();

            fillMatrixAccordingToExistingEdges();

            currentLine++;
        }
    }

    private void fillMatrixAccordingToExistingEdges() {
        for (Map.Entry<Edge, Integer> edgeMapEntry : relatedGraph.edgesAndTheirIndices.entrySet()) {
            currentEdge = edgeMapEntry.getKey();

            if (currentVertice.name.compareTo(currentEdge.firstVertice.name) == 0) {
                indexOfSecondVertice = relatedGraph.verticesAndTheirIndices.get(currentEdge.secondVertice);
                matrix[currentLine][currentColumn] = 1;
                matrix[indexOfSecondVertice][currentColumn] = -1;
            } else if (matrix[currentLine][currentColumn] != -1) {
                matrix[currentLine][currentColumn] = 0;
            }

            currentColumn++;
        }
    }

    public void showIncidenceMatrix() {
        currentLine = 0;
        currentColumn = 0;

        showEdgesSeparatedByTabs();

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            System.out.print(entry.getKey().name + "\t");
            currentColumn = 0;

            showCurrentLineItems();

            currentLine++;
            System.out.println();
        }
    }

    private void showEdgesSeparatedByTabs() {
        System.out.print("\t");

        for (Map.Entry<Edge, Integer> entry : relatedGraph.edgesAndTheirIndices.entrySet())
            System.out.print(entry.getKey().stringRepresentation + "\t");

        System.out.println();
    }

    private void showCurrentLineItems() {
        for (Map.Entry<Edge, Integer> innerEntry : relatedGraph.edgesAndTheirIndices.entrySet()) {
            currentMatrixItem = matrix[currentLine][currentColumn];

            if (currentMatrixItem == 1)
                System.out.print("+");
            else if (currentMatrixItem == 0)
                System.out.print(" ");

            System.out.print(currentMatrixItem);

            if (relatedGraph.isPondered)
                System.out.print(" | " + innerEntry.getKey().value);

            System.out.print("\t");
            currentColumn++;
        }
    }
}