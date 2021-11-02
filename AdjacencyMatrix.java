import java.util.Map;

public class AdjacencyMatrix extends BaseMatrix {
    private Graph relatedGraph;

    public AdjacencyMatrix(Graph graph) {
        relatedGraph = graph;
        numberOfLines = graph.numberOfVertices;
        numberOfColumns = graph.numberOfVertices;
        matrix = new int[numberOfLines][numberOfColumns];

        fillAdjacencyMatrix();
    }

    private void fillAdjacencyMatrix() {
        int indexOfSecondVertice;
        int currentLine = 0;

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.vertices.entrySet()) {
            for (int j = 0; j < numberOfLines; j++)
                matrix[currentLine][j] = 0;

            for (Map.Entry<Edge, Integer> innerEntry : relatedGraph.edges.entrySet()) {
                if (entry.getKey().name.compareTo(innerEntry.getKey().firstVertice.name) == 0) {
                    indexOfSecondVertice = relatedGraph.vertices.get(innerEntry.getKey().secondVertice);
                    matrix[currentLine][indexOfSecondVertice] = 1;
                }
            }

            currentLine++;
        }
    }

    public void showAdjacencyMatrix() {
        int currentLine = 0;

        System.out.print("\t");

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.vertices.entrySet()) {
            System.out.print(entry.getKey().name + "\t");
        }

        System.out.println();

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.vertices.entrySet()) {
            System.out.print(entry.getKey().name + "\t");

            for (int j = 0; j < numberOfLines; j++) {
                System.out.print(matrix[currentLine][j] + "\t");
            }

            System.out.println();
            currentLine++;
        }
    }
}