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
        AllGraphRepresentations allGraphRepresentations;
        DeepFirstSearch deepSearch = null;
        BreadthFirstSearch breadthSearch = null;
        AStarSearch aStarSearch = null;
        GraphHeuristics graphHeuristics = null;

        graph = new Graph("{(s,u,20), (u,v,30), (s,v,10), (u,t,10), (v,t,20)}");
        FordFulkerson fordFulkerson = new FordFulkerson(graph, "s", "t");

        // while ((fileLine = bufferedReader.readLine()) != null) {
        // graph = new Graph(fileLine);
        // allGraphRepresentations = new AllGraphRepresentations(graph);
        // System.out.println("\nGraph: " + graph.stringRepresentation);
        // graph.showVertices();
        // deepSearch = new DeepFirstSearch(graph,
        // allGraphRepresentations.successorAdjacencyList.adjacencyList);
        // System.out.println("path: " + deepSearch.getPathBetweenVertices("a", "c"));
        // deepSearch.showTimes();
        // graphHeuristics = new GraphHeuristics(graph, "[a: 1]");
        // breadthSearch = new BreadthFirstSearch(graph,
        // allGraphRepresentations.successorAdjacencyList.adjacencyList);
        // System.out.println("path: " + breadthSearch.getPathBetweenVertices("a",
        // "c"));
        // // breadthSearch.computeTimes();
        // breadthSearch.showTimes();
        // aStarSearch = new AStarSearch(graph,
        // allGraphRepresentations.successorAdjacencyList.adjacencyList
        // , graphHeuristics);
        // aStarSearch.showTimes();
        // }

        bufferedReader.close();
        scanner.close();
        double end = System.currentTimeMillis();

        System.out.println("Duration: " + (end - start) + "ms\n");
    }
}
