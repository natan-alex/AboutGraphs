import java.util.Map;

public class IncidenceMatrix extends BaseMatrix {
    private Graph relatedGraph;

    public IncidenceMatrix(Graph graph) {
        relatedGraph = graph;
        this.numberOfLines = graph.numberOfVertices;
        this.numberOfColumns = graph.numberOfEdges;
        this.matrix = new int[numberOfLines][numberOfColumns];

        fillIncidenceMatrixForGraph(graph);
    }

    private void fillIncidenceMatrixForGraph(Graph graph) {
        int indexOfSecondVertice;
        int currentColumn = 0, currentLine = 0;

        for (Map.Entry<Vertice, Integer> entry : graph.vertices.entrySet()) {
            currentColumn = 0;

            for (Map.Entry<Edge, Integer> innerEntry : graph.edges.entrySet()) {
                if (entry.getKey().name.compareTo(innerEntry.getKey().firstVertice.name) == 0) {
                    indexOfSecondVertice = graph.vertices.get(innerEntry.getKey().secondVertice);
                    matrix[currentLine][currentColumn] = 1;
                    matrix[indexOfSecondVertice][currentColumn] = -1;
                } else if (matrix[currentLine][currentColumn] != -1) {
                    matrix[currentLine][currentColumn] = 0;
                }

                currentColumn++;
            }

            currentLine++;
        }
    }

    public void showIncidenceMatrix() {
        int currentLine = 0, currentColumn = 0;
        int currentItem;

        System.out.print("\t");

        for (Map.Entry<Edge, Integer> entry : relatedGraph.edges.entrySet())
            System.out.print(entry.getKey().firstVertice.name + " -- " + entry.getKey().secondVertice.name + "\t");

        System.out.println();

        for (Map.Entry<Vertice, Integer> entry : relatedGraph.vertices.entrySet()) {
            System.out.print(entry.getKey().name + "\t");
            currentColumn = 0;

            for (Map.Entry<Edge, Integer> innerEntry : relatedGraph.edges.entrySet()) {
                currentItem = matrix[currentLine][currentColumn];

                if (currentItem == 1)
                    System.out.print("+");
                else if (currentItem == 0)
                    System.out.print(" ");

                System.out.print(currentItem);

                if (innerEntry.getKey().isPondered)
                    System.out.print(" | " + innerEntry.getKey().value);

                System.out.print("\t");
                currentColumn++;
            }

            currentLine++;
            System.out.println();
        }
    }
}