import java.util.Iterator;
import java.util.Map;

public class AdjacencyMatrix extends BaseMatrix {
    private Graph relatedGraph;

    public AdjacencyMatrix(Graph graph) {
        super(graph.numberOfVertices, graph.numberOfVertices);
        relatedGraph = graph;

        fillAdjacencyMatrix();
    }

    private void fillAdjacencyMatrix() {
        int indexOfSecondVertice = 0;
        Iterator<Vertice> verticeIterator = relatedGraph.verticesAndTheirIndices.keySet().iterator();
        Iterator<Edge> edgeIterator;
        Edge currentEdge;
        Vertice currentVertice;

        for (int currentLine = 0; currentLine < numberOfLines; currentLine++) {
            edgeIterator = relatedGraph.edgesAndTheirIndices.keySet().iterator();
            currentVertice = verticeIterator.next();

            initializeMatrixColumnsForLine(currentLine);

            for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++) {
                currentEdge = edgeIterator.next();

                if (currentVertice.equals(currentEdge.firstVertice)) {
                    indexOfSecondVertice = relatedGraph.verticesAndTheirIndices.get(currentEdge.secondVertice);
                    matrix[currentLine][indexOfSecondVertice] = 1;
                }
            }
        }
    }

    private void initializeMatrixColumnsForLine(int currentLine) {
        for (int currentColumn = 0; currentColumn < numberOfColumns; currentColumn++)
            matrix[currentLine][currentColumn] = 0;
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