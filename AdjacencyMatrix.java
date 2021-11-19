public class AdjacencyMatrix extends BaseMatrix implements GraphRepresentation {
    private Graph relatedGraph;

    public AdjacencyMatrix(Graph graph) {
        super(graph.numberOfVertices, graph.numberOfVertices);
        relatedGraph = graph;

        fillAdjacencyMatrix();
    }

    private void fillAdjacencyMatrix() {
        int currentLine = 0;

        for (Vertice vertice : relatedGraph.vertices) {
            initializeTheItemsOfTheMatrixLine(currentLine);

            fillMatrixLineGivenTheCurrentVertice(currentLine, vertice);

            currentLine++;
        }
    }

    private void initializeTheItemsOfTheMatrixLine(int line) {
        for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++)
            matrix[line][currentColumn] = 0;
    }

    private void fillMatrixLineGivenTheCurrentVertice(int currentLine, Vertice currentVertice) {
        int indexOfSecondVertice = 0;

        for (Edge edge : relatedGraph.edges) {
            if (currentVertice.equals(edge.firstVertice)) {
                indexOfSecondVertice = relatedGraph.vertices.indexOf(edge.secondVertice);
                matrix[currentLine][indexOfSecondVertice] = 1;
            }
        }
    }

    @Override
    public void show() {
        System.out.println("\n\tADJACENCY MATRIX\n");
        int currentLine = 0;

        showVerticesSeparatedByTabs();

        for (Vertice vertice : relatedGraph.vertices) {
            System.out.print(vertice.name + "\t");

            showMatrixLineItemsSeparatedByTabs(currentLine);

            System.out.println();
            currentLine++;
        }
    }

    private void showVerticesSeparatedByTabs() {
        System.out.print("\t");

        for (Vertice vertice : relatedGraph.vertices) {
            System.out.print(vertice.name + "\t");
        }

        System.out.println();
    }

    private void showMatrixLineItemsSeparatedByTabs(int currentLine) {
        for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
            System.out.print(matrix[currentLine][currentColumn] + "\t");
        }
    }
}