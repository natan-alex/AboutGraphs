import java.util.Map;

public class AdjacencyMatrix extends BaseMatrix {
    private Graph relatedGraph;
    private int indexOfSecondVertice;
    private int currentLine;
    private Vertice currentVertice;
    private Edge currentEdge;

    public AdjacencyMatrix(Graph graph) {
        super(graph.numberOfVertices, graph.numberOfVertices);
        relatedGraph = graph;

        fillAdjacencyMatrix();
    }

    private void fillAdjacencyMatrix() {
        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            currentVertice = entry.getKey();

            for (int j = 0; j < numberOfLines; j++)
                matrix[currentLine][j] = 0;

            fillMatrixAccordingToExistingEdges();

            currentLine++;
        }
    }

    private void fillMatrixAccordingToExistingEdges() {
        for (Map.Entry<Edge, Integer> edgeMapEntry : relatedGraph.edgesAndTheirIndices.entrySet()) {
            currentEdge = edgeMapEntry.getKey();

            if (currentVertice.name.compareTo(currentEdge.firstVertice.name) == 0) {
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

            for (int j = 0; j < numberOfLines; j++) {
                System.out.print(matrix[currentLine][j] + "\t");
            }

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
}