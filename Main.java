import java.util.Scanner;

import core.*;
import core.abstractions.AbstractFlowNetwork;
import fordFulkerson.*;
import representations.AllGraphRepresentations;
import searches.DeepFirstSearch;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static AbstractFlowNetwork flowNetwork;

    public static void main(String[] args) throws Exception {
        int option;
        do {
            System.out.println("MENU");
            System.out.println("[0] -- leave program");
            System.out.println("[1] -- read flow network from input");
            System.out.print("Enter the option: ");
            option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 0:
                    scanner.close();
                    break;
                case 1:
                    System.out.print("Enter the graph: ");
                    String graphRepresentation = scanner.nextLine();
                    System.out.print("Source vertice: ");
                    String sourceVertice = scanner.nextLine();
                    System.out.print("Sink vertice: ");
                    String sinkVertice = scanner.nextLine();
                    flowNetwork = new FlowNetwork(graphRepresentation, sourceVertice, sinkVertice);
                    doOperationsInGraph();
                    break;
                default:
                    System.out.println("Invalid operation");
                    break;
            }

        } while (option != 0);

    }

    private static void doOperationsInGraph() {
        double start = System.currentTimeMillis();

        System.out.println("\nGraph: " + flowNetwork.getRepresentation());
        flowNetwork.showVertices();
        flowNetwork.showEdges();
        AllGraphRepresentations allGraphRepresentations = new AllGraphRepresentations(flowNetwork);
        allGraphRepresentations.showAllRepresentations();
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork);
        System.out.println("\nDisjoint paths: " + fordFulkerson.computeMaximumFlowAndGetDisjointPaths());
        System.out.println();
        DeepFirstSearch dfs = new DeepFirstSearch(flowNetwork);
        dfs.computeTimes();
        dfs.showTimes();

        double end = System.currentTimeMillis();
        System.out.println("\nDuration: " + (end - start) + "ms\n");
    }
}
