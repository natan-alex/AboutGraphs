import java.util.Map;

public class AdjacencyMatrix extends BaseMatrix {
    private Graph relatedGraph;

    public AdjacencyMatrix(Graph graph) {
        super(graph.numberOfVertices, graph.numberOfVertices);
        relatedGraph = graph;

        fillAdjacencyMatrix();
    }

    private void fillAdjacencyMatrix() {
        int indexOfSecondVertice;
        int currentLine = 0;

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            for (int j = 0; j < numberOfLines; j++)
                matrix[currentLine][j] = 0;

            for (Map.Entry<Edge, Integer> innerEntry : relatedGraph.edgesAndTheirIndices.entrySet()) {
                if (entry.getKey().name.compareTo(innerEntry.getKey().firstVertice.name) == 0) {
                    indexOfSecondVertice = relatedGraph.verticesAndTheirIndices.get(innerEntry.getKey().secondVertice);
                    matrix[currentLine][indexOfSecondVertice] = 1;
                }
            }

            currentLine++;
        }
    }

    public void showAdjacencyMatrix() {
        int currentLine = 0;

        System.out.print("\t");

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            System.out.print(entry.getKey().name + "\t");
        }

        System.out.println();

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.verticesAndTheirIndices.entrySet()) {
            System.out.print(entry.getKey().name + "\t");

            for (int j = 0; j < numberOfLines; j++) {
                System.out.print(matrix[currentLine][j] + "\t");
            }

            System.out.println();
            currentLine++;
        }
    }
}