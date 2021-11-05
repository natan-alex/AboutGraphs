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
        GraphRepresentations graphRepresentations = null;
        DeepFirstSearch deepSearch = null;
        BreadthFirstSearch breadthSearch = null;
        GraphHeuristics graphHeuristics = null;

        while ((fileLine = bufferedReader.readLine()) != null) {
            graph = new Graph(fileLine);
            graphRepresentations = new GraphRepresentations(graph);
            System.out.println("\nGraph: " + graph.stringRepresentation);
            graph.showVertices();
            graphRepresentations.showSuccessorAdjacencyList();
            deepSearch = new DeepFirstSearch(graph, graphRepresentations.successorAdjacencyList);
            deepSearch.showTimes();
            deepSearch.showEdgeClassifications();
            graphHeuristics = new GraphHeuristics(graph, "[a: 1]");
            breadthSearch = new BreadthFirstSearch(graph, graphRepresentations.successorAdjacencyList);
            breadthSearch.showTimes();
        }

        // graph = new Graph(scanner.nextLine());
        // graphRepresentation = new GraphRepresentations(graph);
        // deepSearch = new DeepSearch(graph, graphRepresentation.successorAdjacencyList);
        // deepSearch.showTimes();
        // deepSearch.showEdgeClassifications();

        bufferedReader.close();
        scanner.close();
        double end = System.currentTimeMillis();

        System.out.println("Duration: " + (end - start) + "ms\n");
    }
}
