import java.util.Map;

public class IncidenceMatrix extends BaseMatrix {
    private Graph relatedGraph;

    public IncidenceMatrix(Graph graph) {
        super(graph.numberOfVertices, graph.numberOfEdges);
        relatedGraph = graph;

        int currentLine = 0;
        Vertice currentVertice;

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            currentVertice = entry.getKey();

            fillMatrixLineAccordingToCurrentVertice(currentLine, currentVertice);

            currentLine++;
        }
    }

    private void fillMatrixLineAccordingToCurrentVertice(int currentLine, Vertice currentVertice) {
        int currentColumn = 0;
        Edge currentEdge;
        int indexOfSecondVertice;

        for (Map.Entry<Edge, Integer> edgeMapEntry : relatedGraph.edgesAndTheirIndices.entrySet()) {
            currentEdge = edgeMapEntry.getKey();

            if (currentVertice.equals(currentEdge.firstVertice)) {
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
        int currentLine = 0;

        showEdgesSeparatedByTabs();

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            System.out.print(entry.getKey().name + "\t");

            showCurrentLineItems(currentLine);

            System.out.println();

            currentLine++;
        }
    }

    private void showEdgesSeparatedByTabs() {
        System.out.print("\t");

        for (Map.Entry<Edge, Integer> entry : relatedGraph.edgesAndTheirIndices.entrySet())
            System.out.print(entry.getKey().stringRepresentation + "\t\t");

        System.out.println();
    }

    private void showCurrentLineItems(int currentLine) {
        if (relatedGraph.isPondered)
            showMatrixForPonderedGraph(currentLine);
        else
            showMatrixForUnponderedGraph(currentLine);
    }

    private void showMatrixForPonderedGraph(int currentLine) {
        int currentColumn = 0;

        for (Map.Entry<Edge, Integer> innerEntry : relatedGraph.edgesAndTheirIndices.entrySet()) {
            showMatrixItem(matrix[currentLine][currentColumn]);

            System.out.print(" | " + innerEntry.getKey().value);

            System.out.print("\t");
            currentColumn++;
        }
    }

    private void showMatrixForUnponderedGraph(int currentLine) {
        for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
            showMatrixItem(matrix[currentLine][currentColumn]);

            System.out.print("\t");
        }
    }

    private void showMatrixItem(int item) {
        if (item == 1)
            System.out.print("+");
        else if (item == 0)
            System.out.print(" ");

        System.out.print(item);
    }
}