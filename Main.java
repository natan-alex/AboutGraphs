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
        AStarSearch aStarSearch = null;
        GraphHeuristics graphHeuristics = null;

        // while ((fileLine = bufferedReader.readLine()) != null) {
        // graph = new Graph(fileLine);
        // graphRepresentations = new GraphRepresentations(graph);
        // System.out.println("\nGraph: " + graph.stringRepresentation);
        // graph.showVertices();
        // graphRepresentations.showSuccessorAdjacencyList();
        // deepSearch = new DeepFirstSearch(graph,
        // graphRepresentations.successorAdjacencyList);
        // deepSearch.showTimes();
        // deepSearch.showEdgeClassifications();
        // graphHeuristics = new GraphHeuristics(graph, "[a: 1]");
        // breadthSearch = new BreadthFirstSearch(graph,
        // graphRepresentations.successorAdjacencyList);
        // breadthSearch.showTimes();
        // }

        // graph = new Graph(scanner.nextLine());
        // graphRepresentation = new GraphRepresentations(graph);
        // deepSearch = new DeepSearch(graph,
        // graphRepresentation.successorAdjacencyList);
        // deepSearch.showTimes();
        // deepSearch.showEdgeClassifications();

        graph = new Graph("{ (a,b,1), (b,c,3), (b,d,2), (d,c,4) }");
        graph.showVertices();
        graph.showEdges();
        graphRepresentations = new GraphRepresentations(graph);
        graphRepresentations.showAllRepresentations();
        deepSearch = new DeepFirstSearch(graph, graphRepresentations.successorAdjacencyList);
        deepSearch.showTimes();
        deepSearch.showEdgeClassifications();
        breadthSearch = new BreadthFirstSearch(graph, graphRepresentations.successorAdjacencyList);
        breadthSearch.showTimes();
        graphHeuristics = new GraphHeuristics(graph, "[a: 1, b: 2, c: 3, d: 2]");
        aStarSearch = new AStarSearch(graph, graphRepresentations.successorAdjacencyList, graphHeuristics);
        aStarSearch.showTimes();

        bufferedReader.close();
        scanner.close();
        double end = System.currentTimeMillis();

        System.out.println("Duration: " + (end - start) + "ms\n");
    }
}
