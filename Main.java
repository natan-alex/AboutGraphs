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
        DeepSearch deepSearch = null;
        BreadthSearch breadthSearch = null;

        while ((fileLine = bufferedReader.readLine()) != null) {
            graph = new Graph(fileLine);
            // graph.showEdges();
            graphRepresentation = new GraphRepresentations(graph);
            // graphRepresentation.showAllRepresentations();
            System.out.println("\nGraph: " + graph.stringRepresentation);
            graph.showVertices();
            graphRepresentation.successorAdjacencyList.adjacencyList.forEach((v, l) -> {
                System.out.print(v.name + " -> ");
                l.forEach(v2 -> System.out.print(v2.name + " "));
                System.out.println();
            });
            deepSearch = new DeepSearch(graph, graphRepresentation.successorAdjacencyList);
            deepSearch.showTimes();
            deepSearch.showEdgeClassifications();
            breadthSearch = new BreadthSearch(graph, graphRepresentation.successorAdjacencyList);
            breadthSearch.showTimes();
        }

        // graph = new Graph(scanner.nextLine());
        // graphRepresentation = new GraphRepresentations(graph);
        // deepSearchStructures = new DeepSearch(graph,
        // graphRepresentation.successorAdjacencyList);
        // deepSearchStructures.showTimes();
        // deepSearchStructures.showEdgeClassifications();

        bufferedReader.close();
        scanner.close();
        double end = System.currentTimeMillis();

        System.out.println("Duration: " + (end - start) + "ms\n");
    }
}
