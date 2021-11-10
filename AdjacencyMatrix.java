import java.util.Map;

public class AdjacencyMatrix extends BaseMatrix {
    private Graph relatedGraph;

    public AdjacencyMatrix(Graph graph) {
        super(graph.numberOfVertices, graph.numberOfVertices);
        relatedGraph = graph;

        fillAdjacencyMatrix();
    }

    private void fillAdjacencyMatrix() {
        int currentLine = 0;
        Vertice currentVertice;

        for (Map.Entry<Vertice, Integer> verticeMapEntry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            currentVertice = verticeMapEntry.getKey();

            initializeTheItemsOfTheMatrixLine(currentLine);

            fillMatrixLineAccordingToCurrentVertice(currentLine, currentVertice);

            currentLine++;
        }
    }


    private void initializeTheItemsOfTheMatrixLine(int line) {
        for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++)
            matrix[line][currentColumn] = 0;
    }

    private void fillMatrixLineAccordingToCurrentVertice(int currentLine, Vertice currentVertice) {
        Edge currentEdge;
        int indexOfSecondVertice = 0;

        for (Map.Entry<Edge, Integer> edgeMapEntry : relatedGraph.edgesAndTheirIndices.entrySet()) {
            currentEdge = edgeMapEntry.getKey();

            if (currentVertice.equals(currentEdge.firstVertice)) {
                indexOfSecondVertice = relatedGraph.verticesAndTheirIndices.get(currentEdge.secondVertice);
                matrix[currentLine][indexOfSecondVertice] = 1;
            }
        }
    }

    public void showAdjacencyMatrix() {
        int currentLine = 0;

        showVerticesSeparatedByTabs();

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            System.out.print(entry.getKey().name + "\t");

            showMatrixLineItemsSeparatedByTabs(currentLine);

            System.out.println();
            currentLine++;
        }
    }

    private void showVerticesSeparatedByTabs() {
        System.out.print("\t");

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            System.out.print(entry.getKey().name + "\t");
        }

        System.out.println();
    }

    private void showMatrixLineItemsSeparatedByTabs(int currentLine) {
        for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
            System.out.print(matrix[currentLine][currentColumn] + "\t");
        }
    }
}