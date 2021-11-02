import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        double start = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);

        BufferedReader bufferedReader = new BufferedReader(new FileReader("graphs.txt"));
        String fileLine;
        Graph graph = null;
        GraphRepresentations graphRepresentation = null;
        DeepSearchStructures deepSearchStructures = null;

        while ((fileLine = bufferedReader.readLine()) != null) {
            graph = new Graph(fileLine);
            // System.out.println();
            // graph.showVertices();
            // System.out.println();
            // graph.showEdges();
            graphRepresentation = new GraphRepresentations(graph);
            graphRepresentation.showAllRepresentations();
            deepSearchStructures = new DeepSearchStructures(graph, graphRepresentation.successorAdjacencyList);
            deepSearchStructures.showTimes();
        }

        bufferedReader.close();
        scanner.close();
        double end = System.currentTimeMillis();

        System.out.println("Duration: " + (end - start) + "ms\n");
    }
}
