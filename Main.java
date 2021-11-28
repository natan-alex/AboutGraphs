import java.util.Scanner;

import aboutGraphs.core.*;
import aboutGraphs.searches.*;
import aboutGraphs.representations.*;
import aboutGraphs.fordFulkerson.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String sourceVertice;
    private static String sinkVertice;
    private static Graph graph;
    private static double start;
    private static double end;
    private static FordFulkerson fordFulkerson;
    private static int option;

    public static void main(String[] args) throws Exception {
        do {
            System.out.println("MENU");
            System.out.println("[0] -- leave program");
            System.out.println("[1] -- read graph from input");
            System.out.print("Enter the option: ");
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 0:
                    scanner.close();
                    break;
                case 1:
                    System.out.print("Enter the graph: ");
                    graph = new Graph(scanner.nextLine());
                    System.out.print("Source vertice: ");
                    sourceVertice = scanner.nextLine();
                    System.out.print("Sink vertice: ");
                    sinkVertice = scanner.nextLine();
                    doOperationsInGraph();
                    break;
                default:
                    System.out.println("Invalid operation");
                    break;
            }

        } while (option != 0);

    }

    private static void doOperationsInGraph() {
        start = System.currentTimeMillis();

        System.out.println("\nGraph: " + graph.stringRepresentation);
        graph.showVertices();
        graph.showEdges();
        fordFulkerson = new FordFulkerson(graph, sourceVertice, sinkVertice);
        System.out.println("\nDisjoint paths: " + fordFulkerson.computeMaximumFlowAndGetDisjointPaths());

        end = System.currentTimeMillis();
        System.out.println("\nDuration: " + (end - start) + "ms\n");
    }
}
