package aboutGraphs.representations;

import aboutGraphs.core.*;

public class IncidenceMatrix extends BaseMatrix {
    private Graph relatedGraph;

    public IncidenceMatrix(Graph graph) {
        super(graph.numberOfVertices, graph.numberOfEdges);
        relatedGraph = graph;

        int currentLine = 0;

        for (Vertice vertice : relatedGraph.vertices) {
            fillMatrixLineGivenTheCurrentVertice(currentLine, vertice);

            currentLine++;
        }
    }

    private void fillMatrixLineGivenTheCurrentVertice(int currentLine, Vertice currentVertice) {
        int currentColumn = 0;
        int indexOfSecondVertice;

        for (Edge edge : relatedGraph.edges) {
            if (currentVertice.equals(edge.firstVertice)) {
                indexOfSecondVertice = relatedGraph.vertices.indexOf(edge.secondVertice);
                matrix[currentLine][currentColumn] = 1;
                matrix[indexOfSecondVertice][currentColumn] = -1;
            } else if (matrix[currentLine][currentColumn] != -1) {
                matrix[currentLine][currentColumn] = 0;
            }

            currentColumn++;
        }
    }

    public void showIncidenceMatrix() {
        System.out.println("\n\tINCIDENCE MATRIX\n");
        int currentLine = 0;

        showEdgesSeparatedByTabs();

        for (Vertice vertice : relatedGraph.vertices) {
            System.out.print(vertice.name + "\t");

            showCurrentLineItems(currentLine);

            System.out.println();

            currentLine++;
        }
    }

    private void showEdgesSeparatedByTabs() {
        System.out.print("\t");

        for (Edge edge : relatedGraph.edges)
            System.out.print(edge.stringRepresentation + "\t");

        System.out.println();
    }

    private void showCurrentLineItems(int currentLine) {
        if (relatedGraph.type.isPondered)
            showMatrixForPonderedGraph(currentLine);
        else
            showMatrixForUnponderedGraph(currentLine);
    }

    private void showMatrixForPonderedGraph(int currentLine) {
        int currentColumn = 0;

        for (Edge edge : relatedGraph.edges) {
            showMatrixItem(matrix[currentLine][currentColumn]);

            System.out.print(" | " + edge.value);

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